// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import net.triptech.metahive.model.Definition;

privileged aspect Definition_Roo_Jpa_Entity {
    
    declare @type: Definition: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Definition.id;
    
    @Version
    @Column(name = "version")
    private Integer Definition.version;
    
    public Long Definition.getId() {
        return this.id;
    }
    
    public void Definition.setId(Long id) {
        this.id = id;
    }
    
    public Integer Definition.getVersion() {
        return this.version;
    }
    
    public void Definition.setVersion(Integer version) {
        this.version = version;
    }
    
}
