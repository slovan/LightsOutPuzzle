package com.lightsout.main;
import com.lightsout.general.GaussElim;

public class Execution {
	
	public void printMatrix(int[][] matrix) {
		for (int row[] : matrix) {
			for (int elem : row)
				System.out.printf("%3d ", elem);
			System.out.println();
		}
	}

	public static void main(String[] args) {
		
		Execution ex = new Execution();
		
		/*LightsOutMatrix lom = new LightsOutMatrix(3);
		int[][] matrixA = lom.getLightsOutMatrix();
		for(int buf[] : matrixA) {
			for(int elem : buf)
				System.out.print(elem + " ");
			System.out.println();
		}
		System.out.println();
		
		GaussElimBin geb = new GaussElimBin(matrixA);
		System.out.println("The rank of matrix is: " + geb.getRank() + ".");
		geb.printMatrix();
		System.out.println();*/
		
		EnterMatrix em = new EnterMatrix();
		int[][] matr = em.getMatrix();
		for(int buf[] : matr) {
			for(int elem : buf)
				System.out.print(elem + " ");
			System.out.println();
		}
		
		GaussElim geb = new GaussElim(matr);
		System.out.println("The rank of matrix is: " + geb.getRank() + ".");
		ex.printMatrix(geb.getMatrix());
		System.out.println();
		
	}
}
