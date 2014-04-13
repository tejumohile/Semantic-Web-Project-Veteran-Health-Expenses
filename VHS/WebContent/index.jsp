<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
    <%@ page import="vhs.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body >
<form action="HandlerServlet" method="POST">
	
	<table>
	<tr> <td> State </td></tr>
	<tr><td> 
	<jsp:useBean id="obj" class="vhs.StateBean" scope="page"/>
	<select name="state">
	
				<c:forEach var="item" items="${obj.items}">
     			<option>${item}</option>
    			</c:forEach>
			</select>
	</td></tr>
	<tr><td>
	<select name="area">
		<option selected="selected"> State </option>
		<option>County </option>
		<option>Congressional District</option>
	</td>
	
	</tr>
	<tr><td>Attribute Name
	<jsp:useBean id="column" class="vhs.ColumnBean" scope="page"/>	
	<select name="column">
	
				<c:forEach var="item" items="${column.items}">
     			<option>${item}</option>
    			</c:forEach>
			</select>
	
	
		</td></tr>
	<tr>
		<td>
			<select name="graph">
				<option selected="selected">Map</option>
				<option>Bar chart</option>
			</select>
		</td> 
	</tr>
	<tr> <td> <input type="submit" name="submit"/>
	</table>
	
</form>
</body>
</html>