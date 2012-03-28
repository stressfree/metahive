package net.triptech.metahive.model;

import java.util.List;

import static org.junit.Assert.assertEquals;

import net.triptech.metahive.model.DataGrid;

import org.junit.Test;

/**
 * The Class DataGridIntegrationTest.
 */
public class DataGridIntegrationTest {

    /**
     * Tests the getHeaderField function of the DataGrid class.
     */
    @Test
    public void testGetHeaderField() {

        DataGrid instance = new DataGrid();

        String[] expResults = { "Header 1", "Header 2", "Header 3" };

        instance.addHeaderField(expResults[0]);
        instance.addHeaderField(expResults[1]);
        instance.addHeaderField(expResults[2]);

        assertEquals(expResults[0], instance.getHeaderField(0));
        assertEquals(expResults[1], instance.getHeaderField(1));
        assertEquals(expResults[2], instance.getHeaderField(2));
    }

    /**
     * Tests the getHeaderFields function of the DataGrid class.
     */
    @Test
    public void testGetHeaderFields() {

        DataGrid instance = new DataGrid();

        String[] expResults = { "Header 1", "Header 2", "Header 3" };

        instance.addHeaderField(expResults[0]);
        instance.addHeaderField(expResults[1]);
        instance.addHeaderField(expResults[2]);

        List<String> results = instance.getHeaderFields();

        assertEquals(expResults.length, results.size());

        assertEquals(expResults[0], results.get(0));
        assertEquals(expResults[1], results.get(1));
        assertEquals(expResults[2], results.get(2));
    }


    /**
     * Tests the getRowFields function of the DataGrid class.
     */
    @Test
    public void testGetRowFields() {

        DataGrid instance = new DataGrid();

        String[] expResults = { "Test data 1", "Test data 2", "Test data 3" };

        instance.addRow(expResults);

        List<String> results = instance.getRowFields(0);

        assertEquals(expResults.length, results.size());

        assertEquals(expResults[0], results.get(0));
        assertEquals(expResults[1], results.get(1));
        assertEquals(expResults[2], results.get(2));
    }

}
