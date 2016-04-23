package com.lightsout.core.twoStates;

import com.lightsout.core.Solver;

// find all strategies of solutions if exists
// determine optimal solution (the least number of steps)
public class Solver2States extends Solver{

	
	public int[][] getAllSolutions(int[][] configMatrix) {
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
	
	public int[][] getQuietPatterns(int size) {
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
	
	public int[][] makeGaussElimination(int[][] matrix) {
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
	
	public int[][] makeJordanGaussElim(int[][] matrix) {
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
}
