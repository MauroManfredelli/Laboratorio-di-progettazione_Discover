<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<script>
	$(document).ready(function() {
		$("#headerWeb #btnBacheca, #footerMobile #btnBacheca").addClass("section-active");
	});
</script>

<!-- Content Header (Page header) -->
<section class="content-header"></section>

<!-- Main content -->
<section class="content">
	<jsp:include page="../sceltaLista.jsp"></jsp:include>
	
	<c:forEach items="${listAttrazioni}" var="attrazione" varStatus="indexAttrazione">
		<div class="panel rounded-box-desktop">
		    <div class="box-header with-border">
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
						<div class="carousel-inner" style="height: 300px; max-height: 300px; width: auto; background-color: #FFF; border-radius: 13px;">
							<c:forEach items="${attrazione.fotoPrincipali}" var="foto" varStatus="indexFoto">
								<div class="item <c:if test='${indexFoto.index == 0}'>active</c:if>">
									<img
										src="<%= request.getContextPath() %>/resources/dist/img/attrazione${attrazione.id}/${indexFoto.index + 1}.jpg"
										style="margin: 0 auto; height: 300px; max-height: 300px; width: auto;">
								</div>
							</c:forEach>
						</div>
						<a class="left carousel-control" href="#carousel-example-generic${attrazione.id}"
							data-slide="prev" style="border-color: #000;"> <span class="fa fa-angle-left"></span>
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
								<span style="padding-left: 8px; font-size:18px;">(${attrazione.userInserimento.livello}&nbsp;<i class="fa fa-user text-primary"></i><i class="fas fa-level-up-alt text-primary"></i>)</span>
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
										&nbsp;&nbsp;<span><i class="fas fa-check" data-toggle="tooltip" title="Visite confermate"></i> <span id="num">${attrazione.numeroVisite}</span></span>
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
</section>