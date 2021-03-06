// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.util.Date;
import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.Record;
import net.triptech.metahive.model.Submission;
import net.triptech.metahive.model.SubmittedField;

privileged aspect SubmittedField_Roo_JavaBean {
    
    public Record SubmittedField.getRecord() {
        return this.record;
    }
    
    public void SubmittedField.setRecord(Record record) {
        this.record = record;
    }
    
    public Definition SubmittedField.getDefinition() {
        return this.definition;
    }
    
    public void SubmittedField.setDefinition(Definition definition) {
        this.definition = definition;
    }
    
    public Submission SubmittedField.getSubmission() {
        return this.submission;
    }
    
    public void SubmittedField.setSubmission(Submission submission) {
        this.submission = submission;
    }
    
    public String SubmittedField.getPrimaryRecordId() {
        return this.primaryRecordId;
    }
    
    public void SubmittedField.setPrimaryRecordId(String primaryRecordId) {
        this.primaryRecordId = primaryRecordId;
    }
    
    public String SubmittedField.getSecondaryRecordId() {
        return this.secondaryRecordId;
    }
    
    public void SubmittedField.setSecondaryRecordId(String secondaryRecordId) {
        this.secondaryRecordId = secondaryRecordId;
    }
    
    public String SubmittedField.getTertiaryRecordId() {
        return this.tertiaryRecordId;
    }
    
    public void SubmittedField.setTertiaryRecordId(String tertiaryRecordId) {
        this.tertiaryRecordId = tertiaryRecordId;
    }
    
    public String SubmittedField.getValue() {
        return this.value;
    }
    
    public void SubmittedField.setValue(String value) {
        this.value = value;
    }
    
    public Date SubmittedField.getCreated() {
        return this.created;
    }
    
    public void SubmittedField.setCreated(Date created) {
        this.created = created;
    }
    
}
