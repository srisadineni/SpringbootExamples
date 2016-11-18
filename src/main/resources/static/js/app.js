$(document).ready(function() {
	tiggerAutoComplete();
	

	$("#location").geocomplete({
		details : ".geo-details",
		detailsAttribute : "data-geo",
		types : [ "geocode", "establishment" ],
	});

	
	
});



function tiggerAutoComplete() {
	$("#cityStateZipString").geocomplete({
		
		details : ".search-geo-details",
		detailsAttribute : "data-geo",
	});
}

