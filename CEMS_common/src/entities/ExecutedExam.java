package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import enums.ExamType;

/**
 * Represents an executed exam in the system.
 * The class includes details such as execution date, time and status.
 * Implements Serializable to support object serialization.
 * 
 * @author Ofek Ben Avraham
 * @author Rotem Porat
 * @author Almog Elbaz
 * @author Guy Pariente
 * @author Maayan Avittan
 * @author Paz Fayer
 */
public class ExecutedExam implements Serializable{

	private static final long serialVersionUID = 4534415876012077624L;
	private String examID;
	private String executionCode, executionStartTime, executionEndTime;
	private int timeChange, startingStudents, finishIndividualStudents, notCompletedStudents;
	private LocalDateTime executionDate;
	private ExamType examType;
	
	/**
     * Constructor for creating an instance of ExecutedExam with a specific ExamType.
     *
     * @param examType type of the exam.
     */
	public ExecutedExam(ExamType examType) {
		this.examType = examType;
	}
	

    /**
     * Constructor for creating an instance of ExecutedExam with details.
     *
     * @param examID identifier of the exam.
     * @param executionCode code for executing the exam.
     * @param executionDate the date when the exam was executed.
     * @param executionStartTime the time when the exam started.
     * @param executionEndTime the time when the exam ended.
     * @param timeChange the time change for the exam.
     * @param startingStudents the number of students who started the exam.
     * @param finishIndividualStudents the number of students who finished the exam individually.
     * @param notCompletedStudents the number of students who did not complete the exam.
     */
	public ExecutedExam(String examID, String executionCode, LocalDateTime executionDate, String executionStartTime,
			String executionEndTime, int timeChange, int startingStudents, int finishIndividualStudents,
			int notCompletedStudents) {
		super();
		this.examID = examID;
		this.executionCode = executionCode;
		this.executionDate = executionDate;
		this.executionStartTime = executionStartTime;
		this.executionEndTime = executionEndTime;
		this.timeChange = timeChange;
		this.startingStudents = startingStudents;
		this.finishIndividualStudents = finishIndividualStudents;
		this.notCompletedStudents = notCompletedStudents;
	}
	
	/**
     * Constructor for creating an instance of ExecutedExam with minimum details.
     *
     * @param examID identifier of the exam.
     * @param executionCode code for executing the exam.
     * @param executionDate the date when the exam was executed.
     */

	public ExecutedExam(String examID, String executionCode, LocalDateTime executionDate) {
		this.examID = examID;
		this.executionCode = executionCode;
		this.executionDate = executionDate;
	}

	/**
     * Returns a string representation of the ExecutedExam object.
     *
     * @return a string representation of the ExecutedExam object.
     */
	@Override
	public String toString() {
		return "ExecutedExam [examID=" + examID + ", executionCode=" + executionCode + ", executionStartTime="
				+ executionStartTime + ", executionEndTime=" + executionEndTime + ", timeChange=" + timeChange
				+ ", startingStudents=" + startingStudents + ", finishIndividualStudents=" + finishIndividualStudents
				+ ", notCompletedStudents=" + notCompletedStudents + ", executionDate=" + executionDate + ", examType="
				+ examType + "]";
	}
	
	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}

	public String getExecutionCode() {
		return executionCode;
	}

	public void setExecutionCode(String executionCode) {
		this.executionCode = executionCode;
	}

	public LocalDateTime getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDateTime executionDate) {
		this.executionDate = executionDate;
	}

	public String getExecutionStartTime() {
		return executionStartTime;
	}

	public void setExecutionStartTime(String executionStartTime) {
		this.executionStartTime = executionStartTime;
	}

	public String getExecutionEndTime() {
		return executionEndTime;
	}

	public void setExecutionEndTime(String executionEndTime) {
		this.executionEndTime = executionEndTime;
	}

	public int getTimeChange() {
		return timeChange;
	}

	public void setTimeChange(int timeChange) {
		this.timeChange = timeChange;
	}

	public int getStartingStudents() {
		return startingStudents;
	}

	public void setStartingStudents(int startingStudents) {
		this.startingStudents = startingStudents;
	}

	public int getFinishIndividualStudents() {
		return finishIndividualStudents;
	}

	public void setFinishIndividualStudents(int finishIndividualStudents) {
		this.finishIndividualStudents = finishIndividualStudents;
	}

	public int getNotCompletedStudents() {
		return notCompletedStudents;
	}

	public void setNotCompletedStudents(int notCompletedStudents) {
		this.notCompletedStudents = notCompletedStudents;
	}
	public ExamType getExamType() {
		return examType;
	}
	public void setExamType(ExamType examType) {
		this.examType = examType;
	}
	
}
