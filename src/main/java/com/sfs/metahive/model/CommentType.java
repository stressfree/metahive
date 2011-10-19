package com.sfs.metahive.model;

/**
 * The Enum CommentType.
 */
public enum CommentType {

    CREATE("label_com_sfs_metahive_model_commenttype_create"),
    MODIFY("label_com_sfs_metahive_model_commenttype_modify"),
    DELETE("label_com_sfs_metahive_model_commenttype_delete"),
    GENERAL("label_com_sfs_metahive_model_commenttype_general");
    
    private String messageKey; 
     
    private CommentType(String name) { 
        this.messageKey = name; 
    } 

    public String getMessageKey() { 
        return messageKey; 
    }
}
