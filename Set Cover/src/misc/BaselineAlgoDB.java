package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class BaselineAlgoDB {

	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	
	static String USER = "";
	static String PASS = "";
	
	//sort based on decreasing size(in this case, decreasing size of datasets)
	public static class myComp implements Comparator<ArrayList<Integer>>
	{
		public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2)
		{
			return o2.size()-o1.size();
		}
	}
	
	
	ArrayList<String> inp = new ArrayList<String>();
	ArrayList<ArrayList<Integer>> setCoverList = new ArrayList<ArrayList<Integer>>();
	int ans = 0;
	
	private int Greedy()
	{
		long startTime = System.currentTimeMillis();

		String sql = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// STEP 2: Register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			sql = "SELECT INPUT FROM SETCOVER";
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				inp.add(rs.getString("input"));//adding all the inputs to an array
				
			}
		} catch (Exception e1) {
			System.out.println("Invalid credentials!");
			return -1;
		}
		
		System.out.println("With "+inp.size()+" sets");
		
		/**
		 * Iterate over the entire array, and keep adding each line in each row as a separate dataset. 
		 *  Add it to an arraylist called SetCoverList.
		 */
		
		for (int i = 0; i < inp.size(); i++) {

			String s = inp.get(i);
			if(s.length()>0)
			{
				String st[] = s.split(" ");
				ArrayList<Integer> a = new ArrayList<Integer>();

				for (int j = 0; j < st.length; j++) {
					a.add(Integer.parseInt(st[j]));
				}

				setCoverList.add(a);
			}
		}
		
		/**
		 * Iterate over setCoverList until all sets have been covered.
		 * Sort the sets in decreasing order of their size, then choose the biggest set. 
		 * Eliminate all elements present in that set from all the other sets.
		 * Keep repeating until all elements are covered.
		 * Number of such iterations will give the answer to the minimum number of datasets required.
		 */
		while(setCoverList.size()!=0)
		{
			ans++;
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			Collections.sort(setCoverList, new myComp());
			ArrayList<Integer> chosen = setCoverList.get(0);
			System.out.println(ans+". "+chosen);
			setCoverList.remove(0);

			for (int i = 0; i < setCoverList.size(); i++) {
				ArrayList<Integer> next = setCoverList.get(i);
				next.removeAll(chosen);
				if (next.size() != 0)
					temp.add(next);
			}

			setCoverList = new ArrayList<ArrayList<Integer>>(temp);
		}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time for execution : "+totalTime);
		
		return ans;
	}
	
	
	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		System.out.println("\nEnter the username for connecting to database:");
		USER = reader.nextLine();
		System.out.println("Enter the password:");
		PASS = reader.nextLine();
		
		BaselineAlgoDB algoDB = new BaselineAlgoDB();
		int result = algoDB.Greedy();
		if(result != -1)
			System.out.println("The minimum number of sets required is : "+result);
		else
			System.out.println("Some problem while executing!");
	}
}
