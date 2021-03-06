"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {
	
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

					if(tmp < erg[i].fkKategorieID){ 	//Neue Kategorie in Liste --> div schließen
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

					
					ausgabe += "<a href="+ "../../AuswahlArtikelServlet?unterKategorie=" + erg[i].unterkategorieID +"&kategorie=" +
								erg[i].fkKategorieID +">";
					ausgabe += erg[i].unterkategorieBez + " ";
					ausgabe += "</a>";
					
					if(i == erg.length-1){ // letzte UnterKategorie div schließen
						ausgabe += "</div>";
						ausgabe += "</div>";
					}
					
				}
				

				document.getElementById("navigationBar").innerHTML = ausgabe;
				

			}

		}
	}
	xmlhttp.open("GET", "../../GetBezeichnungAjax", true);
	xmlhttp.send();
	
}