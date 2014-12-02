package projekt.Client.Test;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
import projekt.ServerImpl.Packets.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;


public class TestClient extends Listener {

	private Client client;
	private AuthenticationPacket authPacket;
	private boolean login = false;
	private boolean register = false;
	private Map<Integer, String> users = new HashMap<Integer, String>();
	private String username;
	protected boolean bAddUser = false;
	private ObservableList data = FXCollections.observableArrayList();


	public void loginTestClient(String username, String password) {

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


					while(client.isConnected()) {


					}
					//					Iterator it = users.entrySet().iterator();
					//					while(it.hasNext()) {
					//
					//						Map.Entry pairs = (Map.Entry)it.next();
					//						System.out.println(pairs.getKey() + " = " + pairs.getValue());
					//						activeuserTable.getColumns().add(pairs.getValue());
					//						it.remove(); // avoids a ConcurrentModificationException
					//					}


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

	@FXML private ListView userList;



	public void received(Connection con, Object obj) {

		if(obj instanceof AddUserPacket) {

			AddUserPacket packet = (AddUserPacket)obj;

			users.put(packet.id, packet.username);
			this.bAddUser = true;

			if(this.bAddUser == true) {


				Iterator it = users.entrySet().iterator();
				while(it.hasNext()) {

					Map.Entry pairs = (Map.Entry)it.next();
					System.out.println(pairs.getKey() + " = " + pairs.getValue());
					//								data.add(users);
					//								activeuserTable.setItems(data);
					data.add(pairs.getValue());

					it.remove(); // avoids a ConcurrentModificationException
				}
				userList.setItems(data);
				this.bAddUser = false;
			}

		}else if(obj instanceof RemoveUserPacket) {

			RemoveUserPacket packet = (RemoveUserPacket)obj;
			users.remove(packet.id);

		}else if(obj instanceof AuthenticationSessionPacket) {

			AuthenticationSessionPacket authSessionPacket = (AuthenticationSessionPacket)obj;

			authSessionPacket.username = this.username;
			client.sendTCP(authSessionPacket);
		}
	}

	private void registerPackets() {

		Kryo kryo = client.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(AuthenticationPacket.class);
		kryo.register(AddUserPacket.class);
		kryo.register(RemoveUserPacket.class);
		kryo.register(AuthenticationSessionPacket.class);
		kryo.register(String.class);
	}
}
