package net.triptech.metahive.model;

import junit.framework.Assert;

import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.Description;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class DescriptionIntegrationTest.
 */
@RooIntegrationTest(entity = Description.class)
public class DescriptionIntegrationTest {

    /**
     * Test marker method.
     */
    @Test
    public void testMarkerMethod() {
    }

    /**
     * The add and fetch description test.
     */
    @Test
    @Transactional
    public void addAndFetchDescription() {
        DefinitionDataOnDemand definitionDod = new DefinitionDataOnDemand();
        Definition definition = definitionDod.getRandomDefinition();
        definition.persist();

        DescriptionDataOnDemand descriptionDod = new DescriptionDataOnDemand();
        Description description = descriptionDod.getRandomDescription();
        description.setDefinition(definition);

        description.persist();

        description.flush();
        description.clear();

        Assert.assertNotNull(description.getId());

        Description description2 = Description.findDescription(description.getId());
        Assert.assertNotNull(description2);
        Assert.assertEquals(description.getDescription(), description2.getDescription());

    }
}
