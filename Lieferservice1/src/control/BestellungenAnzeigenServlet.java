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
import modell.SuchergebnisBean;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		
		System.out.println("Ich bin in BestellungenAnzeigenSErvlet");

		ArrayList<BestellungBean> bestellungList = bestellungenHolen(user);
		
		ArrayList<RechnungBean> rechnungList = rechnungenHolen(user, bestellungList);

		System.out.println("Done with BestellungenAnzeigenSErvlet");
		
		request.setAttribute("bestellungList", bestellungList);
		request.setAttribute("rechnungList", rechnungList);

		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/BestellungenAnzeigen.jsp");
		dispatcher.forward(request, response);

	}
	
	public ArrayList<BestellungBean> bestellungenHolen(UserBean user) throws ServletException{
		ArrayList<BestellungBean> bestellungList = new ArrayList<BestellungBean>();
		
		String query = "SELECT BestellungID, Status "
				+ " FROM Bestellung where FKuserID=? ";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, user.getUserid());
			

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
			
				BestellungBean bestellung = new BestellungBean();

				bestellung.setBestellungID(rs.getInt("BestellungID"));
				bestellung.setStatus(rs.getString("Status"));
		
				bestellungList.add(bestellung);

			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
		System.out.println("Bestellungen aus DB geholt, size="+bestellungList.size());
		
		return bestellungList;
	}
	
	public ArrayList<RechnungBean> rechnungenHolen(UserBean user, ArrayList<BestellungBean> bestellungList) throws ServletException {
		ArrayList<RechnungBean> rechnungList = new ArrayList<RechnungBean>();

	 for (int i=0; i<bestellungList.size(); i++) {
	 
		String query = "SELECT summe, Status "
				+ " FROM Rechnung where FKuserID=? AND FKbestellungID=?";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, user.getUserid());
			pstm.setInt(2, bestellungList.get(i).getBestellungID());

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
			
				RechnungBean rechnung = new RechnungBean();

				rechnung.setSumme(rs.getBigDecimal("summe"));
				rechnung.setRechnungsstatus(rs.getString("Status"));

		
				rechnungList.add(rechnung);

			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		

	}
	 System.out.println("Rechnungen aus DB geholt");
	 return rechnungList;
}
	
}


