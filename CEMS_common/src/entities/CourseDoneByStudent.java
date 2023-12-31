package entities;

import java.io.Serializable;

/**
 * Represents a course completed by a student, including the grade, course
 * details, and student information.
 * 
 * @author Paz Fayer
 * @author Maayan Avittan
 * 
 */
public class CourseDoneByStudent implements Serializable {

	private static final long serialVersionUID = 1324037263391507414L;
	private double grade;
	private Course course;
	private Student student;
	private String courseName;
	private String examID;
	private String note;

	/**
	 * Constructs a CourseDoneByStudent object with the specified grade, course, and
	 * student.
	 *
	 * @param grade   the grade obtained by the student in the course
	 * @param course  the course completed by the student
	 * @param student the student who completed the course
	 */
	public CourseDoneByStudent(double grade, Course course, Student student) {
		this.grade = grade;
		this.course = course;
		this.student = student;
	}

	/**
	 * Constructs a CourseDoneByStudent object with the specified grade, course
	 * name, and exam ID.
	 *
	 * @param grade      the grade obtained by the student in the course
	 * @param courseName the name of the course
	 * @param examID     the ID of the exam associated with the course
	 */
	public CourseDoneByStudent(Double grade, String courseName, String examID, String note) {
		this.grade = grade;
		this.courseName = courseName;
		this.examID = examID;
		this.note = note;
	}

	/**
	 * Constructs a new empty CourseDoneByStudent object.
	 */
	public CourseDoneByStudent() {

	}
	/**
	 * Returns the grade obtained by the student in the course.
	 *
	 * @return the grade obtained by the student
	 */
	public double getGrade() {
		return grade;
	}

	/**
	 * Sets the grade obtained by the student in the course.
	 *
	 * @param grade the grade to be set
	 */
	public void setGrade(double grade) {
		this.grade = grade;
	}

	/**
	 * Returns the course completed by the student.
	 *
	 * @return the course completed by the student
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Sets the course completed by the student.
	 *
	 * @param course the course to be set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * Returns the student who completed the course.
	 *
	 * @return the student who completed the course
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Sets the student who completed the course.
	 *
	 * @param student the student to be set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * Returns the name of the course.
	 *
	 * @return the name of the course
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the name of the course.
	 *
	 * @param courseName the name of the course to be set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the ID of the exam associated with the course.
	 *
	 * @return the ID of the exam associated with the course
	 */
	public String getExamID() {
		return examID;
	}

	/**
	 * Sets the ID of the exam associated with the course.
	 *
	 * @param examID the ID of the exam to be set
	 */
	public void setExamID(String examID) {
		this.examID = examID;
	}
	

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Returns a string representation of the CourseDoneByStudent object.
	 *
	 * @return a string representation of the CourseDoneByStudent object
	 */
	@Override
	public String toString() {
		return "CourseDoneByStudent [grade=" + grade + ", course=" + course + ", student=" + student + ", courseName="
				+ courseName + ", examID=" + examID + "]";
	}
}
