"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {

	kategorieArray();
	
	var kategorie = document.getElementById("kategorie");
	kategorie.addEventListener("change",unterKategorie);
	
}

function kategorieArray(){
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {
			
			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				var ausgabe = "";
				
				var katBezListe = new Array();
				
				for(var i = 0; i < erg.length; i++){
					if(i == 0){
						ausgabe += "<option>" + "Kategorie" + "</option>"
					}
					if(!katBezListe.includes(erg[i].bezeichnungKat)){
						katBezListe.push(erg[i].bezeichnungKat);
						ausgabe += "<option value=" + erg[i].fkKategorieID +">";
						ausgabe += erg[i].bezeichnungKat;
						ausgabe += "</option>";
					}
				}
				
				
				//Kategorie und Unterkategorie und ID von beiden id als value in option und Bezeichnung als auswahl
			}
			document.getElementById("kategorie").innerHTML = ausgabe;
		}
	}
	xmlhttp.open("GET", "../../HoleKategorieServlet", true);
	xmlhttp.send();
	
}

function unterKategorie(){
	
	
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {
			
			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				
				var kategorieID = document.getElementById("kategorie").value;
				var ausgabe = "";
				
			
				
				for(var i = 0; i < erg.length; i++){
					if(i == 0){
						ausgabe += "<option>" + "Unterkategorie" + "</option>"
					}
					if(kategorieID == erg[i].fkKategorieID){
						
						ausgabe += "<option value=" + erg[i].unterkategorieID +">";
						ausgabe += erg[i].unterkategorieBez;
						ausgabe += "</option>";
					}
				}
				
				
				//Kategorie und Unterkategorie und ID von beiden id als value in option und Bezeichnung als auswahl
			}
			document.getElementById("unterkategorie").innerHTML = ausgabe;
		}
	}
	xmlhttp.open("GET", "../../HoleKategorieServlet", true);
	xmlhttp.send();
}