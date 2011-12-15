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