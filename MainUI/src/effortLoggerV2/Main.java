/**
 * Title:       Main Application Launcher Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: This class serves as the entry point for the effortLoggerV2 application, extending JavaFX's Application class. 
 *              It overrides the start method to initiate the application by displaying the login scene. 
 *              The main method launches the JavaFX application. Exception handling is incorporated to manage potential 
 *              runtime issues. This class sets the stage for the application, initializing the user interface flow.
 */



package effortLoggerV2;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			LoginScene login = new LoginScene();
			login.displayScene(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			launch(args);
		} catch(Exception jjje) {}
	}
}