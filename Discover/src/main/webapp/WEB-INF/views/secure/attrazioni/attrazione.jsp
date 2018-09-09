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
	<jsp:include page="../sceltaLista.jsp"></jsp:include>
	
	<div class="panel box box-primary">
	    <div class="box-header with-border">
	    	<div style="padding-left: 20px;">
		    	<h3 class="m-0">
		        	<b style="float: left;">${attrazione.nome}</b>
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
					<div class="carousel-inner" style="height: 300px; max-height: 300px; background-color: #FFF; border-radius: 13px;">
						<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
							<div class="item <c:if test='${indexFoto.index == 0}'>active</c:if>">
								<img
									src="<%= request.getContextPath() %>/resources/dist/img/attrazione${attrazione.id}/${indexFoto.index + 1}.jpg"
									style="display: block; margin: 0 auto; height: 100%;">
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
						<div class="col-md-2 text-center" style="float: left;">
							<img src="${attrazione.userInserimento.imageUrl}" class="img-circle" alt="User Image" style="width: 60px; cursor: pointer;">
						</div>
						<div class="col-md-10 p-0" style="float: left;">
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
					
					<div class="row pull-right" id="btnShowDettaggio${attrazione.id}">
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
					</script>
					
					<c:choose>
		        		<c:when test="${attrazione.stato.id == '1' or attrazione.stato.id == '2'}">
							<div class="row" style="padding-top: 15px;">
								<div class="col-md-12" style="float: left; font-size: 22px;">
									<span><i class="fa fa-thumbs-o-up" data-toggle="tooltip" title="Mi piace"></i> <span id="num">${attrazione.reazioniPositive}</span></span>
									&nbsp;&nbsp;<span><i class="fa fa-thumbs-o-down" data-toggle="tooltip" title="Non mi piace"></i> <span id="num">${attrazione.reazioniNegative}</span></span>
									&nbsp;&nbsp;<span><i class="fa fa-map-marker" data-toggle="tooltip" title="Visite confermate"></i> <span id="num">${attrazione.numeroVisite}</span></span>
								</div>
							</div>
						</c:when>
						<c:when test="${attrazione.stato.id == '3' or attrazione.stato.id == '4'}">
							<div class="row" style="padding-top: 15px;">
								<div class="col-md-12" style="float: left; font-size: 22px;">
									<span><i class="fa fa-star" data-toggle="tooltip" title="Valutazione" style="cursor: pointer;"></i> <span id="num">${attrazione.valutazioneMedia}</span></span>
									&nbsp;&nbsp;<span><i class="fa fa-map-marker" data-toggle="tooltip" title="Visite confermate"></i> <span id="num">${attrazione.numeroVisite}</span></span>
								</div>
							</div>
						</c:when>
					</c:choose>
					
					<div class="row" style="padding-top: 15px;">
						<div class="col-md-12" style="text-align: right; font-size: 30px;">
							&nbsp;&nbsp;<span><i class="fa fa-thumbs-o-up text-primary" data-toggle="tooltip" title="Mi piace"  style="cursor: pointer;"></i></span>
							&nbsp;&nbsp;<span><i class="fa fa-thumbs-o-down text-primary" data-toggle="tooltip" title="Non mi piace"  style="cursor: pointer;"></i></span>
							&nbsp;&nbsp;<span><i class="fa fa-comment text-primary" data-toggle="tooltip" title="Aggiungi recensione"  style="cursor: pointer;"></i></span>
							&nbsp;&nbsp;<span><i class="fa fa-suitcase text-primary" data-toggle="tooltip" title="Aggiungi alla lista" id="btnAggiungiAttrazioneLista${attrazione.id}" style="cursor: pointer;" onclick="aggiungiAttrazioneToLista('${attrazione.id}')"></i></span>
							&nbsp;&nbsp;<span><i class="fa fa-map-marker text-primary" style="cursor: pointer;" data-toggle="tooltip" title="Conferma visita"></i></span>
						</div>
					</div>
				</div>
			
				<div class="col-md-12" style="padding-top: 20px;">
					<div class="nav-tabs-custom" style="box-shadow: none; border: none;">
						<ul class="nav nav-tabs" style="border: none;">
							<li class="active m-0"><a href="#recensioni" data-toggle="tab"
								aria-expanded="true"><h5 class="m-0">Recensioni</h5></a></li>
							<li class=""><a href="#foto" data-toggle="tab"
								aria-expanded="false"><h5 class="m-0">Foto</h5></a></li>
							<li class=""><a href="#domande" data-toggle="tab"
								aria-expanded="false"><h5 class="m-0">Domande</h5></a></li>
						</ul>
						<div class="tab-content" style="padding-bottom: 0px; padding-top: 0px; margin-left: -10px;">
							<div class="tab-pane active" id="recensioni">
								<c:if test="${attrazione.stato.id == '3' or attrazione.stato.id == '4'}">
									<div class="col-md-6" style="padding-left: 25px; font-size: 12px; padding-bottom: 5px;">
										<div class="checkbox icheck">
							            	<label>
							                	<input type="checkbox" class="icheck-checbox" checked>&nbsp;&nbsp;<i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i>&nbsp;&nbsp;70 (70%)
							                </label>
							            </div>
										<div class="checkbox icheck">
							            	<label>
							                	<input type="checkbox" class="icheck-checbox" checked>&nbsp;&nbsp;<i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i>&nbsp;&nbsp;10 (10%)
											</label>
										</div>
										<div class="checkbox icheck">
							            	<label>
							                	<input type="checkbox" class="icheck-checbox" checked>&nbsp;&nbsp;<i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i>&nbsp;&nbsp;10 (10%)
											</label>
										</div>
										<div class="checkbox icheck">
							            	<label>
							                	<input type="checkbox" class="icheck-checbox" checked>&nbsp;&nbsp;<i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #f39c12"></i><i class="fa fa-star" style="color: #f39c12"></i>&nbsp;&nbsp;10 (10%)
											</label>
										</div>
										<div class="checkbox icheck">
							            	<label>
							                	<input type="checkbox" class="icheck-checbox" checked>&nbsp;&nbsp;<i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #FFF"></i><i class="fa fa-star" style="color: #f39c12"></i>&nbsp;&nbsp;0 (0%)
											</label>
										</div>
									</div>
								    <div class="col-md-6 text-right" style="margin-top: 20px; margin-bottom: 20px;">
								    	<button type="button" class="btn btn-primary"><i class="fa fa-comment"></i> Aggiungi recensione</button>
								    </div>
								</c:if>
								<c:forEach items="${attrazione.recensioni}" var="recensione" varStatus="indexRecensione">
									<div class="col-md-12 box box-default" style="margin-top: -5px; margin-bottom: 0px;">
										<div class="row" style="padding-top: 10px; padding-bottom: 10px;">
											<div class="col-md-1 text-center" style="float: left;">
												<img src="${recensione.userInserimento.imageUrl}" class="img-circle" alt="User Image" style="width: 60px; cursor: pointer;">
											</div>
											<div class="col-md-11 p-0" style="float: left;">
												<span style="font-size: 22px;">${recensione.userInserimento.userName}</span>
												<span style="padding-left: 8px; font-size:18px;">(${recensione.userInserimento.livello}&nbsp;<i class="fa fa-user text-primary"></i><i class="fa fa-level-up text-primary"></i>)</span>
												<br>
												<small><i class="fa fa-clock-o"></i> <fmt:formatDate type="date" value="${recensione.dataInserimento}" pattern="dd/MM/yyyy HH:mm" /></small>
											</div>
										</div>
										
										<div class="row" style="padding: 10px;">
											<c:if test="${attrazione.stato.id == '3' or attrazione.stato.id == '4'}">
												<i class="fa fa-star" style="<c:choose><c:when test='${recensione.valutazione >= 1}'>color: #f39c12</c:when><c:otherwise>color: #fff</c:otherwise></c:choose>"></i>
												<i class="fa fa-star" style="<c:choose><c:when test='${recensione.valutazione >= 2}'>color: #f39c12</c:when><c:otherwise>color: #fff</c:otherwise></c:choose>"></i>
												<i class="fa fa-star" style="<c:choose><c:when test='${recensione.valutazione >= 3}'>color: #f39c12</c:when><c:otherwise>color: #fff</c:otherwise></c:choose>"></i>
												<i class="fa fa-star" style="<c:choose><c:when test='${recensione.valutazione >= 4}'>color: #f39c12</c:when><c:otherwise>color: #fff</c:otherwise></c:choose>"></i>
												<i class="fa fa-star" style="<c:choose><c:when test='${recensione.valutazione >= 5}'>color: #f39c12</c:when><c:otherwise>color: #fff; border-color: #000;</c:otherwise></c:choose>"></i>
											</c:if>
											<div style="font-size: 18px;"><b>${recensione.titolo}</b></div>
											<div>${recensione.testo}</div>
										</div>
										
										<div class="row" style="padding-top: 10px; padding-bottom: 10px; overflow-x: auto; overflow-y: hidden; max-height: 175px; padding-top: 0px; padding-bottom: 0px; padding-left: 15px;">
											<div style="width: ${recensione.foto.size()*315 + 30}px;">
												<c:forEach items="${recensione.foto}" var="foto" varStatus="indexFoto">
													<img src="${foto.path}" alt="Image recensione" style="width: 300px; height: 150px; display: inline-block; margin-right: 15px; margin-bottom: 15px;">
												</c:forEach>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="tab-pane" id="foto" style="padding: 10px;">
								<div class="alert alert-warning text-center">
									<i class="fa fa-warning" style="font-size: 2.5em;"></i> <b style="font-size: 25px; padding-left: 10px;">Non disponibile</b>
								</div>
							</div>
							<div class="tab-pane" id="domande" style="padding: 10px;">
								<div class="alert alert-warning text-center">
									<i class="fa fa-warning" style="font-size: 2.5em;"></i> <b style="font-size: 25px; padding-left: 10px;">Non disponibile</b>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>