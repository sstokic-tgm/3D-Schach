package projekt.chess.Pieces;

import projekt.chess.Global.Coord;

/**
 * This class is used to characterized the possibilities of movement of a piece.<b>
 * Some pieces like the Pawn have different ways to move and to attack.
 * 
 * @author Maxime Bourgeois
 * @author Nathan Olff
 *
 */
public class Move 
{
	/* Structure */	
	public enum Type {

		ONLY_MOVE,
		ONLY_ATTACK,
		MOVE_AND_ATTACK;
	}

	/* Attributs */
	private int front;
	private int right;
	private boolean repeat;
	private Type attack;

	/* Constructors */
	public Move(int front, int right, boolean repeat, Type attack) {

		this.front = front;
		this.right = right;
		this.repeat = repeat;
		this.attack = attack;
	}

	public Move(int front, int right, boolean repeat) {

		this(front, right, repeat, Type.MOVE_AND_ATTACK);
	}

	/* Getters & Setters */
	public int getFront() {

		return front;
	}

	public void setFront(int front) {

		this.front = front;
	}

	public int getRight() {

		return right;
	}

	public void setRight(int right) {

		this.right = right;
	}

	public boolean isRepeat() {

		return repeat;
	}	

	public void setRepeat(boolean repeat) {

		this.repeat = repeat;
	}

	public Type getAttack() {

		return attack;
	}
	public void setAttack(Type attack) {

		this.attack = attack;
	}

	public Coord getCoord() {

		return new Coord(front, right, 0);
	}

	/* Functions */
	public boolean isAttackAndMove() {

		return (attack == Type.MOVE_AND_ATTACK);
	}

	public boolean isAttackOnly() {

		return (attack == Type.ONLY_ATTACK);
	}

	/**
	 * Check if this movement can be used to attack an ennemy
	 * @return boolean
	 */
	public boolean isAttack() {

		return (attack == Type.MOVE_AND_ATTACK || attack == Type.ONLY_ATTACK);
	}

	/**
	 * Check if this movement can be used to move to an empty square
	 * @return boolean
	 */
	public boolean isMove() {

		return (attack == Type.MOVE_AND_ATTACK || attack == Type.ONLY_MOVE);
	}

	/**
	 * Check if the two moves are equal
	 * @param m
	 * @return boolean
	 */
	public boolean equals(Move m) {

		return (m.front == front && m.right == right);
	}		

	/**
	 * Convert the Move to a String in order to print or display it
	 * @return String
	 */
	@Override
	public String toString() {

		return front + "/" + right;
	}
}