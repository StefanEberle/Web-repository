package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import modell.ArtikelBildBean;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteUnterkategorieServlet")
public class DeleteUnterkategorieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//Bilder und alle Artikel löschen und Unterkategorie
		
		/* wenn Zeit zusätzlich - Artikel einzeln löschen */
		
		String unterK = request.getParameter("unterKategorieDelete");

		if(unterK.equals("Unterkategorie")) {
			response.sendRedirect("html/deleteUnterKategorie.jsp");
			return;
		}
		
		int unterKategorie = Integer.parseInt(unterK);
		
		try {
			deleteImg(unterKategorie);
			deleteUnterKategorie(unterKategorie);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.sendRedirect("html/deleteUnterKategorie.jsp");

	}

	private void deleteImg(int unterKategorie) throws ServletException, SQLException {

		//FKartikelID der entsprechenden UK in Liste speichern
		
		String query = "SELECT FKUnterkategorieID, FKartikelID, ArtikelBild  FROM thidb.Artikel A INNER JOIN ArtikelBild\n"
				+ "WHERE FKUnterkategorieID = ? \n" + "AND ArtikelID = FKartikelID";

		List<ArtikelBildBean> artikelList = new ArrayList<ArtikelBildBean>();

		try (Connection conn = ds.getConnection(); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setInt(1, unterKategorie);
			try (ResultSet rs = stm.executeQuery()) {

				while (rs.next()) {
					ArtikelBildBean artikel = new ArtikelBildBean();

					artikel.setFkartikelID(rs.getInt("FKartikelID"));

					artikelList.add(artikel);

				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
		//Bild anhand der ID aus der Tabelle löschen und wenn bei Artikel die gleiche ID vorhanden ist - diese ebenfalls löschen
		String deleteQuery = "DELETE ab.* , a.* FROM thidb.ArtikelBild ab LEFT JOIN Artikel a ON a.ArtikelID = ab.FKartikelID WHERE ab.FKartikelID = ?";

		for (int i = 0; i < artikelList.size(); i++) {

			try (Connection conn = ds.getConnection();
					PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(deleteQuery)) {

				ArtikelBildBean artikel = new ArtikelBildBean();
				artikel = artikelList.get(i);
				pstm.setInt(1, artikel.getFkartikelID());

				pstm.executeUpdate();
				
				conn.close();
			} catch (Exception ex) {
				throw new ServletException(ex.getMessage());
			}

		}

	}


	private void deleteUnterKategorie(int unterkategorie) throws ServletException {

		String deleteQuery = "Delete FROM thidb.UnterKategorie WHERE (UnterkategorieID = ?)";	

		try (Connection conn = ds.getConnection();
				PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(deleteQuery)) {

			
			pstm.setInt(1, unterkategorie);

			pstm.executeUpdate();
			
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}

}
