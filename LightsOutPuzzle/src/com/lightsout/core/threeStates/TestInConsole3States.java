package com.lightsout.core.threeStates;

import java.util.Scanner;

import com.lightsout.core.twoStates.InitialConfig;

public abstract class TestInConsole3States {

	public static void printMatrix(int[][] matrix) {
		for (int row[] : matrix) {
			for (int elem : row)
				System.out.printf("%1d ", elem);
			System.out.println();
		}
	}

	public static void main(String[] args) {

		
		
		// ******* Test copyOfMatrix method *******
		/*
		int[][] array = InitialConfig.getRandomConfig(5);
		int[][] clone = Solution.copyOfMatrix(array);
		for (int i = 0; i < clone.length; i ++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(System.identityHashCode(array[i]) + "  ");
			}
			System.out.println();
			for (int j = 0; j < clone[i].length; j++){
				System.out.print(System.identityHashCode(clone[i]) + "  ");		    
			}
			System.out.println();
			System.out.println("-----");
		}
		*/
		
		// ******* Test solution*******
		
		Scanner sc = new Scanner(System.in); // to read from console
		boolean flag; // to make input from the console correct
		String str; // user message, entered to console
		/*
		System.out.println("Do you want to generate configuration matrix automatically (y/n)?");
		do {
			str = sc.nextLine();
			if ((!str.equals("y"))&&(!str.equals("n"))) {
				flag = false;
				System.out.println("Please, enter only y (if yes) or n (if not).");
			} else {
				flag = true;
			}
		} while (!flag);
		
		if (str.equals("y")) {*/
			//int[][] conf = {{0,1,1},{0,0,1},{0,0,0}};
			int[][] conf = {{0,1},{1,1}};
			System.out.println("Enter size of the configuration matrix to be generated.");
			int size = sc.nextInt();
			System.out.println("LightsOutMatrix");
			int[][] matrix = Solver3States.getLightsOutMatrix(size);
			printMatrix(matrix);
			System.out.println("Gauss");
			int[][] gauss = Solver3States.makeGaussElimination(matrix);			
			printMatrix(gauss);
			System.out.println("Jordan");
			int[][] jordan = Solver3States.getAugmentedMatrix(Solver3States.getLightsOutMatrix(size), conf);
			jordan = Solver3States.makeJordanGaussElim(jordan);
			printMatrix(jordan);
			System.out.println("Quiet");
			printMatrix(Solver3States.getQuietPatterns(size));
			System.out.println("Sol");
			int[][] conf1 = {{0,2},{2,2}};
			printMatrix(Solver3States.getAllSolutions(conf1));
			System.out.println("Opt");
			printMatrix(Solver3States.getOptimalSolution(conf1));
			/*
			do {
				int size = sc.nextInt();
				if (size > 0) {
					flag = true;
					//new RandomInput(size);
					System.out.print("Please, wait. Looking for solvable initial configuration matrix... ");
					InitialConfig.writeToFile(InitialConfig.getRandomConfig(size));
					System.out.print("Done.\n");
				} else {
					flag = false;
					System.out.println("Please, try again. The size cannot be less than 1.");
				}
			} while (!flag);
			
		}*/
		sc.close();
		/*
		System.out.println("\nThe configuration matrix is:");
		printMatrix(InitialConfig.getConfigFromFile());
		
		System.out.print("Please, wait. Looking for optimal solution... ");
		int[][] optimalSolution = Solver3States.getOptimalSolution(InitialConfig.getConfigFromFile());
		System.out.print("Done.\n");
		
		
		if (optimalSolution == null)
			System.out.println("\nSuch configuration does not have any solutions!");
		else {			
			System.out.println("\nThe optimal solution (" + Solver3States.getStepsOptimalSolution(InitialConfig.getConfigFromFile()) + " steps) is:");
			printMatrix(optimalSolution);
		}*/
		
		
		
		// *******  Test results handling  *******
		/*
		ResultsHandler rh1 = new ResultsHandler(3);
		System.out.println(rh1.isBetweenWinners(new Result("vovan", 122)));
		for (int i = 0; i < 10; i++)
			rh1.updateResultsList(new Result("vovan"+(int)(Math.random()*300)+"", (int)(Math.random()*500)));
		System.out.println(rh1.getResultsList());
		*/
		
		// *******  Test game process  *******
		/*
		int[][] matrix = InitialConfig.getRandomConfig(5);
		GameProcess3States gp = new GameProcess3States(matrix);
		printMatrix(gp.getStartConfigMatrix());
		System.out.println(gp.getOptimalSteps());
		*/
	}
}
