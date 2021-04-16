"use strict";
document.addEventListener("DOMContentLoaded", init);

function init() {

	
}

function addShoppingCart(artikelID){
	

	var menge = document.getElementById("menge").value;
	var sendURL = "artikelID=" + artikelID + "&" + "menge=" + menge;
	
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4) {

			if (xmlhttp.status == 200) {

				var erg = JSON.parse(xmlhttp.responseText);
				var ausgabe = "";

				alert("jo");
			
			}

		}

	}

	xmlhttp.open("POST", "../../WarenkorbServlet", true);
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(sendURL);

	
	
	
	
}