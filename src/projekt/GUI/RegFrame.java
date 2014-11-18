package projekt.GUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegFrame{

	public static Stage regStage;
	private Parent regParent;
	private Scene regScene;

	public void createRegFrame() throws IOException{
		//always starts from the rootdirectory bin
		URL url = getClass().getResource("/projekt/GUI/fxml/RegFrame.fxml");

		regStage = new Stage();

		regParent = FXMLLoader.load(url);

		regScene = new Scene(regParent);

		regStage.setTitle("3D Schach");
		regStage.setScene(regScene);
		regStage.setResizable(false); // current, cause no layout and its awful if you resize it
		regStage.show();
	}
	
}
