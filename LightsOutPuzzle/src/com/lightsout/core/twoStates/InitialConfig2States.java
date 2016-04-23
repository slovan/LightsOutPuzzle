package com.lightsout.core.twoStates;

import com.lightsout.core.InitialConfig;
import com.lightsout.core.Solver;

public class InitialConfig2States extends InitialConfig {

	protected int genRandomInteger() {
		return rand.nextInt(2);
	}
	
	protected Solver getSolver() {
		return new Solver2States();
	}
	
}
