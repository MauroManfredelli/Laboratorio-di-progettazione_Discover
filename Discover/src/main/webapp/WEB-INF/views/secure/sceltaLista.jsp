<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-md-3 col-md-offset-9" style="width: auto; position: fixed; top: 105px; z-index: 1; right: 0px;  background-color: #FFF; padding-top: 10px; padding-bottom: 10px; border: 1px solid #3c8dbc; border-right: none; border-top-left-radius: 20px; border-bottom-left-radius: 20px;" id="inputSelectLista">
	<i class="fa fa-suitcase text-info hidden" onclick="mostraScegliLista()" id="btnMostraScegliLista" style="cursor: pointer;"> Liste</i>
	<span id="scegliLista">
		<label>Lista selezionata:&nbsp;&nbsp;<i class="fa fa-times-circle text-danger" onclick="mostraScegliLista()" style="cursor: pointer;"></i></label>
		<select id="idListaSelezionata" class="form-control chosen chosen-select" data-live-search="true" >
			<option value="">-</option>
			<option value="">Milano</option>
			<c:forEach items="${listeUtente}" var="lista" varStatus="indexLista">
				<option value="${lista.id}">${lista.nome}</option>
			</c:forEach>
		</select>
	</span>
	<script>
		function mostraScegliLista() {
			if($("#scegliLista").hasClass("hidden")) {
				$("#scegliLista").removeClass("hidden");
				$("#btnMostraScegliLista").addClass("hidden");
			} else {
				$("#scegliLista").addClass("hidden");
				$("#btnMostraScegliLista").removeClass("hidden");
			}
		}
	</script>
</div>