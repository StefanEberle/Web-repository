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
		

		<c:if test="${not empty errorRequest}">
			<c:out value="${errorRequest}" />
		</c:if>

		<c:choose>
			<c:when test="${not empty warenkorbArtikelList}">
				<div id="warenkorbArtikelList">

					<c:forEach var="a" items="${warenkorbArtikelList}"
						varStatus="counter">


						<div class="warenkorbItems" id="warenkorbArtikel">
							<img src="../../RetrieveImageServlet?artikelID=${a.artikelID}"
								class="artikelBild">

							<ul class="artikelBeschreibung">
								<li>Marke: ${a.marke}</li>
								<li>Gebinde: ${a.gebinde}</li>

								<li>Stückzahl: ${a.stueckzahl}</li>

								<li>Preis pro Stück= ${a.gesamtpreis}</li>

								<li id=warenkorbAnzahlChange class="control_InputButton"><input
									type="number" id="menge${a.artikelID}" name="menge" min="1"
									max="10" value="${a.stueckzahl}">
									<button type="submit" id="warenkorbIcon" value="${a.artikelID}"
										name="artikelID" onclick="addArtikelAjax(this)">Anzahl
										ändern</button></li>


								<li><form action="../../DeleteWarenkorbArtikelServlet"
										method="POST">
										<button type="submit" 
											value="${a.artikelID}" name="deleteArtikel">Artikel
											entfernen</button>
									</form></li>

							</ul>

						</div>


					</c:forEach>
					<h3 id="gesamtsumme">Gesamtpreis ${gesamtsumme} €</h3>



				</div>

				<form action="../../CreateBestellungRechnungServlet" method="POST">
					<div>

						<section id="zahlungsart">
							<p>Zahlungsart</p>
							<input type="radio" id="bar" name="zahlungsmethode" value="bar"
								checked> <label for="bar">Barzahlung (bei
								Lieferung)</label> <br> <input type="radio" id="rechnung"
								name="zahlungsmethode" value="rechnung"> <label
								for="rechnung">Zahlung per Rechnung</label> <br> <input
								type="radio" id="bankeinzug" name="zahlungsmethode"
								value="bankeinzug"> <label for="bankeinzug">Zahlung
								per Bankeinzug</label>

						</section>
					</div>


					<div id="unsereBank">
						<p>
							Unsere Kontodaten:<br /> Kontoinhaber: Max Mustermann<br />
							Kontonummer: 123456789<br /> Bank:Musterbank
						</p>
					</div>
					
					

					<div id="kontodaten">
					
					<c:choose>
          	<c:when test="${not empty bankdaten}">
          	
          		<label>Kontoinhaber:in</label> <input type="text" name="strasse"
							value="${bankdaten.kontoinhaber}" maxlength="75"><br>
							 <label>Kontonummer</label>
						<input type="text" name="iban" value="${bankdaten.IBAN}"
							maxlength="75"><br> 
							<label>Bank</label> <input
							type="text" name="bank" value="${bankdaten.bank}" maxlength="75"><br>
          		
            </c:when>
            <c:otherwise>
            	<label>Kontoinhaber:in</label> <input type="text" name="kontoinhaber"
							 maxlength="49" required><br>
							<label>Kontonummer</label>
						<input type="text" name="iban" maxlength="22" required><br> 
							<label>Bank</label>
							 <input type="text" name="bank" maxlength="49" required><br>
			</c:otherwise>
		</c:choose>
					
						
					</div>





					<button type="submit" name="warenkorbAnzeigen">
						Zahlungspflichtig bestellen</button>
				</form>


			</c:when>
			<c:otherwise>
				<p>Sie haben noch keine Artikel in den Warenkorb gelegt.</p>
			</c:otherwise>
		</c:choose>






	</main>
	
<%@ include file="../jspf/footer.jspf"%>
</html>