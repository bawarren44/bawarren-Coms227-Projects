package hw3;

import static api.CellState.*;

import java.util.ArrayList;
import java.util.Collections;

import api.Cell;
import api.CellState;
import api.Descriptor;
import api.Direction;
import api.PortalInfo;

/**
 * Utility class containing the key algorithms for moves in the CS227Quell game.
 * This class is completely stateless.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class GameSupport {
	public GameSupport() {
		// does nothing
	}

	/**
	 * Shifts the player location in a cell sequence in which no movable blocks are
	 * being moved. That is, if the sequence contains any movable blocks at all,
	 * they are all at the far right and cannot move further. The first cell in the
	 * array contains the player and the last cell in the array must be a boundary
	 * cell, as defined by the <code>CellState.isBoundary</code> method. The player
	 * moves only to the right (there is a <code>Direction</code> parameter, but
	 * that is only used to detect whether the player lands on spikes).
	 * <p>
	 * The player's new location will be one of the following:
	 * <ul>
	 * <li>the cell just before the first movable block in the array, if any, or
	 * <li>the cell just before the boundary, or
	 * <li>the boundary cell itself, in the case that the boundary is a cell with
	 * spikes whose direction is opposite that of the given direction argument (or
	 * is SPIKES_ALL)
	 * </ul>
	 * Any pearls in the sequence are disappeared and any open gates passed by the
	 * player are closed by this method.
	 * <p>
	 * If the descriptors array is non-null, then each descriptor is updated as
	 * follows:
	 * <ul>
	 * <li>The first descriptor's <code>moveTo</code> attribute is set to the
	 * player's new index in the array
	 * <li>All pearls are marked as <code>disappeared</code>
	 * </ul>
	 * The cell objects within each descriptor are not modified. It should be
	 * assumed that the given descriptors array is <em>consistent</em> with the
	 * cells array (allowing for prior movement of movable blocks from a previous
	 * call to <code>shiftMovableBlocks</code>).
	 * 
	 * @param cells       a valid cell sequence in which movable blocks, if any, are
	 *                    at the far right only
	 * @param descriptors a parallel array of <code>Descriptors</code>, consistent
	 *                    with the cell sequence (possibly null)
	 * @param dir         direction of the move, for the purpose of determining
	 *                    whether spikes may be deadly
	 */
	public void shiftPlayer(Cell[] cells, Descriptor[] descriptors, Direction dir) {
		Cell isPresent = cells[0];
		if (dir == Direction.UP) {
			// Moves until player hits a boundary
			for (int i = 0; i < cells.length; i++) {
				// Disappears pearls
				if (cells[i].getState().equals(CellState.PEARL)) {
					cells[i] = new Cell(CellState.EMPTY);
				} else if (spikesAreDeadly(cells[i].getState(), Direction.UP)) {
					// Closes gates
				} else if (cells[i].getState().equals(OPEN_GATE)) {
					cells[i].getState().equals(CLOSED_GATE);
				} else if (cells[i].getState().equals(CLOSED_GATE) || cells[i].getState().equals(SPIKES_LEFT)
						|| cells[i].getState().equals(SPIKES_RIGHT) || cells[i].getState().equals(SPIKES_UP)
						|| cells[i].getState().equals(WALL)) {
					// Moves player position by 1
					cells[i + 1] = isPresent;
					break;
				} else
					cells[i] = new Cell(CellState.EMPTY);
			}
		}
		if (dir == Direction.DOWN) {
			// Moves until player hits a boundary
			for (int i = 0; i < cells.length; i++) {
				// Disappears pearls
				if (cells[i].getState().equals(CellState.PEARL)) {
					cells[i] = new Cell(CellState.EMPTY);
				} else if (spikesAreDeadly(cells[i].getState(), Direction.DOWN)) {
					// Closes gates
				} else if (cells[i].getState().equals(OPEN_GATE)) {
					cells[i].getState().equals(CLOSED_GATE);
				} else if (cells[i].getState().equals(CLOSED_GATE) || cells[i].getState().equals(SPIKES_LEFT)
						|| cells[i].getState().equals(SPIKES_RIGHT) || cells[i].getState().equals(SPIKES_DOWN)
						|| cells[i].getState().equals(WALL)) {
					// Moves player position by 1
					cells[i - 1] = isPresent;
					break;
				} else
					cells[i] = new Cell(CellState.EMPTY);
			}
		}
		if (dir == Direction.LEFT) {
			// Moves until player hits a boundary
			for (int i = 0; i < cells.length; i++) {
				// Empty's cells with pearls in them
				if (cells[i].getState().equals(CellState.PEARL)) {
					cells[i] = new Cell(CellState.EMPTY);
				} else if (spikesAreDeadly(cells[i].getState(), Direction.LEFT)) {
					// Closes gates
				} else if (cells[i].getState().equals(OPEN_GATE)) {
					cells[i].getState().equals(CLOSED_GATE);
				} else if (cells[i].getState().equals(CLOSED_GATE) || cells[i].getState().equals(SPIKES_DOWN)
						|| cells[i].getState().equals(SPIKES_RIGHT) || cells[i].getState().equals(SPIKES_UP)
						|| cells[i].getState().equals(WALL)) {
					// Moves player position by 1
					cells[i - 1] = isPresent;
					break;
				} else
					cells[i] = new Cell(CellState.EMPTY);
			}
		}
		if (dir == Direction.RIGHT) {
			// Moves until player hits a boundary
			for (int i = 0; i < cells.length; i++) {
				// Empty's cells with pearls in them
				if (cells[i].getState().equals(CellState.PEARL)) {
					cells[i] = new Cell(CellState.EMPTY);
				} else if (spikesAreDeadly(cells[i].getState(), Direction.RIGHT)) {
					// Closes gates
				} else if (cells[i].getState().equals(OPEN_GATE)) {
					cells[i].getState().equals(CLOSED_GATE);
				} else if (cells[i].getState().equals(CLOSED_GATE) || cells[i].getState().equals(SPIKES_LEFT)
						|| cells[i].getState().equals(SPIKES_DOWN) || cells[i].getState().equals(SPIKES_UP)
						|| cells[i].getState().equals(WALL)) {
					// Moves player position by 1
					cells[i + 1] = isPresent;
					break;
				} else
					cells[i] = new Cell(CellState.EMPTY);
			}
		}
	}

	/**
	 * Shifts movable blocks in a cell sequence to the right, if any. Adjacent
	 * movable blocks with opposite parity are "merged" and removed. The last cell
	 * in the array must be a boundary cell, as defined by the
	 * <code>CellState.isBoundary</code> method. If a movable block moves over a
	 * pearl (whether or not the block is subsequently removed due to merging with
	 * an adjacent block) then the pearl is also removed.
	 * <p>
	 * If the given array of descriptors is non-null, then it must have the same
	 * length as the cell sequence and the ith descriptor must initially contain a
	 * <em>copy</em> of the ith cell in the <code>cells</code> array. When the
	 * method completes, all descriptors for movable cells must be updated with the
	 * new index of the cell and flagged as disappeared if the the cells were merged
	 * and removed. <em>Note that merging is done from the right.</em> For example,
	 * given a cell sequence represented by ".+-+#", the resulting cell sequence
	 * would be "...+#", but the descriptors would show positions 2 and 3 as having
	 * moved to index 4 and disappeared, and position 1 as having moved to index 4.
	 * 
	 * @param cells       given cell sequence
	 * @param descriptors parallel array of <code>Descriptors</code> exactly
	 *                    matching the cell sequence, possibly null
	 */

	// TODO does not work
	public void shiftMovableBlocks(Cell[] cells, Descriptor[] descriptors) {
		Cell b = null;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 1; j < cells.length; j++) {
				// Deletes blocks that can merge
				if (cells[i].getState().equals(MOVABLE_POS) && cells[i].getState().equals(MOVABLE_NEG)) {
					b = new Cell(cells[i].getState()); // b takes the block
					cells[i] = new Cell(CellState.EMPTY);
					cells[j] = new Cell(CellState.EMPTY);
				}
			}
		}

		for (int i = 0; i < cells.length; i++) {
			// Disappears pearls
			if (cells[i].getState() == (PEARL)) {
				cells[i] = new Cell(CellState.EMPTY);
			} else if (isBoundary(cells[i].getState(), true)) {
				cells[i + 1] = b;
				break;
			}

		}
	}

	/**
	 * Returns the index of the rightmost movable block that is at or to the left of
	 * the given index <code>start</code>. Returns -1 if there is no movable block
	 * at <code>start</code> or to the left.
	 * 
	 * @param cells array of Cell objects
	 * @param start starting index for searching
	 * @return index of first movable block encountered when searching towards the
	 *         left starting from the given starting index, or -1 if there is no
	 *         such cell
	 */
	public int findRightmostMovableCell(Cell[] cells, int start) {
		int index = -1;
		for (int i = start; i < cells.length; --i) {
			if (isMovable(cells[i].getState())) {
				index = i;
				break;
			}
		}
		return index;
	}

}
