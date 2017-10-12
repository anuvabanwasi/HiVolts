public class GameOverPanel extends TextPanel {

    public GameOverPanel(final int DISPLAY_WIDTH, final int DISPLAY_HEIGHT) {

        super(DISPLAY_WIDTH, DISPLAY_HEIGHT, "Game Over!", "Better luck next time...", "( press any key )");
    }

    @Override
    protected void onAnyKeyPressed() {

        GameManager.keyPressedOnGameOver();
    }
}
