package projekt.GUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LobbyFrame{

	public static Stage lobbyStage;

	public void createlobbyFrame() throws IOException{
		
		URL url = getClass().getResource("/projekt/GUI/fxml/lobbyFrame.fxml");

		lobbyStage = new Stage();

		Parent lobbyParent = FXMLLoader.load(url);

		Scene lobbyScene = new Scene(lobbyParent);

		lobbyStage.setTitle("3D Schach");
		lobbyStage.setScene(lobbyScene);
		lobbyStage.setResizable(true);
		lobbyStage.show();
	}
}