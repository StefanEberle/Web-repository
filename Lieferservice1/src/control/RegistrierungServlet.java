package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import modell.AdresseBean;
import modell.UserBean;

@WebServlet("/RegistrierungServlet")
public class RegistrierungServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
	UserBean user;
	AdresseBean adresse;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		
		final String email = request.getParameter("email");
		

		

		final String jahr = request.getParameter("jahr");
		final String monat = request.getParameter("monat");
		final String tag = request.getParameter("tag");

		String geb = tag + "." + monat + "." + jahr;

		if (isEmail(email)) {
			final String passwort = request.getParameter("passwort");
			final String passwort2 = request.getParameter("passwort2");
			
			if (!passwort.equals(passwort2)) {
				response.sendRedirect("html/registrierung.jsp");
			} else {
				
				final String vorname = request.getParameter("vorname");
				final String nachname = request.getParameter("nachname");
				final String strasse = request.getParameter("strasse");
				final String hausNr = request.getParameter("hausnummer");
				final int plz = Integer.parseInt(request.getParameter("plz"));
				final String stadt = request.getParameter("stadt");
				final String etage = request.getParameter("etage");
				final String telNummer = request.getParameter("telefonnummer");
				final String hinweis = request.getParameter("lieferhinweise");
				
				UserBean user = new UserBean();
				AdresseBean adresse = new AdresseBean();

				user.setEmail(email);
				user.setPasswort(passwort);
				user.setLogin(true);
				user.setAdmin(false);

				adresse.setStrasse(strasse);
				adresse.setHausnummer(hausNr);
				adresse.setPlz(plz);
				adresse.setStadt(stadt);
				adresse.setEtage(etage);
				adresse.setTelefonnummer(telNummer);
				adresse.setGeburtstag(geb);
				adresse.setHinweis(hinweis);
				adresse.setVorname(vorname);
				adresse.setNachname(nachname);

				user = addUser(user);
				adresse.setUserid(user.getUserid());

				adresse = addAdresse(adresse);

				HttpSession session = request.getSession();

				session.setAttribute("user", user);
				session.setAttribute("adresse", adresse);

				response.sendRedirect("html/konto.jsp");
			}

		} else {

			response.setContentType("text/html");
	        response.setCharacterEncoding("UTF-8");
	        response.sendRedirect("html/registrierung.jsp?Registrierung=false");
	        return;
		}
	}

	public boolean isEmail(String email) throws ServletException, IOException {

		boolean exist = true;
		String query = "SELECT * FROM thidb.User WHERE Email = ?";

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement pstm = conn.prepareStatement(query);) {

			pstm.setString(1, email);

			try (ResultSet rs = pstm.executeQuery()) { //resultset Liefert Tabelle bzw. Teil von einer Tabelle

				while(rs.next()) { //solange es eine Zeile gibt liefert true 
					exist = false;
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return exist;
	}

	public UserBean addUser(UserBean user) throws ServletException {

		String query = "INSERT INTO thidb.User (Email, Passwort, isLogin, isAdmin) values(?,?,?,?)";

		String[] generatedKeys = new String[] { "UserID" }; // Name der Spalte(n), die automatisch generiert wird
															// (werden)

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(query, generatedKeys)) {

			pstm.setString(1, user.getEmail());
			pstm.setString(2, user.getPasswort());
			pstm.setBoolean(3, true);
			pstm.setBoolean(4, false);

			pstm.executeUpdate();

			// Generierte(n) Schlüssel auslesen
			try(ResultSet rsKeys = pstm.getGeneratedKeys()){
				while (rsKeys.next()) {
					user.setUserid(rsKeys.getInt(1));
			}
			
			}

			conn.close();

		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		return user;
	}

	private AdresseBean addAdresse(AdresseBean adresse) throws ServletException {

		String query = "INSERT INTO thidb.Adresse (FKUserID, Strasse, Hausnummer, PLZ, Stadt, Etage, Telefonnummer, "
				+ "Geburtstag, Hinweis, Vorname, Nachname)  values(?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(query)) {

			
			pstm.setInt(1, adresse.getUserid());
			pstm.setString(2, adresse.getStrasse());
			pstm.setString(3, adresse.getHausnummer());
			pstm.setInt(4, adresse.getPlz());
			pstm.setString(5, adresse.getStadt());
			pstm.setString(6, adresse.getEtage());
			pstm.setString(7, adresse.getTelefonnummer());
			pstm.setString(8, adresse.getGeburtstag());
			pstm.setString(9, adresse.getHinweis());
			pstm.setString(10, adresse.getVorname());
			pstm.setString(11, adresse.getNachname());

			pstm.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		return adresse;
	}

}
