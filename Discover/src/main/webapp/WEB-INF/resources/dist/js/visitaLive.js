$(document).ready(function() {
	initMap();
	setDraggable();
});

function setDraggable() {
	
}

//Note: This example requires that you consent to location sharing when
//prompted by your browser. If you see the error "The Geolocation service
//failed.", it means you probably did not give permission for the browser to
//locate you.
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
		var marker = new google.maps.Marker({
			id: 'posizioneUtente',
			position: position,
			icon: '/discover/resources/dist/img/markers/markerPosizioneUtente_40x40.png',
			formatted_address: "La tua posizione.",
			map: map,
			title:"Localizzazione attrazione",
			draggable: false
		});
		
		google.maps.event.addListener(marker,'click', function() {
			var contentString = "Questa è la tua posizione.";
			infoWindow.setContent(contentString);
			infoWindow.open(map, marker);
		});
		
		marker.setMap(map);	
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

function addMarkersAttrazioniToMap() {
	$.ajax({
    	type: 'GET',
        url : '/discover/mappa/getMarkersAttrazioniVisitaLive',
        data: {
        	"idItinerario" : $("#idItinerario").val()
        },
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
		var contentString = marker.formatted_address + "<br>coordinate: " + marker.getPosition().toUrlValue(6) + "<br><a href='/discover/attrazione/"+attrazione.idAttrazione+"' >Visualizza dettagli</a>";
		infoWindow.setContent(contentString);
		infoWindow.open(map, marker);
	});
	 
	marker.setMap(map);	
	return marker;
}