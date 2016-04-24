package com.lightsout.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.lightsout.core.threeStates.Solver3States;
import com.lightsout.core.twoStates.Solver2States;

public class InitialConfig {

	private int sizeOfGame;
	private int quantityOfStates;
	private Solver solver;
	private Random rand;

	public InitialConfig(int sizeOfGame, int quantityOfStates) {
		this.sizeOfGame = sizeOfGame;
		this.quantityOfStates = quantityOfStates;
		this.rand = new Random();
		switch (quantityOfStates) {
		case 2:
			this.solver = new Solver2States();
			break;
		case 3:
			this.solver = new Solver3States();
			break;
		default:
			this.solver = new Solver2States();
			this.quantityOfStates = 2;
			break;
		}
	}

	public int[][] getRandomConfig() {
		boolean hasSolution;
		int[][] configMatrix = new int[sizeOfGame][sizeOfGame];

		do {
			for (int i = 0; i < sizeOfGame; i++)
				for (int j = 0; j < sizeOfGame; j++)
					configMatrix[i][j] = rand.nextInt(quantityOfStates);
			if (solver.getRank(solver.getLightsOutMatrix(sizeOfGame)) == solver
					.getRank(solver.getAugmentedMatrix(solver.getLightsOutMatrix(sizeOfGame), configMatrix)))
				hasSolution = true;
			else
				hasSolution = false;
		} while (!hasSolution);
		return configMatrix;
	}

	public int[][] getRandomConfig(boolean isSolvable) {
		if (isSolvable)
			return getRandomConfig();
		else {
			int[][] configMatrix = new int[sizeOfGame][sizeOfGame];
			for (int i = 0; i < sizeOfGame; i++)
				for (int j = 0; j < sizeOfGame; j++)
					configMatrix[i][j] = rand.nextInt(quantityOfStates);
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

				String[] strData = str.split(" ");// get numbers from row of file in string array
				int countElem = strData.length;// length of data in row of file

				
				if (configMatrix == null) {
					// initialize configuration matrix (1st step of the loop)
					size = countElem;
					configMatrix = new int[size][size];
				} else if ((countElem != size) || (rowPos >= size)) // check in
																	// case of
																	// incorrect
																	// input
																	// data
					throw new Throwable("Error of configuration matrix size!");

				// fill configuration matrix from file
				for (int i = 0; i < countElem; i++) {
					int element = Integer.parseInt(strData[i]);
					if (element >= 0 && element < this.quantityOfStates)
						configMatrix[rowPos][i] = element;
					else
						throw new Throwable("Error of reading " + (rowPos + 1) + "th row!");
				}
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
					str += " " + configMatrix[i][j];
				}
				str += "\r\n";
				fw.write(str);
			}
		} catch (IOException exc) {
			System.err.println("I/O Error: " + exc);
		}
	}

}
