package effortLoggerV2;

import java.util.ArrayList;

//written by Dayton
public class Plan {
	String title;
	
	//written by Dayton
	public Plan(String title) {
		this.title = title;
	}
	
	//written by Dayton
	public static ArrayList<Plan> fillPlans(ArrayList<Plan> plan) {
		return dataConnect.fillPlans(plan);
	}
	
	public String toString() {
		return title;
	}
}
