package projekt.client;

import javax.swing.JOptionPane;

import projekt.GUI.LobbyFrame;
import projekt.chess.GUI.AbstractView;
import projekt.chess.GUI.MenuListener;
import projekt.chess.GUI.MouseManager;
import projekt.chess.GUI.View;
import projekt.chess.Global.AbstractModel;
import projekt.chess.Global.Model;
import projekt.chess.Players.BotPlayer;

public class ChessStarter {

	public ChessStarter() {

		AbstractModel model = new Model();		
		AbstractView view = new View(model);	

		MouseManager controller = new MouseManager(model, view);
		MenuListener.setController(controller);

		view.addMouseListener(controller);

		/* Launch the game */
		view.pack();
		view.revalidate();
		view.setLocationRelativeTo(null);

		model.newGame();

		controller.createPlayers();
		model.initializePieces();

		controller.refreshAll();

		model.setGameOver(false);

		while(!model.isGameOver() && !model.isStopped()) { // Main loop of a game
			
			if(model.getCurrentPlayer() instanceof BotPlayer) {
				
				((BotPlayer)model.getCurrentPlayer()).play(model, view, controller, 500);
			}
		}
		
		if(!model.isStopped()) {
			
			JOptionPane.showMessageDialog(null, model.getOtherPlayer(model.getCurrentPlayer()).getName() + " won the game !\nCongratulation " + model.getOtherPlayer(model.getCurrentPlayer()).getName());
		}

		LobbyFrame.getLobbyFrameInstance().setVisible(true);
		view.dispose();
	}
}