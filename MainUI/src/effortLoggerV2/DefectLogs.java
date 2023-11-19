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
    // Fields to store defect log properties
    int index;
    String projectName;
    String projectType;
    String detail;
    String injected;
    String removed;
    String category;

    // Constructor to initialize a DefectLog object with specific details
    public DefectLogs(int index, String projectName, String projectType, String detail, String injected, String removed, String category) {
        this.index = index;
        this.projectName = projectName;
        this.projectType = projectType;
        this.detail = detail;
        this.injected = injected;
        this.removed = removed;
        this.category = category;
    }

    
    // *****************
    // Accessor Methods
    // *****************

    // Returns the index of the defect log
    public int getIndex() {
        return index;
    }

    // Returns the project name associated with the defect log
    public String getProjectName() {
        return projectName;
    }

    // Returns the type of project associated with the defect
    public String getProjectType() {
        return projectType;
    }

    // Returns detailed description of the defect
    public String getDetail() {
        return detail;
    }

    // Returns the phase when the defect was injected
    public String getInjected() {
        return injected;
    }

    // Returns the phase when the defect was removed
    public String getRemoved() {
        return removed;
    }

    // Returns the category of the defect
    public String getCategory() {
        return category;
    }

    
    // *****************
    // Mutator Methods
    // *****************

    // Sets the index of the defect log
    public void setIndex(int index) {
        this.index = index;
    }

    // Sets the project name associated with the defect log
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    // Sets the type of project associated with the defect
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    // Sets detailed description of the defect
    public void setDetail(String detail) {
        this.detail = detail;
    }

    // Sets the phase when the defect was injected
    public void setInjected(String injected) {
        this.injected = injected;
    }

    // Sets the phase when the defect was removed
    public void setRemoved(String removed) {
        this.removed = removed;
    }

    // Sets the category of the defect
    public void setCategory(String category) {
        this.category = category;
    }
}
