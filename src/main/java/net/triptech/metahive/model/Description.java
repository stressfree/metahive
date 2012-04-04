/*
 *
 */
package net.triptech.metahive.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Description.
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Description {

    /** The related definition. */
    @NotNull
    @ManyToOne
    private Definition definition;

    /** The unit of measure. */
    private String unitOfMeasure;

    /** The description. */
    @Lob
    private String description;

    /** The example values. */
    private String exampleValues;

    /** The person who created the description. */
    @NotNull
    @ManyToOne
    private Person person;

    /** The created timestamp. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    /**
     * Gets the simple description.
     *
     * @return the simple description
     */
    public final String getSimpleDescription() {
        String simpleDescription = "";
        if (StringUtils.isNotBlank(getDescription())) {
            simpleDescription = Jsoup.parse(getDescription()).text();
        }
        if (simpleDescription.length() > 200) {
            simpleDescription = simpleDescription.substring(0, 198).trim() + "...";
        }
        return simpleDescription;
    }

}
