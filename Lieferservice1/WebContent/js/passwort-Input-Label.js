/* Autor: Stefan Eberle */
"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {

	
	var repeatPW = document.getElementById("repeatPW");
	repeatPW.disabled = true;
	
	document.getElementById("pw").addEventListener("keypress", function() {
		repeatPW.disabled = false;
		});
	
	repeatPW.addEventListener("change", correctPW);


	
}

function correctPW() {

	var pw = document.getElementById("pw").value;
	var repeatPW = document.getElementById("repeatPW").value;
	var status = document.getElementById("meldungPW2");
	

	if (pw === repeatPW) {
		status.innerHTML = "Stimmen überein!";
	} else {
		status.innerHTML = "Stimmen nicht überein!";
	}

	
}



