package com.lightsout.core.twostates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultsHandler {
	private int sizeOfGame;
	private int quantityPlaces;
	private ArrayList<Result> resultsList;
	private String pathToFile;

	public ResultsHandler(int sizeOfGame, int quantityPlaces) {
		this.sizeOfGame = sizeOfGame;
		this.quantityPlaces = quantityPlaces;
		this.pathToFile = "results/" + this.sizeOfGame + ".res";
		
		this.resultsList = readFromFile();
	}

	public ResultsHandler(int sizeOfGame) {
		this(sizeOfGame, 20);
	}

	public synchronized ArrayList<Result> readFromFile() {
		ArrayList<Result> result = new ArrayList<Result>();
		BufferedReader br = null;

		try {
			File f = new File(pathToFile);
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			br = new BufferedReader(new FileReader(f));
			String str; // read row

			while ((str = br.readLine()) != null) {
				String gamerName = "";
				int gamerScore = 0;

				// in case of mistaken empty row
				if (str.equals(""))
					continue;

				for (int i = 0; i < str.length(); i++) {
					if (str.charAt(i) == '\t') {
						gamerName = str.substring(0, i);
						gamerScore = Integer.parseInt(str.substring(i + 1, str.length()));
						break;
					}
				}

				result.add(new Result(gamerName, gamerScore));
			}
		} catch (IOException exc) {
			System.out.println("I/O Error: " + exc);
		} finally {
			try {
				br.close();
			} catch (IOException exc) {
				System.out.println("I/O Error: " + exc);
			}
		}
		return result;
	}
	
	public synchronized void writeToFile(ArrayList<Result> list) {

		try (FileWriter fw = new FileWriter(pathToFile)) {
			for (Result res : list) {
				String str = res.getGamerName() + "\t" + res.getGamerScore() + "\r\n";
				fw.write(str);
			}
		} catch (IOException exc) {
			System.err.println("I/O Error: " + exc);
		}
	}

	public boolean isBetweenWinners(Result result) {
		boolean flag = false;
		if (this.resultsList.isEmpty()) {
			flag = true;
		} else {
			for (Result r : this.resultsList) {
				if (result.getGamerScore() > r.getGamerScore())
					flag = true;
			}
		}
		return flag;
	}

	public synchronized void updateResultsList(Result result) {
		this.resultsList = readFromFile();
		resultsList.add(result);
		resultsList.sort((Result r1, Result r2) -> {
			int result1 = r1.getGamerScore();
			int result2 = r2.getGamerScore();
			return result2 - result1;
		});
		if (resultsList.size() > this.quantityPlaces)
			resultsList.subList(this.quantityPlaces, resultsList.size()).clear();
		writeToFile(resultsList);
	}

	public int getSizeOfGame() {
		return sizeOfGame;
	}

	public void setSizeOfGame(int sizeOfGame) {
		this.sizeOfGame = sizeOfGame;
	}

	public int getQuantityPlaces() {
		return quantityPlaces;
	}

	public void setQuantityPlaces(int quantityPlaces) {
		this.quantityPlaces = quantityPlaces;
	}

	public ArrayList<Result> getResultsList() {
		return resultsList;
	}

	public void setResultsList(ArrayList<Result> resultsList) {
		this.resultsList = resultsList;
	}

	public String getPathToFile() {
		return pathToFile;
	}

	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}

}
