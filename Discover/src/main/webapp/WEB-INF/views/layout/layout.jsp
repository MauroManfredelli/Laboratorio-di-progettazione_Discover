<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
	
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <title>Discover</title>
    <link rel="shortcut icon" href="<%= request.getContextPath() %>/resources/dist/img/logo.jpg" />

	<title><sitemesh:write property='title'/></title>
	
	<!-- Bootstrap 3.3.2 -->
    <link href="<%= request.getContextPath() %>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />    
    <!-- FontAwesome 4.3.0 -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons 2.0.0 -->
    <link href="http://code.ionicframework.com/ionicons/2.0.0/css/ionicons.min.css" rel="stylesheet" type="text/css" />    
    <!-- Theme style -->
    <link href="<%= request.getContextPath() %>/resources/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- AdminLTE Skins. Choose a skin from the css/skins 
         folder instead of downloading all of them to reduce the load. -->
    <link href="<%= request.getContextPath() %>/resources/dist/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />
    <!-- iCheck -->
    <link href="<%= request.getContextPath() %>/resources/plugin/iCheck/square/blue.css" rel="stylesheet" type="text/css" />
    <!-- Morris chart -->
    <link href="<%= request.getContextPath() %>/resources/plugin/morris/morris.css" rel="stylesheet" type="text/css" />
    <!-- jvectormap -->
    <link href="<%= request.getContextPath() %>/resources/plugin/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
    <!-- Date Picker -->
    <link href="<%= request.getContextPath() %>/resources/plugin/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
    <!-- Daterange picker -->
    <link href="<%= request.getContextPath() %>/resources/plugin/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
    <!-- bootstrap wysihtml5 - text editor -->
    <link href="<%= request.getContextPath() %>/resources/plugin/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" rel="stylesheet" type="text/css" />
    <!-- Custom layout -->
    <link href="<%= request.getContextPath() %>/resources/dist/css/application.css" rel="stylesheet" type="text/css" />
    <!-- BOOTSTRAP CHOSEN SELECT --> 
	<link href="<%= request.getContextPath() %>/resources/plugin/bootstrap-chosen/bootstrap-chosen.css" rel="stylesheet">

	<sitemesh:write property='head'/>

</head>

