package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the current time.
 * 
 * @author Paz Fayer
 * @author Maayan Avittan
 * 
 */
public class CurrentTime {
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
	private LocalDateTime now = LocalDateTime.now();

	/**
	 * Constructs a CurrentTime object.
	 */
	public CurrentTime() {
	}

	/**
	 * Returns the current time as a formatted string in the format "HH:mm:ss".
	 *
	 * @return the current time as a formatted string in the format "HH:mm:ss"
	 */
	public String currentTime() {
		return dtf.format(now);
	}
}
