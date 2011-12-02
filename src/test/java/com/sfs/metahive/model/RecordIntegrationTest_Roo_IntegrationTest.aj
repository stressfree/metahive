// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.RecordOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect RecordIntegrationTest_Roo_IntegrationTest {
    
    declare @type: RecordIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: RecordIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: RecordIntegrationTest: @Transactional;
    
    @Autowired
    private RecordOnDemand RecordIntegrationTest.dod;
    
    @Test
    public void RecordIntegrationTest.testCountRecords() {
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", dod.getRandomRecord());
        long count = com.sfs.metahive.model.Record.countRecords();
        org.junit.Assert.assertTrue("Counter for 'Record' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void RecordIntegrationTest.testFindRecord() {
        com.sfs.metahive.model.Record obj = dod.getRandomRecord();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Record.findRecord(id);
        org.junit.Assert.assertNotNull("Find method for 'Record' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Record' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void RecordIntegrationTest.testFindAllRecords() {
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", dod.getRandomRecord());
        long count = com.sfs.metahive.model.Record.countRecords();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Record', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.sfs.metahive.model.Record> result = com.sfs.metahive.model.Record.findAllRecords();
        org.junit.Assert.assertNotNull("Find all method for 'Record' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Record' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void RecordIntegrationTest.testFindRecordEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", dod.getRandomRecord());
        long count = com.sfs.metahive.model.Record.countRecords();
        if (count > 20) count = 20;
        java.util.List<com.sfs.metahive.model.Record> result = com.sfs.metahive.model.Record.findRecordEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Record' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Record' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void RecordIntegrationTest.testFlush() {
        com.sfs.metahive.model.Record obj = dod.getRandomRecord();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Record.findRecord(id);
        org.junit.Assert.assertNotNull("Find method for 'Record' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyRecord(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Record' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void RecordIntegrationTest.testMerge() {
        com.sfs.metahive.model.Record obj = dod.getRandomRecord();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Record.findRecord(id);
        boolean modified =  dod.modifyRecord(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.sfs.metahive.model.Record merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Record' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void RecordIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", dod.getRandomRecord());
        com.sfs.metahive.model.Record obj = dod.getNewTransientRecord(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Record' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Record' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void RecordIntegrationTest.testRemove() {
        com.sfs.metahive.model.Record obj = dod.getRandomRecord();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.Record.findRecord(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Record' with identifier '" + id + "'", com.sfs.metahive.model.Record.findRecord(id));
    }
    
}
