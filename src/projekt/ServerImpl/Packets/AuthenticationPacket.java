package projekt.ServerImpl.Packets;

import com.esotericsoftware.kryonet.Connection;

public interface AuthenticationPacket {

	public boolean login(String username, String password);
	public boolean register(String username, String password);
	public boolean disconnect();
}
