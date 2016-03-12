package com.lightsout.twoStates;

// find all strategies of solutions if exists
// determine optimal solution (the least number of steps)
public class Solution {

	
	public static int[][] getAllSolutions(int[][] configMatrix) {
		int[][] solutions;
		int size = configMatrix.length; // size of the game
		int[][] gaussJordanMatrix = makeJordanGaussElim(getAugmentedMatrix(getLightsOutMatrix(size), configMatrix));

		if (getRank(getLightsOutMatrix(size)) == getRank(getAugmentedMatrix(getLightsOutMatrix(size), configMatrix))) { 
			solutions = getQuietPatterns(size); // generate quiet patterns for the size of game

			// loop to find the solution matrix
			for (int i = 0; i < solutions.length; i++) {
				for (int j = 0; j < solutions[i].length; j++) {
					solutions[i][j] = (gaussJordanMatrix[j][size * size] + solutions[i][j]) % 2;
				}
			}
		} else {
			solutions = null;
		}

		return solutions;
	}

	
	
	
	
	
	public static int[][] getOptimalSolution(int[][] configMatrix) {
		int[][] solutions = getAllSolutions(configMatrix);
		int[][] optimalSolution;
		int size;
		int countMIN; // counter of steps in the optimal solution
		
		if (solutions != null) {
			size = configMatrix.length;
			countMIN = size * size;
			// initialization of optimal solution matrix
			optimalSolution = new int[size][size];
			
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
		return optimalSolution;
	}

	
	
	
	
	
	public static int getStepsOptimalSolution(int[][] configMatrix) {
		int[][] optimalSolution = getOptimalSolution(configMatrix);
		int count = 0; // counter of steps in the optimal solution
		
		if (optimalSolution != null) {
			for (int i = 0; i < optimalSolution.length; i++) {
				for (int j = 0; j < optimalSolution[i].length; j++) {
					if (optimalSolution[i][j] == 1)
						count++;
				}
			}
		}
		return count;
	}
	
	
	
	
	
	public static int[][] getQuietPatterns(int size) {
		int[][] matrix; // matrix of lights out matrix, to be changed with Gauss-Jordan elimination
		int nullity; // the dimension of the null space of the lights out matrix
		int[][] quietPatterns;
		
		matrix = makeJordanGaussElim(getLightsOutMatrix(size));

		nullity = size * size - getRank(getLightsOutMatrix(size));

		quietPatterns = new int[(int) Math.pow(2, nullity)][size * size];
		
		// flag to mark position of dependent variables
		boolean[] pFlags = new boolean[matrix.length];
		for (int i = 0, j = 0; (i < matrix.length) && (j < matrix[i].length); j++) {
			if (matrix[i][j] != 0) {
				i++;
				pFlags[j] = true; // independent variables
			} else {
				pFlags[j] = false; // dependent variables
			}
		}

		// find all combinations of dependent variables in Finite Field of size 2
		// and write them to matrix nullMatrix
		int[][] nullMatrix = new int[(int) Math.pow(2, nullity)][nullity];
		for (int i = 0; i < nullMatrix.length; i++) {
			String str = Integer.toBinaryString(i);
			while (nullity > str.length())
				str = "0" + str;
			for (int j = 0; j < nullMatrix[i].length; j++) {
				nullMatrix[i][j] = Integer.parseInt(str.substring(j, j + 1));
			}
		}

		// include nullMatrix in quietPatterns:
		for (int i = 0; i < quietPatterns.length; i++) {
			for (int j = 0, p = 0; j < quietPatterns[i].length; j++) {
				if (!pFlags[j]) {
					quietPatterns[i][j] = nullMatrix[i][p];
					p++;
				} else {
					quietPatterns[i][j] = 1;
				}
			}
		}

		// fulfill quietPatterns matrix
		for (int i = 0; i < quietPatterns.length; i++) {
			for (int j = 0; j < quietPatterns[i].length; j++) {
				if (pFlags[j]) {
					int sum = 0;
					for (int k = j + 1; k < quietPatterns[i].length; k++) {
						sum = (sum + quietPatterns[i][k] * matrix[j][k]) % 2;
					}
					quietPatterns[i][j] = (2 - sum) % 2;
				}
			}
		}
		return quietPatterns;
	}
	
	
	
	
	
	
	public static int[][] getLightsOutMatrix(int size) {
		int[][] lightsOutMatrix = new int [size*size][size*size];
				
		// fulfill lights out matrix by 0
		for (int i = 0; i < lightsOutMatrix.length; i++) {
			for (int j = 0; j < lightsOutMatrix[i].length; j++) {
				lightsOutMatrix[i][j] = 0;
			}
		}
		
		// fulfill lights out matrix by 1
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int posLOM = i*size + j; // position in lights out matrix
				lightsOutMatrix[posLOM][posLOM] = 1; // main diagonal element
				if (i > 0)
					lightsOutMatrix[posLOM][posLOM - size] = 1;
				if (i < size - 1)
					lightsOutMatrix[posLOM][posLOM + size] = 1;
				if (j > 0)
					lightsOutMatrix[posLOM][posLOM - 1] = 1;
				if (j < size - 1)
					lightsOutMatrix[posLOM][posLOM + 1] = 1;
			}
		}
		return lightsOutMatrix;
	}
	
	
	
	
	public static int[][] getAugmentedMatrix(int[][] coeffMatrix, int[] vectorMatrix){
		int[][] augMatrix = new int[coeffMatrix.length][coeffMatrix.length + 1];
		for (int i = 0; i < coeffMatrix.length; i++)
			for (int j = 0; j < coeffMatrix[i].length; j++)
				augMatrix[i][j] = coeffMatrix[i][j];
		for (int k = 0; k < vectorMatrix.length; k++)
			augMatrix[k][augMatrix[k].length - 1] = vectorMatrix[k];
		return augMatrix;
	}
	
	// use this method if the configuration matrix is specified as square matrix
	// makes vector matrix of n*n length
	public static int[][] getAugmentedMatrix(int[][] coeffMatrix, int[][] configMatrix){
		int[] vectorMatrix = new int[(int)Math.pow(configMatrix.length, 2)];
		for (int i = 0; i < configMatrix.length; i++)
			for (int j = 0; j < configMatrix[i].length; j++)
				vectorMatrix[i*configMatrix.length + j] = configMatrix[i][j];		
		return getAugmentedMatrix(coeffMatrix, vectorMatrix);
	}

	
	
	
	
	public static int[][] makeGaussElimination(int[][] matrix) {
		// cycle of Gauss's elimination
		GaussElim: {
			for (int p = 0, q = 0; (p < matrix.length) && (q < matrix[p].length); p++, q++) {
				// p is row number, q is column number

				// find row with non-zero element of q-th column and make it
				// first in the matrix
				boolean flag = true;
				do {
					if (matrix[p][q] == 0) {
						flag = false;
						for (int i = p + 1; i < matrix.length; i++) {
							if (matrix[i][q] != 0) {
								int[] buf = matrix[i];
								matrix[i] = matrix[p];
								matrix[p] = buf;
								flag = true;
								break;
							}
						}
					} else
						flag = true;
					if (!flag)
						if (q == matrix[p].length - 1) // if the last column
							break GaussElim; // every of rest elements are zero
						else // if not the last column
							q++;
				} while (!flag);

				// make all elements below the first non-zero element of p-th
				// row equals to 0:
				for (int i = p + 1; i < matrix.length; i++) {
					if (matrix[i][q] == 0)
						continue;

					for (int j = matrix[i].length - 1; j >= q; j--) {
						matrix[i][j] = (matrix[i][j] + matrix[p][j]) % 2;
					}
				}
			}
		}
		return matrix;
	}
	
	public static int[][] makeJordanGaussElim(int[][] matrix) {
		matrix = makeGaussElimination(matrix);
		// cycle of Jordano's elimination
		for (int p = matrix.length - 1; p > 0; p--) {
			// p is row number, q is column number
			int q = 0;
			boolean flag = false;
			// find first non-zero element of p-th row equals to 1:
			for (; q < matrix.length; q++) {
				if (matrix[p][q] != 0) {
					flag = true;
					break;
				}
			}
			if (!flag)
				continue; // if all elements of p-th row equal to 0
			// make all elements above the first non-zero element of p-th row
			// equals to 0:
			for (int i = p - 1; i >= 0; i--) {
				if (matrix[i][q] != 0)
					for (int j = matrix[i].length - 1; j >= q; j--) {
						matrix[i][j] = (matrix[i][j] + matrix[p][j]) % 2;
					}
			}
		}
		return matrix;
	}

	public static int getRank(int[][] matrix) {
		int[][] gaussMatrix = makeGaussElimination(matrix);
		int rank = matrix.length;
		
		for (int i = gaussMatrix.length - 1; i >= 0; i--) {
			boolean flag = false;
			for (int j = 0; j < gaussMatrix[i].length; j++) {
				if (gaussMatrix[i][j] != 0)
					flag = true;
			}
			if (!flag)
				rank--;
		}
		return rank;
	}
}
