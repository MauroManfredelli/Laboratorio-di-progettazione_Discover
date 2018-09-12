$(document).ready(function() {
	// $("#liHeadListe").addClass("section-active").css("margin-bottom", "1px");
	initMap();
	setDraggable();
});

$('#headNonVisitate').on("click", function() {
	$('.glyphicon-chevron-right').toggleClass('arrow_show');
	$('.glyphicon-chevron-down').toggleClass('arrow_show');
});

$('#headVisitate').on("click", function() {
	$('.glyphicon-chevron-right').toggleClass('arrow_show');
	$('.glyphicon-chevron-down').toggleClass('arrow_show');
});

function setDraggable() {
	var sortableList = $(".sortable");
	for(var i=0; i<sortableList.length; i++) {
		$(sortableList[i]).sortable({
			connectWith: $(sortableList[i]),
			exclude : $(".notSortable, .noDrag"),
			handle: '.sort-handle',
		});
	}
	
	$('.item-draggable').draggable({
        cursor: 'move',
        revert: '10',
        cancel: ".noDrag",
        start: function (event, ui) {
            $(this).css({
                'opacity': '0.7',
                'z-index': '9999999'
            });
            elementDragFrom = $(this).closest(".panel-collapse");
        },
        stop: function (event, ui) {
            $(this).removeAttr("style");
            $(this).css({
                'right':'0px'
            });
            elementDragged = $(this);
            aggiornaOrdiniStessoTab();
        }
	});
}

function aggiornaOrdiniStessoTab() {
	setTimeout(function() {
		if(elementDragTo == "") {
			aggiornaOrdini();
			aggiornaMarkersMappa();
		} else {
			return;
		}
	}, 200);
}

function confermaVisita(idVisita) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/confermaVisita',
       	data: {
   			"idVisita": idVisita
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {
        	if(result.status == "CONFERMATA") {
	        	elementDragged = $("#item"+idVisita);
	        	elementDragTo = $("#collapseVisitate");
	        	elementDragFrom = $("#collapseNonVisitate");
	            dragElementTo(true);
        	} else {
        		elementDragged = $("#item"+idVisita);
	        	elementDragTo = $("#collapseNonVisitate");
	        	elementDragFrom = $("#collapseVisitate");
	            dragElementTo(true);
        	}
        }
	});
}

var elementDragged = "", elementDragTo = "", elementDragFrom = "";

function dragElementTo(fromClick) {
	setTimeout(function() {
		if(elementDragged == "" || elementDragTo == "") {
			dragElementTo(fromClick);
		} else {
			var clonedElement = $(elementDragged).clone();
			$(elementDragged).remove();
			$(elementDragTo).find("ol").append(clonedElement);
			if($(elementDragFrom).find("li[id^=item]").length == 0) {
				$(elementDragFrom).find("[id^=nessunaVisita]").removeClass("hidden");
			}
			var confermata;
			if($(elementDragTo).attr("id") == "collapseVisitate") {
				confermata = true;
				$(clonedElement).find("[id=spanOrdine]").addClass("btn-black").removeClass("btn-danger");
				$(clonedElement).find("[id=btnConfermaVisita]").addClass("text-success");
				$(clonedElement).find("[id=btnIndicazioniVisita]").addClass("hidden");
				$(clonedElement).find("[id=btnEliminaVisita]").addClass("hidden");
			} else {
				confermata = false;
				$(clonedElement).find("[id=spanOrdine]").removeClass("btn-black").addClass("btn-danger");
				$(clonedElement).find("[id=btnConfermaVisita]").removeClass("text-success");
				$(clonedElement).find("[id=btnConfermaVisita]").addClass("text-action");
				$(clonedElement).find("[id=btnIndicazioniVisita]").removeClass("hidden");
				$(clonedElement).find("[id=btnEliminaVisita]").removeClass("hidden");
			}
			$(clonedElement).find(".tooltip").remove();
			if(fromClick != true) {
				aggiornaVisiteDB(elementDragTo, clonedElement);
			}
			// aggiornaOrdiniTabFrom(elementDragFrom);
			// aggiornaOrdineTabDest(destTab, clonedElement);
			aggiornaOrdini();
			aggiornaMarkersMappa(confermata);
			$(elementDragTo).find("[id^=nessunaVisita]").addClass("hidden");
			elementDragged="";
			elementDragTo="";
			elementDragFrom="";
		    setDraggable();
			$("[data-toggle='tooltip']").tooltip();
			//$("#collapseNonVisitate").find("li").sort(sort_li).appendTo("#collapseNonVisitate");
		    //$("#collapseVisitate").find("li").sort(sort_li).appendTo("#collapseVisitate");
//		    function sort_li(a, b) {
//		        return ($(b).find("[id=spanOrdine]").attr("ordine") < ($(a).find("[id=spanOrdine]").attr("ordine")) ? 1 : -1);
//		    }
		}
	}, 200);
}

