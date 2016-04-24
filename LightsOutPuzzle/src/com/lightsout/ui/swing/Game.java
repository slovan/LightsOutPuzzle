package com.lightsout.ui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import com.lightsout.core.GameProcess;

public class Game extends JFrame implements ActionListener {
	private GameProcess gp;
	private int sizeOfGame; // size of game
	private int quantityOfStates;
	private JPanel jp;
	private MyMenu mm;
	private JButton[][] buttonsField;


	private static final long serialVersionUID = 1L;

	public Game(String title) {
		super(title);
		this.sizeOfGame = 5;
		this.quantityOfStates = 2;
		mm = new MyMenu();
		setJMenuBar(mm);

		// Give the frame an initial size.
		setSize(300, 330);

		// Terminate the program when the user closes the application.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the frame.
		setVisible(true);

		setLayout(new FlowLayout());
		jp = new JPanel();
		JButton startGame = new JButton(new ImageIcon("images/game-start.png"));
		startGame.setFocusable(false);
		startGame.setBorder(BorderFactory.createEmptyBorder());
		startGame.setContentAreaFilled(false);
		startGame.addActionListener((ae) -> startGame());
		jp.add(startGame);
		add(jp);
	}
	
	public void startGame() {
		InitialConfig initialConfig = new InitialConfig(sizeOfGame, quantityOfStates);
		this.gp = new GameProcess(initialConfig.getRandomConfig(), quantityOfStates);
		setGame();
	}
	public void resetGame() {
		this.gp = new GameProcess(gp.getStartConfigMatrix(), quantityOfStates);
		setGame();
	}
	public void stopGame() {
		getContentPane().removeAll();
		setSize(350, 230);
		setLayout(new FlowLayout());
		jp = new JPanel();
		JLabel label = new JLabel("", new ImageIcon("images/game-over.png"), JLabel.CENTER);
		jp.add(label);
		add(jp);
	}
	public void setGame() {
		int[][] changedConfigMatrix = gp.getChangedConfigMatrix();
		this.buttonsField = new JButton[sizeOfGame][sizeOfGame];

		jp.removeAll();
		jp.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		jp.setPreferredSize(new Dimension(35 * sizeOfGame + 2, 35 * sizeOfGame + 2));
		jp.setBackground(Color.GREEN);
		jp.setLayout(new GridLayout(sizeOfGame, sizeOfGame));
		this.setSize(35 * sizeOfGame + 130, 35 * sizeOfGame + 75);

		for (int i = 0; i < sizeOfGame; i++) {
			for (int j = 0; j < sizeOfGame; j++) {
				switch (changedConfigMatrix[i][j]) {
				case 0:
					buttonsField[i][j] = new JButton(new ImageIcon("images/black.png"));
					buttonsField[i][j].setRolloverIcon(new ImageIcon("images/grey.png"));
					buttonsField[i][j].setPressedIcon(new ImageIcon("images/grey_boom.png"));
					break;
				case 1:
					buttonsField[i][j] = new JButton(new ImageIcon("images/light_yellow.png"));
					buttonsField[i][j].setRolloverIcon(new ImageIcon("images/yellow.png"));
					buttonsField[i][j].setPressedIcon(new ImageIcon("images/yellow_boom.png"));
					break;
				case 2:
					buttonsField[i][j] = new JButton(new ImageIcon("images/light_red.png"));
					buttonsField[i][j].setRolloverIcon(new ImageIcon("images/red.png"));
					buttonsField[i][j].setPressedIcon(new ImageIcon("images/red_boom.png"));
					break;
				}
				
				jp.add(buttonsField[i][j]);
				buttonsField[i][j].setBorder(BorderFactory.createEmptyBorder());
				buttonsField[i][j].setContentAreaFilled(false);
				buttonsField[i][j].setFocusable(false);
				
				buttonsField[i][j].setActionCommand(i + " " + j);
				buttonsField[i][j].addActionListener(this);
				
			}
		}		
	}

