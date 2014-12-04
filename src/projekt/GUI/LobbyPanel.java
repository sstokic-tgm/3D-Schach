package projekt.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import projekt.Client.Test.TestClient;
import projekt.ServerImpl.Chat.ChatMessage;

public class LobbyPanel extends JPanel {

	protected static LobbyPanel lobbyPanelInstance;
	protected String columnNames[] = {"ID", "Roomname", "Players"};


	private DefaultTableModel defaultTableModel;
	private DefaultListModel defaultListModel;

	// -- GUI elements --
	private JTable roomsTable;

	private JTextArea chatView;
	private JTextField chatText;

	private JList activeUserList;
	private JLabel userLabel;

	private JButton createRoom, sendText;

	public LobbyPanel() {

		lobbyPanelInstance = this;

		setupPanel();
	}

	protected void setupPanel() {

		this.setLayout(new BorderLayout());


		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 0));

		defaultTableModel = new DefaultTableModel(columnNames, 0);
		roomsTable = new JTable(defaultTableModel) {

			@Override
			public boolean isCellEditable(int arg0, int arg1) {

				return false;
			}
		};
		roomsTable.getTableHeader().setReorderingAllowed(false);
		roomsTable.getTableHeader().setResizingAllowed(false);
		JScrollPane tableScrollPane = new JScrollPane(roomsTable);	



		JPanel chatPanel = new JPanel();
		mainPanel.add(chatPanel);
		chatPanel.setLayout(new BorderLayout());

		chatView = new JTextArea();
		chatView.setEditable(false);
		chatView.setLineWrap(true);
		chatView.setWrapStyleWord(true);

		JScrollPane chatViewScrollPane = new JScrollPane(chatView);
		DefaultCaret caret = (DefaultCaret)chatView.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		chatText = new JTextField();
		chatText.addKeyListener(new EnterListener());
		chatText.setDocument(new LengthRestrictedDocument(256)); // 95 um bis zum textrand zu kommen

		chatPanel.add(chatViewScrollPane, BorderLayout.CENTER);
		chatPanel.add(chatText, BorderLayout.PAGE_END);


		mainPanel.add(tableScrollPane);
		mainPanel.add(chatPanel);


		JPanel activeUserPanel = new JPanel();
		activeUserPanel.setLayout(new BorderLayout());

		userLabel = new JLabel("Active Users");

		defaultListModel = new DefaultListModel();
		activeUserList = new JList(defaultListModel);

		JScrollPane listScrollPane = new JScrollPane(activeUserList);

		activeUserPanel.add(userLabel, BorderLayout.PAGE_START);
		activeUserPanel.add(listScrollPane, BorderLayout.CENTER);


		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(2, 0));

		createRoom = new JButton("Create Room");
		sendText = new JButton("Send");

		ActionHandler ah = new ActionHandler();

		sendText.addActionListener(ah);
		createRoom.addActionListener(ah);

		buttonsPanel.add(createRoom);
		buttonsPanel.add(sendText);
		activeUserPanel.add(buttonsPanel, BorderLayout.PAGE_END);


		this.add(mainPanel, BorderLayout.CENTER);
		this.add(activeUserPanel, BorderLayout.LINE_END);


	}

	public DefaultTableModel getDefaultTableModel() {

		return defaultTableModel;
	}

	public DefaultListModel getDefaultListModel() {

		return defaultListModel;
	}

	public JTextArea getChatView() {

		return chatView;
	}

	public JTextField getChatText() {

		return chatText;
	}

	public JButton getSendTextButton() {

		return sendText;
	}

	public JButton getCreateRoomButton() {

		return createRoom;
	}

	public static LobbyPanel getLobbyPanelInstance() {

		if(lobbyPanelInstance == null)
			lobbyPanelInstance = new LobbyPanel();

		return lobbyPanelInstance;
	}

	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == getSendTextButton()) {

				ChatMessage chatMessage = new ChatMessage();
				chatMessage.message = TestClient.getTestClientInstance().getUsername() + ": " + getChatText().getText();

				getChatText().setText("");

				TestClient.getTestClientInstance().getClient().sendTCP(chatMessage);

			}else if(e.getSource() == getCreateRoomButton()) {

				JOptionPane.showMessageDialog(null, "In work!");
			}
		}
	}

	private final class LengthRestrictedDocument extends PlainDocument {

		private final int limit;

		public LengthRestrictedDocument(int limit) {
			this.limit = limit;
		}

		@Override
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offs, str, a);
			}
		}
	}
}