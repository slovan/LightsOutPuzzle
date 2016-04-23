package com.lightsout.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public abstract class InitialConfig {
	
	protected Random rand = new Random();
	
	public int[][] getRandomConfig(int size) {
		boolean hasSolution;
		int[][] configMatrix = new int[size][size];
		Solver solver = getSolver();
		
		do {
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					configMatrix[i][j] = genRandomInteger();
			if (solver.getRank(solver.getLightsOutMatrix(size)) == solver.getRank(solver.getAugmentedMatrix(solver.getLightsOutMatrix(size), configMatrix)))
				hasSolution = true;
			else
				hasSolution = false;
		} while (!hasSolution);
		return configMatrix;
	}
	
	public int[][] getRandomConfig(int size, boolean isSolvable) {
		if (isSolvable)
			return getRandomConfig(size);
		else {
			int[][] configMatrix = new int[size][size];
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					configMatrix[i][j] = genRandomInteger();
			return configMatrix;
		}
	}
	
	public int[][] getConfigFromFile() {
		int[][] configMatrix = null; // is read from the file
		int size = 0; // size n of n*n initial configuration matrix
		String str; // variable for reading file strings

		try (BufferedReader br = new BufferedReader(new FileReader("input/input.txt"))) {
			int rowPos = 0; // position of active row in the matrix
			while ((str = br.readLine()) != null) {

				// in case of mistaken empty row
				if (str.equals(""))
					continue;

				// find size of matrix at the 1st step of loop
				// or find quantity of elements in row for controlling
				int countElem = 1;
				for (int i = 0; i < str.length(); i++) {
					if (str.charAt(i) == ',')
						countElem++;
				}

				// initialize configuration matrix (1st step of the loop)
				if (configMatrix == null) {
					size = countElem;
					configMatrix = new int[size][size];
				} else if ((countElem != size) || (rowPos >= size)) // check in
																	// case of
																	// incorrect
																	// input
																	// data
					throw new Throwable("Error of configuration matrix size!");

				int colPos = 0; // position of active column in the matrix

				// fill configuration matrix from file
				for (int i = 0; i < str.length(); i++) {
					if ((str.charAt(i) == '0') || (str.charAt(i) == '1')) {
						if (colPos >= size) // check in case of incorrect input
											// data
							throw new Throwable("Error of reading " + (rowPos + 1) + "th row!");
						configMatrix[rowPos][colPos] = Integer.parseInt(str.substring(i, (i + 1)));
						colPos++;
					}
				}

				if (colPos != size) // check in case of incorrect input data
					throw new Throwable("Error of reading " + (rowPos + 1) + "th row!");
				rowPos++;
			}

		} catch (IOException exc) {
			System.out.println("I/O Error: " + exc);
		} catch (Throwable exc) {
			System.out.println(exc);
		}
		return configMatrix;
	}	
	
	// write any config matrix to input.txt
	public void writeToFile(int[][] configMatrix) {
		String str; // variable used to write matrix in the file
		int size = configMatrix.length;

		try (FileWriter fw = new FileWriter("input/input.txt")) {
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
	
	// random generate integer for filling a random configuration
	protected abstract int genRandomInteger();
	
	protected abstract Solver getSolver();
	
}
