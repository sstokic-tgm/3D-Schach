package projekt.chess.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import projekt.chess.Global.AbstractModel;

/**
 * Listens for actions on the menu
 */
public class MenuListener implements ActionListener {

	/* Attributes */
	private static AbstractView window;
	private static AbstractModel model;
	private static MouseManager controller;
	private String menuType;

	/* Constructor(s) */
	public MenuListener(AbstractView window, AbstractModel model, String menuType) {

		MenuListener.window = window;
		MenuListener.model = model;
		this.menuType = menuType;
	}

	public MenuListener(String menuType) {

		this.menuType = menuType;
	}

	public static void setController(MouseManager mouse) {

		controller = mouse;
	}

	/**
	 * Reset the whole game, clean the graphical interface and display the newly created game
	 */
	static public void restartGame() {

		window.cleanAttackBoards();
		window.piecesCleaning();

		model.resetBoard();
		model.initializeBoard();		

		window.attackBoardPlacement();

		model.resetPieces();
		window.refreshDeadPieces();
		model.resetListPlayers();


		window.pack();
		window.revalidate();
		model.setGameOver(false);	
	}

	/* ActionListener methods */

	/**
	 * If an element of the menu is selecting (key pressing or mouse clicking), this method
	 * execute the corresponding action.
	 * @param arg0 : ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {		

		/* gameMenu actions */
		if(menuType.equals("newGame")) {

			model.stopGame();

			try {

				Thread.sleep(200);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			restartGame();
			controller.refreshAll();
		}	
		/* aboutMenu actions */
		else if(menuType.equals("rules")) {

			/* Should be changed to display directly the rules in a formatted display */
			JOptionPane.showMessageDialog((Component) window, "Read the file Rules.txt", "Rules", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}