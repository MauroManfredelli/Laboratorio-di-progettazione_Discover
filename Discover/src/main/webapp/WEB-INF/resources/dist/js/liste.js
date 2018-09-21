
$(document).ready(function() {
	$("#liHeadListe").addClass("section-active").css("margin-bottom", "1px");
	if(inputOrdinaListe != '') {
		$("#inputOrdinaListe").val(inputOrdinaListe).trigger("chosen:updated");
	}
});

function ordinaListe() {
	var inputOrdinaListe = $("#inputOrdinaListe").val();
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/salvaOrdinaListe',
       	data: {
   			"inputOrdinaListe": inputOrdinaListe
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function() {
        	location.reload();
        }
	});
}

function creaWishlist() {
	$("#wishlistModal #nome").val("");
	$("#wishlistModal #id").val("");
	$("#wishlistModal #formNome").removeClass("has-error");
	$("#wishlistModal #formNomeErrore").addClass("hidden");
	$("#wishlistModal").modal("toggle");
}

function salvaWishlist() {
	if(validaWishlist()) {
		$("#modalWushlistForm").submit();
	}
}

function validaWishlist() {
	if($("#wishlistModal #nome").val() == "") {
		$("#wishlistModal #formNome").addClass("has-error");
		$("#wishlistModal #formNomeErrore").removeClass("hidden")
		return false;
	} else {
		$("#wishlistModal #formNome").removeClass("has-error");
		$("#wishlistModal #formNomeErrore").addClass("hidden");
		return true;
	}
}

function archiviaLista(idLista) {
	swal({
		title: '',
		text: 'La lista selezionata verrà archiviata e aggiunta alle liste archiviate. Continuare?',
		html: true,
		showCancelButton: true,
		cancelButtonText: 'Annulla',
		confirmButtonText: 'Continua',
		confirmButtonColor: '#3c8dbc',
	}, function() {
		$.ajax({
	    	type: 'GET',
	        url : '/discover/liste/archiviaLista',
	       	data: {
	   			"idLista": idLista
	       	},
	        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
	        success: function(result) {
	        	if(result.status == "SUCCESS") {
	        		mostraNotifica("Lista archiviata", "primary");
	        		setTimeout(function() {
	        			location.reload();
	        		}, 1000);
	        	} else {
	        		
	        	}
	        }
		});
	});
}

function recuperaLista(idLista) {
	swal({
		title: '',
		text: 'La lista selezionata verrà recuperata e aggiunta alle liste attive. Continuare?',
		html: true,
		showCancelButton: true,
		cancelButtonText: 'Annulla',
		confirmButtonText: 'Continua',
		confirmButtonColor: '#3c8dbc',
	}, function() {
		$.ajax({
	    	type: 'GET',
	        url : '/discover/liste/recuperaLista',
	       	data: {
	   			"idLista": idLista
	       	},
	        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
	        success: function(result) {
	        	if(result.status == "SUCCESS") {
	        		mostraNotifica("Lista recuperata", "primary");
	        		setTimeout(function() {
	        			location.reload();
	        		}, 1000);
	        	} else {
	        		
	        	}
	        }
		});
	});
}

function confermaItinerario(idLista, idItinerario) {
	if($("#divLista"+idLista+" #confermaItinerario i").hasClass("text-success")) {
		$.ajax({
	    	type: 'GET',
	        url : '/discover/liste/rimuoviConfermaItinerario',
	       	data: {
	   			"idItinerario": idItinerario
	       	},
	        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
	        success: function(result) {
	        	$("#divLista"+idLista+" #confermaItinerario i").removeClass("text-success").addClass("text-action");
	        	mostraNotifica("itinerario non confermato", "danger");
	        }
		});
	} else {
		if($("#tipoLista"+idLista).val() == "GIORNI") {
			swal({
				title: '',
				text: 'L\'itinerario verrà modificato in <b>Itinerario programmato</b> con data inizio uguale alla data odierna. Continuare?',
				html: true,
				showCancelButton: true,
				cancelButtonText: 'Annulla',
				confirmButtonText: 'Continua',
				confirmButtonColor: '#3c8dbc',
			}, function() {
				confermaItinerarioAjax(idLista, idItinerario);
				setTimeout(function() {
					location.reload();
				}, 400);
			});
		} else {
			confermaItinerarioAjax(idLista, idItinerario);
		}
	}
}

function confermaItinerarioAjax(idLista, idItinerario) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/confermaItinerario',
       	data: {
   			"idItinerario": idItinerario
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {
        	$("#divLista"+idLista+" #confermaItinerario i").addClass("text-success").removeClass("text-primary");
        	mostraNotifica("itinerario confermato", "success");
        }
	});
}

