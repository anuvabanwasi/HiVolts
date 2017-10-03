import java.awt.image.BufferedImage;

/**
 * @author Quintin Dwight
 */
public class Mho extends AbstractCell {

    BufferedImage image;

    public Mho(Coordinate position) {

        super("Mho.png", position);
    }

    @Override
    public boolean killsPlayer() {
        return true;
    }
}
