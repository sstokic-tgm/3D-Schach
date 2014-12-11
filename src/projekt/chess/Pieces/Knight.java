package projekt.chess.Pieces;

import java.awt.Color;

import projekt.chess.Global.Coord;

public class Knight extends Piece {

	public Knight(Coord c, Color color) {

		super(c, color);

		movements.add(new Move(2, 1, false));
		movements.add(new Move(1, 2, false));
		movements.add(new Move(-2, 1, false));
		movements.add(new Move(-1, 2, false));
		movements.add(new Move(2, -1, false));
		movements.add(new Move(1, -2, false));
		movements.add(new Move(-2, -1, false));
		movements.add(new Move(-1, -2, false));
	}	 
}