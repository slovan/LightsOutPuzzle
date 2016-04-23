package com.lightsout.core;

public abstract class GameProcess {

	protected int userSteps; // quantity of user's steps
	private int timesOfShowingSolution;
	private int score; // total score
	private int optimalSteps;
	private int[][] startConfigMatrix;
	protected int[][] changedConfigMatrix;
	private int[][] currentOptimalSolution;
	private Solver solver;

	public GameProcess(int[][] startConfigMatrix) {
		this.solver = getSolver();
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
		int maxScore = (int) Math.pow(this.startConfigMatrix.length * 10, 2);
		
		this.score = (int) (maxScore * Math.pow(coeff1, coeffPow1) * Math.pow(coeff2, coeffPow2));
	}

	protected abstract void makeOneStep(int row, int column);
	
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

	protected abstract Solver getSolver();
	
	

}
