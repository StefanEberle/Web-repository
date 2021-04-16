package controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import modell.ArtikelBean;
import modell.ArtikelBildBean;

/**
 * Servlet implementation class ArtikelServlet
 */
@WebServlet("/ArtikelServlet")
@MultipartConfig(maxFileSize = 16177215)

public class ArtikelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArtikelBean artikel = new ArtikelBean();
//		ArtikelBildBean artikelBild = new ArtikelBildBean();
		
		final String marke = request.getParameter("marke");
		final int kategorie = Integer.parseInt(request.getParameter("kategorie"));
		final int unterKategorie = Integer.parseInt(request.getParameter("unterKategorie"));
		final String gebinde = request.getParameter("gebinde");
		final BigDecimal fuellmenge = new BigDecimal(request.getParameter("fuelmenge"));
		final int stueckzahl = Integer.parseInt(request.getParameter("stueckzahl"));
		final BigDecimal gesamtpreis = new BigDecimal(request.getParameter("gesamtpreis"));
		final BigDecimal pfandProFlasche = new BigDecimal(request.getParameter("pfandProFlasche"));
		final BigDecimal pfandKasten = new BigDecimal(request.getParameter("pfandKasten"));
		
		BigDecimal epJeLiter = fuellmenge.multiply(new BigDecimal(stueckzahl));
		epJeLiter = gesamtpreis.divide(epJeLiter, 2, RoundingMode.HALF_EVEN);
		
		BigDecimal pfandGesamt = pfandProFlasche.multiply(new BigDecimal(stueckzahl)).add(pfandKasten);
		
		final Part image = request.getPart("artikelBild");
	
		
		artikel.setMarke(marke);
		artikel.setFkkategorieID(kategorie);
		artikel.setFkunterkategorieID(unterKategorie);
		artikel.setGebinde(gebinde);
		artikel.setFuellmenge(fuellmenge);
		artikel.setStueckzahl(stueckzahl);
		artikel.setGesamtpreis(gesamtpreis);
		artikel.setPfandProFlasche(pfandProFlasche);
		artikel.setPfandKasten(pfandKasten);
		artikel.setEpJeLiter(epJeLiter);
		artikel.setPfandGesamt(pfandGesamt);

	
		
		artikel = sicherArtikel(artikel);
		
		//Quelle Bild Upload: https://www.codejava.net/coding/upload-files-to-database-servlet-jsp-mysql
		
		InputStream inputStream = null;
//		String message = null;
		
		if (image != null) {
			
			inputStream = image.getInputStream();
		}
		String query = "INSERT INTO thidb.ArtikelBild (FKartikelID, ArtikelBild) values(?,?)";
		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement stm = (PreparedStatement) conn.prepareStatement(query)) {
			
			if(inputStream != null) {
				
				stm.setInt(1, artikel.getArtikelID());
				stm.setBlob(2, inputStream);
			}
			stm.executeUpdate();
//			int row = stm.executeUpdate();
//			if(row > 0) {
//				message = "Gespeichert in DB";
//			}
			conn.close();
//			inputStream.close();
		}

		catch (SQLException e) {
			//message = "Error: " + e.getMessage();
			e.printStackTrace();
		}
		//request.setAttribute("Message", message);
		
		final RequestDispatcher dispatcher = request.getRequestDispatcher("html/artikelErzeugen.jsp");
		dispatcher.forward(request, response);
		
	
	}

	private ArtikelBean sicherArtikel(ArtikelBean artikel) {

		String query = "INSERT INTO thidb.Artikel (Marke, FKKategorieID, FKUnterkategorieID, Gebinde, "
				+ "Fuellmenge, Stueckzahl, Gesamtpreis, EPjeLiter, PfandproFlasche, PfandKasten, PfandGesamt)  values(?,?,?,?,?,?,?,?,?,?,?)";

		String[] generatedKeys = new String[] { "ArtikelID" }; // Name der Spalte(n), die automatisch generiert wird
																// (werden)

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement stm = (PreparedStatement) conn.prepareStatement(query, generatedKeys)) {

			stm.setString(1, artikel.getMarke());
			stm.setInt(2, artikel.getFkkategorieID());
			stm.setInt(3, artikel.getFkunterkategorieID());
			stm.setString(4, artikel.getGebinde());
			stm.setBigDecimal(5, artikel.getFuellmenge());
			stm.setInt(6, artikel.getStueckzahl());
			stm.setBigDecimal(7, artikel.getGesamtpreis());
			stm.setBigDecimal(8, artikel.getEpJeLiter());
			stm.setBigDecimal(9, artikel.getPfandProFlasche());
			stm.setBigDecimal(10, artikel.getPfandKasten());
			stm.setBigDecimal(11, artikel.getPfandGesamt());

			stm.executeUpdate();

			// Generierte(n) Schlüssel auslesen
			ResultSet rsKeys = stm.getGeneratedKeys();
			while (rsKeys.next()) {
				artikel.setArtikelID(rsKeys.getInt(1));
			}

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artikel;
	}
	
}
