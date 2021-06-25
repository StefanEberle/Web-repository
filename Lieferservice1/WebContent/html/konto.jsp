<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User bearbeiten</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css" href="../../css/konto.css">

<script type="text/javascript" src="../../js/kontoShowContent.js"></script>
<script type="text/javascript" src="../../js/passwort-Input-Label.js"></script>
<script type="text/javascript" src="../../js/email-Input-Label.js"></script>

</head>
<%@ include file="../jspf/header.jspf"%>
<main>
	
	<c:if test="${not empty errorRequest}">
		<c:out value="${errorRequest}" />
	</c:if>

	<section class="mainTitel">
		<h1>Mein Konto</h1>
	</section>
	
	<aside class="mainSideBar">
			<h5>Mein Konto</h5>
			<a>Bestellungen</a>
	</aside>

<section class="mainContent">
	
		
			<article class=mainKonto>
				<h2>Kontoinformationen</h2>
				
				
				<div id="emailBearbeiten">
				<h5>Email bearbeiten:</h5>
				<form action="../../KontoBearbeitenServlet" method="POST" name="emailBearbeiten">
				 	<input type="text" name="email" id="email" size="60" maxlength="20" placeholder="Email Adresse" value="${user.email}" onchange="loadEmailVerfuegbar()" required> <br> 
				 	<label id="emailAvailable"> </label>    				 	 
					<br>
					<input type="password" name="passwort" size="60" maxlength="20" placeholder="Passwort" required>
					<br>
					<button type="submit" name="emailBearbeiten">Änderung speichern</button>
				</form>	
				</div>
				
				<div id="pwBearbeiten"> 
				<h5>Passwort bearbeiten:</h5>
				<form action="../../KontoBearbeitenServlet" method="POST" name="pwBearbeiten">
					<input type="password" name="passwort" id="aktuellesPW" size="60" maxlength="20" placeholder="Aktuelles Passwort" required>
					<br>
					<input type="password" name="passwortNeu" id="pw" pattern=".{8,}" size="60" maxlength="20" placeholder="Neues Passwort" required> <label id="meldungPW1"> </label>
					<br>
					<input type="password" name ="passwortNeu2" id="repeatPW" pattern=".{8,}" size="60" maxlength="20" placeholder="Passwort wiederholen" required> <label id="meldungPW2"> </label><br>
					
					<button type="submit" id="absendeButton" name="pwBearbeiten">Änderung speichern</button>
				</form>	
				</div>
				
				<button type="submit" name="emailBearbeiten" id="emailButton">Email ändern</button>
				<button type="submit" name="pwBearbeiten" id="pwButton">Passwort ändern</button>
				
			</article>
		
		<article class="mainAdresse">	
				<h2>Adresse</h2>
				<h5>Standardrechnungsadresse verwalten:</h5>
			
				
				<form action="../../KontoBearbeitenServlet" method="POST" name="adresseBearbeiten" id="mainAdresse">
				<label>Vorname:</label>
				<input type="text" name="vorname" value="${adresse.vorname}" maxlength="20" required><br>
				<label>Nachname:</label>
				<input type="text" name="nachname" value="${adresse.nachname}" maxlength="20" required><br>
				<label>Straße:</label>
				<input type="text" name="strasse" value="${adresse.strasse}" maxlength="30" required><br>
				<label>Hr:</label>	
				<input type="number" name="hausnummer" value="${adresse.hausnummer}" maxlength="5" required><br>
				<label>PLZ:</label>
				<input type="number" name="plz" value="${adresse.plz}" maxlength="5" required><br>
				<label>Stadt:</label>
				<input type="text" name="stadt" value="${adresse.stadt}" maxlength="30" required><br>
				<label>Etage:</label>
				<input type="text" name="etage" value="${adresse.etage}" maxlength="8" required><br>
				<label>Telefonnummer:</label>
				<input type="text" name="telefonnummer" value="${adresse.telefonnummer}" maxlength="15" required><br>
				<label>Geburstag:</label>
				<input type="text" name="geburtstag" value="${adresse.geburtstag}" maxlength="11" required><br>
				<label>Hinweis</label>
				<input type="text" name="hinweis" value="${adresse.hinweis}" maxlength="250"><br>
		
				<button type="submit" name="adresseBearbeiten">Änderung speichern</button>
				</form>	
	
			
			<button type="submit" name="adresseButton" id="adresseButton">Adresse bearbeiten</button>
		</article>
		
</section>


</main>
<%@ include file="../jspf/footer.jspf"%>
</html>