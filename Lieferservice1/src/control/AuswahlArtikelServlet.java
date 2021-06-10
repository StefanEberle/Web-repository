package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Servlet implementation class AuswahlArtikelServlet
 */
@WebServlet("/AuswahlArtikelServlet")
public class AuswahlArtikelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
	
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Filter artikelAuswahl.jsp 
		// Abhängig von Parameter Query erstellen
	
		String gebinde = "";
		String uKate = "";
		String query = "";
		
		String kategorie = request.getParameter("kategorie");
		String unterKategorie = request.getParameter("unterKategorie"); 
		String pet = request.getParameter("pet");
		String glas = request.getParameter("glas");
		
		
		if(unterKategorie.length() < 1) {
			//uKate += " FKUnterkategorieID IS NOT NULL ";
			query += "SELECT * FROM thidb.Artikel WHERE FKKategorieID = ? ";
		}
		else if(unterKategorie.length() >= 1) {
			uKate += "FKUnterkategorieID = " + unterKategorie;
			query += "SELECT * FROM thidb.Artikel WHERE FKKategorieID = ? AND " + uKate;
		}
		

		if(glas != null && pet != null) {
			 gebinde = "";
		}
		if(pet != null && glas == null) {
			 gebinde = " AND Gebinde = " + "'" + "PET" + "'";
		}
		if(glas != null && pet == null) {
			 gebinde = " AND Gebinde = " + "'" + "Glas" + "'";
		}
		
		query += " " + gebinde;
	
		
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();
		
		try (Connection conn = ds.getConnection("root","root"); PreparedStatement stm = conn.prepareStatement(query)) {
			
			stm.setString(1, kategorie);
			
			
			try (ResultSet rs = stm.executeQuery()) {

				while(rs.next()) {
					ArtikelBean artikel = new ArtikelBean();
					
					artikel.setArtikelID(rs.getInt("ArtikelID"));
					artikel.setMarke(rs.getString("Marke"));
					artikel.setGebinde(rs.getString("Gebinde"));
					artikel.setFuellmenge(rs.getBigDecimal("Fuellmenge"));
					artikel.setStueckzahl(rs.getInt("Stueckzahl"));
					artikel.setGesamtpreis(rs.getBigDecimal("Gesamtpreis"));
					artikel.setEpJeLiter(rs.getBigDecimal("EPjeLiter"));
					artikel.setPfandProFlasche(rs.getBigDecimal("PfandproFlasche"));
					artikel.setPfandKasten(rs.getBigDecimal("PfandKasten"));
					artikel.setPfandGesamt(rs.getBigDecimal("PfandGesamt"));
					
					artikelList.add(artikel);
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
		
		
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(10);
		session.setAttribute("artikelList", artikelList);
		
		response.setContentType("text/html");
		
		//session Zugriff für JSP-EL
		//URL für AJAX (filter.js)
		if(uKate.length() <= 1) {
			response.sendRedirect("html/auswahlArtikel.jsp?kategorie=" + kategorie);
		}else {
			response.sendRedirect("html/auswahlArtikel.jsp?unterKategorie="+unterKategorie + "&kategorie=" + kategorie);
		}
		
		
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Artikel holen über header oder Suche (marke)
		
		String kategorie = request.getParameter("kategorie");
		String unterKategorie = request.getParameter("unterKategorie"); 
		String marke = request.getParameter("marke");

		
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();
		
		
		if(kategorie != null) {
			artikelList = getAlleArtikelKategorie(kategorie);
			
		}
		if(unterKategorie != null) {
			artikelList = getAlleArtikelUnterKategorie(unterKategorie);
			
		}
		if(marke != null) {	
			artikelList = getMarke(marke);
	
		}
	
		response.setContentType("text/html");
		
		
		if(artikelList.size() < 1) {
			
			final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		//wenn dropdown Menü verwendet wird - wert kleiner 1
		if(artikelList.size() > 0 && artikelList.get(0).getFkkategorieID() < 1) {
			
			request.setAttribute("artikelList", artikelList);
			
			final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
			dispatcher.forward(request, response);
		}
		
		//filter.js benötigt kategorie in URL
		//wenn Suche verwendet wird - wert größter 0
		if(artikelList.size() > 0 && artikelList.get(0).getFkkategorieID() > 0) {
			
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(10); // 10 Sekunden
			session.setAttribute("artikelList", artikelList);
			response.sendRedirect("html/auswahlArtikel.jsp?kategorie=" + artikelList.get(0).getFkkategorieID());
		}
		
		
	}


	private List<ArtikelBean> getMarke(String marke) throws ServletException{
		
		String replace = marke.replace("_", " ");
		
		
		String query = "SELECT * FROM thidb.Artikel WHERE Marke = ?";
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();
		
		try (Connection conn = ds.getConnection("root","root"); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, replace);
			try (ResultSet rs = stm.executeQuery()) {

				while(rs.next()) {
					ArtikelBean artikel = new ArtikelBean();
					
					artikel.setArtikelID(rs.getInt("ArtikelID"));
					artikel.setMarke(rs.getString("Marke"));
					artikel.setGebinde(rs.getString("Gebinde"));
					artikel.setFuellmenge(rs.getBigDecimal("Fuellmenge"));
					artikel.setStueckzahl(rs.getInt("Stueckzahl"));
					artikel.setGesamtpreis(rs.getBigDecimal("Gesamtpreis"));
					artikel.setEpJeLiter(rs.getBigDecimal("EPjeLiter"));
					artikel.setPfandProFlasche(rs.getBigDecimal("PfandproFlasche"));
					artikel.setPfandKasten(rs.getBigDecimal("PfandKasten"));
					artikel.setPfandGesamt(rs.getBigDecimal("PfandGesamt"));
					artikel.setFkkategorieID(rs.getInt("FKKategorieID"));
					
					artikelList.add(artikel);
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return artikelList;
		
	}
	private List<ArtikelBean> getAlleArtikelUnterKategorie(String unterKategorie) throws ServletException {
		
		String query = "SELECT * FROM thidb.Artikel WHERE FKUnterkategorieID = ?";
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();
		
		try (Connection conn = ds.getConnection("root","root"); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, unterKategorie);
			try (ResultSet rs = stm.executeQuery()) {
				
				while(rs.next() && rs != null) {
					ArtikelBean artikel = new ArtikelBean();
					
					artikel.setArtikelID(rs.getInt("ArtikelID"));
					artikel.setMarke(rs.getString("Marke"));
					artikel.setGebinde(rs.getString("Gebinde"));
					artikel.setFuellmenge(rs.getBigDecimal("Fuellmenge"));
					artikel.setStueckzahl(rs.getInt("Stueckzahl"));
					artikel.setGesamtpreis(rs.getBigDecimal("Gesamtpreis"));
					artikel.setEpJeLiter(rs.getBigDecimal("EPjeLiter"));
					artikel.setPfandProFlasche(rs.getBigDecimal("PfandproFlasche"));
					artikel.setPfandKasten(rs.getBigDecimal("PfandKasten"));
					artikel.setPfandGesamt(rs.getBigDecimal("PfandGesamt"));
					
					artikelList.add(artikel);
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return artikelList;
	}
	
	private List<ArtikelBean> getAlleArtikelKategorie(String kategorie) throws ServletException {
		
		String query = "SELECT * FROM thidb.Artikel WHERE FKKategorieID = ?";
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();
		
		try (Connection conn = ds.getConnection("root","root"); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, kategorie);
			try (ResultSet rs = stm.executeQuery()) {

				while(rs.next()) {
					ArtikelBean artikel = new ArtikelBean();
					
					artikel.setArtikelID(rs.getInt("ArtikelID"));
					artikel.setMarke(rs.getString("Marke"));
					artikel.setGebinde(rs.getString("Gebinde"));
					artikel.setFuellmenge(rs.getBigDecimal("Fuellmenge"));
					artikel.setStueckzahl(rs.getInt("Stueckzahl"));
					artikel.setGesamtpreis(rs.getBigDecimal("Gesamtpreis"));
					artikel.setEpJeLiter(rs.getBigDecimal("EPjeLiter"));
					artikel.setPfandProFlasche(rs.getBigDecimal("PfandproFlasche"));
					artikel.setPfandKasten(rs.getBigDecimal("PfandKasten"));
					artikel.setPfandGesamt(rs.getBigDecimal("PfandGesamt"));
					
					artikelList.add(artikel);
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return artikelList;
	}

}
