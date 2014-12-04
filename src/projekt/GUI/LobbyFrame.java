package projekt.GUI;

import javax.swing.JFrame;

public class LobbyFrame extends JFrame {

	protected LobbyPanel lobbyPanel;
	
	public LobbyFrame() {
		
		super("Lobby");
		lobbyPanel = new LobbyPanel();
		
		createLobbyPanel();
	}
	
	protected void createLobbyPanel() {
		
		this.add(this.lobbyPanel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 899, 662);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
