// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.CommentType;
import com.sfs.metahive.model.DataSource;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Description;
import com.sfs.metahive.model.Person;
import java.lang.String;
import java.util.Date;

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
    
    public Description Comment.getDescription() {
        return this.description;
    }
    
    public void Comment.setDescription(Description description) {
        this.description = description;
    }
    
    public DataSource Comment.getDataSource() {
        return this.dataSource;
    }
    
    public void Comment.setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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
