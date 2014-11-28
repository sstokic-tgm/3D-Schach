package projekt.ServerImpl;

import javax.swing.JOptionPane;

import projekt.ServerImpl.Packets.*;
import projekt.ServerImpl.RegLogLogic.*;

import com.esotericsoftware.kryonet.*;

public class LoginServerNetworkListener extends Listener {

	public void connected(Connection con) {

		String username = DataBaseConnection.getDatabaseConnectionInstance().getUsername();
		AuthenticationSessionPacket authSessionPacket = new AuthenticationSessionPacket();
		
		authSessionPacket.id = con.getID();
		//authSessionPacket.username = username;
		
		LoginServer.getLoginServerInstance().getSessionUsers().put(authSessionPacket.id, authSessionPacket);
		con.sendTCP(authSessionPacket);
	}

	public void disconnected(Connection con) {
		
	}

	public void received(Connection con, Object obj) {
		
	}
}