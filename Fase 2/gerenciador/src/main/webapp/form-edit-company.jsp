<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/edit-company" var="servletUrl"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrar Empresa</title>
</head>
<body>


	<form action="${servletUrl}" method="post">
		<label for="companyName">Nome da empresa</label>
		<input type="hidden" name="company-id" value="${company.id}" id="companyId"/>
		<input type="text" name="company-name" value="${company.name}" id="companyName"/>
		<button>Editar</button>
	</form>

</body>
</html>