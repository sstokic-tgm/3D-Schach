package projekt.chess.Players;

public interface PlayerListener {	
	/**
	 * Once a player has played, this method is called to change the current player.<br>
	 * It also calls endOfGame if the King has been eaten of if the King is the only piece left of a player.
	 */
	public void hasPlayed();
}