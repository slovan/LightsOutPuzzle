package com.lightsout.core;


public class Result {
	private String gamerName;
	private int gamerScore;
	private int gamerSteps;
	private int optimalSteps;
	
	public Result(String gamerName, int gamerScore, int gamerSteps, int optimalSteps) {
		this.gamerName = gamerName;
		this.gamerScore = gamerScore;
		this.gamerSteps = gamerSteps;
		this.optimalSteps = optimalSteps;
	}

	public String getGamerName() {
		return gamerName;
	}

	public void setGamerName(String gamerName) {
		this.gamerName = gamerName;
	}

	public int getGamerScore() {
		return gamerScore;
	}

	public void setGamerScore(int gamerResult) {
		this.gamerScore = gamerResult;
	}
	
	public int getGamerSteps() {
		return gamerSteps;
	}

	public void setGamerSteps(int gamerSteps) {
		this.gamerSteps = gamerSteps;
	}

	public int getOptimalSteps() {
		return optimalSteps;
	}

	public void setOptimalSteps(int optimalSteps) {
		this.optimalSteps = optimalSteps;
	}

	@Override
	public String toString() {
		return "Result [gamerName=" + gamerName + ", gamerScore=" + gamerScore + ", gamerSteps=" + gamerSteps
				+ ", optimalSteps=" + optimalSteps + "]";
	}

}
