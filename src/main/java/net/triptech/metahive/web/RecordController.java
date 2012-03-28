/*******************************************************************************
 * Copyright (c) 2012 David Harrison, Triptech Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     David Harrison, Triptech Ltd - initial API and implementation
 ******************************************************************************/
package net.triptech.metahive.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.triptech.metahive.FlashScope;
import net.triptech.metahive.messaging.JmsRecalculateRequest;
import net.triptech.metahive.model.DataGrid;
import net.triptech.metahive.model.Definition;
import net.triptech.metahive.model.KeyValue;
import net.triptech.metahive.model.KeyValueBoolean;
import net.triptech.metahive.model.KeyValueCollection;
import net.triptech.metahive.model.MetahivePreferences;
import net.triptech.metahive.model.Person;
import net.triptech.metahive.model.Record;
import net.triptech.metahive.model.SubmittedField;
import net.triptech.metahive.model.UserRole;
import net.triptech.metahive.web.model.CommentForm;
import net.triptech.metahive.web.model.FilterAction;
import net.triptech.metahive.web.model.FilterVector;
import net.triptech.metahive.web.model.KeyValueForm;
import net.triptech.metahive.web.model.KeyValueJson;
import net.triptech.metahive.web.model.RecordFilter;
import net.triptech.metahive.web.model.RecordForm;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import flexjson.JSONSerializer;


@RequestMapping("/records")
@Controller
public class RecordController extends BaseController {

    @Autowired
    private transient JmsTemplate keyValueGenerationTemplate;

