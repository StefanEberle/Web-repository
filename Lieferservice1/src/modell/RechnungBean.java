package modell;

import java.math.BigDecimal;

public class RechnungBean {
	
	private enum Status {
		erstellt, 
		bezahlt
	};
	
	private enum Bezahlung{
		bar, 
		ueberweisung
	}
	
	private int RechnungID;
	private int FKbestellungID;
	private int FKuserID;
	private Status status; 
	private Bezahlung bezahlung;
	private BigDecimal summe;
	
	public RechnungBean (int FKbestellungID, int FKuserID) {
		FKbestellungID = FKbestellungID; 
		FKuserID = FKuserID;
		status = Status.erstellt;
		bezahlung = Bezahlung.bar;
	}
	
	public RechnungBean() {}



	public int getRechnungID() {
		return RechnungID;
	}

	public void setRechnungID(int rechnungID) {
		RechnungID = rechnungID;
	}

	public int getFKuserID() {
		return FKuserID;
	}

	public void setFKuserID(int fKuserID) {
		FKuserID = fKuserID;
	}

	public int getFKbestellungID() {
		return FKbestellungID;
	}

	public void setFKbestellungID(int fKbestellungID) {
		FKbestellungID = fKbestellungID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Bezahlung getBezahlung() {
		return bezahlung;
	}

	public void setBezahlung(Bezahlung bezahlung) {
		this.bezahlung = bezahlung;
	}

	public BigDecimal getSumme() {
		return summe;
	}

	public void setSumme(BigDecimal summe) {
		this.summe = summe;
	};
	
	
	
	

}
