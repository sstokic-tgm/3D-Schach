package projekt.chess.Players;

import projekt.chess.grids.Board;
import projekt.chess.grids.Corner;
import projekt.chess.grids.Grid;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import projekt.chess.GUI.AbstractView;
import projekt.chess.GUI.MouseManager;

import projekt.chess.Global.AbstractModel;
import projekt.chess.Global.Coord;
import projekt.chess.Pieces.ListPieces;
import projekt.chess.Pieces.Piece;

public class BotPlayer extends AbstractPlayer {

	/* Constructors */
	public BotPlayer(String name) {

		super(name);
	}

	public BotPlayer() {

		super();
	}

	/**
	 * The bot plays its turn
	 * @param model
	 * @param view
	 * @param controller
	 * @param speed : integer 
	 */
	public void play(AbstractModel model, AbstractView view, MouseManager controller, int speed) {

		Piece randomPiece = null;
		TreeSet<Coord> treeMoves;
		Board movableGrids;
		Coord randomCoord = null;
		Random rnd = new Random();
		movableGrids = model.getMovableGrids();

		// Get list of living pieces belonging to the bot
		ListPieces piecesOf = ListPieces.getLivingPieces(model.getPieces().getPiecesOf(this));
		// Check if the enemy is in a check position
		if(model.isCheck(model.getOtherPlayer(model.getCurrentPlayer()))) {

			Piece king = model.getPieces().getKing(model.getOtherPlayer(model.getCurrentPlayer()));
			Iterator<Piece> it = model.getPieces().getPiecesOf(model.getCurrentPlayer()).iterator();
			randomPiece = null;

			while(it.hasNext() && randomPiece == null) {

				Piece p = it.next();

				if(model.attackSquares(p).contains(king.getCoordinates())) {
					//Force bot to attack the king
					randomPiece = p;
					randomCoord = king.getCoordinates();
				}
			}

		}

		if(randomPiece == null)
		{		
			/*
			 * If at least one grid is movable from the bot point of view, it has 30% of chance to move a grid.
			 */
			if(rnd.nextInt(10) < 3 && movableGrids.size() > 0) {

				Grid gToMove = (Grid) selectAny(movableGrids);
				Corner tmpCorn = (Corner)selectAny(model.accessibleCornerForGrid(gToMove));
				controller.moveGridTo(gToMove, tmpCorn);
			}
			else {
				// Move of a piece
				do {

					randomPiece = selectAttackPiece(model, piecesOf);
					if(randomPiece == null) {

						randomPiece = selectMovablePiece(model, piecesOf);
					}

					treeMoves = model.accessibleSquares(randomPiece);

				}while(treeMoves.size() == 0);

				Set<Coord> attackCoords = model.attackSquares(treeMoves);
				// 80% of luck for the piece to attack (if a target is reachable)
				if(attackCoords.size() > 0 && new Random().nextInt(10) < 8) {

					randomCoord = (Coord) selectAny(attackCoords);
				}
				else
					randomCoord = (Coord) selectAny(treeMoves);
			}
		}
		if(randomPiece != null) {

			Color backgroundColor = view.getCaseFrom3DCoords(randomPiece.getCoordinates()).getBackground();
			// Background of the selected piece is changed to purple for the duration of the movement
			view.getCaseFrom3DCoords(randomPiece.getCoordinates()).setBackground(new Color(255,0,255, 100));
			view.getCaseFrom3DCoords(randomPiece.getCoordinates()).repaint();
			// Sleep in order to limit the speed of movement on the piece and let the user see what piece is selected and where it is moved
			try {

				Thread.sleep(speed, 0);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			view.getCaseFrom3DCoords(randomPiece.getCoordinates()).setBackground(backgroundColor);
			view.getCaseFrom3DCoords(randomPiece.getCoordinates()).repaint();
			backgroundColor = view.getCaseFrom3DCoords(randomCoord).getBackground();
			view.getCaseFrom3DCoords(randomCoord).setBackground(new Color(255,0,255));
			controller.moveFromTo(randomPiece.getCoordinates(), randomCoord);
			view.getCaseFrom3DCoords(randomCoord).repaint();
			// Like before
			try {

				Thread.sleep(speed, 0);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			view.getCaseFrom3DCoords(randomCoord).setBackground(backgroundColor);
			view.getCaseFrom3DCoords(randomCoord).repaint();

		}
		this.fireHasPlayed();
	}

	/**
	 * Random selection of an element in a set
	 * @param set
	 * @return the element selected
	 */
	private Object selectAny(Set set) {

		int size = set.size();
		Random rndGenerator = new Random();

		if(size != 0) {

			int item = rndGenerator.nextInt(size);
			int i = 0;

			for(Object obj : set) {

				if (i == item)
					return obj;

				i = i + 1;
			}
		}
		return null;
	}

	/**
	 * Random selection of an element in a list
	 * @param list
	 * @return the element selected
	 */
	private Object selectAny(List list) {

		int size = list.size();
		Random rndGenerator = new Random();

		if(size == 1) {

			return list.get(0);

		}else if(size != 0) {

			int item = rndGenerator.nextInt(size);
			return list.get(item);
		}
		return null;
	}

	/**
	 * Select 'randomly' a piece that can attack
	 * @param model
	 * @param set
	 * @return the piece
	 */
	private Piece selectAttackPiece(AbstractModel model, ListPieces set) {

		ListPieces tmpSet = new ListPieces();
		tmpSet.addAll(set);
		Piece p = null;

		do {

			p = (Piece) selectAny(tmpSet);
			if(p != null)
				tmpSet.remove(p);

		}while(tmpSet.size() > 0 && model.attackableSquares(p).size() == 0);

		return (tmpSet.size() == 0) ? null : p;
	}

	/**
	 * Select 'randomly' a piece that can move (without necessary attacking)
	 * @param model
	 * @param set
	 * @return the piece
	 */
	private Piece selectMovablePiece(AbstractModel model, ListPieces set) {

		ListPieces tmpSet = new ListPieces();
		tmpSet.addAll(set);
		Piece p = null;

		do {

			p = (Piece) selectAny(tmpSet);
			if(p != null)
				tmpSet.remove(p);

		}while(tmpSet.size() > 0 && model.accessibleSquares(p).size() == 0);

		return (tmpSet.size() == 0 && p == null) ? null : p;
	}
}
