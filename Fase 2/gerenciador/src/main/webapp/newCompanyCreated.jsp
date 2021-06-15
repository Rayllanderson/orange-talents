<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>

	<c:if test="${not empty companyName}">
		 Empresa ${companyName} cadastrada com sucesso!
	 </c:if>
	 
	 <c:if test="${empty companyName}">
		 Nenhuma empresa cadastrada
	 </c:if>
</body> 
</html>