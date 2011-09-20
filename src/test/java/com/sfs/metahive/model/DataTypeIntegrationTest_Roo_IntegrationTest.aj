// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sfs.metahive.model;

import com.sfs.metahive.model.DataTypeDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DataTypeIntegrationTest_Roo_IntegrationTest {
    
    declare @type: DataTypeIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: DataTypeIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: DataTypeIntegrationTest: @Transactional;
    
    @Autowired
    private DataTypeDataOnDemand DataTypeIntegrationTest.dod;
    
    @Test
    public void DataTypeIntegrationTest.testCountDataTypes() {
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to initialize correctly", dod.getRandomDataType());
        long count = com.sfs.metahive.model.DataType.countDataTypes();
        org.junit.Assert.assertTrue("Counter for 'DataType' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void DataTypeIntegrationTest.testFindDataType() {
        com.sfs.metahive.model.DataType obj = dod.getRandomDataType();
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.DataType.findDataType(id);
        org.junit.Assert.assertNotNull("Find method for 'DataType' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'DataType' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void DataTypeIntegrationTest.testFindAllDataTypes() {
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to initialize correctly", dod.getRandomDataType());
        long count = com.sfs.metahive.model.DataType.countDataTypes();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'DataType', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.sfs.metahive.model.DataType> result = com.sfs.metahive.model.DataType.findAllDataTypes();
        org.junit.Assert.assertNotNull("Find all method for 'DataType' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'DataType' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void DataTypeIntegrationTest.testFindDataTypeEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to initialize correctly", dod.getRandomDataType());
        long count = com.sfs.metahive.model.DataType.countDataTypes();
        if (count > 20) count = 20;
        java.util.List<com.sfs.metahive.model.DataType> result = com.sfs.metahive.model.DataType.findDataTypeEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'DataType' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'DataType' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void DataTypeIntegrationTest.testFlush() {
        com.sfs.metahive.model.DataType obj = dod.getRandomDataType();
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.DataType.findDataType(id);
        org.junit.Assert.assertNotNull("Find method for 'DataType' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyDataType(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'DataType' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void DataTypeIntegrationTest.testMerge() {
        com.sfs.metahive.model.DataType obj = dod.getRandomDataType();
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.DataType.findDataType(id);
        boolean modified =  dod.modifyDataType(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.sfs.metahive.model.DataType merged = (com.sfs.metahive.model.DataType) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'DataType' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void DataTypeIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to initialize correctly", dod.getRandomDataType());
        com.sfs.metahive.model.DataType obj = dod.getNewTransientDataType(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'DataType' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'DataType' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void DataTypeIntegrationTest.testRemove() {
        com.sfs.metahive.model.DataType obj = dod.getRandomDataType();
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'DataType' failed to provide an identifier", id);
        obj = com.sfs.metahive.model.DataType.findDataType(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'DataType' with identifier '" + id + "'", com.sfs.metahive.model.DataType.findDataType(id));
    }
    
}