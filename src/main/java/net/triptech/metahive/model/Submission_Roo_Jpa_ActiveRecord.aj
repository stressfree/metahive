// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.triptech.metahive.model.Submission;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Submission_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Submission.entityManager;
    
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
    
}