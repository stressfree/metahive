package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * The Enum KeyValueGenerator.
 */
public enum KeyValueGenerator {
			
	NOT_APPLICABLE("label_com_sfs_metahive_model_keyvaluegenerator_not_applicable"),
    NEWEST("label_com_sfs_metahive_model_keyvaluegenerator_newest"),
    OLDEST("label_com_sfs_metahive_model_keyvaluegenerator_oldest"),
    FREQUENT_DEFAULT_NEW("label_com_sfs_metahive_model_keyvaluegenerator_frequent_new"),
    FREQUENT_DEFAULT_OLD("label_com_sfs_metahive_model_keyvaluegenerator_frequent_old"),
    UNCLEAR("label_com_sfs_metahive_model_keyvaluegenerator_unclear"),
    AVERAGE("label_com_sfs_metahive_model_keyvaluegenerator_average"),
    HIGHEST("label_com_sfs_metahive_model_keyvaluegenerator_highest"),
    LOWEST("label_com_sfs_metahive_model_keyvaluegenerator_lowest"),
    MEDIAN("label_com_sfs_metahive_model_keyvaluegenerator_median"),
    QUARTILE_LOWER("label_com_sfs_metahive_model_keyvaluegenerator_quartile_lower"),
    QUARTILE_UPPER("label_com_sfs_metahive_model_keyvaluegenerator_quartile_upper");
	
    /** The message key. */
    private String messageKey; 
     
    /**
     * Instantiates a new key value generator.
     *
     * @param name the name
     */
    private KeyValueGenerator(String name) { 
        this.messageKey = name; 
    } 

