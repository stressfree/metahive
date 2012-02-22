package com.sfs.metahive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sfs.metahive.model.Applicability;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.DefinitionType;
import com.sfs.metahive.model.KeyValue;
import com.sfs.metahive.model.KeyValueBoolean;
import com.sfs.metahive.model.KeyValueGenerator;
import com.sfs.metahive.model.KeyValueType;
import com.sfs.metahive.model.MetahivePreferences;
import com.sfs.metahive.model.Record;
import com.sfs.metahive.model.SubmittedField;

public class KeyValueCalculator {

	/** The logger. */
	private static Logger logger = Logger.getLogger(KeyValueCalculator.class);
	
	/**
     * Calculate the key value.
     *
     * @param def the def
     * @param primaryId the primary id
     * @param secondaryId the secondary id
     * @param tertiaryId the tertiary id
     */
    public static void calculateKeyValue(final Definition def, final String primaryId,
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
    		if (def.getDefinitionType() == DefinitionType.STANDARD) {
    			calculateStandardKeyValue(def, kv);
    		}
    		if (def.getDefinitionType() == DefinitionType.SUMMARY) {
    			calculateSummarisedKeyValue(def, kv);
    		}
    		if (def.getDefinitionType() == DefinitionType.CALCULATED) {
    			calculateCalculatedKeyValue(def, kv);
    		}
    	}
    }
    
    
    /**
     * Calculate the key value for a standard definition.
     *
     * @param def the definition
     * @param kv the key value
     */
    private static void calculateStandardKeyValue(final Definition def, 
    		final KeyValue kv) {
    	
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
				Object result = KeyValueGenerator.calculateFromRawStrings(def, values);
	    		
	    		boolean refresh = checkChanged(kv, result);
				
				kv.setValue(result);
				logger.info("Calculated string value: " + kv.getStringValue());
				logger.info("Calculated double value: " + kv.getDoubleValue());
    		
				// Save the key value
				saveKeyValue(kv);
								
				if (refresh) {
					if (def.getSummaryDefinition() != null) {
						// Recalculate the associated summary definition
						
						Definition summaryDef = def.getSummaryDefinition();
						logger.info("Summary definition: " + summaryDef.getId());
						
						try {
							calculateKeyValue(summaryDef, 
									kv.getPrimaryRecordId(), 
									kv.getSecondaryRecordId(), 
									kv.getTertiaryRecordId());
						} catch (Exception e) {
							logger.error("Error calculating summarised definition ("
									+ def.getSummaryDefinition().getId() 
									+ ") for key value (" + kv.getPrimaryRecordId()
									+ kv.getSecondaryRecordId() + kv.getTertiaryRecordId()
									+ "): " + e.getMessage(), e);
						}
					}

					// Recalculate any associated calculated definitions
					recalculateCalculatedDefinitions(def, kv);
				}		
			} else {
				logger.error("This key value " + kv.getPrimaryRecordId() + ":" 
						+ kv.getSecondaryRecordId() + ":" + kv.getTertiaryRecordId() 
						+ " is not applicable to this definition: "
						+ def.getName());    						
			}
		}
    }    

    /**
     * Calculate the key value for a summary definition.
     *
     * @param def the definition
     * @param kv the key value
     */
    private static void calculateSummarisedKeyValue(final Definition def, 
    		final KeyValue kv) {
    	
    	// Load all of the contributed values for this definition/record combination
    	
    	List<Definition> definitions = Definition.findSummarisedDefinitions(def);
    	    	
    	List<Object> values = getSummarisedValues(definitions, kv);
    	
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
    		Object result = KeyValueGenerator.calculateFromObjects(def, values);
    		
    		boolean refresh = checkChanged(kv, result);
    		
			kv.setValue(result);
			logger.info("Calculated string value: " + kv.getStringValue());
			logger.info("Calculated double value: " + kv.getDoubleValue());
	    					
			// Save the key value
			saveKeyValue(kv);
			
			if (refresh) {
				// Recalculate any associated calculated definitions
				recalculateCalculatedDefinitions(def, kv);				
			}
    	}  
    }
    
    /**
     * Calculate the key value for a calculated definition.
     *
     * @param def the definition
     * @param kv the key value
     */
    private static void calculateCalculatedKeyValue(final Definition def, 
    		final KeyValue kv) {
    	
    	// Load all of the variable values for this definition/record combination
    	HashMap<Long, Double> values = getCalculatedValues(
    			def.getCalculatedDefinitions(), kv);    	
    			
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
    		double result = 0;    		
    		try {
    			result = CalculationParser.performCalculation(
    					def.getPlainTextCalculation(), values);
    		} catch (Exception e) {
    			logger.error("Error calculating value: " + e.getMessage());    			
    		}
    		
    		boolean refresh = checkChanged(kv, result);
    		
			kv.setValue(result);
			logger.info("Calculated string value: " + kv.getStringValue());
			logger.info("Calculated double value: " + kv.getDoubleValue());
	    	
			// Save the key value
			if (refresh) {
				// Recalculate any associated calculated definitions
				recalculateCalculatedDefinitions(def, kv);				
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
    		secondaryId = parseRecordId(secondaryRecordId);
    	}
    	if (StringUtils.isNotBlank(tertiaryRecordId)) {
    		tertiaryId = parseRecordId(tertiaryRecordId);
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
     * Save the key value.
     *
     * @param kv the kv
     */
    private static void saveKeyValue(final KeyValue kv) {    	
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
    }
    
    /**
     * Gets the summarised values as a list of objects.
     *
     * @param definitions the definitions
     * @param keyValue the key value
     * @return the summarised values
     */
    private static List<Object> getSummarisedValues(List<Definition> definitions,
    		final KeyValue keyValue) {
    	
    	List<Object> values = new ArrayList<Object>();
    	
    	for (Definition def : definitions) {
    		KeyValue kv = KeyValue.findKeyValue(def, keyValue.getPrimaryRecordId(), 
    				keyValue.getSecondaryRecordId(), keyValue.getTertiaryRecordId());
    		
    		if (kv != null) {
    			values.add(getKeyValueAsObject(def, kv));
    		}
    	}
    	return values;
    }
    
    /**
     * Gets the calculated values as a map of doubles.
     *
     * @param definitions the definitions
     * @param keyValue the key value
     * @return the calculated values
     */
    private static HashMap<Long, Double> getCalculatedValues(
    		final List<Definition> definitions, final KeyValue keyValue) {
    	
    	HashMap<Long, Double> values = new HashMap<Long, Double>();
    	
    	for (Definition def : definitions) {    		
    		double value = 0;
    		
    		KeyValue kv = KeyValue.findKeyValue(def, keyValue.getPrimaryRecordId(), 
    				keyValue.getSecondaryRecordId(), keyValue.getTertiaryRecordId());
    		
    		if (kv != null) {
    			Object kvValue = getKeyValueAsObject(def, kv);
    			
    			if (kvValue instanceof Double) {
    				value = (Double) kvValue;
    			}
    		}
    		values.put(def.getId(), value);
    	}    	
    	return values;    	
    }
    
    /**
     * Recalculate calculated definitions by identifying definitions that use the
     * supplied definition in their calculation and calling the calculate function.
     *
     * @param definition the definition
     * @param kv the key value
     */
    private static void recalculateCalculatedDefinitions(final Definition definition, 
    		final KeyValue kv) {
    	
    	if (definition != null && kv != null) {
    		List<Definition> defs = Definition.findDefinitionsWithVariable(definition);
    		
    		if (defs != null) {
    			for (Definition def : defs) {
    				try {
						calculateKeyValue(def, 
								kv.getPrimaryRecordId(), 
								kv.getSecondaryRecordId(), 
								kv.getTertiaryRecordId());
					} catch (Exception e) {
						logger.error("Error calculating calculated definition ("
								+ def.getSummaryDefinition().getId() + ") for key value ("
								+ kv.getPrimaryRecordId() + kv.getSecondaryRecordId() 
								+ kv.getTertiaryRecordId() + "): " + e.getMessage());
					}
    			}
    		}    	
    	}
    }
    
    /**
     * Gets the key value as an object.
     *
     * @param def the def
     * @param kv the kv
     * @return the key value as object
     */
    private static Object getKeyValueAsObject(final Definition def, final KeyValue kv) {
    	
    	Object value = null;
		
		if (def.getDataType() == DataType.TYPE_STRING) {
			value = kv.getStringValue();
		}
		if (def.getDataType() == DataType.TYPE_BOOLEAN) {
			value = kv.getBooleanValue();
		}
		if (def.getDataType() == DataType.TYPE_NUMBER
				|| def.getDataType() == DataType.TYPE_CURRENCY
				|| def.getDataType() == DataType.TYPE_PERCENTAGE) {
			value = kv.getDoubleValue();
		}
		
		return value;
    }
    
    /**
     * Check to see if the value has changed.
     *
     * @param kv the kv
     * @param newValue the new value
     * @return true, if successful
     */
    private static boolean checkChanged(final KeyValue kv, Object newValue) {
    	
    	boolean refresh = false;
		
		if (kv.getId() == null) {
			refresh = true;
		} else {
			if (newValue == null) {
				refresh = true;				
			} else {
				if (newValue instanceof String) {
					if (!StringUtils.equalsIgnoreCase((String) newValue, 
							kv.getStringValue())) {
						refresh = true;
					}
				}
				if (newValue instanceof Double) {
					if (kv.getDoubleValue() != null) {
						if ((Double) newValue != kv.getDoubleValue()) {
							refresh = true;
						}
					} else {
						refresh = true;
					}
				}
				if (newValue instanceof KeyValueBoolean) {
					if (kv.getBooleanValue() != null) {
						if ((KeyValueBoolean) newValue != kv.getBooleanValue()) {
							refresh = true;
						}
					} else {
						refresh = true;
					}
				}
			}
		}
		return refresh;
    }
    
    /**
     * Parses the record id. Tries casting to an integer first.
     *
     * @param recordId the record id
     * @return the string
     */
    private static String parseRecordId(final String recordId) {
    	
    	String record = "";
    	
    	if (StringUtils.isNotBlank(recordId)) {
    		record = recordId;
    		try {
    			int number = Integer.parseInt(recordId);
    			record = String.valueOf(number);		
    		} catch (NumberFormatException nfe) {
    			// Error casting to a number
    		}
    	}
    	return record;
    }
}
