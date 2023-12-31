package entities;

import java.io.Serializable;

/**
 * This class will hold question with all it data (question,id,answers and etc)
 */
public class Question implements Serializable {

	private static final long serialVersionUID = -4399070668517752953L;
	private String id, field, fieldName, courses, questionText, questionNumber, lecturerName, lecturerID, option1, option2,
			option3, option4, correctAnswer;

	/**
	 * Constructs a new Question object.
	 *
	 * @param field the field related to the question
	 * @param fieldName the name of the field related to the question
	 * @param courses the courses related to the question
	 * @param questionText the text of the question
	 * @param lecturerName the name of the lecturer who created the question
	 * @param lecturerID the ID of the lecturer who created the question
	 * @param option1 the first option of the question
	 * @param option2 the second option of the question
	 * @param option3 the third option of the question
	 * @param option4 the fourth option of the question
	 * @param correctAnswer the correct answer of the question
	 */
	public Question(String field, String fieldName, String courses, String questionText, String lecturerName, String lecturerID, String option1, String option2,
			String option3, String option4, String correctAnswer) {
		this.field = field;
		this.fieldName = fieldName;
		this.courses = courses;
		this.questionText = questionText;
		this.lecturerName = lecturerName;
		this.lecturerID = lecturerID;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctAnswer = correctAnswer;
	}

	/**
	 * Constructs a new Question object with an ID and question number.
	 *
	 * @param id the ID of the question
	 * @param field the field related to the question
	 * @param fieldName the name of the field related to the question
	 * @param courses the courses related to the question
	 * @param questionText the text of the question
	 * @param questionNumber the number of the question
	 * @param lecturerName the name of the lecturer who created the question
	 * @param lecturerID the ID of the lecturer who created the question
	 * @param option1 the first option of the question
	 * @param option2 the second option of the question
	 * @param option3 the third option of the question
	 * @param option4 the fourth option of the question
	 * @param correctAnswer the correct answer of the question
	 */
	public Question(String id, String field, String fieldName, String courses, String questionText, String questionNumber,
			String lecturerName, String lecturerID, String option1, String option2, String option3, String option4,
			String corretAnswer) {
		super();
		this.id = id;
		this.courses = courses;
		this.field = field;
		this.fieldName = fieldName;
		this.questionText = questionText;
		this.questionNumber = questionNumber;
		this.lecturerName = lecturerName;
		this.lecturerID = lecturerID;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctAnswer = corretAnswer;
	}
	
	/**
	 * Constructs a new Question object for computerized exam with question text and options only.
	 *
	 * @param questionText the text of the question
	 * @param option1 the first option of the question
	 * @param option2 the second option of the question
	 * @param option3 the third option of the question
	 * @param option4 the fourth option of the question
	 * @param correctAnswer the correct answer of the question
	 */
	public Question(String questionText, String option1, String option2, String option3, String option4, String correctAnswer) {
		super();
		this.questionText = questionText;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctAnswer = correctAnswer;
	}


    /**
     * Returns the question's ID.
     *
     * @return the question's ID
     */
	public String getId() {
		return id;
	}
	/**
     * Sets the question's ID based on the field and question number.
     */
	public void setId() {
		id = field + questionNumber;
	}

	/**
	 * Returns the field related to the question.
	 *
	 * @return the field related to the question
	 */
	public String getField() {
	    return field;
	}

	/**
	 * Sets the field related to the question.
	 *
	 * @param field the new field related to the question
	 */
	public void setField(String field) {
	    this.field = field;
	}

	/**
	 * Returns the field's name related to the question.
	 *
	 * @return the name of the field related to the question
	 */
	public String getFieldName() {
	    return fieldName;
	}

	/**
	 * Sets the field's name related to the question.
	 *
	 * @param fieldName the new name of the field related to the question
	 */
	public void setFieldName(String fieldName) {
	    this.fieldName = fieldName;
	}

	/**
	 * Returns the courses related to the question.
	 *
	 * @return the courses related to the question
	 */
	public String getCourses() {
	    return courses;
	}

	/**
	 * Sets the courses related to the question.
	 *
	 * @param courses the new courses related to the question
	 */
	public void setCourses(String courses) {
	    this.courses = courses;
	}

	/**
	 * Returns the text of the question.
	 *
	 * @return the text of the question
	 */
	public String getQuestionText() {
	    return questionText;
	}

	/**
	 * Sets the text of the question.
	 *
	 * @param questionText the new text of the question
	 */
	public void setQuestionText(String questionText) {
	    this.questionText = questionText;
	}

	/**
	 * Returns the number of the question.
	 *
	 * @return the number of the question
	 */
	public String getQuestionNumber() {
	    return questionNumber;
	}

	/**
	 * Sets the number of the question.
	 *
	 * @param questionNumber the new number of the question
	 */
	public void setQuestionNumber(String questionNumber) {
	    this.questionNumber = questionNumber;
	}

	/**
	 * Returns the name of the lecturer who created the question.
	 *
	 * @return the name of the lecturer who created the question
	 */
	public String getLecturerName() {
	    return lecturerName;
	}

	/**
	 * Sets the name of the lecturer who created the question.
	 *
	 * @param lecturerName the new name of the lecturer who created the question
	 */
	public void setLecturerName(String lecturerName) {
	    this.lecturerName = lecturerName;
	}

	/**
	 * Returns the ID of the lecturer who created the question.
	 *
	 * @return the ID of the lecturer who created the question
	 */
	public String getLecturerID() {
	    return lecturerID;
	}

	/**
	 * Sets the ID of the lecturer who created the question.
	 *
	 * @param lecturerID the new ID of the lecturer who created the question
	 */
	public void setLecturerID(String lecturerID) {
	    this.lecturerID = lecturerID;
	}

	/**
	 * Returns the first option of the question.
	 *
	 * @return the first option of the question
	 */
	public String getOption1() {
	    return option1;
	}

	/**
	 * Sets the first option of the question.
	 *
	 * @param option1 the new first option of the question
	 */
	public void setOption1(String option1) {
	    this.option1 = option1;
	}

	/**
	 * Returns the second option of the question.
	 *
	 * @return the second option of the question
	 */
	public String getOption2() {
	    return option2;
	}

	/**
	 * Sets the second option of the question.
	 *
	 * @param option2 the new second option of the question
	 */
	public void setOption2(String option2) {
	    this.option2 = option2;
	}

	/**
	 * Returns the third option of the question.
	 *
	 * @return the third option of the question
	 */
	public String getOption3() {
	    return option3;
	}

	/**
	 * Sets the third option of the question.
	 *
	 * @param option3 the new third option of the question
	 */
	public void setOption3(String option3) {
	    this.option3 = option3;
	}

	/**
	 * Returns the fourth option of the question.
	 *
	 * @return the fourth option of the question
	 */
	public String getOption4() {
	    return option4;
	}

	/**
	 * Sets the fourth option of the question.
	 *
	 * @param option4 the new fourth option of the question
	 */
	public void setOption4(String option4) {
	    this.option4 = option4;
	}

	/**
	 * Returns the correct answer of the question.
	 *
	 * @return the correct answer of the question
	 */
	public String getCorrectAnswer() {
	    return correctAnswer;
	}

	/**
	 * Sets the correct answer of the question.
	 *
	 * @param correctAnswer the new correct answer of the question
	 */
	public void setCorrectAnswer(String correctAnswer) {
	    this.correctAnswer = correctAnswer;
	}

	  /**
     * Resets all the fields of this question to null.
     */
	public void resetQuestion() {
		this.id = null;
		this.field = null;
		this.courses = null;
		this.questionText = null;
		this.questionNumber = null;
		this.lecturerName = null;
		this.lecturerID = null;
		this.option1 = null;
		this.option2 = null;
		this.option3 = null;
		this.option4 = null;
		this.correctAnswer = null;
	}

	 /**
     * Returns a string representation of this question.
     *
     * @return a string representation of this question
     */
	@Override
	public String toString() {
		return "Question [id=" + id + ", field=" + field + ", courses=" + courses + ", questionText=" + questionText
				+ ", questionNumber=" + questionNumber + ", lecturerName=" + lecturerName + ", lecturerID=" + lecturerID
				+ ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4
				+ ", corretAnswer=" + correctAnswer + "]";
	}

	 /**
     * Checks whether this question is equal to another object.
     * Two questions are considered equal if they have the same text.
     *
     * @param obj the object to compare with this question
     * @return true if the given object is a question with the same text as this question, false otherwise
     */
	@Override
	public boolean equals(Object obj) {
		if (((Question) obj).getQuestionText().equals(questionText))
			return true;
		return false;
	}

}
