package projekt.chess.Pieces;

import java.awt.Color;

import projekt.chess.Global.Coord;

public class Rook extends Piece {

	public Rook(Coord c, Color color) {

		super(c, color);
		movements.add(new Move(0, -1, true));
		movements.add(new Move(0, 1, true));
		movements.add(new Move(1, 0, true));
		movements.add(new Move(-1, 0, true));
	}
}