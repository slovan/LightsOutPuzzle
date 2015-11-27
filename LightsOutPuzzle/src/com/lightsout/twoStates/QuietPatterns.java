package com.lightsout.twoStates;

// Quiet patterns - sequences of button presses that will leave the lights unchanged
public class QuietPatterns {
	private int[][] matrix; // matrix of lights out matrix, to be changed with
							// Gauss-Jordan elimination
	private int[][] quietPatterns;
	private int size; // size of the game
	private int nullity; // the dimension of the null space of the lights out
							// matrix

	public QuietPatterns() {
		this.size = InitialConfig.getSize();

		LightsOutMatrix lom = new LightsOutMatrix();
		GaussJordanElim gje = new GaussJordanElim(lom.getLightsOutMatrix());
		this.matrix = gje.getMatrix();

		this.nullity = size * size - gje.getRank();

		this.quietPatterns = new int[(int) Math.pow(2, nullity)][size * size];
		findQuietPatterns();
	}

	public void findQuietPatterns() {
		// flag to mark position of dependent variables
		boolean[] pFlags = new boolean[this.matrix.length];
		for (int i = 0, j = 0; j < this.matrix[i].length; j++) {
			if (this.matrix[i][j] != 0) {
				i++;
				pFlags[j] = true;
			} else {
				pFlags[j] = false;
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
						sum += quietPatterns[i][k] * matrix[j][k];
					}
					quietPatterns[i][j] = (2 - sum) % 2;
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

	public int[][] getQuietPatterns() {
		return quietPatterns;
	}

	public void setQuietPatterns(int[][] quietPatterns) {
		this.quietPatterns = quietPatterns;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNullity() {
		return nullity;
	}

	public void setNullity(int nullity) {
		this.nullity = nullity;
	}

}
