package com.sfs.metahive.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Index;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
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
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(KeyValue.class);
	
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
	
	/** The string value. */
	@Index(name="indexStringValue")
	private String stringValue;
	
	/** The double value. */
	@Index(name="indexDoubleValue")
	private Double doubleValue;
	
	/** The boolean value. */
	@Index(name="indexBooleanValue")
	@Enumerated(EnumType.STRING)
	private KeyValueBoolean booleanValue;
	
	/** The user role. */
	@Transient
	private UserRole userRole;
	
	/** The application context. */
	@Transient
	private ApplicationContext context;
	
	
	/**
	 * Gets the css class.
	 *
	 * @return the css class
	 */
	public String getCssClass() {
		String value = "keyValueNumber";
		
		if (this.getDefinition() != null && this.getDefinition().getDataType() != null) {
			value = this.getDefinition().getDataType().getCssClass();
		}
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value as an object
	 */
	public void setValue(final Object value) {
		
		// Reset the values
		this.doubleValue = null;
		this.stringValue = null;
		this.booleanValue = null;
		
		if (value != null && value instanceof String) {
			this.setStringValue((String) value);
		}
		if (value != null && value instanceof Double) {
			this.setDoubleValue((Double) value);
		}
		if (value != null && value instanceof KeyValueBoolean) {
			this.setBooleanValue((KeyValueBoolean) value);
		}
 	}	
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		
		if (this.getDefinition() == null) {
			throw new NullPointerException("A valid definition is required");
		}
		if (this.getContext() == null) {
			throw new NullPointerException("A valid application context is required");
		}
		
		// The default value is authorisation required
		String value = context.getMessage(
				"label_com_sfs_metahive_model_keyvalue_access_restricted", 
				null, LocaleContextHolder.getLocale());
		
		if (UserRole.allowAccess(this.getUserRole(), definition.getKeyValueAccess())) {
			value = context.getMessage("label_com_sfs_metahive_model_keyvalue_no_data", 
					null, LocaleContextHolder.getLocale());
			
			if (this.getDefinition().getDataType() == DataType.TYPE_STRING) {
				if (this.getStringValue() != null) {
					value = this.getStringValue() + appendUnitOfMeasure();
				}
			}
			if (this.getDefinition().getDataType() == DataType.TYPE_BOOLEAN) {
				if (this.getBooleanValue() != null && this.getContext() != null) {
					value = context.getMessage(this.getBooleanValue().getMessageKey(), 
							null, LocaleContextHolder.getLocale());
				}
			}
			if (this.getDefinition().getDataType() == DataType.TYPE_NUMBER) {
				if (this.getDoubleValue() != null) {
					value = String.valueOf(this.getDoubleValue());
					if (StringUtils.endsWithIgnoreCase(value, ".0")) {
						value = StringUtils.substring(value, 0, value.length() -2);
					}
					value += appendUnitOfMeasure();
				}
			}
			if (this.getDefinition().getDataType() == DataType.TYPE_CURRENCY) {
				if (this.getDoubleValue() != null) {
					DecimalFormat df = new java.text.DecimalFormat("$###,###,###,##0.00");
					value = df.format(this.getDoubleValue()) + appendUnitOfMeasure();
				}
			}			
			if (this.getDefinition().getDataType() == DataType.TYPE_PERCENTAGE) {
				if (this.getDoubleValue() != null) {					
					NumberFormat percentFormatter = NumberFormat.getPercentInstance(
							LocaleContextHolder.getLocale());
					value = percentFormatter.format(this.getDoubleValue()) +
							appendUnitOfMeasure();
				}
			}
		}	
		return value;
	}
	
	/**
	 * Find key values for the supplied Record.
	 *
	 * @param record the record
	 * @param definitions the list of definitions to lookup
	 * @return the key value
	 */
	public static List<KeyValue> findKeyValues(final Record record,
			final List<Definition> definitions) {
				
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		
		if (record == null) {
			throw new IllegalArgumentException("A valid record is required");
		}
        
		if (definitions != null && definitions.size() > 0) {
			
			StringBuilder sql = new StringBuilder();
			StringBuilder where = new StringBuilder();
			
			sql.append("SELECT k FROM KeyValue AS k JOIN k.record r");
			sql.append(" LEFT JOIN k.definition d WHERE r.id = :recordId");
				
			sql.append(" AND (");
			for (Definition definition : definitions) {
				if (where.length() > 0) {
					where.append(" OR ");
				}
				where.append("d.id = ");
				where.append(definition.getId());
			}
			sql.append(where.toString());
			sql.append(")");
		
			TypedQuery<KeyValue> q = entityManager().createQuery(
					sql.toString(), KeyValue.class);
                
			q.setParameter("recordId", record.getId());
                
			if (q.getResultList() != null) {
				for (KeyValue keyValue : q.getResultList()) {
					keyValues.add(keyValue);
				}
			}
		}
        return keyValues;
    }
	
	/**
	 * Find key value based on its id values.
	 *
	 * @param def the definition
	 * @param primaryId the primary record id
	 * @param secondaryId the secondary record id
	 * @param tertiaryId the tertiary record id
	 * @return the key value
	 */
	public static KeyValue findKeyValue(final Definition def,
			final String primaryId, final String secondaryId, final String tertiaryId) {
		
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
        		+ " AND LOWER(k.secondaryRecordId) = LOWER(:secondaryRecordId)"
        	    + " AND LOWER(k.tertiaryRecordId) = LOWER(:tertiaryRecordId)", 
        		KeyValue.class);
        q.setParameter("definitionId", def.getId());
        q.setParameter("primaryRecordId", primaryId);
        q.setParameter("secondaryRecordId", secondaryId);
        q.setParameter("tertiaryRecordId", tertiaryId);
        
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
    		List<String> values = SubmittedField.findSubmittedValues(def, 
    				kv.getPrimaryRecordId(), kv.getSecondaryRecordId(),
    				kv.getTertiaryRecordId());
    		
    		logger.info("Number of values: " + values.size());
    		logger.info("Key value id: " + kv.getId());
    		
    		if (values.size() == 0) {
    			// No submitted values exist - delete the key value if one exists
    			if (kv.getId() != null && kv.getId() > 0) {
    				try {
    					kv.remove();
    				} catch (Exception e) {
    					logger.error("Error deleting old key value: " + e.getMessage());
    				}
    			}    			
    		} else {    			
    			// Check to see if the definition is applicable to the key value
    			boolean applicable = true;
    			
    			if (def.getApplicability() == Applicability.RECORD_SECONDARY
    					&& StringUtils.isBlank(kv.getSecondaryRecordId())) {
    				// Not applicable as no secondary record id is defined
    				applicable = false;
    			}
    			
    			if (def.getApplicability() == Applicability.RECORD_SECONDARY
    					&& StringUtils.isBlank(kv.getTertiaryRecordId())) {
    				// Not applicable as no tertiary record id is defined
    				applicable = false;
    			}
    			
    			if (applicable) {
    				kv.setValue(KeyValueGenerator.calculate(def, values));
    				logger.info("Calculated string value: " + kv.getStringValue());
    				logger.info("Calculated double value: " + kv.getDoubleValue());
	    		
    				try {
    					if (kv.getId() == null) {
    						kv.persist();
    						kv.flush();
    					} else {
    						kv.merge();
    					}
    				} catch (Exception e) {
    					logger.error("Error saving key value: " + e.getMessage(), e);
    				}
    			} else {
    				logger.error("This key value " + kv.getPrimaryRecordId() + ":" 
    						+ kv.getSecondaryRecordId() + ":" + kv.getTertiaryRecordId() 
    						+ " is not applicable to this definition: "
    						+ def.getName());    						
    			}
    		}
    	}
    }
    
    /**
     * Get the existing key value, or prepare a new key value for calculating.
     *
     * @param def the def
     * @param primaryRecordId the primary record id
     * @param secondaryRecordId the secondary record id
     * @param tertiaryRecordId the tertiary record id
     * @return the key value
     */
    private static KeyValue prepareKeyValue(final Definition def, 
    		final String primaryRecordId, final String secondaryRecordId,
    		final String tertiaryRecordId) {
    	    	    	
    	MetahivePreferences preferences = MetahivePreferences.load();
    	
    	String primaryId = primaryRecordId;
    	String secondaryId = "";
    	String tertiaryId = "";
    	
    	if (StringUtils.isNotBlank(secondaryRecordId)) {
    		secondaryId = secondaryRecordId;
    	}
    	if (StringUtils.isNotBlank(tertiaryRecordId)) {
    		tertiaryId = tertiaryRecordId;
    	}
    	
    	if (StringUtils.isBlank(secondaryId) && StringUtils.isNotBlank(
    			preferences.getSecondaryRecordDefault())) {
    		secondaryId = preferences.getSecondaryRecordDefault();
    	}

    	if (StringUtils.isBlank(tertiaryId) && StringUtils.isNotBlank(
    			preferences.getTertiaryRecordDefault())) {
    		tertiaryId = preferences.getTertiaryRecordDefault();
    	}
    	    	
    	Record recd = Record.findRecordByRecordIdEquals(primaryId);
    	
    	if (recd == null) {
    		throw new IllegalArgumentException("A valid primaryRecordId is required");
    	}
    	
    	KeyValue kv = KeyValue.findKeyValue(def, primaryId, secondaryId, tertiaryId);
    	    	
    	// If the key value is still null then this is a new record
    	if (kv == null) {
    		kv = new KeyValue();
    		kv.setDefinition(def);
    		kv.setRecord(recd);
    		kv.setKeyValueType(KeyValueType.CALCULATED);
    		kv.setPrimaryRecordId(primaryId);
    		kv.setSecondaryRecordId(secondaryId);
    		kv.setTertiaryRecordId(tertiaryId);
    	}
    	return kv;
    }
    
    /**
     * Append unit of measure.
     *
     * @return the string
     */
    private String appendUnitOfMeasure() {
    	
    	String value = "";
    	
    	if (this.getDefinition() != null 
    			&& this.getDefinition().getDescription() != null) {

        	String unitOfMeasure = this.getDefinition().getDescription()
    				.getUnitOfMeasure();
    		
    		if (StringUtils.isNotBlank(unitOfMeasure)) {
    			value += " " + StringUtils.trim(unitOfMeasure);
    		}
    	}
    	return value;
    }
}
