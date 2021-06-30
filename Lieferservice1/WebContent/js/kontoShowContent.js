"use strict";
document.addEventListener("DOMContentLoaded", init);


function init(){
	
	// div id emailBearbeiten - nicht sichtbar
	var emailForm = document.getElementById("emailBearbeiten");
	emailForm.style.display = "none";
	
	//Button zum zeigen div id emailBearbeiten -- emailButton dann none - PW Button block
	var emailButton = document.getElementById("emailButton");
	emailButton.addEventListener("click",zeigeEmail);
	
	// div pwBearbeiten none
	var pwForm = document.getElementById("pwBearbeiten");
	pwForm.style.display = "none";
	
	//Button zeigt div 
	var pwButton = document.getElementById("pwButton");
	pwButton.addEventListener("click", zeigePasswort);
	
	
	//adresse verbergen
	var adresseForm = document.getElementById("mainAdresse");
	adresseForm.style.display = "none";
	
	//klick adresseButton
	var adresseButton = document.getElementById("adresseButton");
	adresseButton.addEventListener("click",zeigeAdresse);
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

function zeigePasswort(){
	
	var emailForm = document.getElementById("emailBearbeiten");
	emailForm.style.display = "none";
	
	var pwForm = document.getElementById("pwBearbeiten");
	pwForm.style.display = "block";
	
	var pwButton = document.getElementById("pwButton");
	pwButton.style.display = "none";
	
	var emailButton = document.getElementById("emailButton");
	emailButton.style.display = "block";
}

function zeigeAdresse(){
	
	var adresseForm = document.getElementById("mainAdresse");
	adresseForm.style.display = "block";
	
	var adresseButton = document.getElementById("adresseButton");
	adresseButton.style.display = "none";
}