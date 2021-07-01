<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Artikel erzeugen</title>
<base href="${pageContext.request.requestURI}/">
<script type="text/javascript" src="../../js/kategorie-Selected-Ausgabe.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/artikelErzeugen.css">
</head>
<%@ include file="../jspf/header.jspf"%>
<main>

<aside>
<a href="../deleteUnterKategorie.jsp">Unterkategorie löschen</a>
<a href="../deleteArtikel.jsp">Artikel löschen</a>
</aside>

<c:if test="${not empty errorRequest}">
		<c:out value="${errorRequest}" />
	</c:if>
<section class="kategorieAdd">

<form action="../../CreateKategorieServlet" method="POST">
<h3>Kategorie hinzufügen</h3>
<label>Kategorie:</label>
<input type="text" name="kategorieBezeichnung" maxlength="80" placeholder="Name der Kategorie" required>
<button type="submit" name="KategorieButton">Speichern</button>
</form>
</section>

<section class="unterKategorieAdd">
<form action="../../CreateUnterKategorieServlet" method="POST">
<h3>Unterkategorie hinzufügen</h3>

<label>Zugehörige Kategorie auswählen :</label>
<select id="kategorie" name="kategorieBezeichnungAuswahl" required>
<option> </option>
</select>
<br>
<label>Unterkategorie:</label>
<input type="text" name="unterKategorieErzeugen"  maxlength="80" placeholder="Name der Unterkategorie" required>
<button type="submit" name="unterKategorieButton">Speichern</button>
</form>
</section>



<section class="artikelAdd">
<form action="../../CreateArtikelServlet" method="POST" enctype="multipart/form-data">
<h3>Artikel hinzufügen</h3>
<article id="erzeugen1">
<label>Kategorie:</label>
<select id="kategorie2" name="kategorie" required>
<option> </option>
</select>

<label>Unterkategorie:</label>
<select id="unterkategorie" name="unterKategorie" required>
<option></option>
</select>


<label>Gebinde:</label>
<select name="gebinde" required>
<option>PET</option>
<option>Glas</option>
</select>
</article>

<article id="erzeugen2">
<input type="text" name="marke"  maxlength="40" placeholder="Markennamen" required>
<br>
<input type="number" step="0.01" name="gesamtpreis" min="0.1" max="999.99" maxlength="5" placeholder="Gesamtpreis pro Kasten" required>
<br>
<input type="number" step="0.01" name="fuelmenge"  min="0.1" max="6.0" maxlength="5" placeholder="Füllmenge" required>
<br>

<label>Stückzahl</label>
<select name="stueckzahl" required>
<option value="1"> 1 </option>
<option value="2"> 2 </option>
<option value="3"> 3 </option>
<option value="4"> 4 </option>
<option value="6"> 6 </option>
<option value="8"> 8 </option>
<option value="10"> 10 </option>
<option value="12"> 12 </option>
<option value="16"> 16 </option>
<option value="20"> 20 </option>
<option value="24"> 24 </option>
</select>

<br>

<label>Pfand pro Flasche</label>
<select name="pfandProFlasche" required>
<option value="0.00"> 0.00 </option>
<option value="0.08"> 0.08 </option>
<option value="0.15"> 0.15 </option>
<option value="0.25"> 0.25 </option>
</select>
<br>

<label>Pfand Kasten</label>
<select name="pfandKasten" required>
<option value="0.00"> 0.00 </option>
<option value="1.50"> 1.50 </option>
</select>
<br>
<input type="file" id="bild" name="artikelBild" accept="image/gif, image/jpeg, image/png" required>
<br>
</article>

<button type="submit" name="artikelButton">Artikel speichern</button>
</form>
</section>

</main>
<%@ include file="../jspf/footer.jspf"%>
</html>