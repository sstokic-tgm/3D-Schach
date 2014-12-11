package projekt.chess.GUI;

import projekt.chess.grids.Board;
import java.awt.Component;
import java.awt.event.MouseListener;
import projekt.chess.Global.Coord;

public interface AbstractView {

	/* Placement functions */

	/**
	 * Display every movable grids both on the main panel and on the small panel
	 */
	public void attackBoardPlacement();

	/**
	 * Place a list of grids on the main panel
	 * @param boardList
	 */
	public void mainBoardPlacement(Board boardList);

	/**
	 * Place a list of grids on the small panel
	 * @param boardList
	 */
	public void smallBoardPlacement(Board boardList);

	/**
	 * Position every pieces on the board
	 */
	public void piecesPlacement();

	/**
	 * Display the dead pieces in the top-right position of the window
	 */
	public void deadPiecesPlacement();

	/* Cleaning function */

	/**
	 * Display every attack boards (without any piece)
	 */
	public void cleanAttackBoards();

	/**
	 * Display the grids from boardList without any piece on them
	 * @param boardList
	 */
	public void boardCleaning(Board boardList);

	/**
	 * Remove every piece images from the board
	 */
	public void piecesCleaning();

	/**
	 * Remove every images from the dead pieces section
	 */
	public void deadPiecesCleaning();

	/**
	 * Get the JPanelImage corresponding to the 3D coord of the model.<br>
	 * The 3D coord is internally converted to graphical coordinates by calling  {@link Global.Coord#toCoordGraph() Coord.toCoordGraph()}
	 * @param coord
	 * @return the JPanelImage corresponding to coord on the user interface
	 */
	public JPanelImage getCaseFrom3DCoords(Coord coord);

	/**
	 * Get the 2D coord graph corresponding with a user interaction on the main panel
	 * @param x, y : coordinates of click or a mouse event on the UI
	 * @return CoordGraph of the case at the (x,y) coordinates
	 */
	public CoordGraph coordCaseAtPointer(int x, int y);

	/**
	 * Get the 2D coord graph corresponding with a user interaction on the small panel
	 * @param x, y : coordinates of click or a mouse event on the UI
	 * @return CoordGraph of the case at the (x,y) coordinates
	 */
	public CoordGraph coordSmallCaseAtPointer(int x, int y);

	/**
	 * Get the JPanelImage corresponding to the 2D coordGraph sent in parameter
	 * @param coordGraph
	 * @return JPanelImage
	 */
	public JPanelImage getCaseFromCoords(CoordGraph coordGraph);

	/**
	 * Get the JPanelImage (from the small board) corresponding to the 2D coordGraph sent in parameter
	 * @param coordGraph
	 * @return JPanelImage
	 */
	public JPanelImage getSmallCaseFromCoords(CoordGraph coordGraph);

	/**
	 * Remove and display again the dead pieces
	 */
	public void refreshDeadPieces();

	/**
	 * Refresh the player names to reflect the current player in bold font
	 */
	public void refreshCurrentPlayer();

	/**
	 * Display a popup window to ask the user if the first (or second) player is a human or a bot
	 * @param playerName
	 * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION
	 */
	public int popupPlayer(String playerName);

	/*
	 * Functions from the JFrame class that are used directly on the AbstractView
	 * This should not happen if we want to respect a true MVC pattern
	 */
	public void dispose();
	public void revalidate();
	public void addMouseListener(MouseListener listener);
	public void pack();
	public void setLocationRelativeTo(Component object);
}