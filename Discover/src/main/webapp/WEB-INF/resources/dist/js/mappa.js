
$(document).ready(function() {
	initMap();
	$("#headerWeb #btnIntornoMe, #footerMobile #btnIntornoMe").addClass("section-active");
});

// Note: This example requires that you consent to location sharing when
// prompted by your browser. If you see the error "The Geolocation service
// failed.", it means you probably did not give permission for the browser to
// locate you.
var map, infoWindow  = new google.maps.InfoWindow;
var markersAttrazioni = [], infoWindowsMarker = [];
var geocoder =  new google.maps.Geocoder();

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
	    zoom: 14
	});
	
	// Try HTML5 geolocation.
	if (navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(function(position) {
	    var pos = {
	      lat: position.coords.latitude,
	      lng: position.coords.longitude
	    };
	
	    infoWindow.setPosition(pos);
	    infoWindow.setContent('Tu sei qui');
	    infoWindow.open(map);
	    map.setCenter(pos);
	  }, function() {
	    handleLocationError(true, infoWindow, map.getCenter());
	  });
	} else {
	  // Browser doesn't support Geolocation
	      handleLocationError(false, infoWindow, map.getCenter());
	}
	
	addMarkersAttrazioniToMap();
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
                          'Error: The Geolocation service failed.' :
    					  'Error: Your browser doesn\'t support geolocation.');
    infoWindow.open(map);
}

function initMapByAddress() {
	geocoder.geocode( { 'address': $('#localitaMappa').val()}, function(results, status) {
		if (status != google.maps.GeocoderStatus.OK) {
			return;
		}
		
		$("#latitudine").val(results[0].geometry.location.lat());
		$("#longitudine").val(results[0].geometry.location.lng());
		var var_location = new google.maps.LatLng(results[0].geometry.location.lat(),results[0].geometry.location.lng());
 
		var var_mapoptions = {
		  center: var_location,
		  zoom: 14
		};
		
		addMarkersAttrazioniToMap();
		
		map = new google.maps.Map(document.getElementById("map"),
			var_mapoptions);
		  
	    google.maps.event.addDomListener(map, "idle", function() {
		  var center = map.getCenter();
		  google.maps.event.trigger(map, "resize");
		  map.setCenter(center); 
	    });
	})
}

function addMarkersAttrazioniToMap() {
	$.ajax({
    	type: 'GET',
        url : '/discover/mappa/getMarkersAttrazioni',
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(markersAttrazioniJson) {
			for(var i=0; i<markersAttrazioniJson.length; i++) {
				var attrazione = markersAttrazioniJson[i];
				var marker = addMarker(attrazione);
				markersAttrazioni[i] = marker;
			}
        }
	});
}

function addMarker(attrazione) {
	var position = new google.maps.LatLng(attrazione.latitudine, attrazione.longitudine);
	var marker = new google.maps.Marker({
		id: 'marker'+attrazione.ID,
		position: position,
		formatted_address: attrazione.nome + " " + attrazione.localita,
		map: map,
		title:"Localizzazione attrazione",
		draggable: false
	});
	
	google.maps.event.addListener(marker,'click', function() {
		var contentString = marker.formatted_address + "<br>coordinate: " + marker.getPosition().toUrlValue(6) + "<br><a href='/discover/attrazione/"+attrazione.idAttrazione+"' >Visualizza dettagli</a>";
		infoWindow.setContent(contentString);
		infoWindow.open(map, marker);
	});
	 
	marker.setMap(map);	
	return marker;
}