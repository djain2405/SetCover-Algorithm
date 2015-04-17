package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class SetCoverAlgoDB {
	
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	
	static String USER = "pgupta7";
	static String PASS = "200063440";
	
	
	class mycomp implements Comparator<Integer>
	{
		public int compare(Integer o1, Integer o2)
		{
			return o2-o1;
		}
	}
	
	private TreeMap<Integer, ArrayList<ArrayList<Integer>>> setCover = new TreeMap<Integer, ArrayList<ArrayList<Integer>>>(new mycomp());
	TreeSet<Integer> ts = new TreeSet<Integer>();
	private final double p = 2;
	
	private int runAlgo()
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
			 * Gathering all inputs according to K value sets. Since p=2, we will have such sets-
			 *  4–7 : ABCDE, ABDFG
				2–3 : AFG, BCG, GH, EH, CI, AFG
				1   : A, E, I
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

					int kVal = getKValue(a.size()); 
					if(setCover.containsKey(kVal))
					{
						ArrayList<ArrayList<Integer>> al = setCover.get(kVal);
						al.add(a);
						setCover.remove(kVal);
						setCover.put(kVal, al);
					}
					else
					{
						ArrayList<ArrayList<Integer>> al = new ArrayList<ArrayList<Integer>>();
						al.add(a);
						setCover.put(kVal, al);
					}
				}
				
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Invalid credentials!");
			return -1;
		}
		System.out.println();
		System.out.println();
		ArrayList<Integer> chosen = new ArrayList<Integer>();
		int ans = 0;
		
		/**
		 * Processing each division one-by-one.
		 * If any set has to be demoted, it is done in this function itself.
		 */
		
		for (int k = setCover.firstKey(); k >= 0; k--) {
			
			if(!setCover.containsKey(k))
					continue;
			ArrayList<ArrayList<Integer>> aList = setCover.get(k);
			for (int i = 0; i < aList.size(); i++) {
				ArrayList<Integer> next = aList.get(i);
				ArrayList<Integer> nextCopy = new ArrayList<Integer>(next);
				next.removeAll(chosen);
				if(next.size() >= Math.pow(p, k))
				{
					chosen.addAll(next);
					ans++;
					System.out.println(ans+". "+nextCopy);
				}
				else
					if(next.size()!=0)
					{
						int kVal = getKValue(next.size());
						if(setCover.containsKey(kVal))
						{
							ArrayList<ArrayList<Integer>> al = setCover.get(kVal);
							al.add(next);
							setCover.remove(kVal);
							setCover.put(kVal, al);
						}
						else {
							ArrayList<ArrayList<Integer>> al = new ArrayList<ArrayList<Integer>>();
							al.add(next);
							setCover.put(kVal, al);
						}
					}
			}
		}
		
		//Checking
	/*	Collections.sort(chosen);
		ArrayList<Integer> as = new ArrayList<Integer>(ts);
		
		boolean result = true;
		for (int i = 0; i < as.size(); i++) {
			if((int)as.get(i) != (int)chosen.get(i))
			{
				result = false;
			}
		}
		
		System.out.println("Checked with result : "+result);*/
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\nTime for execution : "+totalTime/60000+"m and "+(totalTime%60000)/1000+"s");
		
		return ans;
	}

	/**
	 * Fetching the K value for the size of the set.
	 * @param n
	 * @return
	 */
	int getKValue(int n)
	{
		int ans = 0;
		
		while(n>1)
		{
			n = (int) (n/p);
			ans++;
		}
		
		return ans;
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("SET COVER EFFICIENT ALGORITHM\n\n");
		
	/*	Scanner reader = new Scanner(System.in);
		System.out.println("\nEnter the username for connecting to database:");
		USER = reader.nextLine();
		System.out.println("Enter the password:");
		PASS = reader.nextLine();*/
		
		SetCoverAlgoDB algo = new SetCoverAlgoDB();
		int result = algo.runAlgo();
		if(result != -1)
			System.out.println("The minimum number of sets required is : "+result);
		else
			System.out.println("Some problem while executing!");

	}

}
