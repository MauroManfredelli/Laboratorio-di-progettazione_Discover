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

<div class="modal fade" id="dataVisitaModal" >
	<div class="modal-dialog modal-sm" style="margin-top: 150px;">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h3 class="m-0"><strong>Giorno visita</strong></h3>
        		<button type="button" data-dismiss="modal" aria-label="Close" class="close" style="position:absolute;top:0;right:0;padding:.75rem 1.25rem;color:inherit"><span aria-hidden="true">&times;</span></button>
      		</div>
      		<div class="modal-body">
      			<input type="hidden" id="idVisita" />
   				<div class="row form-group">
					<div class="col-md-12">
						<label>Data visita:</label>
	                    <div class="input-group">
	                        <div class="input-group-addon">
	                        	<i class="fa fa-calendar"></i>
	                        </div>
	                      	<input id="dataVisita" type="text" class="form-control" data-inputmask="'alias': 'gg/mm/aaaa'" data-mask/>
	                    </div>
	                    <script>
	                    	$(document).ready(function() {
	                            $("#dataVisitaModal #dataVisita").inputmask("dd/mm/yyyy", {"placeholder": "gg/mm/aaaa"});
	                    	})
	                    </script>
					</div>
   				</div>
   				<div class="row" style="border-top: 1px solid #ddd; padding-top: 10px;">
   					<div class="col-md-12 text-right">
   						<button type="button" class="btn btn-primary" onclick="salvaModificaDataVisita()" id="btnSalva">
   							<i class="fa fa-save"></i> Salva
   						</button>
   						<button type="button" class="btn btn-default" data-dismiss="modal">
   							<i class="fa fa-repeat"></i> Annulla
   						</button>
   					</div>
   				</div>
      		</div>
      	</div>
    </div>
</div>
