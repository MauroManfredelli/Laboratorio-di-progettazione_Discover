
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