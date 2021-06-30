package control;

import java.io.IOException;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import modell.ArtikelBean;
import modell.UnterKategorieBean;

/**
 * Servlet implementation class AuswahlArtikelServlet
 */
@WebServlet("/AuswahlArtikelServlet")
public class AuswahlArtikelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Filter artikelAuswahl.jsp
		// Abhängig von Parameter Query erstellen

		request.setCharacterEncoding("UTF-8");

		String gebinde = "";
		String unterKate = "";
		String query = "";

		String kategorie = request.getParameter("kategorie");
		String unterKategorie = request.getParameter("unterKategorie");
		String pet = request.getParameter("pet");
		String glas = request.getParameter("glas");
		String marken = request.getParameter("marken");

		// alle Werte null
		if (kategorie.equals("noValue") && unterKategorie.equals("nonValue") && pet == null && glas == null
				&& marken == null) {
			response.setContentType("text/html");

			final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//Wenn über die Suche Artikel aufgerufen wurden
		if (kategorie.equals("noValue")) {
			query += "SELECT * FROM thidb.Artikel WHERE ";
		}
		//Wenn eine Kategorie ausgewählt wurde (z.B. Wasser, Bier etc.)
		if (unterKategorie.equals("nonValue") && !kategorie.equals("noValue")) {
			query += "SELECT * FROM thidb.Artikel WHERE FKKategorieID = ? ";
		}

		//Wenn eine Unterkategorie aufgerufen wurde (z.B. Spritzig, Helles etc.)
		if (!unterKategorie.equals("nonValue") && !kategorie.equals("noValue")) {
			unterKate += "FKUnterkategorieID = " + unterKategorie;
			query += "SELECT * FROM thidb.Artikel WHERE FKKategorieID = ? AND " + unterKate + " ";
		}
		
		//Wenn über die Suche - dann ist kategorie immer noValue
		if (marken != null && kategorie.equals("noValue")) {
			String replace = marken.replace("_", " "); 
			query += " Marke = " + "'" + replace + "'";
		}
		
		// Über Dropdown Menü und Marke ausgewählt
		if (marken != null && !kategorie.equals("noValue")) {
			String replace = marken.replace("_", " ");
			query += " AND Marke = " + "'" + replace + "' ";
		}
		
		//Weder glas noch pet ausgewählt
		if (glas != null && pet != null) {
			gebinde = "";
		}
		//glas ausgewählt
		if (pet != null && glas == null) {

			gebinde = " AND Gebinde = " + "'" + "PET" + "'";
		}
		//pet ausgewählt
		if (glas != null && pet == null) {
			gebinde = " AND Gebinde = " + "'" + "Glas" + "'";
		}
		
		//query zusammenfügen
		query += " " + gebinde;
		
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pstm = conn.prepareStatement(query)) {
			
			if (!kategorie.equals("noValue")) {
				pstm.setString(1, kategorie);
			}

			try (ResultSet rs = pstm.executeQuery()) {

				while (rs.next()) {
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

		request.setAttribute("artikelList", artikelList);

			// Falls Anfrage über die Suche kommt - Ajax filter.js fehlt id
			// Filter wird dann über die unterKategorienList, markenList erzeugt
			if (kategorie.equals("noValue")) {

				artikelList = getMarke(marken);
				List<UnterKategorieBean> unterKategorienList = new ArrayList<UnterKategorieBean>();
				List<ArtikelBean> markenList = new ArrayList<ArtikelBean>();
				try {

					unterKategorienList = uKListeMarkenSuche(marken);
					unterKategorienList = getUK_Marken(unterKategorienList);
					markenList = getFilterMarken(artikelList);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("unterKategorienList", unterKategorienList);
				request.setAttribute("markenList", markenList);
			}
		
		response.setContentType("text/html");

		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
		dispatcher.forward(request, response);


	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Artikel holen über navBar oder Suche 

		String kategorie = request.getParameter("kategorie");
		String unterKategorie = request.getParameter("unterKategorie");
		String marke = request.getParameter("marke");

		response.setContentType("text/html");

		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();

		if (request.getParameter("deleteArtikelButton") != null) {
			artikelList = getAlleArtikelUnterKategorie(unterKategorie);

			request.setAttribute("artikelList", artikelList);

			final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/deleteArtikel.jsp");
			dispatcher.forward(request, response);
			return;
		}

		if (kategorie != null && unterKategorie == null) {
			artikelList = getAlleArtikelKategorie(kategorie);

		}
		if (unterKategorie != null && kategorie != null) {
			artikelList = getAlleArtikelUnterKategorie(unterKategorie);

		}
		if (marke != null) {
			artikelList = getMarke(marke);
			List<UnterKategorieBean> unterKategorienList = new ArrayList<UnterKategorieBean>();
			List<ArtikelBean> markenList = new ArrayList<ArtikelBean>();
			try {

				unterKategorienList = uKListeMarkenSuche(marke);
				unterKategorienList = getUK_Marken(unterKategorienList);
				markenList = getFilterMarken(artikelList);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("unterKategorienList", unterKategorienList);
			request.setAttribute("markenList", markenList);
		}

		request.setAttribute("artikelList", artikelList);

		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
		dispatcher.forward(request, response);

	}

	private List<ArtikelBean> getMarke(String marke) throws ServletException {

		String replace = marke.replace("_", " ");

		String query = "SELECT * FROM thidb.Artikel WHERE Marke = ?";
		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();

		try (Connection conn = ds.getConnection(); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, replace);
			try (ResultSet rs = stm.executeQuery()) {

				while (rs.next()) {
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

		try (Connection conn = ds.getConnection(); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, unterKategorie);
			try (ResultSet rs = stm.executeQuery()) {

				while (rs.next() && rs != null) {
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

		try (Connection conn = ds.getConnection(); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, kategorie);
			try (ResultSet rs = stm.executeQuery()) {

				while (rs.next()) {
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
					artikel.setFkunterkategorieID(rs.getInt("FKUnterkategorieID"));
					artikelList.add(artikel);
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return artikelList;
	}

	private List<UnterKategorieBean> uKListeMarkenSuche(String markenBez) throws SQLException, ServletException {

		markenBez = markenBez.replace("_", " ");

		List<ArtikelBean> artikelList = new ArrayList<ArtikelBean>();
		List<UnterKategorieBean> unterKategorien = new ArrayList<UnterKategorieBean>();

		String query = "SELECT Marke, FKKategorieID, FKUnterkategorieID FROM thidb.Artikel WHERE Marke = ?";

		try (Connection conn = ds.getConnection();
				PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setString(1, markenBez);
			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next() && rs != null) {
					ArtikelBean artikel = new ArtikelBean();

					artikel.setMarke(rs.getString("Marke"));
					artikel.setFkkategorieID(rs.getInt("FKKategorieID"));
					artikel.setFkunterkategorieID(rs.getInt("FKUnterkategorieID"));

					artikelList.add(artikel);
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		String ukQuery = "SELECT * FROM thidb.UnterKategorie WHERE UnterkategorieID = ?";

		if (artikelList.size() > 0) {

			for (int i = 0; i < artikelList.size(); i++) {

				try (Connection conn = ds.getConnection();
						PreparedStatement pstm = conn.prepareStatement(ukQuery)) {

					ArtikelBean artikel = new ArtikelBean();
					artikel = artikelList.get(i);

					pstm.setInt(1, artikel.getFkunterkategorieID());

					try (ResultSet rs = pstm.executeQuery()) {
						while (rs.next() && rs != null) {
							UnterKategorieBean uK = new UnterKategorieBean();

							uK.setUnterkategorieID(rs.getInt("UnterkategorieID"));
							uK.setUnterkategorieBez(rs.getString("UnterkategorieBez"));
							uK.setFkKategorieID(rs.getInt("FKKategorieID"));

							unterKategorien.add(uK);

						}
					}
					conn.close();
				} catch (Exception ex) {
					throw new ServletException(ex.getMessage());
				}

			}
		}
		return unterKategorien;
	}

	private List<UnterKategorieBean> getUK_Marken(List<UnterKategorieBean> filterUnterKatList) {

		List<UnterKategorieBean> ukList = new ArrayList<UnterKategorieBean>();

		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < filterUnterKatList.size(); i++) {
			UnterKategorieBean artikel = filterUnterKatList.get(i);

			String s = artikel.getUnterkategorieBez();

			if (!arr.contains(s)) {
				arr.add(s);
				ukList.add(artikel);
			}

		}

		return ukList;
	}

	private List<ArtikelBean> getFilterMarken(List<ArtikelBean> filterMarkenList) {

		List<ArtikelBean> markenList = new ArrayList<ArtikelBean>();

		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < filterMarkenList.size(); i++) {
			ArtikelBean artikel = filterMarkenList.get(i);

			String s = artikel.getMarke();

			if (!arr.contains(s)) {
				arr.add(s);
				markenList.add(artikel);
			}

		}

		return markenList;
	}

}
