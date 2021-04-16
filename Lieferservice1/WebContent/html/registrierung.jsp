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
<link rel="stylesheet" type="text/css" href="../../css/registrierung.css">
</head>
<%@ include file="../jspf/header.jsp"%>

<main>





<h1>Neues Konto anlegen</h1>
<h3>Persönliche Informationen</h3>





<%-- 

<form action="../../RegistrierungServlet" method="POST"
	id="registrieren">
	
	<div class="user">

		
			<label>Enail:</label> <input type="text" name="email" id="email" size="60" maxlength="60" placeholder="Email Adresse"> <label id="emailTest"> </label>	<br>  <%-- type auf Email noch wechseln  --%>
<%-- 			
			<label>Passwort:</label><input type="password" name="passwort" id="pw" size="60" maxlength="60" placeholder="Passwort"> <label id="meldungPW1"> </label> <br> 
			<label>Passwort wiederholen:</label><input type="password" name ="passwort2" id="repeatPW" size="60" maxlength="60" placeholder="Passwort wiederholen" disabled="disabled"> <label id="meldungPW2"> </label>
		
	</div>
<table class="adresse">
<tbody>

	<tr>	<td>Vorname: <input type="text" name="vorname" size="30" maxlength="40" placeholder="Vorname"> </td>
	
			<td>Nachname: <input type="text" name="nachname" size="30" maxlength="40" placeholder="Nachname">	</td> </tr>
			
			
	<tr> 	  
	 <td>
	
	 	 <label>Geburtstag:</label> 
						 <div>
								<span>
						 			<label for="jahr">Jahr:</label>
						 			<select id="jahr" name="jahr"></select>
						 				
						 		</span>
						 		
						 		<span>
						 		  <label for="monat">Monat:</label>
						 		  <select id="monat" name="monat">
						 		  	<option value="0">January</option>
        							<option value="1">February</option>
        							<option value="2">March</option>
       								<option value="3">April</option>
        							<option value="4">May</option>
        							<option value="5">June</option>
       							 	<option value="6">July</option>
        							<option value="7">August</option>
        							<option value="8">September</option>
        							<option value="9">October</option>
       							 	<option value="10">November</option>
        							<option value="11">December</option>
						 		  </select>
						 		</span>
						 		
						 		<span>
						 				<label for="tag">Tag:</label>
						 				<select id="tag" name="tag">
						 					<option> </option>
						 				</select>
						 					
						 		</span>
						</div>
	 
	 
	 
	 
	 </td> </tr>
	
	
	<tr>	<td>Straße: <input type="text" name="strasse" size="30" maxlength="40" placeholder="Straße"> </td>	 
	
			<td>Hr: <input type="text" name="hausnummer" size="8" maxlength="10" placeholder="Nr.">	</td></tr>
			
	<tr>	<td>PLZ: <input type="number" name="plz" size="8" maxlength="6" placeholder="PLZ"> </td>	
		
			<td>Stadt: <input type="text" name="stadt" size="30" maxlength="40" placeholder="Stadt"> </td></tr>
			
	<tr>	<td>Etage: <input type="number" name="etage" size="8" maxlength="8" placeholder="Etage"></td></tr>
	
	<tr>	<td>Telefonnummer: <input type="number" name="telefonnummer" size="30" maxlength="40" placeholder="Telefonnummer"></td></tr>
	
	<tr> 	<td> <p>Lieferhinweise</p> <textarea name="lieferhinweise" rows="5" cols="40" maxlength="250">Weitere Lieferhinweise</textarea></td></tr>
	
</tbody>
</table>

<button type="submit" name="RegistrierungsButton" id="absendeButton" value="true" disabled">Absenden</button>
</form>
--%>

<form action="../../RegistrierungServlet" method="POST"
	id="registrieren">
  <h2>Registrieren</h2>
	
	<div class="user">
	
			
			<input type="text" name="email" id="email" size="60" maxlength="60" placeholder="Email Adresse" onchange="loadDoc()"> <label id="emailTest"> </label>
			<br>
			<input type="password" name="passwort" id="pw" size="60" maxlength="60" placeholder="Passwort" onchange="pwRules(this)"> <label id="meldungPW1"> </label>
			<br>
			<input type="password" name ="passwort2" id="repeatPW" size="60" maxlength="60" placeholder="Passwort wiederholen" disabled="disabled"> <label id="meldungPW2"> </label>
	</div>
<div class="adresse">		
<input type="text" name="vorname" size="30" maxlength="40" placeholder="Vorname">
<br>
<input type="text" name="nachname" size="30" maxlength="40" placeholder="Nachname">

 <div class="geb">
 <p>Geburstag:</p>
								<span>
						 <%--		<label for="jahr">Jahr:</label>  --%>	
						 			<select id="jahr" name="jahr">
						 			<option>Jahr</option>
						 			</select>
						 			 
						 				
						 		</span>
						 		
						 		<span>
						 <%--	  <label for="monat">Monat:</label>  --%>	
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
						 		</span>
						 		
						 		<span>
						<%--  				<label for="tag">Tag:</label>  --%>
						 				<select id="tag" name="tag">
						 					<option>Tag</option>
						 				</select>
						 					
						 		</span>
						</div>

<input type="text" name="strasse" size="30" maxlength="40" placeholder="Straße">
<br>
<input type="text" name="hausnummer" size="8" maxlength="10" placeholder="Nr.">
<br>
<input type="number" name="plz" size="8" maxlength="6" placeholder="PLZ">
<br>
<input type="text" name="stadt" size="30" maxlength="40" placeholder="Stadt">
<br>
<input type="number" name="etage" size="8" maxlength="8" placeholder="Etage">
<br>
<input type="number" name="telefonnummer" size="30" maxlength="40" placeholder="Telefonnummer">
<br>
<textarea name="lieferhinweise" rows="5" cols="40" maxlength="250">Weitere Lieferhinweise</textarea>
	
	<br>
     <button type="submit" name="RegistrierungsButton" id="absendeButton" value="true" disabled>Absenden</button>
		
	</div>
</form>


	
</main>
<%@ include file="../jspf/footer.jsp"%>
</html>