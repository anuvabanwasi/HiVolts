
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JComponent;

/**
 * @author Anuva Banwasi
 * @author Quintin Dwight
 */

public class GamePanel extends JComponent implements KeyListener {

	private static final String FENCE = "fence", MHO = "mho", SMILEY = "smiley";

	private static final long serialVersionUID = 1L;
	
	private static final int
		ROWS = 12,
		COLS = 12;
	
	private static final int
		NUM_OF_MHOS = 20,
		NUM_OF_INTERIOR_FENCES = 12;
	
	private final int
		X_GRID_OFFSET = 25, // 25 pixels from left,
		Y_GRID_OFFSET = 40; // 40 pixels from top

	private static AbstractCell[][] cells = new AbstractCell[ROWS][COLS];

	private final int CELL_WIDTH = 60, CELL_HEIGHT = 60;

	// Note that a final field can be initialized in constructor
	private final int DISPLAY_WIDTH, DISPLAY_HEIGHT;
	
	private Smiley smiley;
	private List<Mho> mhos = new ArrayList<>();
	private List<Fence> fences = new ArrayList<>();
	private static List<Coordinate> emptyCoordinates = new ArrayList<>();
	
	private boolean gameOver;

	public GamePanel(final int width, final int height) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;

		init();
	}

	private void init() {

		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		
		//Initialize empty cells
		for (int x = 0; x < COLS; x++) {
			for (int y = 0; y < ROWS; y++) {
				emptyCoordinates.add(new Coordinate(x, y));
			}
		}
				
		initCells();

		addKeyListener(this);

		repaint();
	}

	private void initCells() {
		
		// Set Exterior Electric Fences
		initExteriorFences();
			
		// Set Smiley 
		createSmiley(getRandomPosition());
		
		// Set Mho Cells
		for(int i = 0; i < NUM_OF_MHOS; i++) {
			createMho(getRandomPosition());
		}
		
		// Set Interior Electric Fences
		initInteriorFences();
	}

	private void initExteriorFences() {

		for(int i = 0; i < COLS; i++) {
			cells[0][i] = new Fence(new Coordinate(0 , i));
			removeCell(new Coordinate(0, i));
			
			cells[ROWS-1][i] = new Fence(new Coordinate(ROWS-1, i));
			removeCell(new Coordinate(ROWS-1, i));
		}
		
		for(int i = 0; i < ROWS; i++) {
			cells[i][0] = new Fence(new Coordinate(i, 0));
			removeCell(new Coordinate(i, 0));
			
			cells[i][ROWS-1] = new Fence(new Coordinate(i, ROWS-1));
			removeCell(new Coordinate(i, ROWS-1));
		}
	}

	private void initInteriorFences() {
		
		for (int i = 0; i < NUM_OF_INTERIOR_FENCES; i++) {
			createFence(getRandomPosition());
		}
	}

	private Mho createMho(Coordinate c)
	{
		Mho mho = new Mho(c);

		mhos.add(mho);

		initCellObject(mho);

		return mho;
	}

	private Smiley createSmiley(Coordinate c)
	{
		Smiley smiley = new Smiley(c);

		this.smiley = smiley;

		initCellObject(smiley);

		return smiley;
	}

	private Fence createFence(Coordinate c)
	{
		Fence fence = new Fence(c);

		fences.add(fence);

		initCellObject(fence);

		return fence;
	}

	private void initCellObject(AbstractCell cell)
	{
		Coordinate cp = cell.getPosition();

		cells[cp.getX()][cp.getY()] = cell;

		removeCell(cp);
	}

	private void moveSmiley(KeyEvent e) {

		if (!gameOver) {

			Coordinate cp = smiley.getPosition();
			int row = cp.getX(), col = cp.getY();
			int c = e.getKeyCode();

			switch (c) {
				case KeyEvent.VK_Q:
					if (row > 0 && col > 0) {
						moveSmiley(row - 1, col - 1);

					}
					moveMhos();
					break;

				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					if (row > 0) {
						moveSmiley(row - 1, col);
					}
					moveMhos();
					break;

				case KeyEvent.VK_E:
					if (row > 0 && col < COLS - 1) {
						moveSmiley(row - 1, col + 1);
					}
					moveMhos();
					break;

				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					if (col > 0) {
						moveSmiley(row, col - 1);
					}
					moveMhos();
					break;

				case KeyEvent.VK_S:
					moveSmiley(row, col);
					break;

				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					if (col < COLS - 1) {
						moveSmiley(row, col + 1);
					}
					moveMhos();
					break;

				case KeyEvent.VK_Z:
					if (col > 0 && row < ROWS - 1) {
						moveSmiley(row + 1, col - 1);
					}
					moveMhos();
					break;

				case KeyEvent.VK_X:
				case KeyEvent.VK_DOWN:
					if (row < ROWS - 1) {
						moveSmiley(row + 1, col);
					}
					moveMhos();
					break;

				case KeyEvent.VK_C:
					if (row < ROWS - 1 && col < COLS - 1) {
						moveSmiley(row + 1, col + 1);
					}
					moveMhos();
					break;

				case KeyEvent.VK_J:
					jumpSmiley();
					break;
			}
		}
	}

	private void jumpSmiley() {

		int
			x = ThreadLocalRandom.current().nextInt(1, ROWS - 1),
			y = ThreadLocalRandom.current().nextInt(1, COLS - 1);
		
		while (cells[x][y] instanceof Fence) {

			x = getRandomRow();
			y = getRandomCol();
		}
		
		if (cells[x][y] instanceof Mho) {
			return;
		} else if (cells[x][y] == null) {

			Coordinate smileyPosition = smiley.getPosition();
			cells[smileyPosition.getX()][smileyPosition.getY()] = null;
			smiley.move(new Coordinate(x, y));
			cells[x][y] = smiley;
		} 
		repaint();
	}

	private void moveSmiley(int x, int y) {

		if (cells[x][y] instanceof Fence) {
			gameOver();
		} else if (cells[x][y] instanceof Mho) {
			gameOver();
		} else if (cells[x][y] == null) {
			// first reset current cell, then move to next position, assign smiley at that position
			Coordinate smileyPosition = smiley.getPosition();
			cells[smileyPosition.getX()][smileyPosition.getY()] = null;
			smiley.move(new Coordinate(x, y));
			cells[x][y] = smiley;
		}

		repaint();
	}
	
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

	private void moveVertically(int i, Mho mho) {

		int row;
		int col;

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
			// Don't move
		} else {
			move(mho, new Coordinate(row, col));
		}
	}
	
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
				
		if(cells[row][col] instanceof Fence) {
			cells[mhoPosition.getX()][mhoPosition.getY()] = null;
			mhos.remove(i);
		}
		else if(cells[row][col] instanceof Smiley){
			killSmiley(mho, new Coordinate(row, col));
		} else if(cells[row][col] instanceof Mho){
			// Don't move
		} else{
			move(mho, new Coordinate(row, col));
		}
	}

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
			//Don't move
		} else if (cells[row][col] == null) {
			move(mho, new Coordinate(row, col));
			cells[mhoPosition.getX()][mhoPosition.getY()] = null;
			cells[row][col] = mho;
		}
	}


	private void killSmiley(Mho mho, Coordinate position) {

		final Coordinate mhoPosition = mho.getPosition();

		cells[mhoPosition.getX()][mhoPosition.getY()] = null;
		mho.move(position);
		gameOver();
		cells[position.getX()][position.getY()] = new Mho(position);
	}
	
	private void move(Mho mho, Coordinate newPosition) {

		final Coordinate mhoPosition = mho.getPosition();

		cells[mhoPosition.getX()][mhoPosition.getY()] = null;
		mho.move(newPosition);
		cells[newPosition.getX()][newPosition.getY()] = new Mho(newPosition);
		
	}
	
	private void gameOver() {

		System.out.println("Game Over. You Lose!");
		gameOver = true;
		final Coordinate smileyPosition = smiley.getPosition();
		cells[smileyPosition.getX()][smileyPosition.getY()] = null;
		smiley = null;
		removeKeyListener(this);
	}
	

	private Coordinate getRandomPosition() {

		final int randomIndex = ThreadLocalRandom.current().nextInt(0, emptyCoordinates.size());
		return emptyCoordinates.get(randomIndex);
	}

	private int getRandomCol() {

		int y = ThreadLocalRandom.current().nextInt(1, COLS - 1);
		return y;
	}

	private int getRandomRow() {

		int x = ThreadLocalRandom.current().nextInt(1, ROWS - 1);
		return x;
	}


	private void removeCell(Coordinate cell) {

		emptyCoordinates.remove(cell);
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

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);

		repaint();
	}
	
	private void drawCells(Graphics g) {

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

	private void drawGrid(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(X_GRID_OFFSET, Y_GRID_OFFSET, (CELL_WIDTH+1)*ROWS, (CELL_HEIGHT+1)*COLS);

		g.setColor(Color.DARK_GRAY);

		// Draw rows
		for (int row = 0; row <= ROWS; row++) {
			g.drawLine(
				X_GRID_OFFSET,
			Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)),
			X_GRID_OFFSET + COLS * (CELL_WIDTH  + 1),
			Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)));
		}

		// Draw columns
		for (int col = 0; col <= COLS; col++) {
			g.drawLine(
			X_GRID_OFFSET + (col * (CELL_WIDTH + 1)),
				Y_GRID_OFFSET,
			X_GRID_OFFSET + (col * (CELL_WIDTH  + 1)),
			Y_GRID_OFFSET + ROWS * (CELL_HEIGHT + 1));
		}
	}

}
