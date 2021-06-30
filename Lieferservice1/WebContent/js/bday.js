"use strict";
document.addEventListener("DOMContentLoaded", init);

	
var counterMonUndTag = 0;


function init() {
	
	//Aktuelles Jahr
	selectedJahr();
	
	if(counterMonUndTag === 0){ //nach auswahl Jahr sind Monat und Tag auswählbar
		hiddenMonUndTag();
	}

	var jahr = document.getElementById("jahr");
	jahr.addEventListener("change", hiddenMonUndTag);
	jahr.addEventListener("change", selectedTag);
	
	var monat = document.getElementById("monat");
	monat.addEventListener("change", selectedTag);

	
	var tag = document.getElementById("tag");
	var absendeButton = document.getElementById("absendeButton");
	
	monat.addEventListener("change",function(){
		if(jahr.value > 1949 && monat.value > 0 && tag.value > 0){
			absendeButton.removeAttribute("disabled");
		}else{
			absendeButton.setAttribute("disabled", "disabled");
		}
	});
	
	
	jahr.addEventListener("change",function(){
		if(jahr.value > 1949 && monat.value > 0 && tag.value > 0){
			absendeButton.removeAttribute("disabled");
		}else{
			absendeButton.setAttribute("disabled", "disabled");
		}
	});
	
	
	tag.addEventListener("change",function(){
		if(jahr.value > 1949 && monat.value > 0 && tag.value > 0){
			absendeButton.removeAttribute("disabled");
		}else{
			absendeButton.setAttribute("disabled", "disabled");
		}
	});

	
	
}

function hiddenMonUndTag(){ //Jahr ausgewählt counter++
	
	if(counterMonUndTag===1){
		document.getElementById("monat").disabled = false;
		document.getElementById("tag").disabled = false;
	}else{
		document.getElementById("monat").disabled = true;
		document.getElementById("tag").disabled = true;
		counterMonUndTag = counterMonUndTag+1;
	}
	
}



function selectedTag(){
	
	var tage = document.getElementById("tag");
	var valueMonat = monat.options[monat.selectedIndex].value;
	var valueJahr = jahr.options[jahr.selectedIndex].value;

	var days;

	var array31 = ["1", "3", "5", "7", "8", "10", "12"];
	var array30 = ["4", "6", "9", "11"];
	
	
	if(array31.includes(valueMonat)){
		days = 31;
		
	}else if (array30.includes(valueMonat)){
		days = 30;
	}else{
		const date29 = new Date(valueJahr,1,29); // (bei Schaltjahr - Jahr,Feb,29) (Sonst: Jahr, Mar, 01) 
	
		if(date29.getMonth() == 2){ // == 2 bei keinem SJ // == 1 bei SJ
			days = 28;
		}else{
			days = 29;
		}
	}
	
	var options = "";
	for(var i = 1; i <= days; i++){
		options += "<option geburtstag=" + days + ">" + i + "</option>";
	}
	tage.innerHTML = options;
}

function selectedJahr(){ // selected von 1950 bis aktuelles Jahr füllen
	
	
	var end = new Date().getFullYear();
	var options = "";
	for(var year = 1950 ; year <= end; year++){
		if(year === 1950){
			options += "<option>" +"Jahr"+"</option>";
		}
	  options += "<option geburtsjahr=" + year + ">" + year +"</option>";
	}
	jahr.innerHTML = options;
	
	
}




