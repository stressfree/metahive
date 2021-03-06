// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.triptech.metahive.model;

import java.util.List;
import net.triptech.metahive.model.Organisation;
import net.triptech.metahive.model.OrganisationDataOnDemand;
import net.triptech.metahive.model.OrganisationIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect OrganisationIntegrationTest_Roo_IntegrationTest {
    
    declare @type: OrganisationIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: OrganisationIntegrationTest: @Transactional;
    
    @Autowired
    private OrganisationDataOnDemand OrganisationIntegrationTest.dod;
    
    @Test
    public void OrganisationIntegrationTest.testCountOrganisations() {
        Assert.assertNotNull("Data on demand for 'Organisation' failed to initialize correctly", dod.getRandomOrganisation());
        long count = Organisation.countOrganisations();
        Assert.assertTrue("Counter for 'Organisation' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void OrganisationIntegrationTest.testFindOrganisation() {
        Organisation obj = dod.getRandomOrganisation();
        Assert.assertNotNull("Data on demand for 'Organisation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Organisation' failed to provide an identifier", id);
        obj = Organisation.findOrganisation(id);
        Assert.assertNotNull("Find method for 'Organisation' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Organisation' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void OrganisationIntegrationTest.testFindAllOrganisations() {
        Assert.assertNotNull("Data on demand for 'Organisation' failed to initialize correctly", dod.getRandomOrganisation());
        long count = Organisation.countOrganisations();
        Assert.assertTrue("Too expensive to perform a find all test for 'Organisation', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Organisation> result = Organisation.findAllOrganisations();
        Assert.assertNotNull("Find all method for 'Organisation' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Organisation' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void OrganisationIntegrationTest.testFindOrganisationEntries() {
        Assert.assertNotNull("Data on demand for 'Organisation' failed to initialize correctly", dod.getRandomOrganisation());
        long count = Organisation.countOrganisations();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Organisation> result = Organisation.findOrganisationEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Organisation' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Organisation' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void OrganisationIntegrationTest.testFlush() {
        Organisation obj = dod.getRandomOrganisation();
        Assert.assertNotNull("Data on demand for 'Organisation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Organisation' failed to provide an identifier", id);
        obj = Organisation.findOrganisation(id);
        Assert.assertNotNull("Find method for 'Organisation' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyOrganisation(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Organisation' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void OrganisationIntegrationTest.testMergeUpdate() {
        Organisation obj = dod.getRandomOrganisation();
        Assert.assertNotNull("Data on demand for 'Organisation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Organisation' failed to provide an identifier", id);
        obj = Organisation.findOrganisation(id);
        boolean modified =  dod.modifyOrganisation(obj);
        Integer currentVersion = obj.getVersion();
        Organisation merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Organisation' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void OrganisationIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Organisation' failed to initialize correctly", dod.getRandomOrganisation());
        Organisation obj = dod.getNewTransientOrganisation(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Organisation' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Organisation' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Organisation' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void OrganisationIntegrationTest.testRemove() {
        Organisation obj = dod.getRandomOrganisation();
        Assert.assertNotNull("Data on demand for 'Organisation' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Organisation' failed to provide an identifier", id);
        obj = Organisation.findOrganisation(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Organisation' with identifier '" + id + "'", Organisation.findOrganisation(id));
    }
    
}
