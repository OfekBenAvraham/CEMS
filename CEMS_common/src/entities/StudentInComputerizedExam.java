package entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The StudentInComputerizedExam class represents a computerized exam taken by a
 * student. It contains the questions, answers, student information, and other
 * relevant data for grading and analysis.
 *
 *
 * @author Maayan Avittan
 * @author Paz Fayer
 *
 */
public class StudentInComputerizedExam implements Serializable {

	private static final long serialVersionUID = 4563909971829114073L;
	private ArrayList<QuestionInExam> questionInExecutedExam; 
	private ArrayList<String> answers; 
	private Student studentInComputerizedExam;
	private int duration;
	private Double gradeFromAutoCheck = (double) 0;
	private boolean cheated = false;
	private int howLongTheExamLast;
	private int serialNumber;
	private String descriptionForExaminee;

    /**
     * Constructs a new StudentInComputerizedExam object with the specified questions, answers, and student.
     *
     * @param questionInExecutedExam   the questions in the executed exam
     * @param answers                  the student's answers
     * @param studentInComputerizedExam the student in the computerized exam
     */
	public StudentInComputerizedExam(ArrayList<QuestionInExam> questionInExecutedExam, ArrayList<String> answers,
			Student studentInComputerizedExam) {
		super();
		this.questionInExecutedExam = questionInExecutedExam;
		this.answers = answers;
		this.studentInComputerizedExam = studentInComputerizedExam;
	}


    /**
     * Constructs a new StudentInComputerizedExam object with default values.
     */
	public StudentInComputerizedExam() {
		super();
		this.questionInExecutedExam = null;
		this.answers = null;
		this.studentInComputerizedExam = null;
		this.duration = 0;
	}


	/**
	 * Retrieves the serialNumber of the exam.
	 *
	 * @return the serialNumber of the exam.
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets the serialNumber of the exam.
	 *
	 * @param serialNumber the duration of the exam.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}


	/**
	 * Retrieves the howLongTheExamLast of the exam.
	 *
	 * @return the howLongTheExamLast of the exam in minutes.
	 */
	public int getHowLongTheExamLast() {
		return howLongTheExamLast;
	}

	/**
	 * Sets the howLongTheExamLast of the exam.
	 *
	 * @param howLongTheExamLast the duration of the exam in minutes.
	 */
	public void setHowLongTheExamLast(int howLongTheExamLast) {
		this.howLongTheExamLast = howLongTheExamLast;
	}

	/**
	 * Checks if the student cheated during the exam.
	 *
	 * @return true if the student cheated, false otherwise.
	 */
	public boolean isCheated() {
		return cheated;
	}

	/**
	 * Sets the flag indicating if the student cheated during the exam.
	 *
	 * @param cheated true if the student cheated, false otherwise.
	 */
	public void setCheated(boolean cheated) {
		this.cheated = cheated;
	}

	/**
	 * Retrieves the grade obtained from the automatic checking of the exam.
	 *
	 * @return the grade obtained from the automatic checking of the exam.
	 */
	public Double getGradeFromAutoCheck() {
		return gradeFromAutoCheck;
	}

	/**
	 * Sets the grade obtained from the automatic checking of the exam.
	 *
	 * @param gradeFromAutoCheck the grade obtained from the automatic checking of
	 *                           the exam.
	 */
	public void setGradeFromAutoCheck(Double gradeFromAutoCheck) {
		this.gradeFromAutoCheck = gradeFromAutoCheck;
	}

	/**
	 * Retrieves the duration of the exam.
	 *
	 * @return the duration of the exam in minutes.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the exam.
	 *
	 * @param duration the duration of the exam in minutes.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Retrieves the list of questions in the executed exam.
	 *
	 * @return the list of questions in the executed exam.
	 */
	public ArrayList<QuestionInExam> getQuestionInExecutedExam() {
		return questionInExecutedExam;
	}

	/**
	 * Sets the list of questions in the executed exam.
	 *
	 * @param questionInExecutedExam the list of questions in the executed exam.
	 */
	public void setQuestionInExecutedExam(ArrayList<QuestionInExam> questionInExecutedExam) {
		this.questionInExecutedExam = questionInExecutedExam;
	}
	
	

	public String getDescriptionForExaminee() {
		return descriptionForExaminee;
	}

	/**
	 * Sets the list of DescriptionForExaminee in the executed exam.
	 *
	 * @param DescriptionForExaminee the String to the examine in the executed exam.
	 */
	public void setDescriptionForExaminee(String descriptionForExaminee) {
		this.descriptionForExaminee = descriptionForExaminee;
	}


	/**
	 * Retrieves the list of answers provided by the student.
	 *
	 * @return the list of answers provided by the student.
	 */
	public ArrayList<String> getAnswers() {
		return answers;
	}

	/**
	 * Sets the list of answers provided by the student.
	 *
	 * @param answers the list of answers provided by the student.
	 */
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	/**
	 * Retrieves the student who took the computerized exam.
	 *
	 * @return the student who took the computerized exam.
	 */
	public Student getStudentInComputerizedExam() {
		return studentInComputerizedExam;
	}

	/**
	 * Sets the student who took the computerized exam.
	 *
	 * @param studentInComputerizedExam the student who took the computerized exam.
	 */
	public void setStudentInComputerizedExam(Student studentInComputerizedExam) {
		this.studentInComputerizedExam = studentInComputerizedExam;
	}

	/**
	 * Checks the student's exam by comparing the answers with the correct answers
	 * and calculating the grade. If the number of answers is less than the number
	 * of questions, the grade is set to 0.
	 */
	public void CheckStudentExam() {
		if (answers.size() < questionInExecutedExam.size()) {
			return;
		}
		for (int i = 0; i < questionInExecutedExam.size(); i++) {
			if (questionInExecutedExam.get(i).getQuestion().getCorrectAnswer().equals(answers.get(i))) {
				gradeFromAutoCheck += questionInExecutedExam.get(i).getPoints();
			}
		}
	}

	/**
	 * Checks for cheating by comparing the student's responses with the correct
	 * answers. If the percentage of matching answers exceeds 20%, the cheating flag
	 * is set to true.
	 *
	 * @param response the list of responses provided by the student.
	 */
	public void CheckForCheating(ArrayList<String> response) {
		if (answers.isEmpty()) {
			return;
		}
		int numberOfQuestions = questionInExecutedExam.size();
		int cheatingCheck = 0;
		int index = 0;
		for (String responseString : response) {
			String[] otherStudentAns = responseString.split(",");

			for (String ans : otherStudentAns) {
				if (ans.equals(answers.get(index)) && !answers.get(index)
						.equals(questionInExecutedExam.get(index).getQuestion().getCorrectAnswer()))
					cheatingCheck++;
				index++;
			}
			index = 0;
			double factor = (double) cheatingCheck / numberOfQuestions;
			if (factor > 0.2) {
				cheated = true;
				return; 
			}
		}
	}
	@Override
	public String toString() {
		return "StudentInComputerizedExam [questionInExecutedExam=" + questionInExecutedExam + ", answers=" + answers
				+ ", studentInComputerizedExam=" + studentInComputerizedExam + "]";
	}

}