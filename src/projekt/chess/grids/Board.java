package projekt.chess.grids;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import projekt.chess.Global.Coord;
import projekt.chess.Pieces.Piece;

/**
 * The board contains the 4 attack grids (little) and the 3 fixed grids (big)
 */
public class Board extends HashSet<Grid> {

	public Board() { }

	/**
	 * @return a set of the movable grids
	 */
	public Board getAttackBoards() {

		Board tree = new Board();

		for(Grid g : this)
			if(g.isAnAttackBoard())
				tree.add(g);

		return tree;
	}

	/**
	 * @return a set of grids located at odd height (should be the same as {@link #getAttackBoards()}
	 */
	public Board getMovableLevels() {

		Board tree = new Board();

		for(Grid g : this)
			if(g.getLevel() % 2 == 1)
				tree.add(g);

		return tree;
	}

	/**
	 * Get the grid on which a specific coordinates is located
	 * @param c
	 * @return the grid on which c is located or null if the coord is not on any grid
	 */
	public Grid getGridContaining(Coord c) {

		Iterator<Grid> it = this.iterator();
		Grid g = null, tmp = null;

		while(it.hasNext() && g == null) {

			tmp = it.next();

			if(tmp.contains(c))
				g = tmp;
		}
		return g;
	}	

	/**
	 * Get the grid located at a corner
	 * @param cor : corner
	 * @return the grid if it exists, null if there is no grid at this corner
	 */
	public Grid getGridAtCorner(Corner cor) {

		Board treeAttack = this.getAttackBoards();
		Iterator<Grid> it = treeAttack.iterator();
		Grid g;

		do {

			g = it.next();

		}while(it.hasNext() && !g.getCorner().equals(cor));

		if(g.getCorner().equals(cor))
			return g;
		else
			return null;
	}


	/**
	 * @return set of the fixed grids
	 */
	public Board getFixedLevels() {

		Board tree = new Board();

		for(Grid g : this)
			if(g.getLevel() % 2 == 0)
				tree.add(g);

		return tree;
	}

	/**
	 * Add a grid to the board
	 * @param g
	 */
	public void addGrid(Grid g) {

		this.add(g);
	}
}