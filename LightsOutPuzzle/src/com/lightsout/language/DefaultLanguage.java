package com.lightsout.language;

import java.util.HashMap;

public interface DefaultLanguage {
	public static final String START_WINDOW_LABEL_1 = "Welcome to Lights Out Puzzle!";
	public static final String START_WINDOW_LABEL_2 = "Click on the image to start ";
	public static final String START_WINDOW_LABEL_3 = " ";
	public static final String START_WINDOW_LABEL_4 = "-states game!";
	public static final String START_WINDOW_WIDTH = "400";
	public static final String FINISH_MESS_TITLE = "Congratulations!";
	public static final String FINISH_MESS_TEXT_1 = "Your score is ";
	public static final String FINISH_MESS_TEXT_2 = " points. Please, enter your name for list of the best players:";
	public static final String MENU_LABEL_1 = "Game";
	public static final String MENU_LABEL_2 = "Settings";
	public static final String START_GAME_LABEL = "Start new game";
	public static final String RESTART_GAME_LABEL = "Restart game";
	public static final String MENU_LABEL_5 = "Size of game";
	public static final String MENU_LABEL_6 = "Customize...";
	public static final String SIZE_DIALOG_TITLE = "Customize size of game";
	public static final String SIZE_DIALOG_TEXT = "Choose desired size:";
	public static final String MENU_LABEL_7 = "Quantity of light's states";
	public static final String MENU_LABEL_8 = "2 states";
	public static final String MENU_LABEL_9 = "3 states";
	public static final String SAVE_LABEL = "Save game...";
	public static final String LOAD_LABEL = "Load game...";
	public static final String SHOW_SOLUTION_LABEL = "Show solution";
	public static final String EXIT_LABEL = "Exit";
	public static final String WARNING_TITLE = "Warning";
	public static final String INTERRUPT_MESSAGE = "Are you sure to interrupt the game?";
	public static final String REWRITE_SAVES_MESSAGE = "Are you sure to rewrite saved game? Previous game configuration will be lost!..";
	public static final String INTER_LANGUAGE = "Language";
	

	public static HashMap<String, String> getDefaultLanguageScheme() {
		HashMap<String, String> langScheme = new HashMap<String, String>();
		
		langScheme.put("startWindowLabel1", START_WINDOW_LABEL_1);
		langScheme.put("startWindowLabel2", START_WINDOW_LABEL_2);
		langScheme.put("startWindowLabel3", START_WINDOW_LABEL_3);
		langScheme.put("startWindowLabel4", START_WINDOW_LABEL_4);
		langScheme.put("startWindowWidth", START_WINDOW_WIDTH);
		langScheme.put("finishMessTitle", FINISH_MESS_TITLE);
		langScheme.put("finishMessText1", FINISH_MESS_TEXT_1);
		langScheme.put("finishMessText2", FINISH_MESS_TEXT_2);
		langScheme.put("menuLabel1", MENU_LABEL_1);
		langScheme.put("menuLabel2", MENU_LABEL_2);
		langScheme.put("startGameLabel", START_GAME_LABEL);
		langScheme.put("restartGameLabel", RESTART_GAME_LABEL);
		langScheme.put("menuLabel5", MENU_LABEL_5);
		langScheme.put("menuLabel6", MENU_LABEL_6);
		langScheme.put("sizeDialogTitle", SIZE_DIALOG_TITLE);
		langScheme.put("sizeDialogText", SIZE_DIALOG_TEXT);
		langScheme.put("menuLabel7", MENU_LABEL_7);
		langScheme.put("menuLabel8", MENU_LABEL_8);
		langScheme.put("menuLabel9", MENU_LABEL_9);
		langScheme.put("saveLabel", SAVE_LABEL);
		langScheme.put("loadLabel", LOAD_LABEL);
		langScheme.put("showSolLabel", SHOW_SOLUTION_LABEL);
		langScheme.put("exitLabel", EXIT_LABEL);
		langScheme.put("warningTitle", WARNING_TITLE);
		langScheme.put("interruptMessage", INTERRUPT_MESSAGE);
		langScheme.put("rewriteSavesMessage", REWRITE_SAVES_MESSAGE);
		langScheme.put("interfaceLang", INTER_LANGUAGE);

		return langScheme;
	}
}
