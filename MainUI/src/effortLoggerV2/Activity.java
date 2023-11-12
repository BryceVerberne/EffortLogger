/**
 * Title:       Activity Class Class
 * Author:      Dayton Koehler
 * Email:       Dkoehle4@asu.edu
 * Description: Part of the effortLoggerV2 JavaFX application, this class tracks and manages activity durations. 
 *              It records start/end times, calculates duration, and provides formatted time data. 
 *              The class facilitates starting and stopping activities, offering precise time logging capabilities.
 */



package effortLoggerV2;

import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Activity {
	// tells if activity is going on
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	boolean on = false;
	LocalDateTime start, end = null;
	double deltaTime;
	LocalDate dateStart, dateEnd = null;
	
	public Activity() {
		// activity is on now
		on = true;
		start = LocalDateTime.now();
		dateStart = LocalDate.now();
	}
	
	public void stopActivity() {
		end = LocalDateTime.now();
		deltaTime = (double) Duration.between(start, end).toSeconds() / 60.0;
		dateEnd = LocalDate.now();
		on = false; // stop activity now		
	}
	
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