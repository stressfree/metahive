// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Definition;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect DefinitionDataOnDemand_Roo_DataOnDemand {
    
    declare @type: DefinitionDataOnDemand: @Component;
    
    private Random DefinitionDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Definition> DefinitionDataOnDemand.data;
    
    public Definition DefinitionDataOnDemand.getNewTransientDefinition(int index) {
        com.sfs.metahive.model.Definition obj = new com.sfs.metahive.model.Definition();
        setName(obj, index);
        setDescription(obj, index);
        setExampleValues(obj, index);
        return obj;
    }
    
    private void DefinitionDataOnDemand.setName(Definition obj, int index) {
        java.lang.String name = "name_" + index;
        if (name.length() > 100) {
            name = name.substring(0, 100);
        }
        obj.setName(name);
    }
    
    private void DefinitionDataOnDemand.setDescription(Definition obj, int index) {
        java.lang.String description = "description_" + index;
        obj.setDescription(description);
    }
    
    private void DefinitionDataOnDemand.setExampleValues(Definition obj, int index) {
        java.lang.String exampleValues = "exampleValues_" + index;
        obj.setExampleValues(exampleValues);
    }
    
    public Definition DefinitionDataOnDemand.getSpecificDefinition(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Definition obj = data.get(index);
        return Definition.findDefinition(obj.getId());
    }
    
    public Definition DefinitionDataOnDemand.getRandomDefinition() {
        init();
        Definition obj = data.get(rnd.nextInt(data.size()));
        return Definition.findDefinition(obj.getId());
    }
    
    public boolean DefinitionDataOnDemand.modifyDefinition(Definition obj) {
        return false;
    }
    
    public void DefinitionDataOnDemand.init() {
        data = com.sfs.metahive.model.Definition.findDefinitionEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Definition' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.sfs.metahive.model.Definition>();
        for (int i = 0; i < 10; i++) {
            com.sfs.metahive.model.Definition obj = getNewTransientDefinition(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
