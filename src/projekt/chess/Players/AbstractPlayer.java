package projekt.chess.Players;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import projekt.chess.GUI.AbstractView;

/**
 * AbstractPlayer used to manage the human and the bot player the same way in a part of the program.
 */
public abstract class AbstractPlayer {
	
	/* Attributs */
	private String name;
	private Color color;
	private static int numberOfPlayers = 0;
	
	private List<PlayerListener> listPlayerListener = new ArrayList<PlayerListener>();

	/* Constructor(s) */
	public AbstractPlayer(String name) {
		
		this.name = name;
		numberOfPlayers++;
		
		if(numberOfPlayers%2==1)
			color = Color.WHITE;
		else
			color = Color.BLACK;
	}
	
	public AbstractPlayer(String name, Color color) {
		
		this.name = name;
		this.color = color;
	}
	
	public AbstractPlayer(){
		
		this("Player ");
		
		if(numberOfPlayers%2==1)
			name = name + 1;
		else
			name = name + 2;
	}
	
	/* Getters & Setters */
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public Color getColor() {
		
		return color;
	}
	public void setColor(Color color) {
		
		this.color = color;
	}

	/* Listener functions */
	public void addPlayerListener(PlayerListener window) {
		
		this.listPlayerListener.add(window);
	}
	
	public void fireHasPlayed() {
		
		for (PlayerListener listener : this.listPlayerListener) {
			
			listener.hasPlayed();
		}
	}
	
	@Override
	public String toString() {
		
		return name + " (" + getClass().getName() + ") : " + color;
	}
}
