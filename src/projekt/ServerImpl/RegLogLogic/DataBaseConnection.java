package projekt.ServerImpl.RegLogLogic;

import projekt.ServerImpl.Packets.AuthenticationPacket;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class DataBaseConnection extends Connection implements AuthenticationPacket {

	JDBCConnectionLogic jdbcConLogic;
	private String username;
	private static DataBaseConnection dbCon;
	
	public DataBaseConnection() {
		
		dbCon = this;
		
		new ObjectSpace(this).register(1, this);
		
		jdbcConLogic = new JDBCConnectionLogic();
	}
	
	public boolean login(String username, String password) {
		
		this.username = username;
		
		return jdbcConLogic.login(username, password);
	}

	
	public boolean register(String username, String password) {
		
		return jdbcConLogic.register(username, password);
	}

	public boolean disconnect() {
		
		jdbcConLogic.disconnect();
		return jdbcConLogic.getStatus();
	}
	
	public String getUsername() {
		
		return this.username;
	}
	
	public static DataBaseConnection getDatabaseConnectionInstance() {
		
		if(dbCon == null)
			dbCon = new DataBaseConnection();
		
		return dbCon;
	}
}