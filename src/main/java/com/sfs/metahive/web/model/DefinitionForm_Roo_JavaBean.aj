// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.web.model;

import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.DataType;
import java.lang.String;
import java.util.Set;

privileged aspect DefinitionForm_Roo_JavaBean {
    
    public long DefinitionForm.getId() {
        return this.id;
    }
    
    public void DefinitionForm.setId(long id) {
        this.id = id;
    }
    
    public long DefinitionForm.getVersion() {
        return this.version;
    }
    
    public void DefinitionForm.setVersion(long version) {
        this.version = version;
    }
    
    public String DefinitionForm.getName() {
        return this.name;
    }
    
    public void DefinitionForm.setName(String name) {
        this.name = name;
    }
    
    public DataType DefinitionForm.getDataType() {
        return this.dataType;
    }
    
    public void DefinitionForm.setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    
    public Set<Category> DefinitionForm.getCategories() {
        return this.categories;
    }
    
    public void DefinitionForm.setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    
    public String DefinitionForm.getDescription() {
        return this.description;
    }
    
    public void DefinitionForm.setDescription(String description) {
        this.description = description;
    }
    
    public String DefinitionForm.getKeyValueDetermination() {
        return this.keyValueDetermination;
    }
    
    public void DefinitionForm.setKeyValueDetermination(String keyValueDetermination) {
        this.keyValueDetermination = keyValueDetermination;
    }
    
    public String DefinitionForm.getExampleValues() {
        return this.exampleValues;
    }
    
    public void DefinitionForm.setExampleValues(String exampleValues) {
        this.exampleValues = exampleValues;
    }
    
    public String DefinitionForm.getLogMessage() {
        return this.logMessage;
    }
    
    public void DefinitionForm.setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
    
}