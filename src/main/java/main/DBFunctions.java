package main;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;


import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;

public class DBFunctions {

    public static String username = "sql11400415";
    public static String password = "pBCWtb5mgb";
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://sql11.freemysqlhosting.net/sql11400415";
    
    /**  
	* Diese Methode sendet eine nachricht mit sender an die datenbank
	* @param nachricht :ist die nachricht die hochgeladen werden soll
	* @param sender :ist der name des senders
	* @return gibt den Erfolg des Einfügens in die Datenbank zurück
	* @throws ClassNotFoundException
	* @throws SQLException
    */
	public static String neueNachricht(String nachricht,String sender) throws ClassNotFoundException, SQLException {
		try {
			Class.forName(DB_DRIVER);
			Connection con = DriverManager.getConnection(DB_CONNECTION + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", username, password);
			
			String sql = "INSERT INTO nachrichten (NACHRICHT, SENDER)" +
			        "VALUES (?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, nachricht);
			preparedStatement.setString(2, sender);
			preparedStatement.executeUpdate();
			
			JSONObject item = new JSONObject();
    		item.put("SUCCES", "Succes");
    		
			return item.toString();
		}catch (SQLException e) {
			System.out.println("Error"+e);
		}
		
		JSONObject item = new JSONObject();
		item.put("SUCCES", "Failed");
		return item.toString();
	}
	
	/**
	 * Diese Methode ist zum aktualiesieren der Nachrichten zuständig
	 * @return gibt das json object zurück mit einem array von allen gesendeten nachrichten und den erfolg des ganzen
	 * @throws ClassNotFoundException
	 * @throws JSONException
	 * @throws SQLException
	 */
	
	public static String update() throws ClassNotFoundException , JSONException, SQLException {
		
		System.out.println("test");
		try {
			Class.forName(DB_DRIVER);
			Connection con = DriverManager.getConnection(DB_CONNECTION + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", username, password);
			
			Statement stmt = con.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM nachrichten");
			JSONArray array = new JSONArray();
			
	    	while(rs.next()) {
	    		JSONObject item = new JSONObject();
				
	    		item.put("SUCCES", "Succes");
	    		item.put("ID", rs.getString("ID"));
	    		item.put("NACHRICHT", rs.getString("NACHRICHT"));
	    		item.put("SENDER", rs.getString("SENDER"));
	    		item.put("DATUM", rs.getString("DATUM"));
	    		
	    		array.put(item);

	    	}
	    	JSONObject finish = new JSONObject();
	    	finish.put("arr", array);
	    	return finish.toString();
	    	
		} catch (SQLException e) {
			System.out.println("Error"+e);
		}
		JSONObject json_error = new JSONObject();
		json_error.put("SUCCES", "Failed");
		return json_error.toString();
	}
	
	
    /**  
	* Diese Methode loescht eine nachricht aus der datenbank
	* @param id :ist die id der zu löschenden nachricht
	* @return gibt den Erfolg der Methode in einem json Objekt zurück
	* @throws ClassNotFoundException
	* @throws SQLException
    */
	public static String loeschenNachricht(int id) throws ClassNotFoundException, SQLException {
		try {
			Class.forName(DB_DRIVER);
			Connection con = DriverManager.getConnection(DB_CONNECTION + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", username, password);
			
			String sql = "DELETE FROM nachrichten WHERE ID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			
			JSONObject item = new JSONObject();
    		item.put("SUCCES", "Succes");
    		
			return item.toString();
		}catch (SQLException e) {
			System.out.println("Error"+e);
		}
		
		JSONObject item = new JSONObject();
		item.put("SUCCES", "Failed");
		return item.toString();
	}
	
	/**
	 * Diese Methode dient dazu zu überprüfen ob ein Benutzerkonto exestiert und das damit einhergehenden Einloggens
	 * @param un Benutzername
	 * @param pswd Passwort
	 * @return gibt den Erfolg der Methode sowie den Benutzernamen und die Id in form eines json objektes wieder
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static String login(String un, String pswd) throws ClassNotFoundException, SQLException {
		try {
			Class.forName(DB_DRIVER);
			Connection con = DriverManager.getConnection(DB_CONNECTION + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", username, password);
			
			Statement stmt = con.createStatement();
			ResultSet rs;
			System.out.println(1);
			rs = stmt.executeQuery("SELECT * FROM user");
			JSONObject item = new JSONObject();
			System.out.println(1);
	    	while(rs.next()) {
	    		if(rs.getString("USERNAME").equals(un) && rs.getString("PASSWORD").equals(pswd)) {
	    			item.put("SUCCES", "Succesful");
	    			item.put("USERNAME", rs.getString("USERNAME"));
	    			item.put("ID", rs.getInt("ID"));
	    			System.out.println(item.toString());
	    			return item.toString();
	    		}
	    	
	    	}
		}catch (SQLException e) {
			System.out.println("Error"+e);
		}
		
		JSONObject item = new JSONObject();
		item.put("SUCCES", "Failed");
		return item.toString();
	}
	
	
	/**
	 * Dient dem erstellen eines neuen Benutzerkontos
	 * @param un Benutzername
	 * @param pswd Passwort
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String regristrieren(String un, String pswd) throws ClassNotFoundException, SQLException {
		try {
			System.out.println("Funktioniert");
			Class.forName(DB_DRIVER);
			Connection con = DriverManager.getConnection(DB_CONNECTION + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", username, password);
			
			String sql1 = "INSERT INTO user (USERNAME, PASSWORD)" +
			        "VALUES (?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql1);
			preparedStatement.setString(1, un);
			preparedStatement.setString(2, pswd);
			preparedStatement.executeUpdate();
			
		    
			String sql2 = "CREATE TABLE " + un +
		                   "(BESCHREIBUNG VARCHAR(500), " +
		                   "FREUNDE INT(11), "+
		                   "`ALTER` VARCHAR(3) NOT NULL, "+
		                   "ORT VARCHAR(20) NOT NULL);";
			System.out.println(sql2);
			Statement stmt = con.createStatement();
		    stmt.executeUpdate(sql2);
		    
			String sql2_1 = "INSERT INTO "+un+" (BESCHREIBUNG, FREUNDE, `ALTER`, ORT)" +
			        		"VALUES (?, ?, ?, ?)";
			PreparedStatement prpstmt = con.prepareStatement(sql2_1);
			prpstmt.setString(1, "Beschreibung...");
			prpstmt.setInt(2, 0);
			prpstmt.setString(3, "000");
			prpstmt.setString(4, "Wohnort");
			prpstmt.executeUpdate();
		    
			String sql3 = "CREATE TABLE " + un + "freunde" +
	                   "(Freund VARCHAR(16))";
			Statement stmt3 = con.createStatement();
			stmt3.executeUpdate(sql3);
			
			
			JSONObject item = new JSONObject();
			item.put("SUCCES", "Succesful");
			return item.toString();
		}catch (SQLException e) {
			System.out.println("Error"+e);
		}
		
		JSONObject item = new JSONObject();
		item.put("SUCCES", "Failed");
		return item.toString();
	}
	
	/**
	 * Dient als Algorythmus zum suchen eines bestimmten Benutzerkontos
	 * @param un Benutzername 
	 * @return gibt den Status ob die Funtion ein Erfolg war wieder und die Daten des gesuchten Benutzers (in form eines Json Objektes)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static String search(String un) throws ClassNotFoundException, SQLException {
		try {
			Class.forName(DB_DRIVER);
			Connection con = DriverManager.getConnection(DB_CONNECTION + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", username, password);
			
			JSONObject finish = new JSONObject();
	    		
	    	Statement stmt2 = con.createStatement();
	    	ResultSet rs2;
	    	rs2 = stmt2.executeQuery("SELECT * FROM "+un);
	    	
	    	while(rs2.next()) {
	    		finish.put("USERNAME", un);
	    		finish.put("BESCHREIBUNG", rs2.getString("BESCHREIBUNG"));
	    		finish.put("FREUNDE", rs2.getInt("FREUNDE"));
	    		finish.put("ALTER", rs2.getString("ALTER"));
	    		finish.put("ORT", rs2.getString("ORT"));
	    		finish.put("SUCCES", "Succesful");
	    	}
	    	
	    	System.out.println(finish);
	    	return finish.toString();
	    	}
		
		catch (SQLException e) {
			System.out.println("Error"+e);
		}
		
		JSONObject item = new JSONObject();
		item.put("SUCCES", "Failed");
		return item.toString();
	}
	
	/**
	 * dient dazu die jeweiligen Daten des bestimmten Benutzerprofils zu ändern
	 * @param un Benutzername
	 * @param age Alter
	 * @param ort Wohnort
	 * @param besch Beschreibung
	 * @return gibt den Erfolg des Algorythmus in einem Json Objekt zurück
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static String changeProfile(String un, String age, String ort, String besch) throws ClassNotFoundException, SQLException{
		try {
			Class.forName(DB_DRIVER);
			Connection con = DriverManager.getConnection(DB_CONNECTION + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", username, password);
			
			JSONObject finish = new JSONObject();
	    		
	    	Statement stmt2 = con.createStatement();
	    	stmt2.executeUpdate("TRUNCATE "+un);
	    	
	    	String sql = "INSERT INTO "+un+" (BESCHREIBUNG, FREUNDE, `ALTER`, ORT)" +
			        "VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, besch);
			preparedStatement.setInt(2, 0);
			preparedStatement.setString(3, age);
			preparedStatement.setString(4, ort);
			preparedStatement.executeUpdate();
	    	
	    	finish.put("SUCCES", "Succesful");
	    	System.out.println(finish);
	    	return finish.toString();
	    	}
		
		catch (SQLException e) {
			System.out.println("Error"+e);
		}
		
		JSONObject item = new JSONObject();
		item.put("SUCCES", "Failed");
		return item.toString();
	}
		
}