package projekt.chess.Global;


import projekt.chess.GUI.CoordGraph;
/**
 * Coord class representing a 3D coordinate of a piece or a square on the board.
 */
public class Coord implements Comparable<Coord> {

	/* Attributs */
	private int x;
	private int y;
	private int z;

	/* Constructor(s) */
	public Coord(int x, int y, int z) {

		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Coord() { }

	/* Getters & Setters */
	public int getZ() {

		return z;
	}

	public void setZ(int z) {

		this.z = z;
	}

	public int getX() {

		return x;
	}

	public void setX(int x) {

		this.x = x;
	}

	public int getY() {

		return y;
	}

	public void setY(int y) {

		this.y = y;
	}

	/* Functions */
	/**
	 * Convert the Coord into a string in order to print it or display it in the UI
	 */
	@Override
	public String toString() {

		return "(" + x + "," + y + "," + z + ")";
	}

	/**
	 * Convert the 3D coord of the model into a 2D coordinate used by the view
	 * @return
	 */
	public CoordGraph toCoordGraph() {

		int i,j;
		i = x;
		j = y;

		if(z >= 4)
			i+=4;
		if(z >= 6)
			i+=4;
		if(!isAnAttackBoard())
			j+=1;
		else 
			if(y >= 4)	/*left <= 1 / right >= 4*/
				j+=2;				

		return new CoordGraph(i,j);
	}

	/**
	 * Check if 2 coordinates are equal
	 * @param c
	 * @return true if their 3 components are equals
	 */
	public boolean equals(Coord c) {

		return (this.z == c.z && this.x == c.x && this.y == c.y);
	}

	/**
	 * Compare 2 coordinates by looking at the 3 components.<br>
	 * The first one that is looked is the z coordinate (height), then the x and y.
	 * @param c : coordinate that will be compared to this
	 * @return 0 if both are equal
	 * 			-1 if this is "lesser" than c
	 * 			1 if this is greater than c 
	 */
	@Override
	public int compareTo(Coord c) {

		if(this.z == c.z) {

			if(this.x < c.x)
				return -1;
			else if(this.x > c.x)
				return 1;

			else {

				if(this.y < c.y)
					return -1;
				else if(this.y > c.y)
					return 1;
				else 
					return 0;
			}
		}
		else if(this.z < c.z)
			return -1;

		else return 1;
	}

	/**
	 * Add a coordinate to another
	 * @param c
	 * @return a new coordinates corresponding to the addition of the 3 components of the 2 coords
	 */
	public Coord plus(Coord c) {

		return new Coord(x + c.x, y + c.y, z + c.z);
	}

	/**
	 * Subtraction of the components of c to this in order to create another coord
	 * @param c
	 * @return the newly created coord = this - c on the 3 axis
	 */
	public Coord minus(Coord c) {

		return new Coord(x - c.x, y - c.y, z - c.z);
	}

	/**
	 * Check if the coordinate is on a movable grid or on a fixed grid by looking if its height is odd or even
	 * @return true if z is odd
	 */
	public boolean isAnAttackBoard() {

		return (z%2 == 1);
	}

	/**
	 * Quick method to know if a coordinates is within the boundaries of the board.<br>
	 * <b>Be carefull</b> : this method only looks at the big cube bounding the board, it <b>doesn't mean the square actually exists  on a grid !</b> 
	 * @return true if the coord is within the boundaries of the board (!)
	 */
	public boolean isInSpace() {

		return (x >= 0 && x < 10 && y >= 0 && y < 10 && z > 0 && z < 8);
	}	
}