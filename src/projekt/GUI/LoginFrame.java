package projekt.GUI;

import java.net.URL;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.fxml.*;

public class LoginFrame extends Application  {


	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {

		URL url = getClass().getResource("/projekt/GUI/fxml/LoginFrame.fxml");
		//System.out.println(url);
		Parent root = FXMLLoader.load(url);

		Scene scene = new Scene(root);

		stage.setTitle("3D Schach");
		stage.setScene(scene);
		stage.setResizable(false); // current, cause no layout and its awful if you resize it
		stage.show();
	}

}