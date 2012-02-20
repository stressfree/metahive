dojo.require("dojo.parser");

function loadGrid(dataGrid, datasource) {
	dojo.xhrGet({
		url : datasource,
		handleAs : "json",
		load : function(inputJson, ioArgs) {
			dataGrid.setStore(new dojo.data.ItemFileReadStore({
				data : {
					items : inputJson
				}
			}));
		},
		error : function(error) {
			console.log("loading of grid data failed. Exception...", error);
		}
	});
}

function formatDate(formattedCreated) {
	var d = dojo.date.locale.parse(formattedCreated, {
		datePattern : "yyyy-MM-dd HH:mm:ss",
		selector : "date"
	});
	return dojo.date.locale.format(d, {
		datePattern : "dd/MM/yyyy",
		timePattern : "'at' h:mm a"
	});
}

function displayFlashMessage() {
	dojo.style("flashMessage", "display", "none");
	var wipeArgs = {
		node : "flashMessage"
	};
	dojo.fx.wipeIn(wipeArgs).play();
	setTimeout(function() {
		dojo.fx.wipeOut(wipeArgs).play();
	}, 7000);
}


// Definition functions
function definitionDataTypeChange() {
	var selected = keyValueGens[this.attr('value')];
	var nids = new Array();
	for (var i = 0; i < selected.length; i++) {
	    option = {value: selected[i].key, label: selected[i].message};
		nids.push(option);
	}
	var keyValueSelect = dijit.byId('_keyValueGenerator_id');
	keyValueSelect.removeOption(keyValueSelect.getOptions());
	keyValueSelect.addOption(nids);	
}


function removeElement(e) {    	
	node = dojo.query(e.target).closest('li')[0];
	node.parentNode.removeChild(node);
}

function definitionTypeChange() {
	var selected = this.attr('value');
	var newUrl = jsonUrl + "&type=STANDARD";
	
	if (selected == 'STANDARD') {
    	dojo.addClass("relatedDefinitionsSelector", "hidden");
    	dojo.addClass("definitionCalculationField", "hidden");
    	dojo.removeClass("definitionKeyValueGeneratorSelector", "hidden");
	}
	if (selected == 'CALCULATED') {
    	dojo.addClass("definitionKeyValueGeneratorSelector", "hidden");
    	dojo.removeClass("relatedDefinitionsSelector", "hidden");
    	dojo.removeClass("definitionCalculationField", "hidden");
    	newUrl = jsonUrl + "&type=CALCULATED";
	}
	if (selected == 'SUMMARY') {
    	dojo.addClass("definitionCalculationField", "hidden");
    	dojo.removeClass("definitionKeyValueGeneratorSelector", "hidden");
    	dojo.removeClass("relatedDefinitionsSelector", "hidden");
    	newUrl = jsonUrl + "&type=SUMMARY";
	}    	
	buildFilteringSelect(newUrl);    			
	dojo.query('#relatedDefinitionsList').empty(); 
}

function buildFilteringSelect(jsonUrl) {
	
	if (dijit.byId("relatedDefinitionSelector_id")) {
        dijit.byId("relatedDefinitionSelector_id").destroy();
    }
	dojo.query('#relatedDefinitionsSelector label').forEach(function(node, index, arr) {
		dojo.place(dojo.create('div', { id: 'relatedDefinitionSelectorDiv' }), node, 'after');
	});
    
    new dijit.form.FilteringSelect({
        id: "relatedDefinitionSelector_id",
        name: "relatedDefinitionSelector",
        store: new dojo.data.ItemFileWriteStore({ url: jsonUrl })
	}, "relatedDefinitionSelectorDiv");
    
    dijit.byId('relatedDefinitionSelector_id').required = false;    	
}

function addRelatedDefinition() {
	var type = dijit.byId('_definitionType_id').get('value');
	var definitionWidget = dijit.byId('relatedDefinitionSelector_id');
	var selected = definitionWidget.get('value');
	var name = definitionWidget.attr('displayedValue');
	
	if (selected != '' && name != '') {
		var innerHtml = "<a class='removeItem'>-</a> " + name;        		
		if (type == 'CALCULATED') {
			innerHtml += " <span class='variable'>D";
			innerHtml += selected + "</span>";
		}        		
		innerHtml += "<input type='hidden' name='relatedDefinitions' value='" 
				+ selected + "' />";
	
		dojo.query(dojo.create('li', { innerHTML: innerHtml }))
			.forEach(function(node, index, arr) {
				dojo.query('> a', node).onclick(function(e){ removeElement(e); });
			}).place("#relatedDefinitionsList");
	
		var storeData = definitionWidget.store;
		
		storeData.fetch({ query: { id: selected }, onComplete: function (items, request) {
			for (var i = 0; i < items.length; i++) {
				storeData.deleteItem(items[i]);
	    	}
		}});

		definitionWidget.set('value', '');
		definitionWidget.set('displayedValue', '');
	}
}

function testCalculation(url) {
	var parameter = "calculation=" + encodeURIComponent(dijit.byId('_calculation_id').get("value"));
	dojo.xhrPost({
	    url: url,
	    postData: parameter,
	    handleAs: "text",
	    load: function(data){
	        dojo.byId("testCalculationResult").innerHTML = data;
	    }
	});
}