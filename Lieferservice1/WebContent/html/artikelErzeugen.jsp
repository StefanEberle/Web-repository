<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Artikel erzeugen</title>
<base href="${pageContext.request.requestURI}/">
<script type="text/javascript" src="../../js/kategorieAusgabe.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/artikelErzeugen.css">
</head>
<%@ include file="../jspf/header.jspf"%>
<main>

<aside>
<a href="../delete.jsp">Unterkategorie löschen</a>
<a href="../deleteArtikel.jsp">Artikel löschen</a>
</aside>

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
<input type="text" name="marke"  maxlength="40" placeholder="Marke" required>
<br>
<input type="number" step="0.01" name="fuelmenge"  min="0.19" max="99999" maxlength="5" placeholder="Füllmenge" required>
<br>
<input type="number" step="1" name="stueckzahl"  min="1" max="99999" maxlength="5" placeholder="Stückzahl Kasten" required>
<br>
<input type="number" step="0.01" name="gesamtpreis" min="0.1" max="99999" maxlength="5" placeholder="Gesamtpreis pro Kasten" required>
<br>
<input type="number" step="0.01" name="pfandProFlasche"  min="0" max="99999" maxlength="5" placeholder="Pfand pro Flasche">
<br>
<input type="number" step="0.01" name="pfandKasten" min="0" max="99999" maxlength="5" placeholder="Pfand pro Kasten">
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