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

		<c:if test="${not empty param.kategorie}">
			<input type="hidden" name="kategorie" id="filterKategorie"
				value="${param.kategorie}">
		</c:if>
		<c:if test="${empty param.kategorie}">
			<input type="hidden" name="kategorie" id="filterKategorie"
				value="noValue">
		</c:if>


		<c:if test="${not empty param.unterKategorie}">
			<input type="hidden" name="unterKategorie" id="filterUnterKategorie"
				value="${param.unterKategorie}">
		</c:if>
		<c:if test="${empty param.unterKategorie}">
			<input type="hidden" name="unterKategorie" id="filterUnterKategorie"
				value="nonValue">
		</c:if>

		<section id="unterKategorie"></section>
		<section>

			<c:if test="${not empty unterKategorienList}">
				<c:forEach var="a" items="${unterKategorienList}"
					varStatus="counter">

					<a href="../../AuswahlArtikelServlet?unterKategorie=${a.unterkategorieID}&kategorie=${a.fkKategorieID}"> ${a.unterkategorieBez} </a>
						<br>

				</c:forEach>
			</c:if>

		</section>


		<section id="gebinde">
			<p>Gebinde</p>
			<input type="checkbox" id="filterGlas" name="glas" value="glas">
			<label for="glas">Glas</label> <br> <input type="checkbox"
				id="filterPet" name="pet" value="pet"> <label for="pet">PET</label>

		</section>

		<section>
			<p>Marken</p>
			<!-- <div id="marken"></div>  -->
			<article id="marken">
			
			</article>
			<article>
			  <c:if test="${not empty markenList}">
				<select name="marken" id="selectForSearch" size="6">
					<c:forEach var="a" items="${markenList}" varStatus="counter">

						<option value="${a.marke}">${a.marke}</option>

					</c:forEach>
				
				</select>
			  </c:if>
			</article>
		</section>
		<button type="submit" name="filtern">Filtern</button>
	</form>
</aside>


<div id="artikelListe">

	<c:if test="${not empty artikelList}">

		<c:forEach var="a" items="${artikelList}" varStatus="counter">



		



			<div class="${a.gebinde}" id="artikel"><img
				src="../../RetrieveImageServlet?artikelID=${a.artikelID}"
				class="artikelBild">

				<ul class="artikelBeschreibung">
					<li>Marke: ${a.marke}</li>
					<li>Gebinde: ${a.gebinde}</li>
					<li>Füllmenge: ${a.fuellmenge} Liter</li>
					<li>Stückzahl: ${a.stueckzahl}</li>
					<li>Gesamtpreis: ${a.gesamtpreis} €</li>
					<li>zzgl. ${a.pfandGesamt} Pfand</li>
					<li>Pro Liter: ${a.epJeLiter} €/Liter</li>
				</ul> 
				
				<div class="control_InputButton">
					<input type="number" id="menge" name="menge" min="1" max="10" value="1">
					<button type="submit" class="fa fa-shopping-cart" id="warenkorbIcon" value="${a.artikelID}" onclick=""></button>
				</div>
			</div>

		


		</c:forEach>


	</c:if>

</div>


</main>
<%@ include file="../jspf/footer.jspf"%>
</html>