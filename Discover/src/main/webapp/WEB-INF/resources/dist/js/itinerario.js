$(document).ready(function() {
	initMap();
	setDraggable();
});

function setDraggable() {
	$('.item-draggable').draggable({
        cursor: 'move',
        revert: '10',
        start: function (event, ui) {
            $(this).css({
                'opacity': '0.4'
            });
            elementDragFrom = $(this).closest("ol");
        },
        stop: function (event, ui) {
            $(this).removeAttr("style");
            $(this).css({
                'right':'0px'
            });
            elementDragged = $(this);
            aggiornaOrdineSezione();
        }
	});
	$('.dropable-tab').droppable({
        accept: '.item-draggable',
        hoverClass: 'item-hovered',
        drop: function (event, ui) {
            var tab_id = $(this).attr('id');
            // alert(tab_id);
            elementDragTo = $(this);
            dragElementTo();
        }
    });
	var sortableList = $("[id^=data]");
	for(var i=0; i<sortableList.length; i++) {
		if($(sortableList[i]).attr("key") != 'Tutteledate') {
			$(sortableList[i]).sortable({
				exclude : $(".notSortable")
			});
		}
	}
}

var elementDragged = "", elementDragTo = "", elementDragFrom = "";

function dragElementTo() {
	setTimeout(function() {
		if(elementDragged == "" || elementDragTo == "") {
			dragElementTo();
		} else {
			var href = $(elementDragTo).find("a").attr("href");
			var destTab = $("#"+href.substring(1, href.length));
			if($(elementDragFrom).attr("key") == "Tutteledate" ||
					$(destTab).attr("key") == "Tutteledate") {
				elementDragged="";
				elementDragTo="";
				elementDragFrom="";
				return;
			}
			var clonedElement = $(elementDragged).clone();
			$(destTab).append(clonedElement);
			$(elementDragged).remove();
			if($(elementDragFrom).find("li[id^=item]").length == 0) {
				$(elementDragFrom).find("[id^=nessunaAttrazione]").removeClass("hidden");
			}
			aggiornaVisiteDB(destTab, clonedElement);
			aggiornaOrdiniTabFrom(elementDragFrom);
			aggiornaOrdineTabDest(destTab, clonedElement);
			aggiornaMarkersMappa();
			elementDragged="";
			elementDragTo="";
			elementDragFrom="";
			setDraggable();
			$(destTab).find("[id^=nessunaAttrazione]").addClass("hidden");
		}
	}, 200);
}

function aggiornaVisiteDB(tabDest, liElement) {
	var keyString = $(tabDest).attr("keyString");
	var idVisita = $(liElement).attr("idVisita");
	var idItinerario = $("#idItinerario").val();
	aggiornaVisiteDBajax(keyString, idVisita, idItinerario);
}

function aggiornaVisiteDBajax(key, idVisita, idItinerario) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/aggiornaVisite',
       	data: {
   			"key": key,
   			"idVisita": idVisita,
   			"idItinerario": idItinerario
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {}
	});
}

function aggiornaOrdiniTabFrom(tabFrom) {
	if($(tabFrom).attr("key") != "Tutteledate") {
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
			$("[key=Tutteledate]").find("[id="+$(li).attr("id")+"]").find("[id=spanOrdine]").attr("ordine", ordineVal);
			$("[key=Tutteledate]").find("[id="+$(li).attr("id")+"]").find("[id=spanOrdine]").html(ordineVal.replace("-", "."));
		}
		for(var i=0; i<liList.length; i++) {
			var li = liList[i];
			var ordine = $(li).find("[id=spanOrdine]");
			var ordineVal = $(ordine).attr("ordine");
			ordineVal = ordineVal.substring(0, ordineVal.indexOf("-")) + "-" + (i + 1);
			aggiornaVisiteStessaDataDB($(tabFrom).attr("keyString"), $(li).attr("idVisita"), $("#idItinerario").val(), (i+1));
		}
	} else {
		$(tabFrom).find("li").sort(sort_li).appendTo(tabFrom);
	    function sort_li(a, b) {
	        return ($(b).find("[id=spanOrdine]").attr("ordine") < ($(a).find("[id=spanOrdine]").attr("ordine")) ? 1 : -1);
	    }
	}
}

