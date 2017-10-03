import sun.plugin.dom.core.CoreConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.List;
import java.util.ArrayList;
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
	public static List<AbstractCell> cells = new ArrayList<>();
	public static List<Coordinate> emptyCoordinates = new ArrayList<>();
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

		for (int x = 0; x < COLS; x++) {
			for (int y = 0; y < ROWS; y++) {
				emptyCoordinates.add(new Coordinate(x, y));
			}
		}

		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		initCells(true);

		addKeyListener(this);

		repaint();
	}

	private void initCells(final boolean showInitial) {

		// Set Exterior Electric Fences
		for (int i = 0; i < 12; i++) {
			addCell(new Fence(new Coordinate(0, i)));
			addCell(new Fence(new Coordinate(i, 0)));
			addCell(new Fence(new Coordinate(11, i)));
			addCell(new Fence(new Coordinate(i, 11)));
		}

		// Set Smiley AbstractCell
		initSmiley();

		// Set Mho Cells
		for (int i = 0; i < NUM_OF_MHOS; i++) {
			initMho();
		}

		for (int i = 0; i < 12; i++) {
			initFences();
		}

		// Set Interior Electric Fences
		
	}

	private void initMho() {

		Coordinate coordinate = getRandomPosition();

		addCell(new Mho(coordinate));
	}


	private void initFences() {

		Coordinate coordinate = getRandomPosition();

		addCell(new Fence(coordinate));
	}


	private void initSmiley() {

		Coordinate coordinate = getRandomPosition();

		smiley = new Smiley(coordinate);
		addCell(smiley);
	}


	private void addCell(AbstractCell cell) {

		cells.add(cell);

		Coordinate c1 = cell.position;
		for (int i = 0; i < emptyCoordinates.size(); i++) {
			Coordinate c2 = emptyCoordinates.get(i);
			if (c1.x == c2.x && c1.y == c2.y) emptyCoordinates.remove(i);
		}
	}


	private Coordinate getRandomPosition() {

		final int randomIndex = ThreadLocalRandom.current().nextInt(1, cells.size());
		return emptyCoordinates.get(randomIndex);
	}


	private void moveSmiley(KeyEvent e) {

		char c = e.getKeyChar();

		int row = smiley.position.x, col = smiley.position.y;

		switch (c) {
		case 'Q':
		case 'q':
			System.out.println("q pressed");
			if (row > 0 && col > 0) {
				moveSmiley(new Coordinate(row - 1, col - 1));
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());

			}
			break;

		case 'W':
		case 'w':
			System.out.println("w pressed");
			if (row > 0) {
				moveSmiley(new Coordinate(row, col - 1));
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}

			break;

		case 'E':
		case 'e':
			System.out.println("e pressed");
			if (row > 0 && col < COLS - 1) {
				moveSmiley(new Coordinate(row + 1, col - 1));
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}

			break;

		case 'A':
		case 'a':
			System.out.println("a pressed");
			if (col > 0) {
				moveSmiley(new Coordinate(row - 1, col));
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		case 'S':
		case 's':
			System.out.println("s pressed");
			moveSmiley(new Coordinate(row, col));
			// System.out.println("smiley " + smiley.getX() + " : " +
			// smiley.getY());
			break;

		case 'D':
		case 'd':
			System.out.println("d pressed");
			if (col < COLS - 1) {
				moveSmiley(new Coordinate(row + 1, col));
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		case 'Z':
		case 'z':
			System.out.println("z pressed");
			if (col > 0 && row < ROWS - 1) {
				moveSmiley(new Coordinate(row - 1, col + 1));
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		case 'X':
		case 'x':
			System.out.println("x pressed");
			if (row < ROWS - 1) {
				moveSmiley(new Coordinate(row, col + 1));
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		case 'C':
		case 'c':
			System.out.println("c pressed");
			if (row < ROWS - 1 && col < COLS - 1) {
				moveSmiley(new Coordinate(row + 1, col + 1));
				// System.out.println("smiley " + smiley.getX() + " : " +
				// smiley.getY());
			}
			break;

		default:
			System.out.println("Key not supported!");
			break;
		}
	}

	/**
	 * Finds a cell in the array with given x and y coordinates
	 * @param c Coordinate of the new position
	 * @return null if no cell can be found or the cell if it can
	 */
	private AbstractCell getCell(final Coordinate c) {

		for (AbstractCell cell : cells) {

			if (cell.position.x == c.x && cell.position.y == c.y) return cell;
		}

		return null;
	}

	private void moveSmiley(final Coordinate c) {

		AbstractCell cell = getCell(c);
		if (cell == null) {
			smiley.move(c);
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

	/**
	 * Have each cell draw itself
	 * @param g Graphics object used to draw
	 */
	private void drawCells(Graphics2D g) {

		for (AbstractCell cell : cells) {

			cell.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
		}
	}

	/**
	 * @param g Graphics object used to draw
	 */
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
