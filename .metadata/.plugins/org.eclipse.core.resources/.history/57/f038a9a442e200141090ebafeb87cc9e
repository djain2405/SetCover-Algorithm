package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class NaiveBaselineAlgoDB {
	
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	
	static String USER = "pgupta7";
	static String PASS = "200063440";
	
	ArrayList<ArrayList<Integer>> setCoverList = new ArrayList<ArrayList<Integer>>();
	static String finalAns = "";
	
	TreeSet<Integer> ts = new TreeSet<Integer>();
	int ans = 0;
	
	private int baselineAlgo()
	{
		long startTime = System.currentTimeMillis();
		
		String sql = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String s = "";
		
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
			
			/**
			 * Adding all rows to an array for processing it later
			 */
			while (rs.next())
			{
				s = rs.getString("input");
				if(s!=null && s.length()>0)
				{
					System.out.println(s);
					String st[] = s.split(" ");
					ArrayList<Integer> a = new ArrayList<Integer>();

					for (int j = 0; j < st.length; j++) {
						a.add(Integer.parseInt(st[j]));
						ts.add(Integer.parseInt(st[j]));
					}

					setCoverList.add(a);
				}
				
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Invalid credentials!");
			return -1;
		}
		
		System.out.println("\n"+setCoverList.size()+" rows...");
		ArrayList<Integer> as = new ArrayList<Integer>(ts);
		TreeMap<Integer, ArrayList<Integer>> tm = new TreeMap<Integer, ArrayList<Integer>>();
	
		
		ICombinatoricsVector<ArrayList<Integer>> initialVector = Factory.createVector(setCoverList);

		for (int i = 1; i <= setCoverList.size(); i++) 
		{

			/**
			 *  Create a simple combination generator to generate i-combinations of the initial vector
			 */
			Generator<ArrayList<Integer>> gen = Factory.createSimpleCombinationGenerator(initialVector, i);

			/**
			 * Process each combination, check whether it has all the datasets
			 * If yes, add it to another set, for further processing
			 */
			
			for (ICombinatoricsVector<ArrayList<Integer>> combination : gen) {
				ArrayList<Integer> a  = new ArrayList<Integer>();
				StringBuilder ans = new StringBuilder("");
				for (int j = 0; j < i; j++) {
					ArrayList<Integer> value = combination.getValue(j);
					a.addAll(value);
					ans.append((j+1)+". "+value.toString()+"\n");
				}
				
				if(a.containsAll(as))
				{
					tm.put(i, a);
					if(finalAns.equals(""))
						finalAns = ans.toString();
				}
			}
		}
		
		/**
		 * Fetching the first entry which should be the least value in the size of the sets.
		 */
		Entry<Integer, ArrayList<Integer>> setCoverAnswer = tm.pollFirstEntry();
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time for execution : "+totalTime/60000+"m and "+(totalTime%60000)/1000+"s");
		
		return setCoverAnswer.getKey();
	}
	
	
	public static void main(String[] args) {
	
		System.out.println("NAIVE BASELINE ALGORITHM\n\n");
		
		/*Scanner reader = new Scanner(System.in);
		System.out.println("\nEnter the username for connecting to database:");
		USER = reader.nextLine();
		System.out.println("Enter the password:");
		PASS = reader.nextLine();*/
		
		NaiveBaselineAlgoDB algo = new NaiveBaselineAlgoDB();
		int result = algo.baselineAlgo();
		
		if(result != -1)
			System.out.println("The minimum number of sets required is : "+result+"\n");
		else
			System.out.println("Some problem while executing!");
		
		System.out.println(finalAns);
	
	}
}
