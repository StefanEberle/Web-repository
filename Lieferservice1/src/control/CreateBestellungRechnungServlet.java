package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import modell.BestellungBean;
import modell.RechnungBean;
import modell.UserBean;
import modell.WarenkorbArtikelBean;
import modell.WarenkorbBean;


 //wandelt Warenkorb in Bestellung um
//konstruktor in BestellungBean setzt Status automatisch auf "aufgegeben"

/**
 * Servlet implementation class createBestellungServlet
 */
@WebServlet("/createBestellungServlet")
public class CreateBestellungRechnungServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		Integer dieserWarenkorbID = (Integer) session.getAttribute("WarenkorbID");
		UserBean user = (UserBean) session.getAttribute("user");
		BigDecimal summe = (BigDecimal) session.getAttribute("gesamtsumme");
		
		BestellungBean bestellung = new BestellungBean(dieserWarenkorbID);
		
		RechnungBean rechnung = new RechnungBean(bestellung.getBestellungID(), user.getUserid());
		rechnung.setSumme(summe);
		
		rechnungAnzeigen(request, response, rechnung);
		
		warenkorbAsBestellung(dieserWarenkorbID,bestellung);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.sendRedirect("html/RechnungAnzeigen.jsp");
		
	}
	
	public void rechnungAnzeigen(HttpServletRequest request, HttpServletResponse response, RechnungBean rechnung) {
		
		
	}
	
	public void warenkorbAsBestellung(int dieserWarenkorbID, BestellungBean bestellung) throws ServletException {
		
		String query = "UPDATE warenkorbartikel " + 
				"SET FKwarenkorbID = null, FKbestellungID = ? " + 
				"WHERE FKwarenkorbID = ?";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {
			
			pstm.setInt(1, bestellung.getBestellungID());
			pstm.setInt(2, dieserWarenkorbID);
			pstm.executeUpdate(); 
			conn.close();
//DELETE WARENKORB
			
		}
		catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

	}


}
