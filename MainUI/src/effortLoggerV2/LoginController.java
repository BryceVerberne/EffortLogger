package effortLoggerV2;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

//written by Dayton
public class LoginController extends SceneController{

	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button logoutButton;
	
	@FXML
	private Button newAccountButton;
	
	//written by Dayton
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
			}
		} catch(Exception e) {}
		event.consume();

	}	
	
	//written by Dayton
	public void makeNewAccount(ActionEvent event) {
		try {
			switchToNewAccount(event);
		} catch(Exception e) {}
		event.consume();

	}
}
