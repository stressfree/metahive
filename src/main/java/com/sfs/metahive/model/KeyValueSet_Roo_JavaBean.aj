// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.KeyValue;
import java.lang.Long;
import java.lang.String;
import java.util.List;

privileged aspect KeyValueSet_Roo_JavaBean {
    
    public Long KeyValueSet.getId() {
        return this.id;
    }
    
    public void KeyValueSet.setId(Long id) {
        this.id = id;
    }
    
    public String KeyValueSet.getName() {
        return this.name;
    }
    
    public void KeyValueSet.setName(String name) {
        this.name = name;
    }
    
    public List<KeyValue> KeyValueSet.getKeyValues() {
        return this.keyValues;
    }
    
    public void KeyValueSet.setKeyValues(List<KeyValue> keyValues) {
        this.keyValues = keyValues;
    }
    
}
