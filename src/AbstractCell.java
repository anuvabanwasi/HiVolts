
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

	private Coordinate c;
	private BufferedImage image;

	public AbstractCell(String imagePath, Coordinate c) {

		this.c = c;

        try {
			image = ImageIO.read(new File("res/" + imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(final int x_offset, final int y_offset, final int gap, final int width, final int height, Graphics g) {

        final int
            top_x = x_offset + gap + (c.getY() * (width  + gap)),
            top_y = y_offset + gap + (c.getX() * (height + gap));

        g.drawImage(image.getScaledInstance(width, height, BufferedImage.SCALE_FAST), top_x - gap/2, top_y - gap/2, Color.black, null);
    }

	public Coordinate getPosition() {
		return c;
	}

	public void move(Coordinate newPosition) {
	    c = newPosition;
    }
}
