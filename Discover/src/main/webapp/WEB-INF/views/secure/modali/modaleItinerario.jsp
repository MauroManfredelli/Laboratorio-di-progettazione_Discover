<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="today" value="<%=new Date()%>"/>
<fmt:formatDate type="date" value="${today}" pattern="dd/MM/yyyy" var="today"/>

<!-- InputMask -->
<script src="<%= request.getContextPath() %>/resources/plugin/input-mask/jquery.inputmask.js" type="text/javascript"></script>
<script src="<%= request.getContextPath() %>/resources/plugin/input-mask/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
<script src="<%= request.getContextPath() %>/resources/plugin/input-mask/jquery.inputmask.extensions.js" type="text/javascript"></script>
<!-- date-range-picker -->
<script src="<%= request.getContextPath() %>/resources/plugin/daterangepicker/daterangepicker.js" type="text/javascript"></script>
<!-- bootstrap color picker -->
<script src="<%= request.getContextPath() %>/resources/plugin/colorpicker/bootstrap-colorpicker.min.js" type="text/javascript"></script>
<!-- bootstrap time picker -->
<script src="<%= request.getContextPath() %>/resources/plugin/timepicker/bootstrap-timepicker.min.js" type="text/javascript"></script>

<script>
	$(document).ready(function() {
		$('#dateRange').daterangepicker();
	})
</script>

<div class="modal fade" id="itinerarioModal" >
	<div class="modal-dialog modal-md" style="margin-top: 150px;">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h3 class="m-0"><strong>Itinerario</strong></h3>
        		<button type="button" data-dismiss="modal" aria-label="Close" class="close" style="position:absolute;top:0;right:0;padding:.75rem 1.25rem;color:inherit"><span aria-hidden="true">&times;</span></button>
      		</div>
      		<div class="modal-body">
      			<form:form action="/discover/liste/creaItinerario" modelAttribute="itinerario" id="modalItinerarioForm">
      				<div class="row form-group" id="formNome">
   						<div class="col-md-12">
   							<label class="font-weight-bold">
   								Nome dell'itinerario
   							</label>
   						</div>
   						<div class="col-md-12">
   							<form:input path="nome" class="form-control" placeholder="Nome"/>
   						</div>
   						<div class="col-md-12 text-danger hidden" id="formErrore" style="margin-top: 4px;">
   							<i class="fa fa-exclamation-circle"></i> Inserire un nome per l'itinerario!
   						</div>
      				</div>
      				<div class="row form-group" id="formDivisioneGiorni">
   						<div class="col-md-12">
   							<label class="font-weight-bold">
   								L'itinerario è basato su delle date o su un numero di giorni generico?
   							</label>
   						</div>
   						<div class="col-md-12" style="padding-left: 40px;">
   							<div class="radio icheck">
				            	<label>
				                	<input type="radio" name="divisione" id="divisione1" value="1" class="icheck-checbox" onclick="checkDivisioneGiorni()" />&nbsp;&nbsp;Date&nbsp;&nbsp;
				                	<input type="radio" name="divisione" id="divisione2" value="2" class="icheck-checbox" onclick="checkDivisioneGiorni()" />&nbsp;&nbsp;Numero giorni
				                </label>
				            </div>
   						</div>
   						<div class="col-md-12 text-danger hidden" id="formErrore" style="margin-top: 4px;">
   							<i class="fa fa-exclamation-circle"></i> Selezionare una divisione dei giorni!
   						</div>
      				</div>
      				<div class="row form-group hidden" id="formDate">
   						<div class="col-md-12">
   							<label class="font-weight-bold">
   								Date di riferimento
   							</label>
   						</div>
   						<div class="col-md-12">
   							<div class="input-group">
		                        <div class="input-group-addon">
		                        	<i class="fa fa-calendar"></i>
		                        </div>
		                        <input type="text" class="form-control pull-right" id="dateRange"/>
		                        <form:hidden path="dataInizio" />
		                        <form:hidden path="dataFine" />
		                    </div>
   						</div>
   						<div class="col-md-12 text-danger hidden" id="formErrore" style="margin-top: 4px;">
   							<i class="fa fa-exclamation-circle"></i> Inserire delle date di riferimento!
   						</div>
      				</div>
      				<div class="row form-group hidden" id="formGiorni">
   						<div class="col-md-12">
   							<label class="font-weight-bold">
   								Numero di giorni
   							</label>
   						</div>
   						<div class="col-md-12">
   							<form:input path="numeroGiorni" class="form-control" />
   						</div>
   						<div class="col-md-12 text-danger hidden" id="formErrore" style="margin-top: 4px;">
   							<i class="fa fa-exclamation-circle"></i> Inserire un numero di giorni!
   						</div>
      				</div>
      				<div class="row form-group" id="formWishlist">
   						<div class="col-md-12">
   							<label class="font-weight-bold">
   								Seleziona le wishlist da cui vuoi importare le attrazioni da visitare
   							</label>
   						</div>
   						<div class="col-md-12" style="padding-left: 40px;">
   							<c:forEach items="${wishlistUtente}" var="wishlist" varStatus="indexWishlistt">
								<div class="checkbox icheck">
					            	<label>
					                	<form:checkbox path="idWishlist" id="statoWishlist${indexWishlistt.index}" class="icheck-checbox" value="${wishlist.id}" />&nbsp;&nbsp;&nbsp;${wishlist.nome}
					                </label>
					            </div>
							</c:forEach>
   						</div>
   						<div class="col-md-12 text-danger hidden" id="formErrore" style="margin-top: 4px;">
   							<i class="fa fa-exclamation-circle"></i> Selzionare almeno un'attrazione!
   						</div>
      				</div>
      				<div class="row" style="border-top: 1px solid #ddd; padding-top: 10px;">
      					<div class="col-md-12 text-right">
      						<button type="button" class="btn btn-default" data-dismiss="modal">
      							<i class="fa fa-times"></i> Chiudi
      						</button>
      						<button type="button" class="btn btn-success" onclick="salvaItinerario()">
      							<i class="fa fa-save"></i> Salva
      						</button>
      					</div>
      				</div>
      			</form:form>
      		</div>
      	</div>
    </div>
</div>
     