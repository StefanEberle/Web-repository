"use strict";
document.addEventListener("DOMContentLoaded", init);


function init(){
	
	//var warenkorbButton = document.getElementById("warenkorbHinzu");
	//warenkorbButton.addEventListener("click",addArtikel);
	
	//var warenkorbAnzahlChange = document.getElementByID("warenkorbAnzahlChange");
	//warenkorbAnzahlChange.addEventListener("change",
	
	
	//Unsere Bankdaten

var zahlungstext = document.getElementById("unsereBank");
zahlungstext.style.display = "none";


// Kontodaten des Kunden
var bankdaten = document.getElementById("kontodaten");
	bankdaten.style.display = "none";
	
	var bar = document.getElementById("bar");
	bar.addEventListener("click", function(){
				bankdaten.style.display="none";
				zahlungstext.style.display="none";
				});
				
	var rechnung = document.getElementById("rechnung");
	rechnung.addEventListener("click",function(){
			zahlungstext.style.display="block";
			bankdaten.style.display="none";
			});
			
	var bankeinzug = document.getElementById("bankeinzug");
	bankeinzug.addEventListener("click",function(){
	bankdaten.style.display="block";
	zahlungstext.style.display="none";
	});

}

function addArtikel(id){


	 var menge = document.getElementById("menge").value;
	 var artikelid = id.value;
	 var data = "menge="+menge+"&artikelID=" + artikelid;
	 
	 alert(menge+" "+artikelid);
	

var xmlhttp = new XMLHttpRequest();
		
			
	xmlhttp.open("POST", "../../AddWarenkorbArtikelServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);
				

}

function addArtikelAjax(id){

//Problem: Er nimmt nicht den neuen Wert her!
var artikelid = id.value;
var diese = artikelid.toString();
alert(diese);
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
		
			
	xmlhttp.open("POST", "../../AddWarenkorbArtikelAjaxServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);
				

}

function deleteArtikel(id){

var artikelid = id.value;

var diese = artikelid.toString();
alert(diese);
	var dieserArtikel = document.getElementById("warenkorbArtikel"+diese).value;


  var menge = 0;
	 var artikelid = id.value;
	 var data = "menge="+menge+"&artikelID=" + artikelid;
	 
	 alert(menge+" "+artikelid);
	

var xmlhttp = new XMLHttpRequest();
		
			
	xmlhttp.open("POST", "../../AddWarenkorbArtikelAjaxServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);

}

