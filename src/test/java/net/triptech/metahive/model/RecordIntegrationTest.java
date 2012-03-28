package net.triptech.metahive.model;

import javax.validation.ConstraintViolationException;

import junit.framework.Assert;

import net.triptech.metahive.model.Record;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class RecordIntegrationTest.
 */
@RooIntegrationTest(entity = Record.class)
public class RecordIntegrationTest {

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
    public void testInvalidRecord() {
        Record record = new Record();

        record.persist();
    }

    /**
     * The add and fetch record test.
     */
    @Test
    @Transactional
    public void addAndFetchRecord() {
        RecordOnDemand recordDod = new RecordOnDemand();
        Record record = recordDod.getRandomRecord();

        record.persist();

        record.flush();
        record.clear();

        Assert.assertNotNull(record.getId());

        Record record2 = Record.findRecord(record.getId());
        Assert.assertNotNull(record2);
        Assert.assertEquals(record.getRecordId(), record2.getRecordId());

    }
}
