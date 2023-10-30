package effortLoggerV2;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneController {
	// written by Dayton
	public void switchToConsole(ActionEvent event, String user, String pass) {
		try {
			System.out.println("Main UI");
			Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
			MainUI console = new MainUI();
			console.user = dataConnect.getEmployee(user, pass);
			System.out.println(console.user.toString());
			console.displayScene(stage);
		} catch(Exception e) {}
	}
	

	// written by Dayton
	public void switchToLogin(ActionEvent event){
		try {
			System.out.println("login");
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			LoginScene login = new LoginScene();
			login.displayScene(stage);
		} catch(Exception e) {}
	}
	
	// written by Dayton
	public void switchToNewAccount(ActionEvent event) {
		try {
			System.out.println("new account");
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			NewAccountScene nAScene = new NewAccountScene();
			nAScene.displayScene(stage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
