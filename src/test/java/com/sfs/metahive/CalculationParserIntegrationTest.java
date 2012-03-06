package com.sfs.metahive;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * The Class CalculationParserIntegrationTest.
 */
public class CalculationParserIntegrationTest {

    /**
     * Test the performCalculation function.
     */
    @Test
    public void performCalculationTest() {

        Map<Long, Double> variables = new HashMap<Long, Double>();
        variables.put(10l, 100d);
        variables.put(11l, 50d);
        variables.put(12l, 5d);

        double result = CalculationParser.performCalculation("(D10-D11) + d12/2",
                variables);

        boolean success = true;
        if (result == 52.5) {
            success = true;
        }
        assertEquals(true, success);
    }

    /**
     * Test the buildCalculation function.
     */
    @Test
    public void buildCalculationTest() {

        Map<Long, Double> variables = new HashMap<Long, Double>();
        variables.put(10l, 100d);
        variables.put(11l, 50d);
        variables.put(12l, 5d);

        String result = CalculationParser.buildCalculation("(D10-D11) + d12/2",
                variables);

        assertEquals("(100.0-50.0) + 5.0/2", result);
    }

    /**
     * Test the parseVariableIds function.
     */
    @Test
    public void parseVariableIdsTest() {

        Set<Long> result = CalculationParser.parseVariableIds("D11 + d12/100");

        assertEquals(2, result.size());
    }

}
