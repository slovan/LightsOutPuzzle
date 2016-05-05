package com.lightsout.core;

import java.util.Arrays;

public abstract class Solver {

    public abstract int[][] getAllSolutions(int[][] configMatrix);

    public abstract int[][] getQuietPatterns(int size);

    public abstract int[][] makeGaussElimination(int[][] matrix);

    public abstract int[][] makeJordanGaussElim(int[][] matrix);

    public int[][] getOptimalSolution(int[][] configMatrix) {
        int[][] solutions = this.getAllSolutions(configMatrix);
        int[][] optimalSolution;
        int size;
        int countMIN; // counter of steps in the optimal solution

        if (solutions != null) {
            size = configMatrix.length;
            countMIN = size * size;
            // initialization of optimal solution matrix
            optimalSolution = new int[size][size];

            int index = 0; // index of row of the solutions matrix with the
                           // optimal solution
            for (int i = 0; i < solutions.length; i++) {
                int count = 0; // counter of steps in the current solution
                for (int j = 0; j < solutions[i].length; j++) {
                    count += solutions[i][j];
                }
                if (count < countMIN) {
                    countMIN = count;
                    index = i;
                }
            }

            // make square matrix from the row of the optimal solution
            for (int i = 0; i < optimalSolution.length; i++) {
                for (int j = 0; j < optimalSolution[i].length; j++) {
                    optimalSolution[i][j] = solutions[index][i * size + j];
                }
            }

        } else {
            optimalSolution = null;
        }
        return optimalSolution;
    }

    public int getStepsOptimalSolution(int[][] configMatrix) {
        int[][] optimalSolution = this.getOptimalSolution(configMatrix);
        int count = 0; // counter of steps in the optimal solution

        if (optimalSolution != null) {
            for (int i = 0; i < optimalSolution.length; i++) {
                for (int j = 0; j < optimalSolution[i].length; j++) {
                    count += optimalSolution[i][j];
                }
            }
        }
        return count;
    }

    public int[][] getLightsOutMatrix(int size) {
        int[][] lightsOutMatrix = new int[size * size][size * size];

        // fulfill lights out matrix by 0
        for (int i = 0; i < lightsOutMatrix.length; i++) {
            for (int j = 0; j < lightsOutMatrix[i].length; j++) {
                lightsOutMatrix[i][j] = 0;
            }
        }

        // fulfill lights out matrix by 1
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int posLOM = i * size + j; // position in lights out matrix
                lightsOutMatrix[posLOM][posLOM] = 1; // main diagonal element
                if (i > 0) {
                    lightsOutMatrix[posLOM][posLOM - size] = 1;
                }
                if (i < size - 1) {
                    lightsOutMatrix[posLOM][posLOM + size] = 1;
                }
                if (j > 0) {
                    lightsOutMatrix[posLOM][posLOM - 1] = 1;
                }
                if (j < size - 1) {
                    lightsOutMatrix[posLOM][posLOM + 1] = 1;
                }
            }
        }
        return lightsOutMatrix;
    }

    public int[][] getAugmentedMatrix(int[][] coeffMatrix, int[] vectorMatrix) {
        int[][] augMatrix = new int[coeffMatrix.length][coeffMatrix.length + 1];
        for (int i = 0; i < coeffMatrix.length; i++) {
            for (int j = 0; j < coeffMatrix[i].length; j++) {
                augMatrix[i][j] = coeffMatrix[i][j];
            }
        }
        for (int k = 0; k < vectorMatrix.length; k++) {
            augMatrix[k][augMatrix[k].length - 1] = vectorMatrix[k];
        }
        return augMatrix;
    }

    // use this method if the configuration matrix is specified as square matrix
    // makes vector matrix of n*n length
    public int[][] getAugmentedMatrix(int[][] coeffMatrix, int[][] configMatrix) {
        int[] vectorMatrix = new int[(int)Math.pow(configMatrix.length, 2)];
        for (int i = 0; i < configMatrix.length; i++) {
            for (int j = 0; j < configMatrix[i].length; j++) {
                vectorMatrix[i * configMatrix.length + j] = configMatrix[i][j];
            }
        }
        return this.getAugmentedMatrix(coeffMatrix, vectorMatrix);
    }

    public int getRank(int[][] matrix) {
        int[][] gaussMatrix = this.makeGaussElimination(matrix);
        int rank = matrix.length;

        for (int i = gaussMatrix.length - 1; i >= 0; i--) {
            boolean flag = false;
            for (int j = 0; j < gaussMatrix[i].length; j++) {
                if (gaussMatrix[i][j] != 0) {
                    flag = true;
                }
            }
            if (!flag) {
                rank--;
            }
        }
        return rank;
    }

    public int[][] copyOfMatrix(int[][] matrix) {
        int[][] resultMatrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            resultMatrix[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        return resultMatrix;
    }
}
