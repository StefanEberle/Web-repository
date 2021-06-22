<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="${pageContext.request.requestURI}/">
<title>Neues Konto erstellen</title>

<script type="text/javascript" src="../../js/bday.js"></script>
<script type="text/javascript" src="../../js/passwortPruefen.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/registrierung.css">
</head>

<body>
	<noscript>Sie haben JavaScript deaktiviert.</noscript>

	<header>

		<h1>Neues Konto anlegen</h1>
		<h3>Persönliche Informationen</h3>
		<hr>
	</header>
	<main>
	<c:if test="${not empty errorRequest}">
		<c:out value="${errorRequest}" />
	</c:if>
	<section>
		<form action="../../RegistrierungServlet" method="POST"
			id="registrieren">
			<h2>Registrieren</h2>

			<fieldset class="user">


				<p>Email:</p>
				<input type="text" name="email" id="email" size="60" maxlength="60"
					placeholder="Email Adresse" onchange="loadEmailVerfuegbar()"
					required> <label id="emailTest"> </label> <br>
				<p>Passwort:</p>
				<input type="password" name="passwort" id="pw" pattern=".{8,}"
					title="Eight or more characters" size="60" maxlength="60"
					placeholder="Passwort" required>

				<!-- https://www.w3schools.com/tags/att_input_pattern.asp -->
				<!-- onchange="pwRules(this)"  
			
			pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"  -->


				<br>
				<p>Passwort:</p>
				<input type="password" name="passwort2" id="repeatPW"
					pattern=".{8,}" title="Eight or more characters" size="60"
					maxlength="60" placeholder="Passwort wiederholen" required>
				<!-- disabled="disabled" -->
				<label id="meldungPW2"> </label>

			</fieldset>

			<fieldset class="adresse">
				<p>Vorname:</p>
				<input type="text" name="vorname" size="30" maxlength="40"
					placeholder="Vorname" required> <br>
				<p>Nachname:</p>
				<input type="text" name="nachname" size="30" maxlength="40"
					placeholder="Nachname" required>

				<div class="geb">
					<p>Geburstag:</p>
					<select id="jahr" name="jahr" required>
						<option>Jahr</option>
					</select> <select id="monat" name="monat" required>
						<option>Monat</option>
						<option value="1">January</option>
						<option value="2">February</option>
						<option value="3">March</option>
						<option value="4">April</option>
						<option value="5">May</option>
						<option value="6">June</option>
						<option value="7">July</option>
						<option value="8">August</option>
						<option value="9">September</option>
						<option value="10">October</option>
						<option value="11">November</option>
						<option value="12">December</option>
					</select> <select id="tag" name="tag" required>
						<option>Tag</option>
					</select>
				</div>


				<p>Straße:</p>
				<input type="text" name="strasse" size="30" maxlength="40"
					placeholder="Straße" required> <br>
				<p>Hr:</p>
				<input type="text" name="hausnummer" size="5" maxlength="5"
					placeholder="Nr." required> <br>
				<p>PLZ:</p>
				<input type="number" name="plz" size="8" min="85049" max="85098"
					maxlength="5" placeholder="PLZ" required> <br>
				<p>Stadt</p>
				<input type="text" name="stadt" size="30" maxlength="40"
					placeholder="Stadt" required> <br>
				<p>Etage:</p>
				<input type="number" name="etage" size="8" min="0" max="9999"
					maxlength="4" placeholder="Etage" required> <br>
				<p>Mobil:</p>
				<input type="number" name="telefonnummer" size="30" min="0"
					max="9999999999999" maxlength="13" placeholder="Telefonnummer"
					required> <br>
				<textarea name="lieferhinweise" rows="5" cols="40" maxlength="250"
					placeholder="Weitere Lieferhinweise"></textarea>

				<br>

			</fieldset>

			<button type="submit" name="RegistrierungsButton" id="absendeButton"
				value="true" disabled>Absenden</button>
		</form>
	</section>

	<form action="../../index.jsp" name="index">
			<button type="submit" id="loginButton" name="login">Zum Login</button>
		</form>
	</main>
</body>
</html>