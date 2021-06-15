<%
	String companyName = (String) request.getAttribute("companyName");
	System.out.print(companyName);
%>
<html>
<body>
	 Empresa <%= companyName%> cadastrada com sucesso!
</body> 
</html>