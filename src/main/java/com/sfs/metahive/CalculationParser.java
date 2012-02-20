package com.sfs.metahive;

import com.darius.Expr;
import com.darius.Parser;
import com.darius.SyntaxException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



/**
 * The Class Calculation.
 */
public class CalculationParser {

	/** The logger. */
	private static Logger logger = Logger.getLogger(CalculationParser.class);
	
	/** The regex. */
	private final static String regEx = "[dD]\\d?\\d?\\d?\\d?\\d?";
	
	
	/**
	 * Perform the calculation using the supplied calculation string and map of values.
	 *
	 * @param calculation the calculation
	 * @param values the values
	 * @return the double
	 */
	public static double performCalculation(String calculation, 
			Map<Long, Double> values) {
		
		double result = 0d;

		String parsedCalculation = buildCalculation(calculation, values);				
		
		if (StringUtils.isNotBlank(parsedCalculation)) {
			try {
				Expr expr = Parser.parse(parsedCalculation);
				result = expr.value();
			} catch (SyntaxException se) {
				logger.error("Error parsing calculation: " + se.getMessage());
			}
		}
		logger.info("Calculated result: " + result);
		
		return result;
	}
	
	/**
	 * Builds the calculation.
	 *
	 * @param calculation the calculation
	 * @param values the values
	 * @return the string
	 */
	public static String buildCalculation(String calculation, Map<Long, Double> values) {
		
		String parsedCalculation = "";
		
		logger.debug("Calculation: " + calculation);
		logger.debug("Values: " + values);
		
		if (StringUtils.isNotBlank(calculation) && values != null) {
			try {
		         Pattern p = Pattern.compile(regEx);
		         Matcher m = p.matcher(calculation);
		         StringBuffer sb = new StringBuffer();
		         logger.debug("Regular expression: " + regEx);
		         while (m.find ()) {
		        	 logger.info("Variable instance found: " + m.group());
		        	 try {
		        		 String text = m.group();
		        		 Long id = Long.parseLong(StringUtils.substring(text, 1));
		        		 logger.info("Variable id: " + id);
		        		 
		        		 if (values.containsKey(id)) {
		        			 logger.debug("Contains variable " + id);
		        			 double value = values.get(id);
		        			 logger.debug("Value: " + value);
		        			 text = String.valueOf(value);
		        		 }
		        		 logger.debug("Replacement text: " + text);
		        		 m.appendReplacement(sb, Matcher.quoteReplacement(text));
		        		 
		        	 } catch (NumberFormatException nfe) {
		        		 logger.error("Error parsing variable id");
		        	 }	        	 
			     }
		         m.appendTail(sb);
		         
		         parsedCalculation = sb.toString();
		         logger.info("Parsed calculation: " + parsedCalculation);
		         
		    } catch (PatternSyntaxException pe) {
		         logger.error("Regex syntax error ('" + pe.getPattern() + "') "
		        		 + pe.getMessage());
		    }
		}
		return parsedCalculation;
	}
	
	/**
	 * Marks up the calculation.
	 *
	 * @param calculation the calculation
	 * @return the string
	 */
	public static String maredUpCalculation(String calculation) {
		
		String markedUpCalculation = "";
		
		logger.debug("Calculation: " + calculation);
		
		if (StringUtils.isNotBlank(calculation)) {
			try {
		         Pattern p = Pattern.compile(regEx);
		         Matcher m = p.matcher(calculation);
		         StringBuffer sb = new StringBuffer();
		         logger.debug("Regular expression: " + regEx);
		         while (m.find ()) {
		        	 logger.info("Variable instance found: " + m.group());
		        	 try {
		        		 String text = "<span class=\"variable\">" 
		        				 + m.group().toUpperCase() + "</span>";
		        		 
		        		 m.appendReplacement(sb, Matcher.quoteReplacement(text));
		        		 
		        	 } catch (NumberFormatException nfe) {
		        		 logger.error("Error parsing variable id");
		        	 }	        	 
			     }
		         m.appendTail(sb);
		         
		         markedUpCalculation = sb.toString();
		         logger.info("Marked up calculation: " + markedUpCalculation);
		         
		    } catch (PatternSyntaxException pe) {
		         logger.error("Regex syntax error ('" + pe.getPattern() + "') "
		        		 + pe.getMessage());
		    }
		}
		return markedUpCalculation;
	}
	
	/**
	 * Parses the calculation to identify what variables are referenced in it.
	 *
	 * @param calculation the calculation
	 * @return the list
	 */
	public static Set<Long> parseVariableIds(final String calculation) {
		
		HashMap<Long, Long> variableIds = new HashMap<Long, Long>();
		
		logger.debug("Calculation: " + calculation);
		
		if (StringUtils.isNotBlank(calculation)) {
			try {
		         Pattern p = Pattern.compile(regEx);
		         Matcher m = p.matcher(calculation);
	        	 logger.debug("Regular expression: " + regEx);
		         while (m.find()) {
		        	 logger.info("Variable instance found: " + m.group());
		        	 try {
		        		 Long id = Long.parseLong(StringUtils.substring(m.group(), 1));
		        		 logger.debug("Variable id: " + id);
		        		 variableIds.put(id, id);
		        	 } catch (NumberFormatException nfe) {
		        		 logger.error("Error parsing variable id");
		        	 }		        	 
			     }
		    } catch (PatternSyntaxException pe) {
		         logger.error("Regex syntax error ('" + pe.getPattern() + "') "
		        		 + pe.getMessage());
		    }
		}
		
		logger.info(variableIds.keySet().size() + " ids found");
		return variableIds.keySet();
	}
}
