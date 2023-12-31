package entities;

/**
 * The StudentInCourse class represents a student enrolled in a course.
 */
public class StudentInCourse {
	private String courseCode, studentID;

	  /**
     * Constructs a new StudentInCourse object with the specified course code and student ID.
     *
     * @param courseCode the code of the course the student is enrolled in
     * @param studentID  the ID of the student
     */
	public StudentInCourse(String courseCode, String studentID) {
		super();
		this.courseCode = courseCode;
		this.studentID = studentID;
	}
    /**
     * Retrieves the course code of the course the student is enrolled in.
     *
     * @return the course code
     */
	public String getCourseCode() {
		return courseCode;
	}

    /**
     * Sets the course code of the course the student is enrolled in.
     *
     * @param courseCode the course code to set
     */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


    /**
     * Retrieves the ID of the student.
     *
     * @return the student ID
     */
	public String getStudentID() {
		return studentID;
	}

    /**
     * Sets the ID of the student.
     *
     * @param studentID the student ID to set
     */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

}
