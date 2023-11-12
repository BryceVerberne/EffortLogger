/**
 * Title:       Login Scene Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: Part of the effortLoggerV2 application, this class is responsible for managing the login scene. 
 *              It includes methods to display the login scene using JavaFX's Stage and FXMLLoader. 
 *              The class ensures that the current stage is closed before loading and displaying the new login scene, 
 *              handling any exceptions that may occur during this process.
 */



package effortLoggerV2;

import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;

public class LoginScene {
	
	public LoginScene() {
		
	}
	
	public void displayScene(Stage stage) {
		try {
			if(stage.isShowing()) {
				stage.close();
			}
				stage.close();
				stage = FXMLLoader.load(getClass().getResource("scenes/LoginScene.fxml"));
				stage.show();
		} catch(Exception e) {}
	}
}