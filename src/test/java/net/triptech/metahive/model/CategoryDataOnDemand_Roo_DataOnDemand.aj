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
import net.triptech.metahive.model.Category;
import org.springframework.stereotype.Component;

privileged aspect CategoryDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CategoryDataOnDemand: @Component;
    
    private Random CategoryDataOnDemand.rnd = new SecureRandom();
    
    private List<Category> CategoryDataOnDemand.data;
    
    public Category CategoryDataOnDemand.getNewTransientCategory(int index) {
        Category obj = new Category();
        setName(obj, index);
        return obj;
    }
    
    public void CategoryDataOnDemand.setName(Category obj, int index) {
        String name = "name_" + index;
        if (name.length() > 100) {
            name = new Random().nextInt(10) + name.substring(1, 100);
        }
        obj.setName(name);
    }
    
    public Category CategoryDataOnDemand.getSpecificCategory(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Category obj = data.get(index);
        return Category.findCategory(obj.getId());
    }
    
    public Category CategoryDataOnDemand.getRandomCategory() {
        init();
        Category obj = data.get(rnd.nextInt(data.size()));
        return Category.findCategory(obj.getId());
    }
    
    public boolean CategoryDataOnDemand.modifyCategory(Category obj) {
        return false;
    }
    
    public void CategoryDataOnDemand.init() {
        data = Category.findCategoryEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Category' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<net.triptech.metahive.model.Category>();
        for (int i = 0; i < 10; i++) {
            Category obj = getNewTransientCategory(i);
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
