package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import modell.AdresseBean;
import modell.UserBean;



@WebServlet("/RegistrierungServlet")
public class RegistrierungServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
						
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
	UserBean user;
	AdresseBean adresse;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String vorname = request.getParameter("vorname");
		final String nachname = request.getParameter("nachname");
		final String email = request.getParameter("email");
		final String passwort = request.getParameter("passwort");
		final String passwort2 = request.getParameter("passwort2");
		
		
		final String strasse = request.getParameter("strasse");
		final String hausNr = request.getParameter("hausnummer");
		final int plz = Integer.parseInt(request.getParameter("plz"));
		final String stadt = request.getParameter("stadt");
		final String etage = request.getParameter("etage");
		final String telNummer = request.getParameter("telefonnummer");
		final String hinweis = request.getParameter("lieferhinweise");
		
		final String jahr = request.getParameter("jahr");
		final String monat = request.getParameter("monat");
		final String tag = request.getParameter("tag");
		
		String geb = tag + "." + monat + "." + jahr;
		
		if(checkEmail(email)) {
			
			
			
			if(!passwort.equals(passwort2)) {
				
				final RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("html/registrierung.jsp");
				((javax.servlet.RequestDispatcher) dispatcher).forward(request, response);
			}else {
				
				UserBean user = new UserBean();
				AdresseBean adresse = new AdresseBean();
				
				user.setEmail(email);
				user.setPasswort(passwort);
				user.setLogin(true);
				user.setAdmin(false);
				
				adresse.setStrasse(strasse);
				adresse.setHausnummer(hausNr);
				adresse.setPlz(plz);
				adresse.setStadt(stadt);
				adresse.setEtage(etage);
				adresse.setTelefonnummer(telNummer);
				adresse.setGeburtstag(geb);
				adresse.setHinweis(hinweis);
				adresse.setVorname(vorname);
				adresse.setNachname(nachname);
				
				user = userEintragDB(user);
				adresse.setUserid(user.getUserid());
				
				
				adresse = adresseEintragDB(adresse);
				
				HttpSession session = request.getSession(); 	

				session.setAttribute("user", user);
				session.setAttribute("adresse", adresse);
				
				
				
			}
			
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
    	response.setContentType("text/html"); 
    	request.setCharacterEncoding("UTF-8"); 
		
		final RequestDispatcher dispatcher = (RequestDispatcher) request.getRequestDispatcher("html/index.jsp");
		((javax.servlet.RequestDispatcher) dispatcher).forward(request, response);
	}
	
	
	public boolean checkEmail(String email) throws ServletException, IOException {
		
		boolean rueckgabe;
		String query = "SELECT * FROM thidb.User WHERE Email = ?";
		
		
		try (Connection conn = ds.getConnection("root","root");
				PreparedStatement stm = conn.prepareStatement(query);) {
			
			stm.setString(1, email);
			
			try (ResultSet rs = stm.executeQuery()) {
				
				if (!rs.isBeforeFirst()) {
					rueckgabe = true;
				} else {
					rueckgabe = false;
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return rueckgabe;
	}

	
	public UserBean userEintragDB(UserBean user) {
		
		String query = "INSERT INTO thidb.User (Email, Passwort, isLogin, isAdmin) values(?,?,?,?)";
		
		String [] generatedKeys = new String[] {"UserID"}; //Name der Spalte(n), die automatisch generiert wird (werden)
		
		
		try(Connection conn = ds.getConnection("root", "root");
				PreparedStatement stm = (PreparedStatement) conn.prepareStatement(query, generatedKeys)){
			
			stm.setString(1, user.getEmail());
			stm.setString(2, user.getPasswort());
			stm.setBoolean(3, true);
			stm.setBoolean(4, false);
			
        	stm.executeUpdate();
        	
        	//Generierte(n) Schlüssel auslesen 
        	ResultSet rsKeys = stm.getGeneratedKeys();
        	while(rsKeys.next()){
        		user.setUserid(rsKeys.getInt(1));
        	}
        	
        	conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user ;
	}
	
	private AdresseBean adresseEintragDB(AdresseBean adresse) {
		
		String query = "INSERT INTO thidb.Adresse (FKUserID, Strasse, Hausnummer, PLZ, Stadt, Etage, Telefonnummer, "
				+ "Geburtstag, Hinweis, Vorname, Nachname)  values(?,?,?,?,?,?,?,?,?,?,?)";
		
		
		try(Connection conn = ds.getConnection("root", "root");
				PreparedStatement stm = (PreparedStatement) conn.prepareStatement(query)){
		
		stm.setInt(1, adresse.getUserid());
		stm.setString(2, adresse.getStrasse());
		stm.setString(3, adresse.getHausnummer());
		stm.setInt(4,adresse.getPlz());
		stm.setString(5, adresse.getStadt());
		stm.setString(6, adresse.getEtage());
		stm.setString(7, adresse.getTelefonnummer());
		stm.setString(8, adresse.getGeburtstag());
		stm.setString(9, adresse.getHinweis());
		stm.setString(10, adresse.getVorname());
		stm.setString(11, adresse.getNachname());
		
		
		stm.executeUpdate();
		conn.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return adresse;
}

}