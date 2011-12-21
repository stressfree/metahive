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
	 * Find key value by primary id (secondary and tertiary ids are blank).
	 *
	 * @param def the definition
	 * @param primaryId the primary record id
	 * @return the key value
	 */
	public static KeyValue findKeyValueByPrimaryId(final Definition def,
			final String primaryId) {
		
		KeyValue keyValue = null;
		
		if (def == null) {
			throw new IllegalArgumentException("A valid defintion is required");
		}
        if (StringUtils.isBlank(primaryId)) {
        	throw new IllegalArgumentException("The primaryId argument is required");
        }
        
        TypedQuery<KeyValue> q = entityManager().createQuery(
        		"SELECT k FROM KeyValue AS k JOIN k.definition d"
        		+ " WHERE d.id = :definitionId AND"
        	    + " LOWER(k.primaryRecordId) = LOWER(:primaryRecordId)"
        		+ " AND (k.secondaryRecordId IS NULL OR k.secondaryRecordId = '')"
        	    + " AND (k.tertiaryRecordId IS NULL OR k.tertiaryRecordId = '')", 
        		KeyValue.class);
        q.setParameter("definitionId", def.getId());
        q.setParameter("primaryRecordId", primaryId);
        
        List<KeyValue> keyValues = q.getResultList();
        
        if (keyValues != null && keyValues.size() > 0) {
        	keyValue = keyValues.get(0);
        }        
        return keyValue;
    }
	
	/**
	 * Find key value by secondary id.
	 *
	 * @param def the definition
	 * @param primaryId the primary record id
	 * @param secondaryId the secondary record id
	 * @return the key value
	 */
	public static KeyValue findKeyValueBySecondaryId(final Definition def,
			final String primaryId, final String secondaryId) {
		
		KeyValue keyValue = null;
		
		if (def == null) {
			throw new IllegalArgumentException("A valid defintion is required");
		}
        if (StringUtils.isBlank(primaryId)) {
        	throw new IllegalArgumentException("The primaryId argument is required");
        }
        if (StringUtils.isBlank(secondaryId)) {
        	throw new IllegalArgumentException("The secondaryId argument is required");
        }
        
        TypedQuery<KeyValue> q = entityManager().createQuery(
        		"SELECT k FROM KeyValue AS k JOIN k.definition d"
        		+ " WHERE d.id = :definitionId AND"
        	    + " LOWER(k.primaryRecordId) = LOWER(:primaryRecordId)"
        		+ " AND LOWER(k.secondaryRecordId) = LOWER(:secondaryRecordId)", 
        		KeyValue.class);
        q.setParameter("definitionId", def.getId());
        q.setParameter("primaryRecordId", primaryId);
        q.setParameter("secondaryRecordId", primaryId);
        
        List<KeyValue> keyValues = q.getResultList();
        
        if (keyValues != null && keyValues.size() > 0) {
        	keyValue = keyValues.get(0);
        }        
        return keyValue;
    }
	
	/**
	 * Find key value by tertiary id.
	 *
	 * @param def the definition
	 * @param primaryId the primary record id
	 * @param tertiaryId the tertiary record id
	 * @return the key value
	 */
	public static KeyValue findKeyValueByTertiaryId(final Definition def,
			final String primaryId, final String tertiaryId) {
		
		KeyValue keyValue = null;
		
		if (def == null) {
			throw new IllegalArgumentException("A valid defintion is required");
		}
        if (StringUtils.isBlank(primaryId)) {
        	throw new IllegalArgumentException("The primaryId argument is required");
        }
        if (StringUtils.isBlank(tertiaryId)) {
        	throw new IllegalArgumentException("The tertiaryId argument is required");
        }
        
        TypedQuery<KeyValue> q = entityManager().createQuery(
        		"SELECT k FROM KeyValue AS k JOIN k.definition d"
        		+ " WHERE d.id = :definitionId AND"
        	    + " LOWER(k.primaryRecordId) = LOWER(:primaryRecordId)"
        		+ " AND LOWER(k.tertiaryRecordId) = LOWER(:tertiaryRecordId)", 
        		KeyValue.class);
        q.setParameter("definitionId", def.getId());
        q.setParameter("primaryRecordId", primaryId);
        q.setParameter("tertiaryRecordId", primaryId);
        
        List<KeyValue> keyValues = q.getResultList();
        
        if (keyValues != null && keyValues.size() > 0) {
        	keyValue = keyValues.get(0);
        }        
        return keyValue;
    }

    /**
     * Calculate the key value.
     *
     * @param def the def
     * @param primaryId the primary id
     * @param secondaryId the secondary id
     * @param tertiaryId the tertiary id
     */
    public static void calculate(final Definition def, final String primaryId,
    		final String secondaryId, final String tertiaryId) {
    	
    	if (def == null) {
    		throw new IllegalArgumentException("A valid definition is required");
    	}
    	if (StringUtils.isBlank(primaryId)) {
    		throw new IllegalArgumentException("A valid primaryId is required");
    	}    	
    	// Check that the record exists
    	Record record = Record.findRecordByRecordIdEquals(primaryId);
    	if (record == null) {
    		throw new IllegalArgumentException("A valid primaryId is required,"
    				+ " no record exists in the Metahive");
    	}
    	
    	KeyValue kv = prepareKeyValue(def, primaryId, secondaryId, tertiaryId);
    	    	
    	// If the key value is overridden then there's no need to recalculate it
    	if (kv.getKeyValueType() != KeyValueType.OVERRIDDEN) {
    		// Load all of the contributed values for this definition/record combination
    		
    	}
    	
    }
    
    /**
     * Get the existing key value, or prepare a new key value for calculating.
     *
     * @param def the def
     * @param primaryId the primary id
     * @param secondaryId the secondary id
     * @param tertiaryId the tertiary id
     * @return the key value
     */
    private static KeyValue prepareKeyValue(final Definition def, final String primaryId,
    		final String secondaryId, final String tertiaryId) {
    	
    	KeyValue kv = null;
    	
    	if (def.getApplicability() == Applicability.RECORD_PRIMARY) {
    		kv = KeyValue.findKeyValueByPrimaryId(def, primaryId);
    	}
    	if (def.getApplicability() == Applicability.RECORD_SECONDARY) {
    		if (StringUtils.isNotBlank(secondaryId)) {
    			kv = KeyValue.findKeyValueBySecondaryId(def, primaryId, secondaryId);
    		} else {
    			kv = KeyValue.findKeyValueByPrimaryId(def, primaryId);
    		}
    	}
    	if (def.getApplicability() == Applicability.RECORD_TERTIARY) {
    		if (StringUtils.isNotBlank(tertiaryId)) {
    			kv = KeyValue.findKeyValueByTertiaryId(def, primaryId, tertiaryId);
    		} else {
    			kv = KeyValue.findKeyValueByPrimaryId(def, primaryId);
    		}
    	}
    	
    	// If the key value is still null then this is a new record
    	if (kv == null) {
    		kv = new KeyValue();
    		kv.setDefinition(def);
    		kv.setKeyValueType(KeyValueType.CALCULATED);
    		kv.setPrimaryRecordId(primaryId);
    		
    		if (def.getApplicability() == Applicability.RECORD_SECONDARY
    				&& StringUtils.isNotBlank(secondaryId)) {
    			kv.setSecondaryRecordId(secondaryId);
    		}
    		if (def.getApplicability() == Applicability.RECORD_TERTIARY
    				&& StringUtils.isNotBlank(tertiaryId)) {
    			kv.setTertiaryRecordId(tertiaryId);
    		}    		
    	}
    	
    	return kv;
    }
}
