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

function definitionTypeChange() {
	var selected = this.attr('value');        	
	if (selected == 'STANDARD') {
		dojo.removeClass("definitionDataTypeSelector", "hidden");
    	dojo.removeClass("definitionKeyValueGeneratorSelector", "hidden");
	}
	if (selected == 'CALCULATED') {
		dojo.addClass("definitionDataTypeSelector", "hidden");
    	dojo.addClass("definitionKeyValueGeneratorSelector", "hidden");
	}
	if (selected == 'SUMMARY') {
		dojo.addClass("definitionDataTypeSelector", "hidden");
    	dojo.removeClass("definitionKeyValueGeneratorSelector", "hidden");
	}	
}

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