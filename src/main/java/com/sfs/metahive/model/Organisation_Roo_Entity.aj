// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Organisation;
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

privileged aspect Organisation_Roo_Entity {

    declare @type: Organisation: @Entity;

    @PersistenceContext
    transient EntityManager Organisation.entityManager;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Organisation.id;

    @Version
    @Column(name = "version")
    private Integer Organisation.version;

    public Long Organisation.getId() {
        return this.id;
    }

    public void Organisation.setId(Long id) {
        this.id = id;
    }

    public Integer Organisation.getVersion() {
        return this.version;
    }

    public void Organisation.setVersion(Integer version) {
        this.version = version;
    }

    @Transactional
    public void Organisation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public void Organisation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Organisation attached = Organisation.findOrganisation(this.id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void Organisation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public void Organisation.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public Organisation Organisation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Organisation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static final EntityManager Organisation.entityManager() {
        EntityManager em = new Organisation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long Organisation.countOrganisations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Organisation o", Long.class).getSingleResult();
    }

    public static Organisation Organisation.findOrganisation(Long id) {
        if (id == null) return null;
        return entityManager().find(Organisation.class, id);
    }

    public static List<Organisation> Organisation.findOrganisationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Organisation o", Organisation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
