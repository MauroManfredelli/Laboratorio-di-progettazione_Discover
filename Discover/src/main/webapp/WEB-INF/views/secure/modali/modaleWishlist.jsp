<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="today" value="<%=new Date()%>"/>
<fmt:formatDate type="date" value="${today}" pattern="dd/MM/yyyy" var="today"/>

<div class="modal fade" id="wishlistModal" >
	<div class="modal-dialog modal-sm" style="margin-top: 150px;">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h3 class="m-0"><strong>Wishlist</strong></h3>
        		<button type="button" data-dismiss="modal" aria-label="Close" class="close" style="position:absolute;top:0;right:0;padding:.75rem 1.25rem;color:inherit"><span aria-hidden="true">&times;</span></button>
      		</div>
      		<div class="modal-body">
      			<form:form action="/discover/liste/salvaWishlist" method="post" modelAttribute="wishlist" id="modalWushlistForm">
      				<form:hidden path="id" />
      				<div class="row form-group" id="formNome">
   						<div class="col-md-12">
   							<label class="font-weight-bold">
   								Nome della wishlist
   							</label>
   						</div>
   						<div class="col-md-12">
   							<form:input path="nome" class="form-control" placeholder="Nome" autofocus="true"/>
   						</div>
   						<div class="col-md-12 text-danger hidden" id="formNomeErrore" style="margin-top: 4px;">
   							<i class="fa fa-exclamation-circle"></i> Inserire un nome per la wishlist!
   						</div>
      				</div>
      				<div class="row" style="border-top: 1px solid #ddd; padding-top: 10px;">
      					<div class="col-md-12 text-right">
      						<button type="button" class="btn btn-default" data-dismiss="modal">
      							<i class="fa fa-times"></i> Chiudi
      						</button>
      						<button type="button" class="btn btn-primary" onclick="salvaWishlist()">
      							<i class="fa fa-save"></i> Salva
      						</button>
      					</div>
      				</div>
      			</form:form>
      		</div>
      	</div>
    </div>
</div>
     