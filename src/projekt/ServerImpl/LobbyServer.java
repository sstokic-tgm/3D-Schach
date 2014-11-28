package projekt.ServerImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import projekt.ServerImpl.Packets.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class LobbyServer {

	private Server server;
	private Map<Integer, User> users = new HashMap<Integer, User>();
	private static LobbyServer instanceLobbyServer;

	public LobbyServer() {

		instanceLobbyServer = this;

		server = new Server();

		registerPackets();

		server.addListener(new LobbyServerNetworkListener());

		try {

			server.bind(54554); // 54554 Lobby Server port

		} catch (IOException e) {

			System.out.println(e.getMessage());
		} 
		server.start();
	}

	private void registerPackets() {

		Kryo kryo = server.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(AuthenticationPacket.class);
		kryo.register(AddUserPacket.class);
		kryo.register(RemoveUserPacket.class);
		kryo.register(AuthenticationSessionPacket.class);
		kryo.register(String.class);
	}

	public Map<Integer, User> getUsersMap() {

		return this.users;
	}

	public Server getServer() {

		return server;
	}

	public static LobbyServer getLoginServerInstance() {

		if(instanceLobbyServer == null)
			instanceLobbyServer = new LobbyServer();

		return instanceLobbyServer;
	}
}