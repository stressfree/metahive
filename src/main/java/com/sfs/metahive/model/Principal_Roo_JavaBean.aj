// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.UserRole;
import com.sfs.metahive.model.UserStatus;
import java.lang.String;

privileged aspect Principal_Roo_JavaBean {
    
    public String Principal.getOpenIdIdentifier() {
        return this.openIdIdentifier;
    }
    
    public void Principal.setOpenIdIdentifier(String openIdIdentifier) {
        this.openIdIdentifier = openIdIdentifier;
    }
    
    public UserRole Principal.getUserRole() {
        return this.userRole;
    }
    
    public void Principal.setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    
    public UserStatus Principal.getUserStatus() {
        return this.userStatus;
    }
    
    public void Principal.setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    
    public String Principal.getFirstName() {
        return this.firstName;
    }
    
    public void Principal.setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String Principal.getLastName() {
        return this.lastName;
    }
    
    public void Principal.setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String Principal.getEmailAddress() {
        return this.emailAddress;
    }
    
    public void Principal.setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
}
