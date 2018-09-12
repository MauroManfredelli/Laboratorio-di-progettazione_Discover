$(document).ready(function() {
	// $("#liHeadListe").addClass("section-active").css("margin-bottom", "1px");
	initMap();
	setDraggable();
	aggiornaNumeroAttrazioni();
	if($("#allDateInput").val() != 'true') {
		var liList = $("li[id^=item]");
		if(liList.length > 0) {
			var li = liList[0];
			var ol = $(li).closest("ol");
			var nav = $("li[href='#"+$(ol).attr("id")+"']");
			$(nav).click();
			loadMarkersGiorno($(ol).attr("keyString"));
		}
	}
});

function setDraggable() {
	$('.item-draggable').draggable({
        cursor: 'move',
        revert: '10',
        cancel: ".noDrag",
        start: function (event, ui) {
        	
            $(this).css({
                'opacity': '0.4',
                'z-index': '999999'
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
            // var tab_id = $(this).attr('id');
            // alert(tab_id);
            elementDragTo = $(this);
            dragElementTo();
        }
    });
	var sortableList = $("[id^=data]");
	for(var i=0; i<sortableList.length; i++) {
		if($(sortableList[i]).attr("key") != 'Tutteledate') {
			$(sortableList[i]).sortable({
				exclude : $(".notSortable, .noDrag"),
				handle: '.sort-handle',
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
			var href = $(elementDragTo).attr("href");
			var destTab = $("#"+href.substring(1, href.length));
			if($(elementDragFrom).attr("key") == "Tutteledate" ||
					$(destTab).attr("key") == "Tutteledate") {
				elementDragged="";
				elementDragTo="";
				elementDragFrom="";
				return;
			}
			var clonedElement = $(elementDragged).clone();
			$(elementDragged).remove();
			$(destTab).append(clonedElement);
			if($(elementDragFrom).find("li[id^=item]").length == 0) {
				$(elementDragFrom).find("[id^=nessunaAttrazione]").removeClass("hidden");
			}
			aggiornaVisiteDB(destTab, clonedElement);
			aggiornaOrdiniTabFrom(elementDragFrom);
			aggiornaOrdineTabDest(destTab, clonedElement);
			aggiornaMarkersMappa();
			$("li[href='#"+$(elementDragFrom).attr("id")+"']").click();
			elementDragged="";
			elementDragTo="";
			elementDragFrom="";
			setDraggable();
			$("[data-toggle='tooltip']").tooltip();
			$(destTab).find("[id^=nessunaAttrazione]").addClass("hidden");
			aggiornaNumeroAttrazioni();
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
        success: function(result) {
        	if(key.indexOf("(Conclu") > -1) {
        		$("li[id^=item][idVisita='"+idVisita+"']").find("#iconConferma").removeClass("hidden");
        	} else {
        		$("li[id^=item][idVisita='"+idVisita+"']").find("#iconConferma").addClass("hidden");
        	}
        }
	});
}

function aggiornaOrdiniTabFrom(tabFrom) {
	if($(tabFrom).attr("key") != "Tutteledate") {
		var liList = $(tabFrom).find("li[id^=item]");
		var lastOrdineConfermato = 0;
		for(var i=0; i<liList.length; i++) {
			var li = liList[i];
			var ordine = $(li).find("[id=spanOrdine]");
			var ordineVal = $(ordine).attr("ordine");
			var preOrder;
			if(!$(li).find("[id^=iconConferma]").hasClass("hidden") && i>lastOrdineConfermato) {
				lastOrdineConfermato = i;
			}
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
			if(i <= lastOrdineConfermato) {
				$(li).find("[id^=iconConferma]").removeClass("hidden");
			}
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
		if($(nav).attr("href") == "#"+tabDest.attr("id")) {
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
		if($(li).closest("ol").attr("key") == "Tutteledate") {
			continue;
		}
		var id = $(li).attr("idVisita");
		var ordine = $(li).find("[id=spanOrdine]").attr("ordine");
		var tipoMarker = (ordine.substring(0,1) == '0' ? 'marker_black' : 'marker_red');
		for(var i=0; i<markersAttrazioni.length; i++) {
			var marker = markersAttrazioni[i];
			if(marker.id == 'marker'+id) {
				marker.setIcon('/discover/resources/dist/img/markers/'+tipoMarker+"_"+ordine+'.png');
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
var contentsString = {};

function initMap() {
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
		  zoom: 11,
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
		contentsString[attrazione.id] = contentString;
		infoWindow.setContent(contentString);
		infoWindow.open(map, marker);
	});
	 
	marker.setMap(map);	
	return marker;
}

var bounds;
function loadMarkersGiorno(key) {
	if(markersAttrazioni.length == 0) {
		setTimeout(function() {
			loadMarkersGiorno(key);
		}, 500);
		return;
	}
	var someToExtend = false;
	var ol = $("ol[keyString='"+key+"']");
	var liItems = $(ol).find("li[id^=item]");
	bounds = new google.maps.LatLngBounds();
	if(key == "all") {
		for(var j=0; j<markersAttrazioni.length; j++) {
			var marker = markersAttrazioni[j];
			marker.setMap(map);
			bounds.extend(marker.getPosition());
			someToExtend = true;
		}
		if(someToExtend) {
			map.fitBounds(bounds);
		}
		return;
	}
	for(var j=0; j<markersAttrazioni.length; j++) {
		var marker = markersAttrazioni[j];
		marker.setMap(null);
	}
	for(var i=0; i<liItems.length; i++) {
		var li = liItems[i];
		var idVisita = $(li).attr("idVisita");
		for(var j=0; j<markersAttrazioni.length; j++) {
			var marker = markersAttrazioni[j];
			if(marker.id == 'marker'+idVisita) {
				marker.setMap(map);
				bounds.extend(marker.getPosition());
				someToExtend = true;
			}
		}
	}
	if(someToExtend) {
		map.fitBounds(bounds);
	}
}

function resetZomm() {
	map.fitBounds(bounds);
}

// *********************************

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

function cambiaDataVisita(idVisita, tabFrom, element) {
	$("#dataVisitaModal #idVisita").val(idVisita);
	// $("#dataVisitaModal #dataVisita").val("");
	tabFrom = $("#"+element).closest("ol").attr("id");
	$("#dataVisitaModal #btnSalva").attr("onclick", "salvaModificaDataVisita('"+tabFrom+"', '"+element+"')")
	$("#dataVisitaModal").modal("toggle");
}

function cambiaGiornoVisita(idVisita, tabFrom, element) {
	$("#giornoVisitaModal #idVisita").val(idVisita);
	// $("#giornoVisitaModal #giornoVisita").val("");
	tabFrom = $("#"+element).closest("ol").attr("id");
	$("#giornoVisitaModal #btnSalva").attr("onclick", "salvaModificaGiornoVisita('"+tabFrom+"', '"+element+"')")
	$("#giornoVisitaModal").modal("toggle");
}

function salvaModificaDataVisita(tabFrom, element) {
	var idVisita = $("#dataVisitaModal #idVisita").val();
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/salvaModificaDataVisita',
        data: {
        	"idVisita": idVisita,
        	"dataVisita": $("#dataVisitaModal #dataVisita").val()
        },
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(response) {
        	if(response.status == "SUCCESS") {
        		elementDragFrom = $("#"+tabFrom);
        		elementDragged = $("#"+element);
        		var dataVisitaVal = $("#dataVisitaModal #dataVisita").val();
        		if(dataVisitaVal == "") {
        			dataVisitaVal = "Nonprogramm.";
        		}
        		var idTabDest = $("ol[key^='"+dataVisitaVal+"']").attr("id");
                elementDragTo = $("li[href='#"+idTabDest+"']");
                dragElementTo();
                $("#dataVisitaModal").modal("hide");
                if($("#"+idTabDest).attr("key").indexOf("(Conclu") > -1) {
                	$(elementDragged).find("#iconConferma").removeClass("hidden");
            	} else {
            		$(elementDragged).find("#iconConferma").addClass("hidden");
            	}
	        	mostraNotifica('Data di visita aggiornata', 'primary');
        	} else {
        		swal({
        			title: '',
        			text: 'La data deve essere una di quelle specificate per i giorni dell\'itinerario.',
        			html: true,
        			showCancelButton: false,
        			confirmButtonText: 'Continua',
        			confirmButtonColor: '#0066cc',
        		});
        	}
        }
	});
}

function salvaModificaGiornoVisita(tabFrom, element) {
	var idVisita = $("#giornoVisitaModal #idVisita").val();
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/salvaModificaGiornoVisita',
        data: {
        	"idVisita": idVisita,
        	"giornoVisita": $("#giornoVisitaModal #giornoVisita").val()
        },
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(response) {
        	if(response.status == "SUCCESS") {
        		elementDragFrom = $("#"+tabFrom);
        		elementDragged = $("#"+element);
        		var giornoVisitaVal = $("#giornoVisitaModal #giornoVisita").val()
        		if(giornoVisitaVal == "") {
        			giornoVisitaVal = "Nonprogramm.";
        		} else {
        			giornoVisitaVal = "Giorno"+giornoVisitaVal;
        		}
        		var idTabDest = $("ol[key='"+giornoVisitaVal+"']").attr("id");
                elementDragTo = $("li[href='#"+idTabDest+"']");
                dragElementTo();
                $("#giornoVisitaModal").modal("hide");
	        	mostraNotifica('Giorno di visita aggiornata', 'primary');
        	} else {
        		swal({
        			title: '',
        			text: 'Il numero del giorno deve essere entro i limiti del numero di giorni dell\'itinerario.',
        			html: true,
        			showCancelButton: false,
        			confirmButtonText: 'Continua',
        			confirmButtonColor: '#0066cc',
        		});
        	}
        }
	});
}

function modificaDettagliVisita(idVisita) {
	$("#dettagliVisitaModal #idVisita").val(idVisita);
	if($("#showEtichetta"+idVisita).html().indexOf("(") > -1) {
		$("#dettagliVisitaModal #etichettaVisita").val($("#showEtichetta"+idVisita).html().substring(0, $("#showEtichetta"+idVisita).html().indexOf("(") - 1));
		$("#dettagliVisitaModal #oraVisita").val($("#showEtichetta"+idVisita).html().substring($("#showEtichetta"+idVisita).html().indexOf("(") + 1, $("#showEtichetta"+idVisita).html().length - 1));
	} else {
		$("#dettagliVisitaModal #etichettaVisita").val($("#showEtichetta"+idVisita).html());
	}
	$("#dettagliVisitaModal").modal("toggle");
}

function salvaDettagliVisita() {
	var idVisita = $("#dettagliVisitaModal #idVisita").val();
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/salvaModificaEtichetta',
        data: {
        	"idVisita": idVisita,
        	"etichetta": $("#dettagliVisitaModal #etichettaVisita").val(),
        	"ora": $("#dettagliVisitaModal #oraVisita").val()
        },
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(response) {
        	if(response.status == "SUCCESS") {
        		var etichetta = $("#dettagliVisitaModal #etichettaVisita").val();
        		if($("#dettagliVisitaModal #oraVisita").val() != "") {
        			etichetta += " (" + $("#dettagliVisitaModal #oraVisita").val() + ")";
        		}
	        	$("#showEtichetta"+idVisita).html(etichetta);
	        	mostraNotifica('Dettagli visita modificati', 'success');
	        	$("#dettagliVisitaModal").modal("hide");
        	}
        }
	});
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
        	var tabFrom = $("li[id=item"+idVisita+"]").closest("ol");
        	$("li[id=item"+idVisita+"]").remove();
        	aggiornaOrdiniTabFrom(tabFrom);
        	if($(tabFrom).find("li[id^=item]").length == 0) {
				$(tabFrom).find("[id^=nessunaAttrazione]").removeClass("hidden");
			}
        	aggiornaNumeroAttrazioni();
        	mostraNotifica('Visita eliminata', 'danger');
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
			liCloned = liCloned.replace(/_NOTAPREC_/g, visita.notaPrec);
			liCloned = liCloned.replace(/_ORDINESHOW_/g, visita.ordine.replace("-", "."));
			var etichetta = visita.etichetta;
			if(visita.ora != null && visita.ora != "") {
				etichetta += " (" +visita.ora+")";
			}
			liCloned = liCloned.replace(/_ETICHETTA_/g, etichetta );
			liCloned = liCloned.replace(/_URLFOTO_/g, visita.attrazione.fotoPrincipali[0].path.replace(/ /g, "/"));
			liCloned = liCloned.replace(/_IDATTRAZIONE_/g, visita.attrazione.id);
			$("li[id=item"+idVisita+"]").closest("ol").append(liCloned);
			if($("li[id=item"+idVisita+"]").find("span[id=spanOrdine]").hasClass("btn-danger")) {
				$("li[id=item"+visita.id+"]").find("span[id=spanOrdine]").addClass("btn-danger");
			}
			if($("li[id=item"+idVisita+"]").find("span[id=spanOrdine]").hasClass("btn-black")) {
				$("li[id=item"+visita.id+"]").find("span[id=spanOrdine]").addClass("btn-black");
			}
			elementDragFrom = $("li[id=item"+idVisita+"]").closest("ol");
			elementDragged = $("li[id=item"+visita.id+"]");
			clonaMarker(visita, idVisita);
			// aggiornaOrdineSezione();
			setDraggable();
        	mostraNotifica('Visita copiata e aggiunta in fondo alla sezione corrente', 'primary');
        	aggiornaNumeroAttrazioni();
        }
	});
}

