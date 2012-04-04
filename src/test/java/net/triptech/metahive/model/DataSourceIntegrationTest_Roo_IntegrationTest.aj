// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.util.List;
import net.triptech.metahive.model.DataSource;
import net.triptech.metahive.model.DataSourceDataOnDemand;
import net.triptech.metahive.model.DataSourceIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DataSourceIntegrationTest_Roo_IntegrationTest {
    
    declare @type: DataSourceIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: DataSourceIntegrationTest: @Transactional;
    
    @Autowired
    private DataSourceDataOnDemand DataSourceIntegrationTest.dod;
    
    @Test
    public void DataSourceIntegrationTest.testCountDataSources() {
        Assert.assertNotNull("Data on demand for 'DataSource' failed to initialize correctly", dod.getRandomDataSource());
        long count = DataSource.countDataSources();
        Assert.assertTrue("Counter for 'DataSource' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void DataSourceIntegrationTest.testFindDataSource() {
        DataSource obj = dod.getRandomDataSource();
        Assert.assertNotNull("Data on demand for 'DataSource' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'DataSource' failed to provide an identifier", id);
        obj = DataSource.findDataSource(id);
        Assert.assertNotNull("Find method for 'DataSource' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'DataSource' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void DataSourceIntegrationTest.testFindAllDataSources() {
        Assert.assertNotNull("Data on demand for 'DataSource' failed to initialize correctly", dod.getRandomDataSource());
        long count = DataSource.countDataSources();
        Assert.assertTrue("Too expensive to perform a find all test for 'DataSource', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<DataSource> result = DataSource.findAllDataSources();
        Assert.assertNotNull("Find all method for 'DataSource' illegally returned null", result);
        Assert.assertTrue("Find all method for 'DataSource' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void DataSourceIntegrationTest.testFindDataSourceEntries() {
        Assert.assertNotNull("Data on demand for 'DataSource' failed to initialize correctly", dod.getRandomDataSource());
        long count = DataSource.countDataSources();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<DataSource> result = DataSource.findDataSourceEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'DataSource' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'DataSource' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void DataSourceIntegrationTest.testFlush() {
        DataSource obj = dod.getRandomDataSource();
        Assert.assertNotNull("Data on demand for 'DataSource' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'DataSource' failed to provide an identifier", id);
        obj = DataSource.findDataSource(id);
        Assert.assertNotNull("Find method for 'DataSource' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyDataSource(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'DataSource' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void DataSourceIntegrationTest.testMergeUpdate() {
        DataSource obj = dod.getRandomDataSource();
        Assert.assertNotNull("Data on demand for 'DataSource' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'DataSource' failed to provide an identifier", id);
        obj = DataSource.findDataSource(id);
        boolean modified =  dod.modifyDataSource(obj);
        Integer currentVersion = obj.getVersion();
        DataSource merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'DataSource' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void DataSourceIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'DataSource' failed to initialize correctly", dod.getRandomDataSource());
        DataSource obj = dod.getNewTransientDataSource(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'DataSource' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'DataSource' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'DataSource' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void DataSourceIntegrationTest.testRemove() {
        DataSource obj = dod.getRandomDataSource();
        Assert.assertNotNull("Data on demand for 'DataSource' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'DataSource' failed to provide an identifier", id);
        obj = DataSource.findDataSource(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'DataSource' with identifier '" + id + "'", DataSource.findDataSource(id));
    }
    
}
