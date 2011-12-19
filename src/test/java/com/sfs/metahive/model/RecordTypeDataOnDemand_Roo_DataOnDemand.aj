// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.RecordType;
import java.lang.String;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect RecordTypeDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RecordTypeDataOnDemand: @Component;
    
    private Random RecordTypeDataOnDemand.rnd = new SecureRandom();
    
    private List<RecordType> RecordTypeDataOnDemand.data;
    
    public RecordType RecordTypeDataOnDemand.getNewTransientRecordType(int index) {
        RecordType obj = new RecordType();
        setName(obj, index);
        return obj;
    }
    
    public void RecordTypeDataOnDemand.setName(RecordType obj, int index) {
        String name = "name_" + index;
        if (name.length() > 100) {
            name = new Random().nextInt(10) + name.substring(1, 100);
        }
        obj.setName(name);
    }
    
    public RecordType RecordTypeDataOnDemand.getSpecificRecordType(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        RecordType obj = data.get(index);
        return RecordType.findRecordType(obj.getId());
    }
    
    public RecordType RecordTypeDataOnDemand.getRandomRecordType() {
        init();
        RecordType obj = data.get(rnd.nextInt(data.size()));
        return RecordType.findRecordType(obj.getId());
    }
    
    public boolean RecordTypeDataOnDemand.modifyRecordType(RecordType obj) {
        return false;
    }
    
    public void RecordTypeDataOnDemand.init() {
        data = RecordType.findRecordTypeEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'RecordType' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<com.sfs.metahive.model.RecordType>();
        for (int i = 0; i < 10; i++) {
            RecordType obj = getNewTransientRecordType(i);
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