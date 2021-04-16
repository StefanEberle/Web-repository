"use strict";
document.addEventListener("DOMContentLoaded", init);


function init() {

	filterOptionen();
	sucheMarkenAuswahl();
	
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

		//ids.push(child[j].getAttribute("name"));
		child[j].checked = false;
//		alert(child[j].checked);
	}
}


function filterOptionen() {

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				var ausgabe = "";

				const queryString = window.location.search;
				const urlParams = new URLSearchParams(queryString);
				const id = urlParams.get("kategorie");

				for (var i = 0; i < erg.length; i++) {

					if (id == erg[i].fkKategorieID && id != null) {

// ausgabe += "<a>" + erg[i].unterkategorieBez
// + "</a><br>";

						ausgabe += "<a href="
								+ "../../AuswahlArtikelServlet?unterKategorie="
								+ erg[i].unterkategorieID + "&kategorie="
								+ erg[i].fkKategorieID + ">";
						ausgabe += erg[i].unterkategorieBez + " ";
						ausgabe += "</a><br>";
					}
					// if(id2 == erg[i].fkKategorieID && id2 != null){
					// ausgabe += "<a>" + erg[i].unterkategorieBez + "</a><br>";
					// }
				}

				// Kategorie und Unterkategorie und ID von beiden id als value
				// in option und Bezeichnung als auswahl
			}
			document.getElementById("unterKategorie").innerHTML = ausgabe;
		}
	}
	xmlhttp.open("GET", "../../HoleKategorieServlet", true);
	xmlhttp.send();
}

function sucheMarkenAuswahl() {

	
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const id = urlParams.get('kategorie');
	const id2 = urlParams.get("unterKategorie");

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				var ausgabe = "";

				for (var i = 0; i < erg.length; i++) {

					ausgabe += "<p>";
					ausgabe += "<input type=" + "radio " + "name="
							+ "marke " + "value=" + erg[i].originalText +" " + " onchange=nachMarkenFiltern(this.value) " + " />"
							+ erg[i].originalText + "</p>";

				}

				document.getElementById("marken").innerHTML = ausgabe;
			}

		}

	}

	xmlhttp.open("POST", "../../HoleKategorieServlet", true);
	xmlhttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	if (id2 == null) {
		xmlhttp.send("kategorie=" + id);
	}
	if (id2 != null) {
		xmlhttp.send("unterKategorie=" + id2);
	}
}

function filterGebinde(value,boolean){
	
	
	
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
}

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




