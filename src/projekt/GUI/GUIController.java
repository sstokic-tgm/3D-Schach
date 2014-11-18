package projekt.GUI;

import java.io.IOException;
import java.net.URL;

import javax.swing.JOptionPane;

import projekt.Client.Test.TestClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GUIController{
	private RegFrame regf;
	private LoginFrame logf;
	
	@FXML private Button bLogin; 
	@FXML private TextField tfUsername;
	@FXML private PasswordField pfPassword;

	/*
	 * these methods handles a specific Actionevent set in the FXML File
	 */
	@FXML public void handleLogin(ActionEvent event) {
	
		if(tfUsername.getText().trim().length() == 0 &&  pfPassword.getText().trim().length() == 0) {
			
			JOptionPane.showMessageDialog(null, "Username and password is empty!");
		}
		else {
			System.out.println(tfUsername.getText()+" just logged on.\n"+tfUsername.getText()+"'s Password = "+pfPassword.getText());
			
			
		}
	}
	@FXML public void handleReg(ActionEvent event) throws IOException{
		// Hier RegFrame aufrufen
		regf = new RegFrame();
//		logf = new LoginFrame();
		
		logf.getLoginInstance().loginStage.close();
		
		//logf.getLoginStage().hide();
		regf.createRegFrame();
		
	}
	@FXML public void handleSignUp(ActionEvent event){
		
		
		
		//new TestClient(tfUsername.getText(), pfPassword.getText());
		
		regf.regStage.close();
		logf.getLoginInstance().loginStage.show();
		
	}
@FXML public void handleCancel(ActionEvent event){
			
		regf.regStage.close();
		logf.getLoginInstance().loginStage.show();
		
	}
}
