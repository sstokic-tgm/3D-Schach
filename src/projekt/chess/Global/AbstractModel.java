package projekt.chess.Global;

import projekt.chess.grids.Board;
import projekt.chess.grids.Corner;
import projekt.chess.grids.Grid;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import projekt.chess.Pieces.ListPieces;
import projekt.chess.Pieces.Piece;
import projekt.chess.Players.AbstractPlayer;

/**
 * Abstract model used to respect the MVC pattern.<br>
 * Declares the public interface of the model.
 */
public interface AbstractModel {

	/**
	 * @return the value fo the gameOver boolean value.
	 */
	public boolean isGameOver();

	/**
	 * End the game by changing an attribute.<br>
	 * Called only when a player wins.
	 */
	public void endOfGame();

	/**
	 * Stop the game because the user wants to begin another game (from beginning or by loading a backup).<br>
	 * With this method, there is no winner.
	 */
	public void stopGame();

	/**
	 * Prepare a new game by resetting the list of players and the list of pieces
	 */
	public void newGame();

	/**
	 * Check if the game has been stopped intentionally by the user.
	 */
	public boolean isStopped();

	/**
	 * Initialize every pieces on the board
	 */
	public void initializePieces();

	/**
	 * Initialize the 3 fixed grids and the 4 movable grids.
	 */
	public void initializeBoard();

	/**
	 * Set the value of the gameOver boolean variable
	 * @param bool
	 */
	public void setGameOver(boolean bool);

	/* Piece movements */
	/**
	 * Check the places where a specific piece can go.
	 * @param p : the piece that we want to move
	 * @return a set containing the coord of the accesible squares
	 */
	public TreeSet<Coord> accessibleSquares(Piece p);

	/**
	 * Check the ennemy pieces that p can eat directly in one movement from its current location
	 * @param p : the piece we want to move
	 * @return a set of coordinates of the available targets
	 */
	public TreeSet<Coord> attackableSquares(Piece p);

	/**
	 * Get the occupied squares from a list of coordinates 
	 * @param set : a set of coordinates 
	 * @return the coords from the set that are occupied by another piece
	 */
	public TreeSet<Coord> attackSquares(TreeSet<Coord> set);

	/**
	 * Get the occupied squares from coordinates that are accessible to p 
	 * @param p : a piece
	 * @return the coordinates of the occupied squares within the reach of p
	 */
	public TreeSet<Coord> attackSquares(Piece p);

	/**
	 * Move a piece from a place to another
	 * @param origin : where the piece is located
	 * @param dest : where the piece will be moved
	 * @return true if the piece has been moved
	 * 			false if there was no piece at the origin coord
	 */
	public boolean moveFromCoordTo(Coord origin, Coord dest);

	/**
	 * Filter the places where a king can go to avoid putting it into checkmate.<br>
	 * Called when a player wants to move a king.
	 * 
	 * @param casesOfKing : where the king can go
	 * @param player : player that is moving his king
	 * @return a set of places where the king can move safely without being a direct target of an ennemy piece
	 */
	public TreeSet<Coord> checkFilterForKing(TreeSet<Coord> casesOfKing, AbstractPlayer player);

	/**
	 * @return the set of all pieces
	 */
	public ListPieces getPieces();

	/**
	 * Get the pieces of a specific color
	 * @param color
	 * @return a list of pieces
	 */
	public ListPieces getPiecesOf(Color color);

	/**
	 * Remove everything from the set of pieces
	 */
	public void resetPieces();

	/**
	 * Add a piece p to the set of pieces
	 * @param p
	 */
	public void addPiece(Piece p);

	/**
	 * @return the board
	 */
	public Board getBoard();

	/**
	 * Create a new empty board
	 */
	public void resetBoard();

	/**
	 * Get the list of pieces that are located on a specific grid
	 * @param g : a grid
	 * @return list of pieces
	 */
	public ListPieces getPiecesOnGrid(Grid g);

	/**
	 * Check if a grid can be moved by the current player
	 * @param g : a grid
	 * @return true if the grid belongs to the current player
	 */
	public boolean gridIsMovable(Grid g);

	/**
	 * Check if the grid is movable and if it can be moved to a specific corner
	 * @param original : current corner where the grid is placed
	 * @param newCorn : position that has to be checked
	 * @return true if the movement is authorized (see rules)
	 */
	public boolean gridCanGoToPosition(Corner original, Corner newCorn);

	/**
	 * Move the grid to a corner.<br>
	 * Check is made internally to know if the movement is allowed
	 * @param grid : the grid that will be moved
	 * @param newCorner : new corner where the grid will be
	 * @return true if the movement is possible and has been done, false otherwise
	 */
	public boolean moveGridTo(Grid grid, Corner newCorner);

	/**
	 * Give the list of corner accessible for a specific grid, depending on its current location
	 * @param g : a movable grid
	 * @return a list of corners
	 */
	public ArrayList<Corner> accessibleCornerForGrid(Grid g);

	/**
	 * Get the list of movable grids that can be moved to at least one corner next to them
	 * @return a list of grids
	 */
	public Board getMovableGrids();

	/**
	 * Check if a 2-d coordinates is free at every level (3rd dimension)
	 * @param x
	 * @param y
	 * @return true if (x,y,z) is free for all z
	 */
	public boolean allLevelsFreeAt(int x, int y);

	/**
	 * @return the list of players
	 */
	public List<AbstractPlayer> getListPlayers();

	/**
	 * Given a player, the method returns its ennemy (the other player)
	 * @param player : current player
	 * @return next player
	 */
	public AbstractPlayer getOtherPlayer(AbstractPlayer player);

	/**
	 * Create a new empty list of player
	 */
	public void resetListPlayers();

	/**
	 * Add a player to the game
	 * @param player
	 */
	public void addPlayer(AbstractPlayer player);

	/**
	 * Define the current player
	 * @param currentPlayer
	 */
	public void setCurrentPlayer(AbstractPlayer currentPlayer);

	/**
	 * @return the current player
	 */
	public AbstractPlayer getCurrentPlayer();

	/**
	 * Check if a square is empty (or non-existent)
	 * @param c : coordinate
	 * @return true if there is no piece at the coord c
	 */
	public boolean isEmptyAt(Coord c);

	/**
	 * Quick method used to know if the coordinates is within the 2D boundaries (on x and y) of the maximum coordinates possible
	 * @param c : coordinate
	 * @return true if x is within [0;10] and y within [0;5]
	 */
	public boolean coordIsInTheSpace(Coord c);

	/**
	 * @param player
	 * @return true if the player is in a check position
	 */
	public boolean isCheck(AbstractPlayer player);	
}