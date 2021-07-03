/* Autor: Stefan Eberle */
package modell;

public class ArtikelBildBean {

	private int fkartikelID;
	private byte[] artikelBild;
	private String base64Image;
	
	
	public int getFkartikelID() {
		return fkartikelID;
	}
	public void setFkartikelID(int fkartikelID) {
		this.fkartikelID = fkartikelID;
	}
	public byte[] getArtikelBild() {
		return artikelBild;
	}
	public void setArtikelBild(byte[] artikelBild) {
		this.artikelBild = artikelBild;
	}
	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
	
	
}
