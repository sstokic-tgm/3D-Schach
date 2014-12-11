package projekt.GUI;

import java.io.IOException;
import javax.swing.JOptionPane;

import projekt.client.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

public class GUIController{

	private RegFrame regf;
	private LoginFrame logf;
	private LobbyFrame lobbyf;

	@FXML private Button bLogin, bSignUp, bCancel; 
	@FXML private TextField tfLoginUsername, tfRegisterUsername;
	@FXML private PasswordField pfLoginPassword, pfRegisterPassword, pfRegisterPasswordRetype;


	@FXML public void handleLogin(ActionEvent event) throws IOException {

		if(tfLoginUsername.getText().trim().length() == 0 &&  pfLoginPassword.getText().trim().length() == 0) {

			JOptionPane.showMessageDialog(null, "Username and password is empty!");

		}else {

			MainClient mainClient = new MainClient();
			mainClient.mainClientLoginUser(tfLoginUsername.getText(), pfLoginPassword.getText());


			logf.getLoginInstance().loginStage.close();
		}
	}

	@FXML public void handleReg(ActionEvent event) throws IOException{

		regf = new RegFrame();

		logf.getLoginInstance().loginStage.close();
		regf.createRegFrame();

	}

	@FXML public void handleSignUp(ActionEvent event){

		if((tfRegisterUsername.getText().trim().length() == 0 &&  pfRegisterPassword.getText().trim().length() == 0) || (!pfRegisterPassword.getText().equals(pfRegisterPasswordRetype.getText()))) {

			JOptionPane.showMessageDialog(null, "Invalid input!");

		}else {

			MainClient mainClient = new MainClient();
			mainClient.mainClientRegisterUser(tfRegisterUsername.getText(), pfRegisterPassword.getText());

			//regf.regStage.close();
			//logf.getLoginInstance().loginStage.show();
		}
	}

	@FXML public void handleCancel(ActionEvent event){

		regf.regStage.close();
		logf.getLoginInstance().loginStage.show();
	}
}
