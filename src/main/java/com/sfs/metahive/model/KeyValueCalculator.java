package com.sfs.metahive.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * The Class KeyValueCalculator.
 */
public class KeyValueCalculator {

    /**
     * Calculate the key value based on the newest value.
     *
     * @param values the values
     * @return the object
     */
    public static Object newest(final List<Object> values) {
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
    public static Object oldest(final List<Object> values) {
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
    public static Object frequentDefaultNewest(final List<Object> values) {
    	Object keyValue = frequent(values);
    	
    	if (keyValue == null) {
    		keyValue = newest(values);
    	} else {
    		if (StringUtils.isBlank((String) keyValue)) {
    			keyValue = newest(values);
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
    public static Object frequentDefaultOldest(final List<Object> values) {
    	Object keyValue = frequent(values);
    	
    	if (keyValue == null) {
    		keyValue = oldest(values);
    	} else {
    		if (StringUtils.isBlank((String) keyValue)) {
    			keyValue = oldest(values);
    		}
    	}
    	return keyValue;
    }
    
    
    /**
     * Calculate the key value based on what value is unanimous.
     * If there is any conflict then the result is unclear. 
     * This is only applicable to boolean type key values.
     *
     * @param values the values
     * @return the object
     */
    public static Object unclear(final List<Object> values) {
    	KeyValueBoolean keyValue = null;
    	boolean unclearKeyValue = false;
    	
    	for (Object value : values) {
    		if (value instanceof KeyValueBoolean) {    			
    			if (keyValue == null) {
    				keyValue = (KeyValueBoolean) value;
    			}
    			if (keyValue != (KeyValueBoolean) value) {
    				// Mismatch - default to unclear
    				unclearKeyValue = true;
    			}
    		}
    	}
    	
    	if (unclearKeyValue) {
    		// Reset key value to unclear
    		keyValue = KeyValueBoolean.BL_UNCLEAR;
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
}
