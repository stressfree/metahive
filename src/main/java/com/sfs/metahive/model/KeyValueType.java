package com.sfs.metahive.model;

/**
 * The Enum KeyValueType.
 */
public enum KeyValueType {

    CALCULATED("label_com_sfs_metahive_model_keyvalue_caclulated"),
    OVERRIDDEN("label_com_sfs_metahive_model_keyvalue_overridden");
    
    private String messageKey; 
     
    private KeyValueType(String name) { 
        this.messageKey = name; 
    } 

    public String getMessageKey() { 
        return messageKey; 
    }
}
