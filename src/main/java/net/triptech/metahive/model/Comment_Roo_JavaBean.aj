// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.util.Date;
import net.triptech.metahive.model.Comment;
import net.triptech.metahive.model.CommentType;
import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.Person;
import net.triptech.metahive.model.Record;

privileged aspect Comment_Roo_JavaBean {
    
    public CommentType Comment.getCommentType() {
        return this.commentType;
    }
    
    public void Comment.setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }
    
    public String Comment.getMessage() {
        return this.message;
    }
    
    public void Comment.setMessage(String message) {
        this.message = message;
    }
    
    public Definition Comment.getDefinition() {
        return this.definition;
    }
    
    public void Comment.setDefinition(Definition definition) {
        this.definition = definition;
    }
    
    public Record Comment.getRecord() {
        return this.record;
    }
    
    public void Comment.setRecord(Record record) {
        this.record = record;
    }
    
    public Long Comment.getDescriptionId() {
        return this.descriptionId;
    }
    
    public void Comment.setDescriptionId(Long descriptionId) {
        this.descriptionId = descriptionId;
    }
    
    public Long Comment.getDataSourceId() {
        return this.dataSourceId;
    }
    
    public void Comment.setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
    
    public Long Comment.getKeyValueId() {
        return this.keyValueId;
    }
    
    public void Comment.setKeyValueId(Long keyValueId) {
        this.keyValueId = keyValueId;
    }
    
    public Person Comment.getPerson() {
        return this.person;
    }
    
    public void Comment.setPerson(Person person) {
        this.person = person;
    }
    
    public Date Comment.getCreated() {
        return this.created;
    }
    
    public void Comment.setCreated(Date created) {
        this.created = created;
    }
    
}
