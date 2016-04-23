package com.lightsout.core.threeStates;

import com.lightsout.core.GameProcess;
import com.lightsout.core.Solver;

public class GameProcess3States extends GameProcess {

	public GameProcess3States(int[][] startConfigMatrix) {
		super(startConfigMatrix);
	}
	
	public void makeOneStep(int row, int column) {
		if (row >= changedConfigMatrix.length || column >= changedConfigMatrix.length || row < 0 || column < 0)
			return;
		changedConfigMatrix[row][column] = ++changedConfigMatrix[row][column] % 3;
		if (row - 1 >= 0)
			changedConfigMatrix[row - 1][column] = ++changedConfigMatrix[row - 1][column] % 3;
		if (row + 1 < changedConfigMatrix.length)
			changedConfigMatrix[row + 1][column] = ++changedConfigMatrix[row + 1][column] % 3;
		if (column - 1 >= 0)
			changedConfigMatrix[row][column - 1] = ++changedConfigMatrix[row][column - 1] % 3;
		if (column + 1 < changedConfigMatrix.length)
			changedConfigMatrix[row][column + 1] = ++changedConfigMatrix[row][column + 1] % 3;
		this.userSteps++;
	}
	
	protected Solver getSolver() {
		return new Solver3States();
	}

}
