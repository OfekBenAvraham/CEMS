package entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The ExamsDisplayData class represents the data related to exams that will be displayed.
 * This includes information about the course, exam code, date of the exam, its duration, and exam ID.
 */
public class ExamsDisplayData implements Serializable {

	/**
	 * Serial version UID for serialization and de-serialization.
	 */
	private static final long serialVersionUID = -8971928965859432753L;
	
	/**
	 * The course name.
	 */
	private String courseName;
	
	/**
	 * The course code.
	 */
	private String courseCode;
	
	/**
	 * The exam code.
	 */
	private String examCode;
	
	/**
	 * The date of the exam.
	 */
	private Timestamp date;
	
	/**
	 * The duration of the exam.
	 */
	private int duration;
	
	/**
	 * The ID of the exam.
	 */
	private String examId;
	
	/**
	 * The serial number of the executedExmas
	 */
	private String executeExamSerialNum;

	/**
	 * Constructor for creating an exam display data object with necessary information.
	 *
	 * @param examCode The exam code.
	 * @param date The date of the exam.
	 * @param courseName The course name.
	 * @param duration The duration of the exam.
	 * @param examId The ID of the exam.
	 * @param executedExamSerialNumber the serial number of the executed exam.
	 */
	public ExamsDisplayData(String examCode, Timestamp date, String courseName, int duration, String examId,String executedExamSerialNumber) {
		this.examCode = examCode;
		this.date = date;
		this.courseName = courseName;
		this.duration = duration;
		this.examId = examId;
		this.executeExamSerialNum = executedExamSerialNumber;
	}
	/**
	 * Constructor for creating an exam display data object with necessary information.
	 *
	 * @param examCode The exam code.
	 * @param date The date of the exam.
	 * @param courseName The course name.
	 * @param duration The duration of the exam.
	 * @param examId The ID of the exam.
	 */
	public ExamsDisplayData(String courseCode, String courseName, String examCode,Timestamp date,  int duration, String examId, String executedExamSerialNumber) {
		this.examCode = examCode;
		this.date = date;
		this.courseName = courseName;
		this.duration = duration;
		this.examId = examId;
		this.executeExamSerialNum = executedExamSerialNumber;
	}
	/**
	 * Constructor for creating an exam display data object with necessary information.
	 *
	 * @param courseCode The course code.
	 * @param courseName The course name.
	 * @param examCode The exam code.
	 * @param examId The ID of the exam.
	 * @param date The date of the exam.
	 * @param serialNumberOfExecutedCode serial number of executed exam.
	 */
	public ExamsDisplayData(String courseCode,String courseName, String examId, String examCode, Timestamp date, String serialNumOfExecutedExam) {
		this.courseName = courseName;
		this.courseCode = courseCode;
		this.examCode = examCode;
		this.examId = examId;
		this.date = date;
		this.executeExamSerialNum = serialNumOfExecutedExam;
	}
	
	/**
	 * Method for getting the current object.
	 *
	 * @return the current object.
	 */
	public ExamsDisplayData getExamDisplayObj()
	{
		return this;
	}
	
	/**
	 * @return the courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	/**
	 * @param courseCode the courseCode to set
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


	/**
	 * @return the examId
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * @param examId the examId to set
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the examCode
	 */
	public String getExamCode() {
		return examCode;
	}

	/**
	 * @param examCode the examCode to set
	 */
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getExecuteExamSerialNumber() {
		return executeExamSerialNum;
	}

	public void setExecuteExamSerialNumber(String executeExamSerialNum) {
		this.executeExamSerialNum = executeExamSerialNum;
	}

}
