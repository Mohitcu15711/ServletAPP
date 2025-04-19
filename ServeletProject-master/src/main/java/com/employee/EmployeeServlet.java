package com.employee;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/searchEmployee")
public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			String idParam = req.getParameter("id");
			int id = Integer.parseInt(idParam);

			Employee emp = EmployeeDAO.fetchEmployeeById(id);

			if (emp != null) {
				req.setAttribute("employee", emp);
			} else {
				req.setAttribute("error", "Employee not found");
			}

			RequestDispatcher dispatcher = req.getRequestDispatcher("search.jsp");
			dispatcher.forward(req, res);

		} catch (NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid employee ID");
		} catch (Exception e) {
			e.printStackTrace();
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error occurred");
		}
	}
}
