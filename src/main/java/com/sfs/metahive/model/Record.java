package com.sfs.metahive.model;

import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * The Class Record.
 */
@RooJavaBean
@RooToString
@RooEntity
public class Record {

	/** The record id. */
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
	 * Adds the secondary record.
	 *
	 * @param record the record
	 */
	public void addSecondaryRecord(final String record) {
		if (StringUtils.isNotBlank(record)) {
			TreeSet<String> records = buildRecordSet(secondaryRecords);
			if (records.contains(record)) {
				records.add(record);
				secondaryRecordCount = records.size();
				secondaryRecords = buildRecordString(records);
			}
		}
	}
	
	/**
	 * Adds the tertiary record.
	 *
	 * @param record the record
	 */
	public void addTertiaryRecord(final String record) {
		if (StringUtils.isNotBlank(record)) {
			TreeSet<String> records = buildRecordSet(tertiaryRecords);
			if (records.contains(record)) {
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
	
}
