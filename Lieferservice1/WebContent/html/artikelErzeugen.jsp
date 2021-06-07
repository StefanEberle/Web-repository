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

<aside>
<a href="../delete.jsp">Zum Löschen</a>
</aside>

<section>

<form action="../../KategorieServlet" method="POST">
<h3>Kategorie hinzufügen</h3>
<label>Kategorie:</label>
<input type="text" name="kategorieBezeichnung" size="40" maxlength="80" placeholder="Name der Kategorie" required>
<button type="submit" name="KategorieButton">Speichern</button>
</form>
</section>

<section>
<form action="../../UnterKategorieServlet" method="POST">
<h3>Unterkategorie hinzufügen</h3>

<label>Zugehörige Kategorie auswählen :</label>
<select id="kategorie" name="kategorieBezeichnungAuswahl" required>
<option> </option>
</select>
<br>
<label>Unterkategorie:</label>
<input type="text" name="unterKategorieErzeugen" size="40" maxlength="80" placeholder="Name der Unterkategorie" required>
<button type="submit" name="unterKategorieButton">Speichern</button>
</form>
</section>



<section>
<form action="../../ArtikelServlet" method="POST" enctype="multipart/form-data">
<h3>Artikel hinzufügen</h3>
<article>
<label>Kategorie:</label>
<select id="kategorie2" name="kategorie" required>
<option> </option>
</select>
</article>

<article>
<label>Unterkategorie:</label>
<select id="unterkategorie" name="unterKategorie" required>
<option></option>
</select>
</article>

<article>
<label>Gebinde:</label>
<select name="gebinde" required>
<option>PET</option>
<option>Glas</option>
</select>
</article>

<article>
<input type="text" name="marke" size="30" maxlength="40" placeholder="Marke" requierd>
<br>
<input type="number" step="0.01" name="fuelmenge"  min="0.19" max="99999" maxlength="5" placeholder="Füllmenge" required>
<br>
<input type="number" step="1" name="stueckzahl"  min="1" max="99999" maxlength="5" placeholder="Stückzahl Kasten" required>
<br>
<input type="number" step="0.01" name="gesamtpreis" min="0.1" max="99999" maxlength="5" placeholder="Gesamtpreis pro Kasten" required>
<br>
<input type="number" step="0.01" name="pfandProFlasche"  min="0.08" max="99999" maxlength="5" placeholder="Pfand pro Flasche">
<br>
<input type="number" step="0.01" name="pfandKasten" min="1" max="99999" maxlength="5" placeholder="Pfand pro Kasten">
<br>
<input type="file" id="bild" name="artikelBild" accept="image/*" required>
<br>
</article>

<button type="submit" name="artikelButton">Artikel speichern</button>
</form>
</section>

</main>
<%@ include file="../jspf/footer.jsp"%>
</html>