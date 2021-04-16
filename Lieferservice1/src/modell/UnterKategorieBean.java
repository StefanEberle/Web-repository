package modell;

import java.io.Serializable;

public class UnterKategorieBean extends KategorieBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fkKategorieID;
	private int unterkategorieID;
	private String unterkategorieBez;
	
	public int getFkKategorieID() {
		return fkKategorieID;
	}
	public void setFkKategorieID(int fkKategorieID) {
		this.fkKategorieID = fkKategorieID;
	}
	public int getUnterkategorieID() {
		return unterkategorieID;
	}
	public void setUnterkategorieID(int unterkategorieID) {
		this.unterkategorieID = unterkategorieID;
	}
	public String getUnterkategorieBez() {
		return unterkategorieBez;
	}
	public void setUnterkategorieBez(String unterkategorieBez) {
		this.unterkategorieBez = unterkategorieBez;
	}
	
}
