package misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class NaiveBaselineAlgo {
	
	ArrayList<ArrayList<Integer>> setCoverList = new ArrayList<ArrayList<Integer>>();
	static String finalAns = "";
	
	TreeSet<Integer> ts = new TreeSet<Integer>();
	int ans = 0;
	
	private int baselineAlgo(String fileName)
	{
		long startTime = System.currentTimeMillis();
		System.out.println("For "+fileName);

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			String s;
			
			while((s=br.readLine())!=null)
			{
				if(s.length()>0)
				{
					String st[] = s.split(" ");
					ArrayList<Integer> a = new ArrayList<Integer>();

					for (int j = 0; j < st.length; j++) {
						a.add(Integer.parseInt(st[j]));
						ts.add(Integer.parseInt(st[j]));
					}

					setCoverList.add(a);
				}
			}
		}
		catch(Exception e)
		{
			
		}
		System.out.println(setCoverList.size()+" rows...");
		ArrayList<Integer> as = new ArrayList<Integer>(ts);
		TreeMap<Integer, ArrayList<Integer>> tm = new TreeMap<Integer, ArrayList<Integer>>();
		
		ICombinatoricsVector<ArrayList<Integer>> initialVector = Factory.createVector(setCoverList);

		for (int i = 1; i <= setCoverList.size(); i++) 
		{
		
			// Create a simple combination generator to generate 3-combinations of the initial vector
			Generator<ArrayList<Integer>> gen = Factory.createSimpleCombinationGenerator(initialVector, i);

			// Print all possible combinations
			/*for (int j = 0; j < gen.getNumberOfGeneratedObjects(); j++) {

				ICombinatoricsVector<ArrayList<Integer>> combination = gen.getOriginalVector();
				ArrayList<Integer> value = combination.getValue(j);
				System.out.println(value.toString());
			}*/
			for (ICombinatoricsVector<ArrayList<Integer>> combination : gen) {
				ArrayList<Integer> a  = new ArrayList<Integer>();
				StringBuilder ans = new StringBuilder("");
				for (int j = 0; j < i; j++) 
				{
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
		
		Entry<Integer, ArrayList<Integer>> setCoverAnswer = tm.pollFirstEntry();
		//System.out.println(setCoverAnswer.getValue());
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time for execution : "+totalTime/60000+"m and "+(totalTime%60000)/1000+"s");
		
		return setCoverAnswer.getKey();
	}
	
	
	public static void main(String[] args) {
		//System.out.println(new CopyOfBaselineAlgo().Greedy(args[0]));
		System.out.println(new NaiveBaselineAlgo().baselineAlgo("input.txt"));
		System.out.println(finalAns);
	}
}
