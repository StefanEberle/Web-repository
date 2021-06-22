<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Artikel löschen</title>
<base href="${pageContext.request.requestURI}/">
<script type="text/javascript" src="../../js/kategorieAusgabe.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/deleteArtikel.css">
<link rel="stylesheet" type="text/css"
	href="../../css/artikelAuswahl.css">
</head>
<%@ include file="../jspf/header.jspf"%>
<main>
<aside>
<a href="../artikelErzeugen.jsp">Hinzufügen</a>
<a href="../deleteUnterKategorie.jsp">Unterkategorie löschen</a>
</aside>


<section class="artikelDelete">
<form action="../../AuswahlArtikelServlet" method="GET">
<h3>Artikel Unterkategorie auswwählen:</h3>
<article id="erzeugen1">
<label>Kategorie:</label>
<select id="kategorie2" name="kategorie" required>
<option> </option>
</select>

<label>Unterkategorie:</label>
<select id="unterkategorie" name="unterKategorie" required>
<option></option>
</select>
</article>
<button type="submit" name="deleteArtikelButton" value="artikelDelete">Suche</button>
</form>
</section>

<div id="artikelListe">
	
	<c:if test="${not empty artikelList}">
	
		<c:forEach var="a" items="${artikelList}" varStatus="counter">


		<div class="${a.gebinde}" id="artikel">
			
			<img src="../../RetrieveImageServlet?artikelID=${a.artikelID}" class="artikelBild">
		
			<ul class="artikelBeschreibung">
				<li>Marke: ${a.marke}</li>
				<li>Gebinde: ${a.gebinde}</li>
				<li>Füllmenge: ${a.fuellmenge}</li>
				<li>Stückzahl: ${a.stueckzahl}</li>
				<li>Gesamtpreis: ${a.gesamtpreis}</li>
				<li>Pfand: ${a.pfandGesamt}</li>
				<li>Preis pro Liter: ${a.epJeLiter} €/Liter</li>
			</ul>
			<form action="../../DeleteArtikelServlet" method="POST">
			<button type="submit" class="fa fa-trash-o" id="deleteIcon" value="${a.artikelID}" name="deleteArtikel"></button>
			</form>
		</div>
		
	
			
		</c:forEach>
	
	
	</c:if>

</div>

</main>
<%@ include file="../jspf/footer.jspf"%>
</html>