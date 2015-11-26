package com.lightsout.twoStates;

// Generate lights out matrix, is needed to find solution 
public class LightsOutMatrix {
	private int size;
	private int[][] lightsOutMatrix;

	public LightsOutMatrix(int size) {
		this.size = size;
		lightsOutMatrix = new int [size*size][size*size];
		makeLightsOutMatrix();
	}
	
	public LightsOutMatrix() {
		this.size = InitialConfig.getSize();
		lightsOutMatrix = new int [size*size][size*size];
		makeLightsOutMatrix();
	}
	
	// this method fulfill lights out matrix by 0 and 1
	public void makeLightsOutMatrix() {
		
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
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[][] getLightsOutMatrix() {
		return lightsOutMatrix;
	}

	public void setLightsOutMatrix(int[][] lightsOutMatrix) {
		this.lightsOutMatrix = lightsOutMatrix;
	}
	
}