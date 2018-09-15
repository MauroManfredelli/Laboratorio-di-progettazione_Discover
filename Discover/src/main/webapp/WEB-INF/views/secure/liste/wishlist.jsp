<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!-- Content Header (Page header) -->
<section class="content-header p-0"></section>

<!-- Main content -->
<section class="content" id="mapContent">
	<nav style="position: fixed; top: 90px; width: 100%;" class="navbar main-header">
		<div class="col-md-12 p-0" style="border: none">
			<div class="box box-body m-0 light-blue-bg" style="padding: 10px 10px 10px 10px; border-radius: 0px; cursor:default;">
				<span class="font-weight-bold" style="font-size: 25px; padding-left: 20px;">${wishlist.nome}</span>
				<i class="fa fa-calendar" data-toggle="tooltip" title="Trasforma in itinerario" data-placement="right" style="font-size: 30px; padding-left: 10px; cursor: pointer;" onclick="creaItinerario('${wishlist.id}')"></i>
				<i class="fa fa-ellipsis-v" data-toggle="tooltip" title="Modifica info wishlist" data-placement="right" style="font-size: 30px; padding-left: 10px; cursor: pointer;" onclick="modificaWishlist('${wishlist.id}')"></i>
				<i class="fa fa-times pull-right" data-toggle="tooltip" title="Chiudi" data-placement="left" style="font-size: 30px; cursor: pointer; padding-right: 10px;" onclick="location.assign('/discover/liste')"></i>
			</div>
		</div>
	</nav>
	<div style="position: relative; margin-top: 90px; padding-right: 180px; padding-left: 180px;">
		<c:forEach items="${wishlist.attrazioniWishlist}" var="attrazioneWishlist" varStatus="indexAW">
			<c:set var="attrazione" value="${attrazioneWishlist.attrazione}" />
			<div class="panel rounded-box-desktop" id="box${attrazioneWishlist.id}">
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
				        			<small class="text-orange" style="float: left; padding-top: 10px;"><i class="fa fa-star" style="color: #f39c12"></i> ${attrazione.stato.descrizione}</small>
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
							
							<div class="row" style="padding-top: 15px;">
								<div class="col-md-12" style="float: left; font-size: 22px;">
									${attrazione.posizione.descrizione}
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12" style="float: left; font-size: 22px;">
									<b>${attrazione.tipoAttrazione.descrizione}</b>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12" style="float: left; font-size: 22px; text-transform: capitalize">
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
											<span><i class="fa fa-star" style="color: #f39c12" data-toggle="tooltip" title="Valutazione" style="cursor: pointer;"></i> <span id="num">${attrazione.valutazioneMedia}</span></span>
											&nbsp;&nbsp;<span><i class="fas fa-check" data-toggle="tooltip" title="Visite confermate"></i> <span id="num">${attrazione.numeroVisite}</span></span>
										</div>
									</div>
								</c:when>
							</c:choose>
							
							<div class="row" style="padding-top: 15px;">
								<div class="col-md-12" style="text-align: right; font-size: 30px; margin-top: 50px;">
									&nbsp;&nbsp;<span><i class="fa fa-trash text-action" data-toggle="tooltip" title="Rimuovi attrazione"  style="cursor: pointer;" onclick="rimuoviAttrazioneFromWishlist('${attrazioneWishlist.id}')"></i></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</section>

<jsp:include page="/WEB-INF/views/secure/modali/modaleItinerario.jsp"/>
<jsp:include page="/WEB-INF/views/secure/modali/modaleWishlist.jsp"/>
<script src="<%=request.getContextPath()%>/resources/dist/js/wishlist.js" type="text/javascript"></script>