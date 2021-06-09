package modell;

import java.math.BigDecimal;


public class ArtikelBean {

	private int artikelID;
	private String marke;
	private int fkkategorieID;
	private int fkunterkategorieID;
	private String gebinde;
	private BigDecimal fuellmenge;
	private int stueckzahl;
	private BigDecimal gesamtpreis;
	private BigDecimal epJeLiter;
	private BigDecimal pfandProFlasche;
	private BigDecimal pfandKasten;
	private BigDecimal pfandGesamt;
	
	
	
	public int getArtikelID() {
		return artikelID;
	}
	public void setArtikelID(int artikelID) {
		this.artikelID = artikelID;
	}
	public String getMarke() {
		return marke;
	}
	public void setMarke(String marke) {
		this.marke = marke;
	}
	public int getFkkategorieID() {
		return fkkategorieID;
	}
	public void setFkkategorieID(int fkkategorieID) {
		this.fkkategorieID = fkkategorieID;
	}
	public int getFkunterkategorieID() {
		return fkunterkategorieID;
	}
	public void setFkunterkategorieID(int fkunterkategorieID) {
		this.fkunterkategorieID = fkunterkategorieID;
	}
	public String getGebinde() {
		return gebinde;
	}
	public void setGebinde(String gebinde) {
		this.gebinde = gebinde;
	}
	public BigDecimal getFuellmenge() {
		return fuellmenge;
	}
	public void setFuellmenge(BigDecimal fuellmenge) {
		this.fuellmenge = fuellmenge;
	}
	public int getStueckzahl() {
		return stueckzahl;
	}
	public void setStueckzahl(int stueckzahl) {
		this.stueckzahl = stueckzahl;
	}
	public BigDecimal getGesamtpreis() {
		return gesamtpreis;
	}
	public void setGesamtpreis(BigDecimal gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}
	public BigDecimal getEpJeLiter() {
		return epJeLiter;
	}
//	public void setEpJeLiter(BigDecimal gesamtpreis, BigDecimal fuellmenge, int stueckzahl) {
//		BigDecimal tmp = fuellmenge.multiply(new BigDecimal(stueckzahl));
//		
//		this.epJeLiter =  gesamtpreis.divide(tmp, 2, RoundingMode.HALF_EVEN);
//	}
	public BigDecimal getPfandProFlasche() {
		return pfandProFlasche;
	}
	public void setPfandProFlasche(BigDecimal pfandProFlasche) {
		this.pfandProFlasche = pfandProFlasche;
	}
	public BigDecimal getPfandKasten() {
		return pfandKasten;
	}
	public void setPfandKasten(BigDecimal pfandKasten) {
		this.pfandKasten = pfandKasten;
	}
	public BigDecimal getPfandGesamt() {
		return pfandGesamt;
	}
//	public void setPfandGesamt(BigDecimal pfandProFlasche, BigDecimal pfandKasten, int stueckzahl) {
//		this.pfandGesamt = pfandProFlasche.multiply(new BigDecimal(stueckzahl)).add(pfandKasten);
//	}
	public void setEpJeLiter(BigDecimal epJeLiter) {
		this.epJeLiter = epJeLiter;
	}
	public void setPfandGesamt(BigDecimal pfandGesamt) {
		this.pfandGesamt = pfandGesamt;
	}

}
