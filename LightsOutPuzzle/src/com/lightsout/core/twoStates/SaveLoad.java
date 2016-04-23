package com.lightsout.core.twoStates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveLoad {
	private static final String pathToFile = "input/game.svs";
	
	public static boolean isSavedGame() {
		boolean result;
		
		try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {			
			if (br.readLine() == null) {
				result = false;
			} else {
				result = true;
			}			
		} catch (IOException exc) {
			System.out.println("I/O Error: " + exc);
			result = false;
		} 

		return result;
	}
	
	public static GameProcess loadSavedGame() {
		GameProcess gp = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		File fr = new File(pathToFile);
		File tmp = new File(fr+".tmp");
		int userSteps = 0;
		int timesOfShowingSolution = 0;
		int[][] startConfigMatrix = null;
		int[][] changedConfigMatrix = null;
		
		try {			
			if (!fr.exists()) {
				fr.getParentFile().mkdirs();
				fr.createNewFile();
			}
			br = new BufferedReader(new FileReader(fr));
			pw = new PrintWriter(new FileWriter(tmp));
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
		} catch (IOException exc) {
			System.out.println("I/O Error: " + exc);
		} finally {
			try {
				br.close();
				pw.close();
				fr.delete();
		    	tmp.renameTo(fr);
			} catch (IOException exc) {
				System.out.println("I/O Error: " + exc);
			}
		}
		gp = new GameProcess(startConfigMatrix);
		gp.setChangedConfigMatrix(changedConfigMatrix);
		gp.setUserSteps(userSteps);
		gp.setTimesOfShowingSolution(timesOfShowingSolution);
		return gp;
	}
	
	public static void saveGame(GameProcess gp) {

		try (PrintWriter pw = new PrintWriter(new FileWriter(pathToFile))) {
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
