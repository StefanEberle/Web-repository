"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {

	
	var repeatPW = document.getElementById("repeatPW");
	repeatPW.disabled = true;
	
	document.getElementById("pw").addEventListener("click", function() {
		repeatPW.disabled = false;
		});
	
	repeatPW.addEventListener("change", correctPW);

}


function correctPW() {

	var pw = document.getElementById("pw").value;
	var repeatPW = document.getElementById("repeatPW").value;
	var status = document.getElementById("meldungPW2");
	var absendeButton = document.getElementById("absendeButton");

	if (pw === repeatPW) {
		status.innerHTML = "Stimmen überein!";
		absendeButton.removeAttribute("disabled");

	} else {
		status.innerHTML = "Stimmen nicht überein!";
		absendeButton.setAttribute("disabled", "disabled");
	}

	
}

function loadEmailVerfuegbar() {

	
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


