package com.rayllanderson.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rayllanderson.services.CompanyService;

@WebServlet(urlPatterns = { "/remove-company" })
public class RemoveCompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CompanyService service = new CompanyService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt((String) request.getParameter("id"));
		service.deleteById(id);
		response.sendRedirect("list-companies"); 
	}
}
