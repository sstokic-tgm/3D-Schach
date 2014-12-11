package projekt.chess.Pieces;

import java.awt.Color;

import projekt.chess.Global.Coord;

public class King extends Piece {

	public King(Coord c, Color color) {

		super(c, color);

		movements.add(new Move(0, -1, false));
		movements.add(new Move(0, 1, false));
		movements.add(new Move(1, 0, false));
		movements.add(new Move(-1, 0, false));
		movements.add(new Move(1, 1, false));
		movements.add(new Move(-1, -1, false));
		movements.add(new Move(-1, 1, false));
		movements.add(new Move(1, -1, false));
	}
}