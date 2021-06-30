package control;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import modell.ArtikelBean;

/**
 * Servlet implementation class ArtikelServlet
 */
@WebServlet("/CreateArtikelServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5
		* 5, location = "/tmp", fileSizeThreshold = 1024 * 1024)

public class CreateArtikelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final int IMG_WIDTH = 200;
	private static final int IMG_HEIGHT = 200;

	@Resource(lookup = "java:jboss/datasources/MySqlThidbDS")
	private DataSource ds;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		ArtikelBean artikel = new ArtikelBean();

		
		final Part image;
		
		try {
			image = request.getPart("artikelBild");
			
		}catch (Exception e) {
			request.setAttribute("errorRequest", "Artikel create failed!");
			final RequestDispatcher dispatcher = request.getRequestDispatcher("html/artikelErzeugen.jsp");
			dispatcher.forward(request, response);
			return;
		}

		/** Parameter holen **/
		final String marke = request.getParameter("marke");
		final int kategorie = Integer.parseInt(request.getParameter("kategorie"));
		final int unterKategorie = Integer.parseInt(request.getParameter("unterKategorie"));
		final String gebinde = request.getParameter("gebinde");
		final BigDecimal fuellmenge = new BigDecimal(request.getParameter("fuelmenge"));
		final int stueckzahl = Integer.parseInt(request.getParameter("stueckzahl"));
		final BigDecimal gesamtpreis = new BigDecimal(request.getParameter("gesamtpreis"));
		final BigDecimal pfandProFlasche = new BigDecimal(request.getParameter("pfandProFlasche"));
		final BigDecimal pfandKasten = new BigDecimal(request.getParameter("pfandKasten"));

		BigDecimal epJeLiter = fuellmenge.multiply(new BigDecimal(stueckzahl));
		epJeLiter = gesamtpreis.divide(epJeLiter, 2, RoundingMode.HALF_EVEN);
		BigDecimal pfandGesamt = pfandProFlasche.multiply(new BigDecimal(stueckzahl)).add(pfandKasten);

		
		if (kategorie == 0 || unterKategorie == 0) {
			request.setAttribute("errorRequest", "Artikel create failed!");
			final RequestDispatcher dispatcher = request.getRequestDispatcher("html/artikelErzeugen.jsp");
			dispatcher.forward(request, response);
			return;
		}


		BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
		int imgWidth = bufferedImage.getWidth();
		int imgHeight = bufferedImage.getHeight();

		if (!checkSize(imgWidth, imgHeight)) {
			request.setAttribute("errorRequest", "Bild Verhältnis Weite/Höhe muss kleiner 1.5 und Weite & Höhe größer 200 sein!");
			final RequestDispatcher dispatcher = request.getRequestDispatcher("html/artikelErzeugen.jsp");
			dispatcher.forward(request, response);
			return;

		}

		/** ArtikelBean füllen **/
		artikel.setMarke(marke);
		artikel.setFkkategorieID(kategorie);
		artikel.setFkunterkategorieID(unterKategorie);
		artikel.setGebinde(gebinde);
		artikel.setFuellmenge(fuellmenge);
		artikel.setStueckzahl(stueckzahl);
		artikel.setGesamtpreis(gesamtpreis);
		artikel.setPfandProFlasche(pfandProFlasche);
		artikel.setPfandKasten(pfandKasten);
		artikel.setEpJeLiter(epJeLiter);
		artikel.setPfandGesamt(pfandGesamt);

		sicherArtikel(artikel); // Artikel in die Datenbank schreiben

		BufferedImage resized = resize(bufferedImage, IMG_HEIGHT, IMG_WIDTH);

		String imageType = "";
		String imageName = image.getSubmittedFileName();

		// Index von Punkt
		int i = imageName.lastIndexOf(".");
		if (i > 0) {
			imageType = imageName.substring(i + 1);
		}

		/***********
		 * https://newbedev.com/how-to-convert-bufferedimage-to-inputstream
		 ***********/

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(resized, imageType, os);
		InputStream outputImage = new ByteArrayInputStream(os.toByteArray());

		/********************************************************************
		 * Zitat Ende
		 ********************************************************************/

		sicherArtikelBild(artikel.getArtikelID(), outputImage);

		response.sendRedirect("html/artikelErzeugen.jsp");

	}

	/*
	 * https://www.baeldung.com/java-resize-image Abschnitt - 2.2 -
	 * ImagegetScaledInstance()
	 */
	private static BufferedImage resize(BufferedImage img, int height, int width) {

		Image resultingImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = outputImage.createGraphics();
		g.drawImage(resultingImage, 0, 0, width, height, null);
		g.dispose();
//		outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
		return outputImage;
	}

	/***************************************************************
	 * Zitat Ende
	 ***************************************************************/

	private void sicherArtikelBild(int artikelID, InputStream image) {

		String query = "INSERT INTO thidb.ArtikelBild (FKartikelID, ArtikelBild) values(?,?)";
		try (Connection conn = ds.getConnection();
				PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(query)) {

			pstm.setInt(1, artikelID);
			pstm.setBinaryStream(2, image);

			pstm.executeUpdate();

			conn.close();

		}

		catch (SQLException e) {
			// message = "Error: " + e.getMessage();
			e.printStackTrace();
		}

	}

	private void sicherArtikel(ArtikelBean artikel) {

		String query = "INSERT INTO thidb.Artikel (Marke, FKKategorieID, FKUnterkategorieID, Gebinde, "
				+ "Fuellmenge, Stueckzahl, Gesamtpreis, EPjeLiter, PfandproFlasche, PfandKasten, PfandGesamt)  values(?,?,?,?,?,?,?,?,?,?,?)";

		String[] generatedKeys = new String[] { "ArtikelID" }; // Name der Spalte(n), die automatisch generiert wird
																// (werden)

		try (Connection conn = ds.getConnection();
				PreparedStatement stm = (PreparedStatement) conn.prepareStatement(query, generatedKeys)) {

			stm.setString(1, artikel.getMarke());
			stm.setInt(2, artikel.getFkkategorieID());
			stm.setInt(3, artikel.getFkunterkategorieID());
			stm.setString(4, artikel.getGebinde());
			stm.setBigDecimal(5, artikel.getFuellmenge());
			stm.setInt(6, artikel.getStueckzahl());
			stm.setBigDecimal(7, artikel.getGesamtpreis());
			stm.setBigDecimal(8, artikel.getEpJeLiter());
			stm.setBigDecimal(9, artikel.getPfandProFlasche());
			stm.setBigDecimal(10, artikel.getPfandKasten());
			stm.setBigDecimal(11, artikel.getPfandGesamt());

			stm.executeUpdate();

			// Generierte(n) Schlüssel auslesen
			ResultSet rsKeys = stm.getGeneratedKeys();
			while (rsKeys.next()) {
				artikel.setArtikelID(rsKeys.getInt(1));
			}

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean checkSize(int width, int height) {

		double w = width;
		double h = height;
		double diff;
		if (w < 200 || h < 200) {
			return false;
		}
		if (w > h) {
			diff = w / h;
		} else if (h > w) {
			diff = h / w;
		} else {
			diff = 1;
		}

		if (diff > 1.5) {
			return false;
		} else {
			return true;
		}

	}

}
