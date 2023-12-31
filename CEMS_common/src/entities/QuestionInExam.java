package entities;

import java.io.Serializable;

/**
 * The QuestionInExam class represents a question within an exam.
 * It holds information such as the exam ID, the question itself, and the points assigned to the question.
 */
public class QuestionInExam implements Serializable{
	   /**
     * The serial version UID for serialization.
     */
	private static final long serialVersionUID = -8686478139832092922L;
	private String examId;
	private Question question;
	private Double points;


    /**
     * Constructs a new QuestionInExam object.
     *
     * @param examId   the ID of the exam
     * @param question the question object
     * @param points   the points assigned to the question in the exam
     */
	public QuestionInExam(String examId, Question question, Double points) {
		super();
		this.examId = examId;
		this.question = question;
		this.points = points;
	}
	
    /**
     * Constructs a new QuestionInExam object with a question and initial points of 0.0.
     *
     * @param question the question object
     */
	public QuestionInExam(Question question) {
		super();
		this.question = question;
		this.points = 0.0;
	}

    /**
     * Retrieves the ID of the exam.
     *
     * @return the exam ID
     */
	public String getExamId() {
		return examId;
	}

    /**
     * Sets the ID of the exam.
     *
     * @param examId the exam ID to set
     */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	  /**
     * Retrieves the question object.
     *
     * @return the question object
     */
	public Question getQuestion() {
		return question;
	}

	/**
     * Sets the question object.
     *
     * @param question the question object to set
     */
	public void setQuestion(Question question) {
		this.question = question;
	}

	 /**
     * Retrieves the points assigned to the question.
     *
     * @return the points assigned to the question
     */
	public Double getPoints() {
		return points;
	}


    /**
     * Sets the points assigned to the question.
     *
     * @param points the points to set
     */
	public void setPoints(Double points) {
		this.points = points;
	}
	
	  /**
     * Retrieves the text of the question.
     *
     * @return the question text
     */
	public String getQuestionText() {
		return this.question.getQuestionText();
	}
	 /**
     * Retrieves option 1 of the question.
     *
     * @return option 1
     */
    public String getOption1() {
        return this.question.getOption1();
    }

    /**
     * Retrieves option 2 of the question.
     *
     * @return option 2
     */
    public String getOption2() {
        return this.question.getOption2();
    }

    /**
     * Retrieves option 3 of the question.
     *
     * @return option 3
     */
    public String getOption3() {
        return this.question.getOption3();
    }

    /**
     * Retrieves option 4 of the question.
     *
     * @return option 4
     */
    public String getOption4() {
        return this.question.getOption4();
    }
    
    /**
     * Retrieves the correct answer of the question.
     *
     * @return the correct answer
     */
	public String getCorrectAnswer() {
		return this.question.getCorrectAnswer();
	}


}
