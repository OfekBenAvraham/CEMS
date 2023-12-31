package entities;

/**
 * This class represents a lecturer associated with a course in the system.
 */
public class LecturerInCourse {
	private String lecturerId, courseCode;

    /**
     * Initializes a new LecturerInCourse object with the specified lecturer ID and course code.
     *
     * @param lecturerId the ID of the lecturer
     * @param courseCode the code of the course
     */
	public LecturerInCourse(String lecturerId, String courseCode) {
		this.lecturerId = lecturerId;
		this.courseCode = courseCode;
	}

    /**
     * Returns the ID of the lecturer.
     *
     * @return the ID of the lecturer
     */
	public String getLecturerId() {
		return lecturerId;
	}

    /**
     * Sets the ID of the lecturer.
     *
     * @param lecturerId the ID to set
     */
	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

    /**
     * Returns the code of the course.
     *
     * @return the code of the course
     */
	public String getCourseCode() {
		return courseCode;
	}

    /**
     * Sets the code of the course.
     *
     * @param courseCode the course code to set
     */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
}