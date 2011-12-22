package com.sfs.metahive.model;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.persistence.Column;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.sfs.metahive.web.model.RecordFilter;

/**
 * The Class Record.
 */
@RooJavaBean
@RooToString
@RooEntity
public class Record {

    /** The logger. */
    private static Logger logger = Logger.getLogger(Record.class);
	
	/** The record id. */
	@NotNull
	@Size(min = 1, max = 255)
	@Column(unique = true)
	private String recordId;
	
	/** The secondary record count. */
	private int secondaryRecordCount;
	
	/** The secondary record array. */
	private String secondaryRecords;
	
	/** The tertiary record count. */
	private int tertiaryRecordCount;
	
	/** The tertiary record array. */
	private String tertiaryRecords;
	
	
	/**
	 * Contains the supplied secondary record.
	 *
	 * @param record the record
	 * @return true, if successful
	 */
	public boolean containsSecondaryRecord(final String record) {		
		return buildRecordSet(secondaryRecords).contains(record);
	}
	
	/**
	 * Adds the secondary record.
	 *
	 * @param record the record
	 */
	public void addSecondaryRecord(final String record) {
		if (StringUtils.isNotBlank(record)) {
			TreeSet<String> records = buildRecordSet(secondaryRecords);
			if (!records.contains(record)) {
				records.add(record);
				secondaryRecordCount = records.size();
				secondaryRecords = buildRecordString(records);
			}
		}
	}

	/**
	 * Contains the supplied tertiary record.
	 *
	 * @param record the record
	 * @return true, if successful
	 */
	public boolean containsTertiaryRecord(final String record) {		
		return buildRecordSet(tertiaryRecords).contains(record);
	}
	
	/**
	 * Adds the tertiary record.
	 *
	 * @param record the record
	 */
	public void addTertiaryRecord(final String record) {
		if (StringUtils.isNotBlank(record)) {
			TreeSet<String> records = buildRecordSet(tertiaryRecords);
			if (!records.contains(record)) {
				records.add(record);
				tertiaryRecordCount = records.size();
				tertiaryRecords = buildRecordString(records);
			}
		}
	}
		
	/**
	 * Gets the secondary record set.
	 *
	 * @return the secondary record set
	 */
	public TreeSet<String> getSecondaryRecordSet() {
		return buildRecordSet(this.secondaryRecords);	
	}
	
	/**
	 * Gets the tertiary record set.
	 *
	 * @return the tertiary record set
	 */
	public TreeSet<String> getTertiaryRecordSet() {
		return buildRecordSet(this.tertiaryRecords);	
	}
	
	/**
	 * Perform a regular expression match.
	 *
	 * @param record the record
	 * @param regEx the reg ex
	 * @return the string
	 */
	public static Record build(final String recordId,
			final MetahivePreferences prefs) {
		
		Record record = new Record();
		
		if (StringUtils.isNotBlank(recordId)) {
			
			if (prefs != null && StringUtils.isNotBlank(prefs.getPrimaryRecordRegex())) {
				
				String primaryRecordId = regex(recordId, prefs.getPrimaryRecordRegex());

				record = findRecordByRecordIdEquals(primaryRecordId);
				if (record == null) {
					record = new Record();
				}
							
				if (StringUtils.isBlank(record.getRecordId())) {
					record.setRecordId(primaryRecordId);
				}
						
				if (StringUtils.isNotBlank(prefs.getSecondaryRecordRegex())) {
					record.addSecondaryRecord(regex(recordId, 
							prefs.getSecondaryRecordRegex()));
				}
				
				if (StringUtils.isNotBlank(prefs.getTertiaryRecordRegex())) {
					record.addTertiaryRecord(regex(recordId, 
							prefs.getTertiaryRecordRegex()));
				}
							
			} else {
				// Preferences or primary regex not supplied, load the unparsed record
				record = findRecordByRecordIdEquals(recordId);
				if (record == null) {
					record = new Record();
				}
			}
		}
		
		if (StringUtils.isBlank(record.getRecordId())) {
			record.setRecordId(recordId);
		}
		
		return record;
	}
	
	/**
	 * Parses the primary record id.
	 *
	 * @param recordId the record id
	 * @param prefs the prefs
	 * @return the string
	 */
	public static String parsePrimaryRecordId(final String recordId,
			final MetahivePreferences prefs) {
		
		String primaryRecordId = "";
		
		if (StringUtils.isNotBlank(recordId) && prefs != null 
				&& StringUtils.isNotBlank(prefs.getPrimaryRecordRegex())) {
			primaryRecordId = regex(recordId, prefs.getPrimaryRecordRegex());
		}
		return primaryRecordId;
	}
	
	/**
	 * Parses the secondary record id.
	 *
	 * @param recordId the record id
	 * @param prefs the prefs
	 * @return the string
	 */
	public static String parseSecondaryRecordId(final String recordId,
			final MetahivePreferences prefs) {
		
		String secondaryRecordId = "";
		
		if (StringUtils.isNotBlank(recordId) && prefs != null 
				&& StringUtils.isNotBlank(prefs.getSecondaryRecordRegex())) {
			secondaryRecordId = regex(recordId, prefs.getSecondaryRecordRegex());
		}
		return secondaryRecordId;
	}
	
	/**
	 * Parses the tertiary record id.
	 *
	 * @param recordId the record id
	 * @param prefs the prefs
	 * @return the string
	 */
	public static String parseTertiaryRecordId(final String recordId,
			final MetahivePreferences prefs) {
		
		String tertiaryRecordId = "";
		
		if (StringUtils.isNotBlank(recordId) && prefs != null 
				&& StringUtils.isNotBlank(prefs.getTertiaryRecordRegex())) {
			tertiaryRecordId = regex(recordId, prefs.getTertiaryRecordRegex());
		}
		return tertiaryRecordId;
	}	
    
