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

<h1>Ihre Rechnung</h1>


<div> Vielen Dank für Ihre Bestellung. Anbei sehen Sie die dazugehörige Rechnung.</div>

<div>
<h3>Rechnungsempfänger: ${adresse.vorname} ${adresse.nachname}</h3>
<h4>${adresse.strasse} ${adresse.hausnummer}</h4>
<h4>${adresse.plz}</h4>
</div>

<div>Rechnungsnummer: ${rechnung.rechnungID}</div>

<div>

	<c:forEach var="a" items="${warenkorbArtikelList}"
						varStatus="counter">


						<div class="warenkorbItems" id="warenkorbArtikel">
								<h5>Marke: ${a.marke}</h5>
								<h5>Gebinde: ${a.gebinde}</h5>

								<h5>Stückzahl: ${a.stueckzahl}</h5>

								<h5>Preis pro Stück= ${a.gesamtpreis}</h5>

						</div>
						
						</c:forEach>
					
					<h3 id="gesamtsumme">Gesamtpreis ${gesamtsumme} €</h3>
				
</div>
<div>Zahlungsart: ${rechnung.bezahlung}</div>


	</main>
	
<%@ include file="../jspf/footer.jspf"%>
</html>