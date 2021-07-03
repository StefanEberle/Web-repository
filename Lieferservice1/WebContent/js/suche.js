/* Autor: Stefan Eberle */
"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {
	
	var suche = document.getElementById("artikelSucheInput");
	suche.addEventListener("keyup", suchErgebnisse);
	
}

function suchErgebnisse(){
	
	var suche = document.getElementById("artikelSucheInput").value;
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {
			
			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
	
				var ausgabe = "";
				document.getElementById("searchErg").innerHTML = ausgabe;
				
				for(var i = 0; i < erg.length; i++){
					
					if( i > 4){
						break;
					}
					var string = erg[i].originalText;
					if(string.indexOf(' ') >= 0){ // Anführungszeichen schließt vor dem zweiten Word (z.B bei Coca Cola)  -.-
						string = erg[i].originalText.replace(/\s+/g, '_');
					}
					
						ausgabe +=  "<a class=" + "ausgabeMarke " + "href="+ "../../AuswahlArtikelServlet?marke="+ string +"> " + erg[i].originalText + "</a>";
						ausgabe +=  "<br>"
			
				}
				
				document.getElementById("searchErg").innerHTML = ausgabe;
			}
			
		}
		
	}
	
	xmlhttp.open("POST", "../../SucheAjaxServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send("markenBez="+suche);
}