function aggiornaVisiteDB(tabDest, liElement) {
	var conferma = $(tabDest).attr("id") == 'collapseNonVisitate' ? 'false' : 'true';
	var idVisita = $(liElement).attr("idVisita");
	var idItinerario = $("#idItinerario").val();
	aggiornaVisiteDBajax(conferma, idVisita, idItinerario);
}

function aggiornaVisiteDBajax(conferma, idVisita, idItinerario) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/aggiornaVisiteVisitaLive',
       	data: {
   			"conferma": conferma,
   			"idVisita": idVisita,
   			"idItinerario": idItinerario
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {}
	});
}

function aggiornaOrdiniTabFrom(tabFrom) {
	var liList = $(tabFrom).find("li[id^=item]");
	for(var i=0; i<liList.length; i++) {
		var li = liList[i];
		var ordine = $(li).find("[id=spanOrdine]");
		var ordineVal = $(ordine).attr("ordine");
		var preOrder;
		preOrder = ordineVal.substring(0, ordineVal.indexOf("-"));
		ordineVal = preOrder + "-" + (i + 1);
		$(ordine).attr("ordine", ordineVal);
		$(ordine).html(ordineVal.replace("-", "."));
	}
	for(var i=0; i<liList.length; i++) {
		var li = liList[i];
		var ordine = $(li).find("[id=spanOrdine]");
		var ordineVal = $(ordine).attr("ordine");
		ordineVal = ordineVal.substring(0, ordineVal.indexOf("-")) + "-" + (i + 1);
		aggiornaVisiteStessaDataDB($(li).attr("idVisita"), $("#idItinerario").val(), (i+1));
	}
}

function aggiornaVisiteStessaDataDB(keyString, idVisita, idItinerario, ordine) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/aggiornaVisiteStessaDataVisitaLiveDB',
       	data: {
   			"idVisita": idVisita,
   			"idItinerario": idItinerario,
   			"ordine": ordine
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {}
	});
}

function aggiornaOrdineTabDest(tabDest, liElement) {
	var ordine = $(liElement).find("[id=spanOrdine]");
	var ordineVal = $(ordine).attr("ordine");
	ordineVal = ordineVal.substring(0, ordineVal.indexOf("-")) + "-" + $(tabDest).find("li[id^=item]").length;
	$(ordine).attr("ordine", ordineVal);
	$(ordine).html(ordineVal.replace("-", "."));
}

function aggiornaOrdini() {
	var liNonVis = $("#collapseNonVisitate").find("li[id^=item]");
	var liVis = $("#collapseVisitate").find("li[id^=item]");
	var updates = [];
	var ordineCount = 1, j = 0;
	for(var i=0; i<liVis.length; i++) {
		var li = liVis[i];
		var spanOrdine = $(li).find("[id=spanOrdine]");
		var ordine = $(spanOrdine).attr("ordine");
		ordine = ordine.substring(0, ordine.indexOf("-"))+ "-" + ordineCount;
		$(spanOrdine).attr("ordine", ordine);
		$(spanOrdine).html("&nbsp;&nbsp;"+ordineCount+"&nbsp;&nbsp;");
		var update = {};
		update.idVisita = $(li).attr("idVisita");
		update.ordine = ordineCount;
		updates[j++] = update;
		ordineCount++;
	}
	for(var i=0; i<liNonVis.length; i++) {
		var li = liNonVis[i];
		var spanOrdine = $(li).find("[id=spanOrdine]");
		var ordine = $(spanOrdine).attr("ordine");
		ordine = ordine.substring(0, ordine.indexOf("-"))+ "-" + ordineCount;
		$(spanOrdine).attr("ordine", ordine);
		$(spanOrdine).html("&nbsp;&nbsp;"+ordineCount+"&nbsp;&nbsp;");
		var update = {};
		update.idVisita = $(li).attr("idVisita");
		update.ordine = ordineCount;
		updates[j++] = update;
		ordineCount++;
	}
	aggiornaOrdiniDB(updates);
}

