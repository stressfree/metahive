package com.sfs.metahive.model;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = DataSource.class)
public class DataSourceDataOnDemand {

	public void setDefinition(DataSource obj, int index) {
		DefinitionDataOnDemand dod = new DefinitionDataOnDemand();
        Definition definition = dod.getRandomDefinition();
        definition.persist();
        
        obj.setDefinition(definition);
    }
}
