package com.sfs.metahive.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    @NotNull
    @ManyToOne
    private Definition definition;
    
    /** The description. */
    @OneToOne
    private Description description;
    
    /** The data source. */
    @OneToOne
    private DataSource dataSource;    
    
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
    
}