function eliminaLista(idLista) {
	swal({
		title: '',
		text: 'La lista selezionata verrà eliminata e non sarà più possibile utilizzarla. Continuare?',
		html: true,
		showCancelButton: true,
		cancelButtonText: 'Annulla',
		confirmButtonText: 'Continua',
		confirmButtonColor: '#3c8dbc',
	}, function() {
		$.ajax({
	    	type: 'GET',
	        url : '/discover/liste/eliminaLista',
	       	data: {
	   			"idLista": idLista
	       	},
	        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
	        success: function(result) {
	        	if(result.status == "SUCCESS") {
	        		mostraNotifica("Lista eliminata", "danger");
	        		$("#divLista"+idLista).fadeOut();
	        	} else {}
	        }
		});
	});
}

function modificaLista(idLista) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/getLista',
       	data: {
   			"idLista": idLista
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(lista) {
        	if(lista.idWishlist != null) {
        		modificaWishlist(lista);
        	} else {
        		modificaItinerario(lista);
        	}
        }
	});
}

function modificaWishlist(lista) {
	$("#wishlistModal #nome").val(lista.nome);
	$("#wishlistModal #id").val(lista.idWishlist);
	$("#wishlistModal #formNome").removeClass("has-error");
	$("#wishlistModal #formNomeErrore").addClass("hidden");
	$("#wishlistModal").modal("toggle");
}

function modificaItinerario(lista) {
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

function creaItinerario() {
	if($("#itinerarioModal [name=idWishlist]").val() == undefined) {
		swal({
			title: '',
			text: '<b>Non è possibile creare un itinerario!</b><br><br>Per poter creare un itinerario è necessario:<ol style="text-align: center; margin-top: 5px; "><li style="text-align: left;"> creare una wishlist;</li><li style="text-align: left;">inserire nella wishlist le attrazioni che si vogliono visitare.</li></ol><b>Al momento non sono presenti wishlist valide per la creazione di itinerari!</b>',
			html: true,
			showCancelButton: false,
			confirmButtonText: 'Continua',
			confirmButtonColor: '#3c8dbc',
		});
		return;
	}
	$("#itinerarioModal #id").val("");
	$("#itinerarioModal #nome").val("");
	$("#itinerarioModal #numeroGorni").val("");
	$("#itinerarioModal #dateRange").val("");
	$("#itinerarioModal #dataInizio").val("");
	$("#itinerarioModal #dataFine").val("");
	$("#itinerarioModal .form-group").removeClass("has-error");
	$("#itinerarioModal").find("div[id^=formErrore]").addClass("hidden");
	$("#itinerarioModal #formDate").addClass("hidden");
	$("#itinerarioModal #formGiorni").addClass("hidden");
	$("#itinerarioModal [type=radio], #itinerarioModal [type=checkbox]").iCheck('uncheck');
	$("#itinerarioModal #formWishlist").removeClass("hidden");
	$("#itinerarioModal").modal("toggle");
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
				console.log(response);
				for (var i = 0; i < response.errorMessages.length; i++) {
					var item = response.errorMessages[i];
					var errorMessage = item.errorMessage;
					var field = $("#itinerarioModal [name="+item.fieldName+"]");
					$(field).closest(".form-group").addClass("has-error");
					$(field).closest(".form-group").find("div[id^=formErrore]").removeClass("hidden");
				}
			} else {
				mostraNotifica("Itinerario salvato", "success");
				setTimeout(function() {
					location.assign("/discover/liste")
				}, 1000);
			}
		}
	});
}

function trasformaItinerario(idWishlist) {
	if($("#itinerarioModal [name=idWishlist]").val() == undefined) {
		swal({
			title: '',
			text: '<b>La wishlist che hai selezionato è vuota.</b><br>Per creare l\'itinerario devi aggiungere almeno un\'attrazione!',
			html: true,
			showCancelButton: false,
			confirmButtonText: 'Continua',
			confirmButtonColor: '#3c8dbc',
		});
		return;
	}
	$("#itinerarioModal #id").val("");
	$("#itinerarioModal #nome").val("");
	$("#itinerarioModal #numeroGorni").val("");
	$("#itinerarioModal #dateRange").val("");
	$("#itinerarioModal #dataInizio").val("");
	$("#itinerarioModal #dataFine").val("");
	$("#itinerarioModal .form-group").removeClass("has-error");
	$("#itinerarioModal").find("div[id^=formErrore]").addClass("hidden");
	$("#itinerarioModal #formDate").addClass("hidden");
	$("#itinerarioModal #formGiorni").addClass("hidden");
	$("#itinerarioModal [type=radio], #itinerarioModal [type=checkbox]").iCheck('uncheck');
	$("#itinerarioModal #formWishlist").remove();
	if($("#itinerarioModal #modalItinerarioForm").find("[name^=idWishlist]").val() == undefined) {
		$("#itinerarioModal #modalItinerarioForm").append("<input type='hidden' name='idWishlist[0]' value='"+idWishlist+"' />");
	}
	$("#itinerarioModal").modal("toggle");
}