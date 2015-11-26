package com.lightsout.twoStates;

// Add configuration matrix  to lights out matrix
// need to determine if any solution exists
// if yes, prepare augMatrix to find all solutions using Gauss-Jordan elimination
public class AugmentedMatrix {
	private int[][] augMatrix;
	
	public AugmentedMatrix(){
		LightsOutMatrix lom = new LightsOutMatrix();
		int[][] coeffMatrix = lom.getLightsOutMatrix(); // variable of lights out matrix
		this.augMatrix = new int[coeffMatrix.length][coeffMatrix.length + 1];
		int[] vectorMatrix = makeVectorMatrix(InitialConfig.getConfigMatrix());
		makeAugmentedMatrix(coeffMatrix, vectorMatrix);
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
	
	// use this method if the configuration matrix is specified as square matrix
	// makes vector matrix of n*n length
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
