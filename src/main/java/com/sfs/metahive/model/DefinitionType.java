package com.sfs.metahive.model;

/**
 * The Enum DefinitionType.
 */
public enum DefinitionType {

    STANDARD("label_com_sfs_metahive_model_definitiontype_standard"),
    CALCULATED("label_com_sfs_metahive_model_definitiontype_calculated"),
    SUMMARY("label_com_sfs_metahive_model_definitiontype_summary");
    
    private String messageKey; 
     
    private DefinitionType(String name) { 
        this.messageKey = name; 
    } 

    public String getMessageKey() { 
        return messageKey; 
    }
}
