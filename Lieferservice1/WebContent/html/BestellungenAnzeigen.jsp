<!-- Autor: Olga Ohlsson -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BestellungenAnzeigen</title>
<base href="${pageContext.request.requestURI}/">
<link rel="stylesheet" type="text/css" href="../../css/bestellungen.css">

</head>
<%@ include file="../jspf/header.jspf"%>

	<main>

		<h1>Meine Bestellungen</h1>
		
		
		<div class="first">
		<div class="kopfzeile">
			<div><h2>BestellNr</h2></div>
			<div><h2>BestellStatus</h2></div>
			<div><h2>RechnungsNummer</h2></div>
			<div><h2>Rechnungsstatus</h2></div>
			<div><h2>Summe</h2></div>
		</div>
		</div>
		
		<div id= rechnungenListe>
		<c:forEach var="a" items="${rechnungList}" varStatus="counter">
		
		<div> ${a.bestellungID}</div>
		<div>${a.status}</div>
		
		  <div>
			 ${a.rechnungID}
		</div>
		<div>
		${a.rechnungsstatus}
			
		 </div>
		 <div>
		  ${a.summe}€
		 </div>
		 
		 </c:forEach>
		 
		 </div>
		
	
		
		
		</main>
	
<%@ include file="../jspf/footer.jspf"%>
