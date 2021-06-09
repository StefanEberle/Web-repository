<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.requestURI}/">
<meta charset="UTF-8">
<title>Getränke Lieferdienst</title>

<link rel="stylesheet" type="text/css" href="../css/index.css">
<link rel="stylesheet" type="text/css" href="../css/dropdownHeader.css">


</head>
<body>
	<noscript> Sie haben JavaScript deaktiviert.</noscript>
	<header>

		<h3>Um zu unserem Angebot zu gelangen musst du registriert sein!
		</h3>
	<hr>
	</header>
	<main>
	

	<section id="kundenLogin">
		<h1>Kundenlogin</h1>
		<form action="../LoginServlet" method="POST" id="login">


			<input type="text" id="emailAdresse" name=email maxlength="250"
				placeholder="E-mail"> 
			<input type="password" id="passwort"
				name="passwort" maxlength="50" placeholder="Passwort">

			<button type="submit" name="loginButton">Anmelden</button>

		</form>
	</section>

	<section id="reg">
	<hr class="hr1">
		<h3>Neue Kunden</h3>
		<p>Hier kannst Du kostenlos ein Konto erstellen und von unserem
			erstklassigen Angeboten provitieren!</p>

		<form action="../html/registrierung.jsp" name="registrierung">
			<button type="submit" id="registrierenButton" name="registrieren">Ein
				Konto erstellen</button>
		</form>
	<hr class="hr2">	
	</section>


	</main>
</body>
</html>