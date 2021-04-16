package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import modell.AdresseBean;
import modell.UserBean;

/**
 * Servlet implementation class KontoBearbeitenServlet
 */
@WebServlet("/KontoBearbeitenServlet")
public class KontoBearbeitenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
	UserBean user;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		if (request.getParameter("emailBearbeiten") != null) {

			String email = request.getParameter("email");
			String passwort = request.getParameter("passwort");

			try {
				if (passwortUeberpruefen(user, passwort) && checkEmail(email)) {

					sicherEmail(user, email);
					session.setAttribute("user", user);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (request.getParameter("pwBearbeiten") != null) {
			String passwort = request.getParameter("passwort");
			String passwortNeu = request.getParameter("passwortNeu");
			String passwortNeu2 = request.getParameter("passwortNeu2");
			try {
				if(passwortUeberpruefen(user, passwort) && passwortNeu.equals(passwortNeu2)) {
					sicherPW(passwortNeu, user.getUserid());
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
		if(request.getParameter("adresseBearbeiten") != null) {
			String vorname = request.getParameter("vorname");
			String nachname = request.getParameter("nachname");
			String strasse = request.getParameter("strasse");
			String hausnummer = request.getParameter("hausnummer");
			int plz = Integer.parseInt(request.getParameter("plz"));
			String stadt = request.getParameter("stadt");
			String etage = request.getParameter("etage");
			String telefonnummer =request.getParameter("telefonnummer");
			String geburtstag = request.getParameter("geburtstag");
			String hinweis = request.getParameter("hinweis");
			
			AdresseBean adresse = new AdresseBean();
			
			adresse.setUserid(user.getUserid());
			adresse.setVorname(vorname);
			adresse.setNachname(nachname);
			adresse.setStrasse(strasse);
			adresse.setHausnummer(hausnummer);
			adresse.setPlz(plz);
			adresse.setStadt(stadt);
			adresse.setEtage(etage);
			adresse.setTelefonnummer(telefonnummer);
			adresse.setGeburtstag(geburtstag);
			adresse.setHinweis(hinweis);
			
			try {
				sicherAdresse(adresse);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			session.setAttribute("adresse", adresse);
		}

		final RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("html/konto.jsp");
		dispatcher.forward(request, response);

	}
	private void sicherAdresse(AdresseBean adresse) throws SQLException {
		
		String query = "UPDATE thidb.Adresse SET Strasse = ?, Hausnummer = ?, PLZ = ?, Stadt = ?, Etage = ?, Telefonnummer = ?, Geburtstag = ?,"
				+ "Hinweis = ?, Vorname = ?, Nachname = ? WHERE FKUserID = ?";
		
		try(Connection conn = ds.getConnection("root", "root"); PreparedStatement pstm = conn.prepareStatement(query);) {
			
			pstm.setString(1, adresse.getStrasse());
			pstm.setString(2, adresse.getHausnummer());
			pstm.setInt(3, adresse.getPlz());
			pstm.setString(4, adresse.getStadt());
			pstm.setString(5, adresse.getEtage());
			pstm.setString(6, adresse.getTelefonnummer());
			pstm.setString(7, adresse.getGeburtstag());
			pstm.setString(8, adresse.getHinweis());
			pstm.setString(9, adresse.getVorname());
			pstm.setString(10,adresse.getNachname());
			
			pstm.setInt(11, adresse.getUserid());
			
			pstm.executeUpdate();
			
			conn.close();
		}catch (SQLException ex) {
			throw new SQLException(ex.getMessage());
		}
	}
	
	
	
	private boolean passwortUeberpruefen(UserBean user, String pw) throws SQLException, ServletException {
		boolean rueckgabe = false;

		String query = "SELECT Passwort FROM User WHERE UserID = ?";

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(query);) {

			pstm.setInt(1, user.getUserid());
			try (ResultSet rs = pstm.executeQuery()) {

				int spalten = rs.getMetaData().getColumnCount(); // https://www.straub.as/java/jdbc/resultset.html

				if (rs.next()) {

					for (int i = 1; i <= spalten; i++) {

						if (rs.getString(i).equals(pw)) {
							rueckgabe = true;
						} else {
							rueckgabe = false;
							System.out.println("Falsches Passwort eingegeben!"); // AUSGABE JSP!!!!!!!!!!!!!!!
						}
					}
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		return rueckgabe;
	}


	private void sicherPW(String pw, int userID) throws SQLException {

		String query = "UPDATE thidb.User SET Passwort = ? WHERE UserID = ?";

		try (Connection conn = ds.getConnection("root","root"); PreparedStatement pstm = conn.prepareStatement(query);) {

			pstm.setString(1, pw);
			pstm.setInt(2, userID);

			pstm.executeUpdate();

			conn.close();
		} catch (SQLException ex) {
			throw new SQLException(ex.getMessage());
		}
	}
	
	private void sicherEmail(UserBean user, String emailNeu) throws SQLException {

		String query = "UPDATE thidb.User SET Email = ? Where UserID = ?";

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement pstm = conn.prepareStatement(query);) {

			pstm.setString(1, emailNeu);
			pstm.setInt(2, user.getUserid());

			pstm.executeUpdate();

			conn.close();
		} catch (SQLException ex) {
			throw new SQLException(ex.getMessage());
		}
	}

	public boolean checkEmail(String email) throws ServletException, IOException {

		boolean rueckgabe;
		String query = "SELECT * FROM thidb.User WHERE Email = ?";

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement stm = conn.prepareStatement(query);) {

			stm.setString(1, email);

			try (ResultSet rs = stm.executeQuery()) {

				if (!rs.isBeforeFirst()) {
					rueckgabe = true;
				} else {
					rueckgabe = false;
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return rueckgabe;
	}
}
