/**
 * Title:       Effort Logs Class
 * Author:      Dayton Koehler
 * Email:       Dkoehle4@asu.edu
 * Description: In the effortLoggerV2 application, this class is responsible for logging various efforts. 
 *              It tracks project details, life cycle steps, effort categories, and deliverables, 
 *              along with time metrics. Key features include managing activity durations and associated keywords, 
 *              crucial for effective effort management in projects.
 */



package effortLoggerV2;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;

public class EffortLogs {
	
	Activity act;
	Project proj;
	LifeCycle lCycleStep;
	EffortCategory effCat;
	String del, start, end, delta, date;
	int index;
	ArrayList<String> keyWords;
	
	public EffortLogs(Activity activity, Project project, LifeCycle lc, 
						EffortCategory ec, String deliver, int index) 
	{
		this.index = index;
		act = activity;
		proj = project;
		lCycleStep = lc;
		effCat = ec;
		del = deliver;
		start = act.getStartTime();
		end = act.getEndTime();
		delta = act.getDeltaTime();
		date = act.getDate();
		keyWords = new ArrayList<String>();
	}
	
	public void setKeyWords(ArrayList<String> keyList) {
		keyWords = keyList;
	}
	
	public String getProj() {
		return proj.toString();
	}
	
	
	public String getDel() {
		return del;
	}
	
	public String getEffCat() {
		return effCat.toString();
	}
	
	public String getLCycleStep() {
		return lCycleStep.toString();
	}
	
	public String getDate() {
		return act.getDate();
	}
	
	public String getStart() {
		return act.getStartTime();
	}
	
	public String getEnd() {
		return act.getEndTime();
	}
	
	public String getDelta() {
		return act.getDeltaTime();
	}
	
	public String getKeyWords() {
		String s = "";
		if(keyWords.size() > 0) {
			s = keyWords.get(0);
			for(int i = 1; i < keyWords.size(); i++) {
				s += ", " + keyWords.get(i);
			}
		}
		return s;
	}
	
	public SimpleIntegerProperty indexProperty() {
		SimpleIntegerProperty i = new SimpleIntegerProperty(index);
		return i;
	}
	
	public String toString() {
		return "" + getProj() + ", " +  getLCycleStep() + ", " + getEffCat() + 
				", " + getDel() + ", " + getDate() + ", " + getStart() + ", " + getDelta() + getKeyWords();
	}

}
