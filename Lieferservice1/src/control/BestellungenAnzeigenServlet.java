package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import modell.ArtikelBean;
import modell.BestellungBean;
import modell.RechnungBean;
import modell.UserBean;
import modell.WarenkorbArtikelBean;

/**
 * Servlet implementation class BestellungenAnzeigenServlet
 */
@WebServlet("/BestellungenAnzeigenServlet")
public class BestellungenAnzeigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;  
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		BestellungBean bestellung = new BestellungBean(); 
		RechnungBean rechnung = new RechnungBean(); 
		WarenkorbArtikelBean warenkorbArtikel = new WarenkorbArtikelBean();
		
		ArrayList<WarenkorbArtikelBean> warenkorbArtikelList = new ArrayList<WarenkorbArtikelBean>();
		ArrayList<BestellungBean> bestellungList = new ArrayList<BestellungBean>();
		ArrayList<RechnungBean> rechnungList = new ArrayList<RechnungBean>();
		
		
		String query = "SELECT Bestellung.BestellungID, Bestellung.Status, Rechnung.summe, Rechnung.Status, Artikel.Marke, WarenkorbArtikel.FKartikelID, WarenkorbArtikel.AnzahlArtikel"
				+ " FROM Rechnung INNER JOIN Bestellung ON Rechnung.FKbestellungID = Bestellung.BestellungID"
				+ " INNER JOIN WarenkorbArtikel ON Bestellung.BestellungID = WarenkorbArtikel.FKBestellungID"
				+ " INNER JOIN Artikel on WarenkorbArtikel.FKartikelID = Artikel.ArtikelID WHERE Bestellung.FKuserID=?";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, user.getUserid());

			ResultSet rs = pstm.executeQuery(); 
						
			while (rs.next()) {
				bestellung.setBestellungID(rs.getInt("BestellungID"));
				bestellung.setFKuserID(user.getUserid());
				bestellung.setStatus(rs.getString("Bestellung.Status"));
				rechnung.setFKbestellungID(bestellung.getBestellungID());
				rechnung.setSumme(rs.getBigDecimal("summe"));
				rechnung.setRechnungsStatus(rs.getString("RechnungsStatus"));
				warenkorbArtikel.setAnzahlArtikel(rs.getInt("AnzahlArtikel"));
				warenkorbArtikel.setFkartikelID(rs.getInt("FKartikelID"));
				warenkorbArtikel.setFkbestellungID(rs.getInt("BestellungID"));
				bestellungList.add(bestellung);
				rechnungList.add(rechnung);
				warenkorbArtikelList.add(warenkorbArtikel);
				
			}
		}
		catch(Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
		session.setAttribute("bestellungList", bestellungList);
		session.setAttribute("rechnungList", rechnungList);
		session.setAttribute("BestellungArtikelList", warenkorbArtikelList);
		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/BestellungenAnzeigen.jsp");
		dispatcher.forward(request, response);
		
	}

}
