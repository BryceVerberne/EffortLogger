package effortLoggerV2;

public class EffortLogs {
	
	private Activity act;
	private Project proj;
	private LifeCycle lCycleStep;
	private EffortCategory effCat;
	private String del, start, end, delta, date;
	private int index;
	
	public EffortLogs(Activity activity, Project project, LifeCycle lc, EffortCategory ec, String deliver, int index) {
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
	
	public String getDelTitle() {
		return del;
	}
	
	public String getEffortTitle() {
		return effCat.toString();
	}
	
	public String getLifeTitle() {
		return lCycleStep.toString();
	}
	
	public String getDate() {
		return act.getDate();
	}
	
	public String getStartTime() {
		return act.getStartTime();
	}
	
	public String getEndTime() {
		return act.getEndTime();
	}
	
	public String getDeltaTime() {
		return act.getDeltaTime();
	}
	
	public String toString() {
		return "" + getProjectTitle() + ", " +  getLifeTitle() + ", " + getEffortTitle() + 
				", " + getDelTitle() + ", " + getDate() + ", " + getStartTime() + ", " + getDeltaTime();
	}

}
