package projekt.chess.Pieces;

import java.awt.Color;

import projekt.chess.Global.Coord;

public class Bishop extends Piece {

	public Bishop(Coord c, Color color) {

		super(c, color);

		movements.add(new Move(1, 1, true));
		movements.add(new Move(1, -1, true));
		movements.add(new Move(-1, 1, true));
		movements.add(new Move(-1, -1, true));
	}
}