	public void changeButtons() {
		int[][] changedConfigMatrix = gp.getChangedConfigMatrix();
		boolean gameOver = true;
		for (int i = 0; i < sizeOfGame; i++) {
			for (int j = 0; j < sizeOfGame; j++) {
				switch (changedConfigMatrix[i][j]) {
				case 0:
					buttonsField[i][j].setIcon(new ImageIcon("images/black.png"));
					buttonsField[i][j].setRolloverIcon(new ImageIcon("images/grey.png"));
					buttonsField[i][j].setPressedIcon(new ImageIcon("images/grey_boom.png"));
					break;
				case 1:
					buttonsField[i][j].setIcon(new ImageIcon("images/light_yellow.png"));
					buttonsField[i][j].setRolloverIcon(new ImageIcon("images/yellow.png"));
					buttonsField[i][j].setPressedIcon(new ImageIcon("images/yellow_boom.png"));
					gameOver = false;
					break;
				case 2:
					buttonsField[i][j].setIcon(new ImageIcon("images/light_red.png"));
					buttonsField[i][j].setRolloverIcon(new ImageIcon("images/red.png"));
					buttonsField[i][j].setPressedIcon(new ImageIcon("images/red_boom.png"));
					gameOver = false;
					break;
				}
			}
		}
		if (gameOver){
			gp.computeScore();
			stopGame();
			congrat();
		}
	}
	
	public void showSolution() {
		int[][] solution = gp.getCurrentSolution();
		int[][] changedConfigMatrix = gp.getChangedConfigMatrix();
		for (int i = 0; i < sizeOfGame; i++) {
			for (int j = 0; j < sizeOfGame; j++) {
				if (solution[i][j] == 1) {
					if (changedConfigMatrix[i][j] == 0) {
						buttonsField[i][j].setIcon(new ImageIcon("images/sol_black.png"));
					} else if (changedConfigMatrix[i][j] == 1) {
						buttonsField[i][j].setIcon(new ImageIcon("images/sol_light.png"));
					} else {
						buttonsField[i][j].setIcon(new ImageIcon("images/sol_red.png"));
					}						
				} else if (solution[i][j] == 2) {
					if (changedConfigMatrix[i][j] == 0) {
						buttonsField[i][j].setIcon(new ImageIcon("images/sol_black2.png"));
					} else if (changedConfigMatrix[i][j] == 1) {
						buttonsField[i][j].setIcon(new ImageIcon("images/sol_light2.png"));
					} else {
						buttonsField[i][j].setIcon(new ImageIcon("images/sol_red2.png"));
					}
				}
			}
		}
	}

	public void congrat() {
		String name = JOptionPane.showInputDialog(null, "Please, enter your name:", 
				"Congratulations!", JOptionPane.INFORMATION_MESSAGE);
		if ((name == null) || (name.equals("")))
			name = "anonym";
		System.out.println(name + " has finished the game!");
		System.out.println("Optimal steps: " + gp.getOptimalSteps());
		System.out.println("User steps: " + gp.getUserSteps());
		System.out.println("Score: " + gp.getScore());
		ResultsHandler rh = new ResultsHandler(sizeOfGame, quantityOfStates);
		Result result = new Result(name, gp.getScore(), gp.getUserSteps(), gp.getOptimalSteps());
		if(rh.isBetweenWinners(result))
			rh.updateResultsList(result);
		SwingUtilities.invokeLater(() -> new ShowResults(sizeOfGame));

		
	}

	public void actionPerformed(ActionEvent ae) {
		String[] adress = ae.getActionCommand().split(" ");
		int row = Integer.parseInt(adress[0]);
		int col = Integer.parseInt(adress[1]);
		
		gp.makeOneStep(row, col);
		changeButtons();
	}

	public static void main(String[] args) {
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) { }
		SwingUtilities.invokeLater(() -> new Game("The Lights Out Puzzle"));
		//System.out.println("\nThe configuration matrix is:");
		//printMatrix(InitialConfig.getConfigFromFile());
		

	}
