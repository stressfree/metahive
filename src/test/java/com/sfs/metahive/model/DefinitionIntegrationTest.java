package com.sfs.metahive.model;

import java.util.List;

import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class DefinitionIntegrationTest.
 */
@RooIntegrationTest(entity = Definition.class)
public class DefinitionIntegrationTest {

	/** The category. */
	Category category;
	
	/** The record type. */
	Applicability recordType;
	
	/**
	 * Creates the objects.
	 */
	@Before
    public void createObjects() {
		CategoryDataOnDemand categoryDod = new CategoryDataOnDemand();
				
		category = categoryDod.getRandomCategory();
		category.persist();

    }
	
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
		def.setKeyValueGenerator(KeyValueGenerator.NEWEST);
		def.setKeyValueAccess(UserRole.ANONYMOUS);
		def.setCategory(category);
		def.setApplicability(Applicability.RECORD_PRIMARY);
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
		def.setKeyValueGenerator(KeyValueGenerator.NEWEST);
		def.setKeyValueAccess(UserRole.ANONYMOUS);
		def.setCategory(category);
		def.setApplicability(Applicability.RECORD_PRIMARY);

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

		def.setCategory(category);
		def.setApplicability(Applicability.RECORD_PRIMARY);
		def.setDataType(DataType.TYPE_STRING);
		def.setKeyValueGenerator(KeyValueGenerator.NEWEST);
		def.setKeyValueAccess(UserRole.ANONYMOUS);

		def.flush();
		def.clear();

		Assert.assertEquals(Definition.findDefinition(def.getId())
				.getDataType(), DataType.TYPE_STRING);

	}
}
