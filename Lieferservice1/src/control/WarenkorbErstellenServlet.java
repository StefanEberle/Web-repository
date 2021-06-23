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
@WebServlet("/WarenkorbServlet")
public class WarenkorbErstellenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Wird aktiviert, wenn WarenkorbIcon gedr�ckt wird.
		// WarenkorbBean warenkorb = new WarenkorbBean();

		// int artikelID = Integer.parseInt(request.getParameter("artikelID"));
		// int menge = Integer.parseInt(request.getParameter("menge"));

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		if (user.isLogin() == true) {

			WarenkorbBean warenkorb = checkWarenkorbUserVorhanden(user.getUserid());

			// wenn es bisher keinen Warenkorb gibt und Cookies in Warenkorb umgewandelt
			// werden m�ssen
			if (warenkorb == null) {

				// Cookies in Warenkorb umwandeln
				
				WarenkorbBean warenkorbKunde = new WarenkorbBean();
				warenkorbKunde.setFkuserID(user.getUserid());
				
				WarenkorbArtikelBean[] warenkorbArtikel = cookiesAsWarenkorb(warenkorbKunde, request, response);

					// Warenkorb in Datenbank speichern
					String query2 = "INSERT INTO thidb.Warenkorb (WarenkorbID, fkUserID) values(?,?)";
					try (Connection conn2 = ds.getConnection();
							PreparedStatement pstm2 = (PreparedStatement) conn2.prepareStatement(query2)) {

						pstm2.setInt(1, warenkorbKunde.getWarenkorbID());
						pstm2.setInt(2, warenkorbKunde.getFkuserID());

						pstm2.executeUpdate();

						conn2.close();

					}
				

				catch (SQLException e) {
					// message = "Error: " + e.getMessage();
					e.printStackTrace();
				}

				String query3 = "INSERT INTO thidb.WarenkorbArtikel (fkWarenkorbID, fkArtikelID, AnzahlArtikel) values(?,?,?)";
				try (Connection conn3 = ds.getConnection();
						PreparedStatement pstm3 = (PreparedStatement) conn3.prepareStatement(query3)) {

					for (int i = 0; i < warenkorbArtikel.length; i++) {
						WarenkorbArtikelBean neu = new WarenkorbArtikelBean();
						neu = warenkorbArtikel[i];
						pstm3.setInt(1, neu.getFkwarenkorbID());
						pstm3.setInt(2, neu.getFkartikelID());
						pstm3.setInt(3, neu.getAnzahlArtikel());

						i++;
					}
				} catch (SQLException e) {
					// message = "Error: " + e.getMessage();
					e.printStackTrace();
				}

			} else if (warenkorb != null) {
				// es gibt schon einen Warenkorb! Hole diesen Warenkorb aus DB
				WarenkorbBean warenkorbKunde = new WarenkorbBean(); 
				
				warenkorbKunde.setFkuserID(user.getUserid());
				
				//HIER JOIN MIT WARENKORBARTIKEL
				
				String query4 = "SELECT WarenkorbID from thidb.Warenkorb where FkuserID = ?";
				try (Connection conn4 = ds.getConnection();
						PreparedStatement pstm4 = (PreparedStatement) conn4.prepareStatement(query4)) {

					pstm4.setInt(1, warenkorbKunde.getFkuserID());
						
							ResultSet rs4 = pstm4.executeQuery();

							while (rs4.next()) {
								warenkorbKunde.setWarenkorbID(rs4.getInt("WarenkorbID"));
							}
	
						
				} catch (SQLException e) {
					// message = "Error: " + e.getMessage();
					e.printStackTrace();
				}
				
					

			
			}

		}

	else{
		// send User to Login

		// erneutes Aufrufen der Seite
		response.sendRedirect("Warenkorb.html");
	}
	}

	
	private WarenkorbBean checkWarenkorbUserVorhanden(int userID) throws ServletException {

		boolean hasResult;
		WarenkorbBean warenkorb = new WarenkorbBean();

		String query = "SELECT * FROM thidb.Warenkorb WHERE FKuserID = ?";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, userID);

			ResultSet rs = pstm.executeQuery(); // sendet das SQL-Query zum Server und übergibt Ergebnisse an rs
			hasResult = rs.next(); // geht jetzt durch die Datensätze der Tabelle User und überprüft Mail und
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

	private WarenkorbArtikelBean[] cookiesAsWarenkorb(WarenkorbBean warenkorbKunde, HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
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
					if (cookie[i].equals(produktidArr[z])) {
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

}

