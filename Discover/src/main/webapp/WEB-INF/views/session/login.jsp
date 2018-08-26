<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
  <head>
    <meta charset="UTF-8">
    <title>Discover</title>
    <link rel="shortcut icon" href="<%= request.getContextPath() %>/resources/dist/img/logo.jpg" />
    
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <!-- Bootstrap 3.3.2 -->
    <link href="<%= request.getContextPath() %>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="<%= request.getContextPath() %>/resources/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- iCheck -->
    <link href="<%= request.getContextPath() %>/resources/plugin/iCheck/square/blue.css" rel="stylesheet" type="text/css" />
	
	<style>
		body { 
		  background: linear-gradient(to top, rgba(101,101,101,1) 0%, rgba(171,171,171,1) 39%, rgba(271,271,271,1) 100%) !important;
		  -webkit-background-size: cover !important;
		  -moz-background-size: cover !important;
		  -o-background-size: cover !important;
		  background-size: cover !important;
		  min-height: auto;
		}
	</style>
  </head>
  <body class="login-page">
    <div class="login-box">
      <div class="login-logo" style="">
        <img src="<%= request.getContextPath() %>/resources/dist/img/logo_orizzontale.png" alt="Discover" style=""/>
      </div><!-- /.login-logo -->
      <div class="login-box-body">
        <p class="login-box-msg">Autenticati per iniziare una nuova sessione</p>
        <form:form class="form-signin" name='loginForm' action="/discover/j_spring_security_check" method='POST'>
          <div class="form-group has-feedback">
            <input type="text" class="form-control" id="username" name="username" placeholder="Email" required autofocus/>
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" id="password" name="password" placeholder="Password" required/>
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <c:if test="${not empty error}">
			<div class="alert alert-danger" style="margin-left: 20px;margin-right: 20px;"><i class="fa fa-warning"></i> ${error}</div>
		  </c:if>
		  <c:if test="${not empty msg}">
			<div class="alert alert-success" style="margin-left: 20px;margin-right: 20px;">${msg}</div>
		  </c:if>
          <div class="row">
            <div class="col-xs-8">    
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox"> Ricordami
                </label>
              </div>                        
            </div><!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat">Accedi</button>
            </div><!-- /.col -->
          </div>
        </form:form>

        <div class="social-auth-links text-center">
          <p>- OPPURE -</p>
          <form:form action="/discover/connect/facebook" method="POST" style="display: inline" id="facebookLogin">
			<input type="hidden" name="scope" value="public_profile,email" />
          	<button type="submit" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Accedi con Facebook</button>
          </form:form>
          <form:form action="/discover/connect/google" method="POST" style="display: inline" id="googleLogin">
			<input type="hidden" name="scope" value="profile email" />
          	<button type="submit" class="btn btn-block btn-social btn-google-plus btn-flat"><i class="fa fa-google-plus"></i> Accedi con Google+</button>
          </form:form>
        </div><!-- /.social-auth-links -->

        <a href="#">Ho dimenticato la password</a><br>
        <a href="/discover/registration" class="text-center">Iscrivi un nuovo account</a>

      </div><!-- /.login-box-body -->
    </div><!-- /.login-box -->

    <!-- jQuery 2.1.3 -->
    <script src="<%= request.getContextPath() %>/resources/plugin/jQuery/jQuery-2.1.3.min.js"></script>
    <!-- Bootstrap 3.3.2 JS -->
    <script src="<%= request.getContextPath() %>/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- iCheck -->
    <script src="<%= request.getContextPath() %>/resources/plugin/iCheck/icheck.min.js" type="text/javascript"></script>
    <script>
      $(function () {
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
      });
    </script>
  </body>
</html>