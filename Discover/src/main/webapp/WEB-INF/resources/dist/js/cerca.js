$(document).ready(function() {
	$("#headerWeb #btnCerca, #footerMobile #btnCerca").addClass("section-active");
	if($("#lontananzaMinima").val() == "" || $("#lontananzaMinima").val() == null) {
		$("#lontananzaMinima").val("0");
		$("#lontananzaMassima").val("50");
	} 
	$("#sliderLontananza").bootstrapSlider().on('slideStop', function(ev){
	    var data = $('#sliderLontananza').attr("data");
	    data = data.substring(data.indexOf(":") + 2);
	    var lMin = data.substring(data.indexOf("'") + 1, data.indexOf(","));
	    var lMax = data.substring(data.indexOf(",") + 1, data.length - 1);
	    $("#lontananzaMinima").val(lMin);
	    $("#lontananzaMassima").val(lMax);
	    $("#resSlider").html("("+lMin+" Km - "+lMax+" Km)");
	});
	$("#sliderLontananza").bootstrapSlider("setValue", [parseInt($("#lontananzaMinima").val()), parseInt($("#lontananzaMassima").val())]);
    $("#resSlider").html("("+$("#lontananzaMinima").val()+" Km - "+$("#lontananzaMassima").val()+" Km)");
	if($("#localita").val() == null || $("#localita").val() == '') {
		$('#sliderLontananza').bootstrapSlider('disable');
	} 
	$("#boxRisultatiAttrazioni").css("max-height", $("#boxRicercaAttrazioni").css("height")).css("overflow-y", "auto").css("overflow-x", "hidden").css("margin-right", "-19px");
	if ($('input[name=tipoAttrazione]').filter(':checked').length == $('input[name=tipoAttrazione]').length) {
        $('#checkAllTipiAttrazioni').iCheck('check');
    }
	if ($('input[name=statoAttrazione]').filter(':checked').length == $('input[name=statoAttrazione]').length) {
        $('#checkAllStatiAttrazioni').iCheck('check');
    }
	var autocomplete = new google.maps.places.Autocomplete(document.getElementById("localita"));
	autocomplete.addListener('place_changed', function() {
		autocompleteVar = true;
        var place = autocomplete.getPlace();
        if (!place.geometry) {
          // User entered the name of a Place that was not suggested and
          // pressed the Enter key, or the Place Details request failed.
          $("#localita").val("");
          setUserPositionAsCentro();
  	      $('#sliderLontananza').bootstrapSlider('disable');
          return;
        }

        // If the place has a geometry, then present it on a map.
        $("#latCentro").val(place.geometry.location.lat);
	    $("#longCentro").val(place.geometry.location.lng);
	    // $("#lontananzaForm").removeClass("hidden");
	    $('#sliderLontananza').bootstrapSlider('enable');
    });
	if($("#latCentro").val() == "") {
		setUserPositionAsCentro();
	}
});

var autocompleteVar = false;

function checkPosCentro() {
	setTimeout(function() {
		if(!autocompleteVar) {
			/*swal({ 
		  	  title: '',
				  text: '&Egrave; necessario selezionare una località dall\'elenco caricato!',
				  html: true,
				  showCancelButton: false,
				  confirmButtonText: 'Continua',
				  confirmButtonColor: '#0066cc'
		    });*/
			$("#localita").val("");
		}
		if($("#localita").val() == "") {
			$('#sliderLontananza').bootstrapSlider('disable');
		}
		autocompleteVar = false;
	}, 1000);
}

function setUserPositionAsCentro() {
	if (navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(function(position) {
	    var pos = {
	      lat: position.coords.latitude,
	      lng: position.coords.longitude
	    };
	
	    $("#latCentro").val(pos.lat);
	    $("#longCentro").val(pos.lng);
	  }, function() {
	    // ERROR
	  });
	} else {
	  // Browser doesn't support Geolocation
	}
}

var uncheck_singolo_tipi = false;

$('#checkAllTipiAttrazioni').on('ifChecked', function (event) {
	$('input[name=tipoAttrazione]').iCheck('check');
	uncheck_singolo_tipi = false;
});

$('#checkAllTipiAttrazioni').on('ifUnchecked', function (event) {
	if (!uncheck_singolo_tipi)
		$('input[name=tipoAttrazione]').iCheck('uncheck');
	else
		uncheck_singolo_tipi=false;
});

$('input[name=tipoAttrazione]').on('ifUnchecked', function (event) {
    uncheck_singolo_tipi = true;
    $('#checkAllTipiAttrazioni').iCheck('uncheck');
});

$('input[name=tipoAttrazione]').on('ifChecked', function (event) {
	 if ($('input[name=tipoAttrazione]').filter(':checked').length == $('input[name=tipoAttrazione]').length)
		 $('#checkAllTipiAttrazioni').iCheck('check');
});

var uncheck_singolo_stati = false;

$('#checkAllStatiAttrazioni').on('ifChecked', function (event) {
	$('input[name=statoAttrazione]').iCheck('check');
	uncheck_singolo_stati = false;
});

$('#checkAllStatiAttrazioni').on('ifUnchecked', function (event) {
	if (!uncheck_singolo_stati)
		$('input[name=statoAttrazione]').iCheck('uncheck');
	else
		uncheck_singolo_stati=false;
});


$('input[name=statoAttrazione]').on('ifUnchecked', function (event) {
	uncheck_singolo_stati = true;
    $('#checkAllStatiAttrazioni').iCheck('uncheck');
});

$('input[name=statoAttrazione]').on('ifChecked', function (event) {
	if ($('input[name=statoAttrazione]').filter(':checked').length == $('input[name=statoAttrazione]').length)
		 $('#checkAllStatiAttrazioni').iCheck('check');
});

function mostraAltriFiltri(flag) {
	if(flag == 'true') {
		$("[id^=filtroAltro]").removeClass("hidden");
		$("#btnAltriFiltri").addClass("hidden");
		$("#btnMenoFiltri").removeClass("hidden");
		$("#boxRisultatiAttrazioni").css("max-height", $("#boxRicercaAttrazioni").css("height")).css("overflow-y", "auto").css("overflow-x", "hidden").css("margin-right", "-19px");
	} else {
		$("[id^=filtroAltro]").addClass("hidden");
		$("#btnAltriFiltri").removeClass("hidden");
		$("#btnMenoFiltri").addClass("hidden");
		$("#boxRisultatiAttrazioni").css("max-height", $("#boxRicercaAttrazioni").css("height")).css("overflow-y", "auto").css("overflow-x", "hidden").css("margin-right", "-19px");
	}
}

function cercaAttrazioni() {
	$("#ricercaAttrazioniForm").submit();
}
