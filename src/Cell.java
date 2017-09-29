import java.awt.*;

public class Cell {

	public static final int COLS = 12, ROWS = 12;
	protected int myX, myY;

	public Cell(int x, int y) {

		myX = x;
		myY = y;
	}

	public void draw(final int x_offset, final int y_offset, final int width, final int height, Graphics g) {

		final int
			x = x_offset + 1 + (myY * (width  + 1)),
			y = y_offset + 1 + (myX * (height + 1));

		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, width, height);
	}
	
	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}

	public boolean isSolid() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Cell [myX=" + myX + ", myY=" + myY + "]";
	}
}