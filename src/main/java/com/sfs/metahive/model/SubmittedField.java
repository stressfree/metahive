package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Index;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;


/**
 * The Class SubmittedField.
 */
@RooJavaBean
@RooToString
@RooEntity
public class SubmittedField {
		
	/** The record. */
	@ManyToOne
	@NotNull
	private Record record;
	
	/** The definition. */
	@ManyToOne
	@NotNull
	@Index(name="indexDefinition")
	private Definition definition;
	
	/** The submission. */
	@ManyToOne
	@NotNull
	private Submission submission;
	
	/** The primary record id. */
	@NotNull
	@Index(name="indexPrimaryRecordId")
	private String primaryRecordId;
	
	/** The secondary record id. */
	@Index(name="indexSecondaryRecordId")
	private String secondaryRecordId;
	
	/** The tertiary record id. */
	@Index(name="indexTertiaryRecordId")
	private String tertiaryRecordId;
	
	/** The value. */
	@NotNull
	@Size(min = 1, max = 255)
	private String value;
	
	/**
	 * Find the submitted values for the supplied parameters.
	 *
	 * @param def the def
	 * @param primaryId the primary id
	 * @param secondaryId the secondary id
	 * @param tertiaryId the tertiary id
	 * @return the list
	 */
	public static List<String> findSubmittedValues(final Definition def,
			final String primaryId, final String secondaryId, final String tertiaryId) {
		
		List<String> values = new ArrayList<String>();
		
		if (def == null) {
			throw new IllegalArgumentException("A valid defintion is required");
		}
        if (StringUtils.isBlank(primaryId)) {
        	throw new IllegalArgumentException("The primaryId argument is required");
        }
        
        Map<String, Object> variables = new HashMap<String, Object>();
        
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT s FROM SubmittedField AS s JOIN s.definition d");
        sql.append(" WHERE d.id = :definitionId");
        variables.put("definitionId", def.getId());
        
        sql.append(" AND LOWER(s.primaryRecordId) = LOWER(:primaryRecordId)");
        variables.put("primaryRecordId", primaryId);
        
        if (StringUtils.isNotBlank(secondaryId)) {
        	sql.append(" AND LOWER(s.secondaryRecordId) = LOWER(:secondaryRecordId)");
            variables.put("secondaryRecordId", secondaryId);
        } else {
        	sql.append(" AND (s.secondaryRecordId IS NULL OR s.secondaryRecordId = '')");
        }
        if (StringUtils.isNotBlank(tertiaryId)) {
        	sql.append(" AND LOWER(s.tertiaryRecordId) = LOWER(:tertiaryRecordId)");
        	variables.put("tertiaryRecordId", tertiaryId);
        } else {
        	sql.append(" AND (s.tertiaryRecordId IS NULL OR s.tertiaryRecordId = '')");
        }
        
        TypedQuery<SubmittedField> q = entityManager().createQuery(sql.toString(), 
        		SubmittedField.class);
        for (String key : variables.keySet()) {
        	q.setParameter(key, variables.get(key));
        }        
        List<SubmittedField> results = q.getResultList();
        
        if (results != null) {
        	for (SubmittedField field : results) {
        		values.add(field.getValue());
        	}
        }    
        return values;
    }
	
	
}
