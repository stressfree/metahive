package com.sfs.metahive.messaging;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.sfs.metahive.model.SubmittedField;


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
	public JmsRecalculateRequest(SubmittedField field) {
		super();
		this.primaryRecordId = field.getPrimaryRecordId();
		this.secondaryRecordId = field.getSecondaryRecordId();
		this.tertiaryRecordId = field.getTertiaryRecordId();
		this.definitionId = field.getDefinition().getId();
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