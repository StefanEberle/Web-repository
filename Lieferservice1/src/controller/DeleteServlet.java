package controller;

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
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		
		int unterKategorie = Integer.parseInt(request.getParameter("unterKategorieDelete"));

		try {
			deleteImg(unterKategorie);
			deleteArtikel(unterKategorie);
			deleteUnterKategorie(unterKategorie);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.sendRedirect("html/delete.jsp");

	}

	private void deleteImg(int unterKategorie) throws ServletException, SQLException {

		String query = "SELECT FKUnterkategorieID, FKartikelID, ArtikelBild  FROM thidb.Artikel A INNER JOIN ArtikelBild\n"
				+ "WHERE FKUnterkategorieID = ? \n" + "AND ArtikelID = FKartikelID";

		List<ArtikelBildBean> artikelList = new ArrayList<ArtikelBildBean>();

		try (Connection conn = ds.getConnection("root", "root"); PreparedStatement stm = conn.prepareStatement(query)) {

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

		String deleteQuery = "Delete FROM thidb.ArtikelBild WHERE (FKartikelID = ?)";

		for (int i = 0; i < artikelList.size(); i++) {

			try (Connection conn = ds.getConnection("root", "root");
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

	private void deleteArtikel(int unterkategorie) throws ServletException {

		
		String deleteQuery = "Delete FROM thidb.Artikel WHERE (FKUnterkategorieID = ?)";	

			try (Connection conn = ds.getConnection("root", "root");
					PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(deleteQuery)) {

				
				pstm.setInt(1, unterkategorie);

				pstm.executeUpdate();
				
				conn.close();
			} catch (Exception ex) {
				throw new ServletException(ex.getMessage());
			}

		
		
	}

	private void deleteUnterKategorie(int unterkategorie) throws ServletException {

		String deleteQuery = "Delete FROM thidb.UnterKategorie WHERE (UnterkategorieID = ?)";	

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(deleteQuery)) {

			
			pstm.setInt(1, unterkategorie);

			pstm.executeUpdate();
			
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}

}
