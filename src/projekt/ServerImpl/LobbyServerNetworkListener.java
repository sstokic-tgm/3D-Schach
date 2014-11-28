package projekt.ServerImpl;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import projekt.ServerImpl.Packets.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class LobbyServerNetworkListener extends Listener {

	public void connected(Connection con) {

		
	}

	public void disconnected(Connection con) {
		
		LobbyServer.getLoginServerInstance().getUsersMap().remove(con.getID());
		
		RemoveUserPacket packet = new RemoveUserPacket();
		packet.id = con.getID();
		
		LobbyServer.getLoginServerInstance().getServer().sendToAllExceptTCP(con.getID(), packet);
	}

	public void received(Connection con, Object obj) {
		
		if(obj instanceof AuthenticationSessionPacket) {
			
			AuthenticationSessionPacket authSessionPacket = (AuthenticationSessionPacket)obj;
			
			//if(LoginServer.getLoginServerInstance().getSessionUsers().get(authSessionPacket) != null) {
				
				
				AddUserPacket packet = new AddUserPacket();
				packet.id = authSessionPacket.id;
				packet.username = authSessionPacket.username;
				LobbyServer.getLoginServerInstance().getServer().sendToAllExceptTCP(con.getID(), packet);
				
				for(User u : LobbyServer.getLoginServerInstance().getUsersMap().values()) {
					
					AddUserPacket packet2 = new AddUserPacket();
					packet2.id = u.con.getID();
					packet2.username = u.username;
					con.sendTCP(packet2);
				}
				
				User user = new User();
				
				user.username = authSessionPacket.username;
				user.con = con;
				LobbyServer.getLoginServerInstance().getUsersMap().put(con.getID(), user);
				
			//}
		}
	}
}