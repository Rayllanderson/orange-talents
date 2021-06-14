package com.rayllanderson.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/hello")
public class HelloWorldServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>");
		out.println("Voce sabia?! Isto foi escrito via servlet!");
		out.println("</h1>");
		out.println("</body>");
		out.println("</html>");
		
		System.out.println("Entrou na servlet HelloWorldServlet!");
	}
}
