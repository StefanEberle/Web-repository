/* Autor: Stefan Eberle */
package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DeleteArtikelServlet
 */
@WebServlet("/DeleteArtikelServlet")
public class DeleteArtikelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String delete = request.getParameter("deleteArtikel");
		
		

		//Bild anhand der ID aus der Tabelle löschen und wenn bei Artikel die gleiche ID vorhanden ist - diese ebenfalls löschen
		String query = "DELETE ab.* , a.* FROM thidb.ArtikelBild ab LEFT JOIN Artikel a ON a.ArtikelID = ab.FKartikelID WHERE ab.FKartikelID = ?";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstm = conn.prepareStatement(query)){
			
			pstm.setString(1, delete);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("html/deleteArtikel.jsp");
		dispatcher.forward(request, response);
	}

}
