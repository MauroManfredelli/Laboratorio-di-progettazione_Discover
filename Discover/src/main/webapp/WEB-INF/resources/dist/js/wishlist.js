
function modificaWishlist(idWishlist) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/getListaByIdWishlist',
       	data: {
   			"idWishlist": idWishlist
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(lista) {
        	$("#wishlistModal #nome").val(lista.nome);
        	$("#wishlistModal #id").val(lista.idWishlist);
        	$("#wishlistModal #formNome").removeClass("has-error");
        	$("#wishlistModal #formNomeErrore").addClass("hidden");
        	$("#wishlistModal").modal("toggle");
        }
	});
}

function salvaWishlist() {
	if(validaWishlist()) {
		$("#modalWushlistForm").ajaxSubmit({
			type: 'GET',
			success: function(response){
				location.reload();
			}
		});
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

function rimuoviAttrazioneFromWishlist(idAW) {
	$.ajax({
    	type: 'GET',
        url : '/discover/liste/rimuoviAttrazioneFromWishlist',
       	data: {
   			"idAW": idAW
       	},
        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
        success: function(result) {
        	mostraNotifica("Attrazione rimossa", "primary");
        	$("#box"+idAW).fadeOut();
        }
	});
}

function creaItinerario(idWishlist) {
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
				mostraNotifica("Itinerario salvato", "success");
				setTimeout(function() {
					location.assign("/discover/liste/itinerario"+response.idGenerato)
				}, 1000);
			}
		}
	});
}