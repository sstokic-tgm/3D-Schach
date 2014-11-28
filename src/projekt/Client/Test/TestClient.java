package projekt.Client.Test;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

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


	public void loginTestClient(String username, String password) {

		this.username = username;
		
		client = new Client();
		client.start();

		registerPackets();
		
		client.addListener(this);

		authPacket = ObjectSpace.getRemoteObject(client, 1, AuthenticationPacket.class);

		//		new Thread("Connect") {
		//			public void run () {
		//				try {
		//					client.connect(5000, "localhost", 54555);
		//					
		//					login = loginPacket.login("test", "test");
		//					
		//					System.out.print("Login... Connection status: ");
		//					
		//					if(login == true)
		//						System.out.print("true");
		//					else
		//						System.out.print("false");
		//					
		//					System.out.println("\nDisconnecting... Connection status: " + loginPacket.disconnect());
		//					
		//				} catch (IOException ex) {
		//					System.out.println("" + ex.getMessage());
		//					System.exit(1);
		//				}
		//			}
		//		}.start();

		new Thread( () -> {
			try {
				client.connect(5000, "localhost", 54553);
				

				login = authPacket.login(username, password);

				System.out.print("Login... Connection status: ");

				if(login == true) {
					System.out.print("true");

					System.out.println("\nDisconnecting... Connection status: " + authPacket.disconnect());
					

					//while(client.isConnected()) {}
					


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

		//		new Thread("Connect") {
		//			public void run () {
		//				try {
		//					client.connect(5000, "localhost", 54555);
		//					
		//					login = loginPacket.login("test", "test");
		//					
		//					System.out.print("Login... Connection status: ");
		//					
		//					if(login == true)
		//						System.out.print("true");
		//					else
		//						System.out.print("false");
		//					
		//					System.out.println("\nDisconnecting... Connection status: " + loginPacket.disconnect());
		//					
		//				} catch (IOException ex) {
		//					System.out.println("" + ex.getMessage());
		//					System.exit(1);
		//				}
		//			}
		//		}.start();

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
			
			System.out.println(packet.username + packet.id);

		}else if(obj instanceof RemoveUserPacket) {

			RemoveUserPacket packet = (RemoveUserPacket)obj;
			users.remove(packet.id);

		}else if(obj instanceof AuthenticationSessionPacket) {
			System.out.println(username);
			AuthenticationSessionPacket authSessionPacket = (AuthenticationSessionPacket)obj;
			client.stop();
			System.out.println(username);
			client = new Client();
			client.start();
			client.addListener(this);
			
			try {
				client.connect(5000, "localhost", 54554);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(username);
			
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
