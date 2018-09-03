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
	
	<div id="map"></div>
	
	<div id="mapCercaLocalita" style="position: fixed; left: 0px; bottom: 18px; max-width: 400px;" class="box box-primary">
		<div class="row" style="padding-top: 15px; padding-bottom: 15px; padding-right: 10px;">
			<div class="col-md-12">
			<div class="input-group">
				<div class="input-group-addon" style="border: none;">
                	<b>Cerca: </b>
                </div>
				<input id="localitaMappa" class="form-control" style="display: inline-block;" />
				<div class="input-group-addon" style="background-color: #ddd; color: #000;">
                	<i class="fa fa-search" style="cursor: pointer;" onclick="cercaLocalitaMappa()"></i>
                </div>
			</div>
			</div>
		</div>
	</div>
</section>

<script src="<%= request.getContextPath() %>/resources/dist/js/mappa.js" type="text/javascript"></script>