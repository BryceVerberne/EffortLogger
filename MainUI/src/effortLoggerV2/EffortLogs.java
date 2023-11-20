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

import java.text.DecimalFormat;
import java.time.LocalDateTime;
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
	
	LocalDateTime startTemp, endTemp;
	
	int index;
	// Each Effort Log has List of KeyWords
	ArrayList<String> keyWords;
	
	public EffortLogs() {
		
	}
	
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
		startTemp = act.getStartTimeTemp();
		endTemp = act.getEndTimeTemp();
		delta = act.getDeltaTime();
		date = act.getDate();
		keyWords = new ArrayList<String>();
	}
	
	// overloaded constructor
	public EffortLogs(Project project, LifeCycle lc, 
						EffortCategory ec, String deliver, int index) 
	{
		this.index = index;
		proj = project;
		lCycleStep = lc;
		effCat = ec;
		del = deliver;
		keyWords = new ArrayList<String>();
	}
	
	public void setKeyWords(ArrayList<String> keyList) {
		keyWords = keyList;
	}
	
	public String getProj() {
		return proj.toString();
	}
	
	public Project getProjObj() {
		return proj;
	}
	
	public void setProj(Project proj) {
		this.proj = proj;
	}
	
	
	public String getDel() {
		return del;
	}
	
	public void setDel(String del) {
		this.del = del;
	}
	
	public String getEffCat() {
		return effCat.toString();
	}
	
	public EffortCategory getEffCatObj() {
		return effCat;
	}
	
	public void setEffCat(EffortCategory effCat) {
		this.effCat = effCat;
	}
	
	public String getLCycleStep() {
		return lCycleStep.toString();
	}
	
	public LifeCycle getLCycleStepObj() {
		return lCycleStep;
	}
	
	public void setLCycleStep(LifeCycle lCycleStep) {
		this.lCycleStep = lCycleStep;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getStart() {
		return start;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public LocalDateTime getStartTime() {
		return startTemp;
	}
	
	public String getEnd() {
		return end;
	}
	
	public LocalDateTime getEndTime() {
		return endTemp;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getDelta() {
		return delta;
	}
	
	public void setDelta(double dTime) {
		this.delta = new DecimalFormat("#.##").format(dTime);
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
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
				", " + getDel() + ", " + getDate() + ", (" + getStart() + "-" + getEnd() + "), " + getDelta() + getKeyWords();
	}

}
