<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script
	src="https://maps.googleapis.com/maps/api/js?libraries=drawing,geometry,geocode&key=AIzaSyDyEIJOP_23NNYKbQFtwDl8A4_EZ3m_Smg"></script>

<style>
   #map {
     height: 750px;
     width: 100%;
   }
</style>
<!-- Content Header (Page header) -->
<section class="content-header p-0"></section>

<!-- Main content -->
<section class="content" id="mapContent" style="height: calc(90vh - 42px); height: -webkit-calc(90vh - 42px); height: -moz-calc(90vh - 42px);">
	
	<div id="map"></div>
	
	<div id="mapCercaLocalita" style="position: fixed; left: 0px; top: 100px; max-width: 400px;" class="box box-primary">
		<div class="row" style="padding-top: 15px; padding-bottom: 15px; padding-right: 10px;">
			<div class="col-md-12">
			<div class="input-group">
				<div class="input-group-addon" style="border: none;">
                	<b>Cerca: </b>
                </div>
				<input id="localitaMappa" class="form-control" style="display: inline-block;" autofocus />
				<div class="input-group-addon" style="background-color: #f4f4f4; color: #444;">
                	<i class="fa fa-search" style="cursor: pointer;" onclick="initMapByAddress()"></i>
                </div>
			</div>
			</div>
		</div>
	</div>
</section>

<script src="<%= request.getContextPath() %>/resources/dist/js/mappa.js" type="text/javascript"></script>