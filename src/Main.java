import javax.swing.*;

/**
 * Main Class that launches the HiVolts game. It creates a JFrame and add a Display JPanel to it. The Display panel is a grid with Cell
 *
 * @author Anuva
 */
public class Main {

	private static HomePanel homePanel;
	private static GamePanel gamePanel;
	private static JFrame f;

	public static void main(String[] args) {
		// Bring up a JFrame with squares to represent the cells
		final int DISPLAY_WIDTH = 900, DISPLAY_HEIGHT = 900;

		f = new JFrame();
		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);

		f.setLayout(null);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setTitle("HiVolts by Anuva Banwasi and Quintin Dwight");
		f.setResizable(false);

		homePanel = new HomePanel(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.add(homePanel);

		gamePanel = new GamePanel(DISPLAY_WIDTH, DISPLAY_HEIGHT);

		f.setVisible(true);
		f.requestFocusInWindow();

		homePanel.requestFocusInWindow();
	}

	public static void keyPressedOnHome() {

		f.remove(homePanel);

		f.add(gamePanel);

		gamePanel.requestFocusInWindow();
	}
}
