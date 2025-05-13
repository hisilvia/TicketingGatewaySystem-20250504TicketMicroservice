<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>View Tickets</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
		<h2>Ticket History Info......</h2><br/>
		<form:form modelAttribute="ticketHistory" method="post" action="submitTicketHistory">
			
			Ticket: <form:input path="ticket"/>  <br/><br/>
			Action: <form:input path="action"/> <br/><br/>
			ActionBy: <form:input path="actionBy"/> <br/><br/>
			ActionDate: <form:input path="actionDate"/> <br/><br/>
			Comment: <form:textarea path="comments"/>  <br/><br/>
									
			</br></br>
			<input type='submit' id='add' value='Submit'/>
			</br></br>
		</form:form>
    </body>
</html>