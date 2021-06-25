"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {
	

	navBar();
	
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

				
					
					if(!katBezListe.includes(erg[i].bezeichnungKat)){
						katBezListe.push(erg[i].bezeichnungKat);
						
						if(tmp < katBezListe.length){ 	//Neue Kategorie in Liste --> div schließen - solange kleiner bis neue Kategorie add

							ausgabe += "</div>";
							ausgabe += "</div>";
							tmp = katBezListe.length; 
						
						}
						
						ausgabe += "<div class="+ "dropdown" + ">";
						ausgabe += "<button + type=" + "submit"+ " "  + "class=" + "dropbtn" +" "+"name=" + "kategorie " + "value="+ erg[i].fkKategorieID + ">";
						ausgabe += erg[i].bezeichnungKat;
						ausgabe += "</button>";
						ausgabe += "<div class=" + "dropdown-content" + ">"; //Für Unterkategorien
					}

					
					ausgabe += "<a href="+ "../../AuswahlArtikelServlet?unterKategorie=" + erg[i].unterkategorieID +"&kategorie=" +
								erg[i].fkKategorieID +">";
					ausgabe += erg[i].unterkategorieBez + " ";
					ausgabe += "</a>";
					
					if(i == erg.length-1){ // letzte UnterKategorie --> div schließen
						ausgabe += "</div>";
						ausgabe += "</div>";
					}
					
				}
				

				document.getElementById("navigationBar").innerHTML = ausgabe;
				

			}

		}
	}
	xmlhttp.open("GET", "../../GetKatUKatAjaxServlet", true);
	xmlhttp.send();
	
}