package projekt.GUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LobbyFrame{

	public static Stage lobbyStage;

<<<<<<< HEAD
	public void createLobbyFrame() throws IOException{
		
		URL url = getClass().getResource("/projekt/GUI/fxml/LobbyFrame.fxml");

		lobbyStage = new Stage();

		Parent regParent = FXMLLoader.load(url);

		Scene regScene = new Scene(regParent);

		lobbyStage.setTitle("Lobby");
		lobbyStage.setScene(regScene);
		//lobbyStage.setResizable(false);
=======
	public void createlobbyFrame() throws IOException{
		
		URL url = getClass().getResource("/projekt/GUI/fxml/lobbyFrame.fxml");

		lobbyStage = new Stage();

		Parent lobbyParent = FXMLLoader.load(url);

		Scene lobbyScene = new Scene(lobbyParent);

		lobbyStage.setTitle("3D Schach");
		lobbyStage.setScene(lobbyScene);
		lobbyStage.setResizable(true);
>>>>>>> 058314a1a4e69f060fe8c921cc945b53a7de1288
		lobbyStage.show();
	}
}