package com.lightsout.main;

import com.lightsout.twoStates.AugmentedMatrix;
import com.lightsout.twoStates.GaussJordanElim;
import com.lightsout.twoStates.GaussTwoStates;
import com.lightsout.twoStates.LightsOutMatrix;

public class Execution {

	public void printMatrix(int[][] matrix) {
		for (int row[] : matrix) {
			for (int elem : row)
				System.out.printf("%1d ", elem);
			System.out.println();
		}
	}

	public static void main(String[] args) {

		Execution ex = new Execution();

		LightsOutMatrix lom = new LightsOutMatrix(5);
		ex.printMatrix(lom.getLightsOutMatrix());
		System.out.println();

		GaussTwoStates geb = new GaussTwoStates(lom.getLightsOutMatrix());
		System.out.println("The rank of matrix is: " + geb.getRank() + ".");
		ex.printMatrix(geb.getMatrix());
		System.out.println();
		
		AugmentedMatrix augm = new AugmentedMatrix();
		System.out.println("The augmented matrix:");
		ex.printMatrix(augm.getAugMatrix());
		
		GaussJordanElim gje = new GaussJordanElim(augm.getAugMatrix());
		System.out.println("After Gauss-Jordan elimination:");
		ex.printMatrix(gje.getMatrix());
		
		QuietPatterns qp = new QuietPatterns();
		System.out.println("Quiet Patterns:");
		ex.printMatrix(qp.getQuietPatterns());
		
		Solution sol = new Solution();
		sol.findSolutions();

	}
}
