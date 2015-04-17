package com.main;

import java.util.ArrayList;

public class Combinations {
	public ArrayList<ArrayList<Object>> compute(ArrayList<Object> restOfVals) {
		if (restOfVals.size() < 2) {
			ArrayList<ArrayList<Object>> c = new ArrayList<ArrayList<Object>>();
			c.add(restOfVals);
			return c;
		} else {
			ArrayList<ArrayList<Object>> newList = new ArrayList<ArrayList<Object>>();
			for (Object o : restOfVals) {
				// make a copy of the array
				ArrayList<Object> rest = new ArrayList<Object>(restOfVals);
				// remove the object
				rest.remove(o);
				newList.addAll(prependToEach(o, compute(rest)));
			}
			return newList;
		}
	}

	private ArrayList<ArrayList<Object>> prependToEach(Object v,
			ArrayList<ArrayList<Object>> vals) {
		for (ArrayList<Object> o : vals) {
			o.add(0, v);
		}
		return vals;
	}

	public static void main(String args[]) {
		ArrayList<Object> i = new ArrayList<Object>();
		i.add("1");
		i.add("2");
		i.add("3");
		//i.add("4");
		Combinations c = new Combinations();
		ArrayList<ArrayList<Object>> ff = new ArrayList<ArrayList<Object>>();
		ff = c.compute(i);
		System.out.println(ff.size());
		System.out.println(ff);
	}
}