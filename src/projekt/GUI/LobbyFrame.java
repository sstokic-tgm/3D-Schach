package projekt.GUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LobbyFrame{

	public static Stage lobbyStage;

	public void createLobbyFrame() throws IOException{
		
		URL url = getClass().getResource("/projekt/GUI/fxml/LobbyFrame.fxml");

		lobbyStage = new Stage();

		Parent regParent = FXMLLoader.load(url);

		Scene regScene = new Scene(regParent);

		lobbyStage.setTitle("Lobby");
		lobbyStage.setScene(regScene);
		//lobbyStage.setResizable(false);
		lobbyStage.show();
	}
}