package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Enum DataType.
 */
public enum DataType {

    TYPE_UNIQUEID("label_com_sfs_metahive_model_datatype_uniqueid"),
    TYPE_STRING("label_com_sfs_metahive_model_datatype_string"),
    TYPE_NUMBER("label_com_sfs_metahive_model_datatype_number"),
    TYPE_PERCENTAGE("label_com_sfs_metahive_model_datatype_percentage"),
    TYPE_BOOLEAN("label_com_sfs_metahive_model_datatype_boolean");
    
    private String messageKey; 
     
    private DataType(String name) { 
        this.messageKey = name; 
    } 

    public String getMessageKey() { 
        return messageKey; 
    }
    
    public Collection<KeyValueGenerator> getKeyValueGenerators() {
    	
    	Collection<KeyValueGenerator> keyValueGens = new ArrayList<KeyValueGenerator>();
    	
		keyValueGens.add(KeyValueGenerator.NEWEST);
		keyValueGens.add(KeyValueGenerator.OLDEST);
		keyValueGens.add(KeyValueGenerator.FREQUENT_DEFAULT_NEW);
		keyValueGens.add(KeyValueGenerator.FREQUENT_DEFAULT_OLD);
    	
    	if (this == DataType.TYPE_NUMBER || this == DataType.TYPE_PERCENTAGE) {
    		keyValueGens.add(KeyValueGenerator.AVERAGE);
    		keyValueGens.add(KeyValueGenerator.HIGHEST);
    		keyValueGens.add(KeyValueGenerator.LOWEST);
    	}
    	
    	if (this == DataType.TYPE_BOOLEAN) {
    		keyValueGens.add(KeyValueGenerator.UNCLEAR);
    	}
    	
    	if (this == DataType.TYPE_NUMBER || this == DataType.TYPE_PERCENTAGE
    			|| this == DataType.TYPE_BOOLEAN) {
    		keyValueGens.add(KeyValueGenerator.MEDIAN);
    		keyValueGens.add(KeyValueGenerator.QUARTILE_LOWER);
    		keyValueGens.add(KeyValueGenerator.QUARTILE_UPPER);
    	}
    	
    	if (this == DataType.TYPE_UNIQUEID) {
    		keyValueGens = new ArrayList<KeyValueGenerator>();
    		keyValueGens.add(KeyValueGenerator.NOT_APPLICABLE);
    	}  
    	return keyValueGens;
    }
}
