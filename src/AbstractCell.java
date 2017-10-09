
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Quintin Dwight
 * @author Anuva Banwasi
 *
 */
public abstract class AbstractCell {

	private int x, y;
	private BufferedImage image;

	public AbstractCell(String imagePath, int x, int y) {

		this.x = x;
		this.y = y;

        try {
			image = ImageIO.read(new File("res/" + imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(final int x_offset, final int y_offset, final int width, final int height, Graphics g) {

        final int
            top_x = x_offset + 1 + (y * (width  + 1)),
            top_y = y_offset + 1 + (x * (height + 1));

        g.fillRect(top_x, top_y, width, height);
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
	
	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + "]";
	}

}
