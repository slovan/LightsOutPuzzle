package com.lightsout.twoStates;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

// Note: This code requires JDK 7 or later.
// Random generation of configuration matrix
public class RandomInput {
	private int size; // attribute of configuration matrix
	private int[][] configMatrix;

	public RandomInput(int size) {
		this.size = size;

		// initialize random generator
		Random rand = new Random();

		// set configuration matrix
		configMatrix = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				configMatrix[i][j] = rand.nextInt(2);

		writeToFile(configMatrix);
	}
	
	// using this constructor existing of solution is assured if isSolvable=true
	public RandomInput(int size, boolean isSolvable){
		this.size = size;

		// initialize random generator
		Random rand = new Random();

		// set configuration matrix
		configMatrix = new int[size][size];
		if (!isSolvable){
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					configMatrix[i][j] = rand.nextInt(2);
		} else {
			GaussTwoStates geb = new GaussTwoStates(new LightsOutMatrix(size).getLightsOutMatrix());
			GaussJordanElim gje;
			boolean hasSolution;
			
			do {
				for (int i = 0; i < size; i++)
					for (int j = 0; j < size; j++)
						configMatrix[i][j] = rand.nextInt(2);
				gje = new GaussJordanElim(new AugmentedMatrix(new LightsOutMatrix(size).getLightsOutMatrix(), configMatrix).getAugMatrix());
				if (geb.getRank() == gje.getRank())
					hasSolution = true;
				else
					hasSolution = false;
			} while (!hasSolution);
		}
		
		writeToFile(configMatrix);
	}

	// write any config matrix to input.txt
	public void writeToFile(int[][] configMatrix) {
		String str; // variable used to write matrix in the file

		try (FileWriter fw = new FileWriter("src/input.txt")) {
			for (int i = 0; i < size; i++) {
				str = String.valueOf(configMatrix[i][0]);
				for (int j = 1; j < size; j++) {
					str += "," + configMatrix[i][j];
				}
				str += "\r\n";
				fw.write(str);
			}
		} catch (IOException exc) {
			System.err.println("I/O Error: " + exc);
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[][] getConfigMatrix() {
		return configMatrix;
	}

	public void setConfigMatrix(int[][] configMatrix) {
		this.configMatrix = configMatrix;
	}

}
