<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%> --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/WEB-INF/sub-page/styles.jsp" />
<title>View Seats Info</title>
</head>
<body>
<p>
Refresh to get the newest results.
</p>
<div>
<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Price</th>
			<th>Booked</th>
			<th>Type</th>
			<th>Position</th>
		</tr>
	</thead>
	<c:forEach var="seat" items="${seats }">
		<tr>
			<td>${seat.name }</td>
			<td>${seat.price }</td>
			<td>${seat.booked }</td>
			<td>${seat.seatType.description }</td>
			<td>${seat.seatType.position}</td>
		</tr>	
	</c:forEach>
</table>
</div>
</body>
</html>