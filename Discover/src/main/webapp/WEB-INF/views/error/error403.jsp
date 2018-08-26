<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid" id="jumbotronErrore">
	<div class="col-md-12">
		<div class="center">
			<div class="jumbotron" style="background-color: #FFF; color: #000;">
				<h1>Accesso negato</h1>
				<br />
				<p>Non dispondi dei privilegi sufficienti per accedere a questa sezione!</p>
				<br />
				<div> 
					<a class="btn btn--primary" href="${pageContext.servletContext.contextPath}/">Home</a>
				</div>
			</div>
		</div>
	</div>
</div>