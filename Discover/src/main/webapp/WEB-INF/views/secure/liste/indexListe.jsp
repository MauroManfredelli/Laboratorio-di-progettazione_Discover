<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!-- Content Header (Page header) -->
<section class="content-header"></section>

<!-- Main content -->
<section class="content">
	
	<div class="row">
		<div class="col-md-12 text-right">
			<button class="btn btn-primary" id="btnNuovaWishlist" style="width: 130px;" onclick="creaWishlist()">
				<i class="fa fa-plus-circle"></i> Crea wishlist
			</button>
			<button class="btn btn-primary" id="btnNuovoItinerario" style="width: 130px;" onclick="creaItinerario()">
				<i class="fa fa-plus-circle"></i> Crea itinerario
			</button>
		</div>
	</div>

	<div class="nav-tabs-custom" style="margin-top: 10px; box-shadow: none; border: none;">
		<ul class="nav nav-tabs light-blue-bg" style="border: none;">
			<li class="active"><a href="#listeAttive" data-toggle="tab"><h5 class="m-0">Liste attive</h5></a></li>
			<li><a href="#listeArchiviate" data-toggle="tab"><h5 class="m-0">Liste archiviate</h5></a></li>
			<li class="pull-right" style="display: inline-block; width: 250px; margin-top: 5px; margin-bottom: 3px; border: none;">
				<select id="inputOrdinaListe" class="form-control chosen chosen-select" data-live-search="none" onchange="ordinaListe()" style="border: none;">
					<option value="" style="border: none;">-</option>
					<option value="dataCreazione" style="border: none;">Data creazione</option>
					<option value="nome" style="border: none;">Nome</option>
					<option value="numeroAttrazioni" style="border: none;">Numero attrazioni</option>
				</select>
				<script>var inputOrdinaListe = "${inputOrdinaListe}";</script>
			</li>
			<li class="pull-right light-blue-bg" style="display: inline-block; width: 90px; margin-top: 15px; margin-bottom: 5px; border: none;">
				<label class="font-weight-bold" style="padding-right: 10px;">Ordina per:</label>
			</li>
		</ul>
		<div class="tab-content p-0">
			<div class="tab-pane active" id="listeAttive">
				<div class="row">
					<div class="col-md-12" id="containerToAppend">
						<div id="nessunaListaAttiva" class="hidden alert alert-info col-md-offset-4 col-md-4" style="background-color: #FFF !important; color: #00c0ef !important; border-radius: 13px; margin-top: 20px;">
							<div class="col-md-2 text-center">
								<i class="fa fa-info-circle" style="font-size: 2.8em;"></i>
							</div>
							<div class="col-md-10" style="font-size: 25px;">
								 <b>Non sono presenti liste!</b>
							</div>
						</div>
						<c:choose>
							<c:when test="${empty liste}">
								<script>
									$(document).ready(function() {
										$("#nessunaListaAttiva").removeClass("hidden");
									})
								</script>
							</c:when>
							<c:otherwise>
								<c:forEach items="${liste}" var="lista" varStatus="indexLista">
									<div id="divLista${lista.id}">
										<div class="box col-md-12 m-0" style="box-shadow: none; margin-top: -4px; padding-top: 10px;">
											<div class="box-body m-0" style="border: none; padding-left: 0px;">
	    										<c:choose>
	    											<c:when test="${empty lista.attrazioni}">
	    												<div class="col-md-6 p-0">
	    													<div id="carousel-example-generic-empty${lista.id}" class="carousel slide" 
																		data-ride="carousel">
																<div class="carousel-inner" style="height: 200px; max-height: 200px; background-color: #fff;">
																	<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																		Nessuna attrazione
																	</c:forEach>
																</div>
															</div>
	    												</div>
	    											</c:when>
	    											<c:otherwise>
	    												<div class="col-md-6 p-0" id="cotainerImmaginiLista${lista.id}">
	    													<div id="carousel-example-generic${lista.id}" class="carousel slide col-md-12" 
																	data-ride="carousel" style="padding: 0px; border-radius: 13px;">
																<ol class="carousel-indicators">
	    															<c:forEach items="${lista.attrazioni}" var="attrazione" varStatus="indexAttrazione">
																		<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																			<li data-target="#carousel-example-generic${lista.id}" data-slide-to="${indexFoto.index}"
																				class="<c:if test='${indexFoto.index == 0 and indexAttrazione.index == 0}'>active</c:if>"></li>
																		</c:forEach>
																	</c:forEach>
																</ol>
																<div class="carousel-inner" style="height: 300px; max-height: 300px; width: auto; background-color: #fff;">
	    															<c:forEach items="${lista.attrazioni}" var="attrazione" varStatus="indexAttrazione">
																		<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																			<div class="item <c:if test='${indexFoto.index == 0 and indexAttrazione.index == 0}'>active</c:if>">
																				<img
																					src="<%= request.getContextPath() %>/resources/dist/img/attrazione${attrazione.id}/${indexFoto.index + 1}.jpg"
																					style="height: 300px; max-height: 300px; width: auto; margin: 0 auto;;">
																			</div>
																		</c:forEach>
																	</c:forEach>
																</div>
																<a class="left carousel-control" href="#carousel-example-generic${lista.id}"
																	data-slide="prev"> <span class="fa fa-angle-left"></span>
																</a> <a class="right carousel-control" href="#carousel-example-generic${lista.id}"
																	data-slide="next"> <span class="fa fa-angle-right"></span>
																</a>
															</div>
	    												</div>
	    											</c:otherwise>
	    										</c:choose>
	    										<div class="col-md-6">
													<div class="box" style="border: none; box-shadow: none">
														<div class="box-body" style="border: none;">
															<div class="row">
																<div class="col-md-12" style="float: left; font-size: 25px; padding-right: 0px;">
																	<a href="/discover/liste/${lista.id}" style="color: #333;"><span class="text-name"><b>${lista.nome}</b></span></a>
																	<span><i class="fa fa-ellipsis-v pull-right text-primary" style="cursor: pointer;" data-toggle="tooltip" title="Modifica informazioni" onclick="modificaLista('${lista.id}', '${lista.nome}')"></i></span>
																	
																</div>
															</div>
															
															<div class="row" style="padding-top: 15px;">
																<div class="col-md-12" style="float: left; font-size: 22px;">
																	<c:choose>
																		<c:when test="${empty lista.idWishlist}">
																			<c:choose>
																				<c:when test="${not empty lista.dataInizio}">
																					<span class="font-weight-bold">Itininerario programmato:</span><br>
																					<input type="hidden" id="tipoLista${lista.id}" value="PROGRAMMATO" />
																					<span style="padding-left: 20px;">
																						<fmt:formatDate type="date" value="${lista.dataInizio}" pattern="dd/MM/yyyy" /> - <fmt:formatDate type="date" value="${lista.dataFine}" pattern="dd/MM/yyyy" />
																					</span>
																				</c:when>
																				<c:otherwise>
																					<span class="font-weight-bold">Itinerario senza data:</span><br>
																					<input type="hidden" id="tipoLista${lista.id}" value="GIORNI" />
																					<span style="padding-left: 20px;">
																						<c:out value="${lista.numeroGiorni}"></c:out> giorni
																					</span>
																				</c:otherwise>
																			</c:choose>
																		</c:when>
																		<c:otherwise>
																			<span class="font-weight-bold">Wishlist</span>
																			<input type="hidden" id="tipoLista${lista.id}" value="WISHLIST" />
																		</c:otherwise>
																	</c:choose>
																</div>
															</div>
															
															<div class="row">
																<div class="col-md-12" style="float: left; font-size: 22px;">
																	<span><c:out value="${lista.numeroAttrazioni}"></c:out> attrazioni</span>
																</div>
															</div>
														</div>
			    									</div>
	    										</div>
	    										<div class="box-footer p-0" style="border: none;">
													<div class="row">
														<div class="col-md-12" style="text-align: right; font-size: 30px;">
															<c:if test="${not empty lista.idItinerario}">
																<span id="confermaItinerario"><i class="fa fa-check-circle <c:choose><c:when test="${lista.confermato == 'true'}">text-success</c:when><c:otherwise>text-action</c:otherwise></c:choose>" style="cursor: pointer;" data-toggle="tooltip" title="Conferma itinerario" onclick="confermaItinerario('${lista.id}', '${lista.idItinerario}')"></i></span>
															</c:if>
															<span id="archiviaLista">&nbsp;&nbsp;<i class="fa fa-download text-primary" data-toggle="tooltip" title="Archivia" style="cursor: pointer;" onclick="archiviaLista('${lista.id}')"></i></span>
															<span class="hidden" id="recuperaLista">&nbsp;&nbsp;<i class="fa fa-upload text-primary" data-toggle="tooltip" title="Recupera" style="cursor: pointer;" onclick="recuperaLista('${lista.id}')"></i></span>
															&nbsp;&nbsp;<span><i class="fa fa-trash text-primary" style="cursor: pointer;" data-toggle="tooltip" title="Elimina" onclick="eliminaLista('${lista.id}')"></i></span>
															&nbsp;&nbsp;<span>
																<a href="/discover/liste/${lista.id}" style="color: #333; cursor:pointer"><span class="fa fa-edit text-primary" data-toggle="tooltip" title="<c:choose><c:when test="${empty lista.idItinerario}">Modifica attrazioni</c:when><c:otherwise>Modifica visite</c:otherwise></c:choose>"></span></a></span>
														</div>
													</div>
												</div>
	    									</div>
	    								</div>
	    							</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="listeArchiviate" style="border-top-right-radius: 20px;">
				<div class="row">
					<div class="col-md-12" id="containerToAppend">
						<div id="nessunaListaArchiviata" class="hidden alert alert-info col-md-offset-4 col-md-4" style="background-color: #FFF !important; color: #00c0ef !important; border-radius: 13px; margin-top: 20px;">
							<div class="col-md-2 text-center">
								<i class="fa fa-info-circle" style="font-size: 2.8em;"></i>
							</div>
							<div class="col-md-10" style="font-size: 23px;">
								 <b>Non sono presenti liste archiviate!</b>
							</div>
						</div>
						<c:choose>
							<c:when test="${empty listeArchiviate}">
								<script>
									$(document).ready(function() {
										$("#nessunaListaArchiviata").removeClass("hidden");
									})
								</script>
							</c:when>
							<c:otherwise>
								<c:forEach items="${listeArchiviate}" var="lista" varStatus="indexLista">
									<div id="divLista${lista.id}">
										<div class="box col-md-12 m-0" style="box-shadow: none; margin-top: -3px; padding-top: 10px;">
											<div class="box-body m-0" style="border: none; padding-left: 0px;">
	    										<c:choose>
	    											<c:when test="${empty lista.attrazioni}">
	    												<div class="col-md-6 p-0">
	    													<div id="carousel-example-generic-empty${lista.id}" class="carousel slide" 
																		data-ride="carousel">
																<div class="carousel-inner" style="height: 200px; max-height: 200px; background-color: #fff;">
																	<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																		Nessuna attrazione
																	</c:forEach>
																</div>
															</div>
	    												</div>
	    											</c:when>
	    											<c:otherwise>
	    												<div class="col-md-6 p-0" id="cotainerImmaginiLista${lista.id}">
		    												<div id="carousel-example-generic${lista.id}" class="carousel slide col-md-12" 
																	data-ride="carousel" style="padding: 0px; border-radius: 13px;">
																<ol class="carousel-indicators">
	    															<c:forEach items="${lista.attrazioni}" var="attrazione" varStatus="indexAttrazione">
																		<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																			<li data-target="#carousel-example-generic${lista.id}" data-slide-to="${indexFoto.index}"
																				class="<c:if test='${indexFoto.index == 0 and indexAttrazione.index == 0}'>active</c:if>"></li>
																		</c:forEach>
																	</c:forEach>
																</ol>
																<div class="carousel-inner" style="height: 300px; max-height: 300px; width: auto; background-color: #fff;">
	    															<c:forEach items="${lista.attrazioni}" var="attrazione" varStatus="indexAttrazione">
																		<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																			<div class="item <c:if test='${indexFoto.index == 0 and indexAttrazione.index == 0}'>active</c:if>">
																				<img
																					src="<%= request.getContextPath() %>/resources/dist/img/attrazione${attrazione.id}/${indexFoto.index + 1}.jpg"
																					style="height: 300px; max-height: 300px; width: auto; margin: 0 auto;">
																			</div>
																		</c:forEach>
																	</c:forEach>
																</div>
																<a class="left carousel-control" href="#carousel-example-generic${lista.id}"
																	data-slide="prev"> <span class="fa fa-angle-left"></span>
																</a> <a class="right carousel-control" href="#carousel-example-generic${lista.id}"
																	data-slide="next"> <span class="fa fa-angle-right"></span>
																</a>
															</div>
	    												</div>
	    											</c:otherwise>
	    										</c:choose>
	    										<div class="col-md-6">
													<div class="box" style="border: none; box-shadow: none">
														<div class="box-body" style="border: none;">
															<div class="row">
																<div class="col-md-12" style="float: left; font-size: 25px; color: #333;">
																	<!-- <a href="/discover/liste/${lista.id}"> --><span class="text-name"><b>${lista.nome}</b></span><!-- </a> -->
																</div>
															</div>
															
															<div class="row" style="padding-top: 15px;">
																<div class="col-md-12" style="float: left; font-size: 22px;">
																	<c:choose>
																		<c:when test="${empty lista.idWishlist}">
																			<c:choose>
																				<c:when test="${not empty lista.dataInizio}">
																					<span class="font-weight-bold">Itininerario programmato:</span><br>
																					<input type="hidden" id="tipoLista${lista.id}" value="PROGRAMMATO" />
																					<span style="padding-left: 20px;">
																						<fmt:formatDate type="date" value="${lista.dataInizio}" pattern="dd/MM/yyyy" /> - <fmt:formatDate type="date" value="${lista.dataFine}" pattern="dd/MM/yyyy" />
																					</span>
																				</c:when>
																				<c:otherwise>
																					<span class="font-weight-bold">Itinerario senza data:</span><br>
																					<span style="padding-left: 20px;">
																						<c:out value="${lista.numeroGiorni}"></c:out> giorni
																						<input type="hidden" id="tipoLista${lista.id}" value="GIORNI" />
																					</span>
																				</c:otherwise>
																			</c:choose>
																		</c:when>
																		<c:otherwise>
																			<span class="font-weight-bold">Wishlist</span>
																			<input type="hidden" id="tipoLista${lista.id}" value="WISHLIST" />
																		</c:otherwise>
																	</c:choose>
																</div>
															</div>
															
															<div class="row">
																<div class="col-md-12" style="float: left; font-size: 22px;">
																	<span><c:out value="${lista.numeroAttrazioni}"></c:out> attrazioni</span>
																</div>
															</div>
														</div>
			    									</div>
	    										</div>
	    										<div class="box-footer p-0" style="border: none;">
													<div class="row">
														<div class="col-md-12" style="text-align: right; font-size: 30px;">
															<c:if test="${not empty lista.idItinerario}">
																<span id="confermaItinerario" class="hidden"><i class="fa fa-check-circle text-action" style="cursor: pointer;" data-toggle="tooltip" title="Conferma itinerario" onclick="confermaItinerario('${lista.id}', '${lista.idItinerario}')"></i></span>
															</c:if>
															<span class="hidden" id="archiviaLista">&nbsp;&nbsp;<i class="fa fa-download text-action" data-toggle="tooltip" title="Archivia" style="cursor: pointer;" onclick="archiviaLista('${lista.id}')"></i></span>
															<span id="recuperaLista">&nbsp;&nbsp;<i class="fa fa-upload text-action" data-toggle="tooltip" title="Recupera" style="cursor: pointer;" onclick="recuperaLista('${lista.id}')"></i></span>
															&nbsp;&nbsp;<span><i class="fa fa-trash text-action" style="cursor: pointer;" data-toggle="tooltip" title="Elimina" onclick="eliminaLista('${lista.id}')"></i></span>
															&nbsp;&nbsp;<span><i class="fa fa-edit text-action" style="cursor: pointer;" data-toggle="tooltip" title="Modifica" onclick="modificaLista('${lista.id}', '${lista.nome}')"></i></span>
														</div>
													</div>
												</div>
	    									</div>
	    								</div>
	    							</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="/WEB-INF/views/secure/modali/modaleWishlist.jsp"/>
<jsp:include page="/WEB-INF/views/secure/modali/modaleItinerario.jsp"/>

<script src="<%= request.getContextPath() %>/resources/dist/js/liste.js" type="text/javascript"></script>