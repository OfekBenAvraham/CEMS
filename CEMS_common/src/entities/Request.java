package entities;


import enums.RequestStatus;

import java.io.Serializable;

/**
 * The Request class represents a request made by a lecturer for extra time during an exam.
 * Each request is associated with a specific exam, contains a free text explanation from the lecturer,
 * and is tracked by its status. 
 */
public class Request implements Serializable {
	
	/**
	 * 
	 */
	/**
     * Serial version UID for serialization and de-serialization.
     */
    private static final long serialVersionUID = 3580205396141623198L;

    /**
     * The unique identifier of the request.
     */
    private int RequestID;

    /**
     * The exam associated with the request.
     */
    private Exam test;

    /**
     * The unique identifier of the exam.
     */
    private String examId;

    /**
     * The free text explanation provided by the lecturer.
     */
    private String freeText;

    /**
     * The lecturer making the request.
     */
    private Lecturer lecturer;

    /**
     * The name of the lecturer making the request.
     */
    private String lecturerName;

    /**
     * The extra time requested.
     */
    private int extraTime;

    /**
     * The duration of the exam.
     */
    private int duration;

    /**
     * The current status of the request.
     */
    private RequestStatus status;

    /**
     * The unique identifier of the lecturer.
     */
    private String lecturerId;
    
    private String executedExamSerialNum;




	/**
     * Constructs a new Request object using an Exam object, free text, a Lecturer object, and extra time.
     *
     * @param test       the Exam object.
     * @param freeText   the free text provided.
     * @param lecturer   the Lecturer object.
     * @param extraTime  the extra time.
     */
	public Request(Exam test, String freeText, Lecturer lecturer, int extraTime)
	{
		
		this.freeText = freeText;
		this.lecturer = lecturer;
		this.extraTime = extraTime;
		this.duration = test.getDuration();
		this.status = RequestStatus.NOT_RESPONDED_YET; 
	}
	
	
	/**
	 * Constructs a new Request object using an Exam object, free text, a Lecturer object, and extra time.
	 *
	 * @param test       the Exam object.
	 * @param freeText   the free text provided.
	 * @param lecturer   the Lecturer object.
	 * @param extraTime  the extra time.
	 */
	public Request(int reqId,String examId, String freeText, String lecturerName, int duration,int extraTime)
	{
		this.examId = examId;
		this.freeText = freeText;
		this.lecturerName = lecturerName;
		this.duration = duration;
		this.extraTime = extraTime;
		this.status = RequestStatus.NOT_RESPONDED_YET; 
	}
	
	/**
	 * Constructs a new Request object using an Exam object, free text, a Lecturer object, and extra time.
	 *
	 * @param test       the Exam object.
	 * @param freeText   the free text provided.
	 * @param lecturer   the Lecturer object.
	 * @param extraTime  the extra time.
	 */
	public Request(String examId, String freeText, String lecturerName, int duration,int extraTime)
	{

		this.examId = examId;
		this.freeText = freeText;
		this.lecturerName = lecturerName;
		this.duration = duration;
		this.extraTime = extraTime;
		this.status = RequestStatus.NOT_RESPONDED_YET; 
	}

