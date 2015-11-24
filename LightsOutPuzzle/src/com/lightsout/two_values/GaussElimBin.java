package com.lightsout.two_values;

public class GaussElimBin {
	private int[][] matrix;
	private int rank;
	private int det; // contain a determinant of the matrix

	public GaussElimBin(int[][] matrix) {
		this.matrix = matrix;
		this.rank = matrix.length;
		this.det = 1;

		makeGaussElim();
		findRank();
		findDet();
	}

	public void makeGaussElim() {
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
	}

	public void findRank() {
		for (int i = matrix.length - 1; i >= 0; i--) {
			boolean flag = false;
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != 0)
					flag = true;
			}
			if (!flag)
				rank--;
		}
	}

	private void findDet() {
		if (rank < matrix.length)
			det = 0;
		else {
			det = 1;
		}
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getDet() {
		return det;
	}

	public void setDet(int det) {
		this.det = det;
	}

}
