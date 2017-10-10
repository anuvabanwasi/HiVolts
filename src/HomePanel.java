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

    private Font font;

    public HomePanel(final int DISPLAY_WIDTH, final int DISPLAY_HEIGHT) {

        this.DISPLAY_WIDTH = DISPLAY_WIDTH;
        this.DISPLAY_HEIGHT = DISPLAY_HEIGHT;

        init();
    }

    private void init() {

        font = new Font("Verdana", Font.PLAIN, 24);

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

        g2.setFont(font);
        FontMetrics fontMetrics = g2.getFontMetrics(font);

        g2.setColor(Color.WHITE);

        drawCenteredText(g2, fontMetrics, TOP_TEXT, -32);
        drawCenteredText(g2, fontMetrics, MIDDLE_TEXT, 0);
        drawCenteredText(g2, fontMetrics, BOTTOM_TEXT, +32);
    }

    private void drawCenteredText(Graphics2D g2, FontMetrics fm, String text, final int yoffset) {

        g2.drawString(text, DISPLAY_WIDTH/2 - fm.stringWidth(text)/2, DISPLAY_WIDTH/2 + yoffset);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        Main.keyPressedOnHome();
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override public void keyReleased(KeyEvent e) {}
}
