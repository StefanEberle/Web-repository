package modell;

import java.io.Serializable;

public class TextBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String originalText;

	
	public String getOriginalText() {
		return originalText;
	}
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	
}
