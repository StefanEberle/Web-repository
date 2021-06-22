package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import modell.UnterKategorieBean;

/**
 * Servlet implementation class HoleKategorieServlet
 */
@WebServlet("/GetBezeichnungAjax")
public class GetBezeichnungAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//visibileButton.js (navBar()) Dropdown
		//filter.js benötigt nur UK
		//kategorieAusgabe.js für <option> UK ausählen
		
		List<UnterKategorieBean> kategorieList = new ArrayList<UnterKategorieBean>();

		String query = "SELECT FKKategorieID, UnterkategorieBez, UnterkategorieID, K.BezeichnungKat \n"
				+ "FROM thidb.UnterKategorie, thidb.Kategorie K\n" + "WHERE FKKategorieID = K.KategorieID\n"
				+ "ORDER BY FKKategorieID";

		try (Connection conn = ds.getConnection();
				PreparedStatement stm = conn.prepareStatement(query);) {

			try (ResultSet rs = stm.executeQuery()) {

				while (rs != null && rs.next()) {
					UnterKategorieBean kategorie = new UnterKategorieBean();
					
					kategorie.setFkKategorieID(rs.getInt("FKKategorieID"));
					kategorie.setUnterkategorieBez(rs.getString("UnterkategorieBez"));
					kategorie.setBezeichnungKat(rs.getString("BezeichnungKat"));
					kategorie.setUnterkategorieID(rs.getInt("UnterkategorieID"));

					kategorieList.add(kategorie);
					
				}

			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		request.setAttribute("kategorieList", kategorieList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("html/json/dynKateg.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Auto-generated method stub
				doGet(request, response);

	}
	

	
	
}

