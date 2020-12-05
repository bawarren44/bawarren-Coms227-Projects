package hw3;

import static api.CellState.MOVABLE_NEG;
import static api.CellState.MOVABLE_POS;
import static api.CellState.PEARL;
import static api.CellState.PORTAL;

import java.lang.reflect.Array;
import java.util.ArrayList;

import api.Cell;
import api.CellState;
import api.Descriptor;
import api.Direction;
import api.StringUtil;

/**
 * Basic game state and operations for a simplified version of the video game
 * "Quell".
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class CS227Quell {

	/**
	 * Tracks number of pearls left.
	 */
	private int pearlCount;

	/**
	 * Counts the number of pearls that have been disappeared.
	 */
	private int score;

	/**
	 * Determines whether the player is alive or not.
	 */
	private boolean pAlive;

	/**
	 * Tracks total number of moves made by the player.
	 */
	private int moves;

	/**
	 * Two-dimensional array of Cell objects representing the grid on which the game
	 * is played.
	 */
	private Cell[][] grid;

	/**
	 * Instance of GameSupport to be used in the move() algorithm.
	 */
	private GameSupport support;

	/**
	 * Constructs a game from the given string description. The conventions for
	 * representing cell states as characters can be found in
	 * <code>StringUtil</code>.
	 * 
	 * @param init    string array describing initial cell states
	 * @param support GameSupport instance to use in the <code>move</code> method
	 */
	public CS227Quell(String[] init, GameSupport support) {
		grid = StringUtil.createFromStringArray(init);
		this.support = support;
		score = 0;
		pAlive = true;
		moves = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].getState().equals(CellState.PEARL)) {
					pearlCount++;
				}
			}
		}
	}

	/**
	 * Returns the number of columns in the grid.
	 * 
	 * @return width of the grid
	 */
	public int getColumns() {
		return grid[0].length;
	}

	/**
	 * Returns the number of rows in the grid.
	 * 
	 * @return height of the grid
	 */
	public int getRows() {
		return grid.length;
	}

	/**
	 * Returns the cell at the given row and column.
	 * 
	 * @param row - row index for the cell
	 * @param col - column index for the cell
	 * @return cell at given row and column
	 */
	public Cell getCell(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Returns true if the game is over, false otherwise. The game ends when all
	 * pearls are removed from the grid or when the player lands on a cell with
	 * spikes.
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean isOver() {
		if (pearlCount == 0) {
			return true;
		}
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				// If player is near a spike that is deadly to them
				if ((CellState.spikesAreDeadly(grid[i][j].getState(), Direction.UP) && grid[i + 1][j].isPlayerPresent())
						|| (CellState.spikesAreDeadly(grid[i][j].getState(), Direction.DOWN)
								&& grid[i - 1][j].isPlayerPresent())
						|| (CellState.spikesAreDeadly(grid[i][j].getState(), Direction.RIGHT)
								&& grid[i][j - 1].isPlayerPresent())
						|| (CellState.spikesAreDeadly(grid[i][j].getState(), Direction.LEFT)
								&& grid[i][j + 1].isPlayerPresent())) {

					pAlive = false;
				}

			}

		}
		if (pAlive == false) {
			return true;
		}

		return false;
	}

	/**
	 * Returns true if the player won.
	 * 
	 * @return true if game is over and player is alive.
	 */
	public boolean won() {
		return (isOver() == true && pAlive == true);
	}

	/**
	 * Returns the current number of moves made in this game.
	 * 
	 * @return number of moves made.
	 */
	public int getMoves() {
		return moves;
	}

	/**
	 * Returns the current score (number of pearls disappeared) for this game.
	 * 
	 * @return number of pearls disappeared for the current game.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Performs a move along a cell sequence in the given direction, updating the
	 * score, the move count, and all affected cells in the grid. The method returns
	 * an array of Descriptor objects representing the cells in original cell
	 * sequence before modification, with their <code>movedTo</code> and
	 * <code>disappeared</code> status set to indicate the cells' new locations
	 * after modification.
	 * 
	 * @param dir - direction of the move
	 * @return array of Descriptor objects describing modified cells
	 */
	public Descriptor[] move(Direction dir) {
		Cell[] seq = null;
		seq = getCellSequence(dir);
		Descriptor[] descriptors = new Descriptor[seq.length];
		support.shiftMovableBlocks(seq, descriptors);
		support.shiftPlayer(seq, descriptors, dir);
		setCellSequence(seq, dir);
		for (int i = 0; i < seq.length; i++) {
			for (int j = i; i < descriptors.length; j++) {
				// Determines if a Pearl was recently in the index, if so adds 1 to score
				if (seq[i].getState().equals(CellState.EMPTY) && descriptors[j].isDisappeared()) {
					++score;
				}
			}
		}
		++moves;
		return descriptors;
	}

	/**
	 * Finds a valid cell sequence in the given direction starting with the player's
	 * current position and ending with a boundary cell as defined by the method
	 * CellState.isBoundary. The actual cell locations are obtained by following
	 * getNextRow and getNextColumn in the given direction, and the sequence ends
	 * when a boundary cell is found. A boundary cell is defined by the
	 * CellState.isBoundary and is different depending on whether a movable block
	 * has been encountered so far in the cell sequence (the player can move through
	 * open gates and portals, but the movable blocks cannot). It can be assumed
	 * that there will eventually be a boundary cell (i.e., the grid has no infinite
	 * loops). The first element of the returned array is the cell containing the
	 * player, and the last element of the array is the boundary cell. This method
	 * does not modify the grid or any aspect of the game state.
	 * 
	 * @param dir - direction of the sequence
	 * @return array of cells in the cell sequence
	 */
	public Cell[] getCellSequence(Direction dir) {
		int rowTemp = getCurrentRow();
		int colTemp = getCurrentColumn();
		ArrayList<Cell> result = new ArrayList<>();
		Cell b = null;
		// Adding starting point
		b = getCell(rowTemp, colTemp);
		result.add(b);
		// Add next row until result equals grid length or a boundary is reached
		while (!(CellState.isBoundary(b.getState(), false))) {
			if (dir == Direction.UP || dir == Direction.DOWN) {
				b = getCell(getNextRow(rowTemp, colTemp, dir, false), getCurrentColumn());
				result.add(b);
				if (dir == Direction.UP) {
					--rowTemp;
				} else
					++rowTemp;

			}
			if (dir == Direction.RIGHT || dir == Direction.LEFT) {
				b = getCell(getCurrentRow(), getNextColumn(rowTemp, colTemp, dir, false));
				result.add(b);
				if (dir == Direction.LEFT) {
					--colTemp;
				} else
					++colTemp;
			}

		}

		Cell[] end = result.toArray(new Cell[result.size()]);
		return end;
	}

	/**
	 * Sets the given cell sequence and updates the player position. This method
	 * effectively retraces the steps for creating a cell sequence in the given
	 * direction, starting with the player's current position, and updates the grid
	 * with the new cells. Exactly one cell in the given sequence must have the
	 * condition isPlayerPresent true. The given cell sequence can be assumed to be
	 * structurally consistent with the existing grid, e.g., no portal or wall cells
	 * are moved.
	 * 
	 * @param cells - updated cells to replace existing ones in the sequence
	 * 
	 * @param dir   - direction of the cell sequence
	 */
	// TODO Does not work
	public void setCellSequence(Cell[] cells, Direction dir) {
		int rowTemp = getCurrentRow();
		int colTemp = getCurrentColumn();
		if (dir == Direction.LEFT || dir == Direction.RIGHT) {
			for (int i = 0; i < cells.length; i++) {
				grid[rowTemp][getCurrentColumn()] = cells[0];
				grid[getCurrentRow()][getNextColumn(rowTemp, colTemp, dir, false)] = cells[1];
				if (dir == Direction.RIGHT) {
					++colTemp;
				} else
					--colTemp;
			}
		}
		if (dir == Direction.UP || dir == Direction.DOWN) {
			for (int i = 0; i < cells.length; i++) {
				grid[getCurrentRow()][colTemp] = cells[0];
				grid[getNextRow(rowTemp, colTemp, dir, false)][getCurrentColumn()] = cells[i];
				if (dir == Direction.UP) {
					--rowTemp;
				} else
					++rowTemp;
			}
		}

	}

	/**
	 * Returns the current row containing the player.
	 * 
	 * @return cRow - current row containing the player.
	 */
	public int getCurrentRow() {
		int row = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].isPlayerPresent()) {
					row = i;
				}
			}
		}
		return row;
	}

	/**
	 * Returns the current column containing the player.
	 * 
	 * @return cCol - current column containing the player
	 */
	public int getCurrentColumn() {
		int col = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].isPlayerPresent()) {
					col = j;
				}
			}
		}
		return col;

	}

	/**
	 * Returns the next row in the sequence of cells. If the next cell contains a
	 * place where the player cannot be, returns the next row. If portal jump is
	 * true, then adds the rowOffset to row.
	 * 
	 * @param row          - current row
	 * @param col          - current column
	 * @param dir          - direction in which we want to know the next row
	 * @param doPortalJump - true if there is a portal in the next row
	 * @return the next available row to the player
	 */
	public int getNextRow(int row, int col, Direction dir, boolean doPortalJump) {

		if (dir == Direction.DOWN) {
			row = (row + 1) % getRows();
			if (doPortalJump == true) {
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						row = (row + 1) % getRows() + grid[i][j].getRowOffset();
					}
				}
			}
		}
		if (dir == Direction.UP) {
			row = ((row - 1) + getRows()) % getRows();
			if (doPortalJump == true) {
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						row = ((row - 1) + getRows()) % getRows() + grid[i][j].getRowOffset();
					}
				}
			}
		}
		if (dir == Direction.LEFT || dir == Direction.RIGHT) {
			return row;
		}
		return row;
	}

	/**
	 * Returns the next column in the sequence of cells. If the next cell contains a
	 * place where the player cannot be, returns the next column. If portal jump is
	 * true, then adds the colOffset to column.
	 * 
	 * @param row          - current row
	 * @param col          - current column
	 * @param dir          - direction in which we want to know the next column
	 * @param doPortalJump - true if there is a portal in the next column
	 * @return the next available column to the player
	 */
	public int getNextColumn(int row, int col, Direction dir, boolean doPortalJump) {

		if (dir == Direction.RIGHT) {
			col = (col + 1) % getColumns();
			if (doPortalJump == true) {
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						col = (col + 1) % getColumns() + grid[i][j].getColumnOffset();
					}
				}
			}
		}
		if (dir == Direction.LEFT) {
			col = ((col - 1) + getColumns()) % getColumns();
			if (doPortalJump == true) {
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						col = ((col - 1) + getColumns()) % getColumns() + grid[i][j].getColumnOffset();
					}
				}
			}
		}
		if (dir == Direction.UP || dir == Direction.DOWN) {
			return col;
		}
		return col;
	}

	/**
	 * Counts the number of pearls within the grid.
	 * 
	 * @return returns the number of pearls on the grid
	 */
	public int countPearls() {
		return pearlCount;
	}

}
