package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class TestDatabase {
	
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	
	static String USER = "pgupta7";
	static String PASS = "200063440";
	
	private void baselineAlgo(String fileName)
	{
		String sql = "";
		Connection conn = null;
		Statement stmt = null;
		String s = "";
		
		try {
			// STEP 2: Register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			sql = "DROP TABLE SETCOVER";
			stmt.executeQuery(sql);
			
			sql = "CREATE TABLE SETCOVER(INPUT VARCHAR2(100))";
			stmt.executeQuery(sql);

		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));

		
		while((s=br.readLine())!=null)
		{
			sql = "INSERT INTO SETCOVER VALUES('"+s+"')";
			stmt.executeQuery(sql);
		}

			
			
		} catch (Exception e1) {
			System.out.println("Invalid credentials!");
			return;
		}
		
	}
	
	
	public static void main(String[] args) {
	
		System.out.println("NAIVE BASELINE ALGORITHM\n\n");
		
		/*Scanner reader = new Scanner(System.in);
		System.out.println("\nEnter the username for connecting to database:");
		USER = reader.nextLine();
		System.out.println("Enter the password:");
		PASS = reader.nextLine();*/
		
		TestDatabase algo = new TestDatabase();
		algo.baselineAlgo("input2.txt");
	
	}
}
