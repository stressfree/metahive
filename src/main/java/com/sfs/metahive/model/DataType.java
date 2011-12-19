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
    	
    	if (this == DataType.TYPE_UNIQUEID) {
    		keyValueGens.add(KeyValueGenerator.NOT_APPLICABLE);
    	}    	
    	if (this == DataType.TYPE_STRING) {
    		keyValueGens.add(KeyValueGenerator.NEWEST);
    		keyValueGens.add(KeyValueGenerator.OLDEST);
    		keyValueGens.add(KeyValueGenerator.FREQUENT_DEFAULT_NEW);
    		keyValueGens.add(KeyValueGenerator.FREQUENT_DEFAULT_NEW);
    	}    	
    	if (this == DataType.TYPE_NUMBER || this == DataType.TYPE_PERCENTAGE) {
    		keyValueGens.add(KeyValueGenerator.NEWEST);
    		keyValueGens.add(KeyValueGenerator.OLDEST);
    		keyValueGens.add(KeyValueGenerator.AVERAGE);
    		keyValueGens.add(KeyValueGenerator.HIGHEST);
    		keyValueGens.add(KeyValueGenerator.LOWEST);
    		keyValueGens.add(KeyValueGenerator.MEDIAN);
    		keyValueGens.add(KeyValueGenerator.QUARTILE_LOWER);
    		keyValueGens.add(KeyValueGenerator.QUARTILE_UPPER);
    	}
    	if (this == DataType.TYPE_BOOLEAN) {
    		keyValueGens.add(KeyValueGenerator.NEWEST);
    		keyValueGens.add(KeyValueGenerator.OLDEST);
    		keyValueGens.add(KeyValueGenerator.MEDIAN);
    		keyValueGens.add(KeyValueGenerator.UNCLEAR);
    	}
    	return keyValueGens;
    }
}
