package modell;

import java.io.Serializable;

public class WarenkorbArtikelBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int warenkorbArtikelID; //Für einen Warenkorb gibt es "X" warenkorbArtikel
	private int fkwarenkorbID;		// Die Menge der WarenkorbArtikel in einem Warenkorb
	private int fkartikelID;
	private int anzahlArtikel;
	
	
	public int getWarenkorbArtikelID() {
		return warenkorbArtikelID;
	}
	public void setWarenkorbArtikelID(int warenkorbArtikelID) {
		this.warenkorbArtikelID = warenkorbArtikelID;
	}
	public int getFkwarenkorbID() {
		return fkwarenkorbID;
	}
	public void setFkwarenkorbID(int fkwarenkorbID) {
		this.fkwarenkorbID = fkwarenkorbID;
	}
	public int getFkartikelID() {
		return fkartikelID;
	}
	public void setFkartikelID(int fkartikelID) {
		this.fkartikelID = fkartikelID;
	}
	public int getAnzahlArtikel() {
		return anzahlArtikel;
	}
	public void setAnzahlArtikel(int anzahlArtikel) {
		this.anzahlArtikel = anzahlArtikel;
	}
	
}
