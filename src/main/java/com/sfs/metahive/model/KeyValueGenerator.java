package com.sfs.metahive.model;

/**
 * The Enum KeyValueGenerator.
 */
public enum KeyValueGenerator {
	
	NOT_APPLICABLE("label_com_sfs_metahive_model_keyvaluegenerator_not_applicable"),
    NEWEST("label_com_sfs_metahive_model_keyvaluegenerator_newest"),
    OLDEST("label_com_sfs_metahive_model_keyvaluegenerator_oldest"),
    FREQUENT_DEFAULT_NEW("label_com_sfs_metahive_model_keyvaluegenerator_frequent_new"),
    FREQUENT_DEFAULT_OLD("label_com_sfs_metahive_model_keyvaluegenerator_frequent_old"),
    UNCLEAR("label_com_sfs_metahive_model_keyvaluegenerator_unclear"),
    AVERAGE("label_com_sfs_metahive_model_keyvaluegenerator_average"),
    HIGHEST("label_com_sfs_metahive_model_keyvaluegenerator_highest"),
    LOWEST("label_com_sfs_metahive_model_keyvaluegenerator_lowest"),
    MEDIAN("label_com_sfs_metahive_model_keyvaluegenerator_median"),
    QUARTILE_LOWER("label_com_sfs_metahive_model_keyvaluegenerator_quartile_lower"),
    QUARTILE_UPPER("label_com_sfs_metahive_model_keyvaluegenerator_quartile_upper");
    
    private String messageKey; 
     
    private KeyValueGenerator(String name) { 
        this.messageKey = name; 
    } 

    public String getMessageKey() { 
        return messageKey; 
    }
    
}
