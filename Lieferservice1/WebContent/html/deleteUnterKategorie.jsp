<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Artikel erzeugen</title>
<base href="${pageContext.request.requestURI}/">
<script type="text/javascript" src="../../js/kategorieAusgabe.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/delete_UK_undArtikel.css">
</head>
<%@ include file="../jspf/header.jspf"%>
<main>
<aside>
<a href="../artikelErzeugen.jsp">Hinzufügen</a>
<a href="../deleteArtikel.jsp">Artikel löschen</a>
</aside>

<section>
<form action="../../DeleteUnterkategorieServlet" method="POST">
<h3>Unterkategorie und dazugehörige Artikel löschen!</h3>
<article>
<label>Kategorie:</label>
<select id="kategorie2" name="kategorieDelete" required>
<option> </option>
</select>
</article>

<article>
<label>Unterkategorie:</label>
<select id="unterkategorie" name="unterKategorieDelete" required>
<option></option>
</select>
</article>

<button type="submit" name="deleteButton">Löschen</button>
</form>
</section>





</main>
<%@ include file="../jspf/footer.jspf"%>
</html>