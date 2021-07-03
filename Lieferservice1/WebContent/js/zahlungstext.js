/* Autor: Olga Ohlsson */

"use strict";
document.addEventListener("DOMContentLoaded", init);

function init(){

var zahlungstext = document.getElementById("unsereBank");
if(zahlungstext !== null){
	zahlungstext.style.display = "none";
}



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