package projekt.GUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegFrame{

	public static Stage regStage;

	public void createRegFrame() throws IOException{
		
		URL url = getClass().getResource("/projekt/GUI/fxml/RegFrame.fxml");

		regStage = new Stage();

		Parent regParent = FXMLLoader.load(url);

		Scene regScene = new Scene(regParent);

		regStage.setTitle("3D Schach");
		regStage.setScene(regScene);
		regStage.setResizable(false);
		regStage.show();
	}
}