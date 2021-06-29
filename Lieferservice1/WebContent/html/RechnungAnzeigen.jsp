<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rechnung</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css"
	href="../../css/warenkorb.css">

</head>
<%@ include file="../jspf/header.jspf"%>
<body>
<main>

<section>
<h1>Ihre Rechnung</h1>
<article class="mainAdresse">	

<p> Vielen Dank für Ihre Bestellung. Anbei sehen Sie die dazugehörige Rechnung.</p>
				<h2>Adresse</h2>
				<label>Vorname: ${adresse.vorname}</label><br/>
				
				<label>Nachname: ${adresse.nachname}</label><br/>
				
				<label>Straße: ${adresse.strasse} ${adresse.hausnummer}</label><br/>
	
				<label>PLZ: ${adresse.plz} ${adresse.stadt}</label><br/>
				<input type="number" name="plz" value="${adresse.plz}" maxlength="5"><br>
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
		
		</tr>
		
		</c:forEach>
		
	
		</c:if>
	
	</table>
	</section>
	<section><p>Gesamtpreis: ${gesamtsumme}</p></section>
	
	<form action="" method="POST" name="ZahlungsArt" id="zahlungsart">
				<button type="submit" name="bestellungAufgeben" id="bestellungAufgeben">Zahlungsart wählen</button>
				</form>
				
	


</main>
</body>
</html>