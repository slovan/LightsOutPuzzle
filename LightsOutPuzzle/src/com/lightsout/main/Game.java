package com.lightsout.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.lightsout.twoStates.InitialConfig;

public class Game extends JFrame implements ActionListener {

	private JPanel jp;
	private JButton[][] buttonsField;
	private int[][] configMatrix;

	private static final long serialVersionUID = 1L;

	public Game(String title) throws HeadlessException {
		super(title);
		buttonsField = new JButton[5][5];
		configMatrix = InitialConfig.getConfigMatrix();

		// Give the frame an initial size.
		setSize(870, 600);

		// Terminate the program when the user closes the application.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the frame.
		setVisible(true);

		setLayout(new FlowLayout());
		jp = new JPanel();
		jp.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		jp.setPreferredSize(new Dimension(35 * 5 + 2, 35 * 5 + 2));
		jp.setBackground(Color.GREEN);
		jp.setLayout(new GridLayout(5, 5));
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (configMatrix[i][j] == 1) {
					buttonsField[i][j] = new JButton(new ImageIcon("src/orange.png"));
				} else {
					buttonsField[i][j] = new JButton(new ImageIcon("src/black.png"));
				}
				jp.add(buttonsField[i][j]);
				buttonsField[i][j].setBorder(BorderFactory.createEmptyBorder());
				buttonsField[i][j].setContentAreaFilled(false);
				buttonsField[i][j].setRolloverIcon(new ImageIcon("src/grey.png"));
				buttonsField[i][j].setPressedIcon(new ImageIcon("src/orange2.png"));
				//buttonsField[i][j].setSelectedIcon(new ImageIcon("src/orange.png"));
				buttonsField[i][j].setActionCommand(i + " " + j);
				buttonsField[i][j].addActionListener(this);
			}
		}
		

		add(jp);
	}
	
	public void changeButtons() {
		boolean gameOver = true;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (configMatrix[i][j] == 1) {
					buttonsField[i][j].setIcon(new ImageIcon("src/orange.png"));
					gameOver = false;
				} else {
					buttonsField[i][j].setIcon(new ImageIcon("src/black.png"));
				}
			}
		}
		if (gameOver) congrat();
	}
	
	public void congrat() {
		JDialog.setDefaultLookAndFeelDecorated(true);
		String result = JOptionPane.showInputDialog(null, "Please, enter your name:", "Congratulations!",  JOptionPane.INFORMATION_MESSAGE);
		/*int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",
		        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) {
		      System.out.println("No button clicked");
		    } else if (response == JOptionPane.YES_OPTION) {
		      System.out.println("Yes button clicked");
		    } else if (response == JOptionPane.CLOSED_OPTION) {
		      System.out.println("JOptionPane closed");
		    }*/
		System.out.println(result + " has finished the game!");
	}

	public void actionPerformed(ActionEvent ae) {
		String[] adress = ae.getActionCommand().split(" ");
		int row = Integer.parseInt(adress[0]);
		int col = Integer.parseInt(adress[1]);
		
		configMatrix[row][col] = ++configMatrix[row][col] % 2;
		if (row - 1 >= 0)
			configMatrix[row - 1][col] = ++configMatrix[row - 1][col] % 2;
		if (row + 1 < 5)
			configMatrix[row + 1][col] = ++configMatrix[row + 1][col] % 2;
		if (col - 1 >= 0)
			configMatrix[row][col - 1] = ++configMatrix[row][col - 1] % 2;
		if (col + 1 < 5)
			configMatrix[row][col + 1] = ++configMatrix[row][col + 1] % 2;
		changeButtons();
	}
	
	public static void main(String[] args) {
		System.out.println("\nThe configuration matrix is:");
		new InitialConfig();
		printMatrix(InitialConfig.getConfigMatrix());
		SwingUtilities.invokeLater(() -> new Game("The Lights Out Puzzle"));
		
	}
	
	public static void printMatrix(int[][] matrix) {
		for (int row[] : matrix) {
			for (int elem : row)
				System.out.printf("%1d ", elem);
			System.out.println();
		}
	}
}
