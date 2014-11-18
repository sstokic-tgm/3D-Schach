package projekt.GUI;

import java.net.URL;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.fxml.*;

public class LoginFrame extends Application  {

	public static Stage loginStage;
	public static LoginFrame loginInstance = null;

	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage loginStage) throws Exception {

		this.loginStage = loginStage;

		URL url = getClass().getResource("/projekt/GUI/fxml/LoginFrame.fxml");
		Parent root = FXMLLoader.load(url);

		Scene scene = new Scene(root);

		loginStage.setTitle("3D Schach");
		loginStage.setScene(scene);
		loginStage.setResizable(false);
		loginStage.show();

	}

	public static LoginFrame getLoginInstance(){

		if(loginInstance == null)
			loginInstance = new LoginFrame();
		
		return loginInstance;
	}
}