package projekt.GUI;

import java.awt.event.*;

public class EnterListener implements KeyListener	{

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == '\n')	{
			
			LobbyPanel.getLobbyPanelInstance().getSendTextButton().doClick();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}