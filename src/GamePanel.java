
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JComponent;

/**
 * Draws the game field and holds logic for game pieces.
 * Key listener is linked to get input from the user.
 *
 * @author Anuva Banwasi
 * @author Quintin Dwight
 */
public class GamePanel extends JComponent implements KeyListener {

	private static final long serialVersionUID = 1L;
	
	private static final int
		ROWS = 12,
		COLS = 12;
	
	private static final int
		NUM_OF_MHOS = 12,
		NUM_OF_INTERIOR_FENCES = 20;
	
	private final int
		X_GRID_OFFSET = 20,
		Y_GRID_OFFSET = 20;

	private static AbstractCell[][] cells = new AbstractCell[ROWS][COLS];

	private final int CELL_WIDTH = 60, CELL_HEIGHT = 60, CELL_GAP = 10;

	// Note that a final field can be initialized in constructor
	private final int DISPLAY_WIDTH, DISPLAY_HEIGHT;
	
	private Smiley smiley;
	private List<Mho> mhos = new ArrayList<>();
	private List<Fence> fences = new ArrayList<>();
	private static List<Coordinate> noFenceSpots = new ArrayList<>(), emptySpots = new ArrayList<>();
	
	private boolean gameOver;

	public GamePanel(final int width, final int height) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;

		init();
	}

	/**
	 * Initialize the game panel.
	 * This takes care of adding all of the game pieces and setting up graphics.
	 */
	private void init() {

		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);

		//Initialize empty cells
		for (int x = 1; x < COLS-1; x++) {
			for (int y = 1; y < ROWS-1; y++) {
				emptySpots.add(new Coordinate(x, y));
				noFenceSpots.add(new Coordinate(x, y));
			}
		}
				
		initCells();

		addKeyListener(this);

		repaint();
	}

	/**
	 * Initialize all of the cells, this includes
	 * <br>
	 * <ul>
	 *     <li>Placing exterior fences</li>
	 *     <li>Creating mhos</li>
	 *     <li>Creating interior fences</li>
	 *     <li>Creating the smiley</li>
	 * </ul>
	 */
	private void initCells() {
		
		// Set Exterior Electric Fences
		initExteriorFences();
		
		// Set Mho Cells
		for (int i = 0; i < NUM_OF_MHOS; i++) {
			createMho(getRandomPlacePosition());
		}
		
		// Set Interior Electric Fences
		initInteriorFences();

		// Set Smiley
		createSmiley(getRandomPlacePosition());
	}

	/**
	 * Create exterior fences.
	 * They are always in the same spot and are around the edge of the playing field.
	 */
	private void initExteriorFences() {

		for(int i = 0; i < COLS; i++) {
			cells[0][i] = createFence(new Coordinate(0 , i));

			cells[ROWS-1][i] = createFence(new Coordinate(ROWS-1, i));
		}
		
		for(int i = 0; i < ROWS; i++) {
			cells[i][0] = createFence(new Coordinate(i, 0));

			cells[i][ROWS-1] = createFence(new Coordinate(i, ROWS-1));
		}
	}

	/**
	 * Create the interior fences with random positions.
	 */
	private void initInteriorFences() {
		
		for (int i = 0; i < NUM_OF_INTERIOR_FENCES; i++) {
			createFence(getRandomPlacePosition());
		}
	}

	/**
	 * Create a mho and add it to an array.
	 *
	 * @param c Position of the mho
	 * @return Mho instance
	 */
	private Mho createMho(Coordinate c) {

		Mho mho = new Mho(c, CELL_WIDTH, CELL_HEIGHT);

		mhos.add(mho);

		initCellObject(mho);

		return mho;
	}

	/**
	 * Create a smiley and keep a reference to it.
	 *
	 * @param c Position of the smiley
	 * @return Smiley instance
	 */
	private Smiley createSmiley(Coordinate c) {

		Smiley smiley = new Smiley(c, CELL_WIDTH, CELL_HEIGHT);

		this.smiley = smiley;

		initCellObject(smiley);

		return smiley;
	}

	/**
	 * Create a fence and add it to the array.
	 *
	 * @param c Position of the fence
	 * @return Fence object
	 */
	private Fence createFence(Coordinate c) {

		Fence fence = new Fence(c, CELL_WIDTH, CELL_HEIGHT);

		fences.add(fence);

		noFenceSpots.remove(fence.getPosition());

		initCellObject(fence);

		return fence;
	}

	/**
	 * Abstract method for initialization that all cells need to do.
	 *
	 * @param cell Cell to initialize in the array
	 */
	private void initCellObject(AbstractCell cell) {

		Coordinate cp = cell.getPosition();

		cells[cp.getX()][cp.getY()] = cell;
	}

	/**
	 * Move the smiley given a key event.
	 *
	 * @param e Key event in question
	 */
	private void moveSmiley(KeyEvent e) {

		if (!gameOver) {

			Coordinate cp = smiley.getPosition();
			int row = cp.getX(), col = cp.getY();
			int c = e.getKeyCode();

			switch (c) {
				case KeyEvent.VK_Q:
					if (row > 0 && col > 0) {
						moveSmiley(new Coordinate(row - 1, col - 1));

					}
					moveMhos();
					break;

				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					if (row > 0) {
						moveSmiley(new Coordinate(row - 1, col));
					}
					moveMhos();
					break;

				case KeyEvent.VK_E:
					if (row > 0 && col < COLS - 1) {
						moveSmiley(new Coordinate(row - 1, col + 1));
					}
					moveMhos();
					break;

				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					if (col > 0) {
						moveSmiley(new Coordinate(row, col - 1));
					}
					moveMhos();
					break;

				case KeyEvent.VK_S:
					moveMhos();
					break;

				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					if (col < COLS - 1) {
						moveSmiley(new Coordinate(row, col + 1));
					}
					moveMhos();
					break;

				case KeyEvent.VK_Z:
					if (col > 0 && row < ROWS - 1) {
						moveSmiley(new Coordinate(row + 1, col - 1));
					}
					moveMhos();
					break;

				case KeyEvent.VK_X:
				case KeyEvent.VK_DOWN:
					if (row < ROWS - 1) {
						moveSmiley(new Coordinate(row + 1, col));
					}
					moveMhos();
					break;

				case KeyEvent.VK_C:
					if (row < ROWS - 1 && col < COLS - 1) {
						moveSmiley(new Coordinate(row + 1, col + 1));
					}
					moveMhos();
					break;

				case KeyEvent.VK_J:
					jumpSmiley();
					break;
			}
		}
	}

	/**
	 * Jump to smiley to a random position.
	 */
	private void jumpSmiley() {

		Coordinate c = getRandomJunpPosition();

		moveSmiley(c);

		repaint();
	}

	/**
	 * Move the smiley and check if the square will kill the player.
	 *
	 * @param c New coordinate to move to
	 */
	private void moveSmiley(final Coordinate c) {

		final int sx = c.getX(), sy = c.getY();

		if (cells[sx][sy] instanceof Fence) {
			gameOver();
		} else if (cells[sx][sy] instanceof Mho) {
			gameOver();
		} else if (cells[sx][sy] == null) {
			// first reset current cell, then moveMho to next position, assign smiley at that position
			Coordinate smileyPosition = smiley.getPosition();
			cells[smileyPosition.getX()][smileyPosition.getY()] = null;
			smiley.move(c);
			cells[sx][sy] = smiley;
		}

		repaint();
	}

	/**
	 * Move all of the mhos after the player has moved.
	 */
	private void moveMhos() {

		if (!gameOver) {

			final Coordinate smileyPosition = smiley.getPosition();

			for (int i = 0; i < mhos.size() && !gameOver; i++) {

				Mho mho = mhos.get(i);

				Coordinate mhoPosition = mho.getPosition();

				// mho and smiley are in same row
				if (mhoPosition.getX() == smileyPosition.getX()) {
					moveHorizontally(i, mho);
				} 
				// mho and smiley are in same column
				else if (mhoPosition.getY() == smileyPosition.getY()) {
					moveVertically(i, mho);
				}
				else {
					moveDiagonally(i, mho);
				}

				repaint();
			}
		}
	}

	/**
	 * Attempt to move the mho vertically.
	 *
	 * @param i Index of the mho in the array
	 * @param mho Mho to move
	 */
	private void moveVertically(int i, Mho mho) {

		int row, col;

		final Coordinate mhoPosition = mho.getPosition(), smileyPosition = smiley.getPosition();
		
		if (mhoPosition.getX() < smileyPosition.getX()) {
			row = mhoPosition.getX() + 1;
			col = mhoPosition.getY();
		} else {
			row = mhoPosition.getX() - 1;
			col = mhoPosition.getY();
		}
		
		if (cells[row][col] instanceof Fence) {
			cells[mhoPosition.getX()][mhoPosition.getY()] = null;
			mhos.remove(i);
		}
		else if(cells[row][col] instanceof Smiley) {
			killSmiley(mho, new Coordinate(row, col));
		} else if(cells[row][col] instanceof Mho) {
			// Don't moveMho
		} else {
			moveMho(mho, new Coordinate(row, col));
		}
	}

	/**
	 * Attempt to move the mho horizontally.
	 *
	 * @param i Index of the mho in the array
	 * @param mho Mho to move
	 */
	private void moveHorizontally(int i, Mho mho) {

		int row;
		int col;

		final Coordinate mhoPosition = mho.getPosition(), smileyPosition = smiley.getPosition();

		if (mhoPosition.getY() < smileyPosition.getY()) {
			row = mhoPosition.getX();
			col = mhoPosition.getY() + 1;
		} else {
			row = mhoPosition.getX();
			col = mhoPosition.getY() - 1;
		}
				
		if (cells[row][col] instanceof Fence) {
			cells[mhoPosition.getX()][mhoPosition.getY()] = null;
			mhos.remove(i);
		}
		else if(cells[row][col] instanceof Smiley){
			killSmiley(mho, new Coordinate(row, col));
		} else if(cells[row][col] instanceof Mho){
			// Don't moveMho
		} else{
			moveMho(mho, new Coordinate(row, col));
		}
	}

	/**
	 * Attempt to move the mho diagonally.
	 *
	 * @param i Index of the mho in the array
	 * @param mho Mho to move
	 */
	private void moveDiagonally(int i, Mho mho) {
		
		int row = 0, col = 0;

		final Coordinate smileyPosition = smiley.getPosition(), mhoPosition = mho.getPosition();

		if (smileyPosition.getX() - mhoPosition.getX() > 0) {
			row = mhoPosition.getX() + 1;
		} else if (smileyPosition.getX() - mhoPosition.getX() < 0) {
			row = mhoPosition.getX() - 1;
		}

		if (smileyPosition.getY() - mhoPosition.getY() > 0) {
			col = mhoPosition.getY() + 1;
		} else if (smileyPosition.getY() - mhoPosition.getY() < 0) {
			col = mhoPosition.getY() - 1;
		}

		if (cells[row][col] instanceof Smiley) {
			killSmiley(mho, new Coordinate(row, col));
		} else if (cells[row][col] instanceof Mho){
			//Don't moveMho
		} else if (cells[row][col] == null) {
			moveMho(mho, new Coordinate(row, col));
			cells[mhoPosition.getX()][mhoPosition.getY()] = null;
			cells[row][col] = mho;
		}
	}

	/**
	 * Kills the smiley.
	 *
	 * @param mho Mho which inflicted the kill
	 * @param position Death position
	 */
	private void killSmiley(Mho mho, Coordinate position) {

		final Coordinate mhoPosition = mho.getPosition();

		cells[mhoPosition.getX()][mhoPosition.getY()] = null;
		mho.move(position);
		gameOver();
		cells[position.getX()][position.getY()] = mho;
	}

	/**
	 * Move the mho and update the array.
	 *
	 * @param mho Mho to move
	 * @param newPosition New position
	 */
	private void moveMho(Mho mho, Coordinate newPosition) {

		final Coordinate mhoPosition = mho.getPosition();

		cells[mhoPosition.getX()][mhoPosition.getY()] = null;
		mho.move(newPosition);
		cells[newPosition.getX()][newPosition.getY()] = mho;
		
	}
	
	private void gameOver() {

		System.out.println("Game Over. You Lose!");
		gameOver = true;
		final Coordinate smileyPosition = smiley.getPosition();
		cells[smileyPosition.getX()][smileyPosition.getY()] = null;
		smiley = null;
		removeKeyListener(this);
	}

	/**
	 * Get random position that isn't already taken by a cell.
	 *
	 * @return Random position
	 */
	private Coordinate getRandomPlacePosition() {

		// Get random index within array
		final int randomIndex = ThreadLocalRandom.current().nextInt(0, emptySpots.size());
		Coordinate rc = emptySpots.get(randomIndex);

		// Remove coordinate from list
		emptySpots.remove(randomIndex);

		return rc;
	}

	/**
	 * Get a random jump position for the smiley.
	 * This will not return a position where there is a fence,
	 * but it can return a position where there is a mho.
	 *
	 * @return Random position
	 */
	private Coordinate getRandomJunpPosition() {

		// Get random index within array
		final int randomIndex = ThreadLocalRandom.current().nextInt(0, noFenceSpots.size());
		Coordinate rc = noFenceSpots.get(randomIndex);

		return rc;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		moveSmiley(e);
		repaint();
	}
	
	@Override public void keyReleased(KeyEvent e) { }

	@Override public void keyTyped(KeyEvent e) { }
	
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		g2.setColor(Color.BLACK);
		drawGrid(g2);
		drawCells(g2);

		repaint();
	}

	/**
	 * Have each individual cell draw itself.
	 *
	 * @param g2 Graphics object used to draw
	 */
	private void drawCells(Graphics2D g2) {

		// Have each cell draw itself
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				AbstractCell cell = cells[row][col];
				// The cell cannot know for certain the offsets nor the height
				// and width; it has been set up to know its own position, so
				// that need not be passed as an argument to the draw method
				if (cell != null)
					cell.draw(g2, X_GRID_OFFSET, Y_GRID_OFFSET, CELL_GAP, CELL_WIDTH, CELL_HEIGHT);
			}
		}
	}

	/**
	 * Draw the grid background. This is done before each cell individually draws itself.
	 *
	 * @param g2 Graphics object used to draw
	 */
	private void drawGrid(Graphics2D g2) {

		g2.setColor(Color.BLACK);
		g2.fillRect(X_GRID_OFFSET, Y_GRID_OFFSET, (CELL_WIDTH+CELL_GAP)*ROWS, (CELL_HEIGHT+CELL_GAP)*COLS);

		g2.setColor(Color.DARK_GRAY);

		// Draw rows
		for (int row = 0; row <= ROWS; row++) {
			g2.drawLine(
				X_GRID_OFFSET,
			Y_GRID_OFFSET + (row * (CELL_HEIGHT + CELL_GAP)),
			X_GRID_OFFSET + COLS * (CELL_WIDTH  + CELL_GAP),
			Y_GRID_OFFSET + (row * (CELL_HEIGHT + CELL_GAP)));
		}

		// Draw columns
		for (int col = 0; col <= COLS; col++) {
			g2.drawLine(
			X_GRID_OFFSET + (col * (CELL_WIDTH + CELL_GAP)),
				Y_GRID_OFFSET,
			X_GRID_OFFSET + (col * (CELL_WIDTH  + CELL_GAP)),
			Y_GRID_OFFSET + ROWS * (CELL_HEIGHT + CELL_GAP));
		}
	}

}
