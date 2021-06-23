package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
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
		
		String dieserArtikel = request.getParameter("artikelid");
		Integer dieseAnzahl =  Integer.parseInt(request.getParameter("anzahl"));
		
		
		//Cookie holen und Params abfangen
		Cookie dieserCookie[] = request.getCookies();
		
		//Bisheriger Inhalt Warenkorb ausgeben lassen 
		for (int i=0; i<dieserCookie.length; i++) {
			String artikelid = dieserCookie[i].getName(); 
			String anzahl = dieserCookie[i].getValue();
			
			//falls artikelID Seite gleich wie artikelID Cookie, verringere Anzahl
				int number = Integer.parseInt(anzahl);
				if ((dieseAnzahl =- number)>0) { 
				dieserCookie[i].setValue(Integer.toString(dieseAnzahl));
				}
			}
		
		
		
		}
		
		
		
		
	}