    /** The default page size. */
    private final static int DEFAULT_PAGE_SIZE = 50;

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@Valid RecordForm record, BindingResult bindingResult,
            Model uiModel, HttpServletRequest request) {

        Person user = loadUser(request);

        if (user == null) {
            // A valid user is required
            FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
            return "redirect:/records";
        }

        if (StringUtils.isNotBlank(record.getRecordId())) {
            Record newRecord = record.newRecord(loadPreferences());
            newRecord.persist();
            newRecord.flush();
        }

        if (StringUtils.isNotBlank(record.getRecordIds())) {

            Collection<Record> records = record.parseRecords(loadPreferences());

            for (Record newRecord : records) {
                newRecord.persist();
                newRecord.flush();
            }
        }

        uiModel.asMap().clear();

        FlashScope.appendMessage(
                getMessage("metahive_create_complete", Record.class), request);

        return "redirect:/records";
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("record", new RecordForm());
        return "records/create";
    }

    @RequestMapping(params = "bulk", method = RequestMethod.GET)
    public String bulkForm(Model uiModel) {
        uiModel.addAttribute("record", new RecordForm());
        return "records/bulk";
    }

    @RequestMapping(value = "/advanced", method = RequestMethod.GET)
    public String advancedSearch(@RequestParam(value = "id", required = false) String id,
    		Model uiModel, HttpServletRequest request) {

        if (StringUtils.isNotBlank(id)) {
        	// Modify search action
        	Map<String, RecordFilter> searches = getSearches(request);
        	if (searches.containsKey(id)) {
                uiModel.addAttribute("filter", searches.get(id));
        	}

        	Collection<FilterAction> filterActions = new ArrayList<FilterAction>();
            for (FilterAction filterAction : FilterAction.values()) {
            	filterActions.add(filterAction);
            }
            uiModel.addAttribute("filteractions", filterActions);
        }

        uiModel.addAttribute("booleanOptions", populateBooleanValues());

        return "records/advanced";
    }

    @RequestMapping(value = "/advanced", method = RequestMethod.POST)
    public String advancedSearchProcess(
    		@RequestParam(value = "id", required = false) String id,
    		@RequestParam(value = "action", required = false) FilterAction action,
    		Model uiModel, HttpServletRequest request) {

    	RecordFilter filter = new RecordFilter();
    	filter.setEncoding(request.getCharacterEncoding());
    	filter.processSearchForm(request);

    	Map<String, RecordFilter> searches = getSearches(request);

    	if (StringUtils.isNotBlank(id) && searches.containsKey(id)) {
    		// This is a modify search operation - get the processed filter vector
    		FilterVector vector = null;

    		if (filter.getFilterVectors() != null
    				&& filter.getFilterVectors().size() > 0) {
    			vector = filter.getFilterVectors().get(0);

    			if (action != null) {
    				vector.setAction(action);
    			}
    		}
    		// Add the vector to the existing search if it is not null
    		filter = searches.get(id);
    		if (vector != null) {
    			filter.getFilterVectors().add(vector);
    		}
    	}

    	// Add the filter to the search array
    	searches.put(filter.getId(), filter);

    	request.getSession().setAttribute("searches", searches);

        return "redirect:/records?id=" + filter.getId();
    }

    /**
     * Show the record.
     *
     * @param id the id
     * @param uiModel the ui model
     * @return the string
     */
     @RequestMapping(value = "/{id}", method = RequestMethod.GET)
     public String show(@PathVariable("id") Long id, Model uiModel,
             @RequestParam(value = "show", required = false) Boolean show,
             @RequestParam(value = "expand", required = false) Boolean expand,
             HttpServletRequest request) {

        Person user = loadUser(request);
        UserRole role = UserRole.ANONYMOUS;

        boolean showAllDefinitions = false;
        boolean expandAllDefinitions = false;

        if (user != null) {
            role = user.getUserRole();
            showAllDefinitions = user.isShowAllDefinitions();
            expandAllDefinitions = user.isExpandAllDefinitions();
        }

        if (show != null) {
            showAllDefinitions = show;
        }
        if (expand != null) {
            expandAllDefinitions = expand;
        }

        if (user != null) {
            if (show != null) {
                user.setShowAllDefinitions(show);
            }
            if (expand != null) {
                user.setExpandAllDefinitions(expand);
            }
            if (show != null || expand != null) {
                user.merge();
            }
        }

        Record record = Record.findRecord(id);
        record.setShowAllDefinitions(showAllDefinitions);
        record.loadAllAggregatedKeyValues(role, this.getContext());

        CommentForm commentForm = new CommentForm();
        commentForm.setRecord(record);

        uiModel.addAttribute("record", record);
        uiModel.addAttribute("comment", commentForm);
        uiModel.addAttribute("show", showAllDefinitions);
        uiModel.addAttribute("expand", expandAllDefinitions);

        return "records/show";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_EDITOR','ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id, Model uiModel,
            HttpServletRequest request) {

        Person user = loadUser(request);

        if (user == null) {
            // A valid user is required
            FlashScope.appendMessage(getMessage("metahive_valid_user_required"), request);
            return "redirect:/records";
        }

        Record.findRecord(id).remove();
        uiModel.asMap().clear();

        FlashScope.appendMessage(
                getMessage("metahive_delete_complete", Record.class), request);

        return "redirect:/records";
    }

    /**
     * Show information on the key value.
     *
     * @param id the id
     * @param keyValueId the key value id
     * @param uiModel the ui model
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = "/keyvalue/{id}", method = RequestMethod.GET)
     public String keyvalueDetail(@PathVariable("id") Long id, Model uiModel,
             HttpServletRequest request) {

        KeyValue kv = KeyValue.findKeyValue(id);
        kv.setSubmittedFields(SubmittedField.findSubmittedFields(
                kv.getDefinition(), kv.getPrimaryRecordId(),
                kv.getSecondaryRecordId(), kv.getTertiaryRecordId()));

        kv.setContext(this.getContext());

        uiModel.addAttribute("keyValue", kv);

        return "records/keyvaluedetail";
    }

    /**
     * The key value as a json string.
     *
     * @param id the id
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = "/keyvalue/{id}",
            params = "json", method = RequestMethod.GET)
    public @ResponseBody String keyvalueJson(@PathVariable("id") Long id,
             HttpServletRequest request) {

        UserRole userRole = UserRole.ANONYMOUS;

        Person user = loadUser(request);
        if (user != null && user.getUserRole() != null) {
            userRole = user.getUserRole();
        }

        KeyValue kv = KeyValue.findKeyValue(id);
        KeyValueJson kvj = new KeyValueJson(kv, kv.getDefinition(), userRole);

        return new JSONSerializer().exclude("*.class").serialize(kvj);
    }

    @RequestMapping(value = "/keyvalue/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody String overrideKeyValue(@PathVariable("id") Long id,
            KeyValueForm kvForm, Model uiModel, HttpServletRequest request) {

        Person user = loadUser(request);

        if (user == null) {
            // A valid user is required
            return getMessage("metahive_valid_user_required");
        }

        List<KeyValue> updatedValues = KeyValue.updateRelevantKeyValues(id, kvForm, user);

        // Submit a recalculation JMS request for each updated key value
        for (KeyValue kv : updatedValues) {
            JmsRecalculateRequest req = new JmsRecalculateRequest(kv);
            keyValueGenerationTemplate.convertAndSend(req);
        }

        uiModel.asMap().clear();

        return getMessage("metahive_record_keyvaluedetail_success");
    }

    /**
     * List the records.
     *
     * @param id the id
     * @param recordId the record id
     * @param page the page
     * @param size the size
     * @param uiModel the ui model
     * @param request the http servlet request
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(
    		@RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "order", required = false) Long orderId,
            @RequestParam(value = "recordId", required = false) String recordId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            Model uiModel, HttpServletRequest request) {

        Person user = loadUser(request);

        List<Definition> definitions = new ArrayList<Definition>();
        if (user == null) {
            // Load the default definition list
            MetahivePreferences preferences = MetahivePreferences.load();
            definitions = preferences.getDefaultDefinitions();
        } else {
            if (user.getSearchDefinitions().size() == 0) {
                // Set the default definitions for the user
                MetahivePreferences preferences = MetahivePreferences.load();

                for (Definition def : preferences.getDefaultDefinitions()) {
                    definitions.add(def);
                }
                user.setSearchDefinitions(definitions);
                user.persist();
            } else {
                definitions = user.getSearchDefinitions();
            }
        }

        UserRole userRole = UserRole.ANONYMOUS;
        if (user != null && user.getUserRole() != null) {
            userRole = user.getUserRole();
        }


        RecordFilter filter = new RecordFilter();
        filter.setEncoding(request.getCharacterEncoding());


    	Map<String, RecordFilter> searches = getSearches(request);

        if (StringUtils.isNotBlank(id) && searches.containsKey(id)) {
        	filter = searches.get(id);
        }

        if (orderId != null) {
        	filter.setOrderId(orderId);
        }


        String defaultRecordFilter = getMessage("metahive_records_filter_id");

        if (StringUtils.isNotBlank(recordId)
        		&& !StringUtils.equalsIgnoreCase(recordId, defaultRecordFilter)) {
            filter.setRecordId(recordId);
        } else  {
            filter.setRecordId("");
        }

        searches.put(filter.getId(), filter);

    	request.getSession().setAttribute("searches", searches);


        int sizeNo = size == null ? DEFAULT_PAGE_SIZE : size.intValue();
        int pageNo = page == null ? 0 : page.intValue() - 1;

        uiModel.addAttribute("definitions", definitions);

        uiModel.addAttribute("records", Record.findRecordEntries(
                filter, definitions, userRole, this.getContext(),
                pageNo * sizeNo, sizeNo));

        float nrOfPages = (float) Record.countRecords(filter) / sizeNo;

        uiModel.addAttribute("resultCounts", resultCounts());
        uiModel.addAttribute("page", pageNo + 1);
        uiModel.addAttribute("size", sizeNo);
        uiModel.addAttribute("filter", filter);
        uiModel.addAttribute("maxPages",
                (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0)
                ? nrOfPages + 1 : nrOfPages));

        return "records/list";
    }


    @RequestMapping(value = "/search.xls", method = RequestMethod.POST)
    public ModelAndView recordsExport(
    		@RequestParam(value = "searchId", required = false) String id,
    		@RequestParam(value = "searchDefinitions", required = true) String[] defIds,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Person user = loadUser(request);

        UserRole userRole = UserRole.ANONYMOUS;
        if (user != null && user.getUserRole() != null) {
            userRole = user.getUserRole();
        }

        List<Definition> definitions = Definition.findDefinitionEntries(defIds);

        RecordFilter filter = new RecordFilter();

        if (StringUtils.isNotBlank(id)) {
        	Map<String, RecordFilter> searches = getSearches(request);

        	if (searches.containsKey(id)) {
        		filter = searches.get(id);
        	}
        }

        List<Record> records = Record.findRecordEntries(
                filter, definitions, userRole, this.getContext(), 1, 0);

        DataGrid dataGrid = buildDataGrid(records, definitions);

        return new ModelAndView("ExcelTemplateView", "dataGrid", dataGrid);
    }


    @RequestMapping(value = "/{id}/record.xls", method = RequestMethod.POST)
    public ModelAndView recordExport(@RequestParam(
            value = "searchDefinitions", required = true) String[] definitionIds,
            @PathVariable("id") Long id, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Person user = loadUser(request);

        UserRole userRole = UserRole.ANONYMOUS;
        if (user != null && user.getUserRole() != null) {
            userRole = user.getUserRole();
        }

        List<Definition> definitions = Definition.findDefinitionEntries(definitionIds);

        Record record = Record.findRecord(id);
        record.loadKeyValues(definitions, userRole, this.getContext());

        List<Record> records = new ArrayList<Record>();
        records.add(record);

        DataGrid dataGrid = buildDataGrid(records, definitions);

        return new ModelAndView("ExcelTemplateView", "dataGrid", dataGrid);
    }



    @ModelAttribute("user")
    public Person populateUser(HttpServletRequest request) {

        Person user = loadUser(request);

        if (user == null) {
            user = new Person();

            MetahivePreferences preferences = MetahivePreferences.load();
            user.setSearchDefinitions(preferences.getDefaultDefinitions());
        }
        return user;
    }

    @ModelAttribute("exportDefinitions")
    public Map<String, List<Definition>> populateExportDefinitions() {
        return Definition.groupDefinitions(Definition.findTopLevelDefinitions());
    }

    private Collection<KeyValueBoolean> populateBooleanValues() {
    	Collection<KeyValueBoolean> booleanValues = new ArrayList<KeyValueBoolean>();
        for (KeyValueBoolean kvBoolean : KeyValueBoolean.values()) {
            booleanValues.add(kvBoolean);
        }
        return booleanValues;
    }


    /**
     * Builds the data grid.
     *
     * @param records the records
     * @param definitions the definitions
     * @return the data grid
     */
    private DataGrid buildDataGrid(final List<Record> records,
            final List<Definition> definitions) {

        DataGrid dataGrid = new DataGrid();

        if (records.size() == 1) {
            dataGrid.setTitle("Record " + records.get(0).getRecordId());
        } else {
            dataGrid.setTitle("Record search results");
        }

        MetahivePreferences preferences = loadPreferences();

        dataGrid.addHeaderField(preferences.getPrimaryRecordName());

        if (StringUtils.isNotBlank(preferences.getSecondaryRecordName())) {
            dataGrid.addHeaderField(preferences.getSecondaryRecordName());
        }
        if (StringUtils.isNotBlank(preferences.getTertiaryRecordName())) {
            dataGrid.addHeaderField(preferences.getTertiaryRecordName());
        }

        for (Definition definition : definitions) {
            String field = definition.getName();
            if (StringUtils.isNotBlank(definition.getDescription().getUnitOfMeasure())) {
                field += " (" + definition.getDescription().getUnitOfMeasure() + ")";
            }
            dataGrid.addHeaderField(field);
        }

        for (Record record : records) {

            if (record.getKeyValueCollection() != null
                    && record.getKeyValueCollection().size() > 0) {

                for (KeyValueCollection kvCollection : record.getKeyValueCollection()) {

                    List<String> rowData = new ArrayList<String>();

                    rowData.add(kvCollection.getRecordId());

                    if (StringUtils.isNotBlank(preferences.getSecondaryRecordName())) {
                        rowData.add(kvCollection.getSecondaryRecordId());
                    }
                    if (StringUtils.isNotBlank(preferences.getTertiaryRecordName())) {
                        rowData.add(kvCollection.getTertiaryRecordId());
                    }

                    for (String name : kvCollection.getKeyValueMap().keySet()) {
                        KeyValue kv = kvCollection.getKeyValueMap().get(name);

                        rowData.add(kv.getValueSansUnits());
                    }
                    dataGrid.addRow(rowData);
                }
            } else {
                // No data found, enter a blank (no data) row
                List<String> rowData = new ArrayList<String>();

                rowData.add(record.getRecordId());

                if (StringUtils.isNotBlank(preferences.getSecondaryRecordName())) {
                    rowData.add("");
                }
                if (StringUtils.isNotBlank(preferences.getTertiaryRecordName())) {
                    rowData.add("");
                }

                String noData = getMessage(
                        "label_net_triptech_metahive_model_keyvalue_no_data");

                for (int x = 0; x < definitions.size(); x++) {
                    rowData.add(noData);
                }
                dataGrid.addRow(rowData);
            }
        }
        return dataGrid;
    }

    /**
     * Gets the search array from the session (if one exists).
     *
     * @param request the request
     * @return the search array
     */
    private Map<String, RecordFilter> getSearches(final HttpServletRequest request) {

    	Map<String, RecordFilter> searches = new HashMap<String, RecordFilter>();

    	if (request.getSession().getAttribute("searches") != null) {
    		Object objMap = request.getSession().getAttribute("searches");
    		if (objMap instanceof Map) {
    			Map<?, ?> map = (Map<?, ?>) objMap;
    			for (Object objId : map.keySet()) {
    				Object objRecordFilter = map.get(objId);
    				if (objRecordFilter instanceof RecordFilter) {
    					RecordFilter recordFilter = (RecordFilter) objRecordFilter;

    					// Test if it has expired (older than one hour)
    					long oldTime = recordFilter.getCreated().getTime();
    					long newTime = Calendar.getInstance().getTimeInMillis();

    					if ((newTime - oldTime) <= (3600 * 1000)) {
        					searches.put(recordFilter.getId(), recordFilter);
    					}
    				}
    			}
    		}
    	}
    	return searches;
    }
}
