package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import modell.UnterKategorieBean;

/**
 * Servlet implementation class UnterKategorieServlet
 */
@WebServlet("/UnterKategorieServlet")
public class UnterKategorieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//UK erzeugen
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		int kategorieID = Integer.parseInt(request.getParameter("kategorieBezeichnungAuswahl"));
		String unterKategorie = request.getParameter("unterKategorieErzeugen");
		
		
		UnterKategorieBean unterkategorie = new UnterKategorieBean();
		
		
		
		if(unterKategorie != null) {
			
			String query = "INSERT INTO thidb.UnterKategorie (FKKategorieID,UnterkategorieBez)  values(?,?)";

			String[] generatedKeys = new String[] { "UnterkategorieID" }; // Name der Spalte(n), die automatisch generiert wird
																	// (werden)

			try (Connection conn = ds.getConnection("root", "root");
					PreparedStatement stm = (PreparedStatement) conn.prepareStatement(query, generatedKeys)) {

				stm.setInt(1, kategorieID);
				stm.setString(2, unterKategorie);
				

				stm.executeUpdate();

				// Generierte(n) Schlüssel auslesen
				ResultSet rsKeys = stm.getGeneratedKeys();
				while (rsKeys.next()) {
					unterkategorie.setUnterkategorieID(rsKeys.getInt(1));
				}

				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
		response.sendRedirect("html/artikelErzeugen.jsp");
	
		
	}

}
