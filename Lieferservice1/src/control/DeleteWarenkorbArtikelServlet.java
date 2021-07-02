package control;

import java.io.IOException;
import java.math.BigDecimal;
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

import modell.WarenkorbBean;

/**
 * Servlet implementation class WarenkorbArtikelServlet
 */
@WebServlet("/DeleteWarenkorbArtikelServlet")
public class DeleteWarenkorbArtikelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Integer dieserArtikel = Integer.parseInt(request.getParameter("deleteArtikel"));
		Integer dieseAnzahl = 0;
		UserBean user = (UserBean) session.getAttribute("user");

		// Bisheriger Inhalt Warenkorb ausgeben lassen

		WarenkorbBean warenkorb = new WarenkorbBean();

		String query = "SELECT * FROM thidb.Warenkorb WHERE FKuserID = ?";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, user.getUserid());

			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {

				warenkorb.setWarenkorbID(rs.getInt("WarenkorbID"));

			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		if (dieseAnzahl == 0) {
			String query3 = "Delete from thidb.WarenkorbArtikel where FKwarenkorbID = ? AND FKartikelID = ?";
			try (Connection conn3 = ds.getConnection(); PreparedStatement pstm3 = conn3.prepareStatement(query3)) {

				pstm3.setInt(1, warenkorb.getWarenkorbID());
				pstm3.setInt(2, dieserArtikel);
				pstm3.executeUpdate();
				conn3.close();
			}

			catch (Exception ex) {
				throw new ServletException(ex.getMessage());
			}
		}

		WarenkorbArtikelausDB(request, response, user, session);

		response.sendRedirect("html/WarenkorbAnzeigen.jsp");

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
}
