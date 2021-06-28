package control;

import java.io.IOException;
import java.io.PrintWriter;
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

import modell.UnterKategorieBean;
import modell.UserBean;
import modell.WarenkorbArtikelBean;
import modell.WarenkorbBean;

/**
 * Servlet implementation class WarenkorbServlet
 */
@WebServlet("/WarenkorbErstellen")
public class WarenkorbErstellenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Wird aktiviert, wenn WarenkorbIcon gedrückt wird.
		// WarenkorbBean warenkorb = new WarenkorbBean();

		// int artikelID = Integer.parseInt(request.getParameter("artikelID"));
		// int menge = Integer.parseInt(request.getParameter("menge"));

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		System.out.println("ich funktioniere überhaupt");

		if (user == null) {

			System.out.println("ich bin nicht angemeldet!! send me to login");
			// send User to Login

			// erneutes Aufrufen der Seite
			// response.sendRedirect("Lieferser.html");

		}

		else if (user.isLogin() == true) {
			System.out.println("ich springe in user.isLogin() ==true");

			WarenkorbBean warenkorb = checkWarenkorbUserVorhanden(user.getUserid());

			// wenn es bisher keinen Warenkorb gibt und Cookies in Warenkorb umgewandelt
			// werden müssen
			WarenkorbBean warenkorbKunde = new WarenkorbBean();
			warenkorbKunde.setFkuserID(user.getUserid());
			if (warenkorb == null) {
				System.out.println("ich springe in warenkorb == null und erstelle neuen Warenkorb");

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
			
			System.out.println("Es gibt Warenkorb oder Warenkorb wurde gerade erstellt. nun speichern in DB etc.");

			getWarenkorbFromDB(warenkorbKunde);

			WarenkorbArtikelBean[] warenkorbArtikel = cookiesAsWarenkorb(warenkorbKunde, request, response);

			speicherArtikelDB(warenkorbArtikel);
			session.setAttribute("WarenkorbID", warenkorbKunde.getWarenkorbID());

		}

		System.out.println("It fucking worked, bitch! Dies ist deine WarenkorbID:");
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.sendRedirect("html/WarenkorbAnzeigen.jsp");
		return;
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

		System.out.println("Ich bin in 'cookiesAsWarenkorb'Funktion gesprungen!");

		ArrayList<WarenkorbArtikelBean> warenkorbArtikel = new ArrayList<WarenkorbArtikelBean>();

		Cookie cookie[] = request.getCookies();

		System.out.println("ich habe mir Cookies geholt");

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

		System.out.println("ich habe mir die ArtikelIDs aus der DB geholt");

		String[] produktidArr = new String[produktid.size()];
		produktidArr = produktid.toArray(produktidArr);
		System.out.println("ArtikelIDs as array angelegt. Springe nun in Doppelschleife");

		// vergleiche Liste Cookied mit ArrayList ProduktIDs

		// arraylist mit neuen WarenkorbArtikeln erstellen

		for (int i = 0; i < cookie.length; i++) {
			for (int z = 0; z < produktidArr.length; z++) {

				String cookieName = cookie[i].getName();
				String artikelId = produktidArr[z];

				if (artikelId.equals(cookieName)) {
					System.out.println(
							"In DS: CookieName: " + cookie[i].getName() + " cookieValue = " + cookie[i].getValue());
					WarenkorbArtikelBean neuerArtikel = new WarenkorbArtikelBean();
					neuerArtikel.setFkwarenkorbID(warenkorbKunde.getWarenkorbID());
					neuerArtikel.setFkartikelID(Integer.parseInt(cookie[i].getName()));
					neuerArtikel.setAnzahlArtikel(Integer.parseInt(cookie[i].getValue()));
					System.out.println(
							"Ich bin in der Doppelschleife. neuerArtikel erstellt =" + neuerArtikel.getFkartikelID());

					warenkorbArtikel.add(neuerArtikel);

					// Delete Cookie, damit CookieList leer ist
					cookie[i].setMaxAge(1);
					response.addCookie(cookie[i]);
				}
			}

		}

		System.out.println(
				"Doppelschleifer erfolgreich. EIg: Array mit der size anlegen. size= " + warenkorbArtikel.size());
		System.out.println("Folgende Params: warenkorbArtikel.size()= " + warenkorbArtikel.size() + "");
		WarenkorbArtikelBean[] warenkorbArtikelArr = new WarenkorbArtikelBean[warenkorbArtikel.size()];
		warenkorbArtikelArr = warenkorbArtikel.toArray(warenkorbArtikelArr);
		return warenkorbArtikelArr;
	}

	private void speicherArtikelDB(WarenkorbArtikelBean[] warenkorbArtikel) {
		for (int i = 0; i < warenkorbArtikel.length; i++) {
			WarenkorbArtikelBean neu = new WarenkorbArtikelBean();
			neu.setFkwarenkorbID(warenkorbArtikel[i].getFkwarenkorbID());
			neu.setFkartikelID(warenkorbArtikel[i].getFkartikelID());
			neu.setAnzahlArtikel(warenkorbArtikel[i].getAnzahlArtikel());

			System.out.println(
					"speichere neuen warenkorbArtikel in db. dabei werte: fkwarenkorbID= " + neu.getFkwarenkorbID()
							+ " fkartikelID= " + neu.getFkartikelID() + " Anzahl= " + neu.getAnzahlArtikel());

			String query4 = "INSERT INTO thidb.Warenkorbartikel (FKwarenkorbID, FKartikelID, AnzahlArtikel) values(?,?,?)";
			try (Connection conn4 = ds.getConnection();
					PreparedStatement pstm4 = (PreparedStatement) conn4.prepareStatement(query4)) {

				pstm4.setInt(1, neu.getFkwarenkorbID());
				pstm4.setInt(2, neu.getFkartikelID());
				pstm4.setInt(3, neu.getAnzahlArtikel());

				pstm4.executeUpdate();
				conn4.close();
			} catch (SQLException e) {
				// message = "Error: " + e.getMessage();
				e.printStackTrace();
			}
		}
	}

	private void getWarenkorbFromDB(WarenkorbBean warenkorbKunde) {
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

		catch (SQLException e) {
			// message = "Error: " + e.getMessage();
			e.printStackTrace();
		}
	}
}
