// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.ConditionOfUse;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect ConditionOfUseDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ConditionOfUseDataOnDemand: @Component;
    
    private Random ConditionOfUseDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<ConditionOfUse> ConditionOfUseDataOnDemand.data;
    
    public ConditionOfUse ConditionOfUseDataOnDemand.getNewTransientConditionOfUse(int index) {
        com.sfs.metahive.model.ConditionOfUse obj = new com.sfs.metahive.model.ConditionOfUse();
        setName(obj, index);
        setDetails(obj, index);
        return obj;
    }
    
    private void ConditionOfUseDataOnDemand.setName(ConditionOfUse obj, int index) {
        java.lang.String name = "name_" + index;
        if (name.length() > 100) {
            name = name.substring(0, 100);
        }
        obj.setName(name);
    }
    
    private void ConditionOfUseDataOnDemand.setDetails(ConditionOfUse obj, int index) {
        java.lang.String details = "details_" + index;
        obj.setDetails(details);
    }
    
    public ConditionOfUse ConditionOfUseDataOnDemand.getSpecificConditionOfUse(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        ConditionOfUse obj = data.get(index);
        return ConditionOfUse.findConditionOfUse(obj.getId());
    }
    
    public ConditionOfUse ConditionOfUseDataOnDemand.getRandomConditionOfUse() {
        init();
        ConditionOfUse obj = data.get(rnd.nextInt(data.size()));
        return ConditionOfUse.findConditionOfUse(obj.getId());
    }
    
    public boolean ConditionOfUseDataOnDemand.modifyConditionOfUse(ConditionOfUse obj) {
        return false;
    }
    
    public void ConditionOfUseDataOnDemand.init() {
        data = com.sfs.metahive.model.ConditionOfUse.findConditionOfUseEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'ConditionOfUse' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.sfs.metahive.model.ConditionOfUse>();
        for (int i = 0; i < 10; i++) {
            com.sfs.metahive.model.ConditionOfUse obj = getNewTransientConditionOfUse(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}