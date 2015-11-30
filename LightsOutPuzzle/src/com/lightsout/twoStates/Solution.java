package com.lightsout.twoStates;

// find all strategies of solutions if exists
// determine optimal solution (the least number of steps)
public class Solution {
	private Boolean isSolution; // indicate an existence of any solution
	private int size; // size of the game
	private int[][] gaussJordanMatrix;
	private int[][] solutions; // contain all solutions
	private int[][] optimalSolution;
	private int countMIN; // counter of steps in the optimal solution

	public Solution() {

		new InitialConfig(); // must be run at the beginning of program to set
								// values to its static attributes

		size = InitialConfig.getSize(); // set size of game

		GaussTwoStates geb = new GaussTwoStates(new LightsOutMatrix().getLightsOutMatrix());

		GaussJordanElim gje = new GaussJordanElim(new AugmentedMatrix().getAugMatrix());
		gaussJordanMatrix = gje.getMatrix();

		if (geb.getRank() == gje.getRank())
			isSolution = true;
		else
			isSolution = false;
	}

	public void findSolutions() {
		if (isSolution) {
			QuietPatterns qp = new QuietPatterns(); // generate quiet patterns
													// for the size of game
			solutions = qp.getQuietPatterns(); // set reference to quiet
												// patterns matrix

			// loop to find the solution matrix
			for (int i = 0; i < solutions.length; i++) {
				for (int j = 0; j < solutions[i].length; j++) {
					solutions[i][j] = (gaussJordanMatrix[j][size * size] + solutions[i][j]) % 2;
				}
			}
		} else {
			solutions = null;
		}
	}

	public void findOptimalSolution() {
		if (isSolution) {
			findSolutions();
			// initialization of optimal solution matrix
			optimalSolution = new int[size][size];

			countMIN = size * size; // counter of steps in the optimal
									// solution
			int index = 0; // index of row of the solutions matrix with the
							// optimal solution
			for (int i = 0; i < solutions.length; i++) {
				int count = 0; // counter of steps in the current solution
				for (int j = 0; j < solutions[i].length; j++) {
					if (solutions[i][j] == 1)
						count++;
				}
				if (count < countMIN) {
					countMIN = count;
					index = i;
				}
			}

			// make square matrix from the row of the optimal solution
			for (int i = 0; i < optimalSolution.length; i++) {
				for (int j = 0; j < optimalSolution[i].length; j++) {
					optimalSolution[i][j] = solutions[index][i * size + j];
				}
			}

		} else {
			optimalSolution = null;
		}
	}

	public Boolean getIsSolution() {
		return isSolution;
	}

	public void setIsSolution(Boolean isSolution) {
		this.isSolution = isSolution;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[][] getGaussJordanMatrix() {
		return gaussJordanMatrix;
	}

	public void setGaussJordanMatrix(int[][] gaussJordanMatrix) {
		this.gaussJordanMatrix = gaussJordanMatrix;
	}

	public int[][] getSolutions() {
		return solutions;
	}

	public void setSolutions(int[][] solutions) {
		this.solutions = solutions;
	}

	public int[][] getOptimalSolution() {
		return optimalSolution;
	}

	public void setOptimalSolution(int[][] optimalSolution) {
		this.optimalSolution = optimalSolution;
	}

	public int getCountMIN() {
		return countMIN;
	}

	public void setCountMIN(int countMIN) {
		this.countMIN = countMIN;
	}
}
