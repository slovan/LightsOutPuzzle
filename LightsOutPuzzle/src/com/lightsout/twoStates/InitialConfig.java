package com.lightsout.twoStates;

import java.io.*;

// Note: This code requires JDK 7 or later.
// This class read initial configuration from the file
public class InitialConfig {
	private int[][] configMatrix; // is read from the file
	private int size; // size n of n*n initial configuration matrix

	public InitialConfig() {

		String str; // variable for reading file strings

		try (BufferedReader br = new BufferedReader(new FileReader("src/input.txt"))) {
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

				// fulfill configuration matrix from file
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

	}

	public int[][] getMatrix() {
		return configMatrix;
	}

	public void setMatrix(int[][] matrix) {
		this.configMatrix = matrix;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
