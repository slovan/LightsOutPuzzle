package com.lightsout.ui.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.lightsout.core.InitialConfig;
import com.lightsout.core.Result;
import com.lightsout.core.ResultsHandler;
import com.lightsout.core.SaveLoad;
import com.lightsout.language.LanguageSwitcher;
import com.lightsout.core.GameProcess;

public class Game extends JFrame implements ActionListener {
    // private LanguageSwitcher langSw;
    private HashMap<String, String> langScheme;
    private GameProcess gp;
    private int sizeOfGame; // size of game
    private int quantityOfStates;
    private boolean displayingStartWindow;
    private boolean displayingGameOverWindow;
    private JLabel lab1;
    private JLabel lab3;
    private JPanel jp;
    private MyMenu mm;
    private JButton[][] buttonsField;
    private SaveLoad sLoader;

    private static final long serialVersionUID = 1L;

    public Game(String title) {
        super(title);
        this.langScheme = LanguageSwitcher.DEFAULT.getLangScheme();
        try {
            setIconImage(ImageIO.read(new File("images/icon.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.sizeOfGame = 5;
        this.quantityOfStates = 2;
        this.sLoader = new SaveLoad(this.sizeOfGame, this.quantityOfStates);
        // this.gp = null;
        this.mm = new MyMenu();
        setJMenuBar(this.mm);

        // Give the frame an initial size.
        setSize(Integer.parseInt(this.langScheme.get("startWindowWidth")), 350);

        // Terminate the program when the user closes the application.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the frame.
        setVisible(true);
        setResizable(false);

        getContentPane().setLayout(new FlowLayout());

        this.jp = new JPanel();
        this.setStartBackground();
        getContentPane().add(this.jp);
    }

    private void setStartBackground() {

        this.jp.removeAll();
        this.jp.setPreferredSize(new Dimension(Integer.parseInt(this.langScheme.get("startWindowWidth")), 350));
        this.jp.setLayout(new BoxLayout(this.jp, BoxLayout.Y_AXIS));
        this.jp.setBorder(null);
        this.jp.setBackground(null);
        this.setSize(Integer.parseInt(this.langScheme.get("startWindowWidth")), 350);

        this.lab1 = new JLabel();

        this.lab1.setText(this.langScheme.get("startWindowLabel1"));
        this.lab1.setFont(new Font("Georgia", Font.BOLD, 22));
        this.lab1.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.jp.add(Box.createVerticalStrut(10));
        this.jp.add(this.lab1);
        this.jp.add(Box.createVerticalStrut(20));

        JButton startGame = new JButton(new ImageIcon("images/start.png"));
        startGame.setFocusable(false);
        startGame.setBorder(BorderFactory.createEmptyBorder());
        startGame.setContentAreaFilled(false);
        startGame.addActionListener((ae) -> this.startGame());
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.jp.add(startGame);
        this.jp.add(Box.createVerticalStrut(20));

        this.lab3 = new JLabel();
        this.lab3.setText(this.langScheme.get("startWindowLabel2") + this.sizeOfGame + "x" + this.sizeOfGame
                + this.langScheme.get("startWindowLabel3") + this.quantityOfStates
                + this.langScheme.get("startWindowLabel4"));
        this.lab3.setFont(new Font("Georgia", Font.PLAIN, 16));
        this.lab3.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.jp.add(this.lab3);

        this.displayingStartWindow = true;
        this.displayingGameOverWindow = false;
    }

    public void startGame() {
        this.displayingStartWindow = false;
        this.sLoader = new SaveLoad(this.sizeOfGame, this.quantityOfStates);
        InitialConfig initialConfig = new InitialConfig(this.sizeOfGame, this.quantityOfStates);
        this.gp = new GameProcess(initialConfig.getRandomConfig(), this.quantityOfStates);
        // mm = new MyMenu();
        // setJMenuBar(mm);
        this.mm.changeSaveLoadState();
        this.mm.changeShowSolutionState();
        this.mm.changeRestartState();
        this.setGame();

    }

    public void resetGame() {
        this.gp = new GameProcess(this.gp.getStartConfigMatrix(), this.quantityOfStates);
        this.setGame();
    }

    public void stopGame() {
        getContentPane().removeAll();
        setSize(350, 230);
        getContentPane().setLayout(new FlowLayout());
        this.jp = new JPanel();
        JLabel label = new JLabel("", new ImageIcon("images/game-over.png"), JLabel.CENTER);
        this.jp.add(label);
        getContentPane().add(this.jp);
        this.displayingGameOverWindow = true;
    }

    public void setGame() {
        int[][] changedConfigMatrix = this.gp.getChangedConfigMatrix();
        this.buttonsField = new JButton[this.sizeOfGame][this.sizeOfGame];

        this.jp.removeAll();
        this.jp.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        this.jp.setPreferredSize(new Dimension(35 * this.sizeOfGame + 2, 35 * this.sizeOfGame + 2));
        this.jp.setBackground(Color.GREEN);
        this.jp.setLayout(new GridLayout(this.sizeOfGame, this.sizeOfGame));
        this.setSize(35 * this.sizeOfGame + 130, 35 * this.sizeOfGame + 75);

        for (int i = 0; i < this.sizeOfGame; i++) {
            for (int j = 0; j < this.sizeOfGame; j++) {
                switch (changedConfigMatrix[i][j]) {
                    case 0:
                        this.buttonsField[i][j] = new JButton(new ImageIcon("images/black.png"));
                        this.buttonsField[i][j].setRolloverIcon(new ImageIcon("images/grey.png"));
                        this.buttonsField[i][j].setPressedIcon(new ImageIcon("images/grey_boom.png"));
                        break;
                    case 1:
                        this.buttonsField[i][j] = new JButton(new ImageIcon("images/light_yellow.png"));
                        this.buttonsField[i][j].setRolloverIcon(new ImageIcon("images/yellow.png"));
                        this.buttonsField[i][j].setPressedIcon(new ImageIcon("images/yellow_boom.png"));
                        break;
                    case 2:
                        this.buttonsField[i][j] = new JButton(new ImageIcon("images/light_red.png"));
                        this.buttonsField[i][j].setRolloverIcon(new ImageIcon("images/red.png"));
                        this.buttonsField[i][j].setPressedIcon(new ImageIcon("images/red_boom.png"));
                        break;
                }

                this.jp.add(this.buttonsField[i][j]);
                this.buttonsField[i][j].setBorder(BorderFactory.createEmptyBorder());
                this.buttonsField[i][j].setContentAreaFilled(false);
                this.buttonsField[i][j].setFocusable(false);

                this.buttonsField[i][j].setActionCommand(i + " " + j);
                this.buttonsField[i][j].addActionListener(this);

            }
        }
    }

    public void changeButtons() {
        int[][] changedConfigMatrix = this.gp.getChangedConfigMatrix();
        boolean gameOver = true;
        for (int i = 0; i < this.sizeOfGame; i++) {
            for (int j = 0; j < this.sizeOfGame; j++) {
                switch (changedConfigMatrix[i][j]) {
                    case 0:
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/black.png"));
                        this.buttonsField[i][j].setRolloverIcon(new ImageIcon("images/grey.png"));
                        this.buttonsField[i][j].setPressedIcon(new ImageIcon("images/grey_boom.png"));
                        break;
                    case 1:
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/light_yellow.png"));
                        this.buttonsField[i][j].setRolloverIcon(new ImageIcon("images/yellow.png"));
                        this.buttonsField[i][j].setPressedIcon(new ImageIcon("images/yellow_boom.png"));
                        gameOver = false;
                        break;
                    case 2:
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/light_red.png"));
                        this.buttonsField[i][j].setRolloverIcon(new ImageIcon("images/red.png"));
                        this.buttonsField[i][j].setPressedIcon(new ImageIcon("images/red_boom.png"));
                        gameOver = false;
                        break;
                }
            }
        }
        if (gameOver) {
            this.gp.computeScore();
            this.stopGame();
            this.congrat();
            this.gp = null;
            this.mm.changeSaveLoadState();
            this.mm.changeShowSolutionState();
            this.mm.changeRestartState();
        }
    }

    public void showSolution() {
        int[][] solution = this.gp.getCurrentSolution();
        int[][] changedConfigMatrix = this.gp.getChangedConfigMatrix();
        for (int i = 0; i < this.sizeOfGame; i++) {
            for (int j = 0; j < this.sizeOfGame; j++) {
                if (solution[i][j] == 1) {
                    if (changedConfigMatrix[i][j] == 0) {
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/sol_black.png"));
                    } else if (changedConfigMatrix[i][j] == 1) {
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/sol_light.png"));
                    } else {
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/sol_red.png"));
                    }
                } else if (solution[i][j] == 2) {
                    if (changedConfigMatrix[i][j] == 0) {
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/sol_black2.png"));
                    } else if (changedConfigMatrix[i][j] == 1) {
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/sol_light2.png"));
                    } else {
                        this.buttonsField[i][j].setIcon(new ImageIcon("images/sol_red2.png"));
                    }
                }
            }
        }
    }

    public void congrat() {
        ResultsHandler rh = new ResultsHandler(this.sizeOfGame, this.quantityOfStates);
        Result result = new Result("anonym", this.gp.getScore(), this.gp.getUserSteps(), this.gp.getOptimalSteps());
        if (rh.isBetweenWinners(result)) {
            String name = JOptionPane.showInputDialog(null,
                    this.langScheme.get("finishMessText1") + this.gp.getScore()
                            + this.langScheme.get("finishMessText2"),
                    this.langScheme.get("finishMessTitle"), JOptionPane.INFORMATION_MESSAGE);
            if (!(name == null) && !(name.equals(""))) {
                result.setGamerName(name);
            }
            rh.updateResultsList(result);
        }
        SwingUtilities.invokeLater(() -> new ShowResults(this.sizeOfGame, this.quantityOfStates));

    }

    public void actionPerformed(ActionEvent ae) {
        String[] adress = ae.getActionCommand().split(" ");
        int row = Integer.parseInt(adress[0]);
        int col = Integer.parseInt(adress[1]);

        this.gp.makeOneStep(row, col);
        this.changeButtons();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        SwingUtilities.invokeLater(() -> new Game("The Lights Out Puzzle"));
        // System.out.println("\nThe configuration matrix is:");
        // printMatrix(InitialConfig.getConfigFromFile());

    }
    /*
     * public static void printMatrix(int[][] matrix) { for (int row[] : matrix)
     * { for (int elem : row) System.out.printf("%1d ", elem);
     * System.out.println(); } }
     */

    class MyMenu extends JMenuBar implements ActionListener {

        private JMenu jmGame;
        private JMenu jmSettings;
        private JMenu jmSize;
        private JMenu jmStates;
        private JMenu jmLang;
        private JMenuItem jmiNew;
        private JMenuItem jmiSave;
        private JMenuItem jmiLoad;
        private JMenuItem jmiShowSol;
        private JMenuItem jmiReset;
        private JMenuItem jmiExit;
        private ButtonGroup groupOfSize;
        private ButtonGroup groupOfStates;
        private JRadioButtonMenuItem jmiSize1;
        private JRadioButtonMenuItem jmiSize2;
        private JRadioButtonMenuItem jmiSize3;
        private JRadioButtonMenuItem jmiSize4;
        private JRadioButtonMenuItem jmiStates1;
        private JRadioButtonMenuItem jmiStates2;
        private int newSizeOfGame;
        private int newQuantityOfStates;

        MyMenu() {
            super();

            this.newSizeOfGame = Game.this.sizeOfGame;
            this.newQuantityOfStates = Game.this.quantityOfStates;

            // Create the Game menu.

            this.jmGame = new JMenu(Game.this.langScheme.get("menuLabel1"));
            // JMenu
            this.jmiNew = new JMenuItem(Game.this.langScheme.get("startGameLabel"));
            this.jmiReset = new JMenuItem(Game.this.langScheme.get("restartGameLabel"));
            this.changeRestartState();

            this.jmiSave = new JMenuItem(Game.this.langScheme.get("saveLabel"));
            this.jmiLoad = new JMenuItem(Game.this.langScheme.get("loadLabel"));
            this.changeSaveLoadState();

            this.jmiShowSol = new JMenuItem(Game.this.langScheme.get("showSolLabel"));
            this.changeShowSolutionState();

            this.jmiExit = new JMenuItem(Game.this.langScheme.get("exitLabel"));

            this.jmSettings = new JMenu(Game.this.langScheme.get("menuLabel2"));

            this.jmSize = new JMenu(Game.this.langScheme.get("menuLabel5"));
            // Use radio buttons for the size setting
            this.jmiSize1 = new JRadioButtonMenuItem("3 x 3");
            this.jmiSize1.addActionListener((ae) -> {
                this.newSizeOfGame = 3;
                this.changeCurrentGame();
            });
            this.jmiSize2 = new JRadioButtonMenuItem("5 x 5", true);
            this.jmiSize2.addActionListener((ae) -> {
                this.newSizeOfGame = 5;
                this.changeCurrentGame();
            });
            this.jmiSize3 = new JRadioButtonMenuItem("7 x 7");
            this.jmiSize3.addActionListener((ae) -> {
                this.newSizeOfGame = 7;
                this.changeCurrentGame();
            });
            this.jmiSize4 = new JRadioButtonMenuItem(Game.this.langScheme.get("menuLabel6"));
            this.jmiSize4.addActionListener((ae) -> {
                String[] possibleValues = { "3 x 3", "4 x 4", "5 x 5", "6 x 6", "7 x 7", "8 x 8", "9 x 9", "10 x 10",
                                            "11 x 11", "12 x 12" };
                String value = (String)JOptionPane.showInputDialog(null, Game.this.langScheme.get("sizeDialogText"),
                        Game.this.langScheme.get("sizeDialogTitle"), JOptionPane.QUESTION_MESSAGE, null, possibleValues,
                        possibleValues[0]);
                if (value != null) {
                    int posInStr = 0;
                    while (value.charAt(posInStr) >= '0' && value.charAt(posInStr) <= '9') {
                        posInStr++;
                    }
                    this.newSizeOfGame = Integer.parseInt(value.substring(0, posInStr));
                }
                this.changeCurrentGame();
            });
            this.jmSize.add(this.jmiSize1);
            this.jmSize.add(this.jmiSize2);
            this.jmSize.add(this.jmiSize3);
            this.jmSize.addSeparator();
            this.jmSize.add(this.jmiSize4);
            // Create button group for the radio button
            this.groupOfSize = new ButtonGroup();
            this.groupOfSize.add(this.jmiSize1);
            this.groupOfSize.add(this.jmiSize2);
            this.groupOfSize.add(this.jmiSize3);
            this.groupOfSize.add(this.jmiSize4);

            this.jmStates = new JMenu(Game.this.langScheme.get("menuLabel7"));
            this.jmiStates1 = new JRadioButtonMenuItem(Game.this.langScheme.get("menuLabel8"), true);
            this.jmiStates1.addActionListener((ae) -> {
                this.newQuantityOfStates = 2;
                this.changeCurrentGame();
            });
            this.jmiStates2 = new JRadioButtonMenuItem(Game.this.langScheme.get("menuLabel9"));
            this.jmiStates2.addActionListener((ae) -> {
                this.newQuantityOfStates = 3;
                this.changeCurrentGame();
            });
            this.jmStates.add(this.jmiStates1);
            this.jmStates.add(this.jmiStates2);
            this.groupOfStates = new ButtonGroup();
            this.groupOfStates.add(this.jmiStates1);
            this.groupOfStates.add(this.jmiStates2);

            this.jmLang = new JMenu(Game.this.langScheme.get("interfaceLang"));
            JRadioButtonMenuItem jmiLang1 = new JRadioButtonMenuItem("English");
            JRadioButtonMenuItem jmiLang2 = new JRadioButtonMenuItem("Русский");
            JRadioButtonMenuItem jmiLang3 = new JRadioButtonMenuItem("Slovenský");
            try {
                if (LanguageSwitcher.RU.isCurrent()) {
                    jmiLang2.setSelected(true);
                } else if (LanguageSwitcher.SK.isCurrent()) {
                    jmiLang3.setSelected(true);
                } else {
                    jmiLang1.setSelected(true);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                jmiLang1.setSelected(true);
            }
            jmiLang1.addActionListener((ae) -> {
                Game.this.langScheme = LanguageSwitcher.EN.getLangScheme();
                try (PrintWriter pw = new PrintWriter(new FileWriter("langs/config.conf"))) {
                    pw.println("currentLanguage=EN");
                } catch (IOException exc) {
                    System.err.println("I/O Error: " + exc);
                }
                this.refreshInterfaceLang();
            });
            jmiLang2.addActionListener((ae) -> {
                Game.this.langScheme = LanguageSwitcher.RU.getLangScheme();
                try (PrintWriter pw = new PrintWriter(new FileWriter("langs/config.conf"))) {
                    pw.println("currentLanguage=RU");
                } catch (IOException exc) {
                    System.err.println("I/O Error: " + exc);
                }
                this.refreshInterfaceLang();
            });
            jmiLang3.addActionListener((ae) -> {
                Game.this.langScheme = LanguageSwitcher.SK.getLangScheme();
                try (PrintWriter pw = new PrintWriter(new FileWriter("langs/config.conf"))) {
                    pw.println("currentLanguage=SK");
                } catch (IOException exc) {
                    System.err.println("I/O Error: " + exc);
                }
                this.refreshInterfaceLang();
            });
            this.jmLang.add(jmiLang1);
            this.jmLang.add(jmiLang2);
            this.jmLang.add(jmiLang3);
            ButtonGroup groupOfLang = new ButtonGroup();
            groupOfLang.add(jmiLang1);
            groupOfLang.add(jmiLang2);
            groupOfLang.add(jmiLang3);

            this.jmGame.add(this.jmiNew);
            this.jmGame.add(this.jmiReset);
            this.jmGame.add(this.jmiSave);
            this.jmGame.add(this.jmiLoad);
            this.jmGame.add(this.jmiShowSol);
            this.jmGame.addSeparator();
            this.jmGame.add(this.jmiExit);
            this.jmSettings.add(this.jmSize);
            this.jmSettings.add(this.jmStates);
            this.jmSettings.addSeparator();
            this.jmSettings.add(this.jmLang);
            add(this.jmGame);
            add(this.jmSettings);

            // Add action listeners for the menu items.
            this.jmiNew.addActionListener(this);
            this.jmiReset.addActionListener(this);
            // jmSize.addActionListener(this);
            // jmStates.addActionListener(this);
            this.jmiSave.addActionListener(this);
            this.jmiLoad.addActionListener(this);
            this.jmiShowSol.addActionListener(this);
            this.jmiExit.addActionListener(this);
        }

        /**
         * 
         */
        private static final long serialVersionUID = 7390736491903045087L;

        private void changeSaveLoadState() {
            if (Game.this.gp == null) {
                if (Game.this.sLoader.isSavedGame()) {
                    this.jmiSave.setEnabled(false);
                    this.jmiLoad.setEnabled(true);
                } else {
                    this.jmiSave.setEnabled(false);
                    this.jmiLoad.setEnabled(false);
                }
            } else {
                if (Game.this.sLoader.isSavedGame()) {
                    this.jmiSave.setEnabled(true);
                    this.jmiLoad.setEnabled(true);
                } else {
                    this.jmiSave.setEnabled(true);
                    this.jmiLoad.setEnabled(false);
                }
            }
        }

        private void changeShowSolutionState() {
            if (Game.this.gp == null) {
                this.jmiShowSol.setEnabled(false);
            } else {
                this.jmiShowSol.setEnabled(true);
            }
        }

        private void changeRestartState() {
            if (Game.this.gp == null) {
                this.jmiReset.setEnabled(false);
            } else {
                this.jmiReset.setEnabled(true);
            }
        }

        private void refreshInterfaceLang() {
            if (Game.this.displayingStartWindow) {
                Game.this.setSize(Integer.parseInt(Game.this.langScheme.get("startWindowWidth")), 350);
                Game.this.jp.setPreferredSize(
                        new Dimension(Integer.parseInt(Game.this.langScheme.get("startWindowWidth")), 350));
                this.setSize(Integer.parseInt(Game.this.langScheme.get("startWindowWidth")), 350);
                Game.this.lab1.setText(Game.this.langScheme.get("startWindowLabel1"));
                Game.this.lab3.setText(Game.this.langScheme.get("startWindowLabel2") + Game.this.sizeOfGame + "x"
                        + Game.this.sizeOfGame + Game.this.langScheme.get("startWindowLabel3")
                        + Game.this.quantityOfStates + Game.this.langScheme.get("startWindowLabel4"));
            }
            this.jmGame.setText(Game.this.langScheme.get("menuLabel1"));
            this.jmiNew.setText(Game.this.langScheme.get("startGameLabel"));
            this.jmiReset.setText(Game.this.langScheme.get("restartGameLabel"));
            this.jmiSave.setText(Game.this.langScheme.get("saveLabel"));
            this.jmiLoad.setText(Game.this.langScheme.get("loadLabel"));
            this.jmiShowSol.setText(Game.this.langScheme.get("showSolLabel"));
            this.jmiExit.setText(Game.this.langScheme.get("exitLabel"));

            this.jmSettings.setText(Game.this.langScheme.get("menuLabel2"));
            this.jmSize.setText(Game.this.langScheme.get("menuLabel5"));
            this.jmiSize4.setText(Game.this.langScheme.get("menuLabel6"));
            this.jmStates.setText(Game.this.langScheme.get("menuLabel7"));
            this.jmiStates1.setText(Game.this.langScheme.get("menuLabel8"));
            this.jmiStates2.setText(Game.this.langScheme.get("menuLabel9"));
            this.jmLang.setText(Game.this.langScheme.get("interfaceLang"));
        }

        private boolean changeCurrentGame() {
            if (Game.this.displayingStartWindow) {
                Game.this.sizeOfGame = this.newSizeOfGame;
                Game.this.quantityOfStates = this.newQuantityOfStates;
                Game.this.sLoader = new SaveLoad(Game.this.sizeOfGame, Game.this.quantityOfStates);
                Game.this.lab3.setText(Game.this.langScheme.get("startWindowLabel2") + Game.this.sizeOfGame + "x"
                        + Game.this.sizeOfGame + Game.this.langScheme.get("startWindowLabel3")
                        + Game.this.quantityOfStates + Game.this.langScheme.get("startWindowLabel4"));
                this.changeSaveLoadState();
            } else if (Game.this.displayingGameOverWindow) {
                Game.this.sizeOfGame = this.newSizeOfGame;
                Game.this.quantityOfStates = this.newQuantityOfStates;
                Game.this.sLoader = new SaveLoad(Game.this.sizeOfGame, Game.this.quantityOfStates);
                Game.this.setStartBackground();
            } else {
                int value = JOptionPane.showConfirmDialog(null, Game.this.langScheme.get("interruptMessage"),
                        Game.this.langScheme.get("warningTitle"), JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (value == 0) {
                    Game.this.sizeOfGame = this.newSizeOfGame;
                    Game.this.quantityOfStates = this.newQuantityOfStates;
                    Game.this.sLoader = new SaveLoad(Game.this.sizeOfGame, Game.this.quantityOfStates);
                    Game.this.setStartBackground();
                    Game.this.gp = null;
                    this.changeSaveLoadState();
                    this.changeShowSolutionState();
                    this.changeRestartState();
                } else {
                    this.groupOfSize.clearSelection();
                    this.groupOfStates.clearSelection();
                    switch (Game.this.sizeOfGame) {
                        case 3:
                            this.jmiSize1.setSelected(true);
                            break;
                        case 5:
                            this.jmiSize2.setSelected(true);
                            break;
                        case 7:
                            this.jmiSize3.setSelected(true);
                            break;
                        default:
                            this.jmiSize4.setSelected(true);
                            break;
                    }
                    switch (Game.this.quantityOfStates) {
                        case 2:
                            this.jmiStates1.setSelected(true);
                            break;
                        case 3:
                            this.jmiStates2.setSelected(true);
                            break;
                    }
                    return false;
                }
            }
            return true;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            // Get the action command from the menu selection.
            String comStr = ae.getActionCommand();

            if (comStr.equals(Game.this.langScheme.get("startGameLabel"))) {
                if (!Game.this.displayingStartWindow && !Game.this.displayingGameOverWindow) {
                    System.out.println("1. " + !Game.this.displayingStartWindow);
                    System.out.println("2. " + (Game.this.gp != null));
                    int value = JOptionPane.showConfirmDialog(null, Game.this.langScheme.get("interruptMessage"),
                            Game.this.langScheme.get("warningTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (value == 0) {
                        Game.this.startGame();
                    }
                } else {
                    Game.this.startGame();
                }
            } else if (comStr.equals(Game.this.langScheme.get("restartGameLabel"))) {
                if ((Game.this.gp != null) && (Game.this.gp.getStartConfigMatrix().length == Game.this.sizeOfGame)) {
                    int value = JOptionPane.showConfirmDialog(null, Game.this.langScheme.get("interruptMessage"),
                            Game.this.langScheme.get("warningTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (value == 0) {
                        Game.this.resetGame();
                    }
                }
            } else if (comStr.equals(Game.this.langScheme.get("saveLabel"))) {
                if (Game.this.sLoader.isSavedGame()) {
                    int value = JOptionPane.showConfirmDialog(null, Game.this.langScheme.get("rewriteSavesMessage"),
                            Game.this.langScheme.get("warningTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (value == 0) {
                        Game.this.sLoader.saveGame(Game.this.gp);
                        this.changeSaveLoadState();
                        this.changeShowSolutionState();
                    }
                } else {
                    Game.this.sLoader.saveGame(Game.this.gp);
                    this.changeSaveLoadState();
                    this.changeShowSolutionState();
                }
            } else if (comStr.equals(Game.this.langScheme.get("loadLabel"))) {
                if (Game.this.gp == null) {
                    Game.this.displayingStartWindow = false;
                    Game.this.gp = Game.this.sLoader.loadSavedGame();
                    Game.this.setGame();
                } else {
                    Game.this.gp = Game.this.sLoader.loadSavedGame();
                    Game.this.changeButtons();
                }
                this.changeShowSolutionState();
                this.changeSaveLoadState();
                this.changeRestartState();
            } else if (comStr.equals(Game.this.langScheme.get("showSolLabel"))) {
                Game.this.gp.findCurrentSolution();
                Game.this.showSolution();
            } else if (comStr.equals(Game.this.langScheme.get("exitLabel"))) {
                System.exit(0);
            }
        }
    }
}
