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
			<ul class="nav nav-tabs light-blue-bg" style="height: calc(100vh - 42px); height: -webkit-calc(100vh - 42px); height: -moz-calc(100vh - 42px);">
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${key eq 'Tutte le date'}">
						<li class="m-0 font-weight-bold <c:if test='${indexKey.index == 0}'>active</c:if>" style="font-size: 15px; color: #ccc;"><a href="#data${indexKey.index}" data-toggle="tab">${key}</a></li>
					</c:if>
				</c:forEach>
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${key eq 'Non programm.'}">
						<li class="m-0 font-weight-bold <c:if test='${indexKey.index == 0}'>active</c:if>" style="font-size: 15px; color: #ccc;"><a href="#data${indexKey.index}" data-toggle="tab">${key}</a></li>
					</c:if>
				</c:forEach>
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<c:if test="${not (key eq 'Tutte le date' or key eq 'Non programm.')}">
						<li class="m-0 font-weight-bold <c:if test='${indexKey.index == 0}'>active</c:if>" style="font-size: 15px; color: #ccc;"><a href="#data${indexKey.index}" data-toggle="tab">${key}</a></li>
					</c:if>
				</c:forEach>
			</ul>
			<div class="tab-content p-0" style="height: calc(100vh - 42px); height: -webkit-calc(100vh - 42px); height: -moz-calc(100vh - 42px);">
				<c:forEach items="${itinerario.mapAttrazioni.keySet()}" var="key" varStatus="indexKey">
					<div class="tab-pane <c:if test='${indexKey.index == 0}'>active</c:if>" id="data${indexKey.index}" style="width: 100%;">
						<c:forEach items="${itinerario.mapAttrazioni[key]}" var="visita" varStatus="indexVista">
							<div class="box box-body m-0 light-blue-bg" style="margin-left: 136px; position: inherit;">
								<div>
									<div class="text-center">
										<i class="fa fa-align-justify" style="font-size: 1.5em;"></i>
									</div>
								</div>
								<div>
									<div style="margin-top: 10px;">
										<span class="btn btn-danger" style="font-size: 1.5em; border-radius: 20px; padding: 3px;">1.1</span>
										<span style="font-size: 1.3em;">
											<b>${visita.attrazione.nome}</b>
										</span>
									</div>
										<div style="margin-top: 10px;">
											<i class="fa fa-file" style="font-size: 1.5em; text-align: left; padding-right: 10px;"></i>
											<i class="fa fa-info-circle" style="font-size: 1.5em; text-align: left;"></i>
											<i class="fa fa-pencil" style="font-size: 1.5em; float: right;"></i>
											<i class="fa fa-trash" style="font-size: 1.5em; float: right; padding-right: 10px;"></i>
											<i class="fa fa-copy" style="font-size: 1.5em; float: right; padding-right: 10px;"></i>
										</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</section>

<script src="<%=request.getContextPath()%>/resources/dist/js/itinerario.js" type="text/javascript"></script>