package com.lightsout.ui.swing;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.lightsout.core.Result;
import com.lightsout.core.ResultsHandler;

public class ShowResults {
	private int size;
	private ArrayList<Result> resList;
	
	public ShowResults(int size) {
		this.size = size;
		this.resList = new ResultsHandler(size, 2).getResultsList();
		makeGUI();
	}
	
	private Object[][] convertListToArray() {
		Object[][] resArray = new Object[resList.size()][];
		for (int i = 0; i < resArray.length; i++) {
			resArray[i] = new Object[] {i+1, resList.get(i).getGamerName(), resList.get(i).getGamerScore(), resList.get(i).getGamerSteps(), resList.get(i).getOptimalSteps()};
		}
		return resArray;
	}
	
	private void makeGUI() {
		JFrame jfrm = new JFrame("The results list of " + size + "x" + size + " game");
		jfrm.setSize(700, 500);
		jfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jfrm.setVisible(true);
		String[] columnNames = {"Pozition", "Name", "Score", "Steps", "Optimal steps"};
		JTable jtb = new JTable(convertListToArray(), columnNames);
		JScrollPane jsp = new JScrollPane(jtb);
		jfrm.add(jsp);
	}
}
