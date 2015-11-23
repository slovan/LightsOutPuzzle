package com.lightsout.main;

import com.lightsout.two_values.GaussJordanElim;
import com.lightsout.two_values.LightsOutMatrix;

public class QuietPatterns {
	private int[][] matrix;
	private int[][] quietPatterns;
	private int size = 5;
	private int nullity;
	
	public QuietPatterns(){
		GaussJordanElim gje = new GaussJordanElim(new LightsOutMatrix(size).getLightsOutMatrix());
		this.matrix = gje.getMatrix();
		this.nullity = size*size - gje.getRank();
		this.quietPatterns = new int[(int)Math.pow(2, nullity)][size*size];
		findQuietPatterns();
	}
		
	public void findQuietPatterns(){
		boolean[] pFlags = new boolean[this.matrix.length];
		for (int i = 0, j = 0; j < this.matrix[i].length; j++) {
			if (this.matrix[i][j] != 0) {
				i++;
				pFlags[j] = true;
			} else {
				pFlags[j] = false;
			}
		}
		int[][] nullMatrix = new int[(int)Math.pow(2, nullity)][nullity];
		for (int i = 0; i < nullMatrix.length; i++) {
			String str = Integer.toBinaryString(i);
			while (nullity > str.length())
				str = "0" + str;
			for (int j = 0; j < nullMatrix[i].length; j++) {
				nullMatrix[i][j] = Integer.parseInt(str.substring(j, j+1));
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
		// fulfill quietPatterns
		for (int i = 0; i < quietPatterns.length; i++) {
			for (int j = 0; j < quietPatterns[i].length; j++) {
				if (pFlags[j]) {
					int sum = 0;
					for (int k = j + 1; k < quietPatterns[i].length; k++) {
						sum += quietPatterns[i][k]*matrix[j][k];
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
