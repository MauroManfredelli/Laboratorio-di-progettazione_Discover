<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <!-- Navigation -->
<header class="main-header">
  <!-- Logo -->
  <!-- Header Navbar: style can be found in header.less -->
  <nav class="navbar navbar-static-top col-md-12 p-0 light-blue-bg" role="navigation" id="navbarWithMargin" style="margin-left: 0px; border-bottom: 1.5px solid #3c8dbc; cursor:default">
	  <span class="col-md-7 pull-left hidden" id="logoWeb">
		<a href="#" class="light-blue-bg" style="background-color: #FFF; font-size: 2.1em; color: #333; width: 70px;">
			<i class="fa fa-plus-circle"></i>
		</a>
		<a href="/discover" class="pull-right" style="padding-right: 30px;">
			<img src="<%= request.getContextPath() %>/resources/dist/img/logo_orizzontale.png" alt="Discover" style="width: 200px; vertical-align: top; margin-top: 5px; padding-left: 15px;"/>
		</a>
	  </span>
	  <span class="col-md-4 p-0 hidden" id="logoMobile">
		<a href="#" class="btn light-blue-bg" style="background-color: #FFF; color: #333; padding: 10px; padding-right: 5px; padding-left: 5px; border-radius: 0px;  border-right: 1px solid #eee;">
			<i class="fa fa-plus-circle" style="font-size: 1.2em;"></i>
		</a>
		<a href="/discover" class="p-0">
			<img src="<%= request.getContextPath() %>/resources/dist/img/logo_orizzontale.png" alt="Discover" style="width: 90px; padding-left: 5px;"/>
		</a>
	  </span>
    <div class="navbar-custom-menu p-0" style="margin-right: 0px;">
    	
      <ul class="nav navbar-nav" style="margin-right: 0px;">
        <!-- Notifications: style can be found in dropdown.less -->
        <li class="dropdown notifications-menu">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="padding: 10px; color: #333;">
            <i class="far fa-bell" style="font-size: 1.5em;"></i>
            <span class="label label-warning">5</span>
          </a>
          <ul class="dropdown-menu">
            <li class="header">Hai 5 notifiche</li>
            <li>
              <!-- inner menu: contains the actual data -->
              <ul class="menu">
                <li>
                  <a href="#">
                    <i class="fa fa-users text-aqua"></i> 3 nuove recensioni
                  </a>
                </li>
                <li>
                  <a href="#">
                    <i class="fa fa-warning text-yellow"></i> Devi confermare la visita di un'attrazione
                  </a>
                </li>
                <li>
                  <a href="#">
                    <i class="fa fa-mobile text-green" style="font-size: 1.5em;"></i> Nuova connessione da dispositivo mobile
                  </a>
                </li>
                <li>
                  <a href="#">
                    <i class="fa fa-thumbs-up text-blue"></i> 5 nuove reazioni positive
                  </a>
                </li>
                <li>
                  <a href="#">
                    <i class="fa fa-thumbs-down text-red"></i> 5 nuove reazioni negative
                  </a>
                </li>
              </ul>
            </li>
            <li class="footer"><a href="#">Mostra tutte</a></li>
          </ul>
        </li>
        <!-- Messages: style can be found in dropdown.less-->
        <li class="dropdown messages-menu">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="padding: 10px; color: #333;">
            <i class="far fa-envelope" style="font-size: 1.5em;"></i>
            <span class="label label-success">4</span>
          </a>
          <ul class="dropdown-menu">
            <li class="header">Hai 4 nuovi messaggi</li>
            <li>
              <!-- inner menu: contains the actual data -->
              <ul class="menu">
                <li><!-- start message -->
                  <a href="#" onclick="openDirectChat()">
                    <div class="pull-left">
                      <img src="<%= request.getContextPath() %>/resources/dist/img/hic.jpg" class="img-circle" alt="User Image"/>
                    </div>
                    <h4>
                      Hichame Yessou
                      <small><i class="fa fa-clock-o"></i> 1 min</small>
                    </h4>
                    <p>Bella vita... <i class="fa fa-smile-o"></i></p>
                  </a>
                </li><!-- end message -->
                <li><!-- start message -->
                  <a href="#" onclick="openDirectChat()">
                    <div class="pull-left">
                      <img src="<%= request.getContextPath() %>/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image"/>
                    </div>
                    <h4>
                      Alessandro Rossi
                      <small><i class="fa fa-clock-o"></i> 5 min</small>
                    </h4>
                    <p>Hai visto la spiaggia? <i class="fa fa-smile-o"></i></p>
                  </a>
                </li><!-- end message -->
                <li>
                  <a href="#" onclick="openDirectChat()">
                    <div class="pull-left">
                      <img src="<%= request.getContextPath() %>/resources/dist/img/user3-128x128.jpg" class="img-circle" alt="user image"/>
                    </div>
                    <h4>
                      Susanna Bella
                      <small><i class="fa fa-clock-o"></i> 2 ore</small>
                    </h4>
                    <p>Hai qualche posto da consigliare?</p>
                  </a>
                </li>
                <li>
                  <a href="#" onclick="openDirectChat()">
                    <div class="pull-left">
                      <img src="<%= request.getContextPath() %>/resources/dist/img/user4-128x128.jpg" class="img-circle" alt="user image"/>
                    </div>
                    <h4>
                      Rebecca Brambilla
                      <small><i class="fa fa-clock-o"></i> Oggi</small>
                    </h4>
                    <p>Hei</p>
                  </a>
                </li>
                <li>
                  <a href="#" onclick="openDirectChat()">
                    <div class="pull-left">
                      <img src="<%= request.getContextPath() %>/resources/dist/img/user3-128x128.jpg" class="img-circle" alt="user image"/>
                    </div>
                    <h4>
                      Susanna Bella
                      <small><i class="fa fa-clock-o"></i> Ieri</small>
                    </h4>
                    <p>Vorrei andare a Iglesias</p>
                  </a>
                </li>
                <li>
                  <a href="#" onclick="openDirectChat()">
                    <div class="pull-left">
                      <img src="<%= request.getContextPath() %>/resources/dist/img/user4-128x128.jpg" class="img-circle" alt="user image"/>
                    </div>
                    <h4>
                      Rebecca Brambilla
                      <small><i class="fa fa-clock-o"></i> 2 giorni</small>
                    </h4>
                    <p>Buona giornata <i class="fa fa-smile-o"></i></p>
                  </a>
                </li>
              </ul>
            </li>
            <li class="footer"><a href="#" onclick="openDirectChat()">Vedi tutti i messaggi</a></li>
            <script>
            	function openDirectChat() {
            		if($("#myDirectChat").css("display") == "none") {
            			$("#myDirectChat").toggle("bottom");
            		}
            	}
            </script>
          </ul>
        </li>
        <!-- Liste e viaggi -->
        <li id="liHeadListe" data-toggle="tooltip" data-placement="bottom" title="Liste">
          <a href="/discover/liste" style="padding: 10px; color: #333;">
          	<i class="fa fa-suitcase" style="font-size: 1.5em;"></i>
          </a>
        </li>
        <!-- User Account: style can be found in dropdown.less -->
        <li class="dropdown user user-menu">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="padding: 10px; color: #333;">
          	<i class="fa fa-user"></i>
            <span class="hidden-xs"><c:out value="${currentUser.userName}"></c:out></span>
          </a>
          <ul class="dropdown-menu">
            <!-- User image -->
            <li class="user-header" style="background-color: #444">
              <c:choose>
              	<c:when test="${not empty currentUser.imageUrl}">
              		<img src="${currentUser.imageUrl}" class="img-circle" alt="User Image" />
              	</c:when>
              	<c:otherwise>
              		<img src="<%= request.getContextPath() %>/resources/dist/img/icon-user-default.png" class="img-circle" alt="User Image"/>
              	</c:otherwise>
              </c:choose>
              <p>
                <c:out value="${currentUser.userName}"></c:out>
                <small>Iscritto dal Nov. 2012</small>
              </p>
            </li>
            <!-- Menu Footer-->
            <li class="user-footer">
              <div class="pull-left">
                <a href="#" class="btn btn-default btn-flat">Profilo</a>
              </div>
              <div class="pull-right">
                <a href="<c:url value="/logout"/>" class="btn btn-default btn-flat">Esci</a>
              </div>
            </li>
          </ul>
        </li>
        <!-- Tasks: style can be found in dropdown.less -->
        <li class="dropdown tasks-menu">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="padding: 10px; color: #333;">
            <i class="fas fa-cog" ></i>
          </a>
          <ul class="dropdown-menu">
            <li>
              <!-- inner menu: contains the actual data -->
              <ul class="menu">
                <li><!-- Task item -->
                  <a href="#">
                    <h3 style="margin-bottom: 0px;">
                      <i class="fa fa-book"></i> Registro attività
                    </h3>
                  </a>
                </li><!-- end task item -->
                <li><!-- Task item -->
                  <a href="#">
                    <h3 style="margin-bottom: 0px;">
                      <i class="fa fa-star"></i> Preferenze della sezione bacheca
                    </h3>
                  </a>
                </li><!-- end task item -->
                <li><!-- Task item -->
                  <a href="#">
                    <h3 style="margin-bottom: 0px;">
                      <i class="fa fa-lock"></i> Privacy
                    </h3>
                  </a>
                </li><!-- end task item -->
                <li><!-- Task item -->
                  <a href="#">
                    <h3 style="margin-bottom: 0px;">
                      <i class="fa fa-gears"></i> Impostazioni
                    </h3>
                  </a>
                </li><!-- end task item -->
                <li><!-- Task item -->
                  <a href="#">
                    <h3 style="margin-bottom: 0px;">
                      <i class="fa fa-headphones"></i> Supporto
                    </h3>
                  </a>
                </li><!-- end task item -->
                <li><!-- Task item -->
                  <a href="#" class="p-0 text-center" style="color: #333; background-color: #f4f4f4;">
                    <i class="fa fa-ellipsis-h"></i>
                  </a>
                </li><!-- end task item -->
              </ul>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>
  
  <nav class="navbar col-md-12 hidden light-azure-bg" style="padding-left: 0px; margin-left: 0px; padding-right: 0px; border: 1px solid #F0F0F0; border-bottom: 1.5px solid #3c8dbc; cursor:default" id="headerWeb">
  	<a href="/discover" type="button" class="btn btn-lg btn-default col-md-3" id="btnBacheca" style="background-color: #FCFDFF; font-size: 1.3em; text-align: center; border: none; padding-bottom: 11px; border-radius: 0px;">
		<i class="fa fa-list"></i> <span>Bacheca</span>
	</a>
	<a href="javascript:void(0)" type="button" class="btn btn-lg btn-default col-md-3" id="btnBachecaDiscover" style="background-color: #FCFDFF; font-size: 1.3em; text-align: center; border: none; padding-bottom: 8px; border-radius: 0px;">
		<img src="<%= request.getContextPath() %>/resources/dist/img/bacheca_discover.png" style="width: 20px; padding-bottom: 5px;"/> Bacheca Discover
	</a>
	<a href="/discover/cerca"  type="button" class="btn btn-lg btn-default col-md-3" id="btnCerca" style="background-color: #FCFDFF; font-size: 1.3em; text-align: center; border: none; padding-bottom: 11px; border-radius: 0px;">
		<i class="fa fa-search"></i> Cerca
	</a>
	<a href="/discover/mappa" type="button" class="btn btn-lg btn-default col-md-3" id="btnIntornoMe" style="background-color: #FCFDFF; font-size: 1.3em; text-align: center; border: none; padding-bottom: 11px; border-radius: 0px;">
		<i class="fa fa-compass"></i> Intorno a me
	</a>
  </nav>
  
  <nav class="col-md-12 btn-group light-azure-bg" style="padding: 0px; width: 100%; border-bottom: 1.5px solid #3c8dbc; " id="footerMobile">
  	<a href="/discover" type="button" class="btn btn-lg btn-default col-md-3" id="btnBacheca" style="background-color: #FCFDFF; width: 25%; font-size: 0.7em;">
		<i class="fa fa-list" style="padding-bottom: 5px;"></i> Bacheca
	</a>
	<a href="javascript:void(0)" type="button" class="btn btn-lg btn-default col-md-3" id="btnBachecaDiscover" style="background-color: #FCFDFF; width: 25%; font-size: 0.7em;">
		<img src="<%= request.getContextPath() %>/resources/dist/img/bacheca_discover.png" style="width: 11px; padding-bottom: 5px;"/> Discover
	</a>
	<a href="/discover/cerca" type="button" class="btn btn-lg btn-default col-md-3" id="btnCerca" style="background-color: #FCFDFF; width: 25%; font-size: 0.7em;">
		<i class="fa fa-search" style="padding-bottom: 5px;"></i> Cerca
	</a>
	<a href="/discover/mappa" type="button" class="btn btn-lg btn-default col-md-3" id="btnIntornoMe" style="background-color: #FCFDFF; width: 25%; margin-left: 1px; font-size: 0.7em; padding-left: 6px">
		<i class="fa fa-compass" style="padding-bottom: 5px;"></i> Intorno a me
	</a>
  </nav>
</header>