package com.sfs.metahive.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
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
		
	/** The key values (specific to the list of requested values). */
	@Transient
	private Map<String, KeyValueCollection> keyValueMap;
	
	/** The show all definitions flag. */
	@Transient
	private boolean showAllDefinitions;
	
	
	/**
	 * A helper function to get the key value collections as a list.
	 *
	 * @return the first key value collection
	 */
	public List<KeyValueCollection> getKeyValueCollection() {
		
		List<KeyValueCollection> keyValueCollections = 
				new ArrayList<KeyValueCollection>();
		
		if (this.getKeyValueMap() != null && this.getKeyValueMap().size() > 0) {
			for (String key : this.getKeyValueMap().keySet()) {
				KeyValueCollection keyValueCollection = this.getKeyValueMap().get(key);
				keyValueCollections.add(keyValueCollection);
			}
		}
		return keyValueCollections;
	}
	
	/**
	 * A helper function to get the first key value.
	 *
	 * @return the key value
	 */
	public KeyValueCollection getFirstKeyValueCollection() {
		KeyValueCollection keyValueCollection = new KeyValueCollection();
		
		if (this.getKeyValueMap() != null && this.getKeyValueMap().size() > 0) {
			String firstKey = this.getKeyValueMap().keySet().iterator().next();
			keyValueCollection = this.getKeyValueMap().get(firstKey);
		}
		return keyValueCollection;
	}
	
	/**
	 * A helper function to get the key value map excluding the first KeyValueCollection.
	 *
	 * @return the remaining key value map
	 */
	public List<KeyValueCollection> getRemainingKeyValueMap() {
		List<KeyValueCollection> remainingMap = new ArrayList<KeyValueCollection>();
		
		if (this.getKeyValueMap() != null && this.getKeyValueMap().size() > 1) {
			int x = 0;
			for (String key : this.getKeyValueMap().keySet()) {
				if (x != 0) {
					remainingMap.add(this.getKeyValueMap().get(key));
				}
				x++;
			}
		}
		return remainingMap;
	}
	
	/**
	 * Gets the primary key value collection.
	 *
	 * @return the primary key value collection
	 */
	public Map<String, KeyValueCategories> getPrimaryKeyValueCollection() {
				
		return buildMap(Definition.groupDefinitions(
				Definition.findTopLevelDefinitions(Applicability.RECORD_PRIMARY)), 
				Applicability.RECORD_PRIMARY);
	}
	
	/**
	 * Gets the secondary key value collection.
	 *
	 * @return the secondary key value collection
	 */
	public Map<String, KeyValueCategories> getSecondaryKeyValueCollection() {
				
		return buildMap(Definition.groupDefinitions(
				Definition.findTopLevelDefinitions(Applicability.RECORD_SECONDARY)), 
				Applicability.RECORD_SECONDARY);
	}
	
	/**
	 * Gets the tertiary key value collection.
	 *
	 * @return the tertiary key value collection
	 */
	public Map<String, KeyValueCategories> getTertiaryKeyValueCollection() {
				
		return buildMap(Definition.groupDefinitions(
				Definition.findTopLevelDefinitions(Applicability.RECORD_TERTIARY)), 
				Applicability.RECORD_TERTIARY);
	}
	
	/**
	 * Gets the key value map size.
	 *
	 * @return the key value map size
	 */
	public int getKeyValueMapSize() {
		int size = 0;
		if (this.getKeyValueMap() != null) {
			size = this.getKeyValueMap().size();
		}
		return size;
	}
	
	/**
	 * Load all the key values for this record.
	 *
	 * @param userRole the user role
	 * @param context the context
	 */
	public final void loadAllKeyValues(final UserRole userRole,
			final ApplicationContext context) {
		
		loadKeyValues(Definition.findAllDefinitions(), userRole, context);
	}
		
	/**
	 * Load the supplied key values for this record.
	 *
	 * @param definitions the definitions
	 * @param userRole the user role
	 * @param context the context
	 */
	public final void loadKeyValues(final List<Definition> definitions, 
			final UserRole userRole, final ApplicationContext context) {
		
		this.setKeyValues(
				KeyValue.findKeyValues(this, definitions),
				definitions, userRole, context);	
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
		
		if (StringUtils.isBlank(secondaryRecordId) 
				&& StringUtils.isNotBlank(prefs.getSecondaryRecordDefault())) {
			secondaryRecordId = prefs.getSecondaryRecordDefault();
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

		if (StringUtils.isBlank(tertiaryRecordId) 
				&& StringUtils.isNotBlank(prefs.getTertiaryRecordDefault())) {
			tertiaryRecordId = prefs.getTertiaryRecordDefault();
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
		
        if (StringUtils.isBlank(recordId)) {
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
    		final List<Definition> definitions, final UserRole userRole,
    		final ApplicationContext context,
    		final int firstResult, final int maxResults) {
    	
    	List<Record> records = new ArrayList<Record>();
    	
    	StringBuilder sql = new StringBuilder("SELECT r FROM Record r");    	
    	sql.append(buildWhere(filter));
    	sql.append(" ORDER BY r.recordId ASC");
    	
    	TypedQuery<Record> q = entityManager().createQuery(
        		sql.toString(), Record.class);
    	
    	if (maxResults > 0) {
    		q.setFirstResult(firstResult).setMaxResults(maxResults);
    	}
    	
    	HashMap<String, String> variables = buildVariables(filter);
    	for (String variable : variables.keySet()) {
    		q.setParameter(variable, variables.get(variable));
    	}
    	
    	for (Record record : q.getResultList()) {
    		record.setKeyValues(
    				KeyValue.findKeyValues(record, definitions),
    				definitions, userRole, context);
    		records.add(record);
    	}    	
    	return records;
    }

    /**
     * Count the records.
     *
     * @param filter the filter
     * @return the long
     */
    public static long countRecords(final RecordFilter filter) {
    	
    	StringBuilder sql = new StringBuilder("SELECT COUNT(r) FROM Record r");    	
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
    	StringBuilder where = new StringBuilder();
    	
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
	 * Builds the key values map based on the list of key values and definitions.
	 *
	 * @param keyValues the key values
	 * @param definitions the definitions
	 * @param userRole the user role
	 * @param context the context
	 */
	private void setKeyValues(final List<KeyValue> keyValues, 
			final List<Definition> definitions, 
			final UserRole userRole, final ApplicationContext context) {
		
		Map<String, KeyValueCollection> map = 
				new TreeMap<String, KeyValueCollection>();
		
		for (KeyValue keyValue : keyValues) {
			String id = keyValue.getPrimaryRecordId() + "_" 
					+ keyValue.getSecondaryRecordId() + "_" 
					+ keyValue.getTertiaryRecordId();
			
			keyValue.setUserRole(userRole);
			keyValue.setContext(context);
			
			KeyValueCollection keyValueCollection = new KeyValueCollection();
			
			if (map.containsKey(id)) {
				// Get the existing row of data
				keyValueCollection = map.get(id);
			} else {
				// Pre-populate the row with the definition names
				Map<String, KeyValue> values = new TreeMap<String, KeyValue>();				
				for (Definition definition : definitions) {
					KeyValue kv = new KeyValue();
					kv.setDefinition(definition);
					kv.setUserRole(userRole);
					kv.setContext(context);
					
					values.put(definition.getName(), kv);
				}
				keyValueCollection.setId(keyValue.getRecord().getId());
				keyValueCollection.setRecordId(keyValue.getPrimaryRecordId());
				keyValueCollection.setSecondaryRecordId(keyValue.getSecondaryRecordId());
				keyValueCollection.setTertiaryRecordId(keyValue.getTertiaryRecordId());
				keyValueCollection.setKeyValueMap(values);
			}
			
			keyValueCollection.getKeyValueMap().put(
					keyValue.getDefinition().getName(), keyValue);
			
			map.put(id, keyValueCollection);
		}
		this.keyValueMap = map;
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
	         Pattern p = Pattern.compile(regEx);
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
	
	
	/**
	 * Builds the key value category map.
	 *
	 * @param definitionMap the definition map
	 * @param applicability the applicability
	 * @return the map
	 */
	private Map<String, KeyValueCategories> buildMap(
			final Map<String, List<Definition>> definitionMap, 
			final Applicability applicability) {
		
		Map<String, KeyValueCategories> kvCategoryRecords = 
				new TreeMap<String, KeyValueCategories>();
		
		Map<Long, String> categoryMap = new TreeMap<Long, String>();
		
		for (String key : definitionMap.keySet()) {
			List<Definition> definitions = definitionMap.get(key);
			for (Definition definition : definitions) {
				categoryMap.put(definition.getId(), key);
			}
		}
				
		for (KeyValueCollection kvCollection : this.getKeyValueCollection()) {
						
			String id = kvCollection.getRecordId();

			if (applicability == Applicability.RECORD_SECONDARY) {
				id = kvCollection.getSecondaryRecordId();
			}
			if (applicability == Applicability.RECORD_TERTIARY) {
				id = kvCollection.getTertiaryRecordId();
			}
			
			if (StringUtils.isNotBlank(id)) {
				KeyValueCategories kvCategories = new KeyValueCategories();
				kvCategories.setId(id);
			
				if (kvCategoryRecords.containsKey(id)) {
					kvCategories = kvCategoryRecords.get(id);
				}
				
				for (String name : kvCollection.getKeyValueMap().keySet()) {
					
					KeyValue keyValue = kvCollection.getKeyValueMap().get(name);
					
					Long defId = keyValue.getDefinition().getId();
					String defName = keyValue.getDefinition().getName();
					boolean summarisedKeyValue = false;
					
					if (keyValue.getDefinition().getSummaryDefinition() != null) {
						defId = keyValue.getDefinition().getSummaryDefinition().getId();
						defName = keyValue.getDefinition().getSummaryDefinition()
								.getName();
						summarisedKeyValue = true;
					}
										
					if (categoryMap.containsKey(defId)) {

						String category = categoryMap.get(defId);
						
						Map<KeyValueCategory, KeyValueSet> ob = getKeyValueCategory(
								defId, defName, kvCategories, category);
						
						KeyValueCategory kvCategory = ob.keySet().iterator().next();
						KeyValueSet kvSet = ob.values().iterator().next();
						
						if (!summarisedKeyValue) {
							kvSet.addKeyValue(keyValue, this.showAllDefinitions);
						} else {
							kvSet.addChildKeyValue(keyValue, this.showAllDefinitions);
						}
						
						if (this.showAllDefinitions
								|| kvSet.getKeyValueCount() > 0 
								|| kvSet.getChildKeyValueSetCount() > 0) {
							kvCategory.getKeyValueSets().put(defName, kvSet);						
							kvCategories.getCategories().put(category, kvCategory);
						}
					}
				}
				kvCategoryRecords.put(id, kvCategories);
			}
		}

		return kvCategoryRecords;
	}
	
	/**
	 * Gets the key value category.
	 *
	 * @param id the id
	 * @param name the name
	 * @param keyValueCategories the key value categories
	 * @param category the category name
	 * @return the key value category and key value set map
	 */
	private static final Map<KeyValueCategory, KeyValueSet> getKeyValueCategory(
			final Long id, final String name, 
			final KeyValueCategories keyValueCategories,
			final String category) {
		

		KeyValueCategory keyValueCategory = new KeyValueCategory();
		keyValueCategory.setName(category);
		
		if (keyValueCategories.getCategories().containsKey(category)) {
			keyValueCategory = keyValueCategories
					.getCategories().get(category);
		}
		
		KeyValueSet keyValueSet = new KeyValueSet();
		keyValueSet.setId(id);
		keyValueSet.setName(name);
		
		if (keyValueCategory.getKeyValueSets().containsKey(name)) {
			keyValueSet = keyValueCategory.getKeyValueSets().get(name);
		}
		
		Map<KeyValueCategory, KeyValueSet> result = 
				new HashMap<KeyValueCategory, KeyValueSet>();
		result.put(keyValueCategory, keyValueSet);
		
		return result;
	}
	
}
