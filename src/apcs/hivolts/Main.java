package apcs.hivolts;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// Bring up a JFrame with squares to represent the cells
		final int DISPLAY_WIDTH = 500;
		final int DISPLAY_HEIGHT = 500;
		JFrame f = new JFrame();
		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		Display display = new Display(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("HiVolts");
		f.add(display);
		f.setVisible(true);
		f.requestFocusInWindow();
		display.requestFocusInWindow();
	}
}