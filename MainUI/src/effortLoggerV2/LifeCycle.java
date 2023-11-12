/**
 * Title:       LifeCycle Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: As part of the effortLoggerV2 application, this class represents a lifecycle entity. 
 *              It includes attributes like title, EffortCategory, and a static field for highlighting an item. 
 *              The class provides a constructor for initializing the title, a static method 'fillLC' to populate 
 *              a list of LifeCycle objects, and a method 'setOtherDetails' to set additional attributes. 
 *              The toString method is overridden to return the lifecycle title.
 */



package effortLoggerV2;

import java.util.ArrayList;

public class LifeCycle {
	String title;
	EffortCategory EC;
	static int highlightItem;
	
	public LifeCycle(String title) {
		this.title = title;
	}
	
	public static ArrayList<LifeCycle> fillLC(ArrayList<LifeCycle> lc, ArrayList<EffortCategory> ec) {
		return dataConnect.fillLifeCycles(lc, ec, highlightItem);
	}
	
	public void setOtherDetails(EffortCategory effCat, int del) {
		EC = effCat;
		highlightItem = del;
	}
	
	public String toString() {
		return title;
	}
}