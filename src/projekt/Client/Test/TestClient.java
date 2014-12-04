package projekt.Client.Test;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.*;
import javafx.collections.ObservableList.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import projekt.GUI.LobbyFrame;
import projekt.GUI.LobbyPanel;
import projekt.ServerImpl.Chat.ChatMessage;
import projekt.ServerImpl.Packets.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;


public class TestClient extends Listener {

	protected static TestClient testClientInstance;
	
	private Client client;
	private AuthenticationPacket authPacket;
	private boolean login = false;
	private boolean register = false;
	private Map<Integer, String> users = new HashMap<Integer, String>();
	private String username;
	protected boolean bAddUser = false;
	protected boolean bRemoveUser = false;


	public void loginTestClient(String username, String password) {

		testClientInstance = this;
		
		this.username = username;

		client = new Client();
		client.start();

		registerPackets();

		client.addListener(this);

		authPacket = ObjectSpace.getRemoteObject(client, 1, AuthenticationPacket.class);

		new Thread( () -> {
			try {
				client.connect(5000, "localhost", 54553);


				login = authPacket.login(username, password);

				System.out.print("Login... Connection status: ");

				if(login == true) {
					
					System.out.print("true");

					System.out.println("\nDisconnecting... Connection status: " + authPacket.disconnect());

					new LobbyFrame();


					while(client.isConnected()) {

						if(this.bAddUser == true) {

							//LobbyPanel.getLobbyPanelInstance().getDefaultListModel().removeAllElements();
							
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


				}else {
					System.out.print("false");
				}


			} catch (IOException ex) {
				System.out.println("" + ex.getMessage());
				System.exit(1);
			}
		}).start();
	}

	public void registerTestClient(String username, String password) {


		client = new Client();
		client.start();

		registerPackets();

		authPacket = ObjectSpace.getRemoteObject(client, 1, AuthenticationPacket.class);

		new Thread( () -> {
			try {
				client.connect(5000, "localhost", 54553);

				register = authPacket.register(username, password);

				System.out.print("Register... Registered: ");

				if(register == true)
					System.out.print("true");
				else
					System.out.print("false");

				System.out.println("\nDisconnecting... Connection status: " + authPacket.disconnect());

			} catch (IOException ex) {
				System.out.println("" + ex.getMessage());
				System.exit(1);
			}
		}).start();
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

	public Client getClient() {
		
		return client;
	}

	public String getUsername() {
		
		return username;
	}

	private void registerPackets() {

		Kryo kryo = client.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(AuthenticationPacket.class);
		kryo.register(AddUserPacket.class);
		kryo.register(RemoveUserPacket.class);
		kryo.register(AuthenticationSessionPacket.class);
		kryo.register(ChatMessage.class);
		kryo.register(String.class);
	}
	
	public static TestClient getTestClientInstance() {
		
		if(testClientInstance == null)
			testClientInstance = new TestClient();
		
		return testClientInstance;
	}
}
