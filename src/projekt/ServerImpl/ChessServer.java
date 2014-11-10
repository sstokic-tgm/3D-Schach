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
public class ChessServer {

	private Server server;


	public ChessServer() throws IOException {

		server = new Server() {
			
			protected Connection newConnection () {
				
				DataBaseConnection dbCon = new DataBaseConnection();
				
				return dbCon;
			}
		};
		
		registerPackets();
		server.addListener(new NetworkListener());
		
		server.bind(54555);
		server.start();
	}
	
	private void registerPackets() {
		
		Kryo kryo = server.getKryo();
		
		ObjectSpace.registerClasses(kryo);
		kryo.register(AuthenticationPacket.class);
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		new ChessServer();
	}
	
}