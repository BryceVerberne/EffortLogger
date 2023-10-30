package effortLoggerV2;

import java.util.ArrayList;

//written by Dayton
public class Interruption {
	String title;
	
	//written by Dayton
	public Interruption(String title) {
		this.title = title;
	}
	
	//written by Dayton
	public static ArrayList<Interruption> fillInter(ArrayList<Interruption> inter) {
		return dataConnect.fillInterruption(inter);
	}
	
	public String toString() {
		return title;
	}
}