<body class="wysihtml5-supported skin-black fixed">
	<input type="hidden" id="isMobile" />
	
    <!-- jQuery 2.1.3 -->
    <script src="<%= request.getContextPath() %>/resources/plugin/jQuery/jQuery-2.1.3.min.js"></script>
    <!-- jQuery UI 1.11.2 -->
    <script src="http://code.jquery.com/ui/1.11.2/jquery-ui.min.js" type="text/javascript"></script>
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
    <script>
      $.widget.bridge('uibutton', $.ui.button);
    </script>
    <!-- Bootstrap 3.3.2 JS -->
    <script src="<%= request.getContextPath() %>/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>    
    <!-- Morris.js charts -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
    <script src="<%= request.getContextPath() %>/resources/plugin/morris/morris.min.js" type="text/javascript"></script>
    <!-- Sparkline -->
    <script src="<%= request.getContextPath() %>/resources/plugin/sparkline/jquery.sparkline.min.js" type="text/javascript"></script>
    <!-- jvectormap -->
    <script src="<%= request.getContextPath() %>/resources/plugin/jvectormap/jquery-jvectormap-1.2.2.min.js" type="text/javascript"></script>
    <script src="<%= request.getContextPath() %>/resources/plugin/jvectormap/jquery-jvectormap-world-mill-en.js" type="text/javascript"></script>
    <!-- jQuery Knob Chart -->
    <script src="<%= request.getContextPath() %>/resources/plugin/knob/jquery.knob.js" type="text/javascript"></script>
    <!-- daterangepicker -->
    <script src="<%= request.getContextPath() %>/resources/plugin/daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <!-- datepicker -->
    <script src="<%= request.getContextPath() %>/resources/plugin/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
    <!-- Bootstrap WYSIHTML5 -->
    <script src="<%= request.getContextPath() %>/resources/plugin/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js" type="text/javascript"></script>
    <!-- iCheck -->
    <script src="<%= request.getContextPath() %>/resources/plugin/iCheck/icheck.min.js" type="text/javascript"></script>
    <!-- Slimscroll -->
    <script src="<%= request.getContextPath() %>/resources/plugin/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
    <!-- FastClick -->
    <script src='<%= request.getContextPath() %>/resources/plugin/fastclick/fastclick.min.js'></script>
    <!-- AdminLTE App -->
    <script src="<%= request.getContextPath() %>/resources/dist/js/app.min.js" type="text/javascript"></script>
    <!-- BOOTSTRAP CHOSEN SELECT --> 
	<script src='<%= request.getContextPath() %>/resources/plugin/bootstrap-chosen/chosen.jquery.js'></script>
    
    <div class="wrapper">
		<%-- Header --%>
		<%@include file="/WEB-INF/views/layout/header.jsp"%>
		
		<div class="content-wrapper" style="padding-top: 100px;">
	    	<!-- Page Content -->
	        <sitemesh:write property='body'/>
	        
	        <!-- Chat box -->
	        <div class="col-md-4 col-md-offset-8" style="position: fixed; bottom: 50px;">
				<div class="col-md-12 p-0">
					<!-- DIRECT CHAT -->
					<div id="myDirectChat"
						class="box box-primary direct-chat direct-chat-primary" style="display: none;">
						<div class="box-header with-border">
							<h3 class="box-title">Messaggi</h3>
							<div class="box-tools pull-right">
								<span data-toggle="tooltip" title="4 Chat"
									class='badge btn-primary'>4</span>
								<%-- <button class="btn btn-box-tool" data-widget="collapse">
									<i class="fa fa-minus"></i>
								</button> --%>
								<button class="btn btn-box-tool" data-toggle="tooltip"
									title="Contatti" onclick="openChats();">
									<i class="fa fa-comments"></i>
								</button>
								<script>
									function openChats() {
										if($(".direct-chat-contacts").css("transform") == 'matrix(1, 0, 0, 1, 0, 0)') {
											$(".direct-chat-contacts").css("transform", "matrix(1, 0, 0, 1, 500, 0)");
										} else {
											$(".direct-chat-contacts").css("transform", "matrix(1, 0, 0, 1, 0, 0)");
										}
									}
								</script>
								<button class="btn btn-box-tool" data-widget="remove">
									<i class="fa fa-times"></i>
								</button>
							</div>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<!-- Conversations are loaded here -->
							<div class="direct-chat-messages">
								<!-- Message. Default to the left -->
								<div class="direct-chat-msg">
									<div class='direct-chat-info clearfix'>
										<span class='direct-chat-name pull-left'>Hicame Yessou</span>
										<span class='direct-chat-timestamp pull-right'>23 Gennaio
											2:00 pm</span>
									</div>
									<!-- /.direct-chat-info -->
									<img class="direct-chat-img" src="<%= request.getContextPath() %>/resources/dist/img/hic.jpg"
										alt="message user image" />
									<!-- /.direct-chat-img -->
									<div class="direct-chat-text">Ciaooo</div>
									<!-- /.direct-chat-text -->
								</div>
								<!-- /.direct-chat-msg -->
				
								<!-- Message to the right -->
								<div class="direct-chat-msg right">
									<div class='direct-chat-info clearfix'>
										<span class='direct-chat-name pull-right'><c:out value="${currentUser.userName}"></c:out></span> <span
											class='direct-chat-timestamp pull-left'>23 Gennaio 2:05 pm</span>
									</div>
									<!-- /.direct-chat-info -->
									<img src="${currentUser.imageUrl}" class="direct-chat-img" alt="User Image"/>
									<!-- /.direct-chat-img -->
									<div class="direct-chat-text">Hei!! <i class="fa fa-smile-o"></i></div>
									<!-- /.direct-chat-text -->
								</div>
								<!-- /.direct-chat-msg -->
				
								<!-- Message. Default to the left -->
								<div class="direct-chat-msg">
									<div class='direct-chat-info clearfix'>
										<span class='direct-chat-name pull-left'>Hichame Yessou</span>
										<span class='direct-chat-timestamp pull-right'>23 Gennaio
											5:37 pm</span>
									</div>
									<!-- /.direct-chat-info -->
									<img class="direct-chat-img" src="<%= request.getContextPath() %>/resources/dist/img/hic.jpg"
										alt="message user image" />
									<!-- /.direct-chat-img -->
									<div class="direct-chat-text">Dove vai in vacanza?</div>
									<!-- /.direct-chat-text -->
								</div>
								<!-- /.direct-chat-msg -->
				
								<!-- Message to the right -->
								<div class="direct-chat-msg right">
									<div class='direct-chat-info clearfix'>
										<span class='direct-chat-name pull-right'><c:out value="${currentUser.userName}"></c:out></span> <span
											class='direct-chat-timestamp pull-left'>23 Gennaio 6:10 pm</span>
									</div>
									<!-- /.direct-chat-info -->
									<img src="${currentUser.imageUrl}" class="direct-chat-img" alt="User Image"/>
									<!-- /.direct-chat-img -->
									<div class="direct-chat-text">Dove vai tu</div>
									<!-- /.direct-chat-text -->
								</div>
								<!-- /.direct-chat-msg -->
				
								<!-- Message. Default to the left -->
								<div class="direct-chat-msg">
									<div class='direct-chat-info clearfix'>
										<span class='direct-chat-name pull-left'>Hichame Yessou</span>
										<span class='direct-chat-timestamp pull-right'>23 Gennaio
											7:37 pm</span>
									</div>
									<!-- /.direct-chat-info -->
									<img class="direct-chat-img" src="<%= request.getContextPath() %>/resources/dist/img/hic.jpg"
										alt="message user image" />
									<!-- /.direct-chat-img -->
									<div class="direct-chat-text">Bella vita... <i class="fa fa-smile-o"></i></div>
									<!-- /.direct-chat-text -->
								</div>
								<!-- /.direct-chat-msg -->
				
							</div>
							<!--/.direct-chat-messages-->
				
				
							<!-- Contacts are loaded here -->
							<div class="direct-chat-contacts">
								<ul class='contacts-list'>
									<li><a href='#'> <img class='contacts-list-img'
											src='<%= request.getContextPath() %>/resources/dist/img/user2-160x160.jpg' />
											<div class='contacts-list-info'>
												<span class='contacts-list-name'> Alessandro Rossi <small
													class='contacts-list-date pull-right'>2/28/2015</small>
												</span> <span class='contacts-list-msg'>Bella foto!</span>
											</div>
											<!-- /.contacts-list-info -->
									</a></li>
									<!-- End Contact Item -->
									<li><a href='#'> <img class='contacts-list-img'
											src='<%= request.getContextPath() %>/resources/dist/img/user3-128x128.jpg' />
											<div class='contacts-list-info'>
												<span class='contacts-list-name'> Susanna Bella <small
													class='contacts-list-date pull-right'>23/01/2015</small>
												</span> <span class='contacts-list-msg'>Dove vai tu</span>
											</div>
											<!-- /.contacts-list-info -->
									</a></li>
									<!-- End Contact Item -->
									<li><a href='#'> <img class='contacts-list-img'
											src='<%= request.getContextPath() %>/resources/dist/img/user4-128x128.jpg' />
											<div class='contacts-list-info'>
												<span class='contacts-list-name'> Rebecca Brambilla <small
													class='contacts-list-date pull-right'>2/10/2015</small>
												</span> <span class='contacts-list-msg'>Ahahahahahah</span>
											</div>
											<!-- /.contacts-list-info -->
									</a></li>
									<!-- End Contact Item -->
									<li><a href='#'> <img class='contacts-list-img'
											src='<%= request.getContextPath() %>/resources/dist/img/hic.jpg' />
											<div class='contacts-list-info'>
												<span class='contacts-list-name'> Hichame Yessou <small
													class='contacts-list-date pull-right'>23/01/2015</small>
												</span> <span class='contacts-list-msg'>Dove vai in vacanza</span>
											</div>
											<!-- /.contacts-list-info -->
									</a></li>
									<!-- End Contact Item -->
								</ul>
								<!-- /.contatcts-list -->
							</div>
							<!-- /.direct-chat-pane -->
						</div>
						<!-- /.box-body -->
						<div class="box-footer">
							<form action="#" method="post">
								<div class="input-group">
									<input type="text" name="message" placeholder="Messaggio ..."
										class="form-control" /> <span class="input-group-btn">
										<button type="button" class="btn btn-primary btn-flat">Invia</button>
									</span>
								</div>
							</form>
						</div>
						<!-- /.box-footer-->
					</div>
					<!--/.direct-chat -->
				</div>
				<!-- /.col -->
			</div>
	        
	    </div>
	
		<%@include file="/WEB-INF/views/layout/footer.jsp"%>
	</div>
	
    <script>
    	$(document).ready(function() {
    		$('.chosen-select').chosen();
    		$('.chosen-container').attr("style", "width: 100%;");
    		checkRunningOnMobile();
    	});
    	
    	function checkRunningOnMobile() {
    		var isMobile = false; //initiate as false
    		// device detection
    		if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) { 
    		    isMobile = true;
    		}
    		if(isMobile) {
    			$("#footerMobile").removeClass("hidden");
    			$("#headerWeb").addClass("hidden");
    			$("#logoWeb").addClass("hidden");
    			$("#logoMobile").removeClass("hidden");
    			$(".content-wrapper").css("padding-top", "29px").css("padding-bottom", "32px");
    			$(".content").css("padding-left", "0px")
    						 .css("padding-right", "0px");
    			$("#inputSelectLista").css("top", "55px").css("right", "0px");
    		} else {
    			$("#footerMobile").addClass("hidden");
    			$("#headerWeb").removeClass("hidden");
    			$("#logoWeb").removeClass("hidden");
    			$("#logoMobile").addClass("hidden");
    			$(".content-wrapper").css("padding-top", "90px");
    			$(".content").css("padding-left", "20px")
				 			 .css("padding-right", "20px");
    			$("#inputSelectLista").css("top", "105px").css("right", "5px");
    		}
    	}
    	
    	function addReaction(el, reaction) {
    		if($(el).parent("span").hasClass("text-primary") || $(el).parent("span").hasClass("text-danger") || $(el).parent("span").hasClass("text-success")) {
    			$(el).parent("span").removeClass("text-primary").removeClass("text-danger").removeClass("text-success");
    			$(el).parent("span").find("#num").html(parseInt($(el).parent("span").find("#num").html()) - 1);
    		} else {
    			if(reaction == 'like') {
    				$(el).parent("span").addClass("text-primary");
    			} else if(reaction == 'unlike') {
    				$(el).parent("span").addClass("text-danger");
    			} else if(reaction == 'visita') {
    				$(el).parent("span").addClass("text-success");
    			}
    			$(el).parent("span").find("#num").html(parseInt($(el).parent("span").find("#num").html()) + 1);
    		}
    	}
    	
    	 $(function () {
	        $('.icheck-checbox').iCheck({
	          checkboxClass: 'icheckbox_square-blue',
	          radioClass: 'iradio_square-blue',
	          increaseArea: '20%' // optional
	        });
	     });
    </script>

</body>

</html>
