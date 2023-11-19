/**
 * Title:       Defect Logs Class
 * Author:      Bryce Verberne
 * Email:       bverbern@asu.edu
 * Description: This class is part of the effortLoggerV2 package, designed for tracking defects in software projects. 
 *              It includes attributes like project details, defect description, injection and removal phases, and defect category, 
 *              along with a constructor and accessor methods for these fields. Essential for efficient defect management and quality control.
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
	
	// This function returns the project type of a specific defect log
	public String getProjectType() {return projectType;}
	
	// This function returns a string containing further details regarding the defect
	public String getDetail() {return detail;}
	
	// This function returns the user choice for the "Step when Injected" list view section
	public String getInjected() {return injected;}
	
	// This function returns the user choice for the "Step when Removed" list view section
	public String getRemoved() {return removed;}
	
	// This function returns the category of a specific defect log
	public String getCategory() {return category;}
}