package effortLoggerV2;

import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

// written by Dayton
public class Activity {
	// tells if activity is going on
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	boolean on = false;
	LocalDateTime start, end = null;
	double deltaTime;
	LocalDate dateStart, dateEnd = null;
	
	// written by Dayton
	public Activity() {
		// activity is on now
		on = true;
		start = LocalDateTime.now();
		dateStart = LocalDate.now();
	}
	
	// written by Dayton
	public void stopActivity() {
		end = LocalDateTime.now();
		deltaTime = (double) Duration.between(start, end).toSeconds() / 60.0;
		dateEnd = LocalDate.now();
		on = false; // stop activity now		
	}
	
	// written by Dayton
	public String toString() {
		return "" + this.start.format(format) + "\n" + this.end.format(format) + "\n";
	}
	
	public String getDate() {
		return dateStart.format(format);
	}
	
	public String getStartTime() {
		return start.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
	
	public String getEndTime() {
		return end.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

	}
	
	public String getDeltaTime() {
		return new DecimalFormat("#.##").format(deltaTime);
	}
}
