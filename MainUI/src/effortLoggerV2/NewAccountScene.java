/**
 * Title:       New Account Scene Class
 * Author:      Dayton Koehler
 * Email:       Dkoehle4@asu.edu
 * Description: Part of the effortLoggerV2 JavaFX application, this class manages the new account scene. 
 *              It facilitates the transition to the account creation interface by loading and displaying the scene from an FXML file. 
 *              Exception handling is included for robust operation during the scene transition process.
 */



package effortLoggerV2;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class NewAccountScene {
	
	public NewAccountScene() {
		
	}
	public void displayScene(Stage stage) {
		try {
			stage.close();
			stage = FXMLLoader.load(getClass().getResource("scenes/NewAccountScene.fxml"));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}