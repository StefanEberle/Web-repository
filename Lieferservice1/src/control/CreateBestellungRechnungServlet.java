/* Autor: Olga Ohlsson */

package control;

import java.io.IOException;
import java.math.BigDecimal;
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

import modell.BankdatenBean;
import modell.BestellungBean;

import modell.UserBean;

import modell.RechnungBean;

/**
 * Servlet implementation class createBestellungServlet
 */
@WebServlet("/CreateBestellungRechnungServlet")
public class CreateBestellungRechnungServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")

	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		Integer dieserWarenkorbID = (Integer) session.getAttribute("WarenkorbID");
		UserBean user = (UserBean) session.getAttribute("user");
		BigDecimal summe = (BigDecimal) session.getAttribute("gesamtsumme");
		String zahlungsmethode = (String) request.getParameter("zahlungsmethode");

		if (dieserWarenkorbID == null) {
			request.setAttribute("errorRequest", "Kein Warenkorb vorhanden!");
			final RequestDispatcher dispatcher = (RequestDispatcher) request
					.getRequestDispatcher("html/WarenkorbAnzeigen.jsp");
			dispatcher.forward(request, response);
			return;
		}
		if (zahlungsmethode.equals("bankeinzug")) {
			
			
			if (request.getParameter("kontoinhaber").isEmpty() || request.getParameter("iban").isEmpty()|| request.getParameter("bank").isEmpty()) {
					request.setAttribute("errorRequest", "Keine Zahlungsdaten eingegeben!");
					final RequestDispatcher dispatcher = (RequestDispatcher) request
							.getRequestDispatcher("html/WarenkorbAnzeigen.jsp");
					dispatcher.forward(request, response);
				}
				
			else if (!checkUserZahlungsdaten(user.getUserid())) {
					String kontoinhaber = request.getParameter("kontoinhaber");
					String iban = request.getParameter("iban");
					String bank = request.getParameter("bank");
					BankdatenBean bankdaten = new BankdatenBean();
					

						bankdaten.setBank(bank);
						bankdaten.setIBAN(iban);
						bankdaten.setKontoinhaber(kontoinhaber);
						bankdaten.setFKuserID(user.getUserid());
					

					speichereBankdaten(bankdaten);
					
				}
							
			}

		BestellungBean bestellung = new BestellungBean();
		bestellung.setFKuserID(user.getUserid());
		bestellung.setStatus("aufgegeben");

		bestellungInDB(bestellung);

		RechnungBean rechnung = new RechnungBean();
		rechnung.setSumme(summe);
		rechnung.setFKuserID(user.getUserid());
		rechnung.setFKbestellungID(bestellung.getBestellungID());
		rechnung.setRechnungsstatus("offen");
		rechnung.setBezahlung(zahlungsmethode);

		rechnungInDB(rechnung);

		warenkorbAsBestellung(dieserWarenkorbID, bestellung);

		deleteWarenkorb(dieserWarenkorbID);

		// rechnungAnzeigen(request, response, rechnung);

		session.setAttribute("rechnung", rechnung);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.sendRedirect("html/RechnungAnzeigen.jsp");

	}

	// public void rechnungAnzeigen(HttpServletRequest request, HttpServletResponse
	// response, RechnungBean rechnung) {

	// String query= "";

//	}

	public void warenkorbAsBestellung(int dieserWarenkorbID, BestellungBean bestellung) throws ServletException {

		String query = "UPDATE warenkorbartikel " + "SET FKbestellungID = ? " + "WHERE FKwarenkorbID = ?";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, bestellung.getBestellungID());
			pstm.setInt(2, dieserWarenkorbID);
			pstm.executeUpdate();
			conn.close();

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

	}

	public void deleteWarenkorb(int dieserWarenkorbID) throws ServletException {
		// DB: WarenkorbArtikel FKwarenkorbID ON UPDATE SET NULL

		String query = "DELETE from Warenkorb where WarenkorbID = ?";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, dieserWarenkorbID);

			pstm.executeUpdate();
			conn.close();

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

	}

	public void bestellungInDB(BestellungBean bestellung) throws ServletException {

		String query = "INSERT INTO Bestellung (FKuserID, Status) values (?,?)";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, bestellung.getFKuserID());
			pstm.setString(2, bestellung.getStatus().toString());

			pstm.executeUpdate();
			conn.close();

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		String query2 = "Select BestellungID from Bestellung where FKuserID = ?";
		try (Connection conn2 = ds.getConnection(); PreparedStatement pstm2 = conn2.prepareStatement(query2)) {
			pstm2.setInt(1, bestellung.getFKuserID());

			ResultSet rs = pstm2.executeQuery();

			while (rs.next()) {
				bestellung.setBestellungID(rs.getInt("BestellungID"));
			}

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		

	}

	public void rechnungInDB(RechnungBean rechnung) throws ServletException {

		String query = "INSERT INTO Rechnung (FKbestellungID, FKuserID, summe, bezahlung) values (?,?,?,?)";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, rechnung.getFKbestellungID());
			pstm.setInt(2, rechnung.getFKuserID());
			pstm.setBigDecimal(3, rechnung.getSumme());
			pstm.setString(4, rechnung.getBezahlung());

			pstm.executeUpdate();
			conn.close();

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

	}

	public void speichereBankdaten(BankdatenBean bankdaten) throws ServletException {
		String query = "insert into Bankdaten (FKuserID,Kontoinhaber,IBAN, Bank) values(?,?,?,?);";
		try (Connection conn = ds.getConnection(); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setInt(1, bankdaten.getFKuserID());
			pstm.setString(2, bankdaten.getKontoinhaber());
			pstm.setString(3, bankdaten.getIBAN());
			pstm.setString(4, bankdaten.getBank());

			pstm.executeUpdate();
			conn.close();

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

	}
	
	private boolean checkUserZahlungsdaten(int userID) throws ServletException {
		
		boolean check = false;

		String query = "SELECT * from thidb.Bankdaten where FKuserID= ?";
		try (Connection conn = ds.getConnection();
				PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(query)) {
			pstm.setInt(1, userID);
			ResultSet rs = pstm.executeQuery();
			if (rs.next() && rs != null) {
				check = true;
			} else {
				check = false;

			}
			conn.close();

		} catch (SQLException ex) {
			// message = "Error: " + e.getMessage();
			throw new ServletException(ex.getMessage());
		}

		return check;
	}


}