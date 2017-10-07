package com.hivolts;

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

	private static final String FENCE = "fence";

	private static final String MHO = "mho";

	private static final String SMILEY = "smiley";

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
	
	private AbstractCell smiley;
	private List<AbstractCell> mhos = new ArrayList<AbstractCell>();
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
		initSmiley();
		
		// Set Mho Cells
		for(int i = 0; i < NUM_OF_MHOS; i++) {
			initMho();
		}
		
		// Set Interior Electric Fences
		initInteriorFences();
		
		//testMoveToMhoAlignDiagonally();
		//testMhoDiesOnFence();
	}

	private void initExteriorFences() {
		for(int i = 0; i < COLS; i++) {
			cells[0][i] = new Fence(0 , i);
			removeCell(new Coordinate(0, i));
			
			cells[ROWS-1][i] = new Fence(ROWS-1, i);
			removeCell(new Coordinate(ROWS-1, i));
		}
		
		for(int i = 0; i < ROWS; i++) {
			cells[i][0] = new Fence(i, 0);
			removeCell(new Coordinate(i, 0));
			
			cells[i][ROWS-1] = new Fence(i, ROWS-1);
			removeCell(new Coordinate(i, ROWS-1));
		}
	}

	private void initInteriorFences() {
		
		for (int i = 0; i < NUM_OF_INTERIOR_FENCES; i++) {
			initObject(FENCE);
		}
	}
	
	private void initMho() {
		Coordinate c = initObject(MHO);
		mhos.add(cells[c.getX()][c.getY()]);
	}

	private void initSmiley() {
		Coordinate c = initObject(SMILEY);
		smiley = cells[c.getX()][c.getY()];
	}
	
	private Coordinate initObject(String type){
		Coordinate coordinate = getRandomPosition();
		
		int x = coordinate.getX();
		int y = coordinate.getY();

		if(type.equalsIgnoreCase(SMILEY))
			cells[x][y] = new Smiley(x, y);
		else if (type.equalsIgnoreCase(MHO))
			cells[x][y] = new Mho(x, y);
		else if (type.equalsIgnoreCase(FENCE))
			cells[x][y] = new Fence(x, y);
		
		Coordinate c = new Coordinate(x,y);
		
		removeCell(c);
		
		return c;
	}

	private void moveSmiley(KeyEvent e) {
		if (!gameOver) {
			
			int row = smiley.getX(), col = smiley.getY();
			int c = e.getKeyCode();

			switch (c) {
			case KeyEvent.VK_Q:
				System.out.println("q pressed");
				if (row > 0 && col > 0) {
					moveSmiley(row - 1, col - 1);

				}
				moveMhos();
				break;

			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				System.out.println("w pressed");
				if (row > 0) {
					moveSmiley(row - 1, col);
				}
				moveMhos();
				break;

			case KeyEvent.VK_E:
				System.out.println("e pressed");
				if (row > 0 && col < COLS - 1) {
					moveSmiley(row - 1, col + 1);
				}
				moveMhos();
				break;

			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				System.out.println("a pressed");
				if (col > 0) {
					moveSmiley(row, col - 1);
				}
				moveMhos();
				break;

			case KeyEvent.VK_S:
				System.out.println("s pressed");
				moveSmiley(row, col);
				break;

			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				System.out.println("d pressed");
				if (col < COLS - 1) {
					moveSmiley(row, col + 1);
				}
				moveMhos();
				break;

			case KeyEvent.VK_Z:
				System.out.println("z pressed");
				if (col > 0 && row < ROWS - 1) {
					moveSmiley(row + 1, col - 1);
				}
				moveMhos();
				break;

			case KeyEvent.VK_X:
			case KeyEvent.VK_DOWN:
				System.out.println("x pressed");
				if (row < ROWS - 1) {
					moveSmiley(row + 1, col);
				}
				moveMhos();
				break;

			case KeyEvent.VK_C:
				System.out.println("c pressed");
				if (row < ROWS - 1 && col < COLS - 1) {
					moveSmiley(row + 1, col + 1);
				}
				moveMhos();
				break;
			
			case KeyEvent.VK_J:
				System.out.println("j pressed");
				jumpSmiley();
				break;
			default:
				System.out.println("Key not supported!");
				return;
			}
		}
	}

	private void jumpSmiley() {
		int x = ThreadLocalRandom.current().nextInt(1, ROWS - 1),
				y = ThreadLocalRandom.current().nextInt(1, COLS - 1);
		
		while (cells[x][y] instanceof Fence) {
			x = getRandomRow();
			y = getRandomCol();
		}
		
		if (cells[x][y] instanceof Mho) {
			return;
		} else if (cells[x][y] == null) {
			cells[smiley.getX()][smiley.getY()] = null;
			smiley.move(x, y);
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
			cells[smiley.getX()][smiley.getY()] = null;
			smiley.move(x, y);
			cells[x][y] = smiley;
		}  else {
			System.out.println("Should not be here!");
		}

		repaint();
	}
	
	private void moveMhos() {

		if (!gameOver) {
			System.out.println("\nsmiley -> " + smiley.getX() + " , " + smiley.getY());

			for (int i = 0; i < mhos.size() && !gameOver; i++) {

				AbstractCell mho = mhos.get(i);

				System.out.println("current mho position -> " + mho.getX() + " , " + mho.getY());

				// mho and smiley are in same row
				if (mho.getX() == smiley.getX()) {
					moveHorizontally(i, mho);
				} 
				// mho and smiley are in same column
				else if (mho.getY() == smiley.getY()) {
					moveVertically(i, mho);
				} 
				else {
					moveMho(i, mho);
				}

				repaint();
			}
		}
	}
	

	private void moveVertically(int i, AbstractCell mho) {
		int row;
		int col;
		
		if (mho.getX() < smiley.getX()) {
			row = mho.getX() + 1;
			col = mho.getY();			
		} else {
			row = mho.getX() - 1;
			col = mho.getY();
		}
		
		cells[mho.getX()][mho.getY()] = null;
		
		if(cells[row][col] instanceof Fence)
			mhos.remove(i);
		else if(cells[row][col] instanceof Smiley) {
			killSmiley(mho, row, col);
		}
		else{
			move(mho, row, col);
		}
	}
	
	private void moveHorizontally(int i, AbstractCell mho) {
		int row;
		int col;
		
		if (mho.getY() < smiley.getY()) {
			row = mho.getX();
			col = mho.getY() + 1;
		} else {
			row = mho.getX();
			col = mho.getY() - 1;
		}
		
		cells[mho.getX()][mho.getY()] = null;
		
		if(cells[row][col] instanceof Fence)
			mhos.remove(i);
		else if(cells[row][col] instanceof Smiley){
			killSmiley(mho, row, col);
		}
		else{
			move(mho, row, col);
		}
	}
	private void moveMho(int i, AbstractCell mho) {
		AbstractCell destCell = moveToAvailableCell(mho);
		if (destCell != null) {
			cells[mho.getX()][mho.getY()] = null;
			mho = null;
			mhos.set(i, destCell);
			cells[destCell.getX()][destCell.getY()] = new Mho(destCell.getX(), destCell.getY());
			System.out.println("mho  => " + destCell.getX() + " : " + destCell.getY());
		}		
	}

	private AbstractCell moveToAvailableCell(AbstractCell mho) {
		AbstractCell destCell = findAvailableCell(mho);

		/*if (destCell == null)
			destCell = findCell(mho, "fence");*/

		return destCell;
	}

	private AbstractCell findAvailableCell(AbstractCell mho) {

		AbstractCell destCell = moveDiagonally(mho);

		/*if (destCell == null)
			destCell = findEmptyHorizontalOrVerticalCell(mho);*/

		return destCell;
	}

	private AbstractCell moveDiagonally(AbstractCell mho) {
		
		int row = 0;
		int col = 0;
		
		AbstractCell cell = null;

		if (smiley.getX() - mho.getX() > 0) {
			row = mho.getX() + 1;
		} else if (smiley.getX() - mho.getX() < 0)
			row = mho.getX() - 1;

		if (smiley.getY() - mho.getY() > 0) {
			col = mho.getY() + 1;
		} else if (smiley.getY() - mho.getY() < 0) {
			col = mho.getY() - 1;
		}

		if (cells[row][col] instanceof Smiley) {
			killSmiley(mho, row, col);
		} else if (cells[row][col] instanceof Mho){
			//Don't move
		} else if (cells[row][col] == null) {
			System.out.println("Found empty cell on diagonal -> "  + row + " , " + col);
			move(mho, row, col);
		} 
		return cell;
	}


	private void killSmiley(AbstractCell mho, int row, int col) {
		cells[mho.getX()][mho.getY()] = null;
		mho.move(row, col);
		gameOver();
		cells[row][col] = new Mho(row,col);
	}
	
	private void move(AbstractCell mho, int row, int col) {
		cells[mho.getX()][mho.getY()] = null;
		mho.move(row, col);
		cells[row][col] = new Mho(row,col);
	}
	
	private void gameOver() {
		System.out.println("Game Over. You Lose!");
		gameOver = true;
		cells[smiley.getX()][smiley.getY()] = null;
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
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
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

	/**
	 * Tests
	 * 
	 */
	
	public void testMhoDiesOnFence(){
		setSmileyForTest(7, 8);
		setupMhoForTest(7, 3);
		setupFencesForTest(7, 5);
	}
	
	public void testMoveToMhoAlignDiagonally(){
		setSmileyForTest(7, 5);
		setupMhoForTest(3, 4);
		setupFencesForTest(6, 7);
		
		moveSmiley(6, 5);
		moveMhos();
		
		moveSmiley(5, 4);
		moveMhos();
		
		if(!gameOver)
			System.out.println("Error!");
	}
	
	public void testMoveToMhoAlignVertically(){
		setSmileyForTest(5, 5);
		setupMhoForTest(3, 4);
		setupFencesForTest(6, 7);
		
		moveSmiley(5, 4);
		moveMhos();
		
		moveSmiley(3, 4);
		moveMhos();
		
		if(!gameOver)
			System.out.println("Error!");
	}
	
	public void testMoveToMhoAlignHorizontally(){
		setSmileyForTest(3, 7);
		setupMhoForTest(3, 4);
		setupFencesForTest(6, 7);
		
		moveSmiley(3, 6);
		moveMhos();
		
		moveSmiley(3, 5);
		moveMhos();
		
		if(!gameOver)
			System.out.println("Error!");
	}
	
	public void testMoveToElectricFence(){
		setSmileyForTest(4, 5);
		setupMhoForTest(3, 4);
		setupFencesForTest(6, 6);
		
		moveSmiley(5, 5);
		
		if(!gameOver)
			System.out.println("Error!");
	}
	
	public void testMoveToMho(){
		setSmileyForTest(5, 5);
		setupMhoForTest(3, 4);
		setupFencesForTest(6, 6);
		
		moveSmiley(5, 4);
		moveMhos();
		
		moveSmiley(4, 4);
		moveMhos();
		
		if(!gameOver)
			System.out.println("Error!");
	}
	
	public void testMoveToEmptyCell(){
		setSmileyForTest(4, 5);
		setupMhoForTest(3, 4);
		setupFencesForTest(6, 6);
		
		moveSmiley(5, 5);
		
		if(gameOver)
			System.out.println("Error!");
	}
	
	private void setupFencesForTest(int x, int y) {
	
		cells[x][y] = new Fence(x, y);
	}

	private void setupMhoForTest(int x, int y) {

		cells[x][y] = new Mho(x, y);
		mhos.add(cells[x][y]);
	}
	
	public void setSmileyForTest(int x, int y){

		cells[x][y] = new Smiley(x, y);
		smiley = cells[x][y];
	}
}