	/**
	 * Find the record going by the supplied record id.
	 *
	 * @param recordId the recordId
	 * @return the record
	 */
	public static Record findRecordByRecordIdEquals(String recordId) {
		
		Record record = null;
		
        if (recordId == null || recordId.length() == 0) {
        	throw new IllegalArgumentException("The recordId argument is required");
        }
        
        TypedQuery<Record> q = entityManager().createQuery(
        		"SELECT r FROM Record AS r WHERE LOWER(r.recordId) = LOWER(:recordId)", 
        		Record.class);
        q.setParameter("recordId", recordId);
        
        List<Record> records = q.getResultList();
        
        if (records != null && records.size() > 0) {
        	record = records.get(0);
        }        
        return record;
    }
	
	/**
	 * Find all of the records.
	 * 
	 * @return an ordered list of records
	 */
	public static List<Record> findAllRecords() {
        return entityManager().createQuery(
        		"SELECT r FROM Record r ORDER BY r.recordId ASC", Record.class)
        		.getResultList();
    }

    
    /**
     * Find record entries.
     *
     * @param filter the record filter
     * @param firstResult the first result
     * @param maxResults the max results
     * @return the list
     */
    public static List<Record> findRecordEntries(final RecordFilter filter,
    		final int firstResult, final int maxResults) {
    	
    	StringBuffer sql = new StringBuffer("SELECT r FROM Record r");    	
    	sql.append(buildWhere(filter));
    	sql.append(" ORDER BY r.recordId ASC");
    	
    	TypedQuery<Record> q = entityManager().createQuery(
        		sql.toString(), Record.class)
        		.setFirstResult(firstResult).setMaxResults(maxResults);
    	
    	HashMap<String, String> variables = buildVariables(filter);
    	for (String variable : variables.keySet()) {
    		q.setParameter(variable, variables.get(variable));
    	}
    	
    	return q.getResultList();
    }

    /**
     * Count the records.
     *
     * @param filter the filter
     * @return the long
     */
    public static long countRecords(final RecordFilter filter) {
    	
    	StringBuffer sql = new StringBuffer("SELECT COUNT(r) FROM Record r");    	
    	sql.append(buildWhere(filter));
    	
        TypedQuery<Long> q = entityManager().createQuery(sql.toString(), Long.class);

    	HashMap<String, String> variables = buildVariables(filter);
    	for (String variable : variables.keySet()) {
    		q.setParameter(variable, variables.get(variable));
    	}
        
        return q.getSingleResult();
    }
    
    
    /**
     * Builds the where statement.
     *
     * @param filter the filter
     * @return the string
     */
    private static String buildWhere(final RecordFilter filter) {
    	StringBuffer where = new StringBuffer();
    	
    	if (StringUtils.isNotBlank(filter.getRecordId())) {
    		where.append("LOWER(r.recordId) LIKE LOWER(:recordId)");		
    	}
    	
    	if (where.length() > 0) {
    		where.insert(0, " WHERE ");
    	}
    	return where.toString();
    }
    
    /**
     * Builds the variables for the where statement.
     *
     * @param filter the filter
     * @return the hash map
     */
    private static HashMap<String, String> buildVariables(final RecordFilter filter) {
    	
    	HashMap<String, String> variables = new HashMap<String, String>();

    	if (StringUtils.isNotBlank(filter.getRecordId())) {
    		variables.put("recordId", filter.getRecordId());    		
    	}
    	
    	return variables;
    }
		
	/**
	 * Builds the record string from the supplied collection.
	 *
	 * @param records the new records
	 */
	private String buildRecordString(final TreeSet<String> records) {
		StringBuffer recordString = new StringBuffer();
		if (records != null) {
			for (String record : records) {
				if (recordString.length() > 0) {
					recordString.append(",");
				}
				recordString.append("\"");
				recordString.append(record);
				recordString.append("\"");
			}
		}
		return recordString.toString();
	}
	
	/**
	 * Builds the record array from the supplied record string.
	 *
	 * @param recordString the record string
	 * @return the records
	 */
	private TreeSet<String> buildRecordSet(final String recordString) {
				
		TreeSet<String> records = new TreeSet<String>();
		
		if (StringUtils.isNotBlank(recordString)) {
			StringTokenizer st = new StringTokenizer(recordString,",");
			while (st.hasMoreTokens()) {
				String record = st.nextToken();
				if (StringUtils.startsWith(record, "\"")) {
					record = StringUtils.substring(record, 1);
				}
				if (StringUtils.endsWith(record, "\"")) {
					record = StringUtils.substring(record, 0, record.length() - 1);
				}
				if (!records.contains(record)) {
					records.add(record);					
				}
			}
		}	
		return records;
	}
	
	/**
	 * Apply the regular expression to the supplied record.
	 *
	 * @param record the record
	 * @param regEx the reg ex
	 * @return the string
	 */
	private static String regex(final String record, final String regEx) {
				
		String result = ""; 
		
		try {
	         Pattern p = Pattern.compile (regEx);
	         Matcher m = p.matcher(record);
	         if (m.find ()) {
	        	 result = m.group();
		     }
	    } catch (PatternSyntaxException pe) {
	         logger.error("Regex syntax error ('" + pe.getPattern() + "') "
	        		 + pe.getMessage());
	    }
		return result;
	}
	
}
