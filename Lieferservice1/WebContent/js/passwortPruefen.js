"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {

	var passwort = document.getElementById("pw");
	var pw = document.getElementById("pw").value;
	//passwort.addEventListener("change", pwRules);

	var correct = document.getElementById("meldungPW1");
	var repeatPW = document.getElementById("repeatPW");
	repeatPW.addEventListener("change", correctPW);

	
	//document.getElementById("email").addEventListener("change", loadDoc);

}

/*
function pwRules(id) {
	
	var pw = id.value;
	var label = document.getElementById("meldungPW1");
	var sonderzeichen = new RegExp(/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/);
	var inputRepeatPW = document.getElementById("repeatPW");

	if (pw == "") {
		label.innerHTML = "Kein Passwort eingegeben";
		return false;
	} else if (pw.length <= 6) {
		label.innerHTML = "Passwort ist zu kurz";
		return false;
	} else if (pw.length >= 20) {
		label.innerHTML = "Passwort ist zu lang";
		return false;
	} else if (sonderzeichen.test(pw)) {
		label.innerHTML = "Das Passwort darf keine Sonderzeichen enthalten";
		return false;
	} else {
		inputRepeatPW.removeAttribute("disabled");
		label.innerHTML = "";

	}

}
*/

function correctPW() {

	let pw = document.querySelector("#pw");
	let repeatPW = document.querySelector("#repeatPW");
	var status = document.getElementById("meldungPW2");
	var absendeButton = document.getElementById("absendeButton");

	if (pw.value == repeatPW.value) {
		status.innerHTML = "Stimmen überein!";
		absendeButton.removeAttribute("disabled");

	} else {
		status.innerHTML = "Stimmen nicht überein!";
		absendeButton.setAttribute("disabled", "disabled");
	}

}

function loadPwVerfuegbar() {

	
	var email = document.getElementById("email").value;
	

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);

					var x = document.getElementById("emailTest");
					x.innerHTML = erg.originalText;

			}

		}
	}
	xmlhttp.open("POST", "../../DynEmailServlet", true);
	xmlhttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	xmlhttp.send("email=" + email);
}

/*
function aktPW(){
	
	var email = document.getElementById("aktuellesPW").value;
	

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);

					var x = document.getElementById("emailTest");
					x.innerHTML = erg.originalText;

			}

		}
	}
	xmlhttp.open("POST", "../../aktuellesPwServlet", true);
	xmlhttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	xmlhttp.send("aktuellesPW=" + email);
}
*/

