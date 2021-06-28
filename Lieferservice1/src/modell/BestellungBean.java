package modell;

import java.io.Serializable;
import java.math.BigDecimal;

public class  BestellungBean implements Serializable {
	public enum Status {
		aufgegeben, 
		geliefert
	};

	private static final long serialVersionUID = 1L;
	private int BestellungsID;
	private Status status;
	private WarenkorbBean FKwarenkorbID;
	
	public BestellungBean(WarenkorbBean warenkorb) {
		warenkorb = warenkorb;
		Status status = Status.aufgegeben;
		
	}

	public int getBestellungsID() {
		return BestellungsID;
	}

	public void setBestellungsID(int bestellungsID) {
		BestellungsID = bestellungsID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public WarenkorbBean getFKwarenkorbID() {
		return FKwarenkorbID;
	}

	public void setFKwarenkorbID(WarenkorbBean fKwarenkorbID) {
		FKwarenkorbID = fKwarenkorbID;
	}
}
