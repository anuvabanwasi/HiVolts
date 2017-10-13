/**
 * Winning panel
 *
 * @author Quintin Dwight
 */
public class YouWinPanel extends TextPanel {

    public YouWinPanel(final int DISPLAY_WIDTH, final int DISPLAY_HEIGHT) {

        super(DISPLAY_WIDTH, DISPLAY_HEIGHT, "Wow, you win!", "Winning streak?", "( press any key )");
    }

    @Override
    protected void onAnyKeyPressed() {

        GameManager.keyPressedOnYouWin();
    }
}
