package com.rayllanderson.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rayllanderson.entities.Company;
import com.rayllanderson.services.CompanyService;

@WebServlet("/edit-company")
public class EditCompanyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private CompanyService service = new CompanyService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt((String) request.getParameter("id"));
		try {
			Company companyToBeEdited = service.findById(id);
			request.setAttribute("company", companyToBeEdited);
			request.getRequestDispatcher("/form-edit-company.jsp").forward(request, response);
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String companyName = request.getParameter("company-name");
		Integer id = Integer.parseInt((String) request.getParameter("company-id"));
		Company companyToBeEdited = new Company(id, companyName);
		service.edit(companyToBeEdited);
		response.sendRedirect("list-companies"); 
	}
	
}
