/**
 * @author Quintin Dwight
 */
public class Fence extends AbstractCell {

    public Fence(Coordinate position) {

        super("Fence.png", position);
    }

    @Override
    public boolean killsPlayer() {
        return true;
    }
}