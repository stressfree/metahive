package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
