// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Person;
import java.lang.String;
import java.util.Date;

privileged aspect Description_Roo_JavaBean {
    
    public Definition Description.getDefinition() {
        return this.definition;
    }
    
    public void Description.setDefinition(Definition definition) {
        this.definition = definition;
    }
    
    public String Description.getUnitOfMeasure() {
        return this.unitOfMeasure;
    }
    
    public void Description.setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
    
    public String Description.getDescription() {
        return this.description;
    }
    
    public void Description.setDescription(String description) {
        this.description = description;
    }
    
    public String Description.getExampleValues() {
        return this.exampleValues;
    }
    
    public void Description.setExampleValues(String exampleValues) {
        this.exampleValues = exampleValues;
    }
    
    public Person Description.getPerson() {
        return this.person;
    }
    
    public void Description.setPerson(Person person) {
        this.person = person;
    }
    
    public Date Description.getCreated() {
        return this.created;
    }
    
    public void Description.setCreated(Date created) {
        this.created = created;
    }
    
}
