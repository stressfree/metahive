package com.sfs.metahive.model;

/**
 * The Enum UserRole.
 */
public enum UserRole {

    ROLE_ADMIN("label_com_sfs_metahive_model_userrole_admin"),
    ROLE_EDITOR("label_com_sfs_metahive_model_userrole_editor"),
    ROLE_USER("label_com_sfs_metahive_model_userrole_user");
    
    private String messageKey; 
     
    private UserRole(String name) { 
        this.messageKey = name; 
    } 

    public String getMessageKey() { 
        return messageKey; 
    }
}
