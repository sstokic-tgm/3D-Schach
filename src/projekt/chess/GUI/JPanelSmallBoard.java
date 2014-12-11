package projekt.chess.GUI;

import projekt.chess.grids.Corner;

import java.awt.FlowLayout;
import javax.swing.JPanel;


public class JPanelSmallBoard extends JPanel {

	/* Attributs */
	private final Corner corner;

	/* Getters & Setters */
	public Corner getCorner() {

		return corner;
	}

	/* Constructors */
	public JPanelSmallBoard() {

		corner = null;
		((FlowLayout) this.getLayout()).setVgap(0);
	}	

	public JPanelSmallBoard(Corner corner) {	

		this.corner = corner;
		((FlowLayout) this.getLayout()).setVgap(0);
	}
}