package com.najatmavenproject2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Main {

	public static void insertValuesInDbApi() throws IOException, InterruptedException, Exception {

		// URL->Response->String->Define object-> output -> db

		String url = "jdbc:mysql://localhost:3306/apiobjectchainingdb";
		String user = "root";
		String pass = "10@104Ar$";

		HttpClient hClient = HttpClient.newHttpClient();
		HttpRequest requestData = HttpRequest.newBuilder().uri(URI.create("https://restcountries.com/v3.1/all"))
				.build();

		HttpResponse<String> response = hClient.send(requestData, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());

		// Gson gson=new Gson(); //print all data in one line then insert

		Gson gson = new GsonBuilder().setPrettyPrinting().create(); // print all data line by line in pretty shape as
																	// json shape first then insert.

		JsonParser jParser = new JsonParser(); // import from library
		JsonElement jElement = jParser.parse(response.body()); // previous string (up) that json shape. import library
		String jsonString = gson.toJson(jElement);
		System.out.println(jsonString);

		Data[] data = gson.fromJson(jsonString, Data[].class); // the class which contains all classes

		Connection con = null;
		Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
		DriverManager.registerDriver(driver);
		con = (Connection) DriverManager.getConnection(url, user, pass);

		for (Data x : data) {
			String common = x.getName().getCommon();
			String official = x.getName().getOfficial();
			String cca2 = x.getCca2();
			String ccn3 = x.getCcn3();
			String cioc = x.getCioc();
			boolean independent = x.isIndependent();
			String status = x.getStatus();
			boolean unMember = x.isUnMember();
			String name = x.getCurrencies()!= null? Utilities.getCurrencyData(x.getCurrencies()).getName(): "sample";
			String symbol = x.getCurrencies()!= null? Utilities.getCurrencyData(x.getCurrencies()).getSymbol(): "sample";
			String root = x.getIdd().getRoot();
			String region = x.getRegion();
			String subregion = x.getSubregion();
			String eng = x.getLanguages() != null? x.getLanguages().getEng(): "sample";
			boolean landlocked = x.isLandlocked();
			float area = x.getArea();
			String f = x.getDemonyms()!= null? Utilities.getDemonymsData(x.getDemonyms()).getF(): "sample";
			String m = x.getDemonyms()!= null? Utilities.getDemonymsData(x.getDemonyms()).getM(): "sample";
			// String flag = x.getFlag();
			String googleMaps = x.getMaps().getGoogleMaps();
			String openStreetMaps = x.getMaps().getOpenStreetMaps();
			int population = x.getPopulation();
			String fifa = x.getFifa();
			String side = x.getCar().getSide();
			String png = x.getFlags().getPng();
			String svg = x.getFlags().getSvg();
			String startOfWeek = x.getStartOfWeek();

			String SqlQuery = "INSERT INTO ApiData (common,official,cca2,ccn3,cioc,independent,status,unMember,name,symbol,root,region,subregion,eng,landlocked,area,f,m,googleMaps,openStreetMaps,population,fifa,side,png,svg,startOfWeek) VALUES"
					+ " ('" + common + "' ,'" + official + "', '" + cca2 + "','" + ccn3 + "' ,'" + cioc + "',"
					+ independent + ",'" + status + "' , " + unMember + " ,'" + name + "', '" + symbol + "','" + root
					+ "','" + region + "' ,'" + subregion + "', '" + eng + "'," + landlocked + "," + area + ",'" + f
					+ "', '" + m + "','" + googleMaps + "','" + openStreetMaps + "', " + population + " ,'" + fifa
					+ "' ,'" + side + "','" + png + "', '" + svg + "','" + startOfWeek + "')";

			System.out.println(SqlQuery);

			try {
				Statement st = con.createStatement();

				// Executing query
				int m1 = st.executeUpdate(SqlQuery);
				if (m1 >= 0)
					System.out.println("data inserted successfully : " + SqlQuery);
				else
					System.out.println("insertion failed");
				// Closing the connections
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}
		con.close();
	}

	public static void readValuesFromDbApi() throws IOException, InterruptedException {

		String url = "jdbc:mysql://localhost:3306/apiobjectchainingdb";
		String user = "root";
		String pass = "10@104Ar$";

		HttpClient hClient = HttpClient.newHttpClient();
		HttpRequest requestData = HttpRequest.newBuilder().uri(URI.create("https://restcountries.com/v3.1/all"))
				.build();

		HttpResponse<String> response = hClient.send(requestData, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());

		Gson gson = new GsonBuilder().setPrettyPrinting().create(); // print all data line by line in pretty shape as
																	// json shape first then insert.

		JsonParser jParser = new JsonParser();
		JsonElement jElement = jParser.parse(response.body());
		String jsonString = gson.toJson(jElement);
		System.out.println(jsonString);

		Data[] data = gson.fromJson(jsonString, Data[].class);

		Connection con = null;

		try {
			Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			con = (Connection) DriverManager.getConnection(url, user, pass);
			Statement st = con.createStatement();

			String sql = "SELECT * FROM ApiData";
			ResultSet rs = st.executeQuery(sql);

			for (Data x : data) { // for-each
				String common = x.getName().getCommon();
				String official = x.getName().getOfficial();
				String cca2 = x.getCca2();
				String ccn3 = x.getCcn3();
				String cioc = x.getCioc();
				boolean independent = x.isIndependent();
				String status = x.getStatus();
				boolean unMember = x.isUnMember();
				String name = Utilities.getCurrencyData(x.getCurrencies()).getName();
				String symbol = Utilities.getCurrencyData(x.getCurrencies()).getSymbol();
				String root = x.getIdd().getRoot();
				String region = x.getRegion();
				String subregion = x.getSubregion();
				String eng = x.getLanguages().getEng();
				boolean landlocked = x.isLandlocked();
				float area = x.getArea();
				String f = x.getDemonyms().getFra().getF();
				String m = x.getDemonyms().getFra().getM();
				String flag = x.getFlag();
				String googleMaps = x.getMaps().getGoogleMaps();
				String openStreetMaps = x.getMaps().getOpenStreetMaps();
				int population = x.getPopulation();
				String fifa = x.getFifa();
				String side = x.getCar().getSide();
				String png = x.getFlags().getPng();
				String svg = x.getFlags().getSvg();
				String startOfWeek = x.getStartOfWeek();

				System.out.println("common:" + common + ", official: " + official + ", cca2:" + cca2 + ", ccn3:" + ccn3
						+ ", cioc:" + cioc + " " + ", independent:" + independent + ", status:" + status + ",unMember:"
						+ unMember + ", name:" + name + ", symbol:" + symbol + ", root:" + root + ", region:" + region
						+ ", subregion:" + subregion + ", eng:" + eng + ", landlocked:" + landlocked + ", area:" + area
						+ ", f:" + f + ", m:" + ", flag:" + flag + ", googleMaps:" + googleMaps + ", openStreetMaps:"
						+ openStreetMaps + ", population:" + population + ", fifa:" + fifa + ", side:" + side + ", png:"
						+ png + ", svg:" + svg + ", startOfWeek:" + startOfWeek);
			}
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public static void deleteByIdInDbApi() throws IOException, InterruptedException {

		String url = "jdbc:mysql://localhost:3306/apiobjectchainingdb";
		String user = "root";
		String pass = "10@104Ar$";

		HttpClient hClient = HttpClient.newHttpClient();
		HttpRequest requestData = HttpRequest.newBuilder().uri(URI.create("https://restcountries.com/v3.1/all"))
				.build();

		HttpResponse<String> response = hClient.send(requestData, HttpResponse.BodyHandlers.ofString());
//	System.out.println(response.body());

		Gson gson = new Gson();

		JsonParser jParser = new JsonParser();
		JsonElement jElement = jParser.parse(response.body());
		String jsonString = gson.toJson(jElement);
		System.out.println(jsonString); 
		Data[] data = gson.fromJson(jsonString, Data[].class);

		Connection con = null;

		try {
			Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			con = (Connection) DriverManager.getConnection(url, user, pass);
			Statement st = con.createStatement();

			Scanner in = new Scanner(System.in);

			System.out.println("enter id you want to delete:");
			int apiData_id = in.nextInt();

			String sql = "delete from ApiData where apiData_id = ?";

			PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(sql);
			preparedStmt.setInt(1, apiData_id);
			// execute the preparedstatement
			preparedStmt.execute();
			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public static void updateByIdInDbApi() {

		String url = "jdbc:mysql://localhost:3306/apiobjectchainingdb";
		String user = "root";
		String pass = "10@104Ar$";

		HttpClient hClient = HttpClient.newHttpClient();
		HttpRequest requestData = HttpRequest.newBuilder().uri(URI.create("https://restcountries.com/v3.1/all"))
				.build();

		HttpResponse<String> response = null;

		try {
			response = hClient.send(requestData, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();

		JsonParser jParser = new JsonParser();
		JsonElement jElement = jParser.parse(response.body());
		String jsonString = gson.toJson(jElement);
		System.out.println(jsonString);
		Data[] data = gson.fromJson(jsonString, Data[].class);

		Connection con = null;

		try {
			Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			con = (Connection) DriverManager.getConnection(url, user, pass);
			Statement st = con.createStatement();

			Scanner in = new Scanner(System.in);

			System.out.println("Enter the id of the row to update:");
			int apiData_id = in.nextInt();

			String sql = "update ApiData set common = ?, official = ?, cca2 =?, ccn3 =?, cioc=? where apiData_id = ?";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);

			System.out.println("enter common to be updated:");
			String common = in.next();
			System.out.println("enter official to be updated:");
			String official = in.next();
			System.out.println("enter cca2 to be updated:");
			String cca2 = in.next();
			System.out.println("enter ccn3to be updated:");
			String ccn3 = in.next();
			System.out.println("enter cioc to be updated:");
			String cioc = in.next();

			pstmt.setString(1, common);
			pstmt.setString(2, official);
			pstmt.setString(3, cca2);
			pstmt.setString(4, ccn3);
			pstmt.setString(5, cioc);
			pstmt.setInt(6, apiData_id);
			pstmt.executeUpdate();

			String sql2 = "SELECT * FROM ApiData WHERE apiData_id = ?";
			PreparedStatement pstmt2 = (PreparedStatement) con.prepareStatement(sql2);
			pstmt2.setInt(1, apiData_id);
			ResultSet rs = pstmt2.executeQuery();
			if (rs.next()) {
				String commonn = rs.getString("common");
				String officiall = rs.getString("official");
				String cca22 = rs.getString("cca2");
				String ccn33 = rs.getString("ccn3");
				String ciocc = rs.getString("cioc");
				boolean independent = rs.getBoolean("independent");
				String status = rs.getString("status");
				boolean unMember = rs.getBoolean("unMember");
				String name = rs.getString("name");
				String symbol = rs.getString("symbol");
				String root = rs.getString("root");
				String region = rs.getString("region");
				String subregion = rs.getString("subregion");
				String eng = rs.getString("eng");
				boolean landlocked = rs.getBoolean("landlocked");
				float area = rs.getFloat("area");
				String f = rs.getString("f");
				String m = rs.getString("m");
				String flag = rs.getString("flag");
				String googleMaps = rs.getString("googleMaps");
				String openStreetMaps = rs.getString("openStreetMaps");
				int population = rs.getInt("population");
				String fifa = rs.getString("fifa");
				String side = rs.getString("side");
				String png = rs.getString("png");
				String svg = rs.getString("svg");
				String startOfWeek = rs.getString("startOfWeek");

				System.out.println("common:" + commonn + ", official:" + officiall + ", cca2:" + cca22 + ", ccn3:"
						+ ccn33 + ", cioc:" + ciocc + ", independent:" + independent + ", status:" + status
						+ ", unMember:" + unMember + ", name:" + name + ", symbol:" + symbol + ", root:" + root
						+ ", region:" + region + ", subregion:" + subregion + ", eng:" + eng + ", landlocked:"
						+ landlocked + ", landlocked:" + landlocked + ", area:" + area + ", f:" + f + ", m:" + m
						+ ", flag:" + flag + ", googleMaps:" + googleMaps + ", openStreetMaps:" + openStreetMaps
						+ ", population:" + population + ", fifa:" + fifa + ", side:" + side + ", png:" + png + ", svg:"
						+ svg + ", startOfWeek:" + startOfWeek);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws Exception {

		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.println(" Json->api->save the data to db ");
			System.out.println("*******************************");
			System.out.println("Menu:");
			System.out.println("0:insert values of json by api in db table.");
			System.out.println("1:read values from db (Api).");
			System.out.println("2:delete by id in db (Api).");
			System.out.println("3:update by id in db (Api).");
			System.out.println("4:Exit.");
			System.out.println("*******************************");
			System.out.println("Enter a number from menu: ");
			int choice = in.nextInt();

			switch (choice) {
			case 0: {
				insertValuesInDbApi();
				System.out.println("*******************************");
				break;
			}
			case 1: {
				readValuesFromDbApi();
				System.out.println("*******************************");
				break;
			}
			case 2: {
				deleteByIdInDbApi();
				System.out.println("*******************************");
				break;
			}
			case 3: {
				updateByIdInDbApi();
				System.out.println("*******************************");
				break;
			}
			case 4: {
				return;
			}
			default: {
				System.out.println("it is not an option try again");
				System.out.println("*******************************");
			}
			}
		}

	}

}
