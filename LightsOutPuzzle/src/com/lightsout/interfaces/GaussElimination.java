package com.lightsout.interfaces;

public interface GaussElimination {

	public void makeGaussElim();

	public void findRank();

	public void findDet();

	public int[][] getMatrix();

	public void setMatrix(int[][] matrix);

	public int getRank();

	public void setRank(int rank);

	public int getDet();

	public void setDet(int det);

}
