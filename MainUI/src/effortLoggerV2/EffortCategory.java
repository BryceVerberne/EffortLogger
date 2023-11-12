/**
 * Title:       Effort Category Class
 * Author:      Dayton Koehler
 * Email:       Dkoehle4@asu.edu
 * Description: Integral to the effortLoggerV2 application, this class is responsible for representing effort categories. 
 *              It stores each category's title and aliases with the database to populate an array list of effort categories. 
 *              This class is crucial for categorizing and managing different types of efforts within the application.
 */



package effortLoggerV2;

import java.util.ArrayList;

public class EffortCategory {
	String title;
	
	public EffortCategory(String title) {
		this.title = title;
		
	}
	
	public static ArrayList<EffortCategory> fillEC(ArrayList<EffortCategory> ec) {
		return dataConnect.fillEffortCategories(ec);
	}
	
	public String toString() {
		return "" + title;
	}
	
	public int getEffortCategory() {
		for(int i = 0; i < MainUI.ec.size(); i++) {
			if(title.equals(MainUI.ec.get(i).title)) {
				return i;
			}
		}
		return 0;
	}
}
