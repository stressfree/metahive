// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.lang.String;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import net.triptech.metahive.model.Organisation;
import org.springframework.stereotype.Component;

privileged aspect OrganisationDataOnDemand_Roo_DataOnDemand {
    
    declare @type: OrganisationDataOnDemand: @Component;
    
    private Random OrganisationDataOnDemand.rnd = new SecureRandom();
    
    private List<Organisation> OrganisationDataOnDemand.data;
    
    public Organisation OrganisationDataOnDemand.getNewTransientOrganisation(int index) {
        Organisation obj = new Organisation();
        setName(obj, index);
        return obj;
    }
    
    public void OrganisationDataOnDemand.setName(Organisation obj, int index) {
        String name = "name_" + index;
        if (name.length() > 100) {
            name = new Random().nextInt(10) + name.substring(1, 100);
        }
        obj.setName(name);
    }
    
    public Organisation OrganisationDataOnDemand.getSpecificOrganisation(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Organisation obj = data.get(index);
        return Organisation.findOrganisation(obj.getId());
    }
    
    public Organisation OrganisationDataOnDemand.getRandomOrganisation() {
        init();
        Organisation obj = data.get(rnd.nextInt(data.size()));
        return Organisation.findOrganisation(obj.getId());
    }
    
    public boolean OrganisationDataOnDemand.modifyOrganisation(Organisation obj) {
        return false;
    }
    
    public void OrganisationDataOnDemand.init() {
        data = Organisation.findOrganisationEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Organisation' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<net.triptech.metahive.model.Organisation>();
        for (int i = 0; i < 10; i++) {
            Organisation obj = getNewTransientOrganisation(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}