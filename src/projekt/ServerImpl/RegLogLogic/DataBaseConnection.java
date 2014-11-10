package projekt.ServerImpl.RegLogLogic;

import projekt.ServerImpl.Packets.AuthenticationPacket;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class DataBaseConnection extends Connection implements AuthenticationPacket {

	JDBCConnectionLogic jdbcConLogic;
	
	public DataBaseConnection() {
		
		new ObjectSpace(this).register(1, this);
		
		jdbcConLogic = new JDBCConnectionLogic();
	}
	
	public boolean login(String username, String password) {
		
		return jdbcConLogic.login(username, password);
	}

	
	public boolean register(String username, String password) {
		
		return jdbcConLogic.register(username, password);
	}

	public boolean disconnect() {
		
		jdbcConLogic.disconnect();
		return jdbcConLogic.getStatus();
	}
}