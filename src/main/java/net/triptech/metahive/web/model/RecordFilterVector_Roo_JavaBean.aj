// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.web.model;

import net.triptech.metahive.model.Definition;
import net.triptech.metahive.web.model.RecordFilterVector;

privileged aspect RecordFilterVector_Roo_JavaBean {
    
    public Definition RecordFilterVector.getDefinition() {
        return this.definition;
    }
    
    public void RecordFilterVector.setDefinition(Definition definition) {
        this.definition = definition;
    }
    
    public void RecordFilterVector.setCriteria(String criteria) {
        this.criteria = criteria;
    }
    
    public void RecordFilterVector.setConstraint(String constraint) {
        this.constraint = constraint;
    }
    
}
