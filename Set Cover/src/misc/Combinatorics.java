package com.main;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class Combinatorics {

	public static void main(String[] args) {

		// Create the initial vector
		ICombinatoricsVector<String> initialVector = Factory.createVector(
				new String[] { "red", "black", "white", "green", "blue" } );

		for (int i = 1; i < 5; i++) 
		{

			// Create a simple combination generator to generate 3-combinations of the initial vector
			Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, i);

			// Print all possible combinations
			for (ICombinatoricsVector<String> combination : gen) {
				
				System.out.println(combination.toString());

			}

		}
	}
}