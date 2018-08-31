<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!-- Bootstrap slider -->
<link href="<%= request.getContextPath() %>/resources/plugin/bootstrap-slider/slider.css" rel="stylesheet" type="text/css" />
<script src="<%= request.getContextPath() %>/resources/plugin/bootstrap-slider/bootstrap-slider.js" type="text/javascript"></script>

<!-- Content Header (Page header) -->
<section class="content-header"></section>

<!-- Main content -->
<section class="content">
	<jsp:include page="sceltaLista.jsp"></jsp:include>

	<div class="nav-tabs-custom">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#cercaAttrazioni" data-toggle="tab"><h5 class="m-0">Attrazioni</h5></a></li>
			<li><a href="#cercaItinerari" data-toggle="tab"><h5 class="m-0">Itinerari</h5></a></li>
			<li><a href="#cercaUtenti" data-toggle="tab"><h5 class="m-0">Utenti</h5></a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="cercaAttrazioni">
				<div class="row" style="margin-bottom: 20px;">
					<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
						<b>Nome:</b>
					</div>
					<div class="col-md-9">
						<input class="form-control" placeholder="Nome" style="max-width: 600px;" />
					</div>
				</div>
				<div class="row" style="margin-bottom: 20px;">
					<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
						<b>Località:</b>
					</div>
					<div class="col-md-9">
						<input class="form-control" placeholder="Località" style="max-width: 600px;" />
					</div>
				</div>
				<div class="row" style="margin-bottom: 20px;">
					<div class="col-md-3 text-right" style="font-size: 15px; margin-top: 5px; margin-bottom: 5px;" id="labelLunghezzaMassima">
						<b>Lontananza (1 Km - 30 Km):</b>
					</div>
					<div class="col-md-5" style="margin-top: 5px;">
						<input type="text" value="" class="slider form-control" data-slider-min="1" data-slider-max="30" data-slider-step="1" data-slider-value="[1,30]" data-slider-orientation="horizontal" data-slider-selection="before" data-slider-tooltip="show" data-slider-id="blue" style="max-width: 600px;">
					</div>
				</div>
				<div class="row" style="margin-bottom: 20px;">
					<div class="col-md-3 text-right" style="font-size: 15px; margin-top: 5px; margin-bottom: 5px;" id="labelTipologiaAttr">
						<b>Tipologia:</b>
					</div>
					<div class="col-md-3">
						<ul class="list-group m-0">
							<li class="list-group-item p-0" style="border: none;">
								<div class="checkbox icheck" onclick="checkAll()">
					            	<label>
					                	<input type="checkbox" class="icheck-checbox" id="checkAllTipiAttrazioni" checked>&nbsp;&nbsp;&nbsp;Tutte
					                </label>
					            </div>
							</li>
							<li class="list-group-item" style="border: none; padding-top: 0px; padding-left: 80px;">
								<ul class="list-group m-0">
									<c:forEach items="${tipologie}" var="tipo" varStatus="indexTipo">
										<li class="list-group-item p-0" style="border: none;">
											<div class="checkbox icheck">
								            	<label>
								                	<input type="checkbox" name="tipoAttrazione" id="tipoAttrazione${indexTipo.index}" class="icheck-checbox" value="${tipo.id}" checked>&nbsp;&nbsp;&nbsp;${tipo.descrizione}
								                </label>
								            </div>
										</li>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</div>
					<div class="col-md-1 text-right" style="font-size: 15px; margin-top: 5px; margin-bottom: 5px;" id="labelStatoAttrazione">
						<b>Stato:</b>
					</div>
					<div class="col-md-3">
						<ul class="list-group m-0">
							<li class="list-group-item p-0" style="border: none;">
								<div class="checkbox icheck">
					            	<label>
					                	<input type="checkbox" class="icheck-checbox" checked>&nbsp;&nbsp;&nbsp;Tutti
					                </label>
					            </div>
							</li>
							<li class="list-group-item" style="border: none; padding-top: 0px; padding-left: 80px;">
								<ul class="list-group m-0">
									<c:forEach items="${stati}" var="stato" varStatus="indexStato">
										<li class="list-group-item p-0" style="border: none;">
											<div class="checkbox icheck">
								            	<label>
								                	<input type="checkbox" name="statoAttrazione" id="statoAttrazione${indexStato.index}" class="icheck-checbox" value="${stato.id}" checked>&nbsp;&nbsp;&nbsp;${stato.descrizione}
								                </label>
								            </div>
										</li>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<div class="row" style="margin-bottom: 20px;">
					<div class="col-md-5 text-center">
						<a href="#" >Altri filtri...</a> 
					</div>
				</div>
				<div class="row" style="margin-bottom: 20px;">
					<div class="col-md-5 text-center">
						<button type="button" class="btn btn-success">
							<i class="fa fa-search"></i> Cerca
						</button>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="cercaItinerari" style="padding: 10px;">
				<div class="alert alert-warning text-center">
					<i class="fa fa-warning" style="font-size: 2.5em;"></i> <b style="font-size: 25px; padding-left: 10px;">Non diponibile</b>
				</div>
			</div>
			<div class="tab-pane" id="cercaUtenti" style="padding: 10px;">
				<div class="alert alert-warning text-center">
					<i class="fa fa-warning" style="font-size: 2.5em;"></i> <b style="font-size: 25px; padding-left: 10px;">Non diponibile</b>
				</div>
			</div>
		</div>
	</div>
</section>


<script src="<%= request.getContextPath() %>/resources/plugin/bootstrap-slider/bootstrap-slider.js" type="text/javascript"></script>
<script src="<%= request.getContextPath() %>/resources/dist/js/cerca.js" type="text/javascript"></script>
