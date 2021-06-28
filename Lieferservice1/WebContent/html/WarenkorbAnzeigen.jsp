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
 <form action= "../../WarenkorbAnzeigenServlet" method = "POST" > 
           <button type= "submit" name= "warenkorbAnzeigen">
             <i class="fa fa-shopping-cart" id="warenkorbIcon"></i>
             </button> 
             </form>

</main>

<%@ include file="../jspf/footer.jspf"%>
</html>