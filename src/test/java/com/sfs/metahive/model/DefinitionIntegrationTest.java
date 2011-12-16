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
	RecordType recordType;
	
	/**
	 * Creates the objects.
	 */
	@Before
    public void createObjects() {
		CategoryDataOnDemand categoryDod = new CategoryDataOnDemand();
		RecordTypeDataOnDemand recordTypeDod = new RecordTypeDataOnDemand();
				
		category = categoryDod.getRandomCategory();
		category.persist();
		
		recordType = recordTypeDod.getRandomRecordType();
		recordType.persist();
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
		def.setCategory(category);
		def.setRecordType(recordType);
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
		def.setCategory(category);
		def.setRecordType(recordType);

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
		def.setRecordType(recordType);
		def.setDataType(DataType.TYPE_STRING);

		def.flush();
		def.clear();

		Assert.assertEquals(Definition.findDefinition(def.getId())
				.getDataType(), DataType.TYPE_STRING);

	}
}
