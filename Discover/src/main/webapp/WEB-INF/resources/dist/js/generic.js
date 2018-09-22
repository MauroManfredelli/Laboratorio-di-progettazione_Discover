
$(document).ready(function() {
	$('.chosen-select').chosen();
	$('.chosen-container').attr("style", "width: 100%;");
	checkRunningOnMobile();

	$(".modal").on('shown.bs.modal', function() {
		var textarea = $(this).find("textarea");
		if($(textarea).val() != undefined) {
			setTimeout(function (){
				$(textarea).focus();
			}, 100);
		} else {
			var input = $(this).find("input.form-control")[0];
			setTimeout(function (){
				$(input).focus();
			}, 100);
		}
	});
});

var isMobile = false;
function checkRunningOnMobile() {
	isMobile = false; //initiate as false
	// device detection
	if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) { 
	    isMobile = true;
	}
	if(isMobile) {
		$("#footerMobile").removeClass("hidden");
		$("#headerWeb").addClass("hidden");
		$("#logoWeb").addClass("hidden");
		$("#logoMobile").removeClass("hidden");
		$(".content-wrapper").css("padding-top", "64px")/*.css("padding-bottom", "32px")*/;
		$(".content").css("padding-left", "0px")
					 .css("padding-right", "0px");
		$("#inputSelectLista").css("top", "90px");
		$("#labelTipologiaAttr").removeClass("text-right");
		$("#labelStatoAttrazione").removeClass("text-right");
		$("#labelLunghezzaMassima").removeClass("text-right");
		$(".nav-tabs li a h5").removeAttr("style");
		$("[id^=cotainerImmaginiLista]").attr("style", "max-height: 250px; overflow-x: hidden; overflow-y: auto;");
		$("#mapContent").removeClass("p-0");
		// $("#mapCercaLocalita").css("bottom", "0px").addClass("m-0");
		$(".content").css("padding-bottom", "0px");
		$("#btnMostraScegliLista").html("&nbsp;<i class='fa fa-check'></i>");
		$(".rounded-box-desktop").removeClass("rounded-box-desktop").addClass("rounded-box-mobile");
	} else {
		$("#footerMobile").addClass("hidden");
		$("#headerWeb").removeClass("hidden");
		$("#logoWeb").removeClass("hidden");
		$("#logoMobile").addClass("hidden");
		$(".content-wrapper").css("padding-top", "90px");
		$(".content").css("padding-left", "180px")
		 			 .css("padding-right", "180px");
		$("#inputSelectLista").css("top", "105px");
		$("#labelTipologiaAttr").addClass("text-right");
		$("#labelStatoAttrazione").addClass("text-right");
		$("#labelLunghezzaMassima").addClass("text-right");
		$(".nav-tabs li a h5").attr("style", "font-size: 1.5em");
		$("[id^=cotainerImmaginiLista]").removeAttr("style");
		$("#mapContent").addClass("p-0");
		$("#mapContent").closest(".content-wrapper").css("padding-bottom", "0px");
		// $("#mapCercaLocalita").css("bottom", "18px").removeClass("m-0");
		$(".content").css("padding-bottom", "0px");
		$("#btnMostraScegliLista").html("&nbsp;<i class='fa fa-check'></i> <span id='textSceltaLista'>Lista selezionata</span>");
		$(".rounded-box-mobile").addClass("rounded-box-desktop").removeClass("rounded-box-mobile");
	}
}

jQuery.fn.outerHTML = function() {
    return jQuery('<div />').append(this.eq(0).clone()).html();
};

function addReaction(el, reaction) {
	if($(el).parent("span").hasClass("text-primary") || $(el).parent("span").hasClass("text-danger") || $(el).parent("span").hasClass("text-success")) {
		$(el).parent("span").removeClass("text-primary").removeClass("text-danger").removeClass("text-success");
		$(el).parent("span").find("#num").html(parseInt($(el).parent("span").find("#num").html()) - 1);
	} else {
		if(reaction == 'like') {
			$(el).parent("span").addClass("text-primary");
		} else if(reaction == 'unlike') {
			$(el).parent("span").addClass("text-danger");
		} else if(reaction == 'visita') {
			$(el).parent("span").addClass("text-success");
		}
		$(el).parent("span").find("#num").html(parseInt($(el).parent("span").find("#num").html()) + 1);
	}
}

 $(function () {
    $('.icheck-checbox').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
 }); 

function mostraNotifica(testo, tipo) {
	 if(tipo=="success") {
		 $("#notificaSuccess #testoNotifica").html(testo);
		 $("#notificaSuccess").fadeIn();
		 setTimeout(function() {
			 $("#notificaSuccess").fadeOut();
		 }, 2000);
	 } else if(tipo=="danger") {
		 $("#notificaDanger #testoNotifica").html(testo);
		 $("#notificaDanger").fadeIn();
		 setTimeout(function() {
			 $("#notificaDanger").fadeOut();
		 }, 2000);
	 } else if(tipo=="primary") {
		 $("#notificaPrimary #testoNotifica").html(testo);
		 $("#notificaPrimary").fadeIn();
		 setTimeout(function() {
			 $("#notificaPrimary").fadeOut();
		 }, 2000);
	 }
 }