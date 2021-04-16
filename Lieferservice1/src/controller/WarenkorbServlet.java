package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
public class WarenkorbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		WarenkorbBean warenkorb = new WarenkorbBean();
		WarenkorbArtikelBean wkArtikel = new WarenkorbArtikelBean();
		UserBean user = new UserBean();

		int artikelID = Integer.parseInt(request.getParameter("artikelID"));
		int menge = Integer.parseInt(request.getParameter("menge"));

		HttpSession session = request.getSession();
		user = (UserBean) session.getAttribute("user");

		if (user.isLogin() == true) {

			warenkorb = checkWarenkorbUserVorhanden(user.getUserid());
			if (warenkorb != null) {
				
				/** WarenkorbArtikel die übergebenen Parameter übergeben
				 * ein Warenkorb hat eine/mehrere WarenkorbArtikel
				 * FKwarenkorbID muss für den User gleich sein
				 * return liste WarenkorbArtikel für einen User
				 * 
				 *  falls der User noch keinen Warenkorb hat dann
				 *  anlegen und die obigen Schritte ausführen
				 *  
				 *  Warenkorb muss dann noch aktualisert oder 
				 *  geändert werden können und nach der Bestellung 
				 *  müssen alle Einträge der beiden Tabelle gelöscht werden
				 *  
				 *  Falls es einen Warenkorb gibt soll dieser einmal auf einer eigenen 
				 *  jsp angezeigt werden und im Popup Header Icon (bei Hover)**/
			}

		}
	}

	private WarenkorbBean checkWarenkorbUserVorhanden(int userID) throws ServletException {

		boolean hasResult;
		WarenkorbBean warenkorb = new WarenkorbBean();

		String query = "SELECT * FROM thidb.Warenkorb WHERE FKuserID = ?";

		try (Connection conn = ds.getConnection("root", "root");
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

	private WarenkorbArtikelBean addArtikelZuWarenkorb(int warenkorbID,int artikelID, int menge) {

		return null;
	}
}
