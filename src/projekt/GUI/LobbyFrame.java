package projekt.GUI;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class LobbyFrame extends JFrame {

	protected LobbyPanel lobbyPanel;
	private static LobbyFrame lobbyFrameInstance;
	private JLabel welcomeUserLabel;
	
	public LobbyFrame() {
		
		super("Lobby");
		
		lobbyFrameInstance = this;
		
		lobbyPanel = new LobbyPanel();
		
		createLobbyPanel();
	}
	
	protected void createLobbyPanel() {
		
		getContentPane().add(this.lobbyPanel);
		
		JMenuBar menuBar = new JMenuBar();
		welcomeUserLabel = new JLabel();
		
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem closeItem = new JMenuItem("Close");
		fileMenu.add(closeItem);
		
		JMenuItem rulesItem = new JMenuItem("Rules");
		JMenuItem aboutItem = new JMenuItem("About");
		helpMenu.add(rulesItem);
		helpMenu.add(aboutItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(welcomeUserLabel);
		
		this.setJMenuBar(menuBar);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 899, 662);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static LobbyFrame getLobbyFrameInstance() {
		
		if(lobbyFrameInstance == null)
			lobbyFrameInstance = new LobbyFrame();
		
		return lobbyFrameInstance;
	}
	
	public JLabel getWelcomeUserLabel() {
		
		return welcomeUserLabel;
	}
}