    /**
     * Gets the message key.
     *
     * @return the message key
     */
    public String getMessageKey() { 
        return messageKey; 
    }
    
    
    /**
     * Calculate the key value for the supplied definition and list of values.
     *
     * @param def the def
     * @param values the values
     * @return the object
     */
    public static Object calculate(final Definition def, 
    		final List<String> values) {
    	
    	if (def == null) {
    		throw new IllegalArgumentException("A valid definition is required");
    	}
    	
    	Object keyValue = null;
    	
    	List<Object> parsedValues = parseValues(def.getDataType(), values);
    	    	
    	if (def != null && def.getKeyValueGenerator() != null) {
    		if (def.getKeyValueGenerator() == KeyValueGenerator.NEWEST) {
    			keyValue = calculateNewest(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.OLDEST) {
    			keyValue = calculateOldest(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.FREQUENT_DEFAULT_NEW) {
    			keyValue = frequentDefaultNewest(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.FREQUENT_DEFAULT_OLD) {
    			keyValue = frequentDefaultOldest(parsedValues);
    		}
    	}
    	return keyValue;
    }
    
    /**
     * Calculate the key value based on the newest value.
     *
     * @param values the values
     * @return the object
     */
    private static Object calculateNewest(final List<Object> values) {
    	Object keyValue = null;
    	    	
    	if (values != null && values.size() > 0) {
    		keyValue = values.get(values.size() - 1);
    	}
    	return keyValue;
    }
    
    /**
     * Calculate the key value based on the oldest value.
     *
     * @param values the values
     * @return the object
     */
    private static Object calculateOldest(final List<Object> values) {
    	Object keyValue = null;
    	
    	if (values != null && values.size() > 0) {
    		keyValue = values.get(0);
    	}
    	return keyValue;
    }

    /**
     * Calculate the key value based on the most frequent (default to newest if none).
     *
     * @param values the values
     * @return the object
     */
    private static Object frequentDefaultNewest(final List<Object> values) {
    	Object keyValue = frequent(values);
    	
    	if (keyValue == null) {
    		keyValue = calculateNewest(values);
    	} else {
    		if (StringUtils.isBlank((String) keyValue)) {
    			keyValue = calculateNewest(values);
    		}
    	}
    	return keyValue;
    }
    
    /**
     * Calculate the key value based on the most frequent (default to oldest if none).
     *
     * @param values the values
     * @return the object
     */
    private static Object frequentDefaultOldest(final List<Object> values) {
    	Object keyValue = frequent(values);
    	
    	if (keyValue == null) {
    		keyValue = calculateOldest(values);
    	} else {
    		if (StringUtils.isBlank((String) keyValue)) {
    			keyValue = calculateOldest(values);
    		}
    	}
    	return keyValue;
    }
    
    /**
     * Calculate the most frequent key value.
     *
     * @param values the values
     * @return the object
     */
    private static Object frequent(final List<Object> values) {
    	Object keyValue = null;
    	  	
    	Map<String, Integer> hitCount = new HashMap<String, Integer>();
    	Map<String, Object> originalCap = new HashMap<String, Object>();
    	int maxHitCount = 0;
    	
    	if (values != null && values.size() > 0) {
	    	for (Object objValue : values) {	    		
	    		String value = parseToString(objValue);	    		
	    		int count = 0;
	    		if (!hitCount.containsKey(value.toUpperCase())) {
	    			originalCap.put(value.toUpperCase(), objValue);
	    		} else {
	    			count = hitCount.get(value.toUpperCase());
	    		}
	    		count++;
	    		
	    		if (count > maxHitCount) {
	    			maxHitCount = count;
	    		}    		
				hitCount.put(value.toUpperCase(), count);
	    	}
    	}
    	
    	boolean keyValueSet = false;
    	
    	for (String valueKey : hitCount.keySet()) {
    		int count = hitCount.get(valueKey);
    		
    		if (count == maxHitCount) {
    			if (!keyValueSet) {
    				keyValue = originalCap.get(valueKey);
    			} else {
    				// Invalidate the keyValue because there is a duplicate most frequent
    				keyValue = null;
    			}
    		}
    	}  	
    	return keyValue;
    }
    
    /**
     * Parses the values to a list of Objects.
     *
     * @param dataType the data type
     * @param values the values
     * @return the list
     */
    private static List<Object> parseValues(final DataType dataType,
    		final List<String> values) {
    	
    	if (dataType == null) {
    		throw new IllegalArgumentException("A valid data type is required");
    	}
    	
    	List<Object> parsedValues = new ArrayList<Object>();
    	
    	if (values != null) {
    		if (dataType == DataType.TYPE_NUMBER 
    				|| dataType == DataType.TYPE_PERCENTAGE) {
    			// Cast to doubles
    			for (String value : values) {
    				double dblValue = 0;
    				try {
    					dblValue = Double.parseDouble(value);
    	    			parsedValues.add(dblValue);
    				} catch (NumberFormatException nfe) {
    					// Do not add this value to the parsed value map
    				}
    			}
    		}
    		if (dataType == DataType.TYPE_BOOLEAN) {
    			for (String value : values) {
    				parsedValues.add(parseToBoolean(value));
    			}
    		}
    		if (dataType == DataType.TYPE_STRING || dataType == DataType.TYPE_UNIQUEID) {
    			for (String value : values) {
    				parsedValues.add(value);
    			}
    		}
    	}
    	return parsedValues;
    }
    
    /**
     * Parses the object value to a string.
     *
     * @param objValue the value as an object
     * @return the string
     */
    private static String parseToString(final Object objValue) {
    	String value = "";
    	
    	if (objValue != null) {
    		if (objValue instanceof String) {
    			value = (String) objValue;
    		}
    		if (objValue instanceof Double) {
    			value = String.valueOf((Double) objValue);
    		}
    	}
    	return value;
    }
    
    /**
     * Parses the boolean.
     *
     * @param booleanValue the boolean value
     * @return the string
     */
    private static String parseToBoolean(final String blValue) {
    	
    	String value = String.valueOf(KeyValueBoolean.BL_UNCLEAR);
    	
    	if (StringUtils.equalsIgnoreCase(blValue, "true")
    			|| StringUtils.equalsIgnoreCase(blValue, "yes")
    			|| StringUtils.equals(blValue, "1")) {
    		value = String.valueOf(KeyValueBoolean.BL_TRUE);
    	}
    	if (StringUtils.equalsIgnoreCase(blValue, "false")
    			|| StringUtils.equalsIgnoreCase(blValue, "no")
    			|| StringUtils.equals(blValue, "0")) {
    		value = String.valueOf(KeyValueBoolean.BL_FALSE);
    	}
    	return value;
    }
    
}
