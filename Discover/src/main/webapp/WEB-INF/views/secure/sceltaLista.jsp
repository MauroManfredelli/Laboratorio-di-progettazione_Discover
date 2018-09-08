<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-md-3 col-md-offset-9" style="width: auto; position: fixed; top: 105px; z-index: 1; right: 0px;  background-color: #FFF; padding-top: 10px; padding-bottom: 10px; border: 1px solid #3c8dbc; border-right: none; border-top-left-radius: 20px; border-bottom-left-radius: 20px;" id="inputSelectLista">
	<i class="fa fa-suitcase text-info" onclick="mostraScegliLista()" id="btnMostraScegliLista" style="cursor: pointer;"> Lista selezionata</i>
	<span id="scegliLista" class="hidden">
		<label>Lista selezionata:&nbsp;&nbsp;<i class="fa fa-times-circle text-danger" onclick="mostraScegliLista()" style="cursor: pointer;"></i></label>
		<select id="idListaSelezionata" class="form-control chosen chosen-select" data-live-search="true" onchange="saveSceltaLista()">
			<option value="">-</option>
			<c:forEach items="${listeUtente}" var="lista" varStatus="indexLista">
				<option value="${lista.id}">${lista.nome}</option>
			</c:forEach>
		</select>
	</span>
	<script>
		
		var idListaUtente = "${idListaUtente}";
		
		$(document).ready(function() {
			if(idListaUtente != '') {
				$("#idListaSelezionata").val(idListaUtente).trigger("chosen:updated");
				checkAttrazioniLista();
			}	
		});
		
		function checkAttrazioniLista() {
			var idLista = $("#idListaSelezionata").val();
			$.ajax({
		    	type: 'GET',
		        url : '/discover/liste/getAttrazioniLista',
		       	data: {
		   			"idLista": idLista
		       	},
		        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
		        success: function(listAttrazioni) {
		        	$("[id^=btnAggiungiAttrazioneLista]").removeClass("text-success").addClass("text-primary");
		        	for(var i=0; i<listAttrazioni.length; i++) {
		        		var idAttrazione = listAttrazioni[i];
		        		$("#btnAggiungiAttrazioneLista"+idAttrazione).removeClass("text-primary").addClass("text-success");
		        	}
		        }
			});
		}
		
		function mostraScegliLista() {
			if($("#scegliLista").hasClass("hidden")) {
				$("#scegliLista").removeClass("hidden");
				$("#btnMostraScegliLista").addClass("hidden");
			} else {
				$("#scegliLista").addClass("hidden");
				$("#btnMostraScegliLista").removeClass("hidden");
			}
		}
		
		function saveSceltaLista() {
			var idLista = $("#idListaSelezionata").val();
			$.ajax({
		    	type: 'GET',
		        url : '/discover/liste/aggiornaIdListaUtente',
		       	data: {
		   			"idLista": idLista
		       	},
		        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
		        success: function() {
		        	checkAttrazioniLista();
		        }
			});
		}
		
		function aggiungiAttrazioneToLista(idAttrazione) {
			var idLista = $("#idListaSelezionata").val();
			if(idLista == "") {
				swal({
        			title: 'ERRORE',
        			type: 'error',
        			text: 'Selezionare una lista a cui aggiungere l\'attrazione!',
        			html: true,
        			showCancelButton: false,
        			confirmButtonText: 'Continua',
        			confirmButtonColor: '#0066cc',
        		}, function() {
        			$("#inputSelectLista").fadeTo('slow', 0.5).fadeTo('slow', 1.0).fadeTo('slow', 0.5).fadeTo('slow', 1.0).fadeTo('slow', 0.5).fadeTo('slow', 1.0);
        		});
			} else {
				$.ajax({
			    	type: 'GET',
			        url : '/discover/liste/aggiungiAttrazioneToLista',
			       	data: { 
			       		"idAttrazione": idAttrazione,
			   			"idLista": idLista
			       	},
			        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
			        success: function(result) {
			        	if(result.status == "SUCCESS") {
			        		mostraNotifica("Attrazione aggiunta alla lista", "success");
			        		$("#btnAggiungiAttrazioneLista"+idAttrazione).removeClass("text-primary").addClass("text-success");
			        	} else {
			        		swal({
			        			title: 'ATTENZIONE',
			        			type: 'warning',
			        			text: 'La lista selezionata contiene già l\'attrazione selezionata.',
			        			html: true,
			        			showCancelButton: false,
			        			confirmButtonText: 'Continua',
			        			confirmButtonColor: '#0066cc',
			        		});
			        	}
			        }
				});
			}
		}
	</script>
</div>