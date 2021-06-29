package modell;

import java.io.Serializable;
import java.math.BigDecimal;

public class RechnungBean  implements Serializable {
	

	
	private static final long serialVersionUID = 1L;
	private int RechnungID;
	private int FKbestellungID;
	private int FKuserID;
	private String rechnungsstatus; 
	private String bezahlung;
	private BigDecimal summe;
	
	public RechnungBean() {}

	public int getRechnungID() {
		return RechnungID;
	}

	public void setRechnungID(int rechnungID) {
		RechnungID = rechnungID;
	}

	public int getFKbestellungID() {
		return FKbestellungID;
	}

	public void setFKbestellungID(int fKbestellungID) {
		FKbestellungID = fKbestellungID;
	}

	public int getFKuserID() {
		return FKuserID;
	}

	public void setFKuserID(int fKuserID) {
		FKuserID = fKuserID;
	}


	public String getRechnungsstatus() {
		return rechnungsstatus;
	}

	public void setRechnungsstatus(String rechnungsstatus) {
		this.rechnungsstatus = rechnungsstatus;
	}

	public String getBezahlung() {
		return bezahlung;
	}

	public void setBezahlung(String bezahlung) {
		this.bezahlung = bezahlung;
	}

	public BigDecimal getSumme() {
		return summe;
	}

	public void setSumme(BigDecimal summe) {
		this.summe = summe;
	}

	
	
	
	

}
