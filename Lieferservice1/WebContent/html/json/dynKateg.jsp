


<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
[
<c:forEach var="i" items="${kategorieList}" varStatus="status">

{
	"kategorieID":"${i.kategorieID}",
	"bezeichnungKat":"${i.bezeichnungKat}"
}

<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
--%>

[
<c:forEach var="i" items="${kategorieList}" varStatus="status">

{
	"fkKategorieID":"${i.fkKategorieID}",
	"unterkategorieBez":"${i.unterkategorieBez}",
	"bezeichnungKat":"${i.bezeichnungKat}",
	"unterkategorieID":"${i.unterkategorieID}"
	
}

<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
