<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Artikel erzeugen</title>
<base href="${pageContext.request.requestURI}/">
<script type="text/javascript" src="../../js/kategorie-Selected-Ausgabe.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/delete-UK-undArtikel.css">
</head>
<%@ include file="../jspf/header.jspf"%>
<main>
<aside>
<a href="../artikelErzeugen.jsp">Hinzufügen</a>
<a href="../deleteArtikel.jsp">Artikel löschen</a>
</aside>



<section>

<c:if test="${not empty errorRequest}">
		<c:out value="${errorRequest}" />
	</c:if>
	
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