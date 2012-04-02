// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import net.triptech.metahive.model.Definition;

privileged aspect Definition_Roo_Finder {
    
    public static TypedQuery<Definition> Definition.findDefinitionsByNameLike(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        name = name.replace('*', '%');
        if (name.charAt(0) != '%') {
            name = "%" + name;
        }
        if (name.charAt(name.length() - 1) != '%') {
            name = name + "%";
        }
        EntityManager em = Definition.entityManager();
        TypedQuery<Definition> q = em.createQuery("SELECT o FROM Definition AS o WHERE LOWER(o.name) LIKE LOWER(:name)", Definition.class);
        q.setParameter("name", name);
        return q;
    }
    
}
