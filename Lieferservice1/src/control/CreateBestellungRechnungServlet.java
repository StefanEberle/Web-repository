package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


 //wandelt Warenkorb in Bestellung um
//konstruktor in BestellungBean setzt Status automatisch auf "aufgegeben"

/**
 * Servlet implementation class createBestellungServlet
 */
@WebServlet("/createBestellungServlet")
public class CreateBestellungRechnungServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		   
		
		
		
	}


}
