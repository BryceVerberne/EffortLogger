/**
 * Title:       New Account Scene Controller
 * Author(s):   Bryce Verberne, Dayton Koehler  
 * Email(s):    bverbern@asu.edu, Dkoehle4@asu.edu
 * Description: This class, part of the effortLoggerV2 JavaFX application, manages the new account creation scene.
 *              It handles user input validation, ensuring compliance with criteria for usernames, passwords,
 *              and personal information. The class also checks username uniqueness, provides user feedback,
 *              and transitions users to the login scene after successful account creation.
 */



package effortLoggerV2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NewAccountSceneController extends SceneController{
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private TextField fNameField;
	
	@FXML
	private TextField lNameField;
	
	@FXML
	private TextField roleField;
	
	// This function determines if the user's input meets account requirements
		public static boolean inputVerification(String user, String pass, String first, String last, String role) {
			/*
			 * Requirements:
			 * Username
			 *  - Must contain 5-16 characters
			 *  - Contains no whitespace
			 *  - Can contain alphabetic characters (a-zA-Z), numbers (0-9), hyphens (-), and underscores (_)
			 * 
			 * Password
			 *  - Must be 8-16 characters
			 *  - Contains no whitespace
			 *  - At least 1 upper-case letter (A-Z)
			 *  - At least 1 lower-case letter (a-z)
			 *  - At least 1 number (0-9)
			 *  - At least 1 special character (!,@,#,$,%,^,&,*,(,),.,?,\",:,{,},|,<,>)
			 *  
			 * First & Last Name
			 *  - Must be between 3 and 12 characters in length per word
			 *  - Can contain alphabetic characters (a-zA-Z)
			 *  - Can include hyphens (-) and single spaces ( ) as separators, but not at the beginning or end of the name
			 *  - Consecutive separators are not allowed (e.g., no "--" or "  ")
			 */

			// Regular Expressions 
			String usernameRequirements = "^[a-zA-Z0-9_-]{5,16}$";
			String nameRequirements = "^[a-zA-Z]{3,12}(?:[\\s-][a-zA-Z]{3,12})*$";
			String passwordRequirements = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])(?!.*\\s).{8,16}$";
			
			// Check if username already exists in database
			if (dataConnect.findUser(user)) {
				System.out.println("Username already exists. Please try a different username.");
				return false;
			}

			
			// Determine if first name meets specifications
			if (!first.matches(nameRequirements)) {
				System.out.println("Incorrect First Name. Please try again.");
				return false;
			}
			
			if (!last.matches(nameRequirements)) {
				System.out.println("Incorrect Last Name. Please try again.");
				return false;
			}
			
			
			// Determine if username meets specifications
			if (!user.matches(usernameRequirements)) {
				System.out.println("Incorrect Username. Please try again.");
				return false;
			}
			
			// Determine if password meets specifications
			if (!pass.matches(passwordRequirements)) {
				System.out.println("Incorrect Password. Please try again.");
				return false;
			}
			
			
			return true;
		}
	
		public void makeAccount(ActionEvent event) {
			String username, password, firstName, lastName, userRole;
			boolean valid;
			
			// Retrieve user input
			username = usernameField.getText();
			password = passwordField.getText();
			firstName = fNameField.getText();
			lastName = lNameField.getText();
			userRole = roleField.getText();
			
			// Determine if user input is valid
			valid = inputVerification(username, password, firstName, lastName, userRole);
			
			// If valid, execute the following
			if (valid) {
				// Send valid user input to database
				dataConnect.newUserPass(username, password, firstName, lastName, userRole);
				
				// Notify user of account creation
				System.out.println("Created New Account");
				switchToLogin(event);
			}
			
			event.consume();
		}
}