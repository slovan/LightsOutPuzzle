package com.lightsout.general;

public class GaussElim {
	protected int[][] matrix;
	private int rank;

	public GaussElim(int[][] matrix) {
		this.matrix = matrix;
	}

	public void makeGaussElim() {
		int valueLCM;
		int valueGCD;
		// cycle of Gauss's elimination
		GaussElim: {
			for (int p = 0, q = 0; p < matrix.length; p++, q++) {
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
					}
					if (!flag)
						if (q == matrix[p].length - 1) // if the last column
							break GaussElim; // every of rest elements are zero
						else // if not the last column
							q++;
				} while (!flag);
				
				// simplify p-th row using GCD:
				valueGCD = Math.abs(matrix[p][q]);
				for (int j = q + 1; j < matrix[p].length; j++)
					valueGCD = GCD.findGCD(valueGCD, Math.abs(matrix[p][j]));
				for (int j = matrix[p].length - 1; j >= q; j--) {
					matrix[p][j] /= valueGCD;
				}
				
				// make all elements below the first non-zero element of p-th
				// row equals to 0:
				for (int i = p + 1; i < matrix.length; i++) {
					if (matrix[i][q] == 0)
						continue;

					valueLCM = LCM.findLCM(Math.abs(matrix[p][q]), Math.abs(matrix[i][q]));
						for (int j = matrix[i].length - 1; j >= q; j--) {
							matrix[i][j] = matrix[i][j] * (valueLCM / matrix[i][q])
									- matrix[p][j] * (valueLCM / matrix[p][q]);
						}
				}
			}
		}
	}

	public void findRank() {
		makeGaussElim();
		int rank = matrix.length;
		for (int i = matrix.length - 1; i >= 0; i--) {
			boolean flag = false;
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != 0)
					flag = true;
			}
			if (!flag)
				rank--;
		}
		this.rank = rank;
	}

	public int getRank() {
		findRank();
		return rank;
	}

	public void printMatrix() {
		for (int a[] : matrix) {
			for (int b : a)
				System.out.print(b + " ");
			System.out.println();
		}
	}
}
