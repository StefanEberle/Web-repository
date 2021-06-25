package control;

import java.io.IOException;
import java.io.PrintWriter;
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

import modell.ArtikelBean;
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

				// Bisheriger Inhalt Warenkorb ausgeben lassen
		
		System.out.println("ArtikelNr, ¸bergeben von jsp_ " + dieserArtikel);
		System.out.println("Anzahl, ¸bergeben von jsp: " + dieseAnzahl);
		
		
		//Checken, ob User angemeldet ist und einen Warenkorb hat. Hat er KEINEN Warenkorb und ist NICHT angemeldet, Cookie zeugs. 
		//Ist er angemeldet und hat keinen Warenkorb, dann Cookies
		// Ist er angemeldet und HAT einen Warenkorb, dann direkt auf DB.
		
		//User angemeldet = check if Warenkorb vorhanden?
		if (user.isLogin() == true) {
			WarenkorbBean warenkorb = checkWarenkorbUserVorhanden(user.getUserid());
			if (warenkorb==null) {
				//User hat keinen Warenkorb -->Artikel als Cookies speichern
				cookieHinzu(request, response, dieserCookie, dieserArtikel, dieseAnzahl);
				
			}
			else {
				//user Hat einen Warenkorb --> WarenkorbArtikel aus DB holen und Artikel dort hinein speichern
				String query = "SELECT FKartikelID, AnzahlArtikel FROM thidb.WarenkorbArtikel WHERE FkWarenkorbID = ?";
				
				ArrayList<WarenkorbArtikelBean> warenkorbArtikel = new ArrayList<WarenkorbArtikelBean>();

				try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

					pstm.setInt(1, warenkorb.getWarenkorbID());

					ResultSet rs = pstm.executeQuery(); 
								
					while (rs.next()) {
						WarenkorbArtikelBean neuerWarenkorbArtikel = new WarenkorbArtikelBean(); 
						
						neuerWarenkorbArtikel.setFkartikelID(rs.getInt("artikelID"));
						neuerWarenkorbArtikel.setAnzahlArtikel(rs.getInt("anzahl"));
						warenkorbArtikel.add(neuerWarenkorbArtikel);		
						
					}
				}
				catch(Exception ex) {
					throw new ServletException(ex.getMessage());
				}
					
					
					for (int i = 0; i<warenkorbArtikel.size(); i++) {
						warenkorbArtikel.get(i).getFkartikelID();
						Integer dieserArtikelInt = Integer.getInteger(dieserArtikel);
						
						if (warenkorbArtikel.get(i).getFkartikelID() == dieserArtikelInt){
							int neueAnzahl = warenkorbArtikel.get(i).getAnzahlArtikel()+dieseAnzahl;
							
							//perform update in DB
							String query2= "UPDATE thidb.WarenkorbArtikel SET AnzahlArtikel = ? WHERE FKWarenkorbID = ? AND FKartikelID = ?";
							try (Connection conn2 = ds.getConnection(); PreparedStatement pstm2 = conn2.prepareStatement(query2)){
								pstm2.setInt(1, neueAnzahl);
								pstm2.setInt(2, warenkorb.getWarenkorbID());
								pstm2.setInt(3, dieserArtikelInt);
							}
						
						catch(Exception ex) {
							throw new ServletException(ex.getMessage());
						}
							
							
						}
						else {
							String query3 = "INSERT INTO thidb.WarenkorbArtikel (FKwarenkorbID, FKartikelID, AnzahlArtikel) values (?,?,?,?)";
							try (Connection conn3 =ds.getConnection(); PreparedStatement pstm3 = conn3.prepareStatement(query3)){
								pstm3.setInt(1, warenkorb.getWarenkorbID());
								pstm3.setInt(2,  dieserArtikelInt);
								pstm3.setInt(3, dieseAnzahl);
							}
							catch(Exception ex) {
								throw new ServletException(ex.getMessage());
							}
					}

				
			}
			
		}
		}
	
		
		//User NICHT angemeldet --> Artikel als Cookies speichern
		else {
			if (dieserCookie != null) {
				cookieHinzu(request, response, dieserCookie, dieserArtikel, dieseAnzahl);
			

		}

		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
		dispatcher.forward(request, response);

	}

}
	private WarenkorbBean checkWarenkorbUserVorhanden(int userID) throws ServletException {

		boolean hasResult;
		WarenkorbBean warenkorb = new WarenkorbBean();

		String query = "SELECT * FROM thidb.Warenkorb WHERE FKuserID = ?";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, userID);

			ResultSet rs = pstm.executeQuery(); // sendet das SQL-Query zum Server und √ºbergibt Ergebnisse an rs
			hasResult = rs.next(); // geht jetzt durch die Datens√§tze der Tabelle User und √ºberpr√ºft Mail und
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
	
	private void cookieHinzu(HttpServletRequest request, HttpServletResponse response, Cookie[] dieserCookie, String dieserArtikel, Integer dieseAnzahl) {
		System.out.println("Es sind cookies Vorhanden.");

		boolean artikelVorhanden = false;

		for (int i = 0; i < dieserCookie.length; i++) {

			String artikelid = dieserCookie[i].getName();
			String anzahl = dieserCookie[i].getValue();
			System.out.println("Der bestehende Cookie aus Liste hat folgenden Namen/artikelID: " + artikelid);
			System.out.println("der bestehende Cookie aus Liste hat folgenden Value/anzahl: " + anzahl);

			if (dieserArtikel.equals(artikelid)) {

				artikelVorhanden = true;
				System.out.println(
						"Der vo JSP ¸bergebene Artikel hat die selbe artikelID, wie der Cookie (Name), dieserArtikel = "
								+ dieserArtikel + "artikelID= " + artikelid);

				// falls artikelID Seite gleich wie artikelID Cookie, erhˆhe Anzahl
				int number = Integer.parseInt(anzahl);
				int pruef = dieseAnzahl + number;
				System.out.println("der neue Value (anzahl + number) =  " + pruef);

				if ((dieseAnzahl + number) < 50) {

					dieserCookie[i].setValue(Integer.toString(pruef));
					System.out.println("Artikel hat schon existiert, Anzahl erhoeht, ArtikelID= " + dieserArtikel
							+ " neue Anzahl= " + pruef);

					System.out.println(
							"Der Artikel m¸sste nun ver‰nder sein. Gib mir den Ver‰nderten Cookie zur¸ck.");
					System.out.println("Name :" + dieserCookie[i].getName());
					System.out.println("NEUER Value:" + dieserCookie[i].getValue());

				}
			}
		}

		if (!artikelVorhanden) {

			Cookie neuerCookie = new Cookie(dieserArtikel, Integer.toString(dieseAnzahl));
			neuerCookie.setMaxAge(60*60*24*7);
			response.addCookie(neuerCookie);
			System.out.println("new Cookie created, ArtikelID= " + dieserArtikel + " Anzahl= " + dieseAnzahl);
		}
		
	}
}
