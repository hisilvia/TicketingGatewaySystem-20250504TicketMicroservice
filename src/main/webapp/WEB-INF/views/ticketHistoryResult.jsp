<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>View Tickets</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Ticket</th>
                    <th>Action</th>
					<th>ActionBy</th>
					<th>ActionDate</th>
					<th>Comments</th>									
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ticket}" var="ticket">
                    <tr>
                        <td>${ticketHistory.id}</td>
						
                        <td>${ticketHistory.ticket}</td>
						
                        <td>${ticketHistoryt.action}</td>
						<td>${ticketHistory.actionBy}</td>
						<td>${ticketHistory.actionDate}</td>
						<td>${ticketHistory.comments}</td>
																	
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>