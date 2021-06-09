package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import modell.ArtikelBildBean;

/**
 * Servlet implementation class RetrieveImageServlet
 */
@WebServlet("/RetrieveImageServlet")
public class RetrieveImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		
		response.setContentType("text/html;charset=UTF-8");
		String query = "SELECT * FROM thidb.ArtikelBild WHERE FKartikelID = ?";

		int artikelID = Integer.parseInt(request.getParameter("artikelID"));

		ServletOutputStream output = response.getOutputStream();



		try (Connection conn = ds.getConnection("root", "root"); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setInt(1, artikelID);
			try (ResultSet rs = stm.executeQuery()) {

				


				
				if (rs.next()) {
					byte[] imageData = rs.getBytes("ArtikelBild");
					
					//response.setHeader("Content-Type", "image/gif");
					response.setContentType("image/gif");
					response.setContentLength(imageData.length);
					output = response.getOutputStream();
					output.write(imageData);
					output.flush();
					output.close(); 
					// Quelle https://stackoverflow.com/questions/15829367/how-to-display-an-image-from-mysql-database-on-a-jsp-page
				}

			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

	}

}
