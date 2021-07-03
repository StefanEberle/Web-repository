/* Autor: Stefan Eberle */

"use strict";


function loadEmailVerfuegbar() {

	// Bei Registrierung und konto.jsp geutzt für Verfügbarkeit der Email
	
	var email = document.getElementById("email").value;
	

	var xmlhttp = new XMLHttpRequest(); // Objekt zur Kommunikation mit dem Server
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);

					var x = document.getElementById("emailAvailable");
					x.innerHTML = erg.originalText;

			}

		}
	}
	xmlhttp.open("POST", "../../DynEmailServlet", true); // Verbindungsaufbau zum Server
				//methode , Pfad Skript Programm, Datei aufgerufen wird   & As-/Synchron
	
	//Request Type Content-Type  - explizit Wert  "application/x-www-form-urlencoded" über setRequest Header
	xmlhttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	
	xmlhttp.send("email=" + email); // Austausch Daten über send - Post Pflicht send Methode

}