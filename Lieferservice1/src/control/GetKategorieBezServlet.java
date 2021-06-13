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

import modell.KategorieBean;

import modell.UnterKategorieBean;

/**
 * Servlet implementation class GetKategorieBezServlet
 */
@WebServlet("/GetKategorieBezServlet")
public class GetKategorieBezServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		/** Liste mit Kategorien füllen ID und Bez  **/
		
			ArrayList<KategorieBean> listKategorie = new ArrayList<KategorieBean>();
			listKategorie = getKategorie();

			
			request.setAttribute("listKategorie", listKategorie);

			/** Zugriff auf JSON Dokument - über XMLHttpRequest-Obj (responseText) **/
			RequestDispatcher dispatcher = request.getRequestDispatcher("html/json/holKateg.jsp");
			dispatcher.forward(request, response);
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

/** 
		String holeKategorieBezeichnung = request.getParameter("kategorieNamenHolen");
		//ArrayList<TextBean> sucheList = new ArrayList<TextBean>();
		if (holeKategorieBezeichnung != null) {
			ArrayList<KategorieBean> listKategorie = new ArrayList<KategorieBean>();
			listKategorie = getKategorie();		
			request.setAttribute("listKategorie", listKategorie);
			RequestDispatcher dispatcher = request.getRequestDispatcher("html/holKateg.jsp");
			dispatcher.forward(request, response);
		}
**/
	}
	
private ArrayList<KategorieBean> getKategorie() throws ServletException{
		
		ArrayList<KategorieBean> kategorieList = new ArrayList<KategorieBean>();

		String query = "SELECT * FROM thidb.Kategorie";

		try (Connection conn = ds.getConnection("root", "root");
				PreparedStatement stm = conn.prepareStatement(query);) {

			try (ResultSet rs = stm.executeQuery()) {

				while (rs != null && rs.next()) {
					UnterKategorieBean kategorie = new UnterKategorieBean();
					
					kategorie.setKategorieID(rs.getInt("KategorieID"));
					kategorie.setBezeichnungKat(rs.getString("BezeichnungKat"));
					

					kategorieList.add(kategorie);
					
				}

			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
		
		return kategorieList;
	}

}
