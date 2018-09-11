
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
	
	    var position = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
		markerPosizioneUtente = new google.maps.Marker({
			id: 'posizioneUtente',
			position: position,
			icon: '/discover/resources/dist/img/markers/markerPosizioneUtente_40x40.png',
			formatted_address: "La tua posizione.",
			map: map,
			title:"Localizzazione attrazione",
			draggable: false
		});
		
		google.maps.event.addListener(markerPosizioneUtente,'click', function() {
			var contentString = "Questa è la tua posizione.";
			infoWindow.setContent(contentString);
			infoWindow.open(map, markerPosizioneUtente);
		});
    	
		markerPosizioneUtente.setMap(map);	
	    map.setCenter(pos);
	  }, function() {
	    handleLocationError(true, infoWindow, map.getCenter());
	  });
	} else {
	  // Browser doesn't support Geolocation
	      handleLocationError(false, infoWindow, map.getCenter());
	}
	
	var autocomplete = new google.maps.places.Autocomplete(document.getElementById("localitaMappa"));

    // Bind the map's bounds (viewport) property to the autocomplete object,
    // so that the autocomplete requests use the current map bounds for the
    // bounds option in the request.
    autocomplete.bindTo('bounds', map);

    // Set the data fields to return when the user selects a place.
    autocomplete.setFields(
        ['address_components', 'geometry', 'icon', 'name']);
	
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
		
		var var_location = new google.maps.LatLng(results[0].geometry.location.lat(),results[0].geometry.location.lng());
		
		var myStyles =[
		    {
		        featureType: "poi",
		        elementType: "labels",
		        stylers: [
		              { visibility: "off" }
		        ]
		    }
		];
		
		var var_mapoptions = {
		  center: var_location,
		  zoom: 14,
		  styles: myStyles
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
		icon: '/discover/resources/dist/img/markers/marker_red.png',
		formatted_address: attrazione.nome + " " + attrazione.localita,
		map: map,
		title:"Localizzazione attrazione",
		draggable: false
	});
	
	google.maps.event.addListener(marker,'click', function() {
		var contentString = "<div style='max-width: 300px'>"+
				marker.formatted_address + "<br><br>"+
				"<div class='col-md-6'>"+
					"<img src='"+attrazione.imagePath+"' style='display: block; margin: 0 auto; width: 140px;'>"+
				"</div>"+
				"<div class='col-md-6'>"+
					(attrazione.reazioniPositive != null ? "<i class='fa fa-thumbs-o-up'></i> "+attrazione.reazioniPositive+"<br>" : "")+
					(attrazione.reazioniNegative != null ? "<i class='fa fa-thumbs-o-down'></i> "+attrazione.reazioniNegative+"<br>" : "")+
					(attrazione.valutazioneMedia != null ? "<i class='fa fa-star'></i> "+attrazione.valutazioneMedia+"<br>" : "")+
					"<i class='fa fa-map-marker'></i> "+attrazione.visiteConfermate+"<br>"+
					"<a href='/discover/attrazione/"+attrazione.idAttrazione+"' target='_blank'><b>Visualizza dettagli</b></a>"+
				"</div>"+
			"</div>";
		infoWindow.setContent(contentString);
		infoWindow.open(map, marker);
	});
	 
	marker.setMap(map);	
	return marker;
}