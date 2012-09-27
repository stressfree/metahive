// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

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
import net.triptech.metahive.model.Comment;
import net.triptech.metahive.model.CommentDataOnDemand;
import net.triptech.metahive.model.CommentType;
import net.triptech.metahive.model.DefinitionDataOnDemand;
import net.triptech.metahive.model.Person;
import net.triptech.metahive.model.PersonDataOnDemand;
import net.triptech.metahive.model.RecordDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect CommentDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CommentDataOnDemand: @Component;
    
    private Random CommentDataOnDemand.rnd = new SecureRandom();
    
    private List<Comment> CommentDataOnDemand.data;
    
    @Autowired
    private DefinitionDataOnDemand CommentDataOnDemand.definitionDataOnDemand;
    
    @Autowired
    private PersonDataOnDemand CommentDataOnDemand.personDataOnDemand;
    
    @Autowired
    private RecordDataOnDemand CommentDataOnDemand.recordDataOnDemand;
    
    public Comment CommentDataOnDemand.getNewTransientComment(int index) {
        Comment obj = new Comment();
        setCommentType(obj, index);
        setCreated(obj, index);
        setDataSourceId(obj, index);
        setDescriptionId(obj, index);
        setKeyValueId(obj, index);
        setMessage(obj, index);
        setPerson(obj, index);
        return obj;
    }
    
    public void CommentDataOnDemand.setCommentType(Comment obj, int index) {
        CommentType commentType = CommentType.class.getEnumConstants()[0];
        obj.setCommentType(commentType);
    }
    
    public void CommentDataOnDemand.setCreated(Comment obj, int index) {
        Date created = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setCreated(created);
    }
    
    public void CommentDataOnDemand.setDataSourceId(Comment obj, int index) {
        Long dataSourceId = new Integer(index).longValue();
        obj.setDataSourceId(dataSourceId);
    }
    
    public void CommentDataOnDemand.setDescriptionId(Comment obj, int index) {
        Long descriptionId = new Integer(index).longValue();
        obj.setDescriptionId(descriptionId);
    }
    
    public void CommentDataOnDemand.setKeyValueId(Comment obj, int index) {
        Long keyValueId = new Integer(index).longValue();
        obj.setKeyValueId(keyValueId);
    }
    
    public void CommentDataOnDemand.setMessage(Comment obj, int index) {
        String message = "message_" + index;
        obj.setMessage(message);
    }
    
    public void CommentDataOnDemand.setPerson(Comment obj, int index) {
        Person person = personDataOnDemand.getRandomPerson();
        obj.setPerson(person);
    }
    
    public Comment CommentDataOnDemand.getSpecificComment(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Comment obj = data.get(index);
        Long id = obj.getId();
        return Comment.findComment(id);
    }
    
    public Comment CommentDataOnDemand.getRandomComment() {
        init();
        Comment obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Comment.findComment(id);
    }
    
    public boolean CommentDataOnDemand.modifyComment(Comment obj) {
        return false;
    }
    
    public void CommentDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Comment.findCommentEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Comment' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Comment>();
        for (int i = 0; i < 10; i++) {
            Comment obj = getNewTransientComment(i);
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
