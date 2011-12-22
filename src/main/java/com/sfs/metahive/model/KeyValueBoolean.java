package com.sfs.metahive.model;

/**
 * The Enum KeyValueBoolean.
 */
public enum KeyValueBoolean {

	BL_TRUE("label_com_sfs_metahive_model_keyvalueboolean_true"),
    BL_FALSE("label_com_sfs_metahive_model_keyvalueboolean_false"),
    BL_UNCLEAR("label_com_sfs_metahive_model_keyvalueboolean_unclear");
	
	/** The message key. */
	private String messageKey; 
    
    /**
     * Instantiates a new key value boolean.
     *
     * @param name the name
     */
    private KeyValueBoolean(String name) { 
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
}
