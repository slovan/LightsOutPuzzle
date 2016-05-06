package com.lightsout.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class SaveLoad {
    private final String pathToFile;
    private int quantityOfStates;
    private int sizeOfGame;

    public SaveLoad(int sizeOfGame, int quantityOfStates) {
        this.quantityOfStates = quantityOfStates;
        this.sizeOfGame = sizeOfGame;
        this.pathToFile = new String("saves/" + quantityOfStates + "stGame/" + sizeOfGame + "x" + sizeOfGame + ".svs");
        File fr = new File(this.pathToFile);
        if (!fr.exists()) {
            fr.getParentFile().mkdirs();
            try {
                fr.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public boolean isSavedGame() {
        boolean flag;
        try (BufferedReader br = new BufferedReader(new FileReader(this.pathToFile))) {
            String str = br.readLine();
            if (str == null) {
                flag = false;
            } else if (!str.equals("--- Start Config ---")) {
                flag = false;
            } else {
                flag = true;
            }
        } catch (IOException exc) {
            System.out.println("I/O Error: " + exc);
            flag = false;
        }
        return flag;
    }

    public GameProcess loadSavedGame() {
        GameProcess gp = null;
        File fr = new File(this.pathToFile);
        File tmp = new File(fr + ".tmp");
        int userSteps = 0;
        int timesOfShowingSolution = 0;
        int[][] startConfigMatrix = null;
        int[][] changedConfigMatrix = null;

        try (BufferedReader br = new BufferedReader(new FileReader(fr));
                PrintWriter pw = new PrintWriter(new FileWriter(tmp))) {

            String str = br.readLine(); // read row

            while (str != null) {
                switch (str) {
                    case "--- Start Config ---":
                        str = br.readLine();
                        String[] data = str.split(" ");
                        startConfigMatrix = new int[data.length][data.length];
                        for (int i = 0; i < startConfigMatrix.length; i++) {
                            data = str.split(" ");
                            for (int j = 0; j < startConfigMatrix.length; j++) {
                                startConfigMatrix[i][j] = Integer.parseInt(data[j]);
                            }
                            str = br.readLine();
                        }
    
                        break;
                    case "--- Changed Config ---":
                        str = br.readLine();
                        data = str.split(" ");
                        changedConfigMatrix = new int[data.length][data.length];
                        for (int i = 0; i < changedConfigMatrix.length; i++) {
                            data = str.split(" ");
                            for (int j = 0; j < changedConfigMatrix.length; j++) {
                                changedConfigMatrix[i][j] = Integer.parseInt(data[j]);
                            }
                            str = br.readLine();
                        }
                        break;
                    case "--- User's steps ---":
                        userSteps = Integer.parseInt(br.readLine());
                        str = br.readLine();
                        break;
                    case "--- Times of showing solution ---":
                        timesOfShowingSolution = Integer.parseInt(br.readLine());
                        str = br.readLine();
                        break;
                    default:
                        str = br.readLine();
                        break;
                }
            }

        } catch (NullPointerException exc) {
            fr.delete();
            tmp.renameTo(fr);
            JOptionPane.showMessageDialog(null, "Cannot load previous game. New Game will be started", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println();
            return new GameProcess(new InitialConfig(this.sizeOfGame, this.quantityOfStates).getRandomConfig(), this.quantityOfStates);
        } catch (IOException exc) {
            System.err.println("I/O Error: " + exc);
            return gp;
        }
        fr.delete();
        tmp.renameTo(fr);

        gp = new GameProcess(startConfigMatrix, this.quantityOfStates);
        gp.setChangedConfigMatrix(changedConfigMatrix);
        gp.setUserSteps(userSteps);
        gp.setTimesOfShowingSolution(timesOfShowingSolution);

        return gp;
    }

    public void saveGame(GameProcess gp) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(this.pathToFile))) {
            pw.println("--- Start Config ---");
            int[][] startConfigMatrix = gp.getStartConfigMatrix();
            for (int i = 0; i < startConfigMatrix.length; i++) {
                String str = "" + startConfigMatrix[i][0];
                for (int j = 1; j < startConfigMatrix.length; j++) {
                    str += " " + startConfigMatrix[i][j];
                }
                pw.println(str);
            }

            pw.println("--- Changed Config ---");
            int[][] changedConfigMatrix = gp.getChangedConfigMatrix();
            for (int i = 0; i < changedConfigMatrix.length; i++) {
                String str = "" + changedConfigMatrix[i][0];
                for (int j = 1; j < changedConfigMatrix.length; j++) {
                    str += " " + changedConfigMatrix[i][j];
                }
                pw.println(str);
            }

            pw.println("--- User's steps ---");
            pw.println(gp.getUserSteps());

            pw.println("--- Times of showing solution ---");
            pw.println(gp.getTimesOfShowingSolution());

        } catch (IOException exc) {
            System.err.println("I/O Error: " + exc);
        }
    }
}
