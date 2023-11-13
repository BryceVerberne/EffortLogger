/**
 * Title:       Defect Logs Class
 * Author:      Bryce Verberne
 * Email:       bverbern@asu.edu
 * Description: 
 */



package effortLoggerV2;

public class DefectLogs {
	int index;
	String projectName;
	String projectType;
	String detail;
	String injected;
	String removed;
	String category;
	
	
	// ******************
	// Special Functions
	// ******************
	
	// Constructor
	public DefectLogs(int index, String projectName, String projectType, String detail, String injected, String removed, String category) {
		this.index = index;
		this.projectName = projectName;
		this.projectType = projectType;
		this.detail = detail;
		this.injected = injected;
		this.removed = removed;
		this.category = category;
	}
	
	
	// ******************
	// Accessor Functions
	// ******************
	
	// This function returns the index of a specific defect log
	public int getIndex() {return index;}
	
	// This function returns the name of the project name pertaining to a defect log
	public String getProjectName() {return projectName;}
	
	public String getProjectType() {return projectType;}
	
	// This function returns a string containing further details regarding the defect -- TODO --
	public String getDetail() {return detail;}
	
	// This function returns the user choice for the "Step when Injected" list view section
	public String getInjected() {return injected;}
	
	// This function returns the user choice for the "Step when Removed" list view section
	public String getRemoved() {return removed;}
	
	// This function returns the category of a specific defect log
	public String getCategory() {return category;}
	
	
	// ******************
	// Mutator Functions
	// ******************
	
	
	
	// ******************
	// Other Functions
	// ******************
	
	
	// This function concatenates all the values of the object into one descriptive string -- TODO --
	public String toString() {return index + ", " + injected + ", " + removed + ", " + category;}
}