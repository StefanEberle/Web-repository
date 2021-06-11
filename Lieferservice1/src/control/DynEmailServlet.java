package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import modell.TextBean;
import modell.UserBean;

/**
 * Servlet implementation class DynEmailServlet
 */
@WebServlet("/DynEmailServlet")
public class DynEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
	UserBean user;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		TextBean user = new TextBean();
		
		if(checkEmail(email)) {			
			user.setOriginalText(email + " ist Verfügbar!");
			request.setAttribute("textbean", user);
		}else {
			user.setOriginalText(email + " ist nicht Verfügbar!");
			request.setAttribute("textbean", user);
		}
		
		// JSON - Email verfügbar oder nicht 
		// Ausgabe neben INput Felder
		RequestDispatcher dispatcher = request.getRequestDispatcher("html/json/textBean.jsp");
		dispatcher.forward(request, response);
	}
	

public boolean checkEmail(String email) throws ServletException, IOException {
		
		boolean rueckgabe;
		String query = "SELECT * FROM thidb.User WHERE Email = ?";
		
		//Email Verfügbarkeit testen
		try (Connection conn = ds.getConnection("root","root");
				PreparedStatement stm = conn.prepareStatement(query);) {
			
			stm.setString(1, email);
			
			try (ResultSet rs = stm.executeQuery()) {
				
				if (!rs.isBeforeFirst()) {
					rueckgabe = true;
				} else {
					rueckgabe = false;
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return rueckgabe;
	}
}
