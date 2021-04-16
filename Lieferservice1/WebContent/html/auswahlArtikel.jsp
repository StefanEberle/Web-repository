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
<%@ include file="../jspf/header.jsp"%>

<main>

<div>
	<p>Sorte</p>
	<div id="unterKategorie">
	
	</div>
	
	<div>
	
	<div id="gebinde">
	<p>Gebinde</p>
	<input type="checkbox" id="glas" name="gebinde" value="Glas">
  	<label for="glas">Glas</label>	<br>
  	
  	<input type="checkbox" id="pet" name="gebinde" value="PET">
  	<label for="pet">PET</label>
	
	</div>
	<p>Marken</p>
	<div id="marken">
	
	</div>
	
	</div>


</div>

<div id="artikel">
	<c:if test="${not empty artikelList}">
		<c:forEach var="a" items="${artikelList}">

		<div class="${a.gebinde}" name="${a.marke}">
			<div class="artikelBild">
				<img src="../../RetrieveImageServlet?artikelID=${a.artikelID}">
			</div>

			<div class="artikelBeschreibung">
				<p>Marke: ${a.marke}</p>
				<p>Gebinde: ${a.gebinde}</p>
				<p>Füllmenge: ${a.fuellmenge}</p>
				<p>Stückzahl: ${a.stueckzahl}</p>
				<p>Gesamtpreis: ${a.gesamtpreis}</p>
				<p>Pfand: ${a.pfandGesamt}</p>
				<p>( ${a.epJeLiter} €/Liter)</p>
			</div>
			<input type="number" id="menge" name="menge" min="1" max="10" value="1">
			<button type="submit" value="${a.artikelID}" onclick="addShoppingCart(this.value)">Hinzufügen</button>
		</div>
			<br>
		</c:forEach>
	</c:if>

</div>


</main>
<%@ include file="../jspf/footer.jsp"%>
</html>