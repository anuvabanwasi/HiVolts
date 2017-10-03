/**
 * @author Quintin Dwight
 */
public class Smiley extends AbstractCell {

    public Smiley(Coordinate position) {

        super("Smiley.png", position);
    }

    @Override
    public boolean killsPlayer() {
        return false;
    }
}
