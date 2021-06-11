"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {

	kategorieArray();
	
	var kategorie = document.getElementById("kategorie2");
	kategorie.addEventListener("change",unterKategorie);
	
	
}

function kategorieArray(){
	
	// Artikel - delete und erzeugen jsp
	// Kategorien anzeigen
	
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
 						ausgabe += "<option value=" + erg[i].kategorieID +">";

 						
 						ausgabe += erg[i].bezeichnungKat;
 						ausgabe += "</option>";
 					}

				}
				
				
			
			}
			//document.getElementById("kategorie").innerHTML = ausgabe;
			//Für Unterkategorie Hinzufügen - (Kategorie auswählen und neue UK erstellen)
			if(document.getElementById("kategorie") !== null){
				document.getElementById("kategorie").innerHTML = ausgabe;
			}
			
			document.getElementById("kategorie2").innerHTML = ausgabe; //Erzeugen und Delete JSP 
			
		}
	}
	xmlhttp.open("GET", "../../GetKategorieBezServlet", true);
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
	xmlhttp.open("GET", "../../GetBezeichnungAjax", true);
	xmlhttp.send();
}