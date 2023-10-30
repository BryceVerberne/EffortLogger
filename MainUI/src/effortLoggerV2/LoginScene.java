package effortLoggerV2;


import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;

//written by Dayton
public class LoginScene {
	
	//written by Dayton
	public LoginScene() {
		
	}
	
	//written by Dayton
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
