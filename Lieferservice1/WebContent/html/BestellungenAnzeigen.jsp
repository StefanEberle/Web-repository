<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BestellungenAnzeigen</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css" href="../../css/warenkorb.css">

</head>
<%@ include file="../jspf/header.jspf"%>
<body>
	<main>

		<section>
			<!--<article class="mainAdresse">	
			<!--  	<h2>Adresse</h2>
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
		</article>-->
		</section>
		<h1>TESTESTEST</h1>

		<section>
			<table id="warenkorbArtikelList">

				<tr>
					<th>BestellungID</th>
					<th>BestellStatus</th>
					<th>Preis</th>
					<th>Rechnungsstatus</th>
				<tr>

					<c:forEach var="a" items="${bestellungList}" varStatus="counter">

						<tr>
							<td>${a.bestellungID}</td>
							<td>${a.status}</td>
</c:forEach>
							<c:forEach var="b" items="${rechnungList}" varStatus="counter">
								<td>${b.summe}</td>
								<td>${b.rechnungsstatus}</td>
							</c:forEach>
						</tr>


					
				
			</table>

		</section>






	</main>
</body>
</html>