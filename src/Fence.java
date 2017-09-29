import java.awt.*;

/**
 * @author Quintin Dwight
 */
public class Fence extends AbstractCell {

    public Fence(int x, int y) {

        super(x, y);
    }

    @Override
    public void draw(final int x_offset, final int y_offset, final int width, final int height, Graphics g) {

        final int
            top_x = x_offset + 1 + (y * (width  + 1)),
            top_y = y_offset + 1 + (x * (height + 1));

        g.setColor(Color.YELLOW);
        g.fillRect(top_x, top_y, width, height);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}