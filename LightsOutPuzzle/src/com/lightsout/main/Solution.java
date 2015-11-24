package com.lightsout.main;

import com.lightsout.two_values.AugmentedMatrix;
import com.lightsout.two_values.GaussTwoStates;
import com.lightsout.two_values.GaussJordanElim;
import com.lightsout.two_values.LightsOutMatrix;

public class Solution {
	private Boolean isSolution;
	private int[][] gaussJordanMatrix;
	private int size = 5;
	
	public Solution() {
		GaussTwoStates geb = new GaussTwoStates(new LightsOutMatrix(size).getLightsOutMatrix());
		GaussJordanElim gje = new GaussJordanElim(new AugmentedMatrix().getAugMatrix());
		this.gaussJordanMatrix = gje.getMatrix();
		if (geb.getRank() == gje.getRank())
			this.isSolution = true;
		else
			this.isSolution = false;
	}
	
	public void findSolutions() {
		if (isSolution) {
			QuietPatterns qp = new QuietPatterns();
			int quantSol = (int)Math.pow(2, qp.getNullity());
			for (int i = 0; i < quantSol; i++) {
				int[] sol = new int[size*size];
				for (int j = 0; j < sol.length; j++) {
					sol[j] = this.gaussJordanMatrix[j][this.gaussJordanMatrix[j].length - 1];
					sol[j] = (sol[j] + qp.getQuietPatterns()[i][j])%2;
				}
				System.out.println("Solution " + (i+1) + " is:");
				printSolution(sol);
			}
		} else {
			System.out.println("The solution of such combination does not exist!");
		}
	}
	
	public void printSolution(int[] sol) {
		for (int i = 0; i < this.size; i++) {
			for (int k = 0; k < this.size; k++) {
				System.out.printf("%1d ", sol[k + i*size]);
			}
			System.out.println();
		}
	}
}
