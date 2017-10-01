import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Quintin Dwight
 * @author Anuva Banwasi
 */
public abstract class AbstractCell {

	protected int x, y;
	protected BufferedImage image;

	public AbstractCell(String imagePath, int x, int y) {

		try {
			image = ImageIO.read(new File("res/" + imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
	}

	public void draw
		(final int x_offset, final int y_offset, final int width, final int height, Graphics2D g) {

        final int
            top_x = x_offset + GamePanel.GRID_SPACING + (y * (width  + GamePanel.GRID_SPACING)),
            top_y = y_offset + GamePanel.GRID_SPACING + (x * (height + GamePanel.GRID_SPACING));

        g.drawImage(image.getScaledInstance(width, height, BufferedImage.SCALE_FAST), top_x, top_y, Color.black, null);
    }

	public void move(int newX, int newY) {
	    x = newX;
	    y = newY;
    }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public abstract boolean killsPlayer();
	
	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + "]";
	}
}