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
	href="../../css/rechnung.css">

</head>
<%@ include file="../jspf/header.jspf"%>
<body>
<main>

<h1>Ihre Rechnung</h1>


<div> Vielen Dank für Ihre Bestellung. Anbei sehen Sie die dazugehörige Rechnung.</div>

<div>
<p>Rechnungsempfänger:<br/>
 ${adresse.vorname} ${adresse.nachname}<br/>
${adresse.strasse} ${adresse.hausnummer}<br/>
${adresse.plz} ${adresse.stadt}</p>
</div>

<div>Rechnungsnummer: ${rechnung.rechnungID} <br/>
Zahlungsart: ${rechnung.bezahlung}</div>

<div>

	<c:forEach var="a" items="${warenkorbArtikelList}"
						varStatus="counter">


						<div class="warenkorbItems" id="warenkorbArtikel">
								<p>Marke: ${a.marke}</p>
								<p>Gebinde: ${a.gebinde}</p>

								<p>Stückzahl: ${a.stueckzahl}</p>

								<p>Preis pro Stück= ${a.gesamtpreis}</p>

						</div>
						
						</c:forEach>
					
					<h3 id="gesamtsumme">Gesamtpreis ${gesamtsumme} €</h3>
				
</div>



	</main>
	
<%@ include file="../jspf/footer.jspf"%>
</html>