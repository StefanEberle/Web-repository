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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;
	UserBean user;
	AdresseBean adresse;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		
		
		if(user != null) {
			logInOut(user.getUserid(),false);
			session.removeAttribute("user");
			session.removeAttribute("adresse");
			
			response.sendRedirect("html/login.jsp");
		
		}else {
		
			final RequestDispatcher dispatcher = request.getRequestDispatcher("html/login.jsp");
			dispatcher.forward(request, response);
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String mail = request.getParameter("email");
		final String pw = request.getParameter("passwort");
		
		
		try {
			if(checklogin(mail, pw)) {
			
				
				
				UserBean user = new UserBean();
				user = getUserBean(mail);
				
				AdresseBean adresse = new AdresseBean();
				adresse.setUserid(user.getUserid());
				
				adresse = getAdressBean(adresse);
				
				logInOut(user.getUserid(), true);
				
				HttpSession session = request.getSession();
						session.setAttribute("user", user);
						session.setAttribute("adresse", adresse);
				
				final RequestDispatcher dispatcher = request.getRequestDispatcher("html/konto.jsp");
				 dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checklogin(String mail, String pw) throws SQLException, ServletException {
		boolean LoginOk;
		boolean hasResult;

		String query = "Select * from thidb.User where Email = ? and Passwort = ?";

		try (Connection conn = ds.getConnection("root","root"); PreparedStatement pstm = conn.prepareStatement(query)) {

			pstm.setString(1, mail);
			pstm.setString(2, pw);
			ResultSet rs = pstm.executeQuery(); // sendet das SQL-Query zum Server und übergibt Ergebnisse an rs
			hasResult = rs.next(); // geht jetzt durch die Datensätze der Tabelle User und überprüft Mail und Passwort

			if (hasResult) {
				LoginOk = true;
			} else {
				LoginOk = false;
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		return LoginOk;
	}

	private void logInOut(int userID, boolean isLogin) throws ServletException {
		
		

		String query = "UPDATE thidb.User Set isLogin = ? WHERE UserID = ?";

		try (Connection conn = ds.getConnection("root","root");
				PreparedStatement stm = (PreparedStatement) conn.prepareStatement(query);) {
			stm.setBoolean(1, isLogin);
			stm.setInt(2, userID);

			stm.executeUpdate();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}
	
	private UserBean getUserBean(String mail) throws SQLException, ServletException {

		UserBean user = new UserBean();

		String query = "Select * from thidb.User where Email = ?";

		try (Connection conn = ds.getConnection("root","root"); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setString(1, mail);
			try (ResultSet rs = stm.executeQuery()) {

				if (rs != null && rs.next()) {
					user.setUserid(rs.getInt("UserID"));
					user.setEmail(rs.getString("Email"));
					user.setAdmin(rs.getBoolean("isAdmin"));
					user.setLogin(true);
				}
			}
			conn.close();
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return user;
	}
	
	private AdresseBean getAdressBean(AdresseBean adresse) throws SQLException, ServletException {
		

		String query = "Select * from thidb.Adresse where FKUserID = ?";

		try (Connection conn = ds.getConnection("root","root"); PreparedStatement stm = conn.prepareStatement(query)) {

			stm.setInt(1, adresse.getUserid());
			try (ResultSet rs = stm.executeQuery()) {

				if (rs != null && rs.next()) {
					
					adresse.setStrasse(rs.getString("Strasse"));
					adresse.setHausnummer(rs.getString("Hausnummer"));
					adresse.setPlz(rs.getInt("PLZ"));
					adresse.setStadt(rs.getString("Stadt"));
					adresse.setEtage(rs.getString("Etage"));
					adresse.setTelefonnummer(rs.getString("Telefonnummer"));
					adresse.setGeburtstag(rs.getString("Geburtstag"));
					adresse.setHinweis(rs.getString("Hinweis"));
					adresse.setVorname(rs.getString("Vorname"));
					adresse.setNachname(rs.getString("Nachname"));
					

					conn.close();

				}
			}

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		return adresse;
	}
}	