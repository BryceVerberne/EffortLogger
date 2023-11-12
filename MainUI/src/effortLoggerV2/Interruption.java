/**
 * Title:       Interruption Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: This class is part of the effortLoggerV2 application and represents an interruption entity. 
 *              It primarily holds a title for an interruption. The class offers a constructor for initializing 
 *              the title of an interruption and a static method 'fillInter' for populating a list of interruptions 
 *              using data from a data connection. It also overrides the toString method to return the title of the interruption.
 */



package effortLoggerV2;

import java.util.ArrayList;

public class Interruption {
	String title;
	
	public Interruption(String title) {
		this.title = title;
	}
	
	public static ArrayList<Interruption> fillInter(ArrayList<Interruption> inter) {
		return dataConnect.fillInterruption(inter);
	}
	
	public String toString() {
		return title;
	}
}