function aggiornaVisiteStessaDataDB(keyString, idVisita, idItinerario, ordine) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/aggiornaVisiteStessaDataDB',
       	data: {
   			"key": keyString,
   			"idVisita": idVisita,
   			"idItinerario": idItinerario,
   			"ordine": ordine
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {}
	});
}

function aggiornaOrdineTabDest(tabDest, liElement) {
	var liList = $(tabDest).find("li[id^=item]");
	var navList = $("#itinerarioNavTabs").find("li").not("[id=allDate]").not("[id=nonProgramm]");
	var giorno;
	for(var i=0; i<navList.length; i++) {
		var nav = navList[i];
		if($(nav).find("a").attr("href") == "#"+tabDest.attr("id")) {
			giorno = (i + 1);
			break;
		}
	}
	var ordine = $(liElement).find("[id=spanOrdine]");
	var ordineVal = $(ordine).attr("ordine");
	ordineVal = (giorno == undefined ? '0' : giorno) + "-" + $(tabDest).find("li[id^=item]").length;
	if(giorno == undefined) {
		$(ordine).removeClass("btn-danger").addClass("btn-black");
	} else {
		$(ordine).addClass("btn-danger").removeClass("btn-black");
	}
	$(ordine).attr("ordine", ordineVal);
	$(ordine).html(ordineVal.replace("-", "."));
	$("[key=Tutteledate]").find("[id="+$(liElement).attr("id")+"]").find("[id=spanOrdine]").attr("ordine", ordineVal);
	$("[key=Tutteledate]").find("[id="+$(liElement).attr("id")+"]").find("[id=spanOrdine]").html(ordineVal.replace("-", "."));
	if(giorno == undefined) {
		$("[key=Tutteledate]").find("[id="+$(liElement).attr("id")+"]").find("[id=spanOrdine]").addClass("btn-black").removeClass("btn-danger");
	} else {
		$("[key=Tutteledate]").find("[id="+$(liElement).attr("id")+"]").find("[id=spanOrdine]").removeClass("btn-black").addClass("btn-danger");
	}
}

function aggiornaMarkersMappa() {
	var liList = $("li[id^=item]");
	for(var j=0; j<liList.length; j++) {
		var li = liList[j];
		var id = $(li).attr("idVisita");
		var ordine = $(li).find("[id=spanOrdine]").attr("ordine");
		var tipoMarker = (ordine.substring(0,1) == '0' ? 'marker_black' : 'marker_red');
		for(var i=0; i<markersAttrazioni.length; i++) {
			var marker = markersAttrazioni[i];
			if(marker.id == 'marker'+id) {
				// marker.icon = '/discover/resources/dist/img/markers/'+tipoMarker+"_"+ordine+'.png';
				marker = new google.maps.Marker({
					id: 'marker'+id,
					position: marker.position,
					icon: '/discover/resources/dist/img/markers/'+tipoMarker+"_"+ordine+'.png',
					formatted_address: marker.formatted_address,
					map: map,
					title:"Localizzazione attrazione",
					draggable: false
				});
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
		id: 'marker'+attrazione.id,
		position: position,
		icon: '/discover/resources/dist/img/markers/'+attrazione.tipoMarker+"_"+attrazione.ordineMarker+'.png',
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

// *********************************

function mostraNotaVisita(idVisita) {
	
}

function modificaDettagliVisita(idVisita) {
	$("#showEtichetta"+idVisita).addClass("hidden");
	$("#etichetta"+idVisita).removeClass("hidden");
}

function salvaModificaEtichetta(idVisita) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/salvaModificaEtichetta',
        data: {
        	"idVisita": idVisita,
        	"etichetta": $("#etichetta"+idVisita).val()
        },
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(response) {
        	if(response.status == "SUCCESS") {
	        	$("#showEtichetta"+idVisita).html($("#etichetta"+idVisita).val());
	        	annullaModificaEtichetta(idVisita);
        	}
        }
	});
}

function annullaModificaEtichetta(idVisita) {
	$("#showEtichetta"+idVisita).removeClass("hidden");
	$("#etichetta"+idVisita).addClass("hidden");
}

function eliminaVisita(idVisita) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/eliminaVisita',
        data: {
        	"idVisita": idVisita
        },
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(response) {
        	$("li[id=item"+idVisita+"]").remove();
        }
	});
}

