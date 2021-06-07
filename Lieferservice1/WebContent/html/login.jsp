<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kundenlogin</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css" href="../../css/login.css">
</head>

<%@ include file="../jspf/header.jsp"%>
<main>


<h1>Kundenlogin</h1>
<h3>Registrierte Kunden</h3>
<p>Falls Du schon Registriert bist, melde dich hier mit deiner
	E-mail an</p>


<form action="../../LoginServlet" method="POST" id="login">
	<div id="kundenLogin">
		
		<input type="text" id="emailAdresse" name=email maxlength="250" placeholder="E-mail">

		
		<input type="password" id="passwort" name="passwort" maxlength="500" placeholder="Passwort">

		<button type="submit" name="loginButton">Anmelden</button>
	</div>
</form>

<h3>Neue Kunden</h3>
<p>Hier kannst Du kostenlos ein Konto erstellen und von unserem
	erstklassigen Angeboten provitieren</p>

<form action="../registrierung.jsp" name="registrierung">
	<button type="submit" id="registrierenButton" name="registrieren">Ein
		Konto erstellen</button>
</form>
</main>
<br>

<%@ include file="../jspf/footer.jsp"%>
</html>