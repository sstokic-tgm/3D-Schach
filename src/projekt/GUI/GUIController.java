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

	@FXML private Button bLogin, bSignUp, bCancel; 
	@FXML private TextField tfLoginUsername, tfRegisterUsername;
	@FXML private PasswordField pfLoginPassword, pfRegisterPassword, pfRegisterPasswordRetype;


	@FXML public void handleLogin(ActionEvent event) {

		if(tfLoginUsername.getText().trim().length() == 0 &&  pfLoginPassword.getText().trim().length() == 0) {

			JOptionPane.showMessageDialog(null, "Username and password is empty!");

		}else {

			new TestClient().loginTestClient(tfLoginUsername.getText(), pfLoginPassword.getText());
		}
	}

	@FXML public void handleReg(ActionEvent event) throws IOException{

		regf = new RegFrame();

		logf.getLoginInstance().loginStage.close();
		regf.createRegFrame();

	}

	@FXML public void handleSignUp(ActionEvent event){

		if((tfRegisterUsername.getText().trim().length() == 0 &&  pfRegisterPassword.getText().trim().length() == 0) && (pfRegisterPassword.getText().equals(pfRegisterPasswordRetype.getText()))) {

			JOptionPane.showMessageDialog(null, "Invalid input!");

		}else {

			new TestClient().registerTestClient(tfRegisterUsername.getText(), pfRegisterPassword.getText());

			regf.regStage.close();
			logf.getLoginInstance().loginStage.show();
		}
	}

	@FXML public void handleCancel(ActionEvent event){

		regf.regStage.close();
		logf.getLoginInstance().loginStage.show();

	}
}
