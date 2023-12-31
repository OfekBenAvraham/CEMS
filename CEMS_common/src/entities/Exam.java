package entities;

import java.io.Serializable;

import enums.ExamType;


/**
 * This class represents an Exam in the system.
 * It holds various attributes related to an Exam and offers methods for exam management.
 * Implements Serializable for sending objects over a network or writing into files.
 */
public class Exam implements Serializable {
	/**
     * Serialization ID for this class. 
     * This is used to verify that the sender and receiver of an object have loaded classes for that object 
     * that are compatible with respect to serialization.
     */
	private static final long serialVersionUID = -8760888514167968564L;
	
	/**
     * The ID of the exam.
     */
	private String examID;

	/**
     * The duration of the exam.
     */
	private int duration;

	/**
     * The description of the exam for the examinee.
     */
	private String descriptionForExaminee;

	/**
     * The description of the exam for the lecturer.
     */
	private String descriptionForLecturer;

	/**
     * The name of the lecturer who set the exam.
     */
	private String lecturerName; 

	/**
     * The ID of the lecturer who set the exam.
     */
	private String lecturerId;

	/**
     * The status of the exam (locked/unlocked).
     */
	private Boolean locked;

	/**
     * The type of the exam.
     */
	private ExamType examType;

	/**
     * The course for which the exam was set.
     */
	private Course course;

	/**
     * The field of study related to the exam.
     */
	private Field field;
	
	private String examCode;
	private String serialNumber;

	/**
     * Constructor for creating a new Exam with all required attributes.
     *
     * @param examID The ID of the exam.
     * @param duration The duration of the exam.
     * @param descriptionForExaminee The description of the exam for the examinee.
     * @param descriptionForLecturer The description of the exam for the lecturer.
     * @param lecturerName The name of the lecturer who set the exam.
     * @param locked The status of the exam (locked/unlocked).
     * @param examType The type of the exam.
     * @param lecturerId The ID of the lecturer who set the exam.
     * @param course The course for which the exam was set.
     * @param field The field of study related to the exam.
     */
	public Exam(String examID, int duration, String descriptionForExaminee, String descriptionForLecturer,
			String lecturerName, Boolean locked, ExamType examType, String lecturerId, Course course, Field field) {
		super();
		this.examID = examID;
		this.duration = duration;
		this.descriptionForExaminee = descriptionForExaminee;
		this.descriptionForLecturer = descriptionForLecturer;
		this.lecturerName = lecturerName;
		this.locked = locked;
		this.examType = examType;
		this.lecturerId = lecturerId;
		this.course = course;
		this.field = field;
	}

	/**
     * Constructor for creating a new Exam without an ID. The ID must be set later.
     * Other attributes are required.
     *
     * @param duration The duration of the exam.
     * @param descriptionForExaminee The description of the exam for the examinee.
     * @param descriptionForLecturer The description of the exam for the lecturer.
     * @param lecturerName The name of the lecturer who set the exam.
     * @param locked The status of the exam (locked/unlocked).
     * @param examType The type of the exam.
     * @param lecturerId The ID of the lecturer who set the exam.
     * @param course The course for which the exam was set.
     * @param field The field of study related to the exam.
     */
	public Exam(int duration, String descriptionForExaminee, String descriptionForLecturer, String lecturerName,
			Boolean locked, ExamType examType, String lecturerId, Course course, Field field) {
		super();
		this.duration = duration;
		this.descriptionForExaminee = descriptionForExaminee;
		this.descriptionForLecturer = descriptionForLecturer;
		this.lecturerName = lecturerName;
		this.locked = locked;
		this.examType = examType;
		this.lecturerId = lecturerId;
		this.course = course;
		this.field = field;
	}

    /**
     * Returns the exam ID.
     *
     * @return exam ID
     */
    public String getExamID() {
        return examID;
    }

    /**
     * Sets the exam ID.
     *
     * @param examID the exam ID to set
     */
    public void setExamID(String examID) {
        this.examID = examID;
    }

    /**
     * Returns the exam duration.
     *
     * @return duration of the exam
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the exam.
     *
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Returns the description of the exam for examinee.
     *
     * @return description for examinee
     */
    public String getDescriptionForExaminee() {
        return descriptionForExaminee;
    }

    /**
     * Sets the description of the exam for examinee.
     *
     * @param descriptionForExaminee the description to set for examinee
     */
    public void setDescriptionForExaminee(String descriptionForExaminee) {
        this.descriptionForExaminee = descriptionForExaminee;
    }

    /**
     * Returns the description of the exam for lecturer.
     *
     * @return description for lecturer
     */
    public String getDescriptionForLecturer() {
        return descriptionForLecturer;
    }

    /**
     * Sets the description of the exam for lecturer.
     *
     * @param descriptionForLecturer the description to set for lecturer
     */
    public void setDescriptionForLecturer(String descriptionForLecturer) {
        this.descriptionForLecturer = descriptionForLecturer;
    }

    /**
     * Returns the name of the lecturer who set the exam.
     *
     * @return lecturer's name
     */
    public String getLecturerName() {
        return lecturerName;
    }

    /**
     * Sets the name of the lecturer who set the exam.
     *
     * @param lecturerName the name of the lecturer to set
     */
    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    /**
     * Returns the status of the exam (locked/unlocked).
     *
     * @return true if the exam is locked, false otherwise
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * Sets the status of the exam (locked/unlocked).
     *
     * @param locked the status to set
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * Returns the type of the exam.
     *
     * @return type of the exam
     */
    public ExamType getExamType() {
        return examType;
    }

    /**
     * Sets the type of the exam.
     *
     * @param examType the type to set
     */
    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    /**
     * Returns the course for which the exam was set.
     *
     * @return course of the exam
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course for which the exam was set.
     *
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Returns the field of study related to the exam.
     *
     * @return field of the exam
     */
    public Field getField() {
        return field;
    }

    /**
     * Sets the field of study related to the exam.
     *
     * @param field the field to set
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * Returns the ID of the lecturer who set the exam.
     *
     * @return lecturer's ID
     */
    public String getLecturerId() {
        return lecturerId;
    }

    /**
     * Sets the ID of the lecturer who set the exam.
     *
     * @param lecturerId the ID of the lecturer to set
     */
    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
