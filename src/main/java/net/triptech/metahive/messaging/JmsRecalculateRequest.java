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
package net.triptech.metahive.messaging;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import net.triptech.metahive.model.KeyValue;
import net.triptech.metahive.model.SubmittedField;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;



/**
 * The Class JmsRecalculateRequest.
 */
@RooJavaBean
@RooToString
public class JmsRecalculateRequest implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7526471151222776147L;

    /**
     * Instantiates a new jms recalculate request.
     *
     * @param field the submitted field
     */
    public JmsRecalculateRequest(final SubmittedField field) {
        super();
        this.primaryRecordId = field.getPrimaryRecordId();
        this.secondaryRecordId = field.getSecondaryRecordId();
        this.tertiaryRecordId = field.getTertiaryRecordId();
        this.definitionId = field.getDefinition().getId();
    }

    /**
     * Instantiates a new jms recalculate request.
     *
     * @param kv the key value
     */
    public JmsRecalculateRequest(final KeyValue kv) {
        super();
        this.primaryRecordId = kv.getPrimaryRecordId();
        this.secondaryRecordId = kv.getSecondaryRecordId();
        this.tertiaryRecordId = kv.getTertiaryRecordId();
        this.definitionId = kv.getDefinition().getId();
    }

    /**
     * Instantiates a new jms recalculate request.
     */
    public JmsRecalculateRequest() { }

    /** The primary record id. */
    @NotNull
    private String primaryRecordId;

    /** The secondary record id. */
    private String secondaryRecordId;

    /** The tertiary record id. */
    private String tertiaryRecordId;

    /** The definition id. */
    private Long definitionId;

}
