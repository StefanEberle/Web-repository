package modell;

import java.io.Serializable;

public class WarenkorbBean implements Serializable{

	private int warenkorbID;
	private int fkuserID;
	
	
	public int getWarenkorbID() {
		return warenkorbID;
	}
	public void setWarenkorbID(int warenkorbID) {
		this.warenkorbID = warenkorbID;
	}
	public int getFkuserID() {
		return fkuserID;
	}
	public void setFkuserID(int fkuserID) {
		this.fkuserID = fkuserID;
	}
	
}
