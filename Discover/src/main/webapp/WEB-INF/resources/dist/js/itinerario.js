$(document).ready(function() {
	initMap();
	$('.item-draggable').draggable({
        cursor: 'move',
        revert: '10',
        start: function (event, ui) {
            $(this).css({
                'opacity': '0.4'
            });
        },
        stop: function (event, ui) {
            $(this).removeAttr("style");
            $(this).css({
                'right':'0px'
            });
            elementDragged = $(this);
            
        }
	});
	$('.dropable-tab').droppable({
        accept: '.item-draggable',
        hoverClass: 'item-hovered',
        drop: function (event, ui) {
            var tab_id = $(this).attr('id');
            alert(tab_id);
            elementDragTo = $(this);
            dragElementTo();
        }
    });
	var sortableList = $("[id^=data]");
	for(var i=0; i<sortableList.length; i++) {
		$(sortableList[i]).sortable({
			cancel : ".notSortable"
		});
	}
});

var elementDragged = "", elementDragTo = "";

function dragElementTo() {
	setTimeout(function() {
		if(elementDragged == "" || elementDragTo == "") {
			dragElementTo();
		} else {
			var clonedElement = $(elementDragged).clone();
			var href = $(elementDragTo).find("a").attr("href")
			var destTab = $("#"+href.substring(1, href.length));
			$(destTab).append(clonedElement);
			$(elementDragged).remove();
			elementDragged="";
			elementDragTo="";
			$(destTab).find("[id^=nessunaAttrazione]").addClass("hidden");
		}
	}, 200);
}

// Note: This example requires that you consent to location sharing when
// prompted by your browser. If you see the error "The Geolocation service
// failed.", it means you probably did not give permission for the browser to
// locate you.
var map, infoWindow  = new google.maps.InfoWindow;
var markersAttrazioni = [], infoWindowsMarker = [];
var geocoder =  new google.maps.Geocoder();

function initMap() {
	geocoder.geocode( { 'address': $('#localitaMappa').val()}, function(results, status) {
		if (status != google.maps.GeocoderStatus.OK) {
			return;
		}
		
		var var_location = new google.maps.LatLng(results[0].geometry.location.lat(),results[0].geometry.location.lng());
 
		var var_mapoptions = {
		  center: var_location,
		  zoom: 11
		};
		
		addMarkersAttrazioniToMap();
		
		map = new google.maps.Map(document.getElementById("map"),
			var_mapoptions);
		  
	    google.maps.event.addDomListener(map, "idle", function() {
		  var center = map.getCenter();
		  google.maps.event.trigger(map, "resize");
		  map.setCenter(center); 
	    });
	});
}

function addMarkersAttrazioniToMap() {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/getMarkersAttrazioniByItinerario',
        data: {
        	"idItinerario": $("#idItinerario").val()
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
		icon: '/discover/resources/dist/img/markers/'+attrazione.tipoMarker+'.png',
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