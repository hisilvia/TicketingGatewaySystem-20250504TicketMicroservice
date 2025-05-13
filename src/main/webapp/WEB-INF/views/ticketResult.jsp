<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Ticket Confirmation</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table>
            <thead>
                <tr>
					<th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>CreatedBy</th>
					<th>Assignee</th>
					<th>Priority</th>
					<th>Status</th>	
					<th>CreationDate</th>
					<th>Category</th>
					<th>fileAttachmentPath</th>	
					<								
                </tr>
            </thead>
            <tbody>
                
                    <tr>
						<td>${ticket.id}</td>
                        <td>${ticket.title}</td>
                        <td>${ticket.description}</td>
                        <td>${ticket.createBy}</td>
						<td>${ticket.assignee}</td>
						<td>${ticket.priority}</td>
						<td>${ticket.status}</td>
						<td>${ticket.creationDate}</td>
						<td>${ticket.category}</td>
						<td>${ticket.fileAttachmentPath}</td>												
                    </tr>
                
            </tbody>
        </table>
    </body>
</html>