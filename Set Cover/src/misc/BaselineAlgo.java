package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BaselineAlgo {

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
	
	private int Greedy(String fileName)
	{
		long startTime = System.currentTimeMillis();

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			String s;
			
			while((s=br.readLine())!=null)
			{
				inp.add(s);
			}
		}
		catch(Exception e)
		{
			
		}
		
		System.out.println("For "+fileName+" with "+inp.size()+" sets");
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
		
		while(setCoverList.size()!=0)
		{
			ans++;
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			Collections.sort(setCoverList, new myComp());
			ArrayList<Integer> chosen = setCoverList.get(0);
			System.out.println(ans+". "+chosen+" : "+chosen.size());
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
		System.out.println(new BaselineAlgo().Greedy(args[0]));
		//System.out.println(new BaselineAlgo().Greedy("pu.txt"));
	}
}
