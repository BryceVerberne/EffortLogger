package effortLoggerV2;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;

public class EffortLogs {
	
	// User created Activity
	Activity act;
	// User's chosen project
	Project proj;
	// User's chosen Life Cycle Step
	LifeCycle lCycleStep;
	// User's chosen Effort Category 
	EffortCategory effCat;
	// Start, End, and Delta time along with the date
	// And the respective Deliverable/Interruption/ETC
	String del, start, end, delta, date;
	int index;
	// Each Effort Log has List of KeyWords
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
