package modell;

import java.util.ArrayList;

public class SuchergebnisBean {
	private BestellungBean bestellung;
	private RechnungBean rechnung; 
	private ArrayList<WarenkorbArtikelBean> warenkorbArtikel = new ArrayList<WarenkorbArtikelBean>(); 
	private ArtikelBean artikel;
	public BestellungBean getBestellung() {
		return bestellung;
	}
	public void setBestellung(BestellungBean bestellung) {
		this.bestellung = bestellung;
	}
	public RechnungBean getRechnung() {
		return rechnung;
	}
	public void setRechnung(RechnungBean rechnung) {
		this.rechnung = rechnung;
	}
	
	public ArrayList<WarenkorbArtikelBean> getWarenkorbArtikel() {
		return warenkorbArtikel;
	}
	public void setWarenkorbArtikel(ArrayList<WarenkorbArtikelBean> warenkorbArtikel) {
		this.warenkorbArtikel = warenkorbArtikel;
	}
	public ArtikelBean getArtikel() {
		return artikel;
	}
	public void setArtikel(ArtikelBean artikel) {
		this.artikel = artikel;
	}
	
	

}
