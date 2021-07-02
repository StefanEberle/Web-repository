<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Warenkorb</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css" href="../../css/warenkorb.css">
<script type="text/javascript" src="../../js/warenkorb.js"></script>

</head>
<%@ include file="../jspf/header.jspf"%>
<body>
	<main>
<h1>Warenkorb</h1>
	<!--  	<section>
			<article class="mainAdresse">
				<h2>Adresse</h2>

				<form action="../../KontoBearbeitenServlet" method="POST"
					name="adresseBearbeiten" id="mainAdresse">
					<label>Vorname:</label> <input type="text" name="vorname"
						value="${adresse.vorname}" maxlength="75"><br> <label>Nachname:</label>
					<input type="text" name="nachname" value="${adresse.nachname}"
						maxlength="75"><br> <label>Straße:</label> <input
						type="text" name="strasse" value="${adresse.strasse}"
						maxlength="75"><br> <label>Hr:</label> <input
						type="number" name="hausnummer" value="${adresse.hausnummer}"
						maxlength="5"><br> <label>PLZ:</label> <input
						type="number" name="plz" value="${adresse.plz}" maxlength="5"><br>
					<label>Stadt:</label> <input type="text" name="stadt"
						value="${adresse.stadt}" maxlength="75"><br> <label>Etage:</label>
					<input type="text" name="etage" value="${adresse.etage}"
						maxlength="75"><br> <label>Telefonnummer:</label> <input
						type="text" name="telefonnummer" value="${adresse.telefonnummer}"
						maxlength="75"><br> <label>Geburstag:</label> <input
						type="text" name="geburtstag" value="${adresse.geburtstag}"
						maxlength="75"><br> <label>Hinweis</label> <input
						type="text" name="hinweis" value="${adresse.hinweis}"
						maxlength="75"><br>

					<button type="submit" name="adresseBearbeiten">Änderung
						speichern</button>
				</form>


				<button type="submit" name="adresseButton" id="adresseButton">Adresse
					bearbeiten</button>
			</article>
		</section> -->
<c:if test="${ empty WarenkotbArtikelList}">

<p>Sie haben noch keine Artikel zum Warenkorb hinzu gefügt. </p>

</c:if>


	<c:if test="${not empty WarenkorbArtikelList}">
		<div id="warenkorbArtikelList">

		<c:forEach var="a" items="${warenkorbArtikelList}" varStatus="counter">

		
			<div class="${a.gebinde}" id="WarenkorbArtikel"><img
				src="../../RetrieveImageServlet?artikelID=${a.artikelID}"
				class="artikelBild">
				
				<ul class="artikelBeschreibung">
					<li>Marke: ${a.marke}</li>
					<li>Gebinde: ${a.gebinde}</li>
					
					<li>Stückzahl: ${a.stueckzahl}</li>
					
					<li>Preis pro Stück= ${a.gesamtpreis}</li>
					
					<li id= warenkorbAnzahlChange class="control_InputButton">
					<input type="number" id="menge" name="menge" min="1" max="10" value="${a.stueckzahl}">
					<button type="submit" id="warenkorbIcon" value="${a.artikelID}" name ="artikelID" onclick = "addArtikelAjax(this)">Anzahl ändern</button>
					</li>
					
					<li><button type="submit" id ="deleteArtikel" value ="${a.artikelID}" name = "deleteArtikel" onclick="deleteArtikel(this)">Artikel entfernen</button></li>
					
					
		
				</ul> 	
				
			</div>
			
			 
						</c:forEach>
						<p>Gesamtpreis=  ${gesamtsumme} €</p>
						
	
				
</div>
</c:if>


<form action="../../createBestellungRechnungServlet" method="POST">
  		<div>
		
		<section id="zahlungsart">
			<p>Zahlungsart</p>
			<input type="radio" id="bar" name="zahlungsmethode" value="bar" checked>
			<label for="bar">Barzahlung (bei Lieferung)</label>
			<br> <input type="radio" id="rechnung" name="zahlungsmethode" value="rechnung">
				<label for="rechnung">Zahlung per Rechnung</label> <br>
				<input type="radio" id="bankeinzug" name="zahlungsmethode" value="bankeinzug">
				<label for="bankeinzug">Zahlung per Bankeinzug</label>
				
				</section>
				</div>
				

			<div id="unsereBank">
				<p>Unsere Kontodaten:<br/>
				Kontoinhaber: Max Mustermann<br/>
				Kontonummer: 123456789<br/>
				Bank:Musterbank</p>
			</div>

			<div id="kontodaten">
				<label>Kontoinhaber:in</label> <input type="text" name="strasse"
					value="Kontoinhaber" maxlength="75"><br> <label>Kontonummer</label>
				<input type="text" name="kontonummer" value="Kontonummer"
					maxlength="75"><br> <label>Bank</label> <input
					type="text" name="bank" value="Bank" maxlength="75"><br>
			</div>
	


		
		
		<button type="submit" name="warenkorbAnzeigen">
				Zahlungspflichtig bestellen</button>
		</form> 



	</main>
</body>
</html>