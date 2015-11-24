<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello, EE7</title>
<jsp:include page="/WEB-INF/sub-page/styles.jsp" />
<style type="text/css">
ul.servlet-list {
	
}

ul.servlet-list li {
	float: left;
	margin: 0 1em;
}

.clear-both {
	clear: both;
}
</style>
</head>
<body>
	<p>Hello, and welcome!</p>
	<p>
		<c:out value="To this playground, my dear ${username}" />
		<br /> ContextPath:
		<c:out value="${pageContext.servletContext.contextPath}" />
	</p>
	<div class="clear-both">
		<ul class="servlet-list">
			<c:forEach var="servlet" items="${servlets}">
<%-- 			<c:forEach var="servlet" items="${pageContext.servletContext.servlets}"> <!--the ServletContext.getServlets() method has been deprecated-->--%>
				<li><a
					href="${pageContext.servletContext.contextPath}${servlet}">${servlet }</a></li>
			</c:forEach>
		</ul>
		<br class="clear-both" />
	</div>
	<div class="clear-both">
		
	</div>
	<br/><br/>
	<div>
		Or you want to head to <a href="/HelloJSF/faces/index.xhtml">The world
			of JSF</a>.
	</div>
</body>
</html>