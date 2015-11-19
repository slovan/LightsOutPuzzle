package com.lightsout.two_values;

import com.lightsout.main.EnterMatrix;

public class AugmentedMatrix {
	private int[][] augMatrix;
	
	public AugmentedMatrix(){
		this(new LightsOutMatrix(5).getLightsOutMatrix(), new EnterMatrix().getMatrix());
	}
	
	public AugmentedMatrix(int[][] coeffMatrix, int[][] configMatrix){
		int[] vectorMatrix = makeVectorMatrix(configMatrix);
		this.augMatrix = new int[coeffMatrix.length][coeffMatrix.length + 1];
		makeAugmentedMatrix(coeffMatrix, vectorMatrix);
	}
	
	public AugmentedMatrix(int[][] coeffMatrix, int[] vectorMatrix){
		this.augMatrix = new int[coeffMatrix.length][coeffMatrix.length + 1];
		makeAugmentedMatrix(coeffMatrix, vectorMatrix);
	}
	
	public int[] makeVectorMatrix(int[][] configMatrix){
		int[] vectorMatrix = new int[(int)Math.pow(configMatrix.length, 2)];
		for (int i = 0; i < configMatrix.length; i++)
			for (int j = 0; j < configMatrix[i].length; j++)
				vectorMatrix[i*configMatrix.length + j] = configMatrix[i][j];		
		return vectorMatrix;
	}
	
	public void makeAugmentedMatrix(int[][] coeffMatrix, int[] vectorMatrix){
		for (int i = 0; i < coeffMatrix.length; i++)
			for (int j = 0; j < coeffMatrix[i].length; j++)
				this.augMatrix[i][j] = coeffMatrix[i][j];
		for (int k = 0; k < vectorMatrix.length; k++)
			this.augMatrix[k][this.augMatrix[k].length - 1] = vectorMatrix[k];
	}

	public int[][] getAugMatrix() {
		return augMatrix;
	}

	public void setAugMatrix(int[][] augMatrix) {
		this.augMatrix = augMatrix;
	}
	
}
