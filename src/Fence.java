/**
 * @author Quintin Dwight
 */
public class Fence extends AbstractCell {

    public Fence(int x, int y) {

        super("Fence.png", x, y);
    }

    @Override
    public boolean killsPlayer() {
        return true;
    }
}