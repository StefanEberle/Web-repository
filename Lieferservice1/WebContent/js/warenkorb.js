"use strict";

function addArtikel(id){

	 var menge = document.getElementById("menge").value;
	 var artikelid = id.value;
	 var data = "menge="+menge+"&artikelID=" + artikelid;
	 

var xmlhttp = new XMLHttpRequest();
		
			
	xmlhttp.open("POST", "../../AddWarenkorbArtikelServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);
				

}

function addArtikelAjax(id){


var artikelid = id.value;
var diese = artikelid.toString();

 var menge = document.getElementById("menge"+diese).value;
	
	
	 var data = "menge="+menge+"&artikelID=" + artikelid;	 
	 
var xmlhttp = new XMLHttpRequest();


xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				
				var original = document.getElementById("gesamtsumme");
				original.innerHTML = "Gesamtpreis " +erg.originalText + " €";
			
		}
		}
		}
		
			
	xmlhttp.open("POST", "../../ChangeWarenkorbAjaxServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);
				

}

function deleteArtikel(id){

var artikelid = id.value;

var diese = artikelid.toString();

	var dieserArtikel = document.getElementById("warenkorbArtikel"+diese).value;


  var menge = 0;
	 var artikelid = id.value;
	 var data = "menge="+menge+"&artikelID=" + artikelid;
	 

var xmlhttp = new XMLHttpRequest();
		
			
	xmlhttp.open("POST", "../../AddWarenkorbArtikelAjaxServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);

}

