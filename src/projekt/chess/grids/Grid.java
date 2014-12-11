package projekt.chess.grids;

import java.awt.Color;
import projekt.chess.Global.Coord;
import projekt.chess.Players.AbstractPlayer;

/**
 * A grid is a square containing either 2x2 or 4x4 squares where pieces can move.<br>
 * A classic chess game would be composed of a single grid of 8x8 squares.<br>
 * The little grids are called movable or attack grids because they can be moved during the game.
 * These grids have an original owner depending on their original side.<br>
 * The big grids are fixed.<br>
 * See Rulex.txt for more info.
 */
public class Grid {

	private int level;
	private Coord minCoord;
	private Coord maxCoord;
	private boolean attackBoard;
	private Corner corner;
	private Color defaultOwnersColor;

	public Grid() { }

	public Grid(Coord begin) {

		this.level = begin.getZ();

		minCoord = begin;
		int size = ((level%2==0) ? 4 : 2);
		maxCoord = new Coord(begin.getX() + size - 1, begin.getY() + size - 1, level);

		if(level%2 == 1)
			attackBoard = true;
		else
			attackBoard = false;

		defaultOwnersColor = originalOwner();
	}

	/**
	 * Creating a movable grid by specifying its corner with a corresponding String
	 * @param str : String
	 */
	public Grid(String str) { /*only for attack grids */

		this.corner = new Corner(str);
		this.setCoordFromCorner(corner);

		if(level%2 == 1)
			attackBoard = true;
		else
			attackBoard = false;
		defaultOwnersColor = originalOwner();
	}

	/**
	 * Creating a movable grid by specifying its corner with a corresponding String.<br>
	 * The original owner of the grid is given by argument.
	 * @param str : String
	 */
	public Grid(String str, Color colOwner) { /*only for attack grids */

		this.corner = new Corner(str);
		this.setCoordFromCorner(corner);
		if(level%2 == 1)
			attackBoard = true;
		else
			attackBoard = false;

		defaultOwnersColor = colOwner;
	}

	/* Setters and getters */
	/**
	 * Compute the min and max coordinates of the grid by looking at the corner
	 * @param cor
	 */
	public void setCoordFromCorner(Corner cor) {

		this.corner = cor;
		this.level = corner.getLevel();
		this.minCoord = corner.getMinCoord();
		this.maxCoord = new Coord(minCoord.getX() + 1, minCoord.getY() + 1, level);
	}

	public Corner getCorner() {

		return corner;
	}

	public void setCorner(Corner corner) {

		this.corner = corner;
	}

	public int getLevel() {

		return level;
	}

	public void setLevel(int level) {

		this.level = level;
	}

	public int getRows() {

		return maxCoord.getX()-minCoord.getX();
	}

	public boolean isAnAttackBoard() {
		return attackBoard;
	}

	/**
	 * Check if a coordinates is on the grid
	 * @param c
	 * @return boolean
	 */
	public boolean contains(Coord c) {

		return (c.getZ() == this.level && c.getX() >= minCoord.getX() && c.getX() <= maxCoord.getX() && c.getY() >= minCoord.getY() && c.getY() <= maxCoord.getY());
	}
	/**
	 * Check if a coordinate is on, above or below a square of the grid (does not test z)
	 * @param c
	 * @return boolean
	 */
	public boolean contains2d(Coord c) {

		return (c.getX() >= minCoord.getX() && c.getX() <= maxCoord.getX() && c.getY() >= minCoord.getY() && c.getY() <= maxCoord.getY());
	}

	public boolean isOnTheLeft() {

		return minCoord.getY() == 0;
	}

	public boolean isOnFront() {

		return minCoord.getX() == 0;
	}

	public Coord getMinCoord() {

		return minCoord;
	}

	public void setMinCoord(Coord minCoord) {

		this.minCoord = minCoord;
	}

	public Coord getMaxCoord() {

		return maxCoord;
	}


	public void setMaxCoord(Coord maxCoord) {

		this.maxCoord = maxCoord;
	}

	/**
	 * Get the color of the original owner based on its location (should only be used at the beginning of a game to give a value to the attribute defaultOwnersColor)
	 * @return color
	 */
	public Color originalOwner() {

		if(level < 4)
			return Color.WHITE;
		else if (level > 4)
			return Color.BLACK;
		else
			return null;
	}

	public Color getDefaultOwnersColor() {

		return defaultOwnersColor;
	}

	/**
	 * Convert the grid into a String in order to print of display it.
	 * @return String
	 */
	@Override
	public String toString() {

		return "Grid : " + minCoord + " to " + maxCoord;
	}

	/**
	 * Check if the player sent in parameter is the original owner of the board.<br>
	 * It is used to know if a player can move a grid when there is no piece on it.
	 * @param player
	 * @return boolean
	 */
	public boolean belongsTo(AbstractPlayer player) {

		return (player.getColor() == this.defaultOwnersColor);
	}
}