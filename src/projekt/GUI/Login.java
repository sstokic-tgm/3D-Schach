package projekt.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.spi.InitialContextFactory;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Login extends Application  {

	@FXML
	private Button login; 
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		URL url = getClass().getResource("/projekt/GUI/fxml/Login.fxml");
		System.out.println(url);
		Parent root = FXMLLoader.load(url);
				
		Scene scene = new Scene(root);

		stage.setTitle("FXML Welcome");
		stage.setScene(scene);
		stage.show();
	}
	public Button getLogin(){
		return login;
	}
}
