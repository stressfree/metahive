// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.util.List;
import net.triptech.metahive.model.ValidatedField;
import net.triptech.metahive.model.ValidatedRow;

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
