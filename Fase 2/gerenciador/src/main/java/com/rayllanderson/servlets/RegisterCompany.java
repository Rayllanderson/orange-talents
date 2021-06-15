package com.rayllanderson.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rayllanderson.entities.Company;
import com.rayllanderson.services.CompanyService;

@WebServlet("/register-company")
public class RegisterCompany extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private CompanyService service = new CompanyService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String companyName = request.getParameter("company-name");
		Company companyToBeRegistered = new Company(null, companyName);
		service.register(companyToBeRegistered);
		
		//chamar o jsp;
		request.setAttribute("companyName", companyToBeRegistered.getName());
		request.getRequestDispatcher("/list-companies").forward(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html> <body>");
		out.println(" Método não permitido :x");
		out.println("</body> </html>");
	}
	
}
