"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {
	
	//KategorieID und Bezeichnung
	addKategorie();
	
	var kategorie = document.getElementById("kategorie2");
	kategorie.addEventListener("change",unterKategorie);
	
	
}

function addKategorie(){
	
// bei artikelErzeugen.jsp zwei Selected einmal für UK hinzufügen und einmal für Artikel hinzufügen
// Deshalb kategorie und kategorie2

// ansonsten benutzt bei deleteArtikel.jsp und deleteUnterkategorie.jsp

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
	
	
		if (xmlhttp.readyState == 4) {
			
			if (xmlhttp.status == 200) {
				
				
				var erg = JSON.parse(xmlhttp.responseText);
				
				var ausgabe = "";
				
				var katBezListe = new Array();
				
				for(var i = 0; i < erg.length; i++){
					
					if(i == 0){
						ausgabe += "<option value=" + "0" + ">" + "Kategorie" + "</option>"
					}
					if(!katBezListe.includes(erg[i].bezeichnungKat)){
						
						
 						katBezListe.push(erg[i].bezeichnungKat);
 						ausgabe += "<option value=" + erg[i].kategorieID +">";

 						
 						ausgabe += erg[i].bezeichnungKat;
 						ausgabe += "</option>";
 					}

				}
				
				
			
			}
			
			
			if(document.getElementById("kategorie") !== null){
				document.getElementById("kategorie").innerHTML = ausgabe;
			}
			
			document.getElementById("kategorie2").innerHTML = ausgabe; 
			
		}
	}
	xmlhttp.open("GET", "../../AddKategorieAjaxServlet", true);
	xmlhttp.send();
	
}

function unterKategorie(){
	
	// Sobald Kategorie ausgewählt 
	// werden die dazugehörigen Unterkategorien angezeigt
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {
			
			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				
			
				var kategorieID = document.getElementById("kategorie2").value;
				
				var ausgabe = "";
				
				
				for(var i = 0; i < erg.length; i++){
					if(i == 0){
						ausgabe += "<option value=" + "0" + ">" + "Unterkategorie" + "</option>"
					}
					if(kategorieID == erg[i].fkKategorieID){
						
						ausgabe += "<option value=" + erg[i].unterkategorieID +">";
						ausgabe += erg[i].unterkategorieBez;
						ausgabe += "</option>";
					}
				}
				
			}
			
				document.getElementById("unterkategorie").innerHTML = ausgabe;
			
		}
	}
	xmlhttp.open("GET", "../../GetKatUKatAjaxServlet", true);
	xmlhttp.send();
}