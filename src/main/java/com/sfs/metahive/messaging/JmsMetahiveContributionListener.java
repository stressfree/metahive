package com.sfs.metahive.messaging;

import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.sfs.metahive.messaging.JmsRecalculateRequest;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.MetahivePreferences;
import com.sfs.metahive.model.Record;
import com.sfs.metahive.model.Submission;
import com.sfs.metahive.model.SubmittedField;
import com.sfs.metahive.model.ValidatedDataGrid;
import com.sfs.metahive.model.ValidatedField;
import com.sfs.metahive.model.ValidatedRow;

/**
 * The listener interface for receiving jmsMetahiveContribution events.
 * The class that is interested in processing a jmsMetahiveContribution
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addJmsMetahiveContributionListener<code> method. When
 * the jmsMetahiveContribution event occurs, that object's appropriate
 * method is invoked.
 *
 * @see JmsMetahiveContributionEvent
 */
public class JmsMetahiveContributionListener {

	@Autowired
	private transient JmsTemplate keyValueGenerationTemplate;
	
    /** The logger. */
    private static Logger logger = 
    		Logger.getLogger(JmsMetahiveContributionListener.class);
    
    /**
     * On message.
     *
     * @param message the message
     */
    public void onMessage(Object message) {
    	
        logger.debug("JMS message received: " + message);
    	
    	if (message instanceof Long) {
    		// Load the submission for processing
    		Submission submission = Submission.findSubmission((Long) message);
    		
    		if (submission != null) {
    			int processCount = processSubmission(submission);
    			logger.info(processCount + " new values added");
    		} else {
    			logger.error("No submission found for: " + message);
    		}
    	}
    }
    
    private int processSubmission(final Submission submission) {
    	
    	int processCount = 0;
    	
    	// Build the validated data grid
    	ValidatedDataGrid dataGrid = new ValidatedDataGrid(submission.getRawDataGrid());
    	
    	MetahivePreferences prefs = MetahivePreferences.load();
		
		
		TreeMap<Integer, Definition> definitions = new TreeMap<Integer, Definition>();		
		int columnIndex = 0;
		int recordIndex = 0;
		
		for (ValidatedField field : dataGrid.getHeaderFields()) {
			if (field.isValid()) {
				if (StringUtils.equalsIgnoreCase(prefs.getPrimaryRecordName(),
						field.getValue())) {
					recordIndex = columnIndex;
				} else {
					Definition definition = Definition.findDefinitionByNameEquals(
							field.getValue());
					definitions.put(columnIndex, definition);
				}
			}
			columnIndex++;
		}
		
		for (ValidatedRow row : dataGrid.getRows()) {
			if (row.isValid()) {
				// Load the record
				String recordId = row.getFields().get(recordIndex).getValue();
				
				String primaryRecord = Record.parsePrimaryRecordId(recordId, prefs);
				String secondaryRecord = Record.parseSecondaryRecordId(recordId, prefs);
				String tertiaryRecord = Record.parseTertiaryRecordId(recordId, prefs);
				
				Record record = Record.findRecordByRecordIdEquals(primaryRecord);
				
				boolean fieldCreated = false;
				
				if (record != null) {
					for (int index : definitions.keySet()) {
						Definition definition = definitions.get(index);
						ValidatedField cell = row.getFields().get(index);
						if (cell.isValid() && StringUtils.isNotBlank(cell.getValue())) {
							SubmittedField field = new SubmittedField();
							
							field.setDefinition(definition);
							field.setRecord(record);
							field.setSubmission(submission);
							
							field.setPrimaryRecordId(primaryRecord);
							field.setSecondaryRecordId(secondaryRecord);
							field.setTertiaryRecordId(tertiaryRecord);
							
							field.setValue(cell.getValue().trim());
							
							field.persist();
							
							processCount++;
							fieldCreated = true;
							
							JmsRecalculateRequest req = new JmsRecalculateRequest(field);
							keyValueGenerationTemplate.convertAndSend(req);
						}
					}
				}
				
				if (fieldCreated) {
					// Update the secondary/tertiary record counts if applicable
					if (!record.containsSecondaryRecord(secondaryRecord)
							|| !record.containsTertiaryRecord(tertiaryRecord)) {
						record.addSecondaryRecord(secondaryRecord);
						record.addTertiaryRecord(tertiaryRecord);
						record.merge();
					}
				}				
			}
		}
    	return processCount;
    }
}
