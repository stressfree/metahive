// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Applicability;
import com.sfs.metahive.model.Category;
import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.DataSource;
import com.sfs.metahive.model.DataType;
import com.sfs.metahive.model.Description;
import com.sfs.metahive.model.KeyValueGenerator;
import com.sfs.metahive.model.UserRole;
import java.lang.String;
import java.util.List;

privileged aspect Definition_Roo_JavaBean {
    
    public String Definition.getName() {
        return this.name;
    }
    
    public void Definition.setName(String name) {
        this.name = name;
    }
    
    public DataType Definition.getDataType() {
        return this.dataType;
    }
    
    public void Definition.setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    
    public KeyValueGenerator Definition.getKeyValueGenerator() {
        return this.keyValueGenerator;
    }
    
    public void Definition.setKeyValueGenerator(KeyValueGenerator keyValueGenerator) {
        this.keyValueGenerator = keyValueGenerator;
    }
    
    public UserRole Definition.getKeyValueAccess() {
        return this.keyValueAccess;
    }
    
    public void Definition.setKeyValueAccess(UserRole keyValueAccess) {
        this.keyValueAccess = keyValueAccess;
    }
    
    public List<Description> Definition.getDescriptions() {
        return this.descriptions;
    }
    
    public void Definition.setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }
    
    public List<DataSource> Definition.getDataSources() {
        return this.dataSources;
    }
    
    public void Definition.setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }
    
    public Category Definition.getCategory() {
        return this.category;
    }
    
    public void Definition.setCategory(Category category) {
        this.category = category;
    }
    
    public Applicability Definition.getApplicability() {
        return this.applicability;
    }
    
    public void Definition.setApplicability(Applicability applicability) {
        this.applicability = applicability;
    }
    
    public List<Comment> Definition.getComments() {
        return this.comments;
    }
    
    public void Definition.setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
}