function aggiornaOrdiniDB(updates) {
	$.ajax({
    	type: 'POST',
        url : '/discover/liste/aggiornaOrdiniDB',
        data: JSON.stringify(updates),
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(response) {}
	});
}

function aggiornaMarkersMappa(confermata) {
	var liList = $("li[id^=item]");
	for(var j=0; j<liList.length; j++) {
		var li = liList[j];
		var id = $(li).attr("idVisita");
		var ordine = $(li).find("[id=spanOrdine]").attr("ordine");
		var panel = $(li).closest(".panel-collapse");
		var tipoMarker;
		if($(panel).attr("id") == "collapseNonVisitate") {
			tipoMarker = 'marker_red';
		} else {
			tipoMarker = 'marker_black';
		}
		for(var i=0; i<markersAttrazioni.length; i++) {
			var marker = markersAttrazioni[i];
			if(marker.id == 'marker'+id) {
				// marker.icon = '/discover/resources/dist/img/markers/'+tipoMarker+"_"+ordine+'.png';
				marker.setIcon('/discover/resources/dist/img/markers/'+tipoMarker+"_"+ordine.substring(2,3)+'.png');
			}
		}
	}
}

function aggiornaOrdineSezione() {
	setTimeout(function() {
		if(elementDragTo != "") {
			return;
		} else if(elementDragged == "" || elementDragFrom == "") {
			aggiornaOrdineSezione();
		} else {
			aggiornaOrdiniTabFrom(elementDragFrom);
			aggiornaMarkersMappa();
		}
	}, 400);
}

function mostraNotaVisita(idVisita) {
	$("#notaVisitaModal #idVisita").val(idVisita);
	$("#notaVisitaModal #notaVisita").html($("#notaVisita"+idVisita).val());
	$("#notaVisitaModal").modal("toggle");
}

function mostraNotaPrecedente(idVisita) {
	$("#notaVisitaPrecedenteModal #idVisita").val(idVisita);
	$("#notaVisitaPrecedenteModal #notaPrec").html($("#notaPrec"+idVisita).val());
	$("#notaVisitaPrecedenteModal").modal("toggle");
}

$('#notaVisitaModal').on('hide.bs.modal', function (e) {
	var idVisita = $("#notaVisitaModal #idVisita").val();
	if($("#notaVisitaModal #notaVisita").val() != $("#notaVisita"+idVisita).val()) {
		$.ajax({
	    	type: 'GET',
	        url : '/discover/liste/salvaNotaVisita',
	        data: {
	        	"idVisita": idVisita,
	        	"nota": $("#notaVisitaModal #notaVisita").val()
	        },
	        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
	        success: function(response) {
	        	if(response.status == "SUCCESS") {
	        		$("#notaVisita"+idVisita).val($("#notaVisitaModal #notaVisita").val());
		        	mostraNotifica('Nota visita aggiornata', 'primary');
	        	}
	        }
		});
	}
});

$('#notaVisitaPrecedenteModal').on('hide.bs.modal', function (e) {
	var idVisita = $("#notaVisitaPrecedenteModal #idVisita").val();
	if($("#notaVisitaPrecedenteModal #notaPrec").val() != $("#notaPrec"+idVisita).val()) {
		$.ajax({
	    	type: 'GET',
	        url : '/discover/liste/salvaNotaPrec',
	        data: {
	        	"idVisita": idVisita,
	        	"notaPrec": $("#notaVisitaPrecedenteModal #notaPrec").val()
	        },
	        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
	        success: function(response) {
	        	if(response.status == "SUCCESS") {
	        		$("#notaPrec"+idVisita).val($("#notaVisitaPrecedenteModal #notaPrec").val());
		        	mostraNotifica('Nota precedente aggiornata', 'primary');
	        	}
	        }
		});
	}
});

function eliminaVisita(idVisita) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/eliminaVisitaLive',
        data: {
        	"idVisita": idVisita
        },
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(response) {
        	$("li[id=item"+idVisita+"]").remove();
        	if($("#collapseNonVisitate").find("li[id^=item]").length == 0) {
        		$("#collapseNonVisitate").find("li[id^=nessunaVisita]").removeClass("hidden");
        	}
        	for(var i=1; i<markersAttrazioni.length; i++) {
        		var marker = markersAttrazioni[i];
        		if(marker.id == "marker"+idVisita) {
        			marker.setMap(null);
        		}
        	}
        	aggiornaOrdini();
        	aggiornaMarkersMappa();
        	mostraNotifica('Visita inserita nella sezione Non programm.', 'primary');
        }
	});
}

