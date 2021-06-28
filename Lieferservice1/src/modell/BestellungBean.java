package modell;

import java.io.Serializable;
import java.math.BigDecimal;

public class  BestellungBean implements Serializable {
	public enum Status {
		aufgegeben, 
		geliefert
	};

	private static final long serialVersionUID = 1L;
	private int BestellungID;
	private int FKuserID;
	private Status status;
	private int FKwarenkorbID;
	
	public BestellungBean(int FKwarenkorbID) {
		FKwarenkorbID = FKwarenkorbID;
		Status status = Status.aufgegeben;
	}
	
	public BestellungBean() {
		
	}

	public int getBestellungID() {
		return BestellungID;
	}

	public void setBestellungsID(int bestellungID) {
		BestellungID = bestellungID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getFKwarenkorbID() {
		return FKwarenkorbID;
	}

	public void setFKwarenkorbID(int fKwarenkorbID) {
		FKwarenkorbID = fKwarenkorbID;
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

