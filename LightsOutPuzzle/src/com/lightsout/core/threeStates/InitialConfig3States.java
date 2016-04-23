package com.lightsout.core.threeStates;

import com.lightsout.core.InitialConfig;
import com.lightsout.core.Solver;

public class InitialConfig3States extends InitialConfig {

	protected int genRandomInteger() {
		return rand.nextInt(3);
	}
	
	protected Solver getSolver() {
		return new Solver3States();
	}
	
}