	/**
	 * Constructs a new Request object using an Exam object, free text, a Lecturer object, and extra time.
	 *
	 * @param test       the Exam object.
	 * @param freeText   the free text provided.
	 * @param lecturer   the Lecturer object.
	 * @param extraTime  the extra time.
	 *//**
 * Constructs a new Request object using executed exam ID, free text, lecturer name, duration, extra time, and lecturer ID.
 *
 * @param executedExamId  the executed exam ID.
 * @param freeText2       the free text provided.
 * @param lecturerName2   the name of the lecturer.
 * @param duration2       the duration of the exam.
 * @param extraTime2      the extra time.
 * @param lecturerId      the lecturer ID.
 */
	public Request(int reqId,String executedExamId, String freeText2, String lecturerName2, int duration2, int extraTime2,
			String lecturerId) {
		this.RequestID = reqId;
		this.examId=executedExamId;
		this.freeText=freeText2;
		this.lecturerName = lecturerName2;
		this.duration = duration2;
		this.extraTime = extraTime2;
		this.lecturerId = lecturerId;
		this.status = RequestStatus.NOT_RESPONDED_YET;
	}
	
	
	/**
	 * Constructs a new Request object using an Exam object, free text, a Lecturer object, and extra time.
	 *
	 * @param test       the Exam object.
	 * @param freeText   the free text provided.
	 * @param lecturer   the Lecturer object.
	 * @param extraTime  the extra time.
	 */
	public Request(String executedExamId, String freeText2, String lecturerName2, int duration2, int extraTime2,
			String lecturerId) {
		this.examId=executedExamId;
		this.freeText=freeText2;
		this.lecturerName = lecturerName2;
		this.duration = duration2;
		this.extraTime = extraTime2;
		this.lecturerId = lecturerId;
		this.status = RequestStatus.NOT_RESPONDED_YET;
	}
	
	public Request(String executedExamId, String freeText2, String lecturerName2, int duration2, int extraTime2,
			String lecturerId, String executedExamSerialNum) {
		this.examId=executedExamId;
		this.freeText=freeText2;
		this.lecturerName = lecturerName2;
		this.duration = duration2;
		this.extraTime = extraTime2;
		this.lecturerId = lecturerId;
		this.status = RequestStatus.NOT_RESPONDED_YET;
		this.executedExamSerialNum = executedExamSerialNum;
		
	}

	/**
	 * @return the lecturerId
	 */
	public String getLecturerId() {
		return lecturerId;
	}

	/**
	 * @param lecturerId the lecturerId to set
	 */
	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	/**
	 * @return the requestID
	 */
	public int getRequestID() {
		return RequestID;
	}

	/**
	 * @param requestID the requestID to set
	 */
	public void setRequestID(int requestID) {
		RequestID = requestID;
	}

	/**
	 * @param lecturerName the lecturerName to set
	 */
	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	/**
	 * @return the test
	 */
	public Exam getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(Exam test) {
		this.test = test;
	}

	/**
	 * @return the testCode
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * @param testCode the testCode to set
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	/**
	 * @return the freeText
	 */
	public String getFreeText() {
		return freeText;
	}

	/**
	 * @param freeText the freeText to set
	 */
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	/**
	 * @return the lecturer
	 */
	public Lecturer getLecturer() {
		return lecturer;
	}
	
	/**
	 * @return the lecturer name
	 */
	public String getLecturerName() {
		return this.lecturerName;
	}

	/**
	 * @param lecturer the lecturer to set
	 */
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	/**
	 * @return the extraTime
	 */
	public int getExtraTime() {
		return extraTime;
	}

	/**
	 * @param extraTime the extraTime to set
	 */
	public void setExtraTime(int extraTime) {
		this.extraTime = extraTime;
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
	 * @return the status
	 */
	public RequestStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	
    /**
	 * @return the executedExamSerialNum
	 */
	public String getExecutedExamSerialNum() {
		return executedExamSerialNum;
	}
	/**
	 * @param executedExamSerialNum the executedExamSerialNum to set
	 */
	public void setExecutedExamSerialNum(String executedExamSerialNum) {
		this.executedExamSerialNum = executedExamSerialNum;
	}
	
	/**
	 * Returns a string representation of the Request object.
	 *
	 * @return a string representation of the Request object.
	 */
	@Override
	public String toString() {
	    return "Request{" +
	            "RequestID=" + RequestID +
	            ", examId='" + examId + '\'' +
	            ", freeText='" + freeText + '\'' +
	            ", lecturerName='" + lecturerName + '\'' +
	            ", duration=" + duration +
	            ", extraTime=" + extraTime +
	            ", status=" + status +
	            '}';
	}
	
	
	
	
	

}
