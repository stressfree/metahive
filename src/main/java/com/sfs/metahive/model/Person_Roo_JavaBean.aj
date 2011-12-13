// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Comment;
import com.sfs.metahive.model.DataSource;
import com.sfs.metahive.model.Description;
import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.Submission;
import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;
import java.lang.String;
import java.util.Set;

privileged aspect Person_Roo_JavaBean {
    
    public String Person.getOpenIdIdentifier() {
        return this.openIdIdentifier;
    }
    
    public void Person.setOpenIdIdentifier(String openIdIdentifier) {
        this.openIdIdentifier = openIdIdentifier;
    }
    
    public UserRole Person.getUserRole() {
        return this.userRole;
    }
    
    public void Person.setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    
    public UserStatus Person.getUserStatus() {
        return this.userStatus;
    }
    
    public void Person.setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    
    public String Person.getFirstName() {
        return this.firstName;
    }
    
    public void Person.setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String Person.getLastName() {
        return this.lastName;
    }
    
    public void Person.setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String Person.getEmailAddress() {
        return this.emailAddress;
    }
    
    public void Person.setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public Set<Description> Person.getDescriptions() {
        return this.descriptions;
    }
    
    public void Person.setDescriptions(Set<Description> descriptions) {
        this.descriptions = descriptions;
    }
    
    public Set<Organisation> Person.getOrganisations() {
        return this.organisations;
    }
    
    public void Person.setOrganisations(Set<Organisation> organisations) {
        this.organisations = organisations;
    }
    
    public Set<DataSource> Person.getDataSources() {
        return this.dataSources;
    }
    
    public void Person.setDataSources(Set<DataSource> dataSources) {
        this.dataSources = dataSources;
    }
    
    public Set<Comment> Person.getComments() {
        return this.comments;
    }
    
    public void Person.setComments(Set<Comment> comments) {
        this.comments = comments;
    }
    
    public Set<Submission> Person.getSubmissions() {
        return this.submissions;
    }
    
    public void Person.setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }
    
}
