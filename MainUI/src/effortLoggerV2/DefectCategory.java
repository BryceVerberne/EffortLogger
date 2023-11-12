/**
 * Title:       Defect Category Class
 * Author:      Dayton Koehler
 * Email:       Dkoehle4@asu.edu
 * Description: Part of the effortLoggerV2 application, this class represents defect categories. 
 *              It primarily manages a defect category's title and interacts with the database 
 *              to populate a list of defect categories. This class serves as a foundational element 
 *              in organizing and categorizing defects within the application.
 */



package effortLoggerV2;

import java.util.ArrayList;

public class DefectCategory {
	String title;
	
	public DefectCategory(String title) {
		this.title = title;
	}
	
	public static ArrayList<DefectCategory> fillDC(ArrayList<DefectCategory> dc) {
		return dataConnect.fillDefectCategories(dc);
	}
	
	public String toString() {
		return title;
	}
}