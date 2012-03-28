/*******************************************************************************
 * Copyright (c) 2012 David Harrison, Triptech Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     David Harrison, Triptech Ltd - initial API and implementation
 ******************************************************************************/
package net.triptech.metahive.model;

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

        ValidatedField primaryHeader = new ValidatedField();
        primaryHeader.setValue(prefs.getPrimaryRecordName());
        primaryHeader.setIdField(true);
        primaryHeader.setValid(true);
        validatedHeaderFields.add(primaryHeader);

        if (StringUtils.isNotBlank(prefs.getSecondaryRecordName())) {
            ValidatedField secondaryHeader = new ValidatedField();
            secondaryHeader.setValue(prefs.getSecondaryRecordName());
            secondaryHeader.setIdField(true);
            secondaryHeader.setValid(true);
            validatedHeaderFields.add(secondaryHeader);
        }
        if (StringUtils.isNotBlank(prefs.getTertiaryRecordName())) {
            ValidatedField tertiaryHeader = new ValidatedField();
            tertiaryHeader.setValue(prefs.getTertiaryRecordName());
            tertiaryHeader.setIdField(true);
            tertiaryHeader.setValid(true);
            validatedHeaderFields.add(tertiaryHeader);
        }

        HashMap<Integer, Definition> definitions = new HashMap<Integer, Definition>();

        for (String header : dataGrid.getHeaderFields()) {

            if (StringUtils.equalsIgnoreCase(primaryRecordName, header)) {
                // Note down the primary record column id, but don't add it to the array
                primaryColumnId = columnCounter;
            } else {
                ValidatedField validatedHeader = new ValidatedField();
                validatedHeader.setValue(header);

                try {
                    Definition definition = Definition.findDefinitionByNameEquals(header);
                    if (definition != null && definition.getDefinitionType()
                            == DefinitionType.STANDARD) {
                        validatedHeader.setValid(true);
                        definitions.put(columnCounter, definition);
                    }
                } catch (Exception e) {
                    // Error loading definition
                }
                validatedHeaderFields.add(validatedHeader);
            }
            columnCounter++;
        }

        // Identify the primary column id and check to see if the records are valid
        for (List<String> row : dataGrid.getRows()) {

            ValidatedRow validatedRow = new ValidatedRow();

            String primaryRecordId = "";
            String secondaryRecordId = "";
            String tertiaryRecordId = "";

            try {
                String recordId = row.get(primaryColumnId);
                primaryRecordId = Record.parsePrimaryRecordId(recordId, prefs);
                secondaryRecordId = Record.parseSecondaryRecordId(recordId, prefs);
                tertiaryRecordId = Record.parseTertiaryRecordId(recordId, prefs);

                Record record = Record.findRecordByRecordIdEquals(primaryRecordId);
                if (record != null && StringUtils.isNotBlank(record.getRecordId())) {
                    validatedRow.setValid(true);
                }
            } catch (Exception e) {
                // Error verifying record id
            }

            List<ValidatedField> validatedFields = new ArrayList<ValidatedField>();

            // Add the primary, secondary and tertiary ids if applicable
            ValidatedField primaryField = new ValidatedField();
            primaryField.setValue(primaryRecordId);
            primaryField.setValid(true);
            primaryField.setIdField(true);
            validatedFields.add(primaryField);

            if (StringUtils.isNotBlank(prefs.getSecondaryRecordName())) {
                ValidatedField secondaryField = new ValidatedField();
                secondaryField.setValue(secondaryRecordId);
                secondaryField.setValid(true);
                secondaryField.setIdField(true);
                validatedFields.add(secondaryField);
            }
            if (StringUtils.isNotBlank(prefs.getTertiaryRecordName())) {
                ValidatedField tertiaryField = new ValidatedField();
                tertiaryField.setValue(tertiaryRecordId);
                tertiaryField.setValid(true);
                tertiaryField.setIdField(true);
                validatedFields.add(tertiaryField);
            }

            columnCounter = 0;

            for (String field : row) {
                if (columnCounter != primaryColumnId) {
                    ValidatedField validatedField = new ValidatedField();
                    validatedField.setValue(field);
                    if (definitions.containsKey(columnCounter)) {
                        Definition definition = definitions.get(columnCounter);
                        if (definition != null) {
                            validatedField.setValid(true);
                            validatedField.setNotApplicable(checkNotApplicable(
                                    definition, secondaryRecordId, tertiaryRecordId));
                        }
                    }
                    validatedFields.add(validatedField);
                }
                columnCounter++;
            }
            validatedRow.setFields(validatedFields);

            validatedRows.add(validatedRow);
        }
    }

    /**
     * Check whether this field is not applicable.
     *
     * @param definition the definition
     * @param secondaryRecordId the secondary record id
     * @param tertiaryRecordId the tertiary record id
     * @return true, if successful
     */
    private boolean checkNotApplicable(final Definition definition,
            final String secondaryRecordId, final String tertiaryRecordId) {

        boolean notApplicable = false;

        if (definition.getApplicability() == Applicability.RECORD_SECONDARY
                && StringUtils.isBlank(secondaryRecordId)) {
            notApplicable = true;
        }
        if (definition.getApplicability() == Applicability.RECORD_TERTIARY
                && StringUtils.isBlank(tertiaryRecordId)) {
            notApplicable = true;
        }
        return notApplicable;
    }

}
