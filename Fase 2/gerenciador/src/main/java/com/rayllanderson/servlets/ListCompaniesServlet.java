package com.rayllanderson.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rayllanderson.services.CompanyService;

@WebServlet("/list-companies")
public class ListCompaniesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CompanyService service = new CompanyService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var html = new StringBuilder();
		html.append("<html> <body>");
		html.append("<h3>Empresas cadastradas: </h3>");
		html.append("<table> <tr><th>Company Name</th></tr> ");
		service.findAll().forEach(company -> html.append("<tr> <td>" + company.getName() + " </tr> </td>"));
		html.append("</body> </html>");
		PrintWriter out = response.getWriter();
		out.println(html.toString());
	}
	
}
