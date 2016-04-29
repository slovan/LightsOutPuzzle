package com.lightsout.language;

import java.util.HashMap;

public interface DefaultLanguage {
	public static final String START_WINDOW_LABEL_1 = "Welcome to Lights Out Puzzle!";
	public static final String START_WINDOW_LABEL_2 = "Click on the image to start ";
	public static final String START_WINDOW_LABEL_3 = " ";
	public static final String START_WINDOW_LABEL_4 = "-states game!";
	public static final String START_WINDOW_WIDTH = "400";

	

	public static HashMap<String, String> getDefaultLanguageScheme() {
		HashMap<String, String> langScheme = new HashMap<String, String>();
		
		langScheme.put("startWindowLabel1", START_WINDOW_LABEL_1);
		langScheme.put("startWindowLabel2", START_WINDOW_LABEL_2);
		langScheme.put("startWindowLabel3", START_WINDOW_LABEL_3);
		langScheme.put("startWindowLabel4", START_WINDOW_LABEL_4);
		langScheme.put("startWindowWidth", START_WINDOW_WIDTH);

		return langScheme;
	}
}
