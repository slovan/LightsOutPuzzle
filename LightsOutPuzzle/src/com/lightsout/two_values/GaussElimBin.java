package com.lightsout.two_values;
import com.lightsout.gauss_general.GaussElim;

public class GaussElimBin extends GaussElim {

	public GaussElimBin(int[][] matrix) {
		super(matrix);
	}

	@Override
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
}
