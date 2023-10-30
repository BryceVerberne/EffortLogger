package effortLoggerV2;

public class Employee {
	//written by Dayton
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
	
	//written by Dayton
	public String toString() {
		return "" + first_name + " " + last_name + " " + role + " " + id + "\n";
	}
	
}
