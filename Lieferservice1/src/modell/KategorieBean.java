package modell;

import java.io.Serializable;

public class KategorieBean implements Serializable{

	

	private static final long serialVersionUID = 1L;
	
	private int kategorieID;
	private String bezeichnungKat;
	
	public int getKategorieID() {
		return kategorieID;
	}
	public void setKategorieID(int kategorieID) {
		this.kategorieID = kategorieID;
	}
	public String getBezeichnungKat() {
		return bezeichnungKat;
	}
	public void setBezeichnungKat(String bezeichnungKat) {
		this.bezeichnungKat = bezeichnungKat;
	}
	
}
