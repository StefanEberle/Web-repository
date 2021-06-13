<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="${pageContext.request.requestURI}/">
<title>Neues Konto erstellen</title>

<script type="text/javascript" src="../../js/bday.js"></script>
<script type="text/javascript" src="../../js/passwortPruefen.js"></script>
<link rel="stylesheet" type="text/css"
	href="../../css/registrierung.css">
</head>
<%@ include file="../jspf/header.jspf"%>

<main>


<h1>Neues Konto anlegen</h1>
<h3>Persönliche Informationen</h3>


<section>
	<form action="../../RegistrierungServlet" method="POST" id="registrieren">
		<h2>Registrieren</h2>

		<fieldset class="user">


			<input type="text" name="email" id="email" size="60" maxlength="60" placeholder="Email Adresse" onchange="loadEmailVerfuegbar()"> 
			<label id="emailTest"> </label> 
			<br> 
			<input type="password" name="passwort" id="pw" pattern=".{8,}" 
						title="Eight or more characters" size="60" maxlength="60" placeholder="Passwort">
						
			<!-- https://www.w3schools.com/tags/att_input_pattern.asp -->
			<!-- onchange="pwRules(this)"  
			
			pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"  -->
			
			
			<br> 
			<input type="password" name="passwort2" id="repeatPW" pattern=".{8,}"
					   title="Eight or more characters" size="60" maxlength="60" placeholder="Passwort wiederholen">
			<!-- disabled="disabled" --> 
			<label id="meldungPW2"> </label>
			
		</fieldset>
		
		<fieldset class="adresse">
			<input type="text" name="vorname" size="30" maxlength="40" placeholder="Vorname"> 
			<br> 
			<input type="text" name="nachname" size="30" maxlength="40" placeholder="Nachname">

			<div class="geb">
				<p>Geburstag:</p><br>
				<select id="jahr" name="jahr">
						 <option>Jahr</option>
				</select>
				
				<select id="monat" name="monat">
						<option>Monat</option>
						<option value="1">January</option>
						<option value="2">February</option>
						<option value="3">March</option>
						<option value="4">April</option>
						<option value="5">May</option>
						<option value="6">June</option>
						<option value="7">July</option>
						<option value="8">August</option>
						<option value="9">September</option>
						<option value="10">October</option>
						<option value="11">November</option>
						<option value="12">December</option>
				</select>
				
				<select id="tag" name="tag">
						<option>Tag</option>
				</select>
			</div>
			

			<input type="text" name="strasse" size="30" maxlength="40" placeholder="Straße"> 
			<br> 
			<input type="text" name="hausnummer" size="5" maxlength="5" placeholder="Nr.">
			<br> 
			<input type="number" name="plz" size="8" min="1" max="99999" maxlength="5" placeholder="PLZ">
			<br> 
			<input type="text" name="stadt" size="30" maxlength="40" placeholder="Stadt"> 
			<br> 
			<input type="number" name="etage" size="8" min="0" max="9999" maxlength="4" placeholder="Etage"> 
			<br> 
			<input type="number" name="telefonnummer" size="30" min="0" max="9999999999999" maxlength="13" placeholder="Telefonnummer"> 
			<br>
			<textarea name="lieferhinweise" rows="5" cols="40" maxlength="250">Weitere Lieferhinweise</textarea>

			<br>

		</fieldset>
		
		<button type="submit" name="RegistrierungsButton" id="absendeButton"
				value="true" disabled>Absenden</button>
	</form>
</section>


</main>
<%@ include file="../jspf/footer.jspf"%>
</html>