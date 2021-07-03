/* Autor: Olga Ohlsson */
package control;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import modell.UserBean;
import modell.WarenkorbArtikelBean;
import modell.WarenkorbBean;

/**
 * Servlet implementation class WarenkorbArtikelServlet
 */
@WebServlet("/AddWarenkorbArtikelServlet")
public class AddWarenkorbArtikelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String dieserArtikel = request.getParameter("artikelID");
		Integer dieseAnzahl = Integer.parseInt(request.getParameter("menge"));
		UserBean user = (UserBean) session.getAttribute("user");

		// Cookie holen und Params abfangen
		Cookie dieserCookie[] = request.getCookies();

		// Checken, ob User angemeldet ist und einen Warenkorb hat. Hat er KEINEN
		// Warenkorb und ist NICHT angemeldet, Cookie erzeugen.
		// Ist er angemeldet und hat keinen Warenkorb, dann Cookies erzeugen
		// Ist er angemeldet und HAT einen Warenkorb, dann direkt auf DB.

		if (user == null) {
			// User NICHT angemeldet --> Artikel als Cookies speichern
			if (dieserCookie != null) {
				cookieHinzu(request, response, dieserCookie, dieserArtikel, dieseAnzahl);
			}

		} else if (user.isLogin() == true) {

			WarenkorbBean warenkorb = checkWarenkorbUserVorhanden(user.getUserid());
			if (warenkorb == null) {

				// User hat keinen Warenkorb -->Artikel als Cookies speichern
				cookieHinzu(request, response, dieserCookie, dieserArtikel, dieseAnzahl);

			} else {
				// user Hat einen Warenkorb --> WarenkorbArtikel aus DB holen und Artikel dort
				// speichern

				String query = "SELECT FKartikelID, AnzahlArtikel FROM thidb.Warenkorbartikel WHERE FKwarenkorbID = ?";

				ArrayList<WarenkorbArtikelBean> warenkorbArtikel = new ArrayList<WarenkorbArtikelBean>();

				try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

					pstm.setInt(1, warenkorb.getWarenkorbID());

					ResultSet rs = pstm.executeQuery();

					while (rs.next()) {
						WarenkorbArtikelBean neuerWarenkorbArtikel = new WarenkorbArtikelBean();

						neuerWarenkorbArtikel.setFkartikelID(rs.getInt("FKartikelID"));
						neuerWarenkorbArtikel.setAnzahlArtikel(rs.getInt("AnzahlArtikel"));
						warenkorbArtikel.add(neuerWarenkorbArtikel);

					}
				} catch (Exception ex) {
					throw new ServletException(ex.getMessage());
				}

				Integer dieserArtikelInt = Integer.parseInt(dieserArtikel);

				if (warenkorbArtikel.size() > 0) {

					boolean artikelVorhanden = false;

					for (int i = 0; i < warenkorbArtikel.size(); i++) {
						warenkorbArtikel.get(i).getFkartikelID();

						if (warenkorbArtikel.get(i).getFkartikelID() == dieserArtikelInt) {
							// Artikel schon in Warenkorb, erhöhe Anzahl

							artikelVorhanden = true;

							int neueAnzahl = warenkorbArtikel.get(i).getAnzahlArtikel() + dieseAnzahl;

							// perform update in DB
							String query2 = "UPDATE thidb.Warenkorbartikel SET AnzahlArtikel = ? WHERE FKwarenkorbID = ? AND FKartikelID = ?";
							try (Connection conn2 = ds.getConnection();
									PreparedStatement pstm2 = conn2.prepareStatement(query2)) {
								pstm2.setInt(1, neueAnzahl);
								pstm2.setInt(2, warenkorb.getWarenkorbID());
								pstm2.setInt(3, dieserArtikelInt);
								pstm2.executeUpdate();
								conn2.close();
							}

							catch (Exception ex) {
								throw new ServletException(ex.getMessage());
							}

						}
					}

					if (artikelVorhanden == false) {
						// Artikel noch nicht in Warenkorb. Neu auf DB erstellen.

						String query3 = "INSERT INTO thidb.Warenkorbartikel (FKwarenkorbID, FKartikelID, AnzahlArtikel) values (?,?,?)";
						try (Connection conn3 = ds.getConnection();
								PreparedStatement pstm3 = conn3.prepareStatement(query3)) {
							pstm3.setInt(1, warenkorb.getWarenkorbID());
							pstm3.setInt(2, dieserArtikelInt);
							pstm3.setInt(3, dieseAnzahl);

							pstm3.executeUpdate();
							conn3.close();
						} catch (Exception ex) {
							throw new ServletException(ex.getMessage());
						}

					}
				}

				else {
					// Kein Artikel in Warenkorb, Erstelle Artikel auf DB.

					String query3 = "INSERT INTO thidb.Warenkorbartikel (FKwarenkorbID, FKartikelID, AnzahlArtikel) values (?,?,?)";
					try (Connection conn3 = ds.getConnection();
							PreparedStatement pstm3 = conn3.prepareStatement(query3)) {
						pstm3.setInt(1, warenkorb.getWarenkorbID());
						pstm3.setInt(2, dieserArtikelInt);
						pstm3.setInt(3, dieseAnzahl);

						pstm3.executeUpdate();
						conn3.close();
					} catch (Exception ex) {
						throw new ServletException(ex.getMessage());
					}

				}

			}

		}

		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
		dispatcher.forward(request, response);

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

	private void cookieHinzu(HttpServletRequest request, HttpServletResponse response, Cookie[] dieserCookie,
			String dieserArtikel, Integer dieseAnzahl) {
		// Cookies vorhanden, füge diese hinzu

		boolean artikelVorhanden = false;

		for (int i = 0; i < dieserCookie.length; i++) {

			String artikelid = dieserCookie[i].getName();
			String anzahl = dieserCookie[i].getValue();

			if (dieserArtikel.equals(artikelid)) {

				artikelVorhanden = true;

				// falls diese artikelID gleich wie artikelID Cookie, erhöhe Anzahl
				int number = Integer.parseInt(anzahl);
				int pruef = dieseAnzahl + number;

				if ((dieseAnzahl + number) < 50) {
					// Artikel hat schon existiert, erhöhe Anzahl
					dieserCookie[i].setValue(Integer.toString(pruef));
					response.addCookie(dieserCookie[i]);

				}
			}
		}

		if (!artikelVorhanden) {

			Cookie neuerCookie = new Cookie(dieserArtikel, Integer.toString(dieseAnzahl));
			neuerCookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(neuerCookie);

		}

	}
}