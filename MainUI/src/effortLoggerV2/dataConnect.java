package effortLoggerV2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

// connection to the database
public class dataConnect {
	
	private static Connection connection;
	
	public dataConnect() {
		connection = connect();
	}
	
	// written by Dayton
	public static Connection connect() {
		try {
			// uses SQLite to use database on local file of effortLoggerV2
			Class.forName("org.sqlite.JDBC");
			// gains connection to the database file
			Connection connect = DriverManager.getConnection("jdbc:sqlite:db/effort_logger_v2.db");
			return connect;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Searches for the username of employees in database
		public static boolean findUser(String enterUser) {
			try {
				// create a connection to database
				connection = connect();
				
				// create a standard SQL search for employee with specific username and password
				String query = "SELECT * FROM employee WHERE username = ?";
				
				// create a prepared statement so we can avoid SQL injection
				PreparedStatement statement = connection.prepareStatement(query);
				
				// insert username and password at the question mark instead of concatenation
				statement.setString(1, enterUser);
				
		        // we execute a SQL query
		        ResultSet rs = statement.executeQuery();
		        
		        // if there is another table entry
		        // then a employee has the entered username and password
		        if(rs.next()) {
		        	rs.close();
		 	        statement.close();
		        	return true;
		        }
		        rs.close();
		        statement.close();
		        	
			} catch(Exception e) {}
			
			return false;
		}
	
	// written by Dayton
	// searches for the username and password of employees in database
	public static boolean findUserPass(String enterUser, String enterPass) {
		try {
			// create a connection to database
			connection = connect();
			// create a standard SQL search for employee with specific username and password
			String query = "SELECT * FROM employee WHERE username = ? AND password = ?";
			// create a prepared statement so we can avoid SQL injection
			PreparedStatement statement = connection.prepareStatement(query);
			// insert username and password at the question mark instead of concatenation
			statement.setString(1, enterUser);
			statement.setString(2, enterPass);
	        // we execute a SQL query
	        ResultSet rs = statement.executeQuery();
	        // if there is another table entry
	        // then a employee has the entered username and password
	        if(rs.next()) {
	        	rs.close();
	 	        statement.close();
	        	return true;
	        }
	        rs.close();
	        statement.close();
	        	
		} catch(Exception e) {}
		
		return false;
	}
	
	// written by Dayton
	public static boolean newUserPass(String enterUser, String enterPass, String fName,
			String lName, String role) {
		try {
			// create a connection to database
			connection = connect();
			// create a standard SQL insert for employee with specific username and password
			String query = "INSERT into employee (first_name, last_name, employee_role"
					+ ", username, password) values (?, ?, ?, ?, ?)";
			// create a prepared statement so we can avoid SQL injection
			PreparedStatement statement = connection.prepareStatement(query);
			// insert respective entries at the question mark instead of using concatenation
			statement.setString(1, fName);
			statement.setString(2, lName);
			statement.setString(3, role);
			statement.setString(4, enterUser);
			statement.setString(5, enterPass);
	        // we execute a SQL update
	        statement.executeUpdate();
	        // then a employee has the entered username and password
	        
	        statement.close();
	        connection.close();
	        return true;
	        	
		} catch(Exception e) {}
		
		return false;
	}
	
	//written by Dayton
	public static Employee getEmployee(String user, String pass) {
		try {
			// create a connection to database
			connection = connect();
			// create a standard SQL search for employee with specific username and password
			String query = "SELECT * FROM employee WHERE username = ? AND password = ?";
			// create a prepared statement so we can avoid SQL injection
			PreparedStatement statement = connection.prepareStatement(query);
			// insert username and password at the question mark instead of concatenation
			statement.setString(1, user);
			statement.setString(2, pass);
	        // we execute a SQL query
	        ResultSet rs = statement.executeQuery();
	        // if there is another table entry
	        // then a employee has the entered username and password
	        if(rs.next()) {
	        	// get the employee information to make new employee object for main
	        	// effort logger interface 
	        	String enterUser, enterPass, fName, lName, role;
	        	int id = rs.getInt(1);
	        	fName = rs.getString("first_name");
	        	lName = rs.getString("last_name");
	        	enterUser = rs.getString("username");
	        	enterPass = rs.getString("password");
	        	role = rs.getString("employee_role");
	        	
	        	rs.close();
	 	        statement.close();
	        	
	        	return new Employee(fName, lName, role, enterUser, enterPass, id);
	        	
	        }
	        rs.close();
	        statement.close();
	        	
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return null;
		
	}
	
	// written by Dayton
	public static ArrayList<Project> fillProjects(ArrayList<Project> proj, ArrayList<LifeCycle> lc) {
		try {
			ResultSet rs = getRS("project", "LifeCycle is not NULL");
			String title = "";
			int index = -1;
			while(rs.next()) {
				// when the title does not equal the project title, add 1 to index and change title
				// then add a new project to the array list and and initialize the lifecycle array list
				// for the new project object
				if(!title.equals(rs.getString("titleProj"))) {
					index++;
					title = rs.getString("titleProj");
					proj.add(new Project(title));
					proj.get(index).setLifeC();
				}
				// then add the new life cycle index to the project's life cycle array index
				// have to minus 1 for the life cycle array because in database the index starts at 1
				proj.get(index).setLifeCycles(proj, index, lc.get(rs.getInt("LifeCycle") - 1));
			}
			rs.close();
			return proj;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	// Written by Dayton
	public static ArrayList<LifeCycle> fillLifeCycles(ArrayList<LifeCycle> lc,
			ArrayList<EffortCategory> ec, int keyItem) {
		try {
			ResultSet rs = getRS("lifecycle", "titleLC is not NULL");
			String title;
			int effCat, del;
			while(rs.next()) {
				// the Life cycel has a title, integer of Effort Category
				// and integer of default highlighted item of effort category
				title = rs.getString("titleLC");
				effCat = rs.getInt("EffortCategory");
				del = rs.getInt("Deliverable");
				// we have to set the other details like the effort category
				// and the default highlighted item for the new life cycle object
				// then just add it to array list
				LifeCycle lifeCycle = new LifeCycle(title);
				lifeCycle.setOtherDetails(ec.get(effCat-1), del);
				lc.add(lifeCycle);
				
			}
			rs.close();
			return lc;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// written by Dayton
	public static ArrayList<EffortCategory> fillEffortCategories(ArrayList<EffortCategory> ec) {
		try {
			ResultSet rs = getRS("effortcategory", "titleEC is not NULL");
			String title;
			// add the plans to the arraylist
			while(rs.next()) {
				title = rs.getString("titleEC");
				ec.add(new EffortCategory(title));

			}
			rs.close();
			return ec;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// written by Dayton
	public static ArrayList<Plan> fillPlans(ArrayList<Plan> plan) {
		try {
			ResultSet rs = getRS("plan", "titlePlan is not NULL");
			String title;
			// add the plans to the arraylist
			while(rs.next()) {
				title = rs.getString("titlePlan");
				plan.add(new Plan(title));
			}
			rs.close();
			return plan;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// written by Dayton
	public static ArrayList<Deliverable> fillDeliverables(ArrayList<Deliverable> del) {
		try {
			ResultSet rs = getRS("deliverable", "titleDel is not NULL");
			String title;
			// add the deliverables to the arraylist
			while(rs.next()) {
				title = rs.getString("titleDel");
				del.add(new Deliverable(title));
			}
			rs.close();
			return del;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// written by Dayton
	public static ArrayList<Interruption> fillInterruption(ArrayList<Interruption> inter) {
		try {
			ResultSet rs = getRS("interruption", "titleInter is not NULL");
			String title;
			// add the plans to the arraylist
			while(rs.next()) {
				title = rs.getString("titleInter");
				inter.add(new Interruption(title));
			}
			rs.close();
			return inter;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// written by Dayton
	public static ArrayList<DefectCategory> fillDefectCategories(ArrayList<DefectCategory> dc) {
		try {
			ResultSet rs = getRS("defectcategory", "titleDC is not NULL");
			String title;
			// add the plans to the arraylist
			while(rs.next()) {
				title = rs.getString("titleDC");
				dc.add(new DefectCategory(title));
			}
			rs.close();
			return dc;
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return null;
		
	}
	
	// written by Dayton
	public static ResultSet getRS(String location, String want) {
		connection = connect();
		Statement statement;
		ResultSet rs;
		// create a query for the specified location and the wanted parameters for query
		String query = "SELECT * FROM " + location + " WHERE " + want;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			// return the entries at location table with wanted parameters
			return rs;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
