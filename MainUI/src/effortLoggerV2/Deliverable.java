package effortLoggerV2;

import java.util.ArrayList;

//written by Dayton
public class Deliverable {
	String title;
	
	//written by Dayton
	public Deliverable(String title) {
		this.title = title;
	}
	
	//written by Dayton
	public static ArrayList<Deliverable> fillDel(ArrayList<Deliverable> del) {
		return dataConnect.fillDeliverables(del);
	}
	
	public String toString() {
		return title;
	}
}
