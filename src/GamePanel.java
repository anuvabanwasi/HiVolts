import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JComponent;

// Note that the JComponent is set up to listen for mouse clicks
// and mouse movement.  To achieve this, the MouseListener and
// MousMotionListener interfaces are implemented and there is additional
// code in init() to attach those interfaces to the JComponent.

/**
 * @author Anuva Banwasi
 */
public class GamePanel extends JComponent implements KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int
		ROWS = 12,
		COLS = 12;
	public static AbstractCell[][] cells = new AbstractCell[ROWS][COLS];
	private static final int
		NUM_OF_MHOS = 12,
		NUM_OF_INTERIOR_FENCES = 2;
	private static final int
		X_GRID_OFFSET = 20, // 25 pixels from left,
		Y_GRID_OFFSET = 20; // 40 pixels from top
	public static final int GRID_SPACING  = 8;

	private final int CELL_WIDTH = 60, CELL_HEIGHT = 60;

	// Note that a final field can be initialized in constructor
	private final int DISPLAY_WIDTH, DISPLAY_HEIGHT;
	
	private AbstractCell smiley;

	public GamePanel(final int width, final int height) {

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

		// Set Exterior Electric Fences
		for (int i = 0; i < 12; i++) {
			cells[ 0][ i] = new Fence(0 , i );
			cells[ i][ 0] = new Fence(i , 0 );
			cells[11][ i] = new Fence(11, i );
			cells[ i][11] = new Fence(i , 11);
		}

		// Set Smiley AbstractCell
		initSmiley();
		// Set Mho Cells
		for(int i = 0; i < NUM_OF_MHOS; i++) {
			initMho();
		}
		
		// Set Interior Electric Fences
		
	}

	private void initMho() {

		int randomX = ThreadLocalRandom.current().nextInt(1, ROWS - 1),
			randomY = ThreadLocalRandom.current().nextInt(1, COLS - 1);

		cells[randomX][randomY] = new Mho(randomX, randomY);
	}


	private void initSmiley() {

		int randomX = ThreadLocalRandom.current().nextInt(1, ROWS - 1),
			randomY = ThreadLocalRandom.current().nextInt(1, COLS - 1);

		cells[randomX][randomY] = new Smiley(randomX, randomY);
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

		if (cells[x][y] == null) {
			cells[smiley.getX()][smiley.getY()] = null;
			smiley.move(x, y);
			cells[x][y] = smiley;
		} else {
			System.out.println("Should not be here!");
		}

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		g.setColor(Color.BLACK);
		drawGrid(g2);
		drawCells(g2);

		repaint();
	}

	private void drawCells(Graphics2D g) {

		// Have each cell draw itself
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				AbstractCell cell = cells[row][col];
				// The cell cannot know for certain the offsets nor the height
				// and width; it has been set up to know its own position, so
				// that need not be passed as an argument to the draw method
				if (cell != null)
					cell.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
			}
		}
	}

	private void drawGrid(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(X_GRID_OFFSET, Y_GRID_OFFSET, (CELL_WIDTH+GRID_SPACING)*ROWS, (CELL_HEIGHT+GRID_SPACING)*COLS);

		g.setColor(Color.BLACK);

		// Draw rows
		for (int row = 0; row <= ROWS; row++) {
			g.drawLine(
				X_GRID_OFFSET,
			Y_GRID_OFFSET + (row * (CELL_HEIGHT + GRID_SPACING)),
			X_GRID_OFFSET + COLS * (CELL_WIDTH  + GRID_SPACING),
			Y_GRID_OFFSET + (row * (CELL_HEIGHT + GRID_SPACING)));
		}

		// Draw columns
		for (int col = 0; col <= COLS; col++) {
			g.drawLine(
			X_GRID_OFFSET + (col * (CELL_WIDTH + GRID_SPACING)),
				Y_GRID_OFFSET,
			X_GRID_OFFSET + (col * (CELL_WIDTH  + GRID_SPACING)),
			Y_GRID_OFFSET + ROWS * (CELL_HEIGHT + GRID_SPACING));
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
