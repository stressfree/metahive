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
		DataTypeDataOnDemand dataTypeDod = new DataTypeDataOnDemand();
		DataType dataType = dataTypeDod.getRandomDataType();
		dataType.persist();

		Definition def = new Definition();
		def.setDataType(dataType);
		def.setName("Test definition");

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

		DataTypeDataOnDemand dataTypeDod = new DataTypeDataOnDemand();
		DataType dataType = dataTypeDod.getRandomDataType();
		dataType.persist();

		Definition def = new Definition();
		def.setName("Test definition");
		def.setDataType(dataType);

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

		DataType dataType = new DataType();
		dataType.setName("Test data type");
		dataType.persist();

		def.setDataType(dataType);

		def.flush();
		def.clear();

		Assert.assertEquals(Definition.findDefinition(def.getId())
				.getDataType().getName(), dataType.getName());

	}

	/**
	 * Test persist categories in definitions.
	 */
	@Test
	public void testPersistCategoriesInDefinitions() {
		DataTypeDataOnDemand dataTypeDod = new DataTypeDataOnDemand();
		DataType dataType = dataTypeDod.getRandomDataType();
		dataType.persist();

		DefinitionDataOnDemand definitionDod = new DefinitionDataOnDemand();
		Definition def = definitionDod.getRandomDefinition();
		def.setDataType(dataType);

		CategoryDataOnDemand categoryDod = new CategoryDataOnDemand();
		Category c1 = categoryDod.getNewTransientCategory(0);
		Category c2 = categoryDod.getNewTransientCategory(1);

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
