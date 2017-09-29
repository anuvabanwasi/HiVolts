import javax.swing.JFrame;

public class Main {

	final static int DISPLAY_WIDTH = 500, DISPLAY_HEIGHT = 500;

	public static void main(String[] args) {

		// Bring up a JFrame with squares to represent the cells
		JFrame f = new JFrame("HiVolts");

		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);

		GamePanel panel = new GamePanel(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.add(panel);

		f.setVisible(true);

		f.requestFocusInWindow();
		panel.requestFocusInWindow();
	}
}