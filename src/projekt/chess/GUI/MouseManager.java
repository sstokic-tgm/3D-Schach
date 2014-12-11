package projekt.chess.GUI;

import projekt.chess.grids.Corner;
import projekt.chess.grids.Grid;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import projekt.chess.Global.AbstractModel;
import projekt.chess.Global.Coord;
import projekt.chess.Pieces.Bishop;
import projekt.chess.Pieces.Knight;
import projekt.chess.Pieces.Pawn;
import projekt.chess.Pieces.Piece;
import projekt.chess.Pieces.Queen;
import projekt.chess.Pieces.Rook;
import projekt.chess.Players.AbstractPlayer;
import projekt.chess.Players.BotPlayer;
import projekt.chess.Players.HumanPlayer;
import projekt.chess.Players.PlayerListener;

/**
 * Listens for the mouse actions in the UI
 */
public class MouseManager implements MouseListener {

	/* Attributs */
	private AbstractModel model;		
	private AbstractView view;

	private CoordGraph lastColoredCaseCoord;
	private CoordGraph lastPressedCaseCoord;	
	private Grid lastColoredGrid;	
	private Grid lastPressedGrid;	

	private ArrayList<Corner> listAccessibleCorner = null;
	private TreeSet<Coord> listAccessibleSquares = null;

	/* Constructor(s) */
	public MouseManager(AbstractModel abstractModel, AbstractView window) {

		this.model = abstractModel;
		this.view = window;

		lastColoredCaseCoord = null;
		lastColoredGrid = null;
		lastPressedCaseCoord = null;
		lastPressedGrid = null;				
	}

	/* ColoredLayout methods */
	/**
	 * Add a colored layout on top of the JPanel.<br>
	 * Used to show the users the movement possible for the piece he selected.
	 * 
	 * @param pointerCoordGraph
	 * @param pointerPiece
	 */
	private void addAllColoredLayout(CoordGraph pointerCoordGraph, Piece pointerPiece) {	

		Color moveLayoutColor = new Color(0, 255, 0, 100);
		Color attackLayoutColor = new Color(255, 0, 0, 100);

		listAccessibleSquares = model.accessibleSquares(pointerPiece);

		for(Coord tmpCoord : listAccessibleSquares) {

			if(model.getPieces().getPieceAt(tmpCoord) == null)
				view.getCaseFromCoords(tmpCoord.toCoordGraph()).addColoredLayout(moveLayoutColor);
			else
				view.getCaseFromCoords(tmpCoord.toCoordGraph()).addColoredLayout(attackLayoutColor);
		}
		lastColoredCaseCoord = pointerCoordGraph;
	}

	/**
	 * When a movement has been made or if the user has selected another piece, 
	 * the coloured layout added by {@link #addAllColoredLayout()} must be removed.
	 */
	private void cleanAllColoredLayout() {

		if(lastColoredCaseCoord != null) {

			Piece p = model.getPieces().getPieceAt(lastColoredCaseCoord.toCoord());

			if(listAccessibleSquares == null)
				listAccessibleSquares = model.accessibleSquares(p);

			for(Coord tmpCoord : listAccessibleSquares)
				view.getCaseFromCoords(tmpCoord.toCoordGraph()).cleanColoredLayout();

			listAccessibleSquares=null;
			lastColoredCaseCoord=null;
		}
	}

	/**
	 * Colour the background of grid's panels from the small map
	 * @param pointerGrid
	 */
	private void addAllGridColoredLayout(Grid pointerGrid) {

		Color moveLayoutColor = new Color(0, 255, 0, 100);
		int tmpX, tmpY;

		listAccessibleCorner = model.accessibleCornerForGrid(pointerGrid);

		for(Corner tmpCorner : listAccessibleCorner) {

			tmpX = tmpCorner.getMinCoord().toCoordGraph().getX();
			tmpY = tmpCorner.getMinCoord().toCoordGraph().getY();

			view.getSmallCaseFromCoords(new CoordGraph(tmpX, tmpY)).addColoredLayout(moveLayoutColor);
			view.getSmallCaseFromCoords(new CoordGraph(tmpX+1, tmpY)).addColoredLayout(moveLayoutColor);
			view.getSmallCaseFromCoords(new CoordGraph(tmpX, tmpY+1)).addColoredLayout(moveLayoutColor);
			view.getSmallCaseFromCoords(new CoordGraph(tmpX+1, tmpY+1)).addColoredLayout(moveLayoutColor);
		}
		lastColoredGrid = pointerGrid;
	}

