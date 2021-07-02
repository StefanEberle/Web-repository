package modell;

public class BankdatenBean {
	
	private int FKuserID; 
	private String kontoinhaber; 
	private String IBAN; 
	private String bank;
	
	public int getFKuserID() {
		return FKuserID;
	}
	public void setFKuserID(int fKuserID) {
		FKuserID = fKuserID;
	}
	public String getKontoinhaber() {
		return kontoinhaber;
	}
	public void setKontoinhaber(String kontoinhaber) {
		this.kontoinhaber = kontoinhaber;
	}
	
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	

}
