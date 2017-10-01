/**
 * @author Quintin Dwight
 */
public class Smiley extends AbstractCell {

    public Smiley(int x, int y) {

        super("Smiley.png", x, y);
    }

    @Override
    public boolean killsPlayer() {
        return false;
    }
}
