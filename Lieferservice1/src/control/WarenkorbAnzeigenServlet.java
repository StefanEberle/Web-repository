/* Autor: Olga Ohlsson */
package control;

import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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
import modell.BankdatenBean;
import modell.UserBean;
import modell.WarenkorbArtikelBean;
import modell.WarenkorbBean;

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

//		BankdatenBean bankdaten = new BankdatenBean();
//		bankdaten = checkUserZahlungsdaten(user.getUserid());
//
//		if (bankdaten != null) {
//			request.setAttribute("bankdaten", bankdaten);
//		}

		warenkorbErstellen(request, response, session);

		WarenkorbArtikelausDB(request, response, user, session);

		AdresseAusDB(request, response, user, session);

		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/WarenkorbAnzeigen.jsp");
		dispatcher.forward(request, response);

	}

	public void WarenkorbArtikelausDB(HttpServletRequest request, HttpServletResponse response, UserBean user,
			HttpSession session) throws ServletException, IOException {
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

				BigDecimal AnzahlArtikel = BigDecimal.valueOf(rs.getInt("AnzahlArtikel"));
				artikel.setGesamtpreis((rs.getBigDecimal("Gesamtpreis")));

				gesamtsumme = gesamtsumme.add(rs.getBigDecimal("Gesamtpreis").multiply(AnzahlArtikel));

				warenkorbArtikelList.add(artikel);

			}
		}

		catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		session.setAttribute("warenkorbArtikelList", warenkorbArtikelList);
		session.setAttribute("gesamtsumme", gesamtsumme);

	}

	public void AdresseAusDB(HttpServletRequest request, HttpServletResponse response, UserBean user,
			HttpSession session) throws ServletException {
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
		session.setAttribute("adresse", adresse);

	}

	public void warenkorbErstellen(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException {

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null) {

			// Dieser Teil entfällt, da man für das Angebot angemeldet sein muss.
			// Ansonten kein Zugriff auf Seite.

			// erneutes Aufrufen der Seite
			// response.sendRedirect("index.html");

		}

		else if (user.isLogin() == true) {

			WarenkorbBean warenkorb = checkWarenkorbUserVorhanden(user.getUserid());

			// wenn es bisher keinen Warenkorb gibt und Cookies in Warenkorb umgewandelt
			// werden müssen
			WarenkorbBean warenkorbKunde = new WarenkorbBean();
			warenkorbKunde.setFkuserID(user.getUserid());
			if (warenkorb == null) {

				// Warenkorb in Datenbank speichern
				String query2 = "INSERT INTO thidb.Warenkorb (FKuserID) values(?)";

				try (Connection conn2 = ds.getConnection();
						PreparedStatement pstm2 = (PreparedStatement) conn2.prepareStatement(query2)) {

					pstm2.setInt(1, warenkorbKunde.getFkuserID());

					pstm2.executeUpdate();

					conn2.close();

				} catch (SQLException e) {
					// message = "Error: " + e.getMessage();
					e.printStackTrace();
				}
			}

			getWarenkorbFromDB(warenkorbKunde);

			WarenkorbArtikelBean[] warenkorbArtikel = cookiesAsWarenkorb(warenkorbKunde, request, response);

			speicherArtikelDB(warenkorbArtikel);
			session.setAttribute("WarenkorbID", warenkorbKunde.getWarenkorbID());

		}

	}

	private WarenkorbBean checkWarenkorbUserVorhanden(int userID) throws ServletException {

		boolean hasResult;
		WarenkorbBean warenkorb = new WarenkorbBean();

		String query = "SELECT * FROM thidb.Warenkorb WHERE FKuserID = ?";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, userID);

			ResultSet rs = pstm.executeQuery(); // sendet das SQL-Query zum Server und Ã¼bergibt Ergebnisse an rs
			hasResult = rs.next(); // geht jetzt durch die DatensÃ¤tze der Tabelle User und Ã¼berprÃ¼ft Mail und
									// Passwort

			if (hasResult) {
				warenkorb.setWarenkorbID(rs.getInt("WarenkorbID"));
			} else {
				warenkorb = null;
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		return warenkorb;

	}

	private WarenkorbArtikelBean[] cookiesAsWarenkorb(WarenkorbBean warenkorbKunde, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		ArrayList<WarenkorbArtikelBean> warenkorbArtikel = new ArrayList<WarenkorbArtikelBean>();

		Cookie cookie[] = request.getCookies();

		// hole ProduktIDs aus Datenbank

		ArrayList<String> produktid = new ArrayList<String>();

		String query = "SELECT ArtikelID FROM thidb.Artikel";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				produktid.add(rs.getString("ArtikelID"));
			}
		}

		catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		String[] produktidArr = new String[produktid.size()];
		produktidArr = produktid.toArray(produktidArr);

		// vergleiche Liste Cookied mit ArrayList ProduktIDs

		// arraylist mit neuen WarenkorbArtikeln erstellen

		for (int i = 0; i < cookie.length; i++) {
			for (int z = 0; z < produktidArr.length; z++) {

				String cookieName = cookie[i].getName();
				String artikelId = produktidArr[z];

				if (artikelId.equals(cookieName)) {

					WarenkorbArtikelBean neuerArtikel = new WarenkorbArtikelBean();
					neuerArtikel.setFkwarenkorbID(warenkorbKunde.getWarenkorbID());
					neuerArtikel.setFkartikelID(Integer.parseInt(cookie[i].getName()));
					neuerArtikel.setAnzahlArtikel(Integer.parseInt(cookie[i].getValue()));

					warenkorbArtikel.add(neuerArtikel);

					// Delete Cookie, damit CookieList leer ist
					cookie[i].setMaxAge(1);
					response.addCookie(cookie[i]);
				}
			}

		}

		WarenkorbArtikelBean[] warenkorbArtikelArr = new WarenkorbArtikelBean[warenkorbArtikel.size()];
		warenkorbArtikelArr = warenkorbArtikel.toArray(warenkorbArtikelArr);
		return warenkorbArtikelArr;
	}

	private void speicherArtikelDB(WarenkorbArtikelBean[] warenkorbArtikel) throws ServletException {
		for (int i = 0; i < warenkorbArtikel.length; i++) {
			WarenkorbArtikelBean neu = new WarenkorbArtikelBean();
			neu.setFkwarenkorbID(warenkorbArtikel[i].getFkwarenkorbID());
			neu.setFkartikelID(warenkorbArtikel[i].getFkartikelID());
			neu.setAnzahlArtikel(warenkorbArtikel[i].getAnzahlArtikel());

			String query4 = "INSERT INTO thidb.Warenkorbartikel (FKwarenkorbID, FKartikelID, AnzahlArtikel) values(?,?,?)";
			try (Connection conn4 = ds.getConnection();
					PreparedStatement pstm4 = (PreparedStatement) conn4.prepareStatement(query4)) {

				pstm4.setInt(1, neu.getFkwarenkorbID());
				pstm4.setInt(2, neu.getFkartikelID());
				pstm4.setInt(3, neu.getAnzahlArtikel());

				pstm4.executeUpdate();
				conn4.close();
			} catch (SQLException ex) {
				// message = "Error: " + e.getMessage();
				throw new ServletException(ex.getMessage());
			}
		}
	}

	private void getWarenkorbFromDB(WarenkorbBean warenkorbKunde) throws ServletException {
		String query3 = "SELECT WarenkorbID FROM thidb.Warenkorb where FKuserID = ? ";
		try (Connection conn3 = ds.getConnection();
				PreparedStatement pstm3 = (PreparedStatement) conn3.prepareStatement(query3)) {

			pstm3.setInt(1, warenkorbKunde.getFkuserID());

			ResultSet rs = pstm3.executeQuery();
			while (rs.next()) {
				warenkorbKunde.setWarenkorbID(rs.getInt("WarenkorbID"));
			}
			conn3.close();

		}

		catch (SQLException ex) {
			throw new ServletException(ex.getMessage());
		}
	}

	private BankdatenBean checkUserZahlungsdaten(int userID) throws ServletException {
		BankdatenBean bankdaten = new BankdatenBean();

		String query = "SELECT * from thidb.Bankdaten where FKuserID= ?";
		try (Connection conn = ds.getConnection();
				PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(query)) {
			pstm.setInt(1, userID);
			ResultSet rs = pstm.executeQuery();
			if (rs.next() && rs != null) {
				bankdaten.setBank(rs.getString("Bank"));
				bankdaten.setIBAN(rs.getString("IBAN"));
				bankdaten.setFKuserID(userID);
				bankdaten.setKontoinhaber(rs.getString("Kontoinhaber"));
			} else {
				bankdaten = null;

			}
			conn.close();

		} catch (SQLException ex) {
			// message = "Error: " + e.getMessage();
			throw new ServletException(ex.getMessage());
		}

		return bankdaten;
	}

}