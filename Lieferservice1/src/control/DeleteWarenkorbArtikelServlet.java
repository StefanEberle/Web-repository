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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


import modell.ArtikelBean;
import modell.UserBean;
import modell.WarenkorbArtikelBean;
import modell.WarenkorbBean;

/**
 * Servlet implementation class WarenkorbArtikelServlet
 */
@WebServlet("/WarenkorbServlet")
public class DeleteWarenkorbArtikelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String dieserArtikel = request.getParameter("artikelID");
		Integer dieseAnzahl = Integer.parseInt(request.getParameter("menge"));
		UserBean user = (UserBean) session.getAttribute("user");
		WarenkorbBean warenkorb = (WarenkorbBean) session.getAttribute("warenkorb");
	


				// Bisheriger Inhalt Warenkorb ausgeben lassen
		
		System.out.println("ArtikelNr, übergeben von jsp_ " + dieserArtikel);
		System.out.println("Anzahl, übergeben von jsp: " + dieseAnzahl);
		
		String query = "SELECT FKartikelID, AnzahlArtikel FROM thidb.Warenkorbartikel WHERE FKwarenkorbID = ?";
				
				ArrayList<WarenkorbArtikelBean> warenkorbArtikel = new ArrayList<WarenkorbArtikelBean>();

				try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

					pstm.setInt(1, warenkorb.getWarenkorbID());

					ResultSet rs = pstm.executeQuery(); 
								
					while (rs.next()) {
						WarenkorbArtikelBean neuerWarenkorbArtikel = new WarenkorbArtikelBean(); 
						
						neuerWarenkorbArtikel.setFkartikelID(rs.getInt("FKartikelID"));
						neuerWarenkorbArtikel.setAnzahlArtikel(rs.getInt("AnzahlArtikel"));
						warenkorbArtikel.add(neuerWarenkorbArtikel);		
						
					}
				}
				catch(Exception ex) {
					throw new ServletException(ex.getMessage());
				}
				System.out.println("Als nächstes sollte ich in die FOR schleife springen");
				Integer dieserArtikelInt = Integer.parseInt(dieserArtikel);
				
				if (warenkorbArtikel.size()>0) {
					boolean artikelVorhanden = false;
					
					for (int i = 0; i<warenkorbArtikel.size(); i++) {
						warenkorbArtikel.get(i).getFkartikelID();
						
						System.out.println("Ich bin in der FOR-Schleife. ArtikelID von i = " + warenkorbArtikel.get(i).getFkartikelID() + "ArtikelID von 'dieserArtikel' = " + dieserArtikelInt);
						
						if (warenkorbArtikel.get(i).getFkartikelID() == dieserArtikelInt){
							System.out.println("Es gibt diesen artikel in meinem Warenkorb!! Verringere anzahlin DB!! :) ");
							artikelVorhanden = true;
							
							int neueAnzahl = warenkorbArtikel.get(i).getAnzahlArtikel()-dieseAnzahl;
							
							//perform update in DB
							String query2= "UPDATE thidb.Warenkorbartikel SET AnzahlArtikel = ? WHERE FKwarenkorbID = ? AND FKartikelID = ?";
							try (Connection conn2 = ds.getConnection(); PreparedStatement pstm2 = conn2.prepareStatement(query2)){
								pstm2.setInt(1, neueAnzahl);
								pstm2.setInt(2, warenkorb.getWarenkorbID());
								pstm2.setInt(3, dieserArtikelInt);
								pstm2.executeUpdate(); 
								conn2.close();
							}
						
						catch(Exception ex) {
							throw new ServletException(ex.getMessage());
						}
							
		
				
			}
			
		}
		
	
	final RequestDispatcher dispatcher = request.getRequestDispatcher("/html/auswahlArtikel.jsp");
		dispatcher.forward(request, response);

	}
	}
}


	
	


