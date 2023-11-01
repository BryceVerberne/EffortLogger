package effortLoggerV2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EffortLogs {
	
	 Activity act;
	 Project proj;
	 LifeCycle lCycleStep;
	 EffortCategory effCat;
	 String del, start, end, delta, date;
	int index;
	
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
	}
	
	public String getProjectTitle() {
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
	
	public SimpleIntegerProperty indexProperty() {
		SimpleIntegerProperty i = new SimpleIntegerProperty(index);
		return i;
	}
		
//	public String getDelTitle() {
//		return del;
//	}
//	
//	public String getEffortTitle() {
//		return effCat.toString();
//	}
//	
//	public String getLifeTitle() {
//		return lCycleStep.toString();
//	}
//	
//	public String getDate() {
//		return act.getDate();
//	}
//	
//	public String getStartTime() {
//		return act.getStartTime();
//	}
//	
//	public String getEndTime() {
//		return act.getEndTime();
//	}
//	
//	public String getDeltaTime() {
//		return act.getDeltaTime();
//	}	
	
	public String toString() {
		return "" + getProjectTitle() + ", " +  getLCycleStep() + ", " + getEffCat() + 
				", " + getDel() + ", " + getDate() + ", " + getStart() + ", " + getDelta();
	}

}
