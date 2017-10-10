import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Quintin
 */
public class HomePanel extends JComponent implements KeyListener {

    private final int DISPLAY_WIDTH, DISPLAY_HEIGHT;

    private final String TOP_TEXT = "Welcome to HiVolts!", MIDDLE_TEXT = "Made by Anuva Banwasi and Quintin Dwight", BOTTOM_TEXT = "( press any key )";

    public HomePanel(final int DISPLAY_WIDTH, final int DISPLAY_HEIGHT) {

        this.DISPLAY_WIDTH = DISPLAY_WIDTH;
        this.DISPLAY_HEIGHT = DISPLAY_HEIGHT;

        init();
    }

    private void init() {

        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);

        addKeyListener(this);

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLACK);

        g2.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

        // Create big and small fonts as well as getting their metrics
        final Font bigFont = new Font("Verdana", Font.PLAIN, 60), smallFont = new Font("Verdana", Font.PLAIN, 20);
        final FontMetrics bigFontMetrics = g2.getFontMetrics(bigFont), smallFontMetrics = g2.getFontMetrics(smallFont);

        g2.setColor(Color.WHITE);

        g2.setFont(bigFont);
        drawCenteredText(g2, bigFontMetrics, TOP_TEXT, -32);
        g2.setFont(smallFont);
        drawCenteredText(g2, smallFontMetrics, MIDDLE_TEXT, 0);
        drawCenteredText(g2, smallFontMetrics, BOTTOM_TEXT, +32);
    }

    /**
     * Draw centered text
     *
     * @param g2 Graphics object used to draw
     * @param fm Font metrics for the font, used to determine size of string
     * @param text Text to display
     * @param y_offset Vertical offset of the text
     */
    private void drawCenteredText(Graphics2D g2, FontMetrics fm, String text, final int y_offset) {

        g2.drawString(text, DISPLAY_WIDTH/2 - fm.stringWidth(text)/2, DISPLAY_WIDTH/2 + y_offset);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        GameManager.keyPressedOnHome();
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override public void keyReleased(KeyEvent e) {}
}
