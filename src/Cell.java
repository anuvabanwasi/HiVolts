import java.awt.Color;
import java.awt.Graphics;

public class Cell {

	public static final int COLS = 12, ROWS = 12;
	private String type;   //smiley, mho, fence, empty
	private Color myColor; // Based on smiley/mho/fence rules
	private int myX, myY;  // x,y position on grid

	public Cell(int x, int y) {

		this(x, y, false, Color.WHITE, "empty");
	}

	public Cell(int row, int col, boolean alive, Color color, String type) {

		myColor = color;
		myX = row;
		myY = col;
		this.type = type;
	}

	public void draw(int x_offset, int y_offset, int width, int height,
			Graphics g) {
		// I leave this understanding to the reader
		int xleft = x_offset + 1 + (myY * (width + 1));
		int xright = x_offset + width + (myX * (width + 1));
		int ytop = y_offset + 1 + (myX * (height + 1));
		int ybottom = y_offset + height + (myY * (height + 1));
		Color temp = g.getColor();

		g.setColor(myColor);
		g.fillRect(xleft, ytop, width, height);
	}
	
	public void set(String type) {

		this.setType(type);
		if(type.equals("mho"))
			this.setColor(Color.cyan);
		else if (type.equals("smiley"))
			this.setColor(Color.pink);
		else if (type.equals("empty"))
			this.setColor(Color.white);
		else if (type.equals("fence"))
			this.setColor(Color.orange);
	}
	
	public Color getColor() {
		return myColor;
	}

	public void setColor(Color color) {

		myColor = color;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getX() {
		return myX;
	}

	public int getY() {
		return myY;
	}
	
	@Override
	public String toString() {
		return "Cell [myX=" + myX + ", myY=" + myY + ", type=" + type + "]";
	}
}