"use strict";
document.addEventListener("DOMContentLoaded", init);


function init(){

	
	//var warenkorbButton = document.getElementById("warenkorbHinzu");
	//warenkorbButton.addEventListener("click",addArtikel);
	
		$(zahlungsart).click(function(){
}




function zeigeEmail(){
	
	var emailForm = document.getElementById("emailBearbeiten");
	emailForm.style.display = "block";
	
	var pwForm = document.getElementById("pwBearbeiten");
	pwForm.style.display = "none";
	
	var emailButton = document.getElementById("emailButton");
	emailButton.style.display = "none";
	
	var pwButton = document.getElementById("pwButton");
	pwButton.style.display = "block";

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

function zahlungsart(){
var bar = document.getElementById("bar");

var bankeinzug = document.getElementById("bankeinzug");

var rechnung = document.getElementbyID("rechnung");

//Unsere Bankdaten

var zahlungstext = document.querySelectorAll("bankdaten");


// Kontodaten des Kunden
var bankdaten = document.getElementByID("kontodaten");
	bankdaten.style.display = "block";
	
	const zahlungsart = "";
	
	var emailForm = document.getElementById("emailBearbeiten");
	emailForm.style.display = "block";
	
	
	if(bar.checked == true){
		zahlungsart = "zahlungsart=bar";
	}else if(bankeinzug == true){
		zahlungsart = "zahlungsart=bankeinzug";
		bankdaten.style.display = "block";
	}else if(rechnung == true){
		zahlungsart = "zahlungsart=rechnung";
	}
  
	
	var xmlhttp = new XMLHttpRequest();
	
	xmlhttp.open("POST", "../../AuswahlArtikelServlet", false);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(zahlungsart);
	
}
