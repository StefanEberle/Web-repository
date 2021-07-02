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
			zahlungstext.style.display="none";
			bankdaten.style.display="block";
			});
			
	var bankeinzug = document.getElementById("bankeinzug");
	bankeinzug.addEventListener("click",function(){
	bankdaten.style.display="none";
	zahlungstext.style.display="block";
	});
	
	//dieserArtikel = document.getElementById("warenkorbArtikel");

	//var deleteArtikel = document.getElementById("deleteArtikel");
	//deleteArtikel.addEventListener("click",function(){
	//dieserArtikel.style.display="none";});
	
	

				
	

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

 var menge = document.getElementById("menge").value;
	 var artikelid = id.value;
	 var data = "menge="+menge+"&artikelID=" + artikelid;
	 
	 alert(menge+" "+artikelid);
	

var xmlhttp = new XMLHttpRequest();
		
			
	xmlhttp.open("POST", "../../AddWarenkorbArtikelAjaxServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);
				

}

function deleteArtikel(id,dieserArtikel){

  var menge = 0;
	 var artikelid = id.value;
	 var data = "menge="+menge+"&artikelID=" + artikelid;
	 
	 alert(menge+" "+artikelid);
	

var xmlhttp = new XMLHttpRequest();
		
			
	xmlhttp.open("POST", "../../AddWarenkorbArtikelAjaxServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);

}

