// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.web.model;

import java.util.Map;
import net.triptech.metahive.web.model.FilterAction;
import net.triptech.metahive.web.model.FilterVector;

privileged aspect FilterVector_Roo_JavaBean {
    
    public FilterAction FilterVector.getAction() {
        return this.action;
    }
    
    public void FilterVector.setAction(FilterAction action) {
        this.action = action;
    }
    
    public Map<String, String> FilterVector.getFilterVariables() {
        return this.filterVariables;
    }
    
    public void FilterVector.setFilterVariables(Map<String, String> filterVariables) {
        this.filterVariables = filterVariables;
    }
    
    public String FilterVector.getDescription() {
        return this.description;
    }
    
    public void FilterVector.setDescription(String description) {
        this.description = description;
    }
    
}
