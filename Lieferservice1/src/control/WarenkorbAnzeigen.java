package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
/**
 * Servlet implementation class WarenkorbAnzeigen
 */
@WebServlet("/WarenkorbAnzeigen")
public class WarenkorbAnzeigen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Cookie cookie[] = request.getCookies();
		if (cookie !=null) {
			for (int i = 0; i<cookie.length; i++) {
				System.out.println("<br>" + cookie[i].getName()+" "+cookie[i].getValue());
			}
		}
			else {
				System.out.println("Nix funktionieren");
			}
			}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
