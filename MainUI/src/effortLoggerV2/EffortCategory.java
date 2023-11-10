package effortLoggerV2;

import java.util.ArrayList;

//written by Dayton
public class EffortCategory {
	String title;
	
	//written by Dayton
	public EffortCategory(String title) {
		this.title = title;
		
	}
	
	//written by Dayton
	public static ArrayList<EffortCategory> fillEC(ArrayList<EffortCategory> ec) {
		return dataConnect.fillEffortCategories(ec);
	}
	
	//written by Dayton
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
