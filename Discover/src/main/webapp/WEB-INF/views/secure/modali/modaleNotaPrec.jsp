<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="today" value="<%=new Date()%>"/>
<fmt:formatDate type="date" value="${today}" pattern="dd/MM/yyyy" var="today"/>

<div class="modal fade" id="notaVisitaPrecedenteModal" >
	<div class="modal-dialog modal-sm" style="margin-top: 150px;">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h3 class="m-0"><strong>Nota precedente</strong></h3>
        		<button type="button" data-dismiss="modal" aria-label="Close" class="close" style="position:absolute;top:0;right:0;padding:.75rem 1.25rem;color:inherit"><span aria-hidden="true">&times;</span></button>
      		</div>
      		<div class="modal-body">
      			<input type="hidden" id="idVisita" />
   				<div class="row form-group">
					<div class="col-md-12">
						<textarea id="notaPrec" rows="5" class="form-control" placeholder="Nota precedente" style="max-width: 100%; min-width: 100%; min-height: 130px;"></textarea>
					</div>
   				</div>
   				<div class="row" style="border-top: 1px solid #ddd; padding-top: 10px;">
   					<div class="col-md-12 text-right">
   						<button type="button" class="btn btn-default" data-dismiss="modal">
   							<i class="fa fa-times"></i> Chiudi
   						</button>
   					</div>
   				</div>
      		</div>
      	</div>
    </div>
</div>
     