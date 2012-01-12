package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.List;

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
    			keyValue = KeyValueCalculator.newest(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.OLDEST) {
    			keyValue = KeyValueCalculator.oldest(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.FREQUENT_DEFAULT_NEW) {
    			keyValue = KeyValueCalculator.frequentDefaultNewest(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.FREQUENT_DEFAULT_OLD) {
    			keyValue = KeyValueCalculator.frequentDefaultOldest(parsedValues);
    		}
    		// This assumes a boolean set of values.
    		if (def.getKeyValueGenerator() == KeyValueGenerator.UNCLEAR) {
    			keyValue = KeyValueCalculator.unclear(parsedValues);
    		}
    		// This assumes a boolean or numeric set of values.
    		if (def.getKeyValueGenerator() == KeyValueGenerator.MEDIAN) {
    			keyValue = KeyValueCalculator.median(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.QUARTILE_LOWER) {
    			keyValue = KeyValueCalculator.quartileLower(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.QUARTILE_UPPER) {
    			keyValue = KeyValueCalculator.quartileUpper(parsedValues);
    		}
    	    // This assumes numeric (Double) set of values
    		if (def.getKeyValueGenerator() == KeyValueGenerator.AVERAGE) {
    			keyValue = KeyValueCalculator.average(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.HIGHEST) {
    			keyValue = KeyValueCalculator.highest(parsedValues);
    		}
    		if (def.getKeyValueGenerator() == KeyValueGenerator.LOWEST) {
    			keyValue = KeyValueCalculator.lowest(parsedValues);
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
     * Parses the boolean.
     *
     * @param booleanValue the boolean value
     * @return the string
     */
    private static KeyValueBoolean parseToBoolean(final String blValue) {
    	
    	KeyValueBoolean value = KeyValueBoolean.BL_UNCLEAR;
    	
    	if (StringUtils.equalsIgnoreCase(blValue, "true")
    			|| StringUtils.equalsIgnoreCase(blValue, "yes")
    			|| StringUtils.equals(blValue, "1")) {
    		value = KeyValueBoolean.BL_TRUE;
    	}
    	if (StringUtils.equalsIgnoreCase(blValue, "false")
    			|| StringUtils.equalsIgnoreCase(blValue, "no")
    			|| StringUtils.equals(blValue, "0")) {
    		value = KeyValueBoolean.BL_FALSE;
    	}
    	return value;
    }
    
}
