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
<body>
	<main>

		<h1>Meine Bestellungen</h1>
		
		<div id= bestellungenListe>
		<div><h2>BestellNr</h2></div>
		<div><h2>BestellStatus</h2></div>
		<div><h2>RechnungsNummer</h2></div>
		<div><h2>Rechnungsstatus</h2></div>
		
		
		<c:forEach var="a" items="${bestellungList}" varStatus="counter">
		
		<div>Bestellnr= ${a.bestellungID}</div>
		<div>Bestellstatus = ${a.status}</div>
		
		
		
		
		  <c:forEach var="b" items="${rechnungList}" varStatus="counter">
		  </c:forEach>
		  <div>
			
			 RechnungNr: ${b.rechnungID}
			
		</div>
		<div>
	
			 Rechnungsstatus: ${b.rechnungsstatus}
			
		 </div>
		 </c:forEach>
		 
		 </div>
		
	
		
		
	
	</main>
</body>
</html>