// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.DefinitionDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DefinitionIntegrationTest_Roo_IntegrationTest {

    declare @type: DefinitionIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);

    declare @type: DefinitionIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");

    declare @type: DefinitionIntegrationTest: @Transactional;

    @Autowired
    private DefinitionDataOnDemand DefinitionIntegrationTest.dod;

    @Test
    public void DefinitionIntegrationTest.testCountDefinitions() {
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to initialize correctly", dod.getRandomDefinition());
        long count = com.sfs.metahive.model.Definition.countDefinitions();
        org.junit.Assert.assertTrue("Counter for 'Definition' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void DefinitionIntegrationTest.testFindDefinition() {
        com.sfs.metahive.model.Definition obj = dod.getRandomDefinition();
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Definition.findDefinition(id);
        org.junit.Assert.assertNotNull("Find method for 'Definition' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Definition' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void DefinitionIntegrationTest.testFindAllDefinitions() {
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to initialize correctly", dod.getRandomDefinition());
        long count = com.sfs.metahive.model.Definition.countDefinitions();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Definition', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.sfs.metahive.model.Definition> result = com.sfs.metahive.model.Definition.findAllDefinitions();
        org.junit.Assert.assertNotNull("Find all method for 'Definition' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Definition' failed to return any data", result.size() > 0);
    }

    @Test
    public void DefinitionIntegrationTest.testFindDefinitionEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to initialize correctly", dod.getRandomDefinition());
        long count = com.sfs.metahive.model.Definition.countDefinitions();
        if (count > 20) count = 20;
        java.util.List<com.sfs.metahive.model.Definition> result = com.sfs.metahive.model.Definition.findDefinitionEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Definition' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Definition' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void DefinitionIntegrationTest.testFlush() {
        com.sfs.metahive.model.Definition obj = dod.getRandomDefinition();
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Definition.findDefinition(id);
        org.junit.Assert.assertNotNull("Find method for 'Definition' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyDefinition(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Definition' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

    @Test
    public void DefinitionIntegrationTest.testMerge() {
        com.sfs.metahive.model.Definition obj = dod.getRandomDefinition();
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Definition.findDefinition(id);
        boolean modified =  dod.modifyDefinition(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.sfs.metahive.model.Definition merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Definition' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

    @Test
    public void DefinitionIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to initialize correctly", dod.getRandomDefinition());
        com.sfs.metahive.model.Definition obj = dod.getNewTransientDefinition(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Definition' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Definition' identifier to no longer be null", obj.getId());
    }

    @Test
    public void DefinitionIntegrationTest.testRemove() {
        com.sfs.metahive.model.Definition obj = dod.getRandomDefinition();
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Definition' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Definition.findDefinition(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Definition' with identifier '" + id + "'", com.sfs.metahive.model.Definition.findDefinition(id));
    }

}
