<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Account</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css" href="../../css/konto.css">
<script type="text/javascript" src="../../js/konto.js"></script>
<script type="text/javascript" src="../../js/passwortPruefen.js"></script>
</head>
<%@ include file="../jspf/header.jsp"%>
<main>

<div class="main">

	<div class="mainTitel">
		<h1>Mein Konto</h1>
	</div>

	<div class="mainTable">
		
		<div class="mainSideBar">
			<h5>Mein Konto</h5>
			<a>Bestellungen</a> <br>
			<a>Adresse</a> <br>
			<a>Passwort ändern</a>
		</div>
	
		<div class="mainContent">
			<div class=mainKonto>
				<h2>Kontoinformationen</h2>
				
				
				<div id="emailBearbeiten">
				<h5>Email bearbeiten:</h5>
				<form action="../../KontoBearbeitenServlet" method="POST" name="emailBearbeiten" value="EmailBearbeiten">
				 	<input type="text" name="email" id="email" size="60" maxlength="60" placeholder="Email Adresse" value="${user.email}" onchange="loadDoc()"> <br> <label id="emailTest"> </label>
					<br>
					<input type="password" name="passwort" size="60" maxlength="60" placeholder="Passwort">
					<br>
					<button type="submit" name="emailBearbeiten" onclick="">Änderung speichern</button>
				</form>	
				</div>
				
				<div id="pwBearbeiten"> 
				<h5>Passwort bearbeiten:</h5>
				<form action="../../KontoBearbeitenServlet" method="POST" name="pwBearbeiten" value="PWBearbeiten">
					<input type="password" name="passwort" id="aktuellesPW" size="60" maxlength="60" placeholder="Aktuelles Passwort">
					<br>
					<input type="password" name="passwortNeu" id="pw" size="60" maxlength="60" placeholder="Neues Passwort" onchange="pwRules(this)"> <label id="meldungPW1"> </label>
					<br>
					<input type="password" name ="passwortNeu2" id="repeatPW" size="60" maxlength="60" placeholder="Passwort wiederholen" disabled="disabled"> <label id="meldungPW2"> </label>
					
					<button type="submit" id="absendeButton" name="pwBearbeiten" onclick="" disabled>Änderung speichern</button>
				</form>	
				</div>
				
				<button type="submit" name="emailBearbeiten" id="emailButton">Email ändern</button>
				<button type="submit" name="pwBearbeiten" id="pwButton">Passwort ändern</button>
				
			</div>
		
		<div class="mainAdresse">	
				<h2>Adresse</h2>
				<h5>Standardrechnungsadresse verwalten:</h5>
			
				
			<div id="mainAdresse">
				<form action="../../KontoBearbeitenServlet" method="POST" name="adresseBearbeiten">
				<label>Vorname:</label>
				<input type="text" name="vorname" value="${adresse.vorname}" maxlength="75"><br>
				<label>Nachname:</label>
				<input type="text" name="nachname" value="${adresse.nachname}" maxlength="75"><br>
				<label>Straße:</label>
				<input type="text" name="strasse" value="${adresse.strasse}" maxlength="75"><br>
				<label>Hr:</label>	
				<input type="number" name="hausnummer" value="${adresse.hausnummer}" maxlength="5"><br>
				<label>PLZ:</label>
				<input type="number" name="plz" value="${adresse.plz}" maxlength="5"><br>
				<label>Stadt:</label>
				<input type="text" name="stadt" value="${adresse.stadt}" maxlength="75"><br>
				<label>Etage:</label>
				<input type="text" name="etage" value="${adresse.etage}" maxlength="75"><br>
				<label>Telefonnummer:</label>
				<input type="text" name="telefonnummer" value="${adresse.telefonnummer}" maxlength="75"><br>
				<label>Geburstag:</label>
				<input type="text" name="geburstag" value="${adresse.geburtstag}" maxlength="75"><br>
				<label>Hinweis</label>
				<input type="text" name="stadt" value="${adresse.hinweis}" maxlength="75"><br>
		
				<button type="submit" name="adresseBearbeiten">Änderung speichern</button>
				</form>	
	
			</div>
			<button type="submit" name="adresseButton" id="adresseButton">Adresse bearbeiten</button>
			</div>
		</div>
	</div>
</div>


</main>
<%@ include file="../jspf/footer.jsp"%>
</html>