"use strict";
document.addEventListener("DOMContentLoaded", init);


}

function addShoppingCart(artikelID){


	var adresseForm = document.getElementById("mainAdresse");
	adresseForm.style.display = "none";

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
