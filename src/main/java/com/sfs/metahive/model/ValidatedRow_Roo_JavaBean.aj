// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.ValidatedField;
import java.lang.String;
import java.util.List;

privileged aspect ValidatedRow_Roo_JavaBean {
    
    public boolean ValidatedRow.isValid() {
        return this.valid;
    }
    
    public void ValidatedRow.setValid(boolean valid) {
        this.valid = valid;
    }
    
    public List<ValidatedField> ValidatedRow.getFields() {
        return this.fields;
    }
    
    public void ValidatedRow.setFields(List<ValidatedField> fields) {
        this.fields = fields;
    }
    
    public String ValidatedRow.getComment() {
        return this.comment;
    }
    
    public void ValidatedRow.setComment(String comment) {
        this.comment = comment;
    }
    
}