package projekt.ServerImpl;

import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import projekt.GUI.LobbyPanel;
import projekt.ServerImpl.Chat.ChatMessage;
import projekt.ServerImpl.Packets.*;
import projekt.ServerImpl.RegLogLogic.*;

import com.esotericsoftware.kryonet.*;

public class GameServerNetworkListener extends Listener {


	public void connected(Connection con) {

		AuthenticationSessionPacket authSessionPacket = new AuthenticationSessionPacket();

		authSessionPacket.id = con.getID();

		GameServer.getLoginServerInstance().getSessionUsers().put(authSessionPacket.id, authSessionPacket);
		con.sendTCP(authSessionPacket);
	}

	public void disconnected(Connection con) {

		GameServer.getLoginServerInstance().getUsersMap().remove(con.getID());
		GameServer.getLoginServerInstance().getSessionUsers().remove(con.getID());

		RemoveUserPacket packet = new RemoveUserPacket();
		packet.id = con.getID();

		GameServer.getLoginServerInstance().getServer().sendToAllExceptTCP(con.getID(), packet);
	}

	public void received(Connection con, Object obj) {

		if(obj instanceof AuthenticationSessionPacket) {

			AuthenticationSessionPacket authSessionPacket = (AuthenticationSessionPacket)obj;

			if(GameServer.getLoginServerInstance().getSessionUsers().containsKey(authSessionPacket.id)) {


				AddUserPacket packet = new AddUserPacket();
				packet.id = authSessionPacket.id;
				packet.username = authSessionPacket.username;
				GameServer.getLoginServerInstance().getServer().sendToAllTCP(packet);

				for(User u : GameServer.getLoginServerInstance().getUsersMap().values()) {

					AddUserPacket packet2 = new AddUserPacket();
					packet2.id = u.con.getID();
					packet2.username = u.username;
					con.sendTCP(packet2);
				}

				User user = new User();
				user.username = authSessionPacket.username;
				user.con = con;
				GameServer.getLoginServerInstance().getUsersMap().put(con.getID(), user);


			}
			
		}else if(obj instanceof ChatMessage) {
			
			ChatMessage chatMessage = (ChatMessage)obj;
			GameServer.getLoginServerInstance().getServer().sendToAllTCP(chatMessage);
		}
	}
}