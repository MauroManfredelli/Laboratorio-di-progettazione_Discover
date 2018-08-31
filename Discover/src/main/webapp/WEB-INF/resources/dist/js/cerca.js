$(document).ready(function() {
	$("#headerWeb #btnCerca, #footerMobile #btnCerca").addClass("section-active");
	$(".slider").bootstrapSlider();
});

function checkAll() {
	$("#checkAllTipiAttrazioni").click();
}

$("#checkAllTipiAttrazioni").click(function(){
	$('input[name=tipoAttrazione]:checkbox').not(this).prop('checked', this.checked);
});
