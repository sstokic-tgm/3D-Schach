package projekt.chess.grids;

import java.util.ArrayList;
import java.util.List;

import projekt.chess.Global.Coord;

/**
 * Each little grid can be moved (under certain condition) from a corner of a fixed board to another corner.<br>
 * This class defines what a corner is related to coordinates.
 */
public class Corner {

	/* Structure */
	private enum CornerSide {

		KL,
		QL;
	}

	/* Attributs */
	private CornerSide side;
	private int number;

	public Corner() { }

	/* Constructors */
	private Corner(CornerSide side, int number) {

		this.side = side;
		this.number = number;
	}

	public Corner(String string) {

		this.side = CornerSide.valueOf(string.substring(0, 2));
		this.number = string.charAt(2) - '0';
	}

	/* Getters & Setters */

	/**
	 * @return the height at which a grid would be if placed on that corner
	 */
	public int getLevel() {

		int level = 0;

		switch((number-1)/ 2) {

		case 0 : level = 3;
		break;

		case 1 : level = 5;
		break;

		case 2 : level = 7;
		break;
		}
		return level;
	}

	public CornerSide getSide() {

		return side;
	}

	public void setSide(CornerSide side) {

		this.side = side;
	}

	public int getNumber() {

		return number;
	}

	public void setNumber(int number) {

		this.number = number;
	}

	/**
	 * Get the minimum coord of a grid placed here
	 * @return coord
	 */
	public Coord getMinCoord() {

		Coord coord = new Coord();

		if(side == CornerSide.QL)
			coord.setY(0);
		else
			coord.setY(4);

		coord.setZ(getLevel());

		if(number%2 == 1) {

			coord.setX(number-1);
		}else
			coord.setX(number+2);

		return coord;
	}

	/**
	 * Check if a little grid placed on that corner would contain c
	 * @param c : coord
	 * @return boolean
	 */
	public boolean containsCoord(Coord c) {

		return this.getMinCoord().compareTo(c) <= 0 && this.getMinCoord().plus(new Coord(1,1,0)).compareTo(c) >= 0;
	}

	/* Functions */

	/**
	 * Check if a corner is accessible from the current corner (see rules)
	 * @param c
	 * @return true if the movement is allowed.<br>
	 * 		<b>Be carefull</b> : it doesn't check the number of pieces on the grid so it doesn't mean the movement is possible
	 */
	public boolean isAccessible(Corner c) {

		if(c.number == number || (Math.abs(c.number - number) <= 2 && c.side == side))
			return true;
		else
			return false;
	}

	/**
	 * Check if 2 corners are equals
	 * @param c
	 * @return boolean
	 */
	public boolean equals(Corner c) {

		return (c.number == number && c.side == side);
	}

	/**
	 * Converts the corner to a string in order to be printed or displayed
	 * @return String
	 */
	@Override
	public String toString() {

		return side.toString() + number;
	}

	/**
	 * Static method giving every corner possible (4 for each fixed board).
	 * @return list of corner
	 */
	public static List<Corner> getAllCornerPossible() {

		List<Corner> list = new ArrayList<Corner>();

		for(int i=1; i<=6 ; i++) 
			for(CornerSide cPrs : CornerSide.values())
				list.add(new Corner(cPrs, i));

		return list;
	}
}