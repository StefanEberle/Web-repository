"use strict";
document.addEventListener("DOMContentLoaded", init);


function init() {
	/* Asynchron laden */
	
	
	
	unterKategorieFilter();
	
	
	sucheMarkenAuswahl();
	
	/*
	var glas = document.getElementById("filterGlas");
	var pet = document.getElementById("filterPet");
	glas.checked = true;
	pet.checked = true;
	
	glas.addEventListener("change",filterGebinde);
	pet.addEventListener("change",filterGebinde);
	*/
	
	/*
	var cbGlas = document.getElementById("glas");
	cbGlas.checked = true;
	var cbPet = document.getElementById("pet");
	cbPet.checked = true;

	cbGlas.addEventListener("change", function(){
		if(this.checked){
			filterGebinde(cbGlas.value,this.checked);
		}else{
			filterGebinde(cbGlas.value,this.checked);
		}
	});
	
	cbPet.addEventListener("change", function(){
		if(this.checked){
			filterGebinde(cbPet.value,this.checked);
		}else{
			filterGebinde(cbPet.value,this.checked);
		}
	});
	
	
	var child = document.querySelectorAll("div[name]");
	
	for(var j = 0, len = child.length; j < len; j++){
		child[j].checked = false;
	}
	*/
}


//Aside Filter
function unterKategorieFilter() {
	
	var id = document.getElementById("filterKategorie").value;

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				var ausgabe = "";

				// https://stackoverflow.com/questions/901115/how-can-i-get-query-string-values-in-javascript
//				const queryString = window.location.search;
//				const urlParams = new URLSearchParams(queryString);
//				const id = urlParams.get("kategorie");
				
				
				
				for (var i = 0; i < erg.length; i++) {
					
					//KategorieID gehörige Unterkategorie hinzufügen
					if (id == erg[i].fkKategorieID && id != null) {

						ausgabe += "<a href="
								+ "../../AuswahlArtikelServlet?unterKategorie="
								+ erg[i].unterkategorieID + "&kategorie="
								+ erg[i].fkKategorieID + ">";
						ausgabe += erg[i].unterkategorieBez + " ";
						ausgabe += "</a><br>";
					}
					
				}

				// Kategorie und Unterkategorie und ID von beiden id als value
				// in option und Bezeichnung als auswahl
			}
			document.getElementById("unterKategorie").innerHTML = ausgabe;
		}
	}
	xmlhttp.open("GET", "../../GetBezeichnungAjax", true);
	xmlhttp.send();
}
function sucheMarkenAuswahl() {

	
//	const queryString = window.location.search;
//	const urlParams = new URLSearchParams(queryString);
//	const id = urlParams.get('kategorie');
//	const id2 = urlParams.get("unterKategorie");
	var id = document.getElementById("filterKategorie").value;
	var id2 = document.getElementById("filterUnterKategorie").value;

	var isEquals = "nonValue";
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				var ausgabe = "";

				for (var i = 0; i < erg.length; i++) {
					
					var string = erg[i].originalText.replace(/\s+/g, '_');
					
					if(i == 0){
						ausgabe += "<select name="+ "marken" + " method=" + "POST " + "size=" + "6 " + ">";
					}
					
					ausgabe += "<option " + "value=" + string + " />"
							+ erg[i].originalText + "</option>";

				}
				   ausgabe += "</select>";
				document.getElementById("marken").innerHTML = ausgabe;
			}

		}

	}

	xmlhttp.open("POST", "../../sucheAjax", true);
	xmlhttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	if (id2 == isEquals) {
		xmlhttp.send("kategorie=" + id);
	}
	if (id2 != isEquals) {
		xmlhttp.send("unterKategorie=" + id2);
	}
}




/**
function filterGebinde(){
	
	var glas = document.getElementById("filterGlas");
	var pet = document.getElementById("filterPet");
	const gebinde = "";
	
	if(glas.checked == false){
		gebinde = "gebinde=PET";
	}else if(pet.checked == false){
		gebinde = "gebinde=Glas";
	}else if(glas.checked == true && pet.checked == true){
		gebinde = "gebinde=allSelected";
	}else if(glas.checked == false && pet.checked == false){
		gebinde = "NULL";
	}
	
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	
	const kategorieID = urlParams.get("kategorie");
	const unterKategorieID = urlParams.get("unterKategorie");
	

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				var ausgabe = "";

				
				
				for (var i = 0; i < erg.length; i++) {

					if (id == erg[i].fkKategorieID && id != null) {

						ausgabe += "<a href="
								+ "../../AuswahlArtikelServlet?unterKategorie="
								+ erg[i].unterkategorieID + "&kategorie="
								+ erg[i].fkKategorieID + ">";
						ausgabe += erg[i].unterkategorieBez + " ";
						ausgabe += "</a><br>";
					}
					
				}

				// Kategorie und Unterkategorie und ID von beiden id als value
				// in option und Bezeichnung als auswahl
			}
			document.getElementById("unterKategorie").innerHTML = ausgabe;
		}
	}
	xmlhttp.open("POST", "../../AuswahlArtikelServlet", false);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send();
	
}
**/

/*
var elemente = document.querySelectorAll("."+value);

for (var i = 0; i < elemente.length; i++) {
    if(boolean == false){
	elemente[i].style.display = "none";
    }
    if(boolean == true){
    	elemente[i].style.display = "block";
    }
  }

var marke = document.getElementsByName("marke");
for(var i = 0; i < marke.length; i++){
	marke[i].disabled = true;
}
*/

/* 
function nachMarkenFiltern(value){
	
//	var marke = document.getElementById(value);
	
	var gebinde = document.getElementsByName("gebinde");
	for(var i = 0; i < gebinde.length; i++){
		gebinde[i].disabled = true;
	}
	

	var ids = [];
	
	var child = document.querySelectorAll("div[name]");

	for(var j = 0, len = child.length; j < len; j++){
		
		ids.push(child[j].getAttribute("name"));
		
		if(child[j].checked == true){
			child[j].checked = false;
			continue;
		}
	
		if(ids[j] == value){
			child[j].checked = true;
		}
	}
	
	
	
	for(var i = 0; i < child.length; i++){
		
		if(ids[i] == value && child[i].checked == true){
			child[i].style.display = "block";
			
		}
		if(ids[i] != value && child[i].checked == false){
			child[i].style.display = "none";
		}
		if(ids[i] == value && child[i].checked == false){
			for(var j = 0; j < child.length; j++){
			child[j].style.display = "block";
			}
			break;
		}
	}
	
	
	
}
*/






