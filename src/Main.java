package com.hivolts;


import javax.swing.JFrame;
/**
 * Main Class that launches the HiVolts game. It creates a JFrame and add a Display JPanel to it. The Display panel is a grid with Cell
 * @author anuva
 *
 */
public class Main {

	public static void main(String[] args) {
		// Bring up a JFrame with squares to represent the cells
		final int DISPLAY_WIDTH = 900;
		final int DISPLAY_HEIGHT = 900;
		JFrame f = new JFrame();
		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		GamePanel display = new GamePanel(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("HiVolts_AQ");
		f.add(display);
		f.setVisible(true);
		f.requestFocusInWindow();
		display.requestFocusInWindow();
	}
}