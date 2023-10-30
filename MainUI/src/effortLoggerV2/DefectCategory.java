package effortLoggerV2;

import java.util.ArrayList;

//written by Dayton
public class DefectCategory {
	String title;
	
	//written by Dayton
	public DefectCategory(String title) {
		this.title = title;
	}
	
	//written by Dayton
	public static ArrayList<DefectCategory> fillDC(ArrayList<DefectCategory> dc) {
		return dataConnect.fillDefectCategories(dc);
	}
	
	public String toString() {
		return title;
	}
}
