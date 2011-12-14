// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Submission;
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

privileged aspect Submission_Roo_Entity {
    
    declare @type: Submission: @Entity;
    
    @PersistenceContext
    transient EntityManager Submission.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Submission.id;
    
    @Version
    @Column(name = "version")
    private Integer Submission.version;
    
    public Long Submission.getId() {
        return this.id;
    }
    
    public void Submission.setId(Long id) {
        this.id = id;
    }
    
    public Integer Submission.getVersion() {
        return this.version;
    }
    
    public void Submission.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Submission.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Submission.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Submission attached = Submission.findSubmission(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Submission.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Submission.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Submission Submission.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Submission merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Submission.entityManager() {
        EntityManager em = new Submission().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Submission.countSubmissions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Submission o", Long.class).getSingleResult();
    }
    
    public static Submission Submission.findSubmission(Long id) {
        if (id == null) return null;
        return entityManager().find(Submission.class, id);
    }
    
    public static List<Submission> Submission.findSubmissionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Submission o", Submission.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}