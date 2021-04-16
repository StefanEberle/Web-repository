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

		<form action="../../html/konto.jsp">
			<button type="submit" id="benutzerKonto">Benutzerkonto</button>
		</form>

		<h1>Flaschen Lieferservice</h1>
		<h3>Direkt bis zu Ihrer Haustür</h3>



		<%--	<a href="../../html/login.jsp" id="einloggen">Anmelden</a>  Wenn Angemeldet dann Benutzerkonto wenn nicht Anmelden als Text --%>
		<input type="hidden" id="valueTest" value="${user.login}">


		<div class="col-container">

			<form action="../../AuswahlArtikelServlet" method="GET">
				<nav id="navigationBar"></nav>
			</form>
			
			<div class="divLupe" id="divLupe">
				<i class="fa fa-search" id="search"></i>
			</div>
				<div id="artikelSuche">
					<input type="text" id="artikelSucheInput" maxlength="75" placeholder="Suche nach deiner Lieblingsmarke">
						<div id="searchErg">
						
						</div>
					<button type="submit" class="sucheButton">Suche</button>
				</div>

		


			<form action="../../html/login.jsp" id="einloggen">
				<button type="submit" id="anmeldeButton" name="anmeldeButton">Anmelden</button>
			</form>

			<form action="../../LoginServlet" method="GET" id="ausloggen">
				<button type="submit" id="abmeldeButton" name="abmeldeButton">Abmelden</button>
			</form>

		</div>


	</header>