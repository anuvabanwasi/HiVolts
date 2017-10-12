
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Quintin Dwight
 * @author Anuva Banwasi
 */
public abstract class AbstractCell {

	private Coordinate c;
	private Image image;

	public AbstractCell(String imagePath, Coordinate c, final int WIDTH, final int HEIGHT) {

		this.c = c;

        try {
			image = ImageIO.read(new File("res/" + imagePath));
			image = image.getScaledInstance(WIDTH, HEIGHT, BufferedImage.SCALE_FAST);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Draw the image
	 *
	 * @param g2 Graphics object used to draw
	 * @param X_OFFSET X position in the 2D array
	 * @param Y_OFFSET Y position in the 2D array
	 * @param GAP Gap in the grid
	 * @param WIDTH Width of each cell
	 * @param HEIGHT Height of each cell
	 */
	public void draw(Graphics2D g2, final int X_OFFSET, final int Y_OFFSET, final int GAP, final int WIDTH, final int HEIGHT) {

		// Calculate x and y coordinates of image factoring in grid gap and size
        final int
            X = X_OFFSET + GAP + (c.y * (WIDTH  + GAP)),
            Y = Y_OFFSET + GAP + (c.x * (HEIGHT + GAP));

        // Draw image in the center of the grid
        g2.drawImage(image, X - GAP/2, Y - GAP/2, Color.black, null);
    }

	/**
	 * Get current position of the cell
	 *
	 * @return Position of the cell
	 */
	public Coordinate getPosition() {
		return c;
	}

	/**
	 * Move to a new square on the field
	 *
	 * @param newPosition New position
	 */
	public void move(Coordinate newPosition) {

	    c = newPosition;
    }

//	/**
//	 * Whether or not this cell kills an entity
//	 *
//	 * @return Whether or not moving onto this cell kills an entity
//	 */
//	public boolean killsEntity() {
//
//		return false;
//	}
}
