package com.lightsout.two_values;

public class GaussJordanElim {
	private int[][] matrix;

	public GaussJordanElim(int[][] matrix) {
		this.matrix = new GaussElimBin(matrix).getMatrix();
		makeJordanGaussElim();
	}

	public void makeJordanGaussElim() {
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
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

}