function clonaMarker(visita, idVisitaCopied) {
	for(var i=0; i<markersAttrazioni.length; i++) {
		var marker = markersAttrazioni[i];
		if(marker.id == 'marker'+idVisitaCopied) {
			var markerCloned = new google.maps.Marker({
				id: 'marker'+visita.id,
				position: marker.position,
				icon: marker.icon,
				formatted_address: marker.formatted_address,
				map: map,
				title:"Localizzazione attrazione",
				draggable: false
			});
			
			google.maps.event.addListener(markerCloned,'click', function() {
				var contentString = contentsString[idVisitaCopied];
				contentsString[visita.id] = contentString;
				infoWindow.setContent(contentString);
				infoWindow.open(map, markerCloned);
			});
			markerCloned.setMap(map);	
			markersAttrazioni[markersAttrazioni.length] = markerCloned;
		}
	}
}

var liCopia = '<li id="item_IDVISITA_" class="item-draggable list-group-item box box-body m-0" style="position: inherit;" idVisita="_IDVISITA_">'+
						'<div class="noDrag light-grey-bg" style="width: 109%; background-color: #FFF; margin-left: -13px; padding: 10px; margin-top: -12px;">'+
							'<input type="hidden" id="notaPrec_IDVISITA_" value="_NOTAPREC_" />'+
							'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
							'<i class="fa fa-file noDrag" data-toggle="tooltip" title="Nota precedente" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaPrecedente(\'_IDVISITA_\')"></i>'+
						'</div>'+
						'<div style="margin-top: 10px;">'+
							'<div class="text-center">'+
								'<i class="fa fa-align-justify" style="font-size: 1.5em;  cursor: pointer;"></i>'+
							'</div>'+
						'</div>'+
						'<div class="noDrag">'+
							'<div style="margin-top: 10px;">'+
								'<span id="spanOrdine" ordine="_ORDINE_" class="btn " style="font-size: 1.5em; border-radius: 20px; padding: 3px;">'+
									'_ORDINESHOW_'+
								'</span>'+
								'<img '+
									'src="_URLFOTO_" '+
									'style="margin-left: 5px; margin-right: 5px; height: 50px; width: 50px; border-radius: 10px;">'+
								'<span style="font-size: 1.3em;" id="etichettaVisita">'+
									'<b id="showEtichetta_IDVISITA_">_ETICHETTA_</b>'+
								'</span>'+
							'</div>'+
							'<div style="margin-top: 20px;">'+
								'&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-file text-primary" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaVisita(\'_IDVISITA_\')"></i>'+
								'<i class="fa fa-info-circle text-primary" style="font-size: 1.5em; text-align: left; cursor: pointer;" onclick="window.open(\'/discover/attrazione/_IDATTRAZIONE_\', \'_blank\')"></i>'+
								'<i class="fa fa-pencil text-primary" style="font-size: 1.5em; float: right; cursor: pointer;" onclick="modificaDettagliVisita(\'_IDVISITA_\')"></i>'+
								'<i class="fa fa-trash text-primary" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="eliminaVisita(\'_IDVISITA_\')"></i>'+
								'<i class="fa fa-copy text-primary" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="copiaVisita(\'_IDVISITA_\')"></i>'+
							'</div>'+
						'</div>'+
					'</li>';

