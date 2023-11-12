/**
 * Title:       Deliverable Class
 * Author:      Dayton Koehler
 * Email:       Dkoehle4@asu.edu
 * Description: Part of the effortLoggerV2 application, this class represents deliverables. 
 *              It manages deliverable titles and interfaces with the database to populate a list of deliverables. 
 *              Essential for tracking and organizing deliverables, the class aids in maintaining 
 *              structured data for project management within the application.
 */




package effortLoggerV2;

import java.util.ArrayList;

public class Deliverable {
	String title;
	
	public Deliverable(String title) {
		this.title = title;
	}
	
	public static ArrayList<Deliverable> fillDel(ArrayList<Deliverable> del) {
		return dataConnect.fillDeliverables(del);
	}
	
	public String toString() {
		return title;
	}
}