/*
	public static void printMatrix(int[][] matrix) {
		for (int row[] : matrix) {
			for (int elem : row)
				System.out.printf("%1d ", elem);
			System.out.println();
		}
	}
*/

	class MyMenu extends JMenuBar implements ActionListener {

		
		MyMenu() {
			super();
			// Create the Game menu.
			
			JMenu jmGame = new JMenu("Game");
			JMenu jmSettings = new JMenu("Settings");
			//JMenu 
			JMenuItem jmiNew = new JMenuItem("New Game");
			JMenuItem jmiReset = new JMenuItem("Reset");
			
			JMenu jmSize = new JMenu("Size of game");
				// Use radio buttons for the size setting
				JRadioButtonMenuItem jmiSize1 = new JRadioButtonMenuItem("3 x 3");
				jmiSize1.addActionListener((ae) -> sizeOfGame = 3);
				JRadioButtonMenuItem jmiSize2 = new JRadioButtonMenuItem("5 x 5", true);
				jmiSize2.addActionListener((ae) -> sizeOfGame = 5);
				JRadioButtonMenuItem jmiSize3 = new JRadioButtonMenuItem("7 x 7");
				jmiSize3.addActionListener((ae) -> sizeOfGame = 7);
				JRadioButtonMenuItem jmiSize4 = new JRadioButtonMenuItem("Customize...");
				jmiSize4.addActionListener((ae) -> {
					String[] possibleValues = { "3 x 3", "4 x 4", "5 x 5", "6 x 6",
							"7 x 7", "8 x 8", "9 x 9", "10 x 10", "11 x 11", "12 x 12"};
					String value = (String) JOptionPane.showInputDialog(null, "Choose desired size:", 
							"Customize size of game", JOptionPane.QUESTION_MESSAGE, null, possibleValues, 
							possibleValues[0]);
					if (value != null) {
						int posInStr = 0;
						while (value.charAt(posInStr) >= '0' && value.charAt(posInStr) <= '9')
						posInStr++;
						sizeOfGame = Integer.parseInt(value.substring(0, posInStr));
					}
				});
				jmSize.add(jmiSize1);
				jmSize.add(jmiSize2);
				jmSize.add(jmiSize3);
				jmSize.addSeparator();
				jmSize.add(jmiSize4);
				//Create button group for the radio button
				ButtonGroup bg1 = new ButtonGroup();
				bg1.add(jmiSize1);
				bg1.add(jmiSize2);
				bg1.add(jmiSize3);
				bg1.add(jmiSize4);
			
			JMenu jmStates = new JMenu("Quantity of states");
				JRadioButtonMenuItem jmiStates1 = new JRadioButtonMenuItem("2-states game", true);
				jmiStates1.addActionListener((ae) -> quantityOfStates = 2);
				JRadioButtonMenuItem jmiStates2 = new JRadioButtonMenuItem("3-states game");
				jmiStates2.addActionListener((ae) -> quantityOfStates = 3);
				jmStates.add(jmiStates1);
				jmStates.add(jmiStates2);
				ButtonGroup bg2 = new ButtonGroup();
				bg2.add(jmiStates1);
				bg2.add(jmiStates2);
			
			
			JMenuItem jmiSave = new JMenuItem("Save");
			JMenuItem jmiLoad = new JMenuItem("Load");
			JMenuItem jmiShowSol = new JMenuItem("Show solution");
			JMenuItem jmiExit = new JMenuItem("Exit");
			jmGame.add(jmiNew);
			jmGame.add(jmiReset);
			jmGame.add(jmiSave);
			jmGame.add(jmiLoad);
			jmGame.add(jmiShowSol);
			jmGame.addSeparator();
			jmGame.add(jmiExit);
			jmSettings.add(jmSize);
			jmSettings.add(jmStates);
			add(jmGame);
			add(jmSettings);
	
			// Add action listeners for the menu items.
			jmiNew.addActionListener(this);
			jmiReset.addActionListener(this);
			jmSize.addActionListener(this);
			jmStates.addActionListener(this);
			jmiSave.addActionListener(this);
			jmiLoad.addActionListener(this);
			jmiShowSol.addActionListener(this);
			jmiExit.addActionListener(this);
		}
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 7390736491903045087L;
	
		@Override
		public void actionPerformed(ActionEvent ae) {
			// Get the action command from the menu selection.
			String comStr = ae.getActionCommand();

			switch (comStr) {
				case "New Game": startGame(); break;
				case "Reset":
					if ((gp != null) && (gp.getStartConfigMatrix().length == Game.this.sizeOfGame)) {
						resetGame();
					} else
						startGame();
					break;
				case "Save": 
					SaveLoad sl = new SaveLoad(sizeOfGame, quantityOfStates);
					sl.saveGame(gp);
					break;
				case "Load":
					sl = new SaveLoad(sizeOfGame, quantityOfStates);
					Game.this.gp = sl.loadSavedGame();
					Game.this.changeButtons();
					break;
				case "Show solution": 
					gp.findCurrentSolution();
					showSolution(); 
					break;
				case "Exit": System.exit(0); break;
				
			}
		}
	}
}
