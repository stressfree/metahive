// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.web.model;

import com.sfs.metahive.model.CommentType;
import com.sfs.metahive.model.Definition;
import java.lang.String;

privileged aspect CommentForm_Roo_JavaBean {
    
    public long CommentForm.getId() {
        return this.id;
    }
    
    public void CommentForm.setId(long id) {
        this.id = id;
    }
    
    public long CommentForm.getVersion() {
        return this.version;
    }
    
    public void CommentForm.setVersion(long version) {
        this.version = version;
    }
    
    public CommentType CommentForm.getCommentType() {
        return this.commentType;
    }
    
    public void CommentForm.setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }
    
    public Definition CommentForm.getDefinition() {
        return this.definition;
    }
    
    public void CommentForm.setDefinition(Definition definition) {
        this.definition = definition;
    }
    
    public String CommentForm.getMessage() {
        return this.message;
    }
    
    public void CommentForm.setMessage(String message) {
        this.message = message;
    }
    
}
