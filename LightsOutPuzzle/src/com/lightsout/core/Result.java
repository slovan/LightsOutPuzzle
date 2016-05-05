package com.lightsout.core;

/**
 * @author Volodymyr Ponomarenko
 *
 */
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
        return this.gamerName;
    }

    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public int getGamerScore() {
        return this.gamerScore;
    }

    public void setGamerScore(int gamerResult) {
        this.gamerScore = gamerResult;
    }

    public int getGamerSteps() {
        return this.gamerSteps;
    }

    public void setGamerSteps(int gamerSteps) {
        this.gamerSteps = gamerSteps;
    }

    public int getOptimalSteps() {
        return this.optimalSteps;
    }

    public void setOptimalSteps(int optimalSteps) {
        this.optimalSteps = optimalSteps;
    }

    @Override
    public String toString() {
        return "Result [gamerName=" + this.gamerName + ", gamerScore=" + this.gamerScore + ", gamerSteps=" + this.gamerSteps
                + ", optimalSteps=" + this.optimalSteps + "]";
    }

}
