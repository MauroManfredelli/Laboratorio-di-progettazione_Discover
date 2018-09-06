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
     height: 750px;
     width: 100%;
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
	<input type="hidden" id="localitaMappa" value="${localitaCentroMappa}" />
	<div id="map"></div>
	
	<div style="position: fixed; top: 90px; width: 100%;">
		<div class="col-md-12 p-0" style="border: none">
			<div class="box box-body m-0 light-blue-bg" style="padding: 10px 10px 10px 10px; border-radius: 0px;">
				<i class="fa fa-arrow-left hidden" style="font-size: 30px; cursor: pointer;"></i>
				<span class="font-weight-bold" style="font-size: 25px; padding-left: 20px;">${itinerario.nome}</span>
				<i class="fa fa-check-circle text-success" style="font-size: 30px; padding-left: 10px; cursor: pointer;"></i>
				<i class="fa fa-location-arrow text-info" style="font-size: 30px; padding-left: 10px; cursor: pointer;"></i>
				<i class="fa fa-edit" style="font-size: 30px; padding-left: 10px; cursor: pointer;"></i>
				<i class="fa fa-times pull-right" style="font-size: 30px; cursor: pointer; padding-right: 10px;"></i>
			</div>
		</div>
	</div>
	<div style="position: fixed; height: 100%; max-height: 100%; width: 100%; max-width: 500px; overflow-x:hidden; overflow-y: auto; top: 149px;">
		<div class="nav-tabs-custom tabbable tabs-left">
			<ul id="itinerarioNavTabs" class="nav nav-tabs light-blue-bg" style="height: calc(90vh - 42px); height: -webkit-calc(90vh - 42px); height: -moz-calc(90vh - 42px);">
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${key eq 'Non programm.'}">
						<li id="nonProgramm" class="m-0 font-weight-bold dropable-tab active" style="font-size: 15px; color: #ccc; border-bottom: 1px solid #ddd;"><a href="#data${indexKey.index}" data-toggle="tab" class="m-0">${key}</a></li>
					</c:if>
				</c:forEach>
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${not (key eq 'Tutte le date' or key eq 'Non programm.')}">
						<li id="headerData${indexKey.index}" class="m-0 font-weight-bold dropable-tab" style="font-size: 15px; color: #ccc; border-bottom: 1px solid #ddd;"><a href="#data${indexKey.index}" data-toggle="tab" class="m-0">${key}</a></li>
					</c:if>
				</c:forEach>
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${key eq 'Tutte le date'}">
						<li id="allDate" class="m-0 font-weight-bold dropable-tab" style="font-size: 15px; color: #ccc; border-bottom: 1px solid #ddd;"><a href="#data${indexKey.index}" data-toggle="tab" class="m-0">${key}</a></li>
					</c:if>
				</c:forEach>
			</ul>
			<div id="tabContentItinerario" class="sortable tab-content p-0" style="height: calc(90vh - 42px); height: -webkit-calc(90vh - 42px); height: -moz-calc(90vh - 42px);">
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<ol class="list-group tab-pane <c:if test='${key eq "Non programm."}'>active</c:if>" id="data${indexKey.index}" style="width: 100%; padding-left: 136px;" key="${fn:replace(key, ' ', '')}" keyString="${key}">
						<span style="font-size: 17px;"><b><c:out value="${key}"></c:out></b></span>
						<li id="nessunaAttrazione${indexKey.index}" class="hidden notSortable list-group-item m-0 light-azure-bg text-center text-primary" style="border: none; padding-top: 30px; position: inherit; height: calc(90vh - 42px); height: -webkit-calc(90vh - 42px); height: -moz-calc(90vh - 42px);">
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
											<br>
										</c:if>
										<c:if test="${indexVisita.index == 0 or visita.dataVisita != itinerario.mapAttrazioni[key][indexVisita.index - 1].dataVisita}">
											<span style="font-size: 15px;">
												<b><fmt:formatDate type="date" value="${visita.dataVisita}" pattern="dd/MM/yyyy" />:</b>
											</span>
										</c:if>
									</c:if>
									<c:set var="keyCmp" value="Tutte le date" />
									<li id="item${visita.id}" class="<c:if test='${key != keyCmp}'>item-draggable</c:if> list-group-item box box-body m-0 light-blue-bg" style="position: inherit;" idVisita="${visita.id}">
										<div>
											<c:if test="${key != 'Tutte le date'}">
												<div class="text-center">
													<i class="fa fa-align-justify" style="font-size: 1.5em;  cursor: pointer;"></i>
												</div>
											</c:if>
										</div>
										<div>
											<div style="margin-top: 10px;">
												<span id="spanOrdine" ordine="${visita.ordine}" class="btn <c:choose><c:when test='${empty visita.giorno and empty visita.dataVisita}'>btn-black</c:when><c:otherwise>btn-danger</c:otherwise></c:choose>" style="font-size: 1.5em; border-radius: 20px; padding: 3px;">
													${fn:replace(visita.ordine, '-', '.')}
												</span>
												<img
													src="${visita.attrazione.fotoPrincipali[0].path}"
													style="margin-left: 5px; margin-right: 5px; height: 50px; width: 50px; border-radius: 10px;">
												<span style="font-size: 1.3em;" id="etichettaVisita">
													<b <c:if test='${key != keyCmp}'>id="showEtichetta${visita.id}"</c:if>>${visita.etichetta}</b>
													<c:if test='${key != keyCmp}'>
														<div class="input-group hidden" id="etichetta${visita.id}" style="margin-top: 10px;">
															<input class="form-control" style="display: inline-block;" placeholder="Modifica etichetta" />
															<div class="input-group-addon text-primary" style="color: #000;">
											                	<i class="fa fa-save" style="cursor: pointer;" onclick="salvaModificaEtichetta('${visita.id}')"></i>
											                </div>
															<div class="input-group-addon" style="background-color: #ddd; color: #000;">
											                	<i class="fa fa-repeat" style="cursor: pointer;" onclick="annullaModificaEtichetta('${visita.id}')"></i>
											                </div>
														</div>
													</c:if>
												</span>
											</div>
											<div style="margin-top: 20px;">
												<i class="fa fa-file" style="font-size: 1.5em; text-align: left; padding-right: 10px; cursor: pointer;" onclick="mostraNotaVisita('${visita.id}')"></i>
												<i class="fa fa-info-circle" style="font-size: 1.5em; text-align: left; cursor: pointer;" onclick="location.href('/discover/attrazione/${visita.attrazione.id}')"></i>
												<i class="fa fa-pencil" style="font-size: 1.5em; float: right; cursor: pointer;" onclick="modificaDettagliVisita('${visita.id}')"></i>
												<i class="fa fa-trash" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="eliminaVisita('${visita.id}')"></i>
												<i class="fa fa-copy" style="font-size: 1.5em; float: right; padding-right: 10px; cursor: pointer;" onclick="copiaVisita('${visita.id}')"></i>
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

<script src="<%=request.getContextPath()%>/resources/dist/js/itinerario.js" type="text/javascript"></script>