// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import net.triptech.metahive.model.KeyValue;

privileged aspect KeyValue_Roo_Jpa_Entity {
    
    declare @type: KeyValue: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long KeyValue.id;
    
    @Version
    @Column(name = "version")
    private Integer KeyValue.version;
    
    public Long KeyValue.getId() {
        return this.id;
    }
    
    public void KeyValue.setId(Long id) {
        this.id = id;
    }
    
    public Integer KeyValue.getVersion() {
        return this.version;
    }
    
    public void KeyValue.setVersion(Integer version) {
        this.version = version;
    }
    
}