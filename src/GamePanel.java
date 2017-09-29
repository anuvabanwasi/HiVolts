


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JComponent;

// Note that the JComponent is set up to listen for mouse clicks
// and mouse movement.  To achieve this, the MouseListener and
// MousMotionListener interfaces are implemented and there is additional
// code in init() to attach those interfaces to the JComponent.

/**
 *
 */
public class GamePanel extends JComponent implements KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int
		ROWS = 12,
		COLS = 12;
	public static Cell[][] cells = new Cell[ROWS][COLS];
	private static final int
		NUM_OF_MHOS = 3,
		NUM_OF_INTERIOR_FENCES = 2;
	private final int
		X_GRID_OFFSET = 25, // 25 pixels from left,
		Y_GRID_OFFSET = 40; // 40 pixels from top

	private final int CELL_WIDTH = 28, CELL_HEIGHT = 28;

	// Note that a final field can be initialized in constructor
	private final int DISPLAY_WIDTH, DISPLAY_HEIGHT;
	
	private Cell smiley, mho;

	public GamePanel(int width, int height) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		init();
	}

	private void init() {

		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		initCells(true);

		addKeyListener(this);

		repaint();
	}

	private void initCells(boolean showInitial) {

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cells[row][col] = new Cell(row, col);
			}
		}

		// Set Exterior Electric Fences
		for(int i = 0; i < 12; i++) {
			cells[0][i].set("fence");
			cells[i][0].set("fence");
			cells[11][i].set("fence");
			cells[i][11].set("fence");
		}

		// Set Smiley Cell
		initSmiley();
		// Set Mho Cells
		for(int i = 0; i < 12; i++) {
			initMho();
		}
		
		// Set Interior Electric Fences
		
	}

	private void initMho() {

		int randomX = ThreadLocalRandom.current().nextInt(1, ROWS - 1);
		int randomY = ThreadLocalRandom.current().nextInt(1, COLS - 1);

		cells[randomX][randomY].set("mho");
		mho = cells[randomX][randomY];
	}


	private void initSmiley() {

		int randomX = ThreadLocalRandom.current().nextInt(1, ROWS - 1);
		int randomY = ThreadLocalRandom.current().nextInt(1, COLS - 1);

		cells[randomX][randomY].set("smiley");
		smiley = cells[randomX][randomY];
	}


	private void moveSmiley(KeyEvent e) {

		char c = e.getKeyChar();

		int row = smiley.getX(), col = smiley.getY();

		switch (c) {
		case 'Q':
		case 'q':
			System.out.println("q pressed");
			if (row > 0 && col > 0) {
				moveSmiley(row - 1, col - 1);
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());

			}
			break;

		case 'W':
		case 'w':
			System.out.println("w pressed");
			if (row > 0) {
				moveSmiley(row - 1, col);
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}

			break;

		case 'E':
		case 'e':
			System.out.println("e pressed");
			if (row > 0 && col < COLS - 1) {
				moveSmiley(row - 1, col + 1);
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}

			break;

		case 'A':
		case 'a':
			System.out.println("a pressed");
			if (col > 0) {
				moveSmiley(row, col - 1);
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		case 'S':
		case 's':
			System.out.println("s pressed");
			moveSmiley(row, col);
			// System.out.println("smiley " + smiley.getX() + " : " +
			// smiley.getY());
			break;

		case 'D':
		case 'd':
			System.out.println("d pressed");
			if (col < COLS - 1) {
				moveSmiley(row, col + 1);
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		case 'Z':
		case 'z':
			System.out.println("z pressed");
			if (col > 0 && row < ROWS - 1) {
				moveSmiley(row + 1, col - 1);
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		case 'X':
		case 'x':
			System.out.println("x pressed");
			if (row < ROWS - 1) {
				moveSmiley(row + 1, col);
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		case 'C':
		case 'c':
			System.out.println("c pressed");
			if (row < ROWS - 1 && col < COLS - 1) {
				moveSmiley(row + 1, col + 1);
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		default:
			System.out.println("Key not supported!");
			break;
		}
	}

	private void moveSmiley(int x, int y) {

		// System.out.println("Smiley new position : " + x + " , " + y);
		if (cells[x][y].getType().equals("empty")) {
			smiley.set("empty");
			cells[x][y].set("smiley");
			smiley = cells[x][y];
			smiley.set("smiley");

		} else {
			System.out.println("Should not be here!");
		}

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);

		repaint();
	}

	private void drawCells(Graphics g) {

		// Have each cell draw itself
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				// The cell cannot know for certain the offsets nor the height
				// and width; it has been set up to know its own position, so
				// that need not be passed as an argument to the draw method
				cells[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
			}
		}
	}

	private void drawGrid(Graphics g) {

		for (int row = 0; row <= ROWS; row++) {
			g.drawLine(X_GRID_OFFSET, Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)),
					X_GRID_OFFSET + COLS * (CELL_WIDTH + 1), Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)));
		}

		for (int col = 0; col <= COLS; col++) {
			g.drawLine(X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET,
					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET + ROWS * (CELL_HEIGHT + 1));
		}
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		moveSmiley(e);
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	
}
