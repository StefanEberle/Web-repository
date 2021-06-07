"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {
	
	var logInOutButton = document.getElementById("valueTest").value;
	var anmeldeButton = document.getElementById("anmeldeButton");
	var abmeldeButton = document.getElementById("abmeldeButton");
	var benutzerKonto = document.getElementById("benutzerKonto");
	
	if(logInOutButton == null ||  logInOutButton == 0){
		abmeldeButton.style.visibility = "hidden";
		benutzerKonto.style.visibility = "hidden";
	}
	if(logInOutButton == "true"){
		anmeldeButton.style.visibility = "hidden";
		abmeldeButton.style.visibility = "visible";
		benutzerKonto.style.visibility = "visible";
	}
	navBar();
	
	
	
	/*
	 * 
	 var suche = document.getElementById("artikelSuche");
	suche.style.visibility = "hidden"; 
	  
	 
	var suchbar = document.getElementById("search");
	var suchbarInput = document.getElementById("artikelSuche");
	var sucheErg = document.getElementById("searchErg");
	suchbar.addEventListener("mouseover", showInput);
	suchbarInput.addEventListener("mouseover", showInput);
	suchbar.addEventListener("mouseover",showInput);
	
	var suchbarHidden = document.getElementById("divLupe");
	suchbarHidden.addEventListener("mouseout", hiddInput);
*/
}

function hiddInput(){
	var suche = document.getElementById("artikelSuche");
	suche.style.visibility = "hidden";
}

function showInput() {
	var suche = document.getElementById("artikelSuche");
	suche.style.visibility = "visible";
}

function navBar(){
	

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {
				
				var erg = JSON.parse(xmlhttp.responseText);
				var katBezListe = new Array();
				

				
				var ausgabe = "";
				var tmp = 1;
				for(var i = 0; i < erg.length; i++){

					if(tmp < erg[i].fkKategorieID){ //Neue Kategorie in Liste --> div schließen
						ausgabe += "</div>";
						ausgabe += "</div>";
						tmp++;
					}
					
					if(!katBezListe.includes(erg[i].bezeichnungKat)){
						katBezListe.push(erg[i].bezeichnungKat);
						ausgabe += "<div class="+ "dropdown" + ">";
						ausgabe += "<button + type=" + "submit"+ " "  + "class=" + "dropbtn" +" "+"name=" + "kategorie " + "value="+ erg[i].fkKategorieID + ">";
						ausgabe += erg[i].bezeichnungKat;
						ausgabe += "</button>";
						ausgabe += "<div class=" + "dropdown-content" + ">";
					}
					
//					ausgabe += erg[i].fkKategorieID;
//					ausgabe += "</li><li>";
					
					ausgabe += "<a href="+ "../../AuswahlArtikelServlet?unterKategorie=" + erg[i].unterkategorieID +"&kategorie=" +
								erg[i].fkKategorieID +">";
					ausgabe += erg[i].unterkategorieBez + " ";
					ausgabe += "</a>";
					
					if(i == erg.length-1){ // letzte UnterKategorie div schließen
						ausgabe += "</div>";
						ausgabe += "</div>";
					}
					
				}
				
				
//				var ausgabe = "";
////					"<table class='navigation'><tr><td class='suche'>Deine Suchergebnisse</td></tr>"
//					
//				
//				for (var i=0; i < erg.length; i++) {
//					
//						ausgabe += "<li>";
//						ausgabe += erg[i].kategorieID;
//						ausgabe += "</li><li>";
//						ausgabe += erg[i].bezeichnungKat;
//						ausgabe += "</li>"
//					}
				
//				ausgabe += "</table>";
				document.getElementById("navigationBar").innerHTML = ausgabe;
//				document.getElementById("emailTest").value = erg.originalText;
				

			}

		}
	}
	xmlhttp.open("GET", "../../HoleKategorieServlet", true);
	xmlhttp.send();
	
}