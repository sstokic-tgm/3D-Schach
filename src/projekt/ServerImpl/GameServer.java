package projekt.ServerImpl;

import java.io.IOException;
import java.util.*;

import projekt.ServerImpl.Chat.ChatMessage;
import projekt.ServerImpl.Packets.*;
import projekt.ServerImpl.RegLogLogic.DataBaseConnection;
import projekt.ServerImpl.RegLogLogic.JDBCConnectionLogic;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;



/**
 * Server implementation
 * 
 * @author Stokic Stefan
 * @version 0.1
 */
public class GameServer {

	private Server server;
	private Map<Integer, AuthenticationSessionPacket> sessionUsers = new HashMap<Integer, AuthenticationSessionPacket>();
	private Map<Integer, User> users = new HashMap<Integer, User>();
	private static GameServer instanceLoginServer;
	

	public GameServer() {

		instanceLoginServer = this;
		
		server = new Server() {

			protected Connection newConnection () {

				DataBaseConnection dbCon = new DataBaseConnection();

				return dbCon;
			}
		};


		registerPackets();
		server.addListener(new GameServerNetworkListener());

		try {
			
			server.bind(54553); // 54553 Login Server port
			
		} catch (IOException e) {
			
			System.out.println(e.getMessage());
		} 
		server.start();
	}

	protected void registerPackets() {

		Kryo kryo = server.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(AuthenticationPacket.class);
		kryo.register(AddUserPacket.class);
		kryo.register(RemoveUserPacket.class);
		kryo.register(AuthenticationSessionPacket.class);
		kryo.register(ChatMessage.class);
		kryo.register(String.class);
	}

	public Map<Integer, AuthenticationSessionPacket> getSessionUsers() {

		return this.sessionUsers;
	}
	
	public Map<Integer, User> getUsersMap() {

		return this.users;
	}
	
	public Server getServer() {
		
		return server;
	}
	
	public static GameServer getLoginServerInstance() {
		
		if(instanceLoginServer == null)
			instanceLoginServer = new GameServer();
		
		return instanceLoginServer;
	}

	public static void main(String[] args) throws IOException {

		new GameServer();
	}
}