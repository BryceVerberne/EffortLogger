package effortLoggerV2;

import java.util.ArrayList;

//written by Dayton
public class Project {
	String title;
	ArrayList<LifeCycle> lifeC;
	// title of project
	public Project(String title) {
		this.title = title;
		// inplace of database in the future
		lifeC = null;		
	}
	
	//written by Dayton
	public static ArrayList<Project> fillProjects(ArrayList<Project> proj, ArrayList<LifeCycle> lc) {
		return dataConnect.fillProjects(proj, lc);
	}
	
	//written by Dayton
	public void setLifeCycles(ArrayList<Project> proj, int index, LifeCycle lc) {
		proj.get(index).lifeC.add(lc);
	}
	
	//written by Dayton
	public void setLifeC() {
		lifeC = new ArrayList<LifeCycle>();
	}
	
	public String toString() {
		return title;
	}
}
