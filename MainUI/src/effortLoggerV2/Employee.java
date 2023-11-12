/**
 * Title:       Employee Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: Part of the effortLoggerV2 application, this class represents an employee entity. 
 *              It encapsulates employee details such as first name, last name, role, username, password, and ID. 
 *              The class provides a constructor for initializing these attributes and a toString method 
 *              for representing the employee's information in a string format.
 */



package effortLoggerV2;

public class Employee {

	private String first_name, last_name, role, username, password;
	private int id;
	public Employee(String fName, String lName, String eRole, String eUser, String ePass, int eID) {
		first_name = fName;
		last_name = lName;
		role = eRole;
		username = eUser;
		password = ePass;
		id = eID;
	}
	
	public String toString() {
		return "" + first_name + " " + last_name + " " + role + " " + id + "\n";
	}
	
}