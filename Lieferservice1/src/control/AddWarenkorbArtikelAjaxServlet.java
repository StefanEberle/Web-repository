package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



import modell.TextBean;
import modell.UserBean;
import modell.WarenkorbBean;


/**
 * Servlet implementation class AddWarenkorbArtikelAjaxServlet
 */
@WebServlet("/AddWarenkorbArtikelAjaxServlet")
public class AddWarenkorbArtikelAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String dieserArtikel = request.getParameter("artikelID");
		Integer dieserArtikelInt = Integer.parseInt(dieserArtikel);
		Integer dieseAnzahl = Integer.parseInt(request.getParameter("menge"));
		UserBean user = (UserBean) session.getAttribute("user");
		
		
		// hole warenkorb
		WarenkorbBean warenkorb = new WarenkorbBean();
		
		String query = "SELECT * FROM thidb.Warenkorb WHERE FKuserID = ?";

		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, user.getUserid());

			ResultSet rs = pstm.executeQuery(); 
			while ( rs.next()) {
				
				warenkorb.setWarenkorbID(rs.getInt("WarenkorbID"));
			
		}
			} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
		if (dieseAnzahl == 0) {
			String query3= "Delete from thidb.WarenkorbArtikel where FKwarenkorbID = ? AND FKartikelID = ?";
			try (Connection conn3= ds.getConnection(); PreparedStatement pstm3 = conn3.prepareStatement(query3)){
				
				pstm3.setInt(1, warenkorb.getWarenkorbID());
				pstm3.setInt(2, dieserArtikelInt);
				pstm3.executeUpdate(); 
				conn3.close();
			}
		
		catch(Exception ex) {
			throw new ServletException(ex.getMessage());
		}
			}
		
		else if (dieseAnzahl > 0) {
		String query2= "UPDATE thidb.Warenkorbartikel SET AnzahlArtikel = ? WHERE FKwarenkorbID = ? AND FKartikelID = ?";
		try (Connection conn2 = ds.getConnection(); PreparedStatement pstm2 = conn2.prepareStatement(query2)){
			pstm2.setInt(1,dieseAnzahl);
			pstm2.setInt(2, warenkorb.getWarenkorbID());
			pstm2.setInt(3, dieserArtikelInt);
			pstm2.executeUpdate(); 
			conn2.close();
		}
	
	catch(Exception ex) {
		throw new ServletException(ex.getMessage());
	}
	}
		BigDecimal neueSumme = BigDecimal.ZERO;
		
		
		String query3= "select Warenkorbartikel.AnzahlArtikel, Warenkorbartikel.FKartikelID, Artikel.Gesamtpreis" + 
				" from Warenkorbartikel Inner JOIN Artikel" + 
				" on Warenkorbartikel.FKartikelID = Artikel.ArtikelID" + 
				" where warenkorbartikel.FKwarenkorbID= ?";
		try (Connection conn3 = ds.getConnection(); PreparedStatement pstm3 = conn3.prepareStatement(query3)){
		
			pstm3.setInt(1, warenkorb.getWarenkorbID());
			
			ResultSet rs = pstm3.executeQuery(); 
			
			while (rs.next()) {
				BigDecimal anzahlDieserArtikel = BigDecimal.valueOf(rs.getInt("AnzahlArtikel"));
				neueSumme = neueSumme.add(rs.getBigDecimal("Gesamtpreis").multiply(anzahlDieserArtikel));
			}
			conn3.close();
		}
	
	catch(Exception ex) {
		throw new ServletException(ex.getMessage());
	}	
		
		TextBean summe = new TextBean();
			summe.setOriginalText(neueSumme.toString());
			request.setAttribute("textbean", summe);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("html/json/textBean.jsp");
			dispatcher.forward(request, response);

		
	}

}
