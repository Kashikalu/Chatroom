package main;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;

@Path("/chats")
public class Kontakte {
	
	
		/**
		 * Funktion um ein json Objekt zu erstellen wenn kein Benutzername oder kein Passwort vorhanden ist
		 * @return 
		 */
		String Failed() {
			JSONObject item = new JSONObject();
			item.put("SUCCES", "Failed");
			return item.toString();
		}
	
		/**
		 * diese Methode dient als Schnittstelle zwischen der Datenbank und dem Chatroom
		 * @param Nachricht ist der Speicher in dem die Nachrichten gespeichert werden 
		 * @param Sender ist der Speicher in dem der Username der Person die eine Nachricht gesendet hat gespeichert wird 		 
		 * @return gibt an ob die Nachricht an das Programm das die Datenbank managed erfolgreich weitergegeben wurden und es werden Daten an die Website übermittelt
		 * @throws JSONException
		 * @throws IOException
		 * @throws SQLException
		 * @throws ClassNotFoundException
		 */
	
		@Path("/neueNachrichten")
		@POST
		@Consumes("application/x-www-form-urlencoded")
		@Produces("application/json")
		public String neuerKontakt(@FormParam("NACHRICHT") String nachricht, @FormParam("SENDER") String sender)  throws JSONException, IOException, SQLException, ClassNotFoundException {
			System.out.println(nachricht+" "+sender);
			if(nachricht.equals("") || sender.equals("") || sender.equals("undefined")) {
				return Failed();
			}
			return DBFunctions.neueNachricht(nachricht, sender);
			
		}
		
		/**
		 * Diese Methode dient dazu alle Daten aus der Tabelle der Nachrichten auszulesen und an die Website weiter zu geben 
		 * @return gibt an ob die Daten erfolgreich ausgelesen und an die Website weiter geschickt wurden und es werden Daten an die Website übermittelt
		 * @throws JSONException
		 * @throws IOException
		 * @throws SQLException
		 * @throws ClassNotFoundException
		 */
		
		@Path("/updaten")
		@GET
		@Consumes("application/x-www-form-urlencoded")
		@Produces("application/json")
		public String suchenKontakteMitID() throws JSONException, IOException, SQLException, ClassNotFoundException {
			return DBFunctions.update();
		}
		
		/**
		 * Diese Methode kann genutzt werden um bestimmte Nachrichten eines Kontaktes zu löschen
		 * @param ID gibt die Identifikationsnummer der zu löschenden Nachricht an 
		 * @return gibt an ob die Nachricht erfolgreich gelöscht wurde und es werden Daten an die Website übermittelt
		 * @throws JSONException
		 * @throws IOException
		 * @throws SQLException
		 * @throws ClassNotFoundException
		 */
		
		@Path("/loeschen")
		@POST
		@Consumes("application/x-www-form-urlencoded")
		@Produces("application/json")
		public String loeschenKontakt(@FormParam("ID") int id)  throws JSONException, IOException, SQLException, ClassNotFoundException {
			return DBFunctions.loeschenNachricht(id);
		}
		
		/**
		 * Diese Methode dient der Datenübertragung zwischen Website und dem Datenbankprogramm und übermittelt Zugangsdaten für das einloggen
		 * @param un Benutzername
		 * @param pswd Passwort
		 * @return gibt an ob die zum einloggen genutzten Daten erfolgreich übermittelt wurden und es werden Daten an die Website übermittelt
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		
		@Path("/login")
		@POST
		@Consumes("application/x-www-form-urlencoded")
		@Produces("application/json")
		public String login(@FormParam("USERNAME") String un, @FormParam("PASSWORD") String pswd) throws ClassNotFoundException, SQLException {
			//System.out.println(un+" "+pswd);
			//if(un != null && !un.trim().isEmpty() && pswd != null && !pswd.trim().isEmpty()) {
				return DBFunctions.login(un,pswd);
			//}
			//return Failed();
		}
		
		/**
		 * Diese Methode dient der Datenübertragung zwischen Website und dem Datenbankprogramm und übermittelt die Zugangsdaten für die Registrierung
		 * @param un Benutzername
		 * @param pswd Passwort
		 * @return gibt an ob die zum registrieren benötigten Daten erfolgreich übermittelt wurden und es werden Daten an die Website übermittelt
		 * @throws ClassNotFoundException
		 * @throws SQLException
		
		 */
		@Path("/regristrieren")
		@POST
		@Consumes("application/x-www-form-urlencoded")
		@Produces("application/json")
		public String regristrieren(@FormParam("USERNAME") String un, @FormParam("PASSWORD") String pswd) throws ClassNotFoundException, SQLException {
			if(un != null && !un.trim().isEmpty() && pswd != null && !pswd.trim().isEmpty()) {
				return DBFunctions.regristrieren(un,pswd);
			}
			return Failed();
		}
		
		/**
		 * Diese Methode dient der Datenübertragung bei der Suche nach einem Benutzerprofil
		 * @param un Benutzername
		 * @return gibt an ob die Suche erfolgreich war und es werden Daten an die Website übermittelt
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		
		@Path("/search")
		@POST
		@Consumes("application/x-www-form-urlencoded")
		@Produces("application/json")
		public String search(@FormParam("USERNAME") String un) throws ClassNotFoundException, SQLException {
			System.out.println(un);
			return DBFunctions.search(un);
		}
		
		/**
		 * Diese Methode dient der Datenübertragung bei Bearbeitung eines Profils
		 * @param un Benutzername
		 * @param age Alter
		 * @param ort Wohnort
		 * @param besch Beschreibung
		 * @return gibt an ob die Bearbeitung erfolgreich war und es werden Daten an die Website übermittelt 
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		
		@Path("/changeprofile")
		@POST
		@Consumes("application/x-www-form-urlencoded")
		@Produces("application/json")
		public String change(@FormParam("USERNAME") String un, @FormParam("AGE") String age, @FormParam("ORT") String ort, @FormParam("BESCH") String besch) throws ClassNotFoundException, SQLException {
			System.out.println(un);
			return DBFunctions.changeProfile(un, age, ort, besch);
		}
		

}
