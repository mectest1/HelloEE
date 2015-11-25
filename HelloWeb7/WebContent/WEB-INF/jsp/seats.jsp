<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%> --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="refresh" content="10"/>
<jsp:include page="/WEB-INF/sub-page/styles.jsp" />
<title>View Seats Info</title>
<script type="text/javascript">
	window.addEventListener("load", function(e){
		var timeOut = document.getElementById("time-out");
		var curTime; 
		var interval = 1;
		setInterval(function(){
			curTime = parseInt(timeOut.firstChild.nodeValue);
			curTime -= interval;
// 			timeOut.replaceChild(timeOut.firstChild, document.createTextNode(curTime));
			if(0 != curTime){
				timeOut.removeChild(timeOut.firstChild);
				timeOut.appendChild(document.createTextNode(curTime));
			}
		}, 1000 * interval);
	});
</script>
</head>
<body>
<p>
Refresh to get the newest results. Automatically refresh in <span id="time-out">10</span> second(s).
</p>
<div>
<table>
	<col><col><col><col><col><col>
	<thead>
		<tr>
			<th>Index</th>
			<th>Name</th>
			<th>Price</th>
			<th>Booked</th>
			<th>Type</th>
			<th>Position</th>
		</tr>
	</thead>
	<c:forEach var="seat" items="${seats }" varStatus="status">
		<tr>
			<td>${status.index + 1}
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