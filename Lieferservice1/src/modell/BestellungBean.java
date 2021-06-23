package modell;

import java.io.Serializable;

public class  BestellungBean implements Serializable {
	public enum Status {
		aufgegeben, 
		geliefert
	};

	private static final long serialVersionUID = 1L;
	private int BestellungsID;
	private Status status;
	private WarenkorbBean warenkorb;
	
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

	public WarenkorbBean getWarenkorb() {
		return warenkorb;
	}

	public void setWarenkorb(WarenkorbBean warenkorb) {
		this.warenkorb = warenkorb;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}