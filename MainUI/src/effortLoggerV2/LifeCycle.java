package effortLoggerV2;

import java.util.ArrayList;

//written by Dayton
public class LifeCycle {
	String title;
	EffortCategory EC;
	static int highlightItem;
	
	//written by Dayton
	public LifeCycle(String title) {
		this.title = title;
	}
	
	//written by Dayton
	public static ArrayList<LifeCycle> fillLC(ArrayList<LifeCycle> lc, ArrayList<EffortCategory> ec) {
		return dataConnect.fillLifeCycles(lc, ec, highlightItem);
	}
	
	//written by Dayton
	public void setOtherDetails(EffortCategory effCat, int del) {
		EC = effCat;
		highlightItem = del;
	}
	
	public String toString() {
		return title;
	}
}
