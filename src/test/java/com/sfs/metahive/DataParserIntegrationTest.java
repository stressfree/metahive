package com.sfs.metahive;


import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * The Class DataParserIntegrationTest.
 */
public class DataParserIntegrationTest {

	/**
	 * Test the parseTextData function.
	 */
	@Test
	public void parseTextData() {
		
        final int expRows = 3;
        final int expColumns = 4;

        StringBuffer text = new StringBuffer();
        text.append("colA, colB, colC, colD\n");
        text.append("1A,1B,1C,1D\n");
        text.append("2A,2B,2C,2D\n");

        String[][] result = DataParser.parseTextData(text.toString());

        assertEquals(expRows, result.length);

        for (int rowNum = 0; rowNum < result.length; rowNum++) {
            assertEquals(expColumns, result[rowNum].length);
        }
	}
	
}
