package com.lightsout.two_values;
public class LightsOutMatrix {
	public static int size;
	private int[][] matrixC;
	private int[][] matrixI; // identity matrix
	private int[][] lightsOutMatrix;

	public LightsOutMatrix(int size) {
		LightsOutMatrix.size = size;
		setMatrixI();
		setMatrixC();
		setLightsOutMatrix();
	}

	public int[][] getLightsOutMatrix() {
		return lightsOutMatrix;
	}

	public void setLightsOutMatrix() {
		int[][] lightsOutMatrix = new int[size * size][size * size];

		SquareMatrix[][] blockMatrix = new SquareMatrix[size][size];
		for (int i = 0; i < blockMatrix.length; i++)
			for (int j = 0; j < blockMatrix[i].length; j++) {
				if (i == j)
					blockMatrix[i][j] = new SquareMatrix(matrixC);
				else if ((i == j - 1) || (i == j + 1))
					blockMatrix[i][j] = new SquareMatrix(matrixI);
				else
					blockMatrix[i][j] = new SquareMatrix();
			}

		for (int i = 0; i < lightsOutMatrix.length; i++)
			for (int j = 0; j < lightsOutMatrix[i].length; j++) {
				lightsOutMatrix[i][j] = blockMatrix[i / size][j / size].squareMatrix[i % size][j % size];
			}
		this.lightsOutMatrix = lightsOutMatrix;
	}

	public int[][] getMatrixC() {
		return matrixC;
	}

	public void setMatrixC() {
		int[][] matrixC = new int[size][size]; 
		for (int i = 0; i < matrixC.length; i++)
			for (int j = 0; j < matrixC[i].length; j++) {
				if ((i == j) || (i == j - 1) || (i == j + 1))
					matrixC[i][j] = 1;
				else
					matrixC[i][j] = 0;
			}
		this.matrixC = matrixC;
	}

	public int[][] getMatrixI() {
		return matrixI;
	}

	public void setMatrixI() {
		int[][] matrixI = new int[size][size];
		for (int i = 0; i < matrixI.length; i++)
			for (int j = 0; j < matrixI[i].length; j++) {
				if (i == j)
					matrixI[i][j] = 1;
				else
					matrixI[i][j] = 0;
			}
		this.matrixI = matrixI;
	}
}
