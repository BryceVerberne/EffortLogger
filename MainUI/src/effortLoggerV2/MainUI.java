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
		
	
	// written by Dayton
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
	
