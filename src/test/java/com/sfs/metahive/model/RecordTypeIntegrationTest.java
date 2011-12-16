package com.sfs.metahive.model;

import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class RecordTypeIntegrationTest.
 */
@RooIntegrationTest(entity = RecordType.class)
public class RecordTypeIntegrationTest {

	/**
	 * Test marker method.
	 */
	@Test
	public void testMarkerMethod() {
	}

	/**
	 * Test invalid record.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidRecordType() {
		RecordType recordType = new RecordType();

		recordType.persist();
	}

	/**
	 * The add and fetch record type test.
	 */
	@Test
	@Transactional
	public void addAndFetchRecordType() {
		RecordType recordType = new RecordType();
		recordType.setName("JUnit Test record type");

		recordType.persist();

		recordType.flush();
		recordType.clear();

		Assert.assertNotNull(recordType.getId());

		RecordType recordType2 = RecordType.findRecordType(recordType.getId());
		Assert.assertNotNull(recordType2);
		Assert.assertEquals(recordType.getName(), recordType2.getName());

	}
}
