/**
 * Title:       Project Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: This class is an integral part of the effortLoggerV2 application, representing a project entity. 
 *              It includes attributes like the title of the project and a list of associated LifeCycle objects. 
 *              The class provides a constructor for initializing the project title and methods to manage life cycles within a project. 
 *              This includes a static method 'fillProjects' for populating a list of Project objects with data, 
 *              and methods for setting and managing life cycles. The toString method is overridden to return the project title.
 */



package effortLoggerV2;

import java.util.ArrayList;

public class Project {
	String title;
	ArrayList<LifeCycle> lifeC;
	// title of project
	public Project(String title) {
		this.title = title;
		// inplace of database in the future
		lifeC = null;		
	}
	
	public static ArrayList<Project> fillProjects(ArrayList<Project> proj, ArrayList<LifeCycle> lc) {
		return dataConnect.fillProjects(proj, lc);
	}
	
	public void setLifeCycles(ArrayList<Project> proj, int index, LifeCycle lc) {
		proj.get(index).lifeC.add(lc);
	}
	
	public void setLifeC() {
		lifeC = new ArrayList<LifeCycle>();
	}
	
	public String toString() {
		return title;
	}
}
