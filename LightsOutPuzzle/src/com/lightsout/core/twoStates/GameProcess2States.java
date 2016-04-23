package com.lightsout.core.twoStates;

import com.lightsout.core.GameProcess;
import com.lightsout.core.Solver;

public class GameProcess2States extends GameProcess {
	
	public GameProcess2States(int[][] startConfigMatrix) {
		super(startConfigMatrix);
	}

	public void makeOneStep(int row, int column) {
		if (row >= this.changedConfigMatrix.length || column >= this.changedConfigMatrix.length || row < 0 || column < 0)
			return;
		this.changedConfigMatrix[row][column] = ++this.changedConfigMatrix[row][column] % 2;
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
	
	protected Solver getSolver() {
		return new Solver2States();
	}

}
