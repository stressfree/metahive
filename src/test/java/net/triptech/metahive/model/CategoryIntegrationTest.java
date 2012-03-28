package net.triptech.metahive.model;

import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import net.triptech.metahive.model.Category;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class CategoryIntegrationTest.
 */
@RooIntegrationTest(entity = Category.class)
public class CategoryIntegrationTest {

    /**
     * Test marker method.
     */
    @Test
    public void testMarkerMethod() {
    }

    /**
     * Test invalid category.
     */
    @Test(expected = ConstraintViolationException.class)
    public void testInvalidCategory() {
        Category category = new Category();

        category.persist();
    }

    /**
     * The add and fetch category test.
     */
    @Test
    @Transactional
    public void addAndFetchCategory() {
        Category category = new Category();
        category.setName("JUnit Test category");

        category.persist();

        category.flush();
        category.clear();

        Assert.assertNotNull(category.getId());

        Category category2 = Category.findCategory(category.getId());
        Assert.assertNotNull(category2);
        Assert.assertEquals(category.getName(), category2.getName());

    }
}
