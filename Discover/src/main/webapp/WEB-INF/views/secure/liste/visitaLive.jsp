<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<%--<script
	src="https://maps.googleapis.com/maps/api/js?libraries=drawing,geometry,geocode&key=AIzaSyDyEIJOP_23NNYKbQFtwDl8A4_EZ3m_Smg"></script>--%>
<script
	src="https://maps.googleapis.com/maps/api/js?client=gme-gestoredelservizi1"></script>
<script src="<%= request.getContextPath() %>/resources/plugin/jQuery/jquery-sortable.js"></script>

 <style>
   #map {
     height: 700px;
     margin-left: 380px;
     margin-top: 55px;
   }
   
   .glyphicon {
    	display:none;
	}
	.arrow_show {
	    display: inline;
	}
</style>
<!-- Content Header (Page header) -->
<section class="content-header p-0"></section>

<!-- Main content -->
<section class="content" id="mapContent">
	
	<input type="hidden" id="idItinerario" value="${itinerario.id}" />
	<div id="map"></div>
	
	<div style="position: fixed; top: 90px; width: 100%;" id="headerVisitaLive">
		<div class="col-md-12 p-0" style="border: none">
			<div class="box box-body m-0 light-blue-bg" style="padding: 10px 10px 10px 10px; border-radius: 0px; cursor:default;">
				<i class="fa fa-arrow-left" data-toggle="tooltip" title="Torna a itinerario" data-placement="right" style="font-size: 30px; cursor: pointer;" onclick="location.assign('/discover/liste/itinerario${itinerario.id}')"></i>
				<span class="font-weight-bold" style="font-size: 25px; padding-left: 20px;">${itinerario.nome} <img src="<%= request.getContextPath() %>/resources/dist/img/live.png" style="margin-bottom: 5px; margin-left: 10px; width: 50px;"></span>
				<i class="far fa-compass" data-toggle="tooltip" title="Reset mappa" data-placement="left" style="font-size: 40px; cursor: pointer; position: fixed; right: 45px; bottom: 22px; background-color: #FFF; padding: 2px; color: #777" onclick="resetZomm()"></i>
				
				<i class="fa fa-times pull-right" data-placement="left" data-toggle="tooltip" title="Chiudi" style="font-size: 30px; cursor: pointer; padding-right: 10px;" onclick="location.assign('/discover/liste')"></i>
			</div>
		</div>
	</div>
	<div id="accordion" class="light-blue-bg" style="position: fixed; height: 82%; max-height: 82%; width: 100%; max-width: 380px; overflow-x:hidden; overflow-y: auto; top: 150px; padding-bottom: 20px; cursor:default;" id="bodyVisitaLive">
		<div class="panel box box-primary m-0">
			<div class="box-header with-border dropable-tab" style="text-align:center; background-color: #FFDDAC;">
				<h4 class="box-title col-md-12 p-0">
					<a data-toggle="collapse" id="headNonVisitate"
						href="#collapseNonVisitate" style="color: #333; font-size: 18px;"> <b>Da visitare</b> <span class="pull-right"><em class="glyphicon glyphicon-chevron-right"></em><em class="glyphicon glyphicon-chevron-down arrow_show"></em></span></a>
				</h4>
			</div>
			<div id="collapseNonVisitate" class="panel-collapse collapse in">
				<ol class="box-body p-0 m-0 sortable" style="overflow-x: hidden">
					<li id="nessunaVisitaNonConfermata" class="notSortable list-group-item m-0 light-azure-bg text-center text-primary" style="border: none; padding-top: 30px; position: inherit;">
						<div><i class="fa fa-info-circle" style="font-size: 4em;"></i></div>
						<div style="font-size: 1.5em;">Nessuna visita presente</div>
					</li>
					<c:forEach items="${visiteLive}" var="visita" varStatus="indexVisita">
						<c:if test="${not visita.conferma}">
							<script>
								$(document).ready(function() {
									$("#nessunaVisitaNonConfermata").addClass("hidden");
								})
							</script>
							<li id="item${visita.id}" class="item-draggable list-group-item m-0 light-azure-bg" style="position: inherit; z-index: 999;" idVisita="${visita.id}">
								<div class="noDrag light-grey-bg" style="width: 109%; margin-left: -15px; padding: 10px; margin-top: -10px;">
									<input type="hidden" id="notaPrec${visita.id}" value="${visita.notaPrec}" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<i class="fa fa-file noDrag" data-toggle="tooltip" title="Nota precedente" data-placement="right" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaPrecedente('${visita.id}')"></i>
								</div>
								<div class="sort-handle">
									<div class="text-center" style="margin-top: 10px;">
										<i class="fas fa-arrows-alt text-primary" data-toggle="tooltip" title="Trascina per ordinare" style="font-size: 1.5em;  cursor: pointer;"></i>
									</div>
								</div>
								<div class="noDrag">
									<div style="margin-top: 10px;">
										<span id="spanOrdine" ordine="${visita.ordine}" class="btn btn-danger" style="font-size: 1.5em; border-radius: 20px; padding: 3px;">
											&nbsp;&nbsp;${fn:substring(visita.ordine, 2, 3)}&nbsp;&nbsp;
										</span>
										<img
											src="${visita.attrazione.fotoPrincipali[0].path}"
											style="margin-left: 5px; margin-right: 5px; height: 50px; width: 50px; border-radius: 10px;">
										<span style="font-size: 1.3em;" id="etichettaVisita">
											<b>${visita.etichetta} <c:if test="${not empty visita.ora}">(${visita.ora})</c:if></b>
										</span>
									</div>
									<input type="hidden" id="notaVisita${visita.id}" value="${visita.nota}" />
									<div style="margin-top: 20px;">
										<i class="fa fa-file text-primary" data-toggle="tooltip" title="Nota visita" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaVisita('${visita.id}')"></i>
										<i class="fa fa-info-circle text-primary" data-toggle="tooltip" title="Dettagli attrazione" style="font-size: 1.5em; text-align: left; cursor: pointer;" onclick="window.open('/discover/attrazione/${visita.attrazione.id}', '_blank')"></i>
										
										<img src="/discover/resources/dist/img/road_sign.png" class="road-sign"
													style=" float: right; padding-right: 10px; cursor: pointer; height: 22px;" data-toggle="tooltip" title="Raggiungi" id="btnIndicazioniVisita" onclick="indicazioniVisita('${visita.id}')" >
										<i class="fa fa-check-circle text-action" data-toggle="tooltip" title="Conferma" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" id="btnConfermaVisita" onclick="confermaVisita('${visita.id}')"></i>
										<i class="fa fa-trash text-primary" data-toggle="tooltip" title="Sposta in non programmate" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" id="btnEliminaVisita" onclick="eliminaVisita('${visita.id}')"></i>
										
									</div>
								</div>
							</li>
						</c:if>
					</c:forEach>
				</ol>
			</div>
		</div>
		<div class="panel box box-primary m-0">
			<div class="box-header with-border dropable-tab"  style="text-align:center; background-color: #FFDDAC">
				<h4 class="box-title col-md-12 p-0">
					<a data-toggle="collapse" id="headVisitate"
						href="#collapseVisitate" style="color: #333; font-size: 18px;"> <b>Visitate</b>  <span class="pull-right"><em class="glyphicon glyphicon-chevron-right"></em><em class="glyphicon glyphicon-chevron-down arrow_show"></em></span></a>
				</h4>
			</div>
			<div id="collapseVisitate" class="panel-collapse collapse in">
				<ol class="box-body p-0 m-0 sortable" style="overflow-x: hidden">
					<li id="nessunaVisitaConfermata" class="notSortable list-group-item m-0 light-azure-bg text-center text-primary" style="border: none; padding-top: 30px; position: inherit;">
						<div><i class="fa fa-info-circle" style="font-size: 4em;"></i></div>
						<div style="font-size: 1.5em;">Nessuna visita presente</div>
					</li>
					<c:forEach items="${visiteLive}" var="visita" varStatus="indexVisita">
						<c:if test="${visita.conferma}">
							<script>
								$(document).ready(function() {
									$("#nessunaVisitaConfermata").addClass("hidden");
								})
							</script>
							<li id="item${visita.id}" class="item-draggable light-azure-bg list-group-item m-0 light-blue-bg" style="position: inherit;" idVisita="${visita.id}">
								<div class="noDrag light-grey-bg" style="width: 109%; background-color: #FFF; margin-left: -15px; padding: 10px; margin-top: -10px;">
									<input type="hidden" id="notaPrec${visita.id}" value="${visita.notaPrec}" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<i class="fa fa-file noDrag" data-toggle="tooltip" title="Nota precedente" data-placement="right" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaPrecedente('${visita.id}')"></i>
								</div>
								<div class="sort-handle">
									<div class="text-center" style="margin-top: 10px;">
										<i class="fas fa-arrows-alt" style="font-size: 1.5em;  cursor: pointer;"></i>
									</div>
								</div>
								<div class="noDrag">
									<div style="margin-top: 10px;">
										<span id="spanOrdine" ordine="${visita.ordine}" class="btn btn-black" style="font-size: 1.5em; border-radius: 20px; padding: 3px;">
											&nbsp;&nbsp;${fn:substring(visita.ordine, 2, 3)}&nbsp;&nbsp;
										</span>
										<img
											src="${visita.attrazione.fotoPrincipali[0].path}"
											style="margin-left: 5px; margin-right: 5px; height: 50px; width: 50px; border-radius: 10px;">
										<span style="font-size: 1.3em;" id="etichettaVisita">
											<b>${visita.etichetta} <c:if test="${not empty visita.ora}"> (${visita.ora})</c:if></b>
										</span>
									</div>
									<input type="hidden" id="notaVisita${visita.id}" value="${visita.nota}" />
									<div style="margin-top: 20px;">
										&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-file text-primary" data-toggle="tooltip" title="Nota visita" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaVisita('${visita.id}')"></i>
										<i class="fa fa-info-circle text-primary" data-toggle="tooltip" title="Dettagli attrazione" style="font-size: 1.5em; text-align: left; cursor: pointer;" onclick="window.open('/discover/attrazione/${visita.attrazione.id}', '_blank')"></i>
										
										<img src="/discover/resources/dist/img/road_sign.png" class="hidden"
													style=" float: right; padding-right: 10px; cursor: pointer; height: 22px;" data-toggle="tooltip" title="Raggiungi" id="btnIndicazioniVisita" onclick="indicazioniVisita('${visita.id}')" >
										<i class="fa fa-check-circle text-success" data-toggle="tooltip" title="Conferma" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" id="btnConfermaVisita" onclick="confermaVisita('${visita.id}')"></i>
										<i class="fa fa-trash hidden text-primary" data-toggle="tooltip" title="Elimina visita" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" id="btnEliminaVisita" onclick="eliminaVisita('${visita.id}')"></i>
									</div>
								</div>
							</li>
						</c:if>
					</c:forEach>
				</ol>
			</div>
		</div>
	</div>
</section>

<jsp:include page="/WEB-INF/views/secure/modali/modaleNotaVisita.jsp"/>
<jsp:include page="/WEB-INF/views/secure/modali/modaleNotaPrec.jsp"/>
<script src="<%=request.getContextPath()%>/resources/dist/js/visitaLive.js" type="text/javascript"></script>