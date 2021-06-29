package modell;

import java.io.Serializable;
import java.math.BigDecimal;

public class  BestellungBean implements Serializable {


	private static final long serialVersionUID = 1L;
	private int BestellungID;
	private int FKuserID;
	private String status;

	
	public BestellungBean() {
		
	}

	public int getBestellungID() {
		return BestellungID;
	}

	public void setBestellungsID(int bestellungID) {
		BestellungID = bestellungID;
	}

	


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFKuserID() {
		return FKuserID;
	}

	public void setFKuserID(int fKuserID) {
		FKuserID = fKuserID;
	}

	public void setBestellungID(int bestellungID) {
		BestellungID = bestellungID;
	}

}

