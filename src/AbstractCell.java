import java.awt.*;

/**
 * @author Quintin Dwight
 * @author Anuva Banwasi
 */
public abstract class AbstractCell {

	protected int x, y;

	public AbstractCell(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public abstract void draw
		(final int x_offset, final int y_offset, final int width, final int height, Graphics g);

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

	public abstract boolean isSolid();
	
	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + "]";
	}
}