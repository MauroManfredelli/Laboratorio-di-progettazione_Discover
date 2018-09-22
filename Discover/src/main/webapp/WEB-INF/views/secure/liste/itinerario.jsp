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
	src="https://maps.googleapis.com/maps/api/js?libraries=drawing,geometry,geocode&client=gme-gestoredelservizi1"></script>
<script src="<%= request.getContextPath() %>/resources/plugin/jQuery/jquery-sortable.js"></script>

 <style>
   #map {
     height: 700px;
     margin-left: 530px;
     margin-top: 55px;
   }
</style>
<!-- Content Header (Page header) -->
<section class="content-header p-0"></section>

<!-- Main content -->
<section class="content" id="mapContent">

	<script>
		var mapAttrazioni = "${itinerario.mapAttrazioni}";
	</script>
	
	<input type="hidden" id="idItinerario" value="${itinerario.id}" />
	<c:choose>
		<c:when test="${not empty itinerario.numeroGiorni}">
			<input type="hidden" id="tipoItinerario" value="GIORNI" />
		</c:when>
		<c:otherwise>
			<input type="hidden" id="tipoItinerario" value="PROGRAMMATO" />
		</c:otherwise>
	</c:choose>
	<input type="hidden" id="localitaMappa" value="${localitaCentroMappa}" />
	<div id="map"></div>
	
	<div style="position: fixed; top: 90px; width: 100%;" id="headerItinerario">
		<div class="col-md-12 p-0" style="border: none">
			<div class="box box-body m-0 light-blue-bg" style="padding: 10px 10px 10px 10px; border-radius: 0px; cursor:default;">
				<i class="fa fa-arrow-left hidden" style="font-size: 30px; cursor: pointer;"></i>
				<span class="font-weight-bold" style="font-size: 25px; padding-left: 20px;">${itinerario.nome}</span>
				<i class="fa fa-check-circle <c:choose><c:when test="${itinerario.confermato == 'true'}">text-success</c:when><c:otherwise>text-action</c:otherwise></c:choose>" data-toggle="tooltip" title="Conferma itinerario" data-placement="right" style="font-size: 30px; padding-left: 10px; cursor: pointer;" id="btnConfermaItinerario" onclick="confermaItinerario('${itinerario.id}')"></i>
				<i class="fa fa-location-arrow" data-toggle="tooltip" title="LIVE" data-placement="right" style="font-size: 30px; padding-left: 10px; cursor: pointer;" onclick="visitaLive('${itinerario.id}')"></i>
				<i class="fa fa-ellipsis-v" data-toggle="tooltip" title="Modifica info itinerario" data-placement="right" style="font-size: 30px; padding-left: 10px; cursor: pointer;" onclick="modificaItinerario('${itinerario.id}')"></i>
				<i class="far fa-compass" data-toggle="tooltip" title="Reset mappa" data-placement="left" style="font-size: 40px; cursor: pointer; position: fixed; right: 45px; bottom: 22px; background-color: #FFF; padding: 2px; color: #777" onclick="resetZomm()"></i>
				<i class="fa fa-times pull-right" data-toggle="tooltip" title="Chiudi" data-placement="left" style="font-size: 30px; cursor: pointer; padding-right: 10px;" onclick="location.assign('/discover/liste')"></i>
			</div>
		</div>
	</div>
	<div style="position: fixed; height: 100%; max-height: 100%; width: 100%; max-width: 530px; overflow-x:hidden; overflow-y: auto; top: 146px;" id="bodyItinerario">
		<div class="nav-tabs-custom tabbable tabs-left">
			<ul id="itinerarioNavTabs" class="nav nav-tabs nav-iti light-blue-bg m-0" style="height: calc(90vh - 42px); height: -webkit-calc(90vh - 42px); height: -moz-calc(90vh - 42px); overflow-y: auto; cursor:default">
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${key eq 'Non programm.'}">
						<li id="nonProgramm" onclick="loadMarkersGiorno('${key}')" class="m-0 dropable-tab active" style="font-size: 15px; border-bottom: 1px solid #ddd; padding: 10px; cursor: pointer;" href="#data${indexKey.index}" data-toggle="tab">${key} <span id="numeroAttrazioni${fn:replace(key, ' ', '')}" class="badge pull-right" style="margin-left: 5px; backgrpund-color: #808080"><i class="fa fa-spinner fa-spin"></i></span></li>
					</c:if>
				</c:forEach>
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${not (key eq 'Tutte le date' or key eq 'Non programm.')}">
						<li id="headerData${indexKey.index}" onclick="loadMarkersGiorno('${key}')" class="m-0 dropable-tab" style="font-size: 15px; border-bottom: 1px solid #ddd; padding: 10px; cursor: pointer;" href="#data${indexKey.index}" data-toggle="tab">
							<c:choose>
								<c:when test="${key.indexOf('Concluso') > -1}">
									<i style="color: #808080;">${key}</i> <span id="numeroAttrazioni${fn:replace(key, ' ', '')}" class="badge pull-right" style="margin-left: 5px; backgrpund-color: #808080"><i class="fa fa-spinner fa-spin"></i></span>
								</c:when>
								<c:otherwise>
									${key} <span id="numeroAttrazioni${fn:replace(key, ' ', '')}" class="badge pull-right" style="margin-left: 5px; backgrpund-color: #808080"><i class="fa fa-spinner fa-spin"></i></span>
								</c:otherwise>
							</c:choose>
						</li>
					</c:if>
				</c:forEach>
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${key eq 'Tutte le date'}">
						<li id="allDate" class="m-0" style="font-size: 15px; border-bottom: 1px solid #ddd; padding: 10px; cursor: pointer;" onclick="location.assign('/discover/liste/${itinerario.id}/tutteLeDate')" href="#data${indexKey.index}" data-toggle="tab">Riepilogo <span id="numeroAttrazioni${fn:replace(key, ' ', '')}" class="badge pull-right" style="margin-left: 5px; backgrpund-color: #808080"><i class="fa fa-spinner fa-spin"></i></span></li>
					</c:if>
				</c:forEach>
			</ul>
			<div id="tabContentItinerario" class="sortable tab-content p-0 light-blue-bg" style="height: calc(90vh - 42px); height: -webkit-calc(90vh - 42px); height: -moz-calc(90vh - 42px);">
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<ol class="list-group tab-pane <c:if test='${key eq "Non programm."}'>active</c:if>" id="data${indexKey.index}" style="overflow-x: hidden; overflow-y: auto; max-height: 97%; padding-bottom: 20px; cursor:default;" key="${fn:replace(key, ' ', '')}" keyString="${key}">
						<c:if test="${key != 'Tutte le date'}">
							<div style="font-size: 20px;" class="grey-bg">
								<b><c:out value="${key}"></c:out></b>
							</div>
						</c:if>
						<li id="nessunaAttrazione${indexKey.index}" class="hidden notSortable list-group-item m-0 light-azure-bg text-center text-primary" style="border: none; padding-top: 30px; position: inherit;">
							<div><i class="fa fa-info-circle" style="font-size: 4em;"></i></div>
							<div style="font-size: 1.5em;">Nessuna attrazione presente</div>
						</li>
						<c:choose>
							<c:when test="${empty itinerario.mapAttrazioni[key]}">
								<script>
									var index = "${indexKey.index}";
									$("#nessunaAttrazione"+index).removeClass("hidden");
								</script>
							</c:when>
							<c:otherwise>
								<c:forEach items="${itinerario.mapAttrazioni[key]}" var="visita" varStatus="indexVisita">
									<c:if test="${key eq 'Tutte le date'}">
										<c:if test="${indexVisita.index == 0}">
										</c:if>
										<c:if test="${indexVisita.index == 0 or ((not empty visita.dataVisita) and visita.dataVisita != itinerario.mapAttrazioni[key][indexVisita.index - 1].dataVisita)
														or ((not empty visita.giorno) and visita.giorno != itinerario.mapAttrazioni[key][indexVisita.index - 1].giorno)}">
											<div style="font-size: 20px;" class="grey-bg">
												<c:choose>
													<c:when test="${not empty visita.dataVisita}">
														<b><fmt:formatDate type="date" value="${visita.dataVisita}" pattern="dd/MM/yyyy" />:</b>
													</c:when>
													<c:when test="${not empty visita.giorno}">
														<b><c:out value="Giorno ${visita.giorno}"></c:out>:</b>
													</c:when>
													<c:otherwise>
														<b>Non programmate:</b>
													</c:otherwise>
												</c:choose>
											</div>
										</c:if>
									</c:if>
									<c:set var="keyCmp" value="Tutte le date" />
									<li id="item${visita.id}<c:if test="${key eq 'Tutte le date'}">AllDate</c:if>" class="<c:if test='${key != keyCmp}'>item-draggable</c:if> list-group-item box box-body m-0 light-azure-bg" style="position: inherit;" idVisita="${visita.id}">
										<div class="noDrag light-grey-bg" style="width: 109%; margin-left: -13px; padding: 10px; margin-top: -12px;">
											<input type="hidden" id="notaPrec${visita.id}" value="${visita.notaPrec}" />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<i class="fa fa-file noDrag" data-toggle="tooltip" title="Nota precedente" data-placement="right" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaPrecedente('${visita.id}')"></i>
										</div>
										<div class="sort-handle">
											<c:if test="${key != 'Tutte le date'}">
												<div class="text-center" style="margin-top: 10px;">
													<i class="fas fa-arrows-alt text-primary" data-toggle="tooltip" title="Trascina per ordinare" style="font-size: 1.5em;  cursor: pointer;"></i>
												</div>
											</c:if>
										</div>
										<div class="noDrag">
											<div style="margin-top: 10px;">
												<span id="spanOrdine" ordine="${visita.ordine}" class="btn <c:choose><c:when test='${empty visita.giorno and empty visita.dataVisita}'>btn-black</c:when><c:otherwise>btn-danger</c:otherwise></c:choose>" style="font-size: 1.5em; border-radius: 20px; padding: 3px;">
													${fn:replace(visita.ordine, '-', '.')}
												</span>
												<img
													src="${visita.attrazione.fotoPrincipali[0].path}"
													style="margin-left: 5px; margin-right: 5px; height: 50px; width: 50px; border-radius: 10px;">
												<span style="font-size: 1.3em;" id="etichettaVisita">
													<b <c:if test='${key != keyCmp}'>id="showEtichetta${visita.id}"</c:if>>${visita.etichetta} <c:if test="${not empty visita.ora}"> (${visita.ora})</c:if></b>
												</span>
											</div>
											<input type="hidden" id="notaVisita${visita.id}" value="${visita.nota}" />
											<div style="margin-top: 20px;">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<i class="fa fa-file text-primary" data-toggle="tooltip" title="Nota visita" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaVisita('${visita.id}')"></i>
												<i class="fa fa-info-circle text-primary" data-toggle="tooltip" title="Dettagli attrazione" style="font-size: 1.5em; text-align: left; cursor: pointer;" onclick="window.open('/discover/attrazione/${visita.attrazione.id}', '_blank')"></i>
												<c:if test="${key != 'Tutte le date'}">
													<i class="fa fa-ellipsis-v text-primary" data-toggle="tooltip" title="Modifica info visita" style="font-size: 1.5em; float: right; cursor: pointer;" onclick="modificaDettagliVisita('${visita.id}')"></i>
													<i class="fa fa-trash text-primary" data-toggle="tooltip" title="Elimina visita" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="eliminaVisita('${visita.id}')"></i>
													<i class="fa fa-copy text-primary" data-toggle="tooltip" title="Duplica visita" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="copiaVisita('${visita.id}')"></i>
													<c:choose>
														<c:when test="${not empty visita.itinerario.dataInizio}">
															<i class="far fa-calendar-alt text-primary" data-toggle="tooltip" title="Cambia data" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="cambiaDataVisita('${visita.id}', 'data${indexKey.index}', 'item${visita.id}')"></i>
														</c:when>
														<c:when test="${not empty visita.itinerario.numeroGiorni}">
															<i class="far fa-calendar-alt text-primary" data-toggle="tooltip" title="Cambia giorno" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="cambiaGiornoVisita('${visita.id}', 'data${indexKey.index}', 'item${visita.id}')"></i>
														</c:when>
													</c:choose>
												</c:if>
												<i class="fas fa-check text-success <c:if test='${not visita.conferma}'>hidden</c:if>" style="font-size: 1.1em; padding-left: 10px;" id="iconConferma"><span style="font-size: 0.8em; margin-bottom: 2px;">Confermata</span></i>
											</div>
										</div>
									</li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</ol>
				</c:forEach>
			</div>
		</div>
	</div>
</section>

<input type="hidden" id="allDateInput" value="${allDate}" />
<c:if test="${not empty allDate and allDate}">
	<script>
		$(document).ready(function() {
			$("li.active").removeClass("active");
			$(".tab-pane.active").removeClass("active");
			$("#allDate").addClass("active");
			$($("#allDate").attr("href")+".tab-pane").addClass("active");
			loadMarkersGiorno('all');
		})
	</script>
</c:if>

<jsp:include page="/WEB-INF/views/secure/modali/modaleItinerario.jsp"/>
<jsp:include page="/WEB-INF/views/secure/modali/modaleDettagliVisita.jsp"/>
<jsp:include page="/WEB-INF/views/secure/modali/modaleGiornoVisita.jsp"/>
<jsp:include page="/WEB-INF/views/secure/modali/modaleDataVisita.jsp"/>
<jsp:include page="/WEB-INF/views/secure/modali/modaleNotaVisita.jsp"/>
<jsp:include page="/WEB-INF/views/secure/modali/modaleNotaPrec.jsp"/>
<script src="<%=request.getContextPath()%>/resources/dist/js/itinerario.js" type="text/javascript"></script>