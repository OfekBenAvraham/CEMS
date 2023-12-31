package entities;

/**
 * Represents a time with hour, minute, and second components.
 * 
 * @author Paz Fayer
 * @author Maayan Avittan
 */
public class Time {
	private int hour;
	private int minute;
	private int second;

	/**
	 * Constructs a Time object with the specified hour, minute, and second.
	 *
	 * @param hour   the hour component of the time
	 * @param minute the minute component of the time
	 * @param second the second component of the time
	 */
	public Time(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	/**
	 * Constructs a Time object from a string representation of time in the format
	 * "HH:MM:SS".
	 *
	 * @param currentTime a string representing the current time in the format
	 *                    "HH:MM:SS"
	 */
	public Time(String currentTime) {
		String[] time = currentTime.split(":");
		hour = Integer.parseInt(time[0]);
		minute = Integer.parseInt(time[1]);
		second = Integer.parseInt(time[2]);
	}

	/**
	 * Returns the current time as a formatted string in the format "HH:MM:SS".
	 *
	 * @return the current time as a formatted string in the format "HH:MM:SS"
	 */
	public String getCurrentTime() {
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	/**
	 * Updates the time by increasing the second component by one. If necessary,
	 * adjusts the minute and hour components accordingly.
	 */
	public void oneSecondPassed() {
		second++;
		if (second == 60) {
			minute++;
			second = 0;
			if (minute == 60) {
				hour++;
				minute = 0;
				if (hour == 24) {
					hour = 0;
				}
			}
		}
	}

	/**
	 * Updates the time by decreasing the second component by one. If necessary,
	 * adjusts the minute and hour components accordingly.
	 */
	public void oneSecondPassedMinus() {
		second--;
		if (second < 0) {
			minute--;
			second = 59;
			if (minute < 0) {
				hour--;
				minute = 59;
				if (hour < 0) {
					hour = 23;
				}
			}
		}
	}
}
