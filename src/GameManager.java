import javax.swing.*;

/**
 * Creates the JFrame and initializes all of the components for the game.
 * Handles switching frames from important game events.
 *
 * @author Quintin Dwight
 */
public class GameManager {

    private static HomePanel homePanel;
    private static GameOverPanel gameOverPanel;
    private static GamePanel gamePanel;
    private static YouWinPanel youWinPanel;
    private static JFrame f;

    public static void init() {

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
        homePanel.addKeyListener(homePanel);

        gamePanel = new GamePanel(DISPLAY_WIDTH, DISPLAY_HEIGHT);

        gameOverPanel = new GameOverPanel(DISPLAY_WIDTH, DISPLAY_HEIGHT);

        youWinPanel = new YouWinPanel(DISPLAY_WIDTH, DISPLAY_HEIGHT);

        f.repaint();

        f.setVisible(true);
        f.requestFocusInWindow();

        homePanel.requestFocusInWindow();
    }

    /**
     * Called from the home panel when any key is pressed.
     * Transitions from the home panel to the game panel.
     */
    public static void keyPressedOnHome() {

        f.remove(homePanel);
        homePanel.removeKeyListener(homePanel);

        f.add(gamePanel);
        gamePanel.restart();
        gamePanel.addKeyListener(gamePanel);

        f.repaint();

        gamePanel.requestFocusInWindow();
    }

    /**
     * Called from the game over panel when any key is pressed.
     * Transitions from the game over panel to the home panel.
     */
    public static void keyPressedOnGameOver() {

        f.remove(gameOverPanel);
        gameOverPanel.removeKeyListener(gameOverPanel);

        f.add(homePanel);
        homePanel.addKeyListener(homePanel);

        f.repaint();

        homePanel.requestFocusInWindow();
    }

    /**
     * Game over, called from the game panel.
     * Transitions from the game panel to the game over panel.
     */
    public static void gameOver() {

        f.remove(gamePanel);
        gamePanel.removeKeyListener(gamePanel);

        f.add(gameOverPanel);
        gameOverPanel.addKeyListener(gameOverPanel);

        f.repaint();

        gameOverPanel.requestFocusInWindow();
    }

    /**
     * You win, called from the game manager.
     * Transitions from the game panel to the you win panel.
     */
    public static void youWin() {

        f.remove(gamePanel);
        gamePanel.removeKeyListener(gamePanel);

        f.add(youWinPanel);
        youWinPanel.addKeyListener(youWinPanel);

        f.repaint();

        youWinPanel.requestFocusInWindow();
    }

    /**
     * Called from the you win panel when a key is pressed.
     * This transitions from there to the home panel.
     */
    public static void keyPressedOnYouWin() {

        f.remove(youWinPanel);
        youWinPanel.removeKeyListener(youWinPanel);

        f.add(homePanel);
        homePanel.addKeyListener(homePanel);

        f.repaint();

        homePanel.requestFocusInWindow();
    }
}
