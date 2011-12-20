package com.sfs.metahive.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * The Class KeyValue.
 */
@RooJavaBean
@RooToString
@RooEntity
public class KeyValue {

	/** The key value type. */
	@NotNull
	@Enumerated(EnumType.STRING)
	private KeyValueType keyValueType;
	
	/** The record. */
	@ManyToOne
	@NotNull
	private Record record;
	
	/** The definition. */
	@ManyToOne
	@NotNull
	@Index(name="indexDefinition")
	private Definition definition;
	
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
	@Index(name="indexValue")
	private String value;
	
	
	/**
	 * Find key value by secondary id.
	 *
	 * @param definition the definition
	 * @param primaryRecordId the primary record id
	 * @param secondaryRecordId the secondary record id
	 * @return the key value
	 */
	public static KeyValue findKeyValueBySecondaryId(final Definition definition,
			final String primaryRecordId,
			final String secondaryRecordId) {
		
		KeyValue keyValue = null;
		
		if (definition == null) {
			throw new IllegalArgumentException("A valid defintion is required");
		}
        if (StringUtils.isBlank(primaryRecordId)) {
        	throw new IllegalArgumentException(
        			"The primaryRecordId argument is required");
        }
        if (StringUtils.isBlank(secondaryRecordId)) {
        	throw new IllegalArgumentException(
        			"The secondaryRecordId argument is required");
        }
        
        EntityManager em = Definition.entityManager();
        TypedQuery<KeyValue> q = em.createQuery(
        		"SELECT k FROM KeyValue AS k JOIN k.definition d"
        		+ " WHERE d.id = :definitionId AND"
        	    + " LOWER(k.primaryRecordId) = LOWER(:primaryRecordId)"
        		+ " AND LOWER(k.secondaryRecordId) = LOWER(:secondaryRecordId)", 
        		KeyValue.class);
        q.setParameter("definitionId", definition.getId());
        q.setParameter("primaryRecordId", primaryRecordId);
        q.setParameter("secondaryRecordId", primaryRecordId);
        
        List<KeyValue> keyValues = q.getResultList();
        
        if (keyValues != null && keyValues.size() > 0) {
        	keyValue = keyValues.get(0);
        }        
        return keyValue;
    }
	
	/**
	 * Find key value by tertiary id.
	 *
	 * @param definition the definition
	 * @param primaryRecordId the primary record id
	 * @param tertiaryRecordId the tertiary record id
	 * @return the key value
	 */
	public static KeyValue findKeyValueByTertiaryId(final Definition definition,
			final String primaryRecordId,
			final String tertiaryRecordId) {
		
		KeyValue keyValue = null;
		
		if (definition == null) {
			throw new IllegalArgumentException("A valid defintion is required");
		}
        if (StringUtils.isBlank(primaryRecordId)) {
        	throw new IllegalArgumentException(
        			"The primaryRecordId argument is required");
        }
        if (StringUtils.isBlank(tertiaryRecordId)) {
        	throw new IllegalArgumentException(
        			"The tertiaryRecordId argument is required");
        }
        
        EntityManager em = Definition.entityManager();
        TypedQuery<KeyValue> q = em.createQuery(
        		"SELECT k FROM KeyValue AS k JOIN k.definition d"
        		+ " WHERE d.id = :definitionId AND"
        	    + " LOWER(k.primaryRecordId) = LOWER(:primaryRecordId)"
        		+ " AND LOWER(k.tertiaryRecordId) = LOWER(:tertiaryRecordId)", 
        		KeyValue.class);
        q.setParameter("definitionId", definition.getId());
        q.setParameter("primaryRecordId", primaryRecordId);
        q.setParameter("tertiaryRecordId", primaryRecordId);
        
        List<KeyValue> keyValues = q.getResultList();
        
        if (keyValues != null && keyValues.size() > 0) {
        	keyValue = keyValues.get(0);
        }        
        return keyValue;
    }
}
