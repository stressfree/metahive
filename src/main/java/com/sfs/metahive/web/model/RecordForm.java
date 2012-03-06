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
package com.sfs.metahive.web.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.sfs.metahive.DataParser;
import com.sfs.metahive.model.Record;
import com.sfs.metahive.model.MetahivePreferences;

/**
 * The Class RecordForm.
 */
@RooJavaBean
public class RecordForm extends BackingForm {

    /** The id. */
    private long id;

    /** The version. */
    private long version;

    /** The record id. */
    @NotNull
    @Size(min = 1, max = 255)
    private String recordId;

    /** A collection of record ids for generating. */
    @NotNull
    private String recordIds;


    /**
     * Creates a new Record object from the form data.
     *
     * @param preferences the Metahive preferences
     * @return the definition
     */
    public final Record newRecord(final MetahivePreferences prefs) {
        return Record.build(this.recordId, prefs);
    }

    /**
     * Parses the records string into a collection of Record objects.
     *
     * @param prefs the prefs
     * @return the collection
     */
    public final Collection<Record> parseRecords(final MetahivePreferences prefs) {

        Collection<Record> records = new ArrayList<Record>();

        if (StringUtils.isNotBlank(recordIds)) {
            String[][] data = DataParser.parseTextData(recordIds);

            for (int y = 0; y < data.length; y++) {
                for (int x = 0; x < data[y].length; x++) {
                    if (StringUtils.isNotBlank(data[y][x])) {
                        Record rd = Record.build(data[y][x], prefs);
                        if (rd != null && StringUtils.isNotBlank(rd.getRecordId())) {
                            records.add(rd);
                        }
                    }
                }
            }
        }
        return records;
    }

}
