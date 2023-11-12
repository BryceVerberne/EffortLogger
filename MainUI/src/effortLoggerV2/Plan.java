/**
 * Title:       Plan Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: As a component of the effortLoggerV2 application, this class represents a plan entity. 
 *              It primarily consists of a title attribute for a plan. The class provides a constructor to initialize 
 *              the plan's title and a static method 'fillPlans' to populate a list of Plan objects using data connection. 
 *              It also overrides the toString method to return the plan's title.
 */



package effortLoggerV2;

import java.util.ArrayList;

public class Plan {
	String title;
	
	public Plan(String title) {
		this.title = title;
	}
	
	public static ArrayList<Plan> fillPlans(ArrayList<Plan> plan) {
		return dataConnect.fillPlans(plan);
	}
	
	public String toString() {
		return title;
	}
}
