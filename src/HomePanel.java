public class HomePanel extends TextPanel {

    public HomePanel(final int DISPLAY_WIDTH, final int DISPLAY_HEIGHT) {

        super(DISPLAY_WIDTH, DISPLAY_HEIGHT, "Welcome to HiVolts!", "Made by Quintin Dwight and Anuva Banwasi", "( press any key )");
    }

    @Override
    protected void onAnyKeyPressed() {

        GameManager.keyPressedOnHome();
    }
}
