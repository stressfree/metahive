// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.lang.String;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.DefinitionDataOnDemand;
import net.triptech.metahive.model.Description;
import net.triptech.metahive.model.Person;
import net.triptech.metahive.model.PersonDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect DescriptionDataOnDemand_Roo_DataOnDemand {
    
    declare @type: DescriptionDataOnDemand: @Component;
    
    private Random DescriptionDataOnDemand.rnd = new SecureRandom();
    
    private List<Description> DescriptionDataOnDemand.data;
    
    @Autowired
    private DefinitionDataOnDemand DescriptionDataOnDemand.definitionDataOnDemand;
    
    @Autowired
    private PersonDataOnDemand DescriptionDataOnDemand.personDataOnDemand;
    
    public Description DescriptionDataOnDemand.getNewTransientDescription(int index) {
        Description obj = new Description();
        setCreated(obj, index);
        setDefinition(obj, index);
        setDescription(obj, index);
        setExampleValues(obj, index);
        setPerson(obj, index);
        setUnitOfMeasure(obj, index);
        return obj;
    }
    
    public void DescriptionDataOnDemand.setCreated(Description obj, int index) {
        Date created = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setCreated(created);
    }
    
    public void DescriptionDataOnDemand.setDefinition(Description obj, int index) {
        Definition definition = definitionDataOnDemand.getRandomDefinition();
        obj.setDefinition(definition);
    }
    
    public void DescriptionDataOnDemand.setDescription(Description obj, int index) {
        String description = "description_" + index;
        obj.setDescription(description);
    }
    
    public void DescriptionDataOnDemand.setExampleValues(Description obj, int index) {
        String exampleValues = "exampleValues_" + index;
        obj.setExampleValues(exampleValues);
    }
    
    public void DescriptionDataOnDemand.setPerson(Description obj, int index) {
        Person person = personDataOnDemand.getRandomPerson();
        obj.setPerson(person);
    }
    
    public void DescriptionDataOnDemand.setUnitOfMeasure(Description obj, int index) {
        String unitOfMeasure = "unitOfMeasure_" + index;
        obj.setUnitOfMeasure(unitOfMeasure);
    }
    
    public Description DescriptionDataOnDemand.getSpecificDescription(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Description obj = data.get(index);
        return Description.findDescription(obj.getId());
    }
    
    public Description DescriptionDataOnDemand.getRandomDescription() {
        init();
        Description obj = data.get(rnd.nextInt(data.size()));
        return Description.findDescription(obj.getId());
    }
    
    public boolean DescriptionDataOnDemand.modifyDescription(Description obj) {
        return false;
    }
    
    public void DescriptionDataOnDemand.init() {
        data = Description.findDescriptionEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Description' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<net.triptech.metahive.model.Description>();
        for (int i = 0; i < 10; i++) {
            Description obj = getNewTransientDescription(i);
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