function confermaItinerario(idItinerario) {
	if($("#btnConfermaItinerario").hasClass("text-success")) {
		$.ajax({
	    	type: 'GET',
	        url : '/discover/liste/rimuoviConfermaItinerario',
	       	data: {
	   			"idItinerario": idItinerario
	       	},
	        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
	        success: function(result) {
	        	$("#btnConfermaItinerario").removeClass("text-success").addClass("text-primary");
	        	mostraNotifica("itinerario non confermato", "danger");
	        }
		});
	} else {
		if($("#tipoItinerario").val() == "GIORNI") {
			swal({
				title: '',
				text: 'L\'itinerario verrà modificato in <b>Itinerario programmato</b> con data inizio uguale alla data odierna. Continuare?',
				html: true,
				showCancelButton: true,
				cancelButtonText: 'Annulla',
				confirmButtonText: 'Continua',
				confirmButtonColor: '#0066cc',
			}, function() {
				confermaItinerarioAjax(idItinerario);
				setTimeout(function() {
					location.reload();
				}, 400);
			});
		} else {
			confermaItinerarioAjax(idItinerario);
		}
	}
}

function confermaItinerarioAjax(idItinerario) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/confermaItinerario',
       	data: {
   			"idItinerario": idItinerario
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {
        	$("#btnConfermaItinerario").addClass("text-success").removeClass("text-primary");
        	mostraNotifica("itinerario confermato", "success");
        }
	});
}

