package projekt.GUI;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JLabel;

public class LobbyFrame extends JFrame {

	protected LobbyPanel lobbyPanel;
	
	public LobbyFrame() {
		
		super("Lobby");
		lobbyPanel = new LobbyPanel();
		
		createLobbyPanel();
	}
	
	protected void createLobbyPanel() {
		
		getContentPane().add(this.lobbyPanel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 899, 662);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
