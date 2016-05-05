package com.lightsout.core;

import com.lightsout.core.threeStates.Solver3States;
import com.lightsout.core.twoStates.Solver2States;

public class GameProcess {

    private int quantityOfStates;
    private int userSteps; // quantity of user's steps
    private int timesOfShowingSolution;
    private int score; // total score
    private int optimalSteps;
    private int[][] startConfigMatrix;
    private int[][] changedConfigMatrix;
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
        this.changedConfigMatrix = this.solver.copyOfMatrix(startConfigMatrix);
        this.userSteps = 0;
        this.score = 0;
        this.timesOfShowingSolution = 0;
        this.currentOptimalSolution = null;
        this.optimalSteps = this.solver.getStepsOptimalSolution(startConfigMatrix);
    }

    public void computeScore() {
        float coeff1 = 0.99f;
        float coeff2 = 0.5f;
        int coeffPow1 = this.userSteps - this.optimalSteps;
        int coeffPow2 = this.timesOfShowingSolution;
        int maxScore = (int)Math.pow(this.startConfigMatrix.length * 10, this.quantityOfStates);

        this.score = (int)(maxScore * Math.pow(coeff1, coeffPow1) * Math.pow(coeff2, coeffPow2));
    }

    public void makeOneStep(int row, int column) {
        if (row >= this.changedConfigMatrix.length || column >= this.changedConfigMatrix.length || row < 0 || column < 0) {
            return;
        }
        this.changedConfigMatrix[row][column] = ++this.changedConfigMatrix[row][column] % this.quantityOfStates;
        if (row - 1 >= 0) {
            this.changedConfigMatrix[row - 1][column] = ++this.changedConfigMatrix[row - 1][column] % this.quantityOfStates;
        }
        if (row + 1 < this.changedConfigMatrix.length) {
            this.changedConfigMatrix[row + 1][column] = ++this.changedConfigMatrix[row + 1][column] % this.quantityOfStates;
        }
        if (column - 1 >= 0) {
            this.changedConfigMatrix[row][column - 1] = ++this.changedConfigMatrix[row][column - 1] % this.quantityOfStates;
        }
        if (column + 1 < this.changedConfigMatrix.length) {
            this.changedConfigMatrix[row][column + 1] = ++this.changedConfigMatrix[row][column + 1] % this.quantityOfStates;
        }
        this.userSteps++;
    }

    public void findCurrentSolution() {
        this.timesOfShowingSolution++;
        this.currentOptimalSolution = this.solver.getOptimalSolution(this.changedConfigMatrix);
    }

    public int getUserSteps() {
        return this.userSteps;
    }

    public int getScore() {
        return this.score;
    }

    public int getOptimalSteps() {
        return this.optimalSteps;
    }

    public int[][] getStartConfigMatrix() {
        return this.startConfigMatrix;
    }

    public int[][] getChangedConfigMatrix() {
        return this.changedConfigMatrix;
    }

    public int[][] getCurrentSolution() {
        return this.currentOptimalSolution;
    }

    public int getTimesOfShowingSolution() {
        return this.timesOfShowingSolution;
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
