package projekt.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LoginController implements Initializable{
	private Login log = new Login();
	
	@FXML
	public void initialize(URL location, ResourceBundle resources) {
		log.getLogin().setOnAction(new EventHandler<ActionEvent>() {
			
			@FXML
			public void handle(ActionEvent event) {
				System.out.println(":D");
			}
		}); 
	}
}
