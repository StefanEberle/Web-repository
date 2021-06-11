<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



[
<c:forEach var="i" items="${sucheList}" varStatus="status">

{
	"originalText":"${i.originalText}"
}

<c:if test="${not status.last}">,</c:if>
</c:forEach>
]

