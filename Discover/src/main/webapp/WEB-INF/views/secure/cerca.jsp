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
	src="https://maps.googleapis.com/maps/api/js?libraries=drawing,geometry,geocode,places&client=gme-gestoredelservizi1"></script>

<!-- Bootstrap slider -->
<link href="<%= request.getContextPath() %>/resources/plugin/bootstrap-slider/slider.css" rel="stylesheet" type="text/css" />
<script src="<%= request.getContextPath() %>/resources/plugin/bootstrap-slider/bootstrap-slider.js" type="text/javascript"></script>

<!-- Content Header (Page header) -->
<section class="content-header"></section>

<!-- Main content -->
<section class="content">
	<jsp:include page="sceltaLista.jsp"></jsp:include>
	
	<c:if test="${listAttrazioni != null && empty listAttrazioni}">
		<script>
			$(document).ready(function() {
				swal({
					title: '',
					text: 'La ricerca non ha prodotto risultati.',
					html: true,
					showCancelButton: false,
					confirmButtonText: 'Continua',
					confirmButtonColor: '#0066cc',
				});
			});
		</script>
	</c:if>
	
	<div class="nav-tabs-custom" style="box-shadow: none; border: none;">
		<ul class="nav nav-tabs light-blue-bg" style="border: none;">
			<li class="active"><a href="#cercaAttrazioni" data-toggle="tab"><h5 class="m-0">Attrazioni</h5></a></li>
			<li><a href="#cercaItinerari" data-toggle="tab"><h5 class="m-0">Itinerari</h5></a></li>
			<li><a href="#cercaUtenti" data-toggle="tab"><h5 class="m-0">Utenti</h5></a></li>
		</ul>
		<div class="tab-content light-blue-bg p-0" style="border: none;">
			<div class="tab-pane active" id="cercaAttrazioni">
				<div>
					<div class="col-md-12 p-0">
					<div class="<c:if test='${listAttrazioni != null and not empty listAttrazioni}'>col-md-5</c:if>" style="padding-left: 0px;">
					<div class="box box-body" id="boxRicercaAttrazioni" style="border: none;">
						<form:form action="/discover/attrazioni/cerca" method="post" modelAttribute="parametriRicerca" id="ricercaAttrazioniForm" class="m-0">
							<form:hidden path="latCentro" />
							<form:hidden path="longCentro" />
							<div class="row" style="margin-bottom: 20px;">
								<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
									<b>Nome attrazione:</b>
								</div>
								<div class="col-md-9">
									<form:input path="nomeAttrazione" class="form-control" placeholder="Tutte le attrazioni" style="max-width: 600px;" />
								</div>
							</div>
							<div class="row" style="margin-bottom: 20px;">
								<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
									<b>Località:</b>
								</div>
								<div class="col-md-9">
									<form:input path="localita" class="form-control" placeholder="Tutte le località" style="max-width: 600px;" onchange="checkPosCentro()" />
								</div>
							</div>
							<div class="row" style="margin-bottom: 20px;" id="lontananzaForm">
								<div class="col-md-3 text-right" style="font-size: 15px; margin-top: 5px; margin-bottom: 5px;" id="labelLunghezzaMassima">
									<b>Lontananza <span id="resSlider">(0 Km - 50 Km)</span>:</b>
								</div>
								<div class="col-md-5" style="margin-top: 5px;">
									<input type="text" id="sliderLontananza" value="" class="slider form-control" data-slider-min="0" data-slider-max="50" data-slider-step="1" data-slider-value="[0,50]" data-slider-orientation="horizontal" data-slider-selection="before" data-slider-id="blue" data-slider-tooltip="hide" style="max-width: 600px;">
									<form:hidden path="lontananzaMinima" />
									<form:hidden path="lontananzaMassima" />
								</div>
							</div>
							<div class="row" style="margin-bottom: 20px;">
								<div class="col-md-3 text-right" style="font-size: 15px; margin-top: 5px; margin-bottom: 5px;" id="labelTipologiaAttr">
									<b>Tipologia:</b>
								</div>
								<div class="col-md-9">
									<ul class="list-group m-0">
										<li class="list-group-item p-0" style="border: none;">
											<div class="checkbox icheck">
								            	<label>
								                	<input type="checkbox" class="icheck-checbox" id="checkAllTipiAttrazioni">&nbsp;&nbsp;&nbsp;Tutte
								                </label>
								                <c:if test="${empty parametriRicerca.tipoAttrazione}">
								                	<script>
								                		$(document).ready(function() {
									                		$('input[name=tipoAttrazione]').iCheck('check');
								                		});
								                	</script>
								                </c:if>
								            </div>
										</li>
										<li class="list-group-item" style="border: none; padding-top: 0px; padding-left: 80px;">
											<ul class="list-group m-0">
												<c:forEach items="${tipologie}" var="tipo" varStatus="indexTipo">
													<li class="list-group-item p-0" style="border: none;">
														<div class="checkbox icheck">
											            	<label>
											                	<form:checkbox path="tipoAttrazione" id="tipoAttrazione${indexTipo.index}" class="icheck-checbox" value="${tipo.id}" />&nbsp;&nbsp;&nbsp;${tipo.descrizione}
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
								<div class="col-md-3 text-right" style="font-size: 15px; margin-top: 5px; margin-bottom: 5px;" id="labelStatoAttrazione">
									<b>Stato:</b>
								</div>
								<div class="col-md-9">
									<ul class="list-group m-0">
										<li class="list-group-item p-0" style="border: none;">
											<div class="checkbox icheck">
								            	<label>
								                	<input type="checkbox" class="icheck-checbox" id="checkAllStatiAttrazioni">&nbsp;&nbsp;&nbsp;Tutti
								                </label>
								                <c:if test="${empty parametriRicerca.statoAttrazione}">
								                	<script>
								                		$(document).ready(function() {
								                			$('input[name=statoAttrazione]').iCheck('check');
								                		});
								                	</script>
								                </c:if>
								            </div>
										</li>
										<li class="list-group-item" style="border: none; padding-top: 0px; padding-left: 80px;">
											<ul class="list-group m-0">
												<c:forEach items="${stati}" var="stato" varStatus="indexStato">
													<li class="list-group-item p-0" style="border: none;">
														<div class="checkbox icheck">
											            	<label>
											                	<form:checkbox path="statoAttrazione" id="statoAttrazione${indexStato.index}" class="icheck-checbox" value="${stato.id}" />&nbsp;&nbsp;&nbsp;${stato.descrizione}
											                </label>
											            </div>
													</li>
												</c:forEach>
											</ul>
										</li>
									</ul>
								</div>
							</div>
							
							<div class="row hidden" style="margin-bottom: 20px;" id="filtroAltro1">
								<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
									<b>Valutazione media:</b>
								</div>
								<div class="col-md-9">
									<div class="input-group" style="width: 230px;">
										<form:input path="valutazioneMedia" class="form-control" placeholder="Valutazione media" />
										<span class="input-group-addon"><i class="fa fa-star"></i></span>
									</div>
								</div>
							</div>
							
							<div class="row hidden" style="margin-bottom: 20px;" id="filtroAltro2">
								<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
									<b>Numero visite:</b>
								</div>
								<div class="col-md-9">
									<div class="input-group" style="width: 230px;">
										<form:input path="numeroVisite" class="form-control" placeholder="Numero visite" />
										<span class="input-group-addon"><i class="fa fa-check-circle"></i></span>
									</div>
								</div>
							</div>
							
							<div class="row hidden" style="margin-bottom: 20px;" id="filtroAltro3">
								<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
									<b>Numero di recensioni:</b>
								</div>
								<div class="col-md-9">
									<div class="input-group" style="width: 230px;">
										<form:input path="numeroRecensioni" class="form-control" placeholder="Numero di recensioni" />
										<span class="input-group-addon"><i class="fa fa-comment"></i></span>
									</div>
								</div>
							</div>
							
							<div class="row hidden" style="margin-bottom: 20px;" id="filtroAltro4">
								<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
									<b>Percentuale reazioni positive:</b>
								</div>
								<div class="col-md-9">
									<div class="input-group" style="width: 230px;">
										<form:input path="percentualeReazioniPositive" class="form-control" placeholder="Percentuale %" />
										<span class="input-group-addon"><i class="fa fa-thumbs-o-up"></i></span>
									</div>
								</div>
							</div>
							
							<div class="row hidden" style="margin-bottom: 20px;" id="filtroAltro5">
								<div class="col-md-3 text-right" style="float: left; font-size: 15px; margin-top: 5px; margin-bottom: 5px;">
									
								</div>
								<div class="col-md-9">
									<div class="checkbox icheck">
						            	<label>
						                	<form:checkbox path="visitata" id="visitata1" value="1" class="icheck-checbox" />&nbsp;&nbsp;Visitata&nbsp;&nbsp;
						                	<form:checkbox path="visitata" id="visitata2" value="2" class="icheck-checbox" />&nbsp;&nbsp;Non ancora visitata
						                </label>
						            </div>
						            <c:if test="${empty parametriRicerca.visitata}">
					                	<script>
					                		$(document).ready(function() {
					                			$('input[name=visitata]').iCheck('check');
					                		});
					                	</script>
					                </c:if>
								</div>
							</div>
							
							<div class="row" style="margin-bottom: 20px;" id="btnAltriFiltri" onclick="mostraAltriFiltri('true')">
								<div class="col-md-5 text-center">
									<a href="javascript:void(0)" >Altri filtri...</a> 
								</div>
							</div>
							<div class="row hidden" style="margin-bottom: 20px;" id="btnMenoFiltri" onclick="mostraAltriFiltri('false')">
								<div class="col-md-5 text-center">
									<a href="javascript:void(0)" >Mostra meno.</a> 
								</div>
							</div>
							
							<div class="row" style="margin-bottom: 20px;">
								<div class="col-md-5 text-center">
									<button type="button" class="btn btn-primary" onclick="cercaAttrazioni()">
										<i class="fa fa-search"></i> Cerca
									</button>
								</div>
							</div>
						</form:form>
					</div>
					</div>
					
					<div class="col-md-7 p-0 <c:if test='${listAttrazioni == null or empty listAttrazioni}'>d-none</c:if>" style="padding-right: 0px; overflow: hidden;">
						<div id="boxRisultatiAttrazioni">
							<c:forEach items="${listAttrazioni}" var="attrazione" varStatus="indexAttrazione">
								<script>
									$(document).ready(function() {
										$(".content").css("padding-left", "30px")
										 		     .css("padding-right", "30px");
									})
								</script>
								<div class="panel rounded-box-desktop" style="padding-right: 10px;">
								    <div class="box-header with-border" >
								    	<div style="padding-left: 20px;">
									    	<h3 class="m-0">
									        	<a style="cursor: pointer; color: #444;" href="/discover/attrazione/${attrazione.id}" target="_blank"><span class="text-name"><b style="float: left;">${attrazione.nome}</b></span></a>
									        	<span class="pull-right" style="padding-right: 20px;">
									        		<a class="fa fa-info-circle text-primary" data-toggle="tooltip" title="Dettagli" style="cursor: pointer;" href="/discover/attrazione/${attrazione.id}" target="_blank"></a>
									        	</span>
									        	<br>
									        	<c:choose>
									        		<c:when test="${attrazione.stato.id == '1'}">
									        			<small class="text-orange" style="float: left; padding-top: 10px;"><i class="fa fa-star"></i> ${attrazione.stato.descrizione}</small>
									        		</c:when>
									        		<c:when test="${attrazione.stato.id == '2'}">
									        			<small class="text-orange" style="float: left; padding-top: 10px;"><i class="fa fa-binoculars"></i> ${attrazione.stato.descrizione}</small>
									        		</c:when>
									        		<c:when test="${attrazione.stato.id == '3'}">
									        			<small class="text-success" style="float: left; padding-top: 10px;"><i class="fa fa-check"></i> ${attrazione.stato.descrizione}</small>
									        		</c:when>
									        		<c:when test="${attrazione.stato.id == '4'}">
									        			<small class="text-gray" style="float: left; padding-top: 10px;">${attrazione.stato.descrizione}</small>
									        		</c:when>
									        	</c:choose>
									      	</h3>
										</div>
								    </div>
							    	<div class="box-body row">
							    		<div class="col-md-12">
							    			<div id="carousel-example-generic${attrazione.id}" class="carousel slide col-md-6 p-0" 
											data-ride="carousel" style="padding-bottom: 20px;">
												<ol class="carousel-indicators">
													<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
														<li data-target="#carousel-example-generic${attrazione.id}" data-slide-to="${indexFoto.index}"
															class="<c:if test='${indexFoto.index == 0}'>active</c:if>"></li>
													</c:forEach>
												</ol>
												<div class="carousel-inner" style="height: 250px; max-height: 300px; background-color: #fff; border-radius: 13px;">
													<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
														<div class="item <c:if test='${indexFoto.index == 0}'>active</c:if>">
															<img
																src="<%= request.getContextPath() %>/resources/dist/img/attrazione${attrazione.id}/${indexFoto.index + 1}.jpg"
																style="margin: 0 auto; height: 250px; max-height: 300px; width: auto;">
														</div>
													</c:forEach>
												</div>
												<a class="left carousel-control" href="#carousel-example-generic${attrazione.id}"
													data-slide="prev"> <span class="fa fa-angle-left"></span>
												</a> <a class="right carousel-control" href="#carousel-example-generic${attrazione.id}"
													data-slide="next"> <span class="fa fa-angle-right"></span>
												</a>
											</div>
											
											<div class="col-md-6" style="padding-top: 20px;">
												<div class="row">
													<div class="col-md-4 text-center" style="float: left;">
														<img src="${attrazione.userInserimento.imageUrl}" class="img-circle" alt="User Image" style="width: 60px; cursor: pointer;">
													</div>
													<div class="col-md-8 p-0" style="float: left;">
														<span style="font-size: 22px;">${attrazione.userInserimento.userName}</span>
														<span style="padding-left: 8px; font-size:18px;">(${attrazione.userInserimento.livello}&nbsp;<i class="fa fa-user text-primary"></i><i class="fa fa-level-up text-primary"></i>)</span>
														<br>
														<small><i class="fa fa-clock-o"></i> <fmt:formatDate type="date" value="${attrazione.dataInserimento}" pattern="dd/MM/yyyy" /></small>
													</div>
												</div>
												
												<div class="row" style="padding-top: 15px;">
													<div class="col-md-12" style="float: left; font-size: 18px;">
														${attrazione.posizione.descrizione}
													</div>
												</div>
												
												<div class="row">
													<div class="col-md-12" style="float: left; font-size: 18px;">
														<b>${attrazione.tipoAttrazione.descrizione}</b>
													</div>
												</div>
												
												<div class="row">
													<div class="col-md-12" style="float: left; font-size: 18px; text-transform: capitalize">
														${fn:toLowerCase(fn:replace(attrazione.accesso, '_', ' '))}
													</div>
												</div>
												
												<%--<div class="row pull-right" id="btnShowDettaggio${attrazione.id}">
													<a type="button" onclick="mostraDescrizioneDettaglio('${attrazione.id}')" style="cursor: pointer;">Altro...</a>
												</div>
												<div class="row hidden" id="descrizioneDettaggio${attrazione.id}">
													<div class="col-md-12" style="float: left;">
														<b>Descrizione:</b><br>
														${attrazione.descrizione}<br>
														<a type="button" class="pull-right" onclick="mostraDescrizioneDettaglio('${attrazione.id}')" style="cursor: pointer;">Mostra meno</a>
													</div>
												</div>
												<script>
													function mostraDescrizioneDettaglio(id) {
														if($("#descrizioneDettaggio"+id).hasClass("hidden")) {
															$("#descrizioneDettaggio"+id).removeClass("hidden");
															$("#btnShowDettaggio"+id).addClass("hidden");
														} else {
															$("#descrizioneDettaggio"+id).addClass("hidden");
															$("#btnShowDettaggio"+id).removeClass("hidden");
														}
													}
												</script>--%>
												
												<c:choose>
									        		<c:when test="${attrazione.stato.id == '1' or attrazione.stato.id == '2'}">
														<div class="row" style="padding-top: 15px;">
															<div class="col-md-12" style="float: left; font-size: 22px;">
																<span><i class="far fa-thumbs-up" data-toggle="tooltip" title="Mi piace"></i> <span id="num">${attrazione.reazioniPositive}</span></span>
																&nbsp;&nbsp;<span><i class="far fa-thumbs-down" data-toggle="tooltip" title="Non mi piace"></i> <span id="num">${attrazione.reazioniNegative}</span></span>
																&nbsp;&nbsp;<span><i class="fas fa-check" data-toggle="tooltip" title="Visite confermate"></i> <span id="num">${attrazione.numeroVisite}</span></span>
															</div>
														</div>
													</c:when>
													<c:when test="${attrazione.stato.id == '3' or attrazione.stato.id == '4'}">
														<div class="row" style="padding-top: 15px;">
															<div class="col-md-12" style="float: left; font-size: 22px;">
																<span><i class="fa fa-star" data-toggle="tooltip" title="Valutazione" style="cursor: pointer;"></i> <span id="num">${attrazione.valutazioneMedia}</span></span>
																&nbsp;&nbsp;<span><i class="fa fa-check-circle" data-toggle="tooltip" title="Visite confermate" style="cursor: pointer;"></i> <span id="num">${attrazione.numeroVisite}</span></span>
															</div>
														</div>
													</c:when>
												</c:choose>
												
												<div class="row" style="padding-top: 15px;">
													<div class="col-md-12" style="text-align: right; font-size: 30px;">
														&nbsp;&nbsp;<span><i class="fas fa-thumbs-up text-action" data-toggle="tooltip" title="Mi piace"  style="cursor: pointer;"></i></span>
														&nbsp;&nbsp;<span><i class="fas fa-thumbs-down text-action" data-toggle="tooltip" title="Non mi piace"  style="cursor: pointer;"></i></span>
														&nbsp;&nbsp;<span><i class="fa fa-comment text-action" data-toggle="tooltip" title="Aggiungi recensione"  style="cursor: pointer;"></i></span>
														&nbsp;&nbsp;<span><i class="fa fa-suitcase text-action" data-toggle="tooltip" title="Aggiungi alla lista" id="btnAggiungiAttrazioneLista${attrazione.id}" style="cursor: pointer;" onclick="aggiungiAttrazioneToLista('${attrazione.id}')"></i></span>
														&nbsp;&nbsp;<span><i class="fa fa-check-circle text-action" style="cursor: pointer;" data-toggle="tooltip" title="Conferma visita"></i></span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="cercaItinerari" style="padding: 10px;">
				<div class="alert alert-warning text-center">
					<i class="fa fa-warning" style="font-size: 2.5em;"></i> <b style="font-size: 25px; padding-left: 10px;">Non disponibile</b>
				</div>
			</div>
			<div class="tab-pane" id="cercaUtenti" style="padding: 10px;">
				<div class="alert alert-warning text-center">
					<i class="fa fa-warning" style="font-size: 2.5em;"></i> <b style="font-size: 25px; padding-left: 10px;">Non disponibile</b>
				</div>
			</div>
		</div>
	</div>
</section>


<script src="<%= request.getContextPath() %>/resources/plugin/bootstrap-slider/bootstrap-slider.js" type="text/javascript"></script>
<script src="<%= request.getContextPath() %>/resources/dist/js/cerca.js" type="text/javascript"></script>
