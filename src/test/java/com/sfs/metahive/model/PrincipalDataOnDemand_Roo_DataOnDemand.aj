// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.Principal;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect PrincipalDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PrincipalDataOnDemand: @Component;
    
    private Random PrincipalDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Principal> PrincipalDataOnDemand.data;
    
    public Principal PrincipalDataOnDemand.getNewTransientPrincipal(int index) {
        com.sfs.metahive.model.Principal obj = new com.sfs.metahive.model.Principal();
        setOpenIdIdentifier(obj, index);
        setUserRole(obj, index);
        setUserStatus(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        setEmailAddress(obj, index);
        return obj;
    }
    
    private void PrincipalDataOnDemand.setOpenIdIdentifier(Principal obj, int index) {
        java.lang.String openIdIdentifier = "openIdIdentifier_" + index;
        obj.setOpenIdIdentifier(openIdIdentifier);
    }
    
    private void PrincipalDataOnDemand.setUserRole(Principal obj, int index) {
        com.sfs.metahive.model.UserRole userRole = com.sfs.metahive.model.UserRole.class.getEnumConstants()[0];
        obj.setUserRole(userRole);
    }
    
    private void PrincipalDataOnDemand.setUserStatus(Principal obj, int index) {
        com.sfs.metahive.model.UserStatus userStatus = com.sfs.metahive.model.UserStatus.class.getEnumConstants()[0];
        obj.setUserStatus(userStatus);
    }
    
    private void PrincipalDataOnDemand.setFirstName(Principal obj, int index) {
        java.lang.String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }
    
    private void PrincipalDataOnDemand.setLastName(Principal obj, int index) {
        java.lang.String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }
    
    private void PrincipalDataOnDemand.setEmailAddress(Principal obj, int index) {
        java.lang.String emailAddress = "emailAddress_" + index;
        obj.setEmailAddress(emailAddress);
    }
    
    public Principal PrincipalDataOnDemand.getSpecificPrincipal(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Principal obj = data.get(index);
        return Principal.findPrincipal(obj.getId());
    }
    
    public Principal PrincipalDataOnDemand.getRandomPrincipal() {
        init();
        Principal obj = data.get(rnd.nextInt(data.size()));
        return Principal.findPrincipal(obj.getId());
    }
    
    public boolean PrincipalDataOnDemand.modifyPrincipal(Principal obj) {
        return false;
    }
    
    public void PrincipalDataOnDemand.init() {
        data = com.sfs.metahive.model.Principal.findPrincipalEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Principal' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.sfs.metahive.model.Principal>();
        for (int i = 0; i < 10; i++) {
            com.sfs.metahive.model.Principal obj = getNewTransientPrincipal(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}