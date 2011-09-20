package com.sfs.metahive.model;

import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class DataTypeIntegrationTest.
 */
@RooIntegrationTest(entity = DataType.class)
public class DataTypeIntegrationTest {

	/**
	 * Test marker method.
	 */
	@Test
	public void testMarkerMethod() {
	}

	/**
	 * Test invalid data type.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidDataType() {
		DataType dataType = new DataType();

		dataType.persist();
	}

	/**
	 * The add and fetch data type test.
	 */
	@Test
	@Transactional
	public void addAndFetchDataType() {
		DataTypeDataOnDemand dataTypeDod = new DataTypeDataOnDemand();
		DataType dataType = dataTypeDod.getRandomDataType();

		dataType.persist();

		dataType.flush();
		dataType.clear();

		Assert.assertNotNull(dataType.getId());

		DataType dataType2 = DataType.findDataType(dataType.getId());
		Assert.assertNotNull(dataType2);
		Assert.assertEquals(dataType.getName(), dataType2.getName());

	}
}
