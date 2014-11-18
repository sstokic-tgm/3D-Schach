package projekt.GUI;

import java.net.URL;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.fxml.*;

public class LoginFrame extends Application  {

public static Stage loginStage;
public static LoginFrame logInstance = null;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage loginStage) throws Exception {
		this.loginStage = loginStage;
		//always starts from the rootdirectory bin
		URL url = getClass().getResource("/projekt/GUI/fxml/LoginFrame.fxml");
		//System.out.println(url);
		Parent root = FXMLLoader.load(url);

		Scene scene = new Scene(root);

		loginStage.setTitle("3D Schach");
		loginStage.setScene(scene);
		loginStage.setResizable(false); // current, cause no layout and its awful if you resize it
		loginStage.show();
		//loginStage.hide();
	}
	private Stage getLoginStage(){
		return loginStage;
	}
	
	public static LoginFrame getLoginInstance(){
		
		if(logInstance == null)
			 return logInstance = new LoginFrame();
		else
			return logInstance;
	}
}