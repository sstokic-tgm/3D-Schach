package projekt.chess.Pieces;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import projekt.chess.Global.Coord;
import projekt.chess.Players.AbstractPlayer;

/**
 * General class for a Piece.<br>
 * Each type of piece (pawn, rook,...) is derived from this class.
 */
public abstract class Piece {

	/* Attributs */	
	private Coord coordinates;
	private Color color;
	protected ArrayList<Move> movements;
	private boolean alive;

	/* Constructors */
	public Piece(Coord coor, Color col) {

		movements = new ArrayList<Move>();
		coordinates = coor;
		color = col;
		alive = true;
	}

	/* Getters & Setters */
	public Coord getCoordinates() {

		return coordinates;
	}

	public void setCoordinates(Coord coordinates) {

		this.coordinates = coordinates;
	}

	public Color getColor() {

		return color;
	}

	public void setColor(Color color) {

		this.color = color;
	}

	public ArrayList<Move> getMovements() {

		return movements;
	}

	public void setMovements(ArrayList<Move> movements) {

		this.movements = movements;
	}

	public int getX() {

		return coordinates.getX();
	}
	public int getY() {

		return coordinates.getY();
	}

	/* Functions */
	public boolean isAlive() {

		return alive;
	}

	/**
	 * Check if 2 pieces are enemies
	 * @param p
	 * @return true if the 2 pieces have different colors
	 */
	public boolean isEnnemyOf(Piece p) {

		return (color != p.color);
	}

	/**
	 * Check if a piece belongs to a specific player
	 * @param player
	 * @return true if the player and the piece have the same color
	 */
	public boolean belongsTo(AbstractPlayer player) {

		return (player.getColor() == this.color);
	}

	/**
	 * Kills the piece by changing its alive attribute and placing it at (0,0,0)
	 */
	public void kill() {

		alive = false;
		coordinates = new Coord(0,0,0);
	}

	/**
	 * Convert the piece to a String in order to be printed or displayed
	 * @return String
	 */
	@Override
	public String toString() {

		return this.getClass().getSimpleName() + "(" + color + ") /" + coordinates.toString();
	}

	/**
	 * Moving the piece by a relative movement
	 * @param c : coord
	 */
	public void translate(Coord c) {

		setCoordinates(coordinates.plus(c));
	}
}