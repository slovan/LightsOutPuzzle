package com.lightsout.core;

import com.lightsout.core.threeStates.Solver3States;
import com.lightsout.core.twoStates.Solver2States;

public class GameProcess {

	private int quantityOfStates;
	protected int userSteps; // quantity of user's steps
	private int timesOfShowingSolution;
	private int score; // total score
	private int optimalSteps;
	private int[][] startConfigMatrix;
	protected int[][] changedConfigMatrix;
	private int[][] currentOptimalSolution;
	private Solver solver;

	public GameProcess(int[][] startConfigMatrix, int quantityOfStates) {
		this.quantityOfStates = quantityOfStates;
		switch (quantityOfStates) {
		case 3:
			this.solver = new Solver3States();
			break;
		case 2:
			this.solver = new Solver2States();
			break;
		}
		this.startConfigMatrix = startConfigMatrix;
		this.changedConfigMatrix = solver.copyOfMatrix(startConfigMatrix);
		this.userSteps = 0;
		this.score = 0;
		this.timesOfShowingSolution = 0;
		this.currentOptimalSolution = null;
		this.optimalSteps = solver.getStepsOptimalSolution(startConfigMatrix);
	}

	public void computeScore() {
		float coeff1 = 0.99f;
		float coeff2 = 0.5f;
		int coeffPow1 = this.userSteps - this.optimalSteps;
		int coeffPow2 = this.timesOfShowingSolution;
		int maxScore = (int) Math.pow(this.startConfigMatrix.length * 10, quantityOfStates);

		this.score = (int) (maxScore * Math.pow(coeff1, coeffPow1) * Math.pow(coeff2, coeffPow2));
	}

	public void makeOneStep(int row, int column) {
		if (row >= changedConfigMatrix.length || column >= changedConfigMatrix.length || row < 0 || column < 0)
			return;
		changedConfigMatrix[row][column] = ++changedConfigMatrix[row][column] % this.quantityOfStates;
		if (row - 1 >= 0)
			changedConfigMatrix[row - 1][column] = ++changedConfigMatrix[row - 1][column] % this.quantityOfStates;
		if (row + 1 < changedConfigMatrix.length)
			changedConfigMatrix[row + 1][column] = ++changedConfigMatrix[row + 1][column] % this.quantityOfStates;
		if (column - 1 >= 0)
			changedConfigMatrix[row][column - 1] = ++changedConfigMatrix[row][column - 1] % this.quantityOfStates;
		if (column + 1 < changedConfigMatrix.length)
			changedConfigMatrix[row][column + 1] = ++changedConfigMatrix[row][column + 1] % this.quantityOfStates;
		this.userSteps++;
	}

	public void findCurrentSolution() {
		this.timesOfShowingSolution++;
		this.currentOptimalSolution = solver.getOptimalSolution(changedConfigMatrix);
	}

	public int getUserSteps() {
		return userSteps;
	}

	public int getScore() {
		return score;
	}

	public int getOptimalSteps() {
		return optimalSteps;
	}

	public int[][] getStartConfigMatrix() {
		return startConfigMatrix;
	}

	public int[][] getChangedConfigMatrix() {
		return changedConfigMatrix;
	}

	public int[][] getCurrentSolution() {
		return currentOptimalSolution;
	}

	public int getTimesOfShowingSolution() {
		return timesOfShowingSolution;
	}

	public void setTimesOfShowingSolution(int timesOfShowingSolution) {
		this.timesOfShowingSolution = timesOfShowingSolution;
	}

	public void setUserSteps(int userSteps) {
		this.userSteps = userSteps;
	}

	public void setChangedConfigMatrix(int[][] changedConfigMatrix) {
		this.changedConfigMatrix = changedConfigMatrix;
	}


}
