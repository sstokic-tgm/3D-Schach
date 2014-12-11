package projekt.chess.GUI;

import projekt.chess.Global.Coord;

/**
 * CoordGraph is the 2D coordinates representing, on the UI, the 3D Coord class.
 * Each 3D position in the Model has a 2D equivalent on the screen.
 */
public class CoordGraph implements Comparable<CoordGraph> {

	/* Attributs */
	private int x;
	private int y;

	/* Getters & Setters */
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

	/* Constructor(s) */
	public CoordGraph(int x, int y){

		this.x = x;
		this.y = y;
	}

	/* Comparison methods */
	public boolean equals(CoordGraph coord) {

		if(this==null && coord == null)
			return true;
		else if(this==null || coord == null)
			return false;
		else
			return (x==coord.x) && (y==coord.y);
	}

	/**
	 * Compare 2 CoordGraph objects.<br>
	 * The comparison is made first on the x attribute and then on the y of both x are equal.
	 * 
	 * @param coord
	 * @return 0 if both equal
	 * 			-1 if this is lesser than coord
	 * 			1 if this is greater than coord
	 */
	@Override
	public int compareTo(CoordGraph coord) {

		if(x < coord.x)
			return -1;
		else if(x > coord.x)
			return 1;
		else {
			if(y < coord.y)
				return -1;
			else if(y > coord.y)
				return 1;
			else 
				return 0;
		}
	}

	/**
	 * Convert the CoordGraph to a String in order to be printed or displayed
	 */
	@Override
	public String toString() {

		return "" + x + ";" + y;
	}

	/**
	 * Convert the 2D CoordGraph into a 3D Coord in order to interact with the model
	 * @return Coord equivalent
	 */
	public Coord toCoord() {

		int i,j,k;
		if(x >= 12) {

			k = 6;
			i = x-8;

		}else if(x >= 6) {

			k = 4;
			i = x-4;
		}else {

			k = 2;
			i = x;
		}

		if(y >= 2 && y <= 5) { /*fixed grid*/

			j = y-1;

		}else if(y <= 1) {

			j = y;
			k++;

		}else {

			j = y-2;
			k++;
		}
		return new Coord(i,j,k);
	}
}