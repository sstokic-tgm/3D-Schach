package projekt.chess.Players;

import java.awt.Color;

public class HumanPlayer extends AbstractPlayer {
	
	/* Constructors */
	public HumanPlayer(String name) {
		
		super(name);
	}
	
	public HumanPlayer(String name, Color color) {
		
		super(name, color);
	}
	
	public HumanPlayer() {
		
		super();
	}	
}