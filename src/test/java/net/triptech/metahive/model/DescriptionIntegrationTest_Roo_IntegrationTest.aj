// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import net.triptech.metahive.model.DescriptionDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DescriptionIntegrationTest_Roo_IntegrationTest {
    
    declare @type: DescriptionIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: DescriptionIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: DescriptionIntegrationTest: @Transactional;
    
    @Autowired
    private DescriptionDataOnDemand DescriptionIntegrationTest.dod;
    
    @Test
    public void DescriptionIntegrationTest.testCountDescriptions() {
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to initialize correctly", dod.getRandomDescription());
        long count = net.triptech.metahive.model.Description.countDescriptions();
        org.junit.Assert.assertTrue("Counter for 'Description' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void DescriptionIntegrationTest.testFindDescription() {
        net.triptech.metahive.model.Description obj = dod.getRandomDescription();
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to provide an identifier", id);
        obj = net.triptech.metahive.model.Description.findDescription(id);
        org.junit.Assert.assertNotNull("Find method for 'Description' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Description' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void DescriptionIntegrationTest.testFindAllDescriptions() {
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to initialize correctly", dod.getRandomDescription());
        long count = net.triptech.metahive.model.Description.countDescriptions();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Description', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<net.triptech.metahive.model.Description> result = net.triptech.metahive.model.Description.findAllDescriptions();
        org.junit.Assert.assertNotNull("Find all method for 'Description' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Description' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void DescriptionIntegrationTest.testFindDescriptionEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to initialize correctly", dod.getRandomDescription());
        long count = net.triptech.metahive.model.Description.countDescriptions();
        if (count > 20) count = 20;
        java.util.List<net.triptech.metahive.model.Description> result = net.triptech.metahive.model.Description.findDescriptionEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Description' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Description' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void DescriptionIntegrationTest.testFlush() {
        net.triptech.metahive.model.Description obj = dod.getRandomDescription();
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to provide an identifier", id);
        obj = net.triptech.metahive.model.Description.findDescription(id);
        org.junit.Assert.assertNotNull("Find method for 'Description' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyDescription(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Description' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void DescriptionIntegrationTest.testMerge() {
        net.triptech.metahive.model.Description obj = dod.getRandomDescription();
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to provide an identifier", id);
        obj = net.triptech.metahive.model.Description.findDescription(id);
        boolean modified =  dod.modifyDescription(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        net.triptech.metahive.model.Description merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Description' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void DescriptionIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to initialize correctly", dod.getRandomDescription());
        net.triptech.metahive.model.Description obj = dod.getNewTransientDescription(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Description' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Description' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void DescriptionIntegrationTest.testRemove() {
        net.triptech.metahive.model.Description obj = dod.getRandomDescription();
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Description' failed to provide an identifier", id);
        obj = net.triptech.metahive.model.Description.findDescription(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Description' with identifier '" + id + "'", net.triptech.metahive.model.Description.findDescription(id));
    }
    
}
