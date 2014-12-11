package projekt.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JOptionPane;

import projekt.GUI.LobbyPanel;
import projekt.GUI.LobbyFrame;
import projekt.GUI.LoginFrame;
import projekt.ServerImpl.Chat.ChatMessage;
import projekt.ServerImpl.Packets.AddUserPacket;
import projekt.ServerImpl.Packets.AuthenticationPacket;
import projekt.ServerImpl.Packets.AuthenticationSessionPacket;
import projekt.ServerImpl.Packets.RemoveUserPacket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import javafx.application.Platform;

public class MainClient extends Listener {

	protected static MainClient mainClientInstance;

	private Client client;
	private AuthenticationPacket authPacket;
	private boolean login;
	private boolean register;
	private Map<Integer, String> users = new HashMap<Integer, String>();
	private String username;
	private String password;
	protected boolean bAddUser = false;
	protected boolean bRemoveUser = false;

	public MainClient() {

		mainClientInstance = this;

		client = new Client();
		client.start();

		registerPackets();

		client.addListener(this);
	}

	public MainClient(String username, String password) {

		mainClientInstance = this;

		this.username = username;
		this.password = password; // password sollte noch auf der Client-Seite gehasht werden(auf der Server-Seite dann auch wieder)

		client = new Client();
		client.start();

		registerPackets();

		client.addListener(this);
	}

	public void mainClientLoginUser(String username, String password) {

		this.username = username;
		this.password = password; // password sollte noch auf der Client-Seite gehasht werden(auf der Server-Seite dann auch wieder)

		authPacket = ObjectSpace.getRemoteObject(client, 1, AuthenticationPacket.class);

		new Thread( () -> {
			try {

				client.connect(5000, "localhost", 54553);

				login = authPacket.login(username, password);
				authPacket.disconnect();

				if(login == true) {

					
					new LobbyFrame();
					LobbyFrame.getLobbyFrameInstance().getWelcomeUserLabel().setText("Welcome, " + getUsername());

					while(client.isConnected()) {

						if(this.bAddUser == true) {

							LobbyPanel.getLobbyPanelInstance().getDefaultListModel().removeAllElements();

							for(Map.Entry<Integer, String> entry : users.entrySet()) {

								LobbyPanel.getLobbyPanelInstance().getDefaultListModel().addElement(entry.getValue().toString());
								System.out.println("bAddUser\n"+entry.getValue().toString());
							}


							this.bAddUser = false;
						}

						if(this.bRemoveUser == true) {

							LobbyPanel.getLobbyPanelInstance().getDefaultListModel().removeAllElements();

							for(Map.Entry<Integer, String> entry : users.entrySet()) {

								LobbyPanel.getLobbyPanelInstance().getDefaultListModel().addElement(entry.getValue().toString());
								System.out.println("bRemoveUser\n"+entry.getValue().toString());
							}

							this.bRemoveUser = false;
						}
					}


				} else {
					
					JOptionPane.showMessageDialog(null, "Username or password is incorrect!");
				}
			} catch (IOException ex) {
				System.out.println("Error: " + ex.getMessage());
				System.exit(1);
			}
		}).start();
	}

	public void mainClientRegisterUser(String username, String password) {

		authPacket = ObjectSpace.getRemoteObject(client, 1, AuthenticationPacket.class);

		new Thread( () -> {
			try {
				client.connect(5000, "localhost", 54553);

				register = authPacket.register(username, password);
				authPacket.disconnect();
				
				if(register == true) {
					
					JOptionPane.showMessageDialog(null, "Successfully registered!");
					
				} else {
					
					JOptionPane.showMessageDialog(null, "An error occured while registrating or username already exists!");
				}

				mainClientInstance = null;

			} catch (IOException ex) {
				System.out.println("Error: " + ex.getMessage());
				System.exit(1);
			}
		}).start();
	}


	protected void registerPackets() {

		Kryo kryo = client.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(AuthenticationPacket.class);
		kryo.register(AddUserPacket.class);
		kryo.register(RemoveUserPacket.class);
		kryo.register(AuthenticationSessionPacket.class);
		kryo.register(ChatMessage.class);
		kryo.register(String.class);
	}

	public boolean getBoolLogin() {

		return this.login;
	}

	public boolean getBoolRegister() {

		return this.register;
	}

	public Client getClient() {

		return this.client;
	}

	public String getUsername() {

		return this.username;
	}

	public static MainClient getMainClientInstance() {

		if(mainClientInstance == null)
			mainClientInstance = new MainClient();

		return mainClientInstance;
	}

	public void sendMessage(String message) {

		ChatMessage chatMessage = new ChatMessage();
		chatMessage.message = this.username + ": " + message;	

		client.sendTCP(chatMessage);
	}

	public void received(Connection con, Object obj) {

		if(obj instanceof AddUserPacket) {

			AddUserPacket packet = (AddUserPacket)obj;
			users.put(packet.id, packet.username);

			this.bAddUser = true;

		}else if(obj instanceof RemoveUserPacket) {

			RemoveUserPacket packet = (RemoveUserPacket)obj;
			users.remove(packet.id);

			this.bRemoveUser = true;

		}else if(obj instanceof AuthenticationSessionPacket) {

			AuthenticationSessionPacket authSessionPacket = (AuthenticationSessionPacket)obj;

			authSessionPacket.username = this.username;
			client.sendTCP(authSessionPacket);

		}else if(obj instanceof ChatMessage) {

			ChatMessage chatMessage = (ChatMessage)obj;

			LobbyPanel.getLobbyPanelInstance().getChatView().append(chatMessage.message + "\n");
		}
	}
}