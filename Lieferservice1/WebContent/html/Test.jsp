<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css"
	href="../../css/warenkorb.css">

</head>
<%@ include file="../jspf/header.jspf"%>
<body>
<main>

<section>
<article class="mainAdresse">	
				<h2>Adresse</h2>
				<h5>Standardrechnungsadresse verwalten:</h5>
			
				
				<form action="../../KontoBearbeitenServlet" method="POST" name="adresseBearbeiten" id="mainAdresse">
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
				<input type="text" name="geburtstag" value="${adresse.geburtstag}" maxlength="75"><br>
				<label>Hinweis</label>
				<input type="text" name="hinweis" value="${adresse.hinweis}" maxlength="75"><br>
		
				<button type="submit" name="adresseBearbeiten">Änderung speichern</button>
				</form>	
	
			
			<button type="submit" name="adresseButton" id="adresseButton">Adresse bearbeiten</button>
		</article>
</section>

<section>
<table id="warenkorbArtikelList">
	
		<c:if test="${not empty warenkorbArtikelList}">
	
		
		
		<tr>
		<th>Artikel</th>
		<th>Gebinde</th>
		<th>Anzahl</th>
		<th>Preis</th>
		<th>Menge ändern</th>
		<tr>
		
		<c:forEach var="a" items="${warenkorbArtikelList}" varStatus = "counter">
		 <tr>
		<td class="dd" id="artikel"><img
				src="../../RetrieveImageServlet?artikelID=${a.artikelID}"
				class="artikelBild">
		</td>
		<td>Gebinde: ${a.gebinde}</td>
		<td> ${a.stueckzahl}</td>
		<td>Preis pro Artikel: ${a.gesamtpreis} €/Artikel</td>
		<td> 
		<button>Anzahl erhöhen</button>
				<button>Anzahl verringern</button>
			
		</td>
		
		</tr>
		
		</c:forEach>
		
	
		</c:if>
	
	</table>
	</section>
	<section><p>Gesamtpreis: ${gesamtsumme}</p></section>
	
	<form action="../../CreateBestellungRechnungServlet" method="POST" name="createBestellungRechnung" id="bestellung">
				<button type="submit" name="bestellungAufgeben" id="bestellungAufgeben">Zahlungspflichtig bestellen</button>
				</form>
				
	


</main>
</body>
</html>