//Note: This example requires that you consent to location sharing when
//prompted by your browser. If you see the error "The Geolocation service
//failed.", it means you probably did not give permission for the browser to
//locate you.
var map, infoWindow  = new google.maps.InfoWindow;
var markersAttrazioni = [], infoWindowsMarker = [];
var geocoder =  new google.maps.Geocoder();
var markerPosizioneUtente;
var bounds;

function initMap() {
	var myStyles =[
	    {
	        featureType: "poi",
	        elementType: "labels",
	        stylers: [
	              { visibility: "off" }
	        ]
	    }
	];
	map = new google.maps.Map(document.getElementById('map'), {
	    zoom: 14,
	    styles: myStyles
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
		bounds = new google.maps.LatLngBounds();
		bounds.extend(markerPosizioneUtente.getPosition());
		
		addMarkersAttrazioniToMap();
	  }, function() {
	    handleLocationError(true, infoWindow, map.getCenter());
	  });
	} else {
	  // Browser doesn't support Geolocation
      handleLocationError(false, infoWindow, map.getCenter());
	}
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	console.log("errore geolocalizzazione");
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
                          'Error: The Geolocation service failed.' :
    					  'Error: Your browser doesn\'t support geolocation.');
    infoWindow.open(map);
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
				bounds.extend(marker.getPosition());
			}
			map.fitBounds(bounds);
			// addWayToMap();
        }
	});
}

function addMarker(attrazione) {
	var position = new google.maps.LatLng(attrazione.latitudine, attrazione.longitudine);
	var marker = new google.maps.Marker({
		id: 'marker'+attrazione.id,
		position: position,
		icon: '/discover/resources/dist/img/markers/'+attrazione.tipoMarker+'_'+attrazione.ordineMarker.substring(2,3)+'.png',
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
									(attrazione.valutazioneMedia != null ? (attrazione.valutazioneMedia != "null" ? "<i class='fa fa-star'></i> "+attrazione.valutazioneMedia+"<br>" : "<i class='fa fa-star'></i> Nessuna<br>") : "")+
									"<i class='fa fa-map-marker'></i> "+attrazione.visiteConfermate+"<br>"+
									"<a href='/discover/attrazione/"+attrazione.idAttrazione+"' target='_blank'><b>Visualizza dettagli</b></a><br>"+
									"<a href='https://www.google.com/maps/search/?api=1&query="+marker.getPosition().lat()+","+marker.getPosition().lng()+"' target='_blank'><b>Visualizza su maps</b></a>"+
								"</div>"+
							"</div>";
		infoWindow.setContent(contentString);
		infoWindow.open(map, marker);
	});
	 
	marker.setMap(map);	
	return marker;
}

function indicazioniVisita(idVisita) {
	for(var i=0; i<markersAttrazioni.length; i++) {
		var marker = markersAttrazioni[i];
		if(marker.id == "marker"+idVisita) {
			var position = marker.position;
			window.open("https://www.google.com/maps/search/?api=1&query="+marker.getPosition().lat()+","+marker.getPosition().lng()+"", "_blank");
			addWay(markerPosizioneUtente, marker);
		}
	}
}


var directionsDisplay = new google.maps.DirectionsRenderer;
var directionsService = new google.maps.DirectionsService;

function addWay(from, to) {
	directionsDisplay.setMap(null);
//	var stepArray = [];
//	if(markersAttrazioni.length > 1) {
//		var pos=0;
//		for(var i=1; i<(markersAttrazioni.length - 1); i++) {
//			stepArray[pos++] = markersAttrazioni[i].position;
//		}
//	}
	directionsDisplay.setMap(map);
	directionsDisplay.setOptions({suppressMarkers: true});
	directionsService.route({
    	origin: from.position, // Postal address for the start marker (obligatory)
		destination:  to.position, // Postal Address or GPS coordinates for the end marker (obligatory)
		//route : 'way', // Block's ID for the route display (optional)
		travelMode: 'WALKING',
		language : 'italian' // language of the route detail (optional)
	}, function(response, status) {
		if (status === 'OK') {
			directionsDisplay.setDirections(response);
//			for(var i=0; i<response.routes.length; i++) {
//				var leg = response.routes[i].legs[0];
//				console.log(leg);
//			}
	    } else {
	        window.alert('Directions request failed due to ' + status);
	    }
	});
}

function resetZomm() {
	map.fitBounds(bounds);
}