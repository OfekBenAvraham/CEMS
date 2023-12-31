package entities;

import java.io.Serializable;
import java.util.ArrayList;

import enums.Role;

/**
 * Represents a student user. Extends the User class and implements the
 * Serializable interface.
 * 
 * @author Maayan Avittan
 * @author Ofek Ben Avraham
 * @author Rotem Porat
 * @author Guy Pariente
 * @author Almog Elbaz
 * @author Paz Fayer
 *
 */
public class Student extends User implements Serializable {
	private static final long serialVersionUID = -195757161121479278L;
	private ArrayList<CourseDoneByStudent> courses;
	private CourseDoneByStudent choosenCourse = null;
	private String choosenCurrentExam;

	/**
	 * Constructs a new Student object with the specified parameters.
	 *
	 * @param role        The role of the student.
	 * @param username    The username of the student.
	 * @param password    The password of the student.
	 * @param firstName   The first name of the student.
	 * @param lastName    The last name of the student.
	 * @param userID      The user ID of the student.
	 * @param email       The email of the student.
	 * @param phoneNumber The phone number of the student.
	 */
	public Student(Role role, String username, String password, String firstName, String lastName, String userID,
			String email, String phoneNumber) {
		super(role, username, password, firstName, lastName, userID, email, phoneNumber);
	}

	/**
	 * Constructs a new empty Student object.
	 */
	public Student() {
		super();
	}

	public Student(String studentId, String firstName, String lastName) {
		super(studentId,firstName,lastName);
	}

	/**
	 * Gets the courses taken by the student.
	 *
	 * @return The list of courses taken by the student.
	 */
	public ArrayList<CourseDoneByStudent> getCourses() {
		return courses;
	}

	/**
	 * Sets the courses taken by the student.
	 *
	 * @param courses The list of courses taken by the student.
	 */
	public void setCourses(ArrayList<CourseDoneByStudent> courses) {
		this.courses = courses;
	}

	/**
	 * Sets the chosen course for the student.
	 *
	 * @param chosenCourse The chosen course for the student.
	 */
	public void setChoosenCourse(CourseDoneByStudent choosenCourse) {
		this.choosenCourse = choosenCourse;
	}

	/**
	 * Gets the chosen course for the student.
	 *
	 * @return The chosen course for the student.
	 */
	public CourseDoneByStudent getChoosenCourse() {
		return choosenCourse;
	}

	/**
	 * Gets the chosen current exam for the student.
	 *
	 * @return The chosen current exam for the student.
	 */
	public String getChoosenCurrentExam() {
		return choosenCurrentExam;
	}

	/**
	 * Sets the chosen current exam for the student.
	 *
	 * @param chosenCurrentExam The chosen current exam for the student.
	 */
	public void setChoosenCurrentExam(String choosenCurrentExam) {
		this.choosenCurrentExam = choosenCurrentExam;
	}
	
	public String getStudentId()
	{
		return super.getUserID();
	}
	public String getFirstName()
	{
		return super.getFirstName();
	}
	public String getLastName()
	{
		return super.getLastName();
	}

}
