/**
 * Title:       Scene Controller Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: This class, a part of the effortLoggerV2 application, serves as a controller to manage scene transitions. 
 *              It provides methods to switch between different scenes, including the main console, login, and new account scenes. 
 *              The class utilizes JavaFX's event handling and stage manipulation to change the user interface dynamically 
 *              based on user interactions. It plays a crucial role in navigating through the application.
 */



package effortLoggerV2;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneController {
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
	

	public void switchToLogin(ActionEvent event){
		try {
			System.out.println("login");
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			LoginScene login = new LoginScene();
			login.displayScene(stage);
		} catch(Exception e) {}
	}
	
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