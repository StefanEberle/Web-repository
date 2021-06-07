package controller;

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
	
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
	
		
		request.setAttribute("artikelList", artikelList);
		
		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
		dispatcher.forward(request, response);
		
		
	}


	private List<ArtikelBean> getMarke(String marke) throws ServletException{
		
		String query = "SELECT * FROM thidb.Artikel WHERE Marke = ?";
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();
		
		try (Connection conn = ds.getConnection("root","root"); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, marke);
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
	private List<ArtikelBean> getAlleArtikelUnterKategorie(String unterKategorie) throws ServletException {
		
		String query = "SELECT * FROM thidb.Artikel WHERE FKUnterkategorieID = ?";
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();
		
		try (Connection conn = ds.getConnection("root","root"); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, unterKategorie);
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
