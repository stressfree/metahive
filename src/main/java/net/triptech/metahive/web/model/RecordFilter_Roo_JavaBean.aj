// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.web.model;

import java.util.Date;
import java.util.List;
import net.triptech.metahive.web.model.FilterVector;
import net.triptech.metahive.web.model.RecordFilter;

privileged aspect RecordFilter_Roo_JavaBean {
    
    public void RecordFilter.setId(String id) {
        this.id = id;
    }
    
    public String RecordFilter.getRecordId() {
        return this.recordId;
    }
    
    public void RecordFilter.setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    public Long RecordFilter.getOrderId() {
        return this.orderId;
    }
    
    public boolean RecordFilter.isOrderDescending() {
        return this.orderDescending;
    }
    
    public void RecordFilter.setOrderDescending(boolean orderDescending) {
        this.orderDescending = orderDescending;
    }
    
    public List<FilterVector> RecordFilter.getFilterVectors() {
        return this.filterVectors;
    }
    
    public void RecordFilter.setFilterVectors(List<FilterVector> filterVectors) {
        this.filterVectors = filterVectors;
    }
    
    public void RecordFilter.setCreated(Date created) {
        this.created = created;
    }
    
}