function visitaLive(idItinerario) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/visitaLive',
       	data: {
   			"idItinerario": idItinerario
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {
        	if(result.status == "SUCCESS") {
        		location.assign("/discover/liste/visitaLive/"+idItinerario);
        	} else {
        		swal({
    				title: '',
    				text: 'Non è possibile accedere alla modalità LIVE. Per poter accedere a questa funzionalità è necessario confermare l\'itinerario e che ci sia presente almeno una visita per la data odierna.',
    				html: true,
    				showCancelButton: false,
    				confirmButtonText: 'Continua',
    				confirmButtonColor: '#0066cc',
    			});
        	}
        }
	});
}

$('#divisione1, #divisione2').on('ifChecked', function (event) {
	var divisione = $("#itinerarioModal [name=divisione]:checked").val();
	if(divisione == '1') {
		$("#itinerarioModal #formDate").removeClass("hidden");
		$("#itinerarioModal #formGiorni").addClass("hidden");
		
	} else if(divisione == '2') {
		$("#itinerarioModal #formGiorni").removeClass("hidden");
		$("#itinerarioModal #formDate").addClass("hidden");
	}
});

function modificaItinerario(idItinerario) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/getListaByIdItinerario',
       	data: {
   			"idItinerario": idItinerario
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(lista) {
			$("#itinerarioModal #id").val(lista.idItinerario);
			$("#itinerarioModal #nome").val(lista.nome);
			$("#itinerarioModal #numeroGorni").val(lista.numeroGiorni);
			if(lista.formattedDataInizio != null) {
				$("#itinerarioModal #dateRange").val(lista.formattedDataInizio + " - " +lista.formattedDataFine);
				$("#itinerarioModal #dataInizio").val(lista.formattedDataInizio);
				$("#itinerarioModal #dataFine").val(lista.formattedDataFine);
				$("#itinerarioModal #dateRange").daterangepicker().startDate = lista.formattedDataInizio;
				$("#itinerarioModal #dateRange").daterangepicker().endDate = lista.formattedDataFine;
			}
			$("#itinerarioModal .form-group").removeClass("has-error");
			$("#itinerarioModal").find("div[id^=formErrore]").addClass("hidden");
			$("#itinerarioModal #formDate").addClass("hidden");
			$("#itinerarioModal #formGiorni").addClass("hidden");
			$("#itinerarioModal [type=radio], #itinerarioModal [type=checkbox]").iCheck('uncheck');
			$("#itinerarioModal #divisione"+(lista.numeroGiorni != null ? "2" : "1")).iCheck('check');
			$("#itinerarioModal #formWishlist").addClass("hidden");
			$("#itinerarioModal").modal("toggle");
        }
	});
}

