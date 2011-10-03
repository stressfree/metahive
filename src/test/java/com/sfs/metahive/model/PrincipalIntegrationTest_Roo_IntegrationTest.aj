// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.PrincipalDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PrincipalIntegrationTest_Roo_IntegrationTest {
    
    declare @type: PrincipalIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: PrincipalIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: PrincipalIntegrationTest: @Transactional;
    
    @Autowired
    private PrincipalDataOnDemand PrincipalIntegrationTest.dod;
    
    @Test
    public void PrincipalIntegrationTest.testCountPrincipals() {
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to initialize correctly", dod.getRandomPrincipal());
        long count = com.sfs.metahive.model.Principal.countPrincipals();
        org.junit.Assert.assertTrue("Counter for 'Principal' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void PrincipalIntegrationTest.testFindPrincipal() {
        com.sfs.metahive.model.Principal obj = dod.getRandomPrincipal();
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Principal.findPrincipal(id);
        org.junit.Assert.assertNotNull("Find method for 'Principal' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Principal' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void PrincipalIntegrationTest.testFindAllPrincipals() {
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to initialize correctly", dod.getRandomPrincipal());
        long count = com.sfs.metahive.model.Principal.countPrincipals();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Principal', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.sfs.metahive.model.Principal> result = com.sfs.metahive.model.Principal.findAllPrincipals();
        org.junit.Assert.assertNotNull("Find all method for 'Principal' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Principal' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void PrincipalIntegrationTest.testFindPrincipalEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to initialize correctly", dod.getRandomPrincipal());
        long count = com.sfs.metahive.model.Principal.countPrincipals();
        if (count > 20) count = 20;
        java.util.List<com.sfs.metahive.model.Principal> result = com.sfs.metahive.model.Principal.findPrincipalEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Principal' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Principal' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void PrincipalIntegrationTest.testFlush() {
        com.sfs.metahive.model.Principal obj = dod.getRandomPrincipal();
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Principal.findPrincipal(id);
        org.junit.Assert.assertNotNull("Find method for 'Principal' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPrincipal(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Principal' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PrincipalIntegrationTest.testMerge() {
        com.sfs.metahive.model.Principal obj = dod.getRandomPrincipal();
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Principal.findPrincipal(id);
        boolean modified =  dod.modifyPrincipal(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.sfs.metahive.model.Principal merged = (com.sfs.metahive.model.Principal) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Principal' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PrincipalIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to initialize correctly", dod.getRandomPrincipal());
        com.sfs.metahive.model.Principal obj = dod.getNewTransientPrincipal(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Principal' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Principal' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void PrincipalIntegrationTest.testRemove() {
        com.sfs.metahive.model.Principal obj = dod.getRandomPrincipal();
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Principal' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Principal.findPrincipal(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Principal' with identifier '" + id + "'", com.sfs.metahive.model.Principal.findPrincipal(id));
    }
    
}