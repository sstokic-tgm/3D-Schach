package projekt.Client.Test;


import java.io.IOException;

import projekt.ServerImpl.Packets.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;


public class TestClient {

	private Client client;
	private AuthenticationPacket loginPacket;
	private boolean login = false;
	private String username;
	

	public TestClient(String username, String password) {

		this.username = username;
		
		client = new Client();
		client.start();

		registerPackets();

		loginPacket = ObjectSpace.getRemoteObject(client, 1, AuthenticationPacket.class);

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
				client.connect(5000, "localhost", 54555);
				
				login = loginPacket.login(username, password);
				
				System.out.print("Login... Connection status: ");
				
				if(login == true)
					System.out.print("true");
				else
					System.out.print("false");
				
				System.out.println("\nDisconnecting... Connection status: " + loginPacket.disconnect());
				
			} catch (IOException ex) {
				System.out.println("" + ex.getMessage());
				System.exit(1);
			}
		}).start();
	}

	private void registerPackets() {

		Kryo kryo = client.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(AuthenticationPacket.class);
	}
}
