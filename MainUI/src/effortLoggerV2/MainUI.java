/**
 * Title:       Main User Interface
 * Authors:     Dayton Koehler, Bryce Verberne
 * Emails:      Dkoehle4@asu.edu, bverbern@asu.edu
 * Description: Part of the effortLoggerV2 application, this class manages the main user interface. 
 *              It maintains the application state, including the current user, lists of activities, logs, and various project entities. 
 *              The class is responsible for loading and displaying the main UI scene using JavaFX's FXMLLoader. 
 *              It also initializes and holds references to various data collections like projects, life cycles, 
 *              effort categories, deliverables, plans, interruptions, and defect categories, 
 *              which are essential for the application's operation.
 */



package effortLoggerV2;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class MainUI {
	
	Employee user;
	public static ArrayList<Activity> activityList;
	// hold the effort logs that have been created by a user
	public static ArrayList<EffortLogs> effLogs;
	public static ArrayList<DefectLogs> defectLogs;
	public static HashMap<Project, Integer> projectIndexes;
	
	// used to hold SQL database information
	public static ArrayList<Project> projects;
	public static ArrayList<LifeCycle> lifeCycles;
	public static ArrayList<EffortCategory> ec;
	public static ArrayList<Deliverable> deliv;
	public static ArrayList<Plan> plan;
	public static ArrayList<Interruption> interrupt;
	public static ArrayList<DefectCategory> dc;
	
	
	ConsoleController console = new ConsoleController();
	
	// used to show the array list of definitions in the combo boxes of EffortLogger Console
		
	public MainUI() {
		user = null;
	}
	
	public void displayScene(Stage stage) {
		try {
				stage.close();
				stage = FXMLLoader.load(getClass().getResource("scenes/MainUI.fxml"));
				stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}