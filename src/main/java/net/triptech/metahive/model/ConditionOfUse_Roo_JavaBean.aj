// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.lang.String;
import java.util.List;
import net.triptech.metahive.model.DataSource;

privileged aspect ConditionOfUse_Roo_JavaBean {
    
    public String ConditionOfUse.getName() {
        return this.name;
    }
    
    public void ConditionOfUse.setName(String name) {
        this.name = name;
    }
    
    public String ConditionOfUse.getDetails() {
        return this.details;
    }
    
    public void ConditionOfUse.setDetails(String details) {
        this.details = details;
    }
    
    public List<DataSource> ConditionOfUse.getDataSources() {
        return this.dataSources;
    }
    
    public void ConditionOfUse.setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }
    
}