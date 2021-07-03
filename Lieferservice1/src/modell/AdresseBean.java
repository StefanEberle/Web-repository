/* Autor: Stefan Eberle */
package modell;

import java.io.Serializable;

public class AdresseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userid;
	private String vorname;
	private String nachname;
	
	private String Strasse;
	private String hausnummer;
	private Integer plz;
	private String stadt;
	private String etage;
	private String telefonnummer;
	private String geburtstag;
	private String hinweis;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getStrasse() {
		return Strasse;
	}
	public void setStrasse(String strasse) {
		Strasse = strasse;
	}
	public String getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}
	public Integer getPlz() {
		return plz;
	}
	public void setPlz(Integer plz) {
		this.plz = plz;
	}
	public String getStadt() {
		return stadt;
	}
	public void setStadt(String stadt) {
		this.stadt = stadt;
	}
	public String getEtage() {
		return etage;
	}
	public void setEtage(String etage) {
		this.etage = etage;
	}
	public String getTelefonnummer() {
		return telefonnummer;
	}
	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}
	public String getGeburtstag() {
		return geburtstag;
	}
	public void setGeburtstag(String geburtstag) {
		this.geburtstag = geburtstag;
	}
	public String getHinweis() {
		return hinweis;
	}
	public void setHinweis(String hinweis) {
		this.hinweis = hinweis;
	}

	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
}
