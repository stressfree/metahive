package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * The Class ValidatedDataGrid.
 */
public class ValidatedDataGrid {

	/** The title. */
	private String title = "";
	
	/** The validated header fields. */
	private List<ValidatedField> validatedHeaderFields = new ArrayList<ValidatedField>();
	
	/** The validated rows. */
	private List<ValidatedRow> validatedRows = new ArrayList<ValidatedRow>();
	
	
	/**
	 * Instantiates a new validated data grid.
	 *
	 * @param dataGrid the data grid
	 */
	public ValidatedDataGrid(DataGrid dataGrid) {
		if (dataGrid != null) {
			title = dataGrid.getTitle();
			validateData(dataGrid);
		}
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public final String getTitle() {		
		return title;
	}
	
	/**
	 * Gets the header fields.
	 *
	 * @return the header fields
	 */
	public List<ValidatedField> getHeaderFields() {
		return validatedHeaderFields;
	}
		
	/**
	 * Process the verified data.
	 *
	 * @param data the data
	 * @param person the person
	 * @param organisation the organisation
	 * @return the int
	 */
	public int processData(final String data, final Person person, 
			final Organisation organisation) {

		int processCount = 0;
				
		MetahivePreferences prefs = MetahivePreferences.load();
		
		// Create the new submission
		Submission submission = new Submission();
		submission.setPerson(person);
		submission.setOrganisation(organisation);
		submission.setRawData(data);
					
		submission.persist();
		
		
		TreeMap<Integer, Definition> definitions = new TreeMap<Integer, Definition>();		
		int columnIndex = 0;
		int recordIndex = 0;
		
		for (ValidatedField field : this.getHeaderFields()) {
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
		
		for (ValidatedRow row : this.getRows()) {
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
		
		if (processCount == 0) {
			// Delete the empty submission
			submission.remove();
		}
		
		return processCount;		
	}

	/**
	 * Gets the rows as a list.
     *
	 * @return the list of row data
	 */
	public List<ValidatedRow> getRows() {
		return validatedRows;
	}
	
	/**
	 * Validate data.
	 *
	 * @param dataGrid the data grid
	 */
	private void validateData(DataGrid dataGrid) {
		
		MetahivePreferences prefs = MetahivePreferences.load();
		
		int primaryColumnId = 0;
		String primaryRecordName = prefs.getPrimaryRecordName();
				
		// Check that each header is a valid definition
		int columnCounter = 0;
		HashMap<Integer, Integer> validColumns = new HashMap<Integer, Integer>();
		
		for (String header : dataGrid.getHeaderFields()) {
			
			if (StringUtils.equalsIgnoreCase(primaryRecordName, header)) {
				primaryColumnId = columnCounter;
			}

			ValidatedField validatedHeader = new ValidatedField();
			validatedHeader.setValue(header);
			
			try {
				Definition definition = Definition.findDefinitionByNameEquals(header);
				if (definition != null) {
					validatedHeader.setValid(true);
					validColumns.put(columnCounter, columnCounter);
				}
			} catch (Exception e) {
				// Error loading definition
			}			
			validatedHeaderFields.add(validatedHeader);			
			
			columnCounter++;
		}
		
		// Identify the primary column id and check to see if the records are valid
		for (List<String> row : dataGrid.getRows()) {
			
			ValidatedRow validatedRow = new ValidatedRow();
			
			try {				
				String recordId = row.get(primaryColumnId);
				String primaryRecordId = Record.parsePrimaryRecordId(recordId, prefs);
				
				Record record = Record.findRecordByRecordIdEquals(primaryRecordId);
				if (record != null && StringUtils.isNotBlank(record.getRecordId())) {
					validatedRow.setValid(true);
				}	
			} catch (Exception e) {
				// Error verifying record id
			}
			
			List<ValidatedField> validatedFields = new ArrayList<ValidatedField>();
			
			columnCounter = 0;
			
			for (String field : row) {
				ValidatedField validatedField = new ValidatedField();
				validatedField.setValue(field);
				if (validColumns.containsKey(columnCounter)) {
					validatedField.setValid(true);					
				}				
				validatedFields.add(validatedField);
				
				columnCounter++;
			}
			validatedRow.setFields(validatedFields);
			
			validatedRows.add(validatedRow);
		}		
	}
	
}
