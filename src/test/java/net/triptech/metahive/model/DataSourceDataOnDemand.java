package net.triptech.metahive.model;

import net.triptech.metahive.model.DataSource;
import net.triptech.metahive.model.Definition;

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
