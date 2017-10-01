import java.awt.image.BufferedImage;

/**
 * @author Quintin Dwight
 */
public class Mho extends AbstractCell {

    BufferedImage image;

    public Mho(int x, int y) {

        super("Mho.png", x, y);
    }

    @Override
    public boolean killsPlayer() {
        return true;
    }
}
