package entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a Course in the system.
 * It holds various attributes related to a Course and offers methods for course management.
 * Implements Serializable for sending objects over a network or writing into files.
 */

public class Course implements Serializable {
	

	/**
     * Serialization ID for this class. 
     * This is used to verify that the sender and receiver of an object have loaded classes for that object 
     * that are compatible with respect to serialization.
     */
	/**
     * The name of the course.
     */
	private String courseName;

	/**
     * The code of the course.
     */
	private String courseCode;

	/**
     * The field code associated with the course.
     */
	private String fieldCode;

	/**
     * Constructor for creating a new Course with specified name, course code and field code.
     *
     * @param courseName The name of the course.
     * @param courseCode The code of the course.
     * @param fieldCode The field code associated with the course.
     */
	public Course(String courseName, String courseCode, String fieldCode) {
		this.courseName = courseName;
		this.courseCode = courseCode;
		this.fieldCode = fieldCode;
	}
	
	/**
     * Constructor for creating a new Course with specified name and course code.
     *
     * @param courseName The name of the course.
     * @param courseCode The code of the course.
     */
	public Course(String courseName, String courseCode)
	{
		this.courseName = courseName;
		this.courseCode = courseCode;
	}

	/**
     * Getter for courseName.
     *
     * @return The name of the course.
     */
	public String getCourseName() {
		return courseName;
	}

	/**
     * Setter for courseName.
     *
     * @param courseName The name to set for the course.
     */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
     * Getter for courseCode.
     *
     * @return The code of the course.
     */
	public String getCourseCode() {
		return courseCode;
	}

	/**
     * Setter for courseCode.
     *
     * @param courseCode The code to set for the course.
     */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
     * Getter for fieldCode.
     *
     * @return The field code associated with the course.
     */
	public String getFieldCode() {
		return fieldCode;
	}

	/**
     * Setter for fieldCode.
     *
     * @param fieldCode The field code to set for the course.
     */
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	/**
     * This method is overridden from the Object class.
     * It provides a consistent way to compute the hash code for a Course object.
     *
     * @return A hash code value for this Course object.
     */
	@Override
	public int hashCode() {
		return Objects.hash(courseCode, courseName, fieldCode);
	}

	/**
     * This method is overridden from the Object class.
     * It checks if an object is equal to this Course object based on the courseCode, courseName, and fieldCode.
     *
     * @param obj The object to be compared for equality with this Course object.
     * @return True if the given object is equal to this Course object, false otherwise.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(courseCode, other.courseCode) && Objects.equals(courseName, other.courseName)
				&& Objects.equals(fieldCode, other.fieldCode);
	}
	
	
}
