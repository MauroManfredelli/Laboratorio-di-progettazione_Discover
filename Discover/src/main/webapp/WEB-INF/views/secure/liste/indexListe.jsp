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
			<button class="btn btn-default" id="btnNuovoItinerario" style="width: 130px;">
				<i class="fa fa-plus-circle"></i> Crea itinerario
			</button>
		</div>
	</div>

	<div class="nav-tabs-custom" style="margin-top: 10px;">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#listeAttive" data-toggle="tab"><h5 class="m-0">Liste attive</h5></a></li>
			<li><a href="#listeArchiviate" data-toggle="tab"><h5 class="m-0">Liste archiviate</h5></a></li>
			<li class="pull-right" style="display: inline-block; width: 200px; margin-top: 5px; margin-bottom: 10px;">
				<label class="font-weight-bold" style="padding-right: 20px;">Cerca:</label>
				<input type="text" id="inputCercaListe" class="form-control" onkeydown="cercaListe(this)" style="display: inline-block; width: 130px;" />
			</li>
		</ul>
		<div class="tab-content p-0">
			<div class="tab-pane active" id="listeAttive">
				<div class="row">
					<div class="col-md-12">
						<c:choose>
							<c:when test="${empty liste}">
								<div class="alert alert-info col-md-offset-4 col-md-4" style="background-color: #FFF !important; color: #00c0ef !important;">
									<div class="col-md-2 text-center">
										<i class="fa fa-info-circle" style="font-size: 2.8em;"></i>
									</div>
									<div class="col-md-10" style="font-size: 25px;">
										 <b>Non sono presenti liste!</b>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<c:forEach items="${liste}" var="lista" varStatus="indexLista">
									<div id="divLista${lista.id}">
										<div class="box box-body col-md-12 m-0">
    										<c:choose>
    											<c:when test="${empty lista.attrazioni}">
    												<div class="col-md-6 p-0">
    													<div id="carousel-example-generic-empty${lista.id}" class="carousel slide" 
																	data-ride="carousel">
															<div class="carousel-inner" style="height: 200px; max-height: 200px; background-color: #333; border-radius: 13px;">
																<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																	Nessuna attrazione
																</c:forEach>
															</div>
														</div>
    												</div>
    											</c:when>
    											<c:otherwise>
    												<div class="col-md-6" id="cotainerImmaginiLista${lista.id}">
	    												<c:forEach items="${lista.attrazioni}" var="attrazione" varStatus="indexAttrazione">
	    													<div id="carousel-example-generic${lista.id}${attrazione.id}" class="carousel slide <c:choose><c:when test='${lista.attrazioni.size() == 1}'>col-md-12</c:when><c:otherwise>col-md-6</c:otherwise></c:choose>" 
																	data-ride="carousel" style="padding: 0px; padding-bottom: 5px; padding-right: 5px;">
																<ol class="carousel-indicators">
																	<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																		<li data-target="#carousel-example-generic${lista.id}${attrazione.id}" data-slide-to="${indexFoto.index}"
																			class="<c:if test='${indexFoto.index == 0}'>active</c:if>"></li>
																	</c:forEach>
																</ol>
																<div class="carousel-inner" style="height: 250px; max-height: 250px; background-color: #333;">
																	<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
																		<div class="item <c:if test='${indexFoto.index == 0}'>active</c:if>">
																			<img
																				src="<%= request.getContextPath() %>/resources/dist/img/attrazione${attrazione.id}/${indexFoto.index + 1}.jpg"
																				style="display: block; margin: 0 auto; height: 100%;">
																		</div>
																	</c:forEach>
																</div>
																<a class="left carousel-control" href="#carousel-example-generic${lista.id}${attrazione.id}"
																	data-slide="prev"> <span class="fa fa-angle-left"></span>
																</a> <a class="right carousel-control" href="#carousel-example-generic${lista.id}${attrazione.id}"
																	data-slide="next"> <span class="fa fa-angle-right"></span>
																</a>
															</div>
	    												</c:forEach>
    												</div>
    											</c:otherwise>
    										</c:choose>
    										<div class="col-md-6">
												<div class="box" style="border: none; box-shadow: none">
													<div class="box-body" style="border: none;">
														<div class="row">
															<div class="col-md-12" style="float: left; font-size: 25px;">
																<b>${lista.nome}</b>
															</div>
														</div>
														
														<div class="row" style="padding-top: 15px;">
															<div class="col-md-12" style="float: left; font-size: 22px;">
																<c:choose>
																	<c:when test="${empty lista.idWishlist}">
																		<c:choose>
																			<c:when test="${not empty lista.dataInizio}">
																				Itininerario programmato:<br>
																				<span style="padding-left: 20px;">
																					<fmt:formatDate type="date" value="${lista.dataInizio}" pattern="dd/MM/yyyy" /> - <fmt:formatDate type="date" value="${lista.dataFine}" pattern="dd/MM/yyyy" />
																				</span>
																			</c:when>
																			<c:otherwise>
																				Itinerario senza data:<br>
																				<span style="padding-left: 20px;">
																					<c:out value="${lista.numeroGiorni}"></c:out>
																				</span>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		Wishlist
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
														
														<div class="row">
															<div class="col-md-12" style="float: left; font-size: 22px;">
																<c:out value="${lista.numeroAttrazioni}"></c:out> Attrazioni
															</div>
														</div>
													</div>
													
													<div class="box-footer" style="border: none;">
														<div class="row">
															<div class="col-md-12" style="text-align: right; font-size: 30px;">
																<span><i class="fa fa-archive text-primary" data-toggle="tooltip" title="Archivia" style="cursor: pointer;"></i></span>
																&nbsp;&nbsp;<span><i class="fa fa-trash text-danger" style="cursor: pointer;" data-toggle="tooltip" title="Elimina"></i></span>
																&nbsp;&nbsp;<span><i class="fa fa-edit text-info" style="cursor: pointer;" data-toggle="tooltip" title="Modifica"></i></span>
															</div>
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
			<div class="tab-pane" id="listeArchiviate">
				<div class="row">
					<div class="col-md-12">
						<c:choose>
							<c:when test="${empty listeArchiviate}">
								<div class="alert alert-info col-md-offset-4 col-md-4" style="background-color: #FFF !important; color: #00c0ef !important;">
									<div class="col-md-2 text-center">
										<i class="fa fa-info-circle" style="font-size: 2.8em;"></i>
									</div>
									<div class="col-md-10" style="font-size: 23px;">
										 <b>Non sono presenti liste archiviate!</b>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="/WEB-INF/views/secure/modali/modaleWishlist.jsp"/>

<script src="<%= request.getContextPath() %>/resources/dist/js/liste.js" type="text/javascript"></script>