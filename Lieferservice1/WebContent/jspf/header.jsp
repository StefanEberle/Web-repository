<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.requestURI}/">
<meta charset="UTF-8">
<script type="text/javascript" src="../../js/visibileButton.js"></script>
<script type="text/javascript" src="../../js/suche.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/dropdownHeader.css">
<link rel="stylesheet" type="text/css" href="../../css/suche.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


</head>
<body>
	<header>
	<noscript> Sie haben JavaScript deaktiviert.</noscript>
	

		<h1>Flaschen Lieferservice</h1>
		<h3>Direkt bis zu Ihrer Haustür</h3>



		
		<input type="hidden" id="valueTest" value="${user.login}">


		<nav class="navBar">

			<form action="../../AuswahlArtikelServlet" method="GET">
				<div id="navigationBar"></div>
			</form>
			
			<div class="divLupe" id="divLupe">
				<i class="fa fa-search" id="search"></i>
				
				
					<input type="text" id="artikelSucheInput" maxlength="75" placeholder="Suche nach deiner Lieblingsmarke">
						<div id="searchErg">
						
						</div>
		   </div>
			
			<div class="warenkorb" id="warenkorb">
				<i class="fa fa-shopping-cart" id="warenkorbIcon"></i>
			
				<form action="../../html/konto.jsp">
			<button type="submit" id="benutzerKonto"><i class="fa fa-user" id="userIcon"></i></button>
		</form>
			</div>	
				
	<div class="log">
		<form action="../../LoginServlet" method="GET" id="ausloggen">
				<button type="submit" id="abmeldeButton" name="abmeldeButton">Abmelden</button>
			</form>


			<form action="../../index.jsp" id="einloggen">
				<button type="submit" id="anmeldeButton" name="anmeldeButton">Anmelden</button>
			</form>
	</div>
			

		</nav>


	</header>