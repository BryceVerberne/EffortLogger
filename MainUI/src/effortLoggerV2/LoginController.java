/**
 * Title:       Login Controller
 * Authors:     Dayton Koehler, Hardeek Das
 * Emails:      Dkoehle4@asu.edu, hdas2@asu.edu
 * Description: This class, part of the effortLoggerV2 application, extends SceneController and manages the login functionality. 
 *              It handles the user interface for login, including fields for username and password, login, logout, 
 *              and account creation buttons, and an error message label. The class includes methods for handling login attempts, 
 *              locking an account after multiple failed attempts, and transitioning to account creation. 
 *              These functionalities are implemented with JavaFX components and event handling.
 */



package effortLoggerV2;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends SceneController{

	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button logoutButton;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Button newAccountButton;
	
	@FXML
	private Label errorText;
	
	int numFails = 0;
	
	public void login(ActionEvent event){
		String user, pass;
		try {
			user = usernameField.getText();
			pass = passwordField.getText();
			// if findUserPass returns true, then login information worked
			if(dataConnect.findUserPass(user, pass)) {
				System.out.println("Success");
				switchToConsole(event, user, pass);
			} else {
				System.out.println("Not Good");
				numFails++;	// count the number of times user fails to login
				
				if(numFails >= 3) {
					lockAccount();
				}
			}
		} catch(Exception e) {}
		event.consume();

	}
	
	// method to prevent anymore login attempts
	public void lockAccount() {
		loginButton.setDisable(true);
		newAccountButton.setDisable(true);
		errorText.setText("Exceeded maximum login attempts. Please wait 5 minutes.");
	}
	
	public void makeNewAccount(ActionEvent event) {
		try {
			switchToNewAccount(event);
		} catch(Exception e) {}
		event.consume();

	}
}
