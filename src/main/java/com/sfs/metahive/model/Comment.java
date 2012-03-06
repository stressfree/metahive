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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Comment.
 */
@RooJavaBean
@RooToString
@RooEntity
public class Comment {

    /** The comment type. */
    @NotNull
    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    /** The message. */
    @Lob
    private String message;

    /** The related definition. */
    @ManyToOne
    private Definition definition;

    /** The related record. */
    @ManyToOne
    private Record record;

    /** The related description id if applicable. */
    private Long descriptionId;

    /** The related data source id if applicable. */
    private Long dataSourceId;

    /** The related key value id if applicable */
    private Long keyValueId;

    /** The person who created the comment. */
    @NotNull
    @ManyToOne
    private Person person;

    /** The created timestamp. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    /**
     * The on create actions.
     */
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    /**
     * Gets the related object type.
     *
     * @return the related object
     */
    public String getRelatedObject() {
        String changedObject = "definition";

        if (this.dataSourceId != null && this.dataSourceId > 0) {
            changedObject = "datasource";
        }

        if (this.record != null) {
            changedObject = "record";
        }

        if (this.keyValueId != null && this.keyValueId > 0) {
            changedObject = "keyvalue";
        }

        return changedObject;
    }
}
