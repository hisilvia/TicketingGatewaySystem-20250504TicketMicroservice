<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="ISO-8859-1">
	<style></style>
	<title>Ticket Form</title>
	
	</head>
	<body>
		<h2>Ticket Info......</h2><br/>
		<form:form modelAttribute="ticket" method="post" action="submitTicket">
			
			Title: <form:input path="title"/>  <br/><br/>
			Description: <form:input path="description"/> <br/><br/>
			
			CreatedBy: <form:input path="createdBy"/> <br/><br/>
			Assignee: <form:input path="assignee"/> <br/><br/>
			
			Priority: <form:input path="priority"/>  <br/><br/>
			Status: <form:input path="status"/> <br/><br/>
			CreationDate: <form:input path="creationDate"/> <br/><br/>
			Category: <form:input path="category"/> <br/><br/>
			FileAttachmentPath: <form:input path="fileAttachmentPath"/>  <br/><br/>
									
			</br></br>
			<input type='submit' id='add' value='Submit'/>
			</br></br>
		</form:form>
			
	</body>
</html>