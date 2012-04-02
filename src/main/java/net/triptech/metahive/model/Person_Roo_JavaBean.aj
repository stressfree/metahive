// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.lang.Boolean;
import java.lang.String;
import java.util.List;
import java.util.Map;
import net.triptech.metahive.model.Comment;
import net.triptech.metahive.model.DataSource;
import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.Description;
import net.triptech.metahive.model.Organisation;
import net.triptech.metahive.model.Submission;
import net.triptech.metahive.model.UserRole;
import net.triptech.metahive.model.UserStatus;

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
    
    public List<Description> Person.getDescriptions() {
        return this.descriptions;
    }
    
    public void Person.setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }
    
    public List<Organisation> Person.getOrganisations() {
        return this.organisations;
    }
    
    public void Person.setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }
    
    public List<DataSource> Person.getDataSources() {
        return this.dataSources;
    }
    
    public void Person.setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }
    
    public List<Comment> Person.getComments() {
        return this.comments;
    }
    
    public void Person.setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public List<Submission> Person.getSubmissions() {
        return this.submissions;
    }
    
    public void Person.setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
    
    public List<Definition> Person.getSearchDefinitions() {
        return this.searchDefinitions;
    }
    
    public void Person.setSearchDefinitions(List<Definition> searchDefinitions) {
        this.searchDefinitions = searchDefinitions;
    }
    
    public boolean Person.isShowAllDefinitions() {
        return this.showAllDefinitions;
    }
    
    public void Person.setShowAllDefinitions(boolean showAllDefinitions) {
        this.showAllDefinitions = showAllDefinitions;
    }
    
    public boolean Person.isExpandAllDefinitions() {
        return this.expandAllDefinitions;
    }
    
    public void Person.setExpandAllDefinitions(boolean expandAllDefinitions) {
        this.expandAllDefinitions = expandAllDefinitions;
    }
    
    public String Person.getSearchOptions() {
        return this.searchOptions;
    }
    
    public void Person.setSearchOptions(String searchOptions) {
        this.searchOptions = searchOptions;
    }
    
    public void Person.setSearchOptionsMap(Map<String, Boolean> searchOptionsMap) {
        this.searchOptionsMap = searchOptionsMap;
    }
    
}