function salvaItinerario() {
	var dateRange = $("#itinerarioModal #dateRange").val();
	$("#itinerarioModal #dataInizio").val(dateRange.substring(0, dateRange.indexOf("-") - 1));
	$("#itinerarioModal #dataFine").val(dateRange.substring(dateRange.indexOf("-") + 1, dateRange.length));
	$("#modalItinerarioForm").ajaxSubmit({
		type: 'GET',
		success: function(response){
			$("#itinerarioModal .form-group").removeClass("has-error");
			$("#itinerarioModal").find("div[id^=formErrore]").addClass("hidden");
			if(response.status == "ERROR") {
				for (var i = 0; i < response.errorMessages.length; i++) {
					var item = response.errorMessages[i];
					var errorMessage = item.errorMessage;
					var field = $("#itinerarioModal [name="+item.fieldName+"]");
					$(field).closest(".form-group").addClass("has-error");
					$(field).closest(".form-group").find("div[id^=formErrore]").removeClass("hidden");
				}
			} else {
				mostraNotifica("Itinerario modificato", "success");
				setTimeout(function() {
					location.reload();
				}, 1000);
			}
		}
	});
}

function aggiornaNumeroAttrazioni() {
	var olList = $("ol[id^=data]");
	var totAttrazioni = 0;
	for(var i=0; i<olList.length; i++) {
		var ol = olList[i];
		var numeroAttrazioni = $(ol).find("li[id^=item]").length;
		$("[id='numeroAttrazioni"+$(ol).attr("key")+"']").html(numeroAttrazioni);
		if($(ol).attr("key") != 'Tutteledate') {
			totAttrazioni += numeroAttrazioni;
		}
	}
	$("#numeroAttrazioniTutteledate").html(totAttrazioni);
}