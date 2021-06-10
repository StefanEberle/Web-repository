<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Artikel</title>
<base href="${pageContext.request.requestURI}/">
<script type="text/javascript" src="../../js/filter.js"></script>
<script type="text/javascript" src="../../js/warenkorb.js"></script>
<link rel="stylesheet" type="text/css"
	href="../../css/artikelAuswahl.css">

</head>
<%@ include file="../jspf/header.jspf"%>

<main>

<aside>
<form action="../../AuswahlArtikelServlet" method="POST">
	<p>Sorten</p>
	<input type="hidden" name="kategorie" value="${param.kategorie}" >
	<input type="hidden" name="unterKategorie" value="${param.unterKategorie}" >
	
	<section id="unterKategorie">
	
	</section>
	
	
	
	<section id="gebinde">
	<p>Gebinde</p>
	<input type="checkbox" id="filterGlas" name="glas" value="glas">
  	<label for="glas">Glas</label>	<br>
  	
  	<input type="checkbox" id="filterPet" name="pet" value="pet">
  	<label for="pet">PET</label>
	
	</section>
	
	
	<!-- <p>Marken</p> <div id="marken"></div>  -->
	
	   
<button type="submit" name="filtern">Filtern</button>
</form>
</aside>


<table id="artikelListe">
	
	<c:if test="${not empty artikelList}">
	
		<c:forEach var="a" items="${artikelList}" varStatus="counter">
	
	
		
		<c:if test="${(counter.index mod 3) == 0 }">
			<tr>
			
		</c:if>
		
		
		
		<td class="${a.gebinde}" id="artikel">
			
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
			
			<input type="number" id="menge" name="menge" min="1" max="10" value="1">
			<button type="submit" class="fa fa-shopping-cart" id="warenkorbIcon" value="${a.artikelID}" onclick="addShoppingCart(this.value)"></button>
			
		</td>
		
		<c:if test="${(counter.count mod 3) == 0 && counter.last}">
			</tr> 
		</c:if>
			
			
		</c:forEach>
	
	
	</c:if>

</table>


</main>
<%@ include file="../jspf/footer.jspf"%>
</html>