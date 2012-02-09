package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.Collections;
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
    		if (keyValue instanceof String && StringUtils.isBlank((String) keyValue)) {
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
    		if (keyValue instanceof String && StringUtils.isBlank((String) keyValue)) {
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
     * Calculate the median value from the list of supplied values.
     * This assumes that the list of values are Double or KeyValueBoolean objects.
     * If no Double or KeyValueBoolean objects exist then null is returned.
     *
     * @param values the values
     * @return the object
     */
    public static Object median(final List<Object> values) {
    	Object keyValue = null;
    	
    	if (values.size() > 0) {
    		if (values.get(0) instanceof Double) {
    			ArrayList<Double> sortedList = parseToSortedDoubleList(values);
    			if (sortedList.size() > 0) {    				
    				keyValue = getMedian(sortedList);			
    			}
    		}
    		if (values.get(0) instanceof KeyValueBoolean) {
    			// Parse the list of doubles to a list of:
    			// twos (true), ones (unclear), and zeros (false)
    			List<Object> doubleValues = new ArrayList<Object>();
    			
    			for (Object object : values) {
    				if (object instanceof KeyValueBoolean) {
    					doubleValues.add(parseBooleanToDouble(object));
    				}
    			}
    			
    			ArrayList<Double> sortedList = parseToSortedDoubleList(doubleValues);
    			if (sortedList.size() > 0) {
    				keyValue = parseDoubleToBoolean(getMedian(sortedList));
    			}
    		}    		
    	}
    	return keyValue;
    }
    
    /**
     * Calculate the lower quartile value from the list of supplied values.
     * This assumes that the list of values are Double or KeyValueBoolean objects.
     * If no Double or KeyValueBoolean objects exist then null is returned.
     *
     * @param values the values
     * @return the object
     */
    public static Object quartileLower(final List<Object> values) {

    	Object keyValue = null;
    	
    	if (values.size() > 0) {
    		if (values.get(0) instanceof Double) {
    			ArrayList<Double> sortedList = parseToSortedDoubleList(values);
    			if (sortedList.size() > 0) {    				
    				keyValue = getQuartileLower(sortedList);			
    			}
    		}
    		if (values.get(0) instanceof KeyValueBoolean) {
    			// Parse the list of doubles to a list of:
    			// twos (true), ones (unclear), and zeros (false)
    			List<Object> doubleValues = new ArrayList<Object>();
    			
    			for (Object object : values) {
    				if (object instanceof KeyValueBoolean) {
    					doubleValues.add(parseBooleanToDouble(object));
    				}
    			}
    			
    			ArrayList<Double> sortedList = parseToSortedDoubleList(doubleValues);
    			if (sortedList.size() > 0) {
    				keyValue = parseDoubleToBoolean(getQuartileLower(sortedList));
    			}
    		}    		
    	}    	
    	return keyValue;
    }
    
    /**
     * Calculate the upper quartile value from the list of supplied values.
     * This assumes that the list of values are Double or KeyValueBoolean objects.
     * If no Double or KeyValueBoolean objects exist then null is returned.
     *
     * @param values the values
     * @return the object
     */
    public static Object quartileUpper(final List<Object> values) {
    	
    	Object keyValue = null;
    	
    	if (values.size() > 0) {
    		if (values.get(0) instanceof Double) {
    			ArrayList<Double> sortedList = parseToSortedDoubleList(values);
    			if (sortedList.size() > 0) {    				
    				keyValue = getQuartileUpper(sortedList);			
    			}
    		}
    		if (values.get(0) instanceof KeyValueBoolean) {
    			// Parse the list of doubles to a list of:
    			// twos (true), ones (unclear), and zeros (false)
    			List<Object> doubleValues = new ArrayList<Object>();
    			
    			for (Object object : values) {
    				if (object instanceof KeyValueBoolean) {
    					doubleValues.add(parseBooleanToDouble(object));
    				}
    			}
    			
    			ArrayList<Double> sortedList = parseToSortedDoubleList(doubleValues);
    			if (sortedList.size() > 0) {
    				keyValue = parseDoubleToBoolean(getQuartileUpper(sortedList));
    			}
    		}    		
    	}    	
    	return keyValue;
    }
    
    /**
     * Calculate the average value from the list of supplied values.
     * This assumes that the list of values are Double objects.
     * If no Double objects exist in the values list then null is returned.
     *
     * @param values the values
     * @return the object
     */
    public static Object average(final List<Object> values) {
    	Double keyValue = null;
    	
    	double runningTotal = 0;
    	int count = 0;
    	
    	for (Object value : values) {
    		if (value instanceof Double) {
    			runningTotal += (Double) value;
    			count++;
    		}
    	}
    	
    	if (count > 0) {
    		// At least one valid Double value existed
    		keyValue = runningTotal / count;
    	}    	
    	return keyValue;
    }
    
    /**
     * Calculate the highest value from the list of supplied values.
     * This assumes that the list of values are Double objects.
     * If no Double objects exist in the values list then null is returned.     * 
     *
     * @param values the values
     * @return the object
     */
    public static Object highest(final List<Object> values) {
    	Double keyValue = null;
    	
    	ArrayList<Double> sortedList = parseToSortedDoubleList(values);
    	
    	if (sortedList.size() > 0) {
    		keyValue = sortedList.get(sortedList.size() - 1);
    	}
    	return keyValue;
    }

    /**
     * Calculate the lowest value from the list of supplied values.
     * This assumes that the list of values are Double objects.
     * If no Double objects exist in the values list then null is returned.     * 
     *
     * @param values the values
     * @return the object
     */
    public static Object lowest(final List<Object> values) {
    	Double keyValue = null;
    	
    	ArrayList<Double> sortedList = parseToSortedDoubleList(values);
    	
    	if (sortedList.size() > 0) {
    		keyValue = sortedList.get(0);
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
    
    /**
     * Parse the value list into a sorted double list.
     *
     * @param values the values
     * @return the array list
     */
    private static ArrayList<Double> parseToSortedDoubleList(final List<Object> values) {
    	ArrayList<Double> list = new ArrayList<Double>();
    	
    	for (Object value : values) {
    		if (value instanceof Double) {
    			list.add((Double) value);
    		}
    	}
    	Collections.sort(list);
    	
    	return list;
    }
    
    /**
     * Gets the median value from the sorted list of doubles.
     *
     * @param sortedList the sorted list
     * @return the median
     */
    private static double getMedian(final List<Double> sortedList) {
    	
    	double median = 0;
    	
    	if (sortedList.size() % 2 == 1) {
			median = sortedList.get((sortedList.size() + 1) / 2 - 1);
		} else {
			double lower = sortedList.get(sortedList.size() / 2 - 1);
			double upper = sortedList.get(sortedList.size() / 2);
		 
			median = (lower + upper) / 2.0;
		}
    	return median;    	
    }
    
    /**
     * Gets the lower quartile value.
     *
     * @param sortedList the sorted list
     * @return the quartile lower
     */
    private static double getQuartileLower(final List<Double> sortedList) {

    	double quartileLower = 0;
    	
    	if (sortedList.size() > 3) {
    		double median = getMedian(sortedList);    		
    		quartileLower = getMedian(getValuesLessThan(sortedList, median));
    	} else {
    		// If less than three values return the first (lowest) value
    		quartileLower = sortedList.get(0);
    	}    	
    	return quartileLower;
    }
    
    /**
     * Gets the upper quartile value.
     *
     * @param sortedList the sorted list
     * @return the quartile upper
     */
    private static double getQuartileUpper(final List<Double> sortedList) {
    	
    	double quartileUpper = 0;
    	
    	if (sortedList.size() > 3) {
    		double median = getMedian(sortedList);    		
    		quartileUpper = getMedian(getValuesGreaterThan(sortedList, median));
    	} else {
    		// If less than three values return the last (highest) value
    		quartileUpper = sortedList.get(sortedList.size() - 1);
    	}    	
    	return quartileUpper;
    }
    
    /**
     * Gets the values greater than the supplied limit.
     *
     * @param values the values
     * @param limit the limit
     * @return the values greater than the supplied limit
     */
    private static List<Double> getValuesGreaterThan(final List<Double> values,
    		final double limit) {
    	
        List<Double> modValues = new ArrayList<Double>();
     
        for (double value : values) {
            if (value > limit || (value == limit)) {
                modValues.add(value);
            }
        }
        return modValues;
    }
     
    /**
     * Gets the values less than the supplied limit.
     *
     * @param values the values
     * @param limit the limit
     * @return the values less than the supplied limit
     */
    public static List<Double> getValuesLessThan(final List<Double> values, 
    		final double limit) {
    	
        List<Double> modValues = new ArrayList<Double>();
     
        for (double value : values) {
            if (value < limit || (value == limit)) {
                modValues.add(value);
            }
        }     
        return modValues;
    }
    
    /**
     * Parses the KeyValueBoolean to a double.
     *
     * @param bl the bl
     * @return the double
     */
    private static double parseBooleanToDouble(final Object object) {
    	
    	double value = 1;
    	
    	if (object instanceof KeyValueBoolean) {
    		KeyValueBoolean bl = (KeyValueBoolean) object;

    		if (bl == KeyValueBoolean.BL_TRUE) {
    			value = 2;
    		}
    		if (bl == KeyValueBoolean.BL_UNCLEAR) {
    			value = 1;
    		}
    		if (bl == KeyValueBoolean.BL_FALSE) {
    			value = 0;
    		}
    	}
		return value;
    }
    
    /**
     * Parses the double to a KeyValueBoolean.
     *
     * @param value the value
     * @return the key value boolean
     */
    private static KeyValueBoolean parseDoubleToBoolean(final Double value) {
    	
    	KeyValueBoolean bl = KeyValueBoolean.BL_UNCLEAR;
    	
    	if (value == 2) {
    		bl = KeyValueBoolean.BL_TRUE;
		}
		if (value == 1) {
			bl = KeyValueBoolean.BL_UNCLEAR;
		}
		if (value == 0) {
			bl = KeyValueBoolean.BL_FALSE;    					
		}
    	return bl;
    }
}
