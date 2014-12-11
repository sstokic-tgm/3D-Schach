package projekt.chess.Pieces;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import projekt.chess.Global.Coord;
import projekt.chess.Players.AbstractPlayer;

/**
 * List of pieces.<br>
 * Extends an ArrayList<Piece> but adds some methods specific to the game
 */
public class ListPieces extends ArrayList<Piece> {

	/**
	 * Get the pieces of a specific player.
	 * @param player
	 * @return list of pieces
	 */
	public ListPieces getPiecesOf(AbstractPlayer player) {

		ListPieces set = new ListPieces();
		Color playerColor = player.getColor();

		for(Piece p : this) {

			if(playerColor.equals(p.getColor()))
				set.add(p);
		}
		return set;
	}

	/**
	 * Get the list of pieces still alive from a set of pieces
	 * @param set
	 * @return list of pieces
	 */
	public static ListPieces getLivingPieces(ListPieces set) {

		ListPieces livingPieces = new ListPieces();

		for(Piece p : set) {

			if(p.isAlive())
				livingPieces.add(p);
		}
		return livingPieces;
	}

	/**
	 * Get the list of living pieces
	 * @return set of pieces
	 */
	public ListPieces getLivingPieces() {

		ListPieces livingPieces = new ListPieces();

		for(Piece p : this) {

			if(p.isAlive())
				livingPieces.add(p);
		}
		return livingPieces;
	}

	/**
	 * Get the list of dead pieces
	 * @return
	 */
	public ListPieces getDeadPieces() {

		ListPieces deadPieces = new ListPieces();

		for(Piece p : this) {

			if(!p.isAlive())
				deadPieces.add(p);
		}
		return deadPieces;
	}
	/**
	 * Get the piece located at c within the list
	 * @param c
	 * @return a piece if one is found, null otherwise
	 */
	public Piece getPieceAt(Coord c) {

		Piece pieceAt = null;
		Iterator<Piece> it = this.iterator();

		if(it.hasNext()) {

			Piece p = null;

			do {

				p = it.next();
			}
			while(it.hasNext() && !p.getCoordinates().equals(c));

			if(p != null && p.getCoordinates() != null && p.getCoordinates().equals(c)) {

				pieceAt = p;
			}
		}
		return pieceAt;
	}

	/**
	 * Get the king of a specific player
	 * @param player
	 * @return the king of the player, null if it is dead
	 */
	public Piece getKing(AbstractPlayer player) {

		ListPieces piecesOf = this.getPiecesOf(player);

		for(Piece p : piecesOf) {

			if(p instanceof King && p.isAlive()) {

				return p;
			}
		}
		return null;		
	}
}