package com.lightsout.language;

import java.util.HashMap;

/**
 * @author Volodymyr Ponomarenko
 *
 */
public interface IDefaultLanguage {
    String START_WINDOW_LABEL_1 = "Welcome to Lights Out Puzzle!";
    String START_WINDOW_LABEL_2 = "Click on the image to start ";
    String START_WINDOW_LABEL_3 = " ";
    String START_WINDOW_LABEL_4 = "-states game!";
    String START_WINDOW_WIDTH = "400";
    String FINISH_MESS_TITLE = "Congratulations!";
    String FINISH_MESS_TEXT_1 = "Your score is ";
    String FINISH_MESS_TEXT_2 = " points. Please, enter your name for list of the best players:";
    String MENU_LABEL_1 = "Game";
    String MENU_LABEL_2 = "Settings";
    String START_GAME_LABEL = "Start new game";
    String RESTART_GAME_LABEL = "Restart game";
    String MENU_LABEL_5 = "Size of game";
    String MENU_LABEL_6 = "Customize...";
    String SIZE_DIALOG_TITLE = "Customize size of game";
    String SIZE_DIALOG_TEXT = "Choose desired size:";
    String MENU_LABEL_7 = "Quantity of light's states";
    String MENU_LABEL_8 = "2 states";
    String MENU_LABEL_9 = "3 states";
    String SAVE_LABEL = "Save game...";
    String LOAD_LABEL = "Load game...";
    String SHOW_SOLUTION_LABEL = "Show solution";
    String EXIT_LABEL = "Exit";
    String WARNING_TITLE = "Warning";
    String INTERRUPT_MESSAGE = "Are you sure to interrupt the game?";
    String REWRITE_SAVES_MESSAGE = "Are you sure to rewrite saved game? Previous game configuration will be lost!..";
    String INTER_LANGUAGE = "Language";

    
    /**
     * Put defined interface constants in HashMap container
     * 
     * @return HashMap container with default (English) language scheme
     */
    static HashMap<String, String> getDefaultLanguageScheme() {
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
