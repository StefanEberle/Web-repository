package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

		System.out.println("ArtikelNr, übergeben von jsp_ " + dieserArtikel);
		System.out.println("Anzahl, übergeben von jsp: " + dieseAnzahl);

		// Cookie holen und Params abfangen
		Cookie dieserCookie[] = request.getCookies();

		// Bisheriger Inhalt Warenkorb ausgeben lassen

		if (dieserCookie != null) {
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
							"Der vo JSP übergebene Artikel hat die selbe artikelID, wie der Cookie (Name), dieserArtikel = "
									+ dieserArtikel + "artikelID= " + artikelid);

					// falls artikelID Seite gleich wie artikelID Cookie, erhöhe Anzahl
					int number = Integer.parseInt(anzahl);
					int pruef = dieseAnzahl + number;
					System.out.println("der neue Value (anzahl + number) =  " + pruef);

					if ((dieseAnzahl + number) < 50) {

						dieserCookie[i].setValue(Integer.toString(pruef));
						System.out.println("Artikel hat schon existiert, Anzahl erhoeht, ArtikelID= " + dieserArtikel
								+ " neue Anzahl= " + pruef);

						System.out.println(
								"Der Artikel müsste nun veränder sein. Gib mir den Veränderten Cookie zurück.");
						System.out.println("Name :" + dieserCookie[i].getName());
						System.out.println("NEUER Value:" + dieserCookie[i].getValue());

					}
				}
			}

			if (!artikelVorhanden) {

				Cookie neuerCookie = new Cookie(dieserArtikel, Integer.toString(dieseAnzahl));
				response.addCookie(neuerCookie);
				System.out.println("new Cookie created, ArtikelID= " + dieserArtikel + " Anzahl= " + dieseAnzahl);
			}

		}

		final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
		dispatcher.forward(request, response);

	}

}
