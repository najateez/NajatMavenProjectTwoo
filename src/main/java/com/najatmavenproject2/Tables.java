package com.najatmavenproject2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

public class Tables {
	
	public static void isApiDataTableCreated() {
		
		String url = "jdbc:mysql://localhost:3306/apiobjectchainingdb";
		 String user = "root";
	     String pass = "10@104Ar$"; 
			
	     // can not create table with duplicate column name
		String apiDataTable = "CREATE TABLE ApiData (" 
		        + "apidata_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"  
		        + "common VARCHAR(100)," 
		        + "official VARCHAR(100),"
		        
		        + "cca2 VARCHAR(100),"
		        + "ccn3 VARCHAR(100),"
		        + "cioc VARCHAR(100),"
		        + "independent bit,"
		        + "status VARCHAR(100),"
		        + "unMember bit,"
		        + "name VARCHAR(100),"
		        + "symbol VARCHAR(100),"
		        + "root VARCHAR(100),"
		        + "region VARCHAR(100),"
		        + "subregion VARCHAR(100),"
		        + "eng VARCHAR(100),"
		
		        + "landlocked bit,"
		        + "area float,"
		        + "f VARCHAR(100),"
		        + "m VARCHAR(100),"
		        + "flag VARCHAR(100),"
		        + "googleMaps VARCHAR(100),"
		        + "openStreetMaps VARCHAR(100),"
		        + "population INT,"
		        + "fifa VARCHAR(100),"
		        + "side VARCHAR(100),"
		       
		        + "png VARCHAR(100),"
		        + "svg VARCHAR(100),"
		        
		        + "startOfWeek VARCHAR(100))"; 
		
	        Connection con = null;
	        
	        try {

	            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
	            DriverManager.registerDriver(driver);
	            
	            con = DriverManager.getConnection(url, user,pass);
	            Statement st = con.createStatement();

	            int m = st.executeUpdate(apiDataTable);
	            if (m >=  1) {
	                System.out.println("table apiobjectchaining created successfully : " + apiDataTable);
	                
	            }
	            else {
	                System.out.println("table api not created, try again");
	            }
	            con.close();
	        } catch (Exception ex) {
	            System.err.println(ex);
	  }
	        
	}
	
	
	public static void main(String[] args) {
		
		isApiDataTableCreated();
		
		

	
	}
}
