package control;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import modell.BestellungBean;

import modell.UserBean;
import modell.WarenkorbArtikelBean;
import modell.WarenkorbBean;
import modell.RechnungBean;



/**
 * Servlet implementation class createBestellungServlet
 */
@WebServlet("/createBestellungRechnungServlet")
public class CreateBestellungRechnungServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")

	private DataSource ds;

 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("welcome to CreateBEstellungRechnung2");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		Integer dieserWarenkorbID = (Integer) session.getAttribute("WarenkorbID");
		UserBean user = (UserBean) session.getAttribute("user");
		BigDecimal summe = (BigDecimal) session.getAttribute("gesamtsumme");
		String zahlungsmethode = (String) request.getParameter("zahlungsmethode");
		
		
		
		System.out.println("welcome to CreateBEstellungRechnung2");
		System.out.println("Zahlungsmethode= "+ zahlungsmethode);
		
		
		BestellungBean bestellung = new BestellungBean();
		bestellung.setFKuserID(user.getUserid());
		bestellung.setStatus("aufgegeben");
		
		bestellungInDB(bestellung);
		System.out.println("BEstellung erstellt");
		
		RechnungBean rechnung = new RechnungBean();
		rechnung.setSumme(summe);
		rechnung.setFKuserID(user.getUserid());
		rechnung.setFKbestellungID(bestellung.getBestellungID());
		rechnung.setRechnungsstatus("offen");
		rechnung.setBezahlung(zahlungsmethode);
		
		
		System.out.println("Rechnung erstellt. RECHNUNG BEZAHLUNG = " + rechnung.getBezahlung());
		
		
		
		rechnungInDB(rechnung);
		
		warenkorbAsBestellung(dieserWarenkorbID,bestellung);
		
		deleteWarenkorb(dieserWarenkorbID);
		
		//rechnungAnzeigen(request, response, rechnung);
		
		session.setAttribute("rechnung", rechnung);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.sendRedirect("html/RechnungAnzeigen.jsp");
		System.out.println("CreateBEstellungREchnung ENDE");
	}
	
	//public void rechnungAnzeigen(HttpServletRequest request, HttpServletResponse response, RechnungBean rechnung) {
		
		//String query= "";
		
		
//	}
	
	public void warenkorbAsBestellung(int dieserWarenkorbID, BestellungBean bestellung) throws ServletException {
		
		System.out.println("Wandle warenorbArtikel in BestellungsArtikel um");
		
		String query = "UPDATE warenkorbartikel " + 
				"SET FKbestellungID = ? " + 
				"WHERE FKwarenkorbID = ?";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {
			
			pstm.setInt(1, bestellung.getBestellungID());
			pstm.setInt(2, dieserWarenkorbID);
			pstm.executeUpdate(); 
			conn.close();

		}
		catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		System.out.println("WarenkorbArtikel Erfolgreich in Bestellung umgewandelt.");

	}
	
	public void deleteWarenkorb(int dieserWarenkorbID) throws ServletException {
		//DB: WarenkorbArtikel FKwarenkorbID ON UPDATE SET NULL
		System.out.println("Lösche nun Warenkorb. ON UPDATE SET NULL in Warenkorbartikel");
		
		String query = "DELETE from Warenkorb where WarenkorbID = ?";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {
			
			pstm.setInt(1, dieserWarenkorbID);
		
			pstm.executeUpdate(); 
			conn.close();

		}
		catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		System.out.println("Warenkorb erfolgreich gelöscht.");
		
	}
	
	public void bestellungInDB(BestellungBean bestellung) throws ServletException {
		
		System.out.println("Nun Bestelung in DB speichern");
		System.out.println("Dafür Values: FKuser ID = " + bestellung.getFKuserID() + "status= " + bestellung.getStatus().toString());
		
	
		String query = "INSERT INTO Bestellung (FKuserID, Status) values (?,?)";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {
			
			pstm.setInt(1, bestellung.getFKuserID());
			pstm.setString(2, bestellung.getStatus().toString());
		
			pstm.executeUpdate(); 
			conn.close();
			
			}
		catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		String query2 = "Select BestellungID from Bestellung where FKuserID = ?";
		try (Connection conn2 = ds.getConnection(); PreparedStatement pstm2 = conn2.prepareStatement(query2)) {
			 pstm2.setInt(1,bestellung.getFKuserID());
			 
			ResultSet rs=  pstm2.executeQuery();
			 
			 while (rs.next()) {
				 bestellung.setBestellungID(rs.getInt("BestellungID"));
			 }
			 
			 
		}
		catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
		System.out.println("Speichern der BEstellung in DB erfolgreich. BestellungID = "+ bestellung.getBestellungID());
		
	}
	
	public void rechnungInDB(RechnungBean rechnung) throws ServletException {
		
		System.out.println("Speichere Rechnung in DB");
		System.out.println("Dafür werte: FKbestellungID = "+ rechnung.getFKbestellungID());
		System.out.println("FKuserID= "+ rechnung.getFKuserID());
		System.out.println("Summe= " + rechnung.getSumme());
		
		
		String query = "INSERT INTO Rechnung (FKbestellungID, FKuserID, summe, bezahlung) values (?,?,?,?)";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {
			
			pstm.setInt(1, rechnung.getFKbestellungID());
			pstm.setInt(2, rechnung.getFKuserID());
			pstm.setBigDecimal(3, rechnung.getSumme());
			pstm.setString(4, rechnung.getBezahlung());
		
			pstm.executeUpdate(); 
			conn.close();

		}
		catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		
		System.out.println("Rechnung erfolgreich in DB gepseichert.");
		
	}


}
