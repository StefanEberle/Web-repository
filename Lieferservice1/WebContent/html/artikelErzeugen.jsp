<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Artikel erzeugen</title>
<base href="${pageContext.request.requestURI}/">
<script type="text/javascript" src="../../js/kategorieAusgabe.js"></script>
</head>
<%@ include file="../jspf/header.jsp"%>
<main>

<div>
<form action="../../ArtikelServlet" method="POST" enctype="multipart/form-data">

<input type="text" name="marke" size="30" maxlength="40" placeholder="Marke">
<br>

<label>Kategorie:</label>
<select id="kategorie" name="kategorie">
<option> </option>
</select>
<br>

<label>Unterkategorie:</label>
<select id="unterkategorie" name="unterKategorie">
<option></option>
</select>
<br>

<label>Gebinde:</label>
<select name="gebinde">
<option>PET</option>
<option>Glas</option>
</select>
<br>

<input type="number" step="0.01" name="fuelmenge"  maxlength="5" placeholder="Füllmenge">
<br>
<input type="number" step="1" name="stueckzahl"  maxlength="40" placeholder="Stückzahl Kasten">
<br>
<input type="number" step="0.01" name="gesamtpreis"  maxlength="5" placeholder="Gesamtpreis pro Kasten">
<br>
<input type="number" step="0.01" name="pfandProFlasche"  maxlength="5" placeholder="Pfand pro Flasche">
<br>
<input type="number" step="0.01" name="pfandKasten" maxlength="5" placeholder="Pfand pro Kasten">
<br>
<input type="file" id="bild" name="artikelBild" size="50">
<br>


<button type="submit" name="artikelButton">Artikel speichern</button>
</form>
</div>



</main>
<%@ include file="../jspf/footer.jsp"%>
</html>