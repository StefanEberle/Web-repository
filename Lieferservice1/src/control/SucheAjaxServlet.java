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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import modell.TextBean;

/**
 * Servlet implementation class sucheAjax
 */
@WebServlet("/SucheAjaxServlet")
public class SucheAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String suche = request.getParameter("markenBez"); // Input

		/* Für Marken Filter */

		String katID = request.getParameter("kategorie");
		String unterKatID = request.getParameter("unterKategorie");

		ArrayList<TextBean> sucheList = new ArrayList<TextBean>();

		if (suche != null) {
			sucheList = getMarke(suche);

		}

		if (katID != null && unterKatID == null) {
			sucheList = getFilterMarken(katID, "FKKategorieID");
		}
		if (katID == null && unterKatID != null) {
			sucheList = getFilterMarken(unterKatID, "FKUnterkategorieID");
		}

		request.setAttribute("sucheList", sucheList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("html/json/suche.jsp");
		dispatcher.forward(request, response);
	}

	private ArrayList<TextBean> getMarke(String bez) throws ServletException {

		ArrayList<TextBean> list = new ArrayList<TextBean>();

		bez = "%" + bez + "%";
		int tmp = 0;
		
		String query = "SELECT DISTINCT Marke FROM thidb.Artikel WHERE Marke LIKE ?";

		try (Connection conn = ds.getConnection();
				PreparedStatement stm = conn.prepareStatement(query);) {

			stm.setString(1, bez);

			try (ResultSet rs = stm.executeQuery()) {

				while (rs != null && rs.next()) {
					
					if (!bez.equals("%%")) {
						
						TextBean name = new TextBean();
						name.setOriginalText(rs.getString("Marke"));

						list.add(name);
					}
					
				}

			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		return list;
	}

	private ArrayList<TextBean> getFilterMarken(String bez, String tmp) throws ServletException {

		ArrayList<TextBean> list = new ArrayList<TextBean>();

		String query = "SELECT DISTINCT Marke FROM thidb.Artikel WHERE " + tmp + "= ?";

		try (Connection conn = ds.getConnection();
				PreparedStatement stm = conn.prepareStatement(query);) {

			stm.setString(1, bez);

			try (ResultSet rs = stm.executeQuery()) {

				while (rs != null && rs.next()) {

					TextBean name = new TextBean();
					name.setOriginalText(rs.getString("Marke"));

					list.add(name);

				}

			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		return list;
	}

}
