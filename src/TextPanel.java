import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Displays three lines of centered text.
 * The first line is bigger than all of the others, like a header.
 *
 * @author Quintin Dwight
 */
public class TextPanel extends JPanel implements KeyListener {

    protected final int DISPLAY_WIDTH, DISPLAY_HEIGHT;

    protected final String TOP_TEXT, MIDDLE_TEXT, BOTTOM_TEXT;

    protected static final Font BIG_FONT = new Font("Verdana", Font.PLAIN, 60), SMALL_FONT = new Font("Verdana", Font.PLAIN, 20);

    protected static final int TEXT_SPACING = 32;

    public TextPanel(final int DISPLAY_WIDTH, final int DISPLAY_HEIGHT, final String TOP_TEXT, final String MIDDLE_TEXT, final String BOTTOM_TEXT) {

        this.DISPLAY_WIDTH = DISPLAY_WIDTH;
        this.DISPLAY_HEIGHT = DISPLAY_HEIGHT;

        this.TOP_TEXT = TOP_TEXT;
        this.MIDDLE_TEXT = MIDDLE_TEXT;
        this.BOTTOM_TEXT = BOTTOM_TEXT;

        init();
    }

    private void init() {

        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLACK);

        g2.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

        // Create big and small fonts as well as getting their metrics
        final FontMetrics bigFontMetrics = g2.getFontMetrics(BIG_FONT), smallFontMetrics = g2.getFontMetrics(SMALL_FONT);

        g2.setColor(Color.WHITE);

        g2.setFont(BIG_FONT);
        drawCenteredText(g2, bigFontMetrics, TOP_TEXT, -TEXT_SPACING);
        g2.setFont(SMALL_FONT);
        drawCenteredText(g2, smallFontMetrics, MIDDLE_TEXT, 0);
        drawCenteredText(g2, smallFontMetrics, BOTTOM_TEXT, +TEXT_SPACING);
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

        repaint();

        onAnyKeyPressed();
    }

    /**
     * Called from by the key listener when any key is pressed.
     * Intended to be overidden by subclasses.
     */
    protected void onAnyKeyPressed() {}

    @Override public void keyTyped(KeyEvent e) {}

    @Override public void keyReleased(KeyEvent e) {}
}
