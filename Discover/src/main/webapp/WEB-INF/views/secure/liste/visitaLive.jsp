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
	
	<input type="hidden" id="idItinerario" value="${itinerario.id}" />
	<div id="map"></div>
	
	<div style="position: fixed; top: 90px; width: 100%;">
		<div class="col-md-12 p-0" style="border: none">
			<div class="box box-body m-0 light-blue-bg" style="padding: 10px 10px 10px 10px; border-radius: 0px;">
				<i class="fa fa-arrow-left" style="font-size: 30px; cursor: pointer;" onclick="location.assign('/discover/liste/itinerario${itinerario.id}')"></i>
				<span class="font-weight-bold" style="font-size: 25px; padding-left: 20px;">${itinerario.nome}</span>
				
				<i class="fa fa-times text-primary pull-right" style="font-size: 30px; cursor: pointer; padding-right: 10px;" onclick="location.assign('/discover/liste')"></i>
			</div>
		</div>
	</div>
	
</section>

<script src="<%=request.getContextPath()%>/resources/dist/js/visitaLive.js" type="text/javascript"></script>