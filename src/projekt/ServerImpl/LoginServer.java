package projekt.ServerImpl;

import java.io.IOException;
import java.util.*;

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
public class LoginServer {

	private Server server;
	private Map<Integer, AuthenticationSessionPacket> sessionUsers = new HashMap<Integer, AuthenticationSessionPacket>();
	private static LoginServer instanceLoginServer;
	

	public LoginServer() {

		instanceLoginServer = this;
		
		server = new Server() {

			protected Connection newConnection () {

				DataBaseConnection dbCon = new DataBaseConnection();

				return dbCon;
			}
		};


		registerPackets();
		server.addListener(new LoginServerNetworkListener());

		try {
			
			server.bind(54553); // 54553 Login Server port
			
		} catch (IOException e) {
			
			System.out.println(e.getMessage());
		} 
		server.start();
	}

	private void registerPackets() {

		Kryo kryo = server.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(AuthenticationPacket.class);
		kryo.register(AuthenticationSessionPacket.class);
	}

	public Map<Integer, AuthenticationSessionPacket> getSessionUsers() {

		return this.sessionUsers;
	}
	
	public Server getServer() {
		
		return server;
	}
	
	public static LoginServer getLoginServerInstance() {
		
		if(instanceLoginServer == null)
			instanceLoginServer = new LoginServer();
		
		return instanceLoginServer;
	}

	public static void main(String[] args) throws IOException {

		new LoginServer();
		new LobbyServer();
	}

}