<%@page pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ page import="java.util.Date" %>
<%
	Date now = new Date();
	pageContext.setAttribute("time",now);
%>

<div class="container-fluid" id="jumbotronErrore" style="padding-top: 100px">
	<div class="col-md-12">
		<div class="center">
			<div class="jumbotron" style="background-color: #FFF; color: #000;">
				<h1>Qualcosa &egrave; andato storto.</h1>
				<br>
				<p><b>Si &egrave; verificato un errore. Si prega di riprovare.</b></p> 
				<p>Qualora l'errore dovesse persistere, contattare il <a href="#"><b>supporto Discover</b></a>.</p>
			</div>
		</div>
	</div>
</div>