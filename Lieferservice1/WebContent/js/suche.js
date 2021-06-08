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
				
				for(var i = 0; i < erg.length; i++){
					
					
						ausgabe +=  "<a class=" + "ausgabeMarke " + "href="+ "../../AuswahlArtikelServlet?marke="+ erg[i].originalText +"> " + erg[i].originalText + "</a>";
						ausgabe +=  "<br>"
			
				}
				
				document.getElementById("searchErg").innerHTML = ausgabe;
			}
			
		}
		
	}
	
	xmlhttp.open("POST", "../../HoleKategorieServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send("methode="+suche);
}