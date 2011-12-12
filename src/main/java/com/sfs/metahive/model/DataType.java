package com.sfs.metahive.model;

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
}
