$(document).ready(function() {
	$("#headerWeb #btnCerca, #footerMobile #btnCerca").addClass("section-active");
	$("#sliderLontananza").bootstrapSlider().on('slideStop', function(ev){
	    var data = $('#sliderLontananza').attr("data");
	    data = data.substring(data.indexOf(":") + 2);
	    var lMin = data.substring(data.indexOf("'") + 1, data.indexOf(","));
	    var lMax = data.substring(data.indexOf(",") + 1, data.length - 1);
	    $("#lontananzaMinima").val(lMin);
	    $("#lontananzaMassima").val(lMax);
	});
	$("#boxRisultatiAttrazioni").css("max-height", $("#boxRicercaAttrazioni").css("height")).css("overflow-y", "auto").css("overflow-x", "hidden");
	if ($('input[name=tipoAttrazione]').filter(':checked').length == $('input[name=tipoAttrazione]').length) {
        $('#checkAllTipiAttrazioni').iCheck('check');
    }
	if ($('input[name=statoAttrazione]').filter(':checked').length == $('input[name=statoAttrazione]').length) {
        $('#checkAllStatiAttrazioni').iCheck('check');
    }
});

$('#checkAllTipiAttrazioni').on('ifChecked', function (event) {
	$('input[name=tipoAttrazione]').iCheck('check');
});

$('input[name=tipoAttrazione]').on('ifUnchecked', function (event) {
    $('#checkAllTipiAttrazioni').iCheck('uncheck');
});

$('input[name=tipoAttrazione]').on('ifChecked', function (event) {
    if ($('input[name=tipoAttrazione]').filter(':checked').length == $('input[name=tipoAttrazione]').length) {
        $('#checkAllTipiAttrazioni').iCheck('check');
    }
});

$('#checkAllStatiAttrazioni').on('ifChecked', function (event) {
	$('input[name=statoAttrazione]').iCheck('check');
});

$('input[name=statoAttrazione]').on('ifUnchecked', function (event) {
    $('#checkAllStatiAttrazioni').iCheck('uncheck');
});

$('input[name=statoAttrazione]').on('ifChecked', function (event) {
    if ($('input[name=statoAttrazione]').filter(':checked').length == $('input[name=statoAttrazione]').length) {
        $('#checkAllStatiAttrazioni').iCheck('check');
    }
});

function mostraAltriFiltri(flag) {
	if(flag == 'true') {
		$("[id^=filtroAltro]").removeClass("hidden");
		$("#btnAltriFiltri").addClass("hidden");
		$("#btnMenoFiltri").removeClass("hidden");
		$("#boxRisultatiAttrazioni").css("max-height", $("#boxRicercaAttrazioni").css("height")).css("overflow-y", "auto").css("overflow-x", "hidden");
	} else {
		$("[id^=filtroAltro]").addClass("hidden");
		$("#btnAltriFiltri").removeClass("hidden");
		$("#btnMenoFiltri").addClass("hidden");
		$("#boxRisultatiAttrazioni").css("max-height", $("#boxRicercaAttrazioni").css("height")).css("overflow-y", "auto").css("overflow-x", "hidden");
	}
}

function cercaAttrazioni() {
	$("#ricercaAttrazioniForm").submit();
}
