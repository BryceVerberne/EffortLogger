package effortLoggerV2;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

//written by Dayton
public class NewAccountScene {
	
	//written by Dayton
	public NewAccountScene() {
		
	}

	//written by Dayton
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