	/**
	 * Clean the color added by {@link #addAllGridColoredLayout(Grid)}
	 */
	private void cleanAllGridColoredLayout() {

		int tmpX, tmpY;

		if(lastPressedGrid != null) {

			if(lastColoredGrid != null)
				listAccessibleCorner = model.accessibleCornerForGrid(lastColoredGrid);
			else if(lastPressedGrid != null)
				listAccessibleCorner = model.accessibleCornerForGrid(lastPressedGrid);

			for(Corner tmpCorner : listAccessibleCorner) {

				tmpX = tmpCorner.getMinCoord().toCoordGraph().getX();
				tmpY = tmpCorner.getMinCoord().toCoordGraph().getY();

				view.getSmallCaseFromCoords(new CoordGraph(tmpX, tmpY)).cleanColoredLayout();
				view.getSmallCaseFromCoords(new CoordGraph(tmpX+1, tmpY)).cleanColoredLayout();
				view.getSmallCaseFromCoords(new CoordGraph(tmpX, tmpY+1)).cleanColoredLayout();
				view.getSmallCaseFromCoords(new CoordGraph(tmpX+1, tmpY+1)).cleanColoredLayout();
			}
			listAccessibleCorner=null;
			lastColoredGrid=null;
		}
	}

	/* Attack-Move verification */

	/**
	 * Check if the destination is reachable from the selected piece
	 * @param destination
	 * @return true if the movement / attack is possible
	 */
	private boolean isAccessible(Coord destination) {

		boolean toBeReturn = false;

		if(listAccessibleSquares != null && listAccessibleSquares.contains(destination))
			toBeReturn = true;
		else if(listAccessibleCorner != null) {

			for(Corner tmpCorner : listAccessibleCorner) {

				if(tmpCorner.containsCoord(destination))
					toBeReturn = true;
			}
		}	
		return toBeReturn;			
	}

	/**
	 * Move a piece from a 2D coord on the screen to another
	 * @param from
	 * @param to
	 */
	private void moveFromTo(CoordGraph from, CoordGraph to) {

		moveFromTo(from.toCoord(), to.toCoord());
	}
	/**
	 * Move a piece from a 3D coord on the screen to another
	 * @param from
	 * @param to
	 */
	public void moveFromTo(Coord from, Coord to) {

		view.piecesCleaning();
		model.moveFromCoordTo(from, to);
		view.piecesPlacement();
		Piece p = model.getPieces().getPieceAt(to);

		if(p instanceof Pawn && ((p.getColor().equals(Color.WHITE) && ((to.getX() == 8 && to.getZ() == 6) || to.getX() == 9) ||
				(p.getColor().equals(Color.BLACK) && ((to.getX() == 1 && to.getZ() == 2) || to.getX() == 0))))) 
		{
			promotion(p);
			view.piecesCleaning();
			view.piecesPlacement();
		}
		view.deadPiecesCleaning();		
		view.deadPiecesPlacement();	

		view.revalidate();
	}

	/**
	 * Move an attack grid to a specific corner
	 * @param gToMove
	 * @param tmpCorn
	 */
	public void moveGridTo(Grid gToMove, Corner tmpCorn) {

		view.cleanAttackBoards();
		view.piecesCleaning();

		model.moveGridTo(gToMove, tmpCorn);

		view.attackBoardPlacement();
		view.piecesPlacement();
	}

	/**
	 * Refresh the whole user interface
	 */
	public void refreshAll() {

		view.cleanAttackBoards();
		view.piecesCleaning();		

		view.attackBoardPlacement();

		view.piecesPlacement();	
		view.revalidate();
	}	

