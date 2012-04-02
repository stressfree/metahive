// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

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
import net.triptech.metahive.model.SubmittedField;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SubmittedField_Roo_Entity {
    
    declare @type: SubmittedField: @Entity;
    
    @PersistenceContext
    transient EntityManager SubmittedField.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long SubmittedField.id;
    
    @Version
    @Column(name = "version")
    private Integer SubmittedField.version;
    
    public Long SubmittedField.getId() {
        return this.id;
    }
    
    public void SubmittedField.setId(Long id) {
        this.id = id;
    }
    
    public Integer SubmittedField.getVersion() {
        return this.version;
    }
    
    public void SubmittedField.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void SubmittedField.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void SubmittedField.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SubmittedField attached = SubmittedField.findSubmittedField(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void SubmittedField.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void SubmittedField.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public SubmittedField SubmittedField.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SubmittedField merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager SubmittedField.entityManager() {
        EntityManager em = new SubmittedField().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SubmittedField.countSubmittedFields() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SubmittedField o", Long.class).getSingleResult();
    }
    
    public static List<SubmittedField> SubmittedField.findAllSubmittedFields() {
        return entityManager().createQuery("SELECT o FROM SubmittedField o", SubmittedField.class).getResultList();
    }
    
    public static SubmittedField SubmittedField.findSubmittedField(Long id) {
        if (id == null) return null;
        return entityManager().find(SubmittedField.class, id);
    }
    
    public static List<SubmittedField> SubmittedField.findSubmittedFieldEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SubmittedField o", SubmittedField.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
