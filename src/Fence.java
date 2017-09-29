import java.awt.*;

public class Fence extends Cell {

    public Fence(int x, int y) {

        super(x, y);
    }

    @Override
    public void draw(final int x_offset, final int y_offset, final int width, final int height, Graphics g) {

        final int
            x = x_offset + 1 + (myY * (width  + 1)),
            y = y_offset + 1 + (myX * (height + 1));

        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}