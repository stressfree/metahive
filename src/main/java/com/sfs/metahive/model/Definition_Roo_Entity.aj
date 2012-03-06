// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Definition;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Definition_Roo_Entity {

    declare @type: Definition: @Entity;

    @PersistenceContext
    transient EntityManager Definition.entityManager;

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

    @Transactional
    public void Definition.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public void Definition.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Definition attached = Definition.findDefinition(this.id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void Definition.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public void Definition.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public Definition Definition.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Definition merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static final EntityManager Definition.entityManager() {
        EntityManager em = new Definition().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long Definition.countDefinitions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Definition o", Long.class).getSingleResult();
    }

    public static Definition Definition.findDefinition(Long id) {
        if (id == null) return null;
        return entityManager().find(Definition.class, id);
    }

    public static List<Definition> Definition.findDefinitionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Definition o", Definition.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
