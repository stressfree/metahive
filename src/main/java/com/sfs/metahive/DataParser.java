package com.sfs.metahive;

import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * The Class DataParser.
 */
public class DataParser {
	
	/**
     * Parses the text data.
     *
     * @param text the text
     *
     * @return the tree map< integer, tree map< integer, string>>
     */
    public static String[][] parseTextData(final String text) {
    	
        TreeMap<Integer, TreeMap<Integer, String>> rowData =
            new TreeMap<Integer, TreeMap<Integer, String>>();

        // This counter holds the maximum number of columns provided
        int maxNumberOfTokens = 0;

        if (text != null) {
            StringTokenizer tokenizer = new StringTokenizer(text, "\n");

            int lineCounter = 0;

            while (tokenizer.hasMoreTokens()) {
                String line = tokenizer.nextToken();
                TreeMap<Integer, String> parsedLine = new TreeMap<Integer, String>();

                final StringTokenizer tabTokenizer = new StringTokenizer(line, "\t");
                if (tabTokenizer.countTokens() > 1) {
                    parsedLine = tokenizerToMap(tabTokenizer);
                } else {
                    final StringTokenizer commaTokenizer = new StringTokenizer(line, ",");
                    parsedLine = tokenizerToMap(commaTokenizer);
                }
                if (parsedLine.size() > maxNumberOfTokens) {
                    maxNumberOfTokens = parsedLine.size();
                }

                rowData.put(lineCounter, parsedLine);
                lineCounter++;
            }
        }
        
    	String[][]parsedData = new String[rowData.size()][];

        // Now cycle through all the parsed data
        // Ensure that each row has the same (max) number of tokens
        for (int rowIndex : rowData.keySet()) {
            TreeMap<Integer, String> parsedLine = rowData.get(rowIndex);

            // This map holds the final values
            TreeMap<Integer, String> columnTokens = new TreeMap<Integer, String>();

            for (int i = 0; i < maxNumberOfTokens; i++) {
                if (parsedLine.containsKey(i)) {
                    String value = parsedLine.get(i);
                    columnTokens.put(i, value);
                } else {
                    columnTokens.put(i, "");
                }
            }
            
            parsedData[rowIndex] = new String[columnTokens.size()];
            
            for (int columnIndex : columnTokens.keySet()) {
            	String value = columnTokens.get(columnIndex);          	
            	parsedData[rowIndex][columnIndex] = value;
            }
        }
        return parsedData;
    }

    /**
     * Tokenizer to map.
     *
     * @param tokenizer the tokenizer
     *
     * @return the tree map< integer, string>
     */
    private static TreeMap<Integer, String> tokenizerToMap(
            final StringTokenizer tokenizer) {

        TreeMap<Integer, String> parsedData = new TreeMap<Integer, String>();

        int lineCounter = 0;
        if (tokenizer != null) {
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();

                parsedData.put(lineCounter, token.trim());
                lineCounter++;
            }
        }
        return parsedData;
    }
	
}
