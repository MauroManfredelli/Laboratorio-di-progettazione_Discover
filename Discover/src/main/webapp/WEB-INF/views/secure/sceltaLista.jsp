<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-md-3 col-md-offset-9" style="width: auto; position: fixed; top: 105px; z-index: 1; right: 0px;  background-color: #FFF; padding-top: 10px; padding-bottom: 10px; border: 1px solid #3c8dbc; border-right: none; border-top-left-radius: 20px; border-bottom-left-radius: 20px;" id="inputSelectLista">
	<i class="fa fa-suitcase text-action" onclick="mostraScegliLista()" id="btnMostraScegliLista" style="cursor: pointer;"><i class="fa fa-check-circle"></i> <span id="textSceltaLista">Lista selezionata</span></i>
	<span id="scegliLista" class="hidden">
		<label>Lista selezionata:&nbsp;&nbsp;<i class="fa fa-plus-circle text-primary" onclick="creaWishlist()" data-toggle="tooltip" title="Crea wishlist" style="cursor: pointer;"></i> <i class="fa fa-times-circle text-danger" onclick="mostraScegliLista()" data-toggle="tooltip" title="Chiudi" style="cursor: pointer;"></i></label>
		<select id="idListaSelezionata" class="form-control chosen chosen-select" data-live-search="true" onchange="saveSceltaLista()" placeholder="Seleziona una lista">
			<option value="">-</option>
			<c:forEach items="${listeUtente}" var="lista" varStatus="indexLista">
				<option value="${lista.id}">${lista.nome}</option>
			</c:forEach>
		</select>
	</span>
	<script>
		
		var idListaUtente = "${idListaUtente}";
		
		$(document).ready(function() {
			if(idListaUtente != '' && idListaUtente != null) {
				$("#idListaSelezionata").val(idListaUtente).trigger("chosen:updated");
				$("#textSceltaLista").html($("#idListaSelezionata option[value="+$("#idListaSelezionata").val()+"]").html());
				$("#textSceltaLista").closest("i").removeClass("text-action").addClass("text-success");
			} else {
				$("#textSceltaLista").html("Seleziona lista");
				$("#textSceltaLista").closest("i").addClass("text-action").removeClass("text-success");
			}
			checkAttrazioniLista();
		});
		
		function checkAttrazioniLista() {
			var idLista = $("#idListaSelezionata").val();
			if($("[id=idListaSelezionata][style='']").val() != undefined && $("[id=idListaSelezionata][style='']").val() != '') {
				idLista = $("[id=idListaSelezionata][style='']").val();
			}
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
		
		function mostraScegliLista(action) {
			if(action == undefined) {
				if($("#scegliLista").hasClass("hidden")) {
					$("#scegliLista").removeClass("hidden");
					$("#btnMostraScegliLista").addClass("hidden");
				} else {
					$("#scegliLista").addClass("hidden");
					$("#btnMostraScegliLista").removeClass("hidden");
				}
			} else if(action == 'show') {
				$("#scegliLista").removeClass("hidden");
				$("#btnMostraScegliLista").addClass("hidden");
			} else if(action == 'hide') {
				$("#scegliLista").addClass("hidden");
				$("#btnMostraScegliLista").removeClass("hidden");
			}
		}
		
		function saveSceltaLista() {
			var idLista = $("#idListaSelezionata").val();
			if($("[id=idListaSelezionata][style='']").val() != undefined && $("[id=idListaSelezionata][style='']").val() != '') {
				idLista = $("[id=idListaSelezionata][style='']").val();
			}
			$.ajax({
		    	type: 'GET',
		        url : '/discover/liste/aggiornaIdListaUtente',
		       	data: {
		   			"idLista": idLista
		       	},
		        headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
		        success: function() {
		        	checkAttrazioniLista();
		        	if(idLista != '') {
						$("#textSceltaLista").html($("#idListaSelezionata option[value="+idLista+"]").html());
						$("#idListaSelezionata").val(idLista).trigger("chosen:updated");
						$("#textSceltaLista").closest("i").removeClass("text-action").addClass("text-success");
					} else {
						$("#textSceltaLista").html("Seleziona lista");
						$("#textSceltaLista").closest("i").addClass("text-action").removeClass("text-success");
					}
		        	mostraScegliLista('hide');
		        }
			});
		}
		
		function aggiungiAttrazioneToLista(idAttrazione) {
			var idLista = $("#idListaSelezionata").val();
			if(idLista == "") {
				swal({
        			title: '',
        			text: 'Selezionare una lista a cui aggiungere l\'attrazione!<br><br>'+$("#idListaSelezionata").outerHTML().replace("display: none;", ""),
        			html: true,
        			showCancelButton: false,
        			confirmButtonText: 'Continua',
        			confirmButtonColor: '#3c8dbc',
        		}, function() {
        			$("#inputSelectLista").fadeTo('slow', 0.5).fadeTo('slow', 1.0).fadeTo('slow', 0.5).fadeTo('slow', 1.0);
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
			        		mostraNotifica("Attrazione rimossa dalla lista", "success");
			        		$("#btnAggiungiAttrazioneLista"+idAttrazione).addClass("text-primary").removeClass("text-success");
			        	}
			        }
				});
			}
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
				$("#modalWushlistForm").ajaxSubmit({
					success: function() {
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
	</script>
</div>


<jsp:include page="/WEB-INF/views/secure/modali/modaleWishlist.jsp"/>