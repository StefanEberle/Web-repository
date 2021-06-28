package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import modell.AdresseBean;
import modell.ArtikelBean;
import modell.UserBean;
import modell.WarenkorbArtikelBean;


/**
 * Servlet implementation class WarenkorbServlet
 */
@WebServlet("/WarenkorbAnzeigenServlet")
public class WarenkorbAnzeigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
	
		System.out.println("Hello I am fucking working");
		
		WarenkorbArtikelausDB(request,response, user);
		
		AdresseAusDB(request, response, user);
		
		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/Test.jsp");
		dispatcher.forward(request, response);
		
	}
public void WarenkorbArtikelausDB(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
	ArrayList<ArtikelBean> warenkorbArtikelList = new ArrayList<ArtikelBean>();
	BigDecimal gesamtsumme = BigDecimal.ZERO;
	
	
	String query = "SELECT artikel.ArtikelID, artikel.Marke, artikel.Gebinde, artikel.Fuellmenge, artikel.Gesamtpreis, warenkorbartikel.AnzahlArtikel "
			+ "FROM artikel INNER JOIN warenkorbartikel ON artikel.ArtikelID = warenkorbartikel.FKartikelID "
			+ "INNER JOIN warenkorb ON warenkorb.warenkorbID = warenkorbartikel.FKwarenkorbID WHERE warenkorb.FKuserID = ?";

	try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {
	

		pstm.setInt(1, user.getUserid());
		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			ArtikelBean artikel = new ArtikelBean();

			artikel.setArtikelID(rs.getInt("ArtikelID"));
			artikel.setMarke(rs.getString("Marke"));
			artikel.setGebinde(rs.getString("Gebinde"));
			artikel.setFuellmenge(rs.getBigDecimal("Fuellmenge"));
			artikel.setStueckzahl(rs.getInt("AnzahlArtikel"));
			artikel.setGesamtpreis((rs.getBigDecimal("Gesamtpreis")));
			BigDecimal AnzahlArtikel = BigDecimal.valueOf(rs.getInt("AnzahlArtikel"));
			gesamtsumme= gesamtsumme.add(rs.getBigDecimal("Gesamtpreis").multiply(AnzahlArtikel));
			System.out.println("Bigdecimal Berechnung: "+ AnzahlArtikel + "*"+ rs.getBigDecimal("Gesamtpreis") +" = "+gesamtsumme);
			warenkorbArtikelList.add(artikel);
			System.out.println("ich bin auf der DB");
		}
	}

	catch (Exception ex) {
		throw new ServletException(ex.getMessage());
	}

	request.setAttribute("warenkorbArtikelList", warenkorbArtikelList);
	request.setAttribute("gesamtsumme", gesamtsumme);
	
}

public void AdresseAusDB(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException {
	AdresseBean adresse = new AdresseBean();
	
	String query = "SELECT * from thidb.Adresse where FKUserID = ? ";
			

	try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {
	

		pstm.setInt(1, user.getUserid());
		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			adresse.setVorname(rs.getString("Vorname"));
			adresse.setNachname(rs.getString("Nachname"));
			adresse.setStrasse(rs.getString("Strasse"));
			adresse.setHausnummer(rs.getString("Hausnummer"));
			adresse.setPlz(rs.getInt("PLZ"));
			adresse.setStadt(rs.getString("Stadt"));
			adresse.setEtage(rs.getString("Etage"));
			adresse.setTelefonnummer(rs.getString("Telefonnummer"));
			adresse.setHinweis(rs.getString("Hinweis"));
			
		}
	}

	catch (Exception ex) {
		throw new ServletException(ex.getMessage());
	}
	request.setAttribute("adresse",adresse);
	
	
	
}


}

		
	

	


