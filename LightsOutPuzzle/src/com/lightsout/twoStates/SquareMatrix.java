package com.lightsout.twoStates;

public class SquareMatrix {
	int[][] squareMatrix;

	public SquareMatrix() {
		int[][] matrix = new int[LightsOutMatrix.size][LightsOutMatrix.size];
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = 0;
			}
		this.squareMatrix = matrix;
	}

	public SquareMatrix(int[][] matrix) {
		this.squareMatrix = matrix;
	}

}
