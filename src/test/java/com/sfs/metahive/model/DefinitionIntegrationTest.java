package com.sfs.metahive.model;

import java.util.List;

import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class DefinitionIntegrationTest.
 */
@RooIntegrationTest(entity = Definition.class)
public class DefinitionIntegrationTest {

	/**
	 * Test marker method.
	 */
	@Test
	public void testMarkerMethod() {
	}

	/**
	 * The add and fetch definition test.
	 */
	@Test
	@Transactional
	public void addAndFetchDefinition() {
		Definition def = new Definition();
		def.setDataType(DataType.TYPE_STRING);
		def.setName("JUnit Test definition");

		def.persist();

		def.flush();
		def.clear();

		Assert.assertNotNull(def.getId());

		Definition def2 = Definition.findDefinition(def.getId());
		Assert.assertNotNull(def2);
		Assert.assertEquals(def.getName(), def2.getName());
	}

	/**
	 * Test invalid definition.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidDefinition() {
		Definition def = new Definition();

		def.persist();
	}

	/**
	 * Test find by name finder.
	 */
	@Test
	public void testFindByNameFinder() {

		Definition def = new Definition();
		def.setName("JUnit Test definition");
		def.setDataType(DataType.TYPE_STRING);

		def.persist();
		def.flush();
		Definition.entityManager().clear();

		List<Definition> definitions = Definition.findDefinitionsByNameLike(
				"Test").getResultList();
		
		boolean result = false;
		if (definitions.size() > 0) {
			result = true;
		}
		
		Assert.assertEquals(true, result);
	}

	/**
	 * Test persist data type in definitions.
	 */
	@Test
	public void testPersistDataTypeInDefinitions() {
		DefinitionDataOnDemand definitionDod = new DefinitionDataOnDemand();
		Definition def = definitionDod.getRandomDefinition();

		def.setDataType(DataType.TYPE_STRING);

		def.flush();
		def.clear();

		Assert.assertEquals(Definition.findDefinition(def.getId())
				.getDataType(), DataType.TYPE_STRING);

	}

	/**
	 * Test persist categories in definitions.
	 */
	@Test
	public void testPersistCategoriesInDefinitions() {

		DefinitionDataOnDemand definitionDod = new DefinitionDataOnDemand();
		Definition def = definitionDod.getRandomDefinition();
		def.setDataType(DataType.TYPE_STRING);

		CategoryDataOnDemand categoryDod = new CategoryDataOnDemand();
		Category c1 = categoryDod.getNewTransientCategory(0);
		Category c2 = categoryDod.getNewTransientCategory(1);
		c1.persist();
		c2.persist();
		
		def.getCategories().add(c1);
		def.getCategories().add(c2);

		c1.getDefinitions().add(def);
		c2.getDefinitions().add(def);
		
		def.flush();
		def.clear();

		boolean result = false;
		if (Definition.findDefinition(def.getId()).getCategories().size() > 1) {
			result = true;
		}
		
		Assert.assertEquals(true, result);

	}
}
