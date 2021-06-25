package control;

import java.io.IOException;
import java.io.InputStream;
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

	



		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, artikelID);
			try (ResultSet rs = pstm.executeQuery()) {

				if (rs != null && rs.next()) {
					
					Blob img = rs.getBlob("ArtikelBild");
					response.reset();
					long length = img.length();
					response.setHeader("Conten-Length", String.valueOf(length));
					
					try(InputStream input = img.getBinaryStream();){
						final int bufferSize = 256;
						byte [] buffer = new byte[bufferSize];
						
						ServletOutputStream out = response.getOutputStream();
						while((length = input.read(buffer)) != -1) {
							out.write(buffer,0,(int) length);
						}
						out.flush();
					}

				}

			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

	}

}
