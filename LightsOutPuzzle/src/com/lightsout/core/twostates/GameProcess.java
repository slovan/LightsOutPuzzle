package com.lightsout.core.twostates;

public class GameProcess {
	private int userSteps; // quantity of user's steps
	private int score; // total score
	private int optimalSteps;
	private int[][] startConfigMatrix;
	private int[][] changedConfigMatrix;
	private int[][] optimalSolution;

	public GameProcess(int[][] startConfigMatrix) {
		this.startConfigMatrix = startConfigMatrix;
		this.changedConfigMatrix = Solver.copyOfMatrix(startConfigMatrix);
		this.userSteps = 0;
		this.score = 0;
		this.optimalSolution = Solver.getOptimalSolution(startConfigMatrix);
		this.optimalSteps = Solver.getStepsOptimalSolution(startConfigMatrix);
	}

	public void makeOneStep(int row, int column) {
		if (row >= changedConfigMatrix.length || column >= changedConfigMatrix.length || row < 0 || column < 0)
			return;
		changedConfigMatrix[row][column] = ++changedConfigMatrix[row][column] % 2;
		if (row - 1 >= 0)
			changedConfigMatrix[row - 1][column] = ++changedConfigMatrix[row - 1][column] % 2;
		if (row + 1 < changedConfigMatrix.length)
			changedConfigMatrix[row + 1][column] = ++changedConfigMatrix[row + 1][column] % 2;
		if (column - 1 >= 0)
			changedConfigMatrix[row][column - 1] = ++changedConfigMatrix[row][column - 1] % 2;
		if (column + 1 < changedConfigMatrix.length)
			changedConfigMatrix[row][column + 1] = ++changedConfigMatrix[row][column + 1] % 2;
		this.userSteps++;
	}

	public int getUserSteps() {
		return userSteps;
	}

	public void setUserSteps(int userSteps) {
		this.userSteps = userSteps;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getOptimalSteps() {
		return optimalSteps;
	}

	public void setOptimalSteps(int optimalSteps) {
		this.optimalSteps = optimalSteps;
	}

	public int[][] getStartConfigMatrix() {
		return startConfigMatrix;
	}

	public void setStartConfigMatrix(int[][] startConfigMatrix) {
		this.startConfigMatrix = startConfigMatrix;
	}

	public int[][] getChangedConfigMatrix() {
		return changedConfigMatrix;
	}

	public void setChangedConfigMatrix(int[][] changedConfigMatrix) {
		this.changedConfigMatrix = changedConfigMatrix;
	}

	public int[][] getOptimalSolution() {
		return optimalSolution;
	}

	public void setOptimalSolution(int[][] optimalSolution) {
		this.optimalSolution = optimalSolution;
	}

}
