<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WarenkorbAnzeigen</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css"
	href="../../css/warenkorb.css">

</head>
<%@ include file="../jspf/header.jspf"%>


<main>
<jsp: include page = "../../WarenkorbAnzeigenServlet" flush ="true"/>
	<table id="warenkorbArtikelList">
	
		<c:if test="${not empty warenkorbArtikelList}">
	
		<c:forEach var="a" items="${warenkorbArtikelList}" varStatus = "counter">
		
		<td class=${a.gebinde} id= "artikel"><img src=../../RetrieveImageServlet?artikelID=${a.artikelID}>
		
		<ul class = artikelBeschreibung>
				<li>Marke: ${a.marke}</li>
				<li>Gebinde: ${a.gebinde}</li>
				<li>Füllmenge: ${a.fuellmenge}</li>
				<li>Stückzahl: ${a.stueckzahl}</li>
				<li>Preis pro Liter: ${a.epJeLiter} €/Liter</li>
		</ul>
		
		<p>Gesamtpreis: </p>
		
		
		</td>
		
		
		</c:forEach>
		</c:if>
		
		
	
	
	</table>


</main>


</html>