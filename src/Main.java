import javax.swing.JFrame;

/**
 * @author Anuva Banwasi
 */
public class Main {

	final static int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500;

	public static void main(String[] args) {

		// Bring up a JFrame with squares to represent the cells
		JFrame f = new JFrame("HiVolts");

		f.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);

		GamePanel panel = new GamePanel(WINDOW_WIDTH, WINDOW_HEIGHT);
		f.add(panel);

		f.setVisible(true);

		f.requestFocusInWindow();
		panel.requestFocusInWindow();
	}
}