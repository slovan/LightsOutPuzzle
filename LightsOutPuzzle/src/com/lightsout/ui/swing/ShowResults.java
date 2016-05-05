package com.lightsout.ui.swing;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.lightsout.core.Result;
import com.lightsout.core.ResultsHandler;

public class ShowResults {
    private int sizeOfGame;
    private int quantityOfStates;
    private ArrayList<Result> resList;

    public ShowResults(int sizeOfGame, int quantityOfStates) {
        this.sizeOfGame = sizeOfGame;
        this.quantityOfStates = quantityOfStates;
        this.resList = new ResultsHandler(sizeOfGame, quantityOfStates).getResultsList();
        this.makeGUI();
    }

    @Deprecated
    public ShowResults(int sizeOfGame) {
        this(sizeOfGame, 2);
    }

    private Object[][] convertListToArray() {
        Object[][] resArray = new Object[this.resList.size()][];
        for (int i = 0; i < resArray.length; i++) {
            resArray[i] = new Object[] { i + 1, this.resList.get(i).getGamerName(), this.resList.get(i).getGamerScore(),
                    this.resList.get(i).getGamerSteps(), this.resList.get(i).getOptimalSteps() };
        }
        return resArray;
    }

    private void makeGUI() {
        JFrame jfrm = new JFrame(
                "The results list of " + this.sizeOfGame + "x" + this.sizeOfGame + " " + this.quantityOfStates + "-states game");
        jfrm.setSize(700, 500);
        jfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jfrm.setVisible(true);
        String[] columnNames = { "Pozition", "Name", "Score", "Steps", "Optimal steps" };
        JTable jtb = new JTable(this.convertListToArray(), columnNames);
        JScrollPane jsp = new JScrollPane(jtb);
        jfrm.add(jsp);
    }
}
