
$(document).ready(function() {
	
});

function creaWishlist() {
	$("#wishlistModal #nome").val("");
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
		title: 'ATTENZIONE',
		type: 'info',
		text: 'La lista selezionata verrà archiviata e aggiunta alle liste archiviate. Continuare?',
		html: true,
		showCancelButton: true,
		cancelButtonText: 'Annulla',
		confirmButtonText: 'Continua',
		confirmButtonColor: '#0066cc',
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
	        		$("#divLista"+idLista).fadeOut();
	        		var copy = $("#divLista"+idLista).clone();
	        		$("#divLista"+idLista).remove();
	        		$("#listeArchiviate .row .col-md-12").append(copy);
	        		$("#divLista"+idLista).fadeIn();
	        		$("#divLista"+idLista+" #recuperaLista").removeClass("hidden");
	        		$("#divLista"+idLista+" #archiviaLista").addClass("hidden");
        			$("#nessunaListaArchiviata").addClass("hidden");
	        		if($("#listeAttive .row .col-md-12 [id^=divLista]").length == 0) {
	        			$("#nessunaListaAttiva").removeClass("hidden");
	        		}
	        	} else {
	        		
	        	}
	        }
		});
	});
}

function recuperaLista(idLista) {
	swal({
		title: 'ATTENZIONE',
		type: 'info',
		text: 'La lista selezionata verrà recuperata e aggiunta alle liste attive. Continuare?',
		html: true,
		showCancelButton: true,
		cancelButtonText: 'Annulla',
		confirmButtonText: 'Continua',
		confirmButtonColor: '#0066cc',
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
	        		$("#divLista"+idLista).fadeOut();
	        		var copy = $("#divLista"+idLista).clone();
	        		$("#divLista"+idLista).remove();
	        		$("#listeAttive .row .col-md-12").append(copy);
	        		$("#divLista"+idLista).fadeIn();
	        		$("#divLista"+idLista+" #recuperaLista").addClass("hidden");
	        		$("#divLista"+idLista+" #archiviaLista").removeClass("hidden");
        			$("#nessunaListaAttiva").addClass("hidden");
	        		if($("#nessunaListaArchiviata .row .col-md-12 [id^=divLista]").length == 0) {
	        			$("#nessunaListaArchiviata").removeClass("hidden");
	        		}
	        	} else {
	        		
	        	}
	        }
		});
	});
}

function eliminaLista(idLista) {
	swal({
		title: 'ATTENZIONE',
		type: 'info',
		text: 'La lista selezionata verrà eliminata e non sarà più possibile utilizzarla. Continuare?',
		html: true,
		showCancelButton: true,
		cancelButtonText: 'Annulla',
		confirmButtonText: 'Continua',
		confirmButtonColor: '#0066cc',
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
	        	} else {
	        		
	        	}
	        }
		});
	});
}

function modificaLista(idLista) {
	// TODO
}

function creaItinerario() {
	$("#itinerarioModal #nome").val("");
	$("#itinerarioModal #numeroGorni").val("");
	$("#itinerarioModal .form-group").removeClass("has-error");
	$("#itinerarioModal").find("div[id^=formErrore]").addClass("hidden");
	$("#itinerarioModal #formDate").addClass("hidden");
	$("#itinerarioModal #formGiorni").addClass("hidden");
	$("#itinerarioModal [type=radio], #itinerarioModal [type=checkbox]").iCheck('uncheck');
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
				for (var i = 0; i < response.errorMessages.length; i++) {
					var item = response.errorMessages[i];
					var errorMessage = item.errorMessage;
					var field = $("#itinerarioModal [name="+item.fieldName+"]");
					$(field).closest(".form-group").addClass("has-error");
					$(field).closest(".form-group").find("div[id^=formErrore]").removeClass("hidden");
				}
			} else {
				mostraNotifica("Itinerario crearo", "success");
				setTimeout(function() {
					location.assign("/discover/liste")
				}, 1000);
			}
		}
	});
}