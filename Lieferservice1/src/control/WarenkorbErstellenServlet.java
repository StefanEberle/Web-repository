package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
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
 * Servlet implementation class WarenkorbServlet
 */
@WebServlet("/WarenkorbServlet")
public class WarenkorbErstellenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//WarenkorbBean warenkorb = new WarenkorbBean();
				
				//int artikelID = Integer.parseInt(request.getParameter("artikelID"));
				//int menge = Integer.parseInt(request.getParameter("menge"));

				HttpSession session = request.getSession();
				UserBean user = (UserBean) session.getAttribute("user");

				if (user.isLogin() == true) {

					//warenkorb = checkWarenkorbUserVorhanden(user.getUserid());
					if (request.getCookies() != null) {
						
						//wenn ein Warenkorb vorhanden, zeige Inhalt an.
						Cookie dieserCookie[] = request.getCookies();
						
						//Cookie in HasMap speichern, um sie sp�ter in HTML List anzeigen zu koennen.
						//HashMap<ArtikelBean,String> warenkorbInhalt = new HashMap<ArtikelBean,String>();
//						ArrayList<WarenkorbArtikelBean> warenkorbInhalt = new ArrayList<WarenkorbArtikelBean>();
//						
//						for (int i = 0; i<dieserCookie.length; i++) {
//							String str1 = dieserCookie[i].getName();
//							String str2 = dieserCookie[i].getValue();
//												
//							WarenkorbArtikelBean warenkorbArtikelNeu = new WarenkorbArtikelBean();
//							warenkorbArtikelNeu.setFkartikelID(Integer.parseInt(str1));
//							warenkorbArtikelNeu.setAnzahlArtikel(Integer.parseInt(str2));
//							warenkorbInhalt.add(warenkorbArtikelNeu);
//						}
						
					}
					}
						
						
					
					else {
						
						//neuen Warenkorb fuer User erstellen 
						WarenkorbBean neuerWarenkorb = new WarenkorbBean();
						neuerWarenkorb.setFkuserID(user.getUserid());
						
						//neuen Warenkorb in Cookie �bergeben
						
					
						String cookieWarenkorbID = request.getParameter(String.valueOf(neuerWarenkorb.getWarenkorbID()));
		
						
						//HTML Response 
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						
						//Cookie erstellen
						if (cookieWarenkorbID != null) {
							Cookie cookie = new Cookie(cookieWarenkorbID, "");
							cookie.setMaxAge(3600*7*24);
							response.addCookie(cookie);
							
							//erneutes Aufrufen der Seite
							response.sendRedirect("Warenkorb.html");
						}
						
						
					}

				}
			

	private WarenkorbBean checkWarenkorbUserVorhanden(int userID) throws ServletException {

		boolean hasResult;
		WarenkorbBean warenkorb = new WarenkorbBean();

		String query = "SELECT * FROM thidb.Warenkorb WHERE FKuserID = ?";

		try (Connection conn = ds.getConnection();
				PreparedStatement pstm = conn.prepareStatement(query)) {

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

}