	/* Methods for MouseListener */
	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	/**
	 * Manage the different actions taken when the user click somewhere on the window (other than a button or a menu item).<br>
	 * The action taken depend on the previous clicks and the area selected.
	 * @param e : MouseEvent
	 */
	public void mousePressed(MouseEvent e) {

		CoordGraph pointerCoordGraph = view.coordCaseAtPointer(e.getX(), e.getY());	

		/* Case in the mainGrid */
		if(pointerCoordGraph != null) {

			/* If no case is "focused" or if the user have clicked on a different one */
			if(lastPressedCaseCoord == null || !pointerCoordGraph.toCoord().equals(lastPressedCaseCoord)) {	

				Piece pointerPiece = model.getPieces().getPieceAt(pointerCoordGraph.toCoord());

				/* If there is a piece on this case and it belongs to the current player */
				if(pointerPiece != null && pointerPiece.belongsTo(model.getCurrentPlayer())) {

					/* Clean the case focus effect */
					if(lastPressedCaseCoord != null)
						this.cleanAllColoredLayout();					

					/* Add the focus effect */
					lastPressedCaseCoord=pointerCoordGraph;
					this.addAllColoredLayout(pointerCoordGraph, pointerPiece);

					view.revalidate();
				}
				/* If there is no piece, that a case is "focused" and that the case is accessible */
				else if(lastPressedCaseCoord != null && isAccessible(pointerCoordGraph.toCoord())) {

					/* Move the piece */
					moveFromTo(lastPressedCaseCoord, pointerCoordGraph);			

					/* Clean the case focus effect */
					this.cleanAllColoredLayout();

					model.getCurrentPlayer().fireHasPlayed();
				}
			}

			/* If a movable grid was "focused", we clean his focus effect */
			if(lastColoredGrid != null) {

				this.cleanAllGridColoredLayout();
				lastPressedGrid=null;	
			}
		} else {

			pointerCoordGraph = view.coordSmallCaseAtPointer(e.getX(), e.getY());

			/* Case in the smallGrid */
			if(pointerCoordGraph != null) {

				Grid pointerGrid = model.getBoard().getGridContaining(pointerCoordGraph.toCoord());				
				boolean isOnAccessibleCorner = this.isAccessible(pointerCoordGraph.toCoord());

				/* If no grid is "focused" or if the user have clicked on a different one */
				if(lastPressedGrid == null || pointerGrid == null || (pointerGrid != null && lastPressedGrid != null && !pointerGrid.equals(lastPressedGrid))) {  

					/* Click on a grid (first click) */
					if(pointerGrid != null) {

						/* If the board is movable */
						if(pointerGrid.getCorner() != null) {	

							/* If the board belongs to the current player */
							if(pointerGrid.belongsTo(model.getCurrentPlayer())) {

								/* Clean the grid focus effect */
								if(lastPressedGrid != null)
									this.cleanAllGridColoredLayout();

								/* Add the focus effect */
								lastPressedGrid = pointerGrid;
								this.addAllGridColoredLayout(lastPressedGrid);

								view.revalidate();
							}
							/* Clean the grid focus effect */
							else {

								this.cleanAllGridColoredLayout();
								lastPressedGrid=null;	
							}
						}
						/* If board is not a movable one */
						else {

							this.cleanAllGridColoredLayout();
							lastPressedGrid=null;	
						}
					}
					/* Try to access to a corner (second click) */
					else if(isOnAccessibleCorner) {

						Coord pointerCoord = pointerCoordGraph.toCoord();

						/* Find the corner and move the grid to the corner */
						for(Corner tmpCorner : listAccessibleCorner) {

							if(tmpCorner.containsCoord(pointerCoord)) {

								/* Clean the grid focus effect */
								this.cleanAllGridColoredLayout();	

								/* Move the grid */
								this.moveGridTo(lastPressedGrid, tmpCorner);

								lastPressedGrid=null;	
							}
						} if(lastPressedGrid != null) {

							/* Clean the grid focus effect */
							this.cleanAllGridColoredLayout();	
							lastPressedGrid=null;	
						}
						model.getCurrentPlayer().fireHasPlayed();			
					}
				}
			}
			/* If a case was "focused", we clean his focus effect */
			if(lastPressedCaseCoord != null)
				this.cleanAllColoredLayout();
		}
	}

	/**
	 * Manage the "drop" action in a drag and drop of a piece.
	 */
	@Override
	public void mouseReleased(MouseEvent e){

		CoordGraph pointerCoordGraph = view.coordCaseAtPointer(e.getX(), e.getY());

		if(pointerCoordGraph != null && lastPressedCaseCoord != null) {       		

			if(!pointerCoordGraph.equals(lastPressedCaseCoord)) {

				/* Then move the piece */
				if(isAccessible(pointerCoordGraph.toCoord())) {

					moveFromTo(lastPressedCaseCoord, pointerCoordGraph);
					this.cleanAllColoredLayout();
					model.getCurrentPlayer().fireHasPlayed();

				}else if(lastColoredCaseCoord != null)  
					this.cleanAllColoredLayout();

				lastPressedCaseCoord=null;
			}			
		}		
	}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

	/**
	 * Ask the user with 2 popup the type of players (human or bot) and create them in the model.
	 */
	public void createPlayers() {

		model.resetListPlayers();
		AbstractPlayer p1, p2;

		if(view.popupPlayer("Player 1") == JOptionPane.YES_OPTION)
			p1 = new HumanPlayer();
		else
			p1 = new BotPlayer();

		if(view.popupPlayer("Player 2") == JOptionPane.YES_OPTION)
			p2 = new HumanPlayer();
		else
			p2 = new BotPlayer();

		model.addPlayer(p1);
		model.addPlayer(p2);

		for(AbstractPlayer player : model.getListPlayers()) {

			player.addPlayerListener((PlayerListener) model);
			player.addPlayerListener((PlayerListener) view);
		}		
		model.setCurrentPlayer(p1);
	}
	/**
	 * Promotion is applied when a pawn has reached the ennemy line.<br>
	 * It is then allowed to transform into a Queen, a Bishop, a Knight or a Rook.<br>
	 * The player chooses what he prefers within a popup window. 
	 * @param p
	 */
	private void promotion(Piece p) {

		UIManager.put("OptionPane.cancelButtonText", "Cancel");

		Object[] possibilities = {"Queen", "Bishop", "Knight", "Rook"};
		String input = (String) JOptionPane.showInputDialog(null, "Your pawn can be promoted.\nSelect its new type : ", "Promotion", JOptionPane.QUESTION_MESSAGE, null, possibilities, possibilities[0]);

		Piece newP = null;
		if(input.equals("Rook"))
			newP = new Rook(p.getCoordinates(), p.getColor());
		else if(input.equals("Knight"))
			newP = new Knight(p.getCoordinates(), p.getColor());
		else if(input.equals("Bishop"))
			newP = new Bishop(p.getCoordinates(), p.getColor());
		else
			newP = new Queen(p.getCoordinates(), p.getColor());

		model.getPieces().remove(p);

		if(newP != null)
			model.getPieces().add(newP);

		view.piecesCleaning();	
		view.piecesPlacement();
	}
}