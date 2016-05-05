package com.lightsout.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultsHandler {
    private int sizeOfGame;
    private int quantityPlaces; // quantity of saved places
    private int quantityOfStates;
    private ArrayList<Result> resultsList;
    private String pathToFile;

    public ResultsHandler(int sizeOfGame, int quantityOfStates, int quantityPlaces) {
        this.sizeOfGame = sizeOfGame;
        this.quantityPlaces = quantityPlaces;
        this.quantityOfStates = quantityOfStates;
        this.pathToFile = "results/" + this.sizeOfGame + "x" + this.sizeOfGame + "." + this.quantityOfStates + "st.res";
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
        this.resultsList = this.readFromFile();
    }

    public ResultsHandler(int sizeOfGame, int quantityOfStates) {
        this(sizeOfGame, quantityOfStates, 20);
    }

    public ArrayList<Result> readFromFile() {
        ArrayList<Result> result = new ArrayList<Result>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.pathToFile))) {

            String str; // read row

            while ((str = br.readLine()) != null) {
                // in case of mistaken empty row
                if (str.equals("")) {
                    continue;
                }

                String gamerName = "";
                int gamerScore = 0;
                int gamerSteps = 0;
                int optimalSteps = 0;

                String[] data = str.split("\t");
                if (data.length != 4) {
                    throw new IOException("Error: incorrect data format in the file of results");
                }
                for (int i = 0; i < data.length; i++) {
                    switch (i) {
                        case 0:
                            gamerName = data[i];
                            break;
                        case 1:
                            gamerScore = Integer.parseInt(data[i]);
                            break;
                        case 2:
                            gamerSteps = Integer.parseInt(data[i]);
                            break;
                        case 3:
                            optimalSteps = Integer.parseInt(data[i]);
                            break;
                    }
                }
                result.add(new Result(gamerName, gamerScore, gamerSteps, optimalSteps));
            }
        } catch (NumberFormatException exc) {
            System.err.println("Error: incorrect data format in the file of results");
            new File(this.pathToFile).delete();
        } catch (IOException exc) {
            System.err.println(exc.getMessage());
            new File(this.pathToFile).delete();
        }
        return result;
    }

    public void writeToFile(ArrayList<Result> list) {

        try (FileWriter fw = new FileWriter(this.pathToFile)) {
            for (Result res : list) {
                String str = res.getGamerName() + "\t" + res.getGamerScore() + "\t" + res.getGamerSteps() + "\t"
                        + res.getOptimalSteps() + "\r\n";
                fw.write(str);
            }
        } catch (IOException exc) {
            System.err.println("I/O Error: " + exc);
        }
    }

    public boolean isBetweenWinners(Result result) {
        boolean flag = false;
        if (this.resultsList.isEmpty() || this.resultsList.size() < 20) {
            flag = true;
        } else {
            for (Result r : this.resultsList) {
                if (result.getGamerScore() > r.getGamerScore()) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public void updateResultsList(Result result) {
        this.resultsList = this.readFromFile();
        this.resultsList.add(result);
        this.resultsList.sort((Result r1, Result r2) -> {
            int result1 = r1.getGamerScore();
            int result2 = r2.getGamerScore();
            return result2 - result1;
        });
        if (this.resultsList.size() > this.quantityPlaces) {
            this.resultsList.subList(this.quantityPlaces, this.resultsList.size()).clear();
        }
        this.writeToFile(this.resultsList);
    }

    public int getSizeOfGame() {
        return this.sizeOfGame;
    }

    public void setSizeOfGame(int sizeOfGame) {
        this.sizeOfGame = sizeOfGame;
    }

    public int getQuantityPlaces() {
        return this.quantityPlaces;
    }

    public void setQuantityPlaces(int quantityPlaces) {
        this.quantityPlaces = quantityPlaces;
    }

    public ArrayList<Result> getResultsList() {
        return this.resultsList;
    }

    public void setResultsList(ArrayList<Result> resultsList) {
        this.resultsList = resultsList;
    }

    public String getPathToFile() {
        return this.pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

}
