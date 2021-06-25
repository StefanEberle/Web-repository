package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import modell.ArtikelBean;
import modell.UserBean;
/**
 * Servlet implementation class WarenkorbAnzeigen
 */
@WebServlet("/WarenkorbAnzeigen")
public class WarenkorbAnzeigen extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		String userid = String.valueOf(user.getUserid());
		
		ArrayList<ArtikelBean> warenkorbArtikel = new ArrayList<ArtikelBean>(); 
		
		BigDecimal gesamtSumme;
		
		
		String query = "SELECT thidb.Artikel.ArtikelID, Marke, Gebinde, Fuellmenge, Gesamtpreis, thidb.WarenkorbArtikel.AnzahlArtikel  FROM thidb.Artikel INNER JOIN thidb.WarenkorbArtikel ON thidb.Artikel.ArtikelID = thidb.WarenkorbArtikel.FKartikelID WHERE thidb.WarenkorbArtikel.FKUserID = ?";
		
		
		try (Connection conn = ds.getConnection(); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, userid);
			try (ResultSet rs = stm.executeQuery()) {

				while (rs.next() && rs != null) {
					ArtikelBean artikel = new ArtikelBean();

					artikel.setArtikelID(rs.getInt("ArtikelID"));
					artikel.setMarke(rs.getString("Marke"));
					artikel.setGebinde(rs.getString("Gebinde"));
					artikel.setFuellmenge(rs.getBigDecimal("Fuellmenge"));
					
					int anzahlArtikel = rs.getInt("AnzahlArtikel");
					BigDecimal preis = rs.getBigDecimal("Gesamtpreis");
					BigDecimal gesamtpreisArtikel = (preis.multiply(BigDecimal.valueOf(anzahlArtikel)));
					gesamtSumme = gesamtSumme.add(gesamtpreisArtikel);
					
					
					artikel.setStueckzahl(rs.getInt("AnzahlArtikel"));
					artikel.setGesamtpreis(gesamtpreisArtikel);
					artikel.setEpJeLiter(rs.getBigDecimal("EPjeLiter"));
					artikel.setPfandProFlasche(rs.getBigDecimal("PfandproFlasche"));
					artikel.setPfandKasten(rs.getBigDecimal("PfandKasten"));
					artikel.setPfandGesamt(rs.getBigDecimal("PfandGesamt"));

					warenkorbArtikel.add(artikel);
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
	
	}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
