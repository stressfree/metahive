// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import net.triptech.metahive.model.Category;

privileged aspect Category_Roo_Jpa_Entity {
    
    declare @type: Category: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Category.id;
    
    @Version
    @Column(name = "version")
    private Integer Category.version;
    
    public Long Category.getId() {
        return this.id;
    }
    
    public void Category.setId(Long id) {
        this.id = id;
    }
    
    public Integer Category.getVersion() {
        return this.version;
    }
    
    public void Category.setVersion(Integer version) {
        this.version = version;
    }
    
}
