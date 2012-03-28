package net.triptech.metahive.model;

import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import net.triptech.metahive.model.ConditionOfUse;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ConditionOfUseIntegrationTest.
 */
@RooIntegrationTest(entity = ConditionOfUse.class)
public class ConditionOfUseIntegrationTest {

    /**
     * Test marker method.
     */
    @Test
    public void testMarkerMethod() {
    }

    /**
     * Test invalid condition of use.
     */
    @Test(expected = ConstraintViolationException.class)
    public void testInvalidCondition() {
        ConditionOfUse conditionOfUse = new ConditionOfUse();

        conditionOfUse.persist();
    }

    /**
     * The add and fetch condition of use test.
     */
    @Test
    @Transactional
    public void addAndFetchCondition() {
        ConditionOfUse condition = new ConditionOfUse();
        condition.setName("Test condition");

        condition.persist();

        condition.flush();
        condition.clear();

        Assert.assertNotNull(condition.getId());

        ConditionOfUse condition2 = ConditionOfUse.findConditionOfUse(condition.getId());
        Assert.assertNotNull(condition2);
        Assert.assertEquals(condition.getName(), condition2.getName());

    }
}
