package com.lightsout.core.twostates;


public class Result {
	private String gamerName;
	private int gamerScore;
	
	public Result(String gamerName, int gamerScore) {
		this.gamerName = gamerName;
		this.gamerScore = gamerScore;
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
	
	public String toString() {
		return this.gamerName + "\t" + this.gamerScore;
	}
}
