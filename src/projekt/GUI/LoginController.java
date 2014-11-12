package projekt.GUI;

import javax.swing.JOptionPane;

import projekt.Client.Test.TestClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController{
	
	@FXML private Button bLogin; 
	@FXML private TextField tfUsername;
	@FXML private PasswordField pfPassword;

	@FXML public void handleLogin(ActionEvent event) {
		
		
		
		if(tfUsername.getText().trim().length() == 0 &&  pfPassword.getText().trim().length() == 0) {
			
			JOptionPane.showMessageDialog(null, "Username and password is empty!");
		}
		else {
			
			new TestClient(tfUsername.getText(), pfPassword.getText());
		}
	}
}
