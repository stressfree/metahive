// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import net.triptech.metahive.model.Person;
import net.triptech.metahive.model.PersonDataOnDemand;
import net.triptech.metahive.model.UserRole;
import net.triptech.metahive.model.UserStatus;
import org.springframework.stereotype.Component;

privileged aspect PersonDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PersonDataOnDemand: @Component;
    
    private Random PersonDataOnDemand.rnd = new SecureRandom();
    
    private List<Person> PersonDataOnDemand.data;
    
    public Person PersonDataOnDemand.getNewTransientPerson(int index) {
        Person obj = new Person();
        setEmailAddress(obj, index);
        setExpandAllDefinitions(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        setOpenIdIdentifier(obj, index);
        setSearchOptions(obj, index);
        setShowAllDefinitions(obj, index);
        setUserRole(obj, index);
        setUserStatus(obj, index);
        return obj;
    }
    
    public void PersonDataOnDemand.setEmailAddress(Person obj, int index) {
        String emailAddress = "foo" + index + "@bar.com";
        obj.setEmailAddress(emailAddress);
    }
    
    public void PersonDataOnDemand.setExpandAllDefinitions(Person obj, int index) {
        Boolean expandAllDefinitions = true;
        obj.setExpandAllDefinitions(expandAllDefinitions);
    }
    
    public void PersonDataOnDemand.setFirstName(Person obj, int index) {
        String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }
    
    public void PersonDataOnDemand.setLastName(Person obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }
    
    public void PersonDataOnDemand.setOpenIdIdentifier(Person obj, int index) {
        String openIdIdentifier = "openIdIdentifier_" + index;
        obj.setOpenIdIdentifier(openIdIdentifier);
    }
    
    public void PersonDataOnDemand.setSearchOptions(Person obj, int index) {
        String searchOptions = "searchOptions_" + index;
        obj.setSearchOptions(searchOptions);
    }
    
    public void PersonDataOnDemand.setShowAllDefinitions(Person obj, int index) {
        Boolean showAllDefinitions = true;
        obj.setShowAllDefinitions(showAllDefinitions);
    }
    
    public void PersonDataOnDemand.setUserRole(Person obj, int index) {
        UserRole userRole = UserRole.class.getEnumConstants()[0];
        obj.setUserRole(userRole);
    }
    
    public void PersonDataOnDemand.setUserStatus(Person obj, int index) {
        UserStatus userStatus = UserStatus.class.getEnumConstants()[0];
        obj.setUserStatus(userStatus);
    }
    
    public Person PersonDataOnDemand.getSpecificPerson(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Person obj = data.get(index);
        Long id = obj.getId();
        return Person.findPerson(id);
    }
    
    public Person PersonDataOnDemand.getRandomPerson() {
        init();
        Person obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Person.findPerson(id);
    }
    
    public boolean PersonDataOnDemand.modifyPerson(Person obj) {
        return false;
    }
    
    public void PersonDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Person.findPersonEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Person' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Person>();
        for (int i = 0; i < 10; i++) {
            Person obj = getNewTransientPerson(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