function copiaVisita(idVisita) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/copiaVisita',
        data: {
        	"idVisita": idVisita
        },
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(visita) {
			var liCloned = liCopia;
			liCloned = liCloned.replace(/_IDVISITA_/g, visita.id);
			liCloned = liCloned.replace(/_ORDINE_/g, visita.ordine);
			liCloned = liCloned.replace(/_ORDINESHOW_/g, visita.ordine.replace("-", "."));
			liCloned = liCloned.replace(/_ETICHETTA_/g, visita.etichetta);
			liCloned = liCloned.replace(/_URLFOTO_/g, visita.attrazione.fotoPrincipali[0].path);
			liCloned = liCloned.replace(/_IDATTRAZIONE_/g, visita.attrazione.id);
			$("li[id=item"+idVisita+"]").closest("ol").append(liCloned);
			elementDragFrom = $("li[id=item"+idVisita+"]").closest("ol");
			elementDragged = $($("li[id=item"+visita.id+"]"));
			aggiornaOrdineSezione();
        }
	});
}

var liCopia = '<li id="item_IDVISITA_" class="item-draggable list-group-item box box-body m-0 light-blue-bg" style="position: inherit;" idVisita="_IDVISITA_">'+
						'<div>'+
							'<div class="text-center">'+
								'<i class="fa fa-align-justify" style="font-size: 1.5em;  cursor: pointer;"></i>'+
							'</div>'+
						'</div>'+
						'<div>'+
							'<div style="margin-top: 10px;">'+
								'<span id="spanOrdine" ordine="_ORDINE_" class="btn " style="font-size: 1.5em; border-radius: 20px; padding: 3px;">'+
									'_ORDINESHOW_'+
								'</span>'+
								'<img'+
									'src="_URLFOTO_"'+
									'style="margin-left: 5px; margin-right: 5px; height: 50px; width: 50px; border-radius: 10px;">'+
								'<span style="font-size: 1.3em;" id="etichettaVisita">'+
									'<b id="showEtichetta_IDVISITA_">_ETICHETTA_</b>'+
									'<div class="input-group hidden" id="etichetta_IDVISITA_">'+
										'<input class="form-control" style="display: inline-block;" />'+
										'<div class="input-group-addon" style="background-color: #ddd; color: #000;">'+
						                	'<i class="fa fa-save" style="cursor: pointer;" onclick="salvaModificaEtichetta(\'_IDVISITA_\')"></i>'+
						                	'<i class="fa fa-repeat" style="cursor: pointer;" onclick="annullaModificaEtichetta(\'_IDVISITA_\')"></i>'+
						               '</div>'+
						            '</div>'+
								'</span>'+
							'</div>'+
							'<div style="margin-top: 20px;">'+
								'<i class="fa fa-file" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaVisita(\'_IDVISITA_\')"></i>'+
								'<i class="fa fa-info-circle" style="font-size: 1.5em; text-align: left; cursor: pointer;" onclick="location.href(\'/discover/attrazione/_IDATTRAZIONE_\')"></i>'+
								'<i class="fa fa-pencil" style="font-size: 1.5em; float: right; cursor: pointer;" onclick="modificaDettagliVisita(\'_IDVISITA_\')"></i>'+
								'<i class="fa fa-trash" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="eliminaVisita(\'_IDVISITA_\')"></i>'+
								'<i class="fa fa-copy" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="copiaVisita(\'_IDVISITA_\')"></i>'+
							'</div>'+
						'</div>'+
					'</li>';