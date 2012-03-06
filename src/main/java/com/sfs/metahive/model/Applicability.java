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
package com.sfs.metahive.model;

import org.apache.commons.lang.StringUtils;

/**
 * The Class Applicability.
 */
public enum Applicability {

    RECORD_PRIMARY,
    RECORD_SECONDARY,
    RECORD_TERTIARY;


    /**
     * Gets the metahive name.
     *
     * @return the metahive name
     */
    public String getMetahiveName() {
        String metahiveName = "";

        MetahivePreferences preferences = MetahivePreferences.load();

        if (this == Applicability.RECORD_PRIMARY
                && StringUtils.isNotBlank(preferences.getPrimaryRecordName())) {
            metahiveName = preferences.getPrimaryRecordName();
        }
        if (this == Applicability.RECORD_SECONDARY
                && StringUtils.isNotBlank(preferences.getSecondaryRecordName())) {
            metahiveName = preferences.getSecondaryRecordName();
        }
        if (this == Applicability.RECORD_TERTIARY
                && StringUtils.isNotBlank(preferences.getTertiaryRecordName())) {
            metahiveName = preferences.getTertiaryRecordName();
        }

        return metahiveName;
    }

}
