// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.web.model;

import com.sfs.metahive.model.ConditionOfUse;
import com.sfs.metahive.model.Definition;
import com.sfs.metahive.model.Organisation;
import com.sfs.metahive.model.Person;
import java.lang.String;
import java.util.Date;
import java.util.List;

privileged aspect DataSourceForm_Roo_JavaBean {

    public long DataSourceForm.getId() {
        return this.id;
    }

    public void DataSourceForm.setId(long id) {
        this.id = id;
    }

    public long DataSourceForm.getVersion() {
        return this.version;
    }

    public void DataSourceForm.setVersion(long version) {
        this.version = version;
    }

    public Definition DataSourceForm.getDefinition() {
        return this.definition;
    }

    public void DataSourceForm.setDefinition(Definition definition) {
        this.definition = definition;
    }

    public String DataSourceForm.getCollectionSource() {
        return this.collectionSource;
    }

    public void DataSourceForm.setCollectionSource(String collectionSource) {
        this.collectionSource = collectionSource;
    }

    public Organisation DataSourceForm.getOrganisation() {
        return this.organisation;
    }

    public void DataSourceForm.setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public String DataSourceForm.getDetails() {
        return this.details;
    }

    public void DataSourceForm.setDetails(String details) {
        this.details = details;
    }

    public ConditionOfUse DataSourceForm.getConditionOfUse() {
        return this.conditionOfUse;
    }

    public void DataSourceForm.setConditionOfUse(ConditionOfUse conditionOfUse) {
        this.conditionOfUse = conditionOfUse;
    }

    public Date DataSourceForm.getCollectionDate() {
        return this.collectionDate;
    }

    public void DataSourceForm.setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public List<Person> DataSourceForm.getPointsOfContact() {
        return this.pointsOfContact;
    }

    public void DataSourceForm.setPointsOfContact(List<Person> pointsOfContact) {
        this.pointsOfContact = pointsOfContact;
    }

    public String DataSourceForm.getLogMessage() {
        return this.logMessage;
    }

    public void DataSourceForm.setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

}
