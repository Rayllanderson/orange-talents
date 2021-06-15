<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%-- 	<c:if test="${not empty companyName}"> --%>
<%-- 		 Empresa ${companyName} cadastrada com sucesso! --%>
<%-- 	 </c:if> --%>
	 
<%-- 	 <c:if test="${empty companyName}"> --%>
<!-- 		 Nenhuma empresa cadastrada -->
<%-- 	 </c:if> --%>
	 
	 <br/>
	 
	 <a href="/gerenciador/form-register-company.jsp">Cadastrar Nova empresa</a>

	<h3>Empresas cadastradas: </h3>
	<table> 
		<tr>
			<th>Company Name</th>
		</tr>
		<c:forEach var="company" items="${companies}">
			
			 <tr>
			 	<td>${company.name}</td>
			 	<td><a href="/gerenciador/remove-company?id=${company.id}">Remover</a></td>
			 </tr>
		</c:forEach>
	</table>

</body>
</html>