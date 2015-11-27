package com.lightsout.main;

import java.util.Scanner;

//import com.lightsout.twoStates.AugmentedMatrix;
//import com.lightsout.twoStates.GaussJordanElim;
//import com.lightsout.twoStates.GaussTwoStates;
import com.lightsout.twoStates.InitialConfig;
//import com.lightsout.twoStates.LightsOutMatrix;
//import com.lightsout.twoStates.QuietPatterns;
import com.lightsout.twoStates.RandomInput;
import com.lightsout.twoStates.Solution;

public class Execution {

	public void printMatrix(int[][] matrix) {
		for (int row[] : matrix) {
			for (int elem : row)
				System.out.printf("%1d ", elem);
			System.out.println();
		}
	}

	public static void main(String[] args) {

		Execution ex = new Execution(); // to make possible use printMatrix method
		Scanner sc = new Scanner(System.in); // to read from console
		boolean flag; // to make input from the console correct
		String str; // user message, entered to console
		
		System.out.println("Do you want to generate configuration matrix automatically (y/n)?");
		do {
			str = sc.nextLine();
			if ((!str.equals("y"))&&(!str.equals("n"))) {
				flag = false;
				System.out.println("Please, enter only y (if yes) or n (if not).");
			} else {
				flag = true;
			}
		} while (!flag);
		
		if (str.equals("y")) {
			System.out.println("Enter size of the configuration matrix to be generated.");
			do {
				int size = sc.nextInt();
				if (size > 0) {
					flag = true;
					new RandomInput(size);
				} else {
					flag = false;
					System.out.println("Please, try again. The size cannot be less than 1.");
				}
			} while (!flag);
		}
		sc.close();
		
		Solution sol = new Solution();
		System.out.println("\nThe configuration matrix is:");
		ex.printMatrix(InitialConfig.getConfigMatrix());
		
		/* 
		LightsOutMatrix lom = new LightsOutMatrix();
		System.out.println("\nLights out matrix is: ");
		ex.printMatrix(lom.getLightsOutMatrix());
		*/

		/*
		GaussTwoStates geb = new GaussTwoStates(lom.getLightsOutMatrix());
		System.out.println("\nThe rank of matrix is: " + geb.getRank() + ".");
		ex.printMatrix(geb.getMatrix());
		*/
		
		/*
		AugmentedMatrix augm = new AugmentedMatrix();
		System.out.println("\nThe augmented matrix:");
		ex.printMatrix(augm.getAugMatrix());
		*/
		
		/*
		GaussJordanElim gje = new GaussJordanElim(augm.getAugMatrix());
		System.out.println("\nAfter Gauss-Jordan elimination:");
		ex.printMatrix(gje.getMatrix());
		*/
		
		/*
		QuietPatterns qp = new QuietPatterns();
		System.out.println("\nQuiet Patterns:");
		ex.printMatrix(qp.getQuietPatterns());
		*/
		
		if (sol.getSolutions() == null)
			System.out.println("\nSuch configuration does not have any solutions!");
		else {
			System.out.println("\nAll solutions:");
			ex.printMatrix(sol.getSolutions());
			
			System.out.println("\nThe optimal solution (" + sol.getCountMIN() + " steps) is:");
			ex.printMatrix(sol.getOptimalSolution());
		}

	}
}
