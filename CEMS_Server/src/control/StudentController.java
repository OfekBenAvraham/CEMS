package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import entities.CourseDoneByStudent;
import entities.ExamStatistics;
import entities.ExecutedExam;
import entities.Message;
import entities.Question;
import entities.QuestionInExam;
import entities.StudentInComputerizedExam;
import enums.Answers;
import enums.ExamStatus;
import enums.ExamType;

/**
 * 
 * The StudentController class provides methods for managing student-related operations.
 * 
 * @author Maayan Avittan
 * @author Paz Fayer
 * 
*/
public class StudentController {
	/**
	 * Retrieves the grades of a student.
	 *
	 * @param receivedMessage The received message.
	 * @param code            The student code.
	 * @return A message containing the operation result and the list of student
	 *         grades.
	 */
	public static Message studentViewGrades(Message receivedMessage, Object code) {
		Message resMessage = null;
		String sCode = (String) code; // casting to sent the query as string
		ResultSet rs = Query.getStudentGrades(sCode);

		if (rs == null) {
			resMessage = new Message(receivedMessage.getOperation(), Answers.NoStudentGradesFound);
			return resMessage;
		}
		ArrayList<CourseDoneByStudent> gradesList = new ArrayList<CourseDoneByStudent>();
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMessage.getOperation(), Answers.NoStudentGradesFound);
			} else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					// Extract the data from the result set
					Double grade = rs.getDouble("finalGrade");
					String courseName = rs.getString("courseName");
					String examID = rs.getString("executedExamId");
					String note = rs.getString("note");

					// Create an instance of MyClass and populate it with the retrieved values
					CourseDoneByStudent gradesData = new CourseDoneByStudent(grade, courseName, examID, note);

					// Add the instance to the collection
					gradesList.add(gradesData);

				}
			}
			resMessage = new Message(receivedMessage.getOperation(), Answers.GradesListFound, gradesList);
			// System.out.println(resMessage.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}

	/**
	 * Retrieves the exam of a student.
	 *
	 * @param receivedMessage The received message.
	 * @param code            The student code.
	 * @return A message containing the operation result and the executed exam.
	 */
	public static Message studentGetExam(Message receivedMessage, Object code) {
		Message resMessage = null;
		String sCode = (String) code; // casting to sent the query as string
		ResultSet rs = Query.getStudentExam(sCode);
		
		if (rs == null) {
			resMessage = new Message(receivedMessage.getOperation(), Answers.NoExamFound);
			return resMessage;
		}
		
		ExecutedExam executedExam = null;
		
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMessage.getOperation(), Answers.NoExamFound);
			}
			else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					String typeOfExam = rs.getString("executionType");
					if (typeOfExam.equalsIgnoreCase("manual")) {
						executedExam = new ExecutedExam(ExamType.MANUAL);
					}
					if (typeOfExam.equalsIgnoreCase("computerized")) {
						executedExam = new ExecutedExam(ExamType.COMPUTERIZED);
					}
					resMessage = new Message(receivedMessage.getOperation(), Answers.ExamFound, executedExam);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Starts a computerized exam for a student.
	 *
	 * @param receivedMessage The received message.
	 * @param code            The student code.
	 * @return A message containing the operation result and the student in computerized exam data.
	 */
	public static Message studentStartComputerizedExam(Message receivedMessage, Object code) {
		Message resMessage = null;
		String sCode = (String) code; // casting to sent the query as string
		ResultSet rs = Query.studentstartComputerizedExam(sCode); //change query to return the points and correct answer

		if (rs == null) {
			resMessage = new Message(receivedMessage.getOperation(), Answers.NoQuestionsAvailable);
			return resMessage;
		}
		
		StudentInComputerizedExam studentInComputerizedExam = new StudentInComputerizedExam();
		ArrayList<QuestionInExam> questions = new ArrayList<>();
		int duration = 0;
		int serialNumber = 0;
		String descriptionForExaminee = null;
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMessage.getOperation(), Answers.NoQuestionsAvailable);
			}
			else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					
	                // Extract the data from the result set
	                String questiontext = rs.getString("questiontext");
	                String option1 = rs.getString("option1");
	                String option2 = rs.getString("option2");
	                String option3 = rs.getString("option3");
	                String option4 = rs.getString("option4");
	                duration = rs.getInt("duration");
	                Double points = rs.getDouble("points");
	                String correctAnswer = rs.getString("correctanswer");
	                String examID = rs.getString("examID");
	                serialNumber = rs.getInt("serialNumber");
	                descriptionForExaminee = rs.getString("descriptionForExaminee");
	                
	                
	                // Create an instance of MyClass and populate it with the retrieved values
	                Question question = new Question(questiontext, option1, option2, option3, option4, correctAnswer);
	                QuestionInExam questionInExam = new QuestionInExam(examID, question, points);
	                
	                // Add the instance to the collection
	                questions.add(questionInExam);
				}
				studentInComputerizedExam.setQuestionInExecutedExam(questions);
				studentInComputerizedExam.setDuration(duration);
				studentInComputerizedExam.setSerialNumber(serialNumber);
				studentInComputerizedExam.setDescriptionForExaminee(descriptionForExaminee);
			}
			resMessage = new Message(receivedMessage.getOperation(), Answers.QuestionsFoundForStudentInComputerizedExam, studentInComputerizedExam);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Checks if a computerized exam is locked for a student.
	 *
	 * @param receivedMessage The received message.
	 * @param code            The student code.
	 * @return A message containing the operation result and the lock status of the exam.
	 */
	public static Message StudentComputerizedExamLockCheck(Message recievedMsg, Object code) {
		Message resMessage = null;
		String sCode = (String) code; // casting to sent the query as string
		ResultSet rs = Query.LockCheck(sCode);
		boolean check = false;
		try {
			if (!rs.next()) {
				resMessage = new Message(recievedMsg.getOperation(), Answers.NoSpecificData);
			}
			else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
	                // Extract the data from the result set
	                String status = rs.getString("status");
	                if (status.equalsIgnoreCase(ExamStatus.active.name())) {
	                	check = false;
	                }
	                if (status.equalsIgnoreCase(ExamStatus.locked.name()))
	                	check = true;
				}
			}
			resMessage = new Message(recievedMsg.getOperation(), Answers.StatusUpdated, check);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resMessage;
	}

	/**
	 * Checks if the time of a computerized exam has changed for a student.
	 *
	 * @param receivedMessage The received message.
	 * @param code            The student code.
	 * @return A message containing the operation result and the time change of the exam.
	 */
	public static Message StudentComputerizedExamTimeChangeCheck(Message recievedMsg, Object code) {
		Message resMessage = null;
		String sCode = (String) code; // casting to sent the query as string
		ResultSet rs = Query.TimeChangeCheck(sCode);
		int timeChange = 0;

		try {
			if (!rs.next()) {
				resMessage = new Message(recievedMsg.getOperation(), Answers.NoSpecificData);
			}
			else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
	                // Extract the data from the result set
					timeChange = rs.getInt("timeChange");
				}
			}
			resMessage = new Message(recievedMsg.getOperation(), Answers.TimeUpdated, timeChange);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resMessage;
	}

	/**
	 * Finishes a computerized exam for a student and saves their answers in the database.
	 *
	 * @param receivedMessage        The received message.
	 * @param studentInComputerizedExam The student in computerized exam data.
	 * @return A message containing the operation result.
	 */
	public static Message StudentFinishComputerizedExam(Message recievedMsg, Object studentInComputerizedExam) {
		Message resMessage = null;
		StudentInComputerizedExam sICE = (StudentInComputerizedExam) studentInComputerizedExam;
		String studentAnswers = "";
		for (String ans : sICE.getAnswers()) {
			if (studentAnswers.equals("")) {
				studentAnswers += ans;
			}
			else {
				studentAnswers = studentAnswers + "," + ans;
			}
		}
		Query.SendingStudentAnswersToDB(sICE, studentAnswers);
		resMessage = new Message(recievedMsg.getOperation(), Answers.StudentAnswersSavedInDB);
		return resMessage;
	}

	/**
	 * Checks if a student has cheated in a computerized exam.
	 *
	 * @param receivedMessage        The received message.
	 * @param studentInComputerizedExam The student in computerized exam data.
	 * @return A message containing the operation result and the answers of other students who took the exam.
	 */
	public static Message StudentCheckIfCheated(Message recievedMsg, Object studentInComputerizedExam) {
		Message resMessage = null;
		StudentInComputerizedExam sICE = (StudentInComputerizedExam) studentInComputerizedExam;
		String examID = sICE.getQuestionInExecutedExam().get(0).getExamId();
		String studentID = sICE.getStudentInComputerizedExam().getUserID();
		ResultSet rs = Query.CheckIfCheated(examID, studentID);
		
		ArrayList<String> otherStudentsAnswers = new ArrayList<>();
		try {
			if (!rs.next()) {
				resMessage = new Message(recievedMsg.getOperation(), Answers.NoStudentDidThisExamYet);
			}
			else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
	                // Extract the data from the result set
					String otherAnswers = rs.getString("studentanswers");
					
					otherStudentsAnswers.add(otherAnswers);
				}
			}
			resMessage = new Message(recievedMsg.getOperation(), Answers.ThereAreStudentsWhoTookThisExam, otherStudentsAnswers);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}

	/**
	 * Checks if a student has completed the exam.
	 * 
	 * @param recievedMsg The received message object.
	 * @param studentArryaListCodeAndID The student's array list containing the exam code and student ID.
	 * @return The message indicating if the student has answered the exam.
	 */
	@SuppressWarnings("unchecked") // not check if the object is instance of ArrayList
	public static Message StudentCheckIfDidTheExam(Message recievedMsg, Object studentArryaListCodeAndID) {
		Message resMessage = null;
		ArrayList<String> sSCE = (ArrayList<String>) studentArryaListCodeAndID;
		String examCode = sSCE.get(0);
		String studentID = sSCE.get(1);
		ResultSet rs = Query.CheckIfDidExam(examCode, studentID);
		boolean hasAns = false;
		try {
			if (!rs.next()) {
				resMessage = new Message(recievedMsg.getOperation(), Answers.NoSpecificData);
			}
			else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
	                // Extract the data from the result set
					String data = rs.getString("answerStatus");
					if (data.equalsIgnoreCase("No answer")) {
						hasAns = false;
					}
					else if(data.equalsIgnoreCase("Has answer")) {
						hasAns = true;
					}
			}
		}
			resMessage = new Message(recievedMsg.getOperation(), Answers.StudentCheckIfThereAreAnswers, hasAns);
			}catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Retrieves the statistics for a student.
	 *
	 * @param receivedMsg   The received message.
	 * @param selectedGrade The student grade object.
	 * @return A message containing the operation result and the exam statistics for
	 *         the student.
	 */
	public static Message StudentGetStatistics(Message receivedMsg, Object selectedGrade) {
		Message resMessage = null;
		CourseDoneByStudent ob = (CourseDoneByStudent) selectedGrade;
		ResultSet rs = Query.getStatistics(ob);
		ArrayList<ExamStatistics> statisticsList = new ArrayList<>();
		ArrayList<Double> grades = new ArrayList<>();

		try {
			if (!rs.next()) {

				resMessage = new Message(receivedMsg.getOperation(), Answers.NoSpecificData);
			} else {

				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					// Extract the data from the result set

					String grade = rs.getString("finalGrade");

					// Create an ExamStatistics object and add it to the list
					ExamStatistics statistics = new ExamStatistics("0", "0", grade);

					statisticsList.add(statistics);
					grades.add(Double.parseDouble(grade));

				}

				String avg = calculateAverage(grades);
				String med = calculateMedian(grades);

				if (!statisticsList.isEmpty()) {
					ExamStatistics statistics = statisticsList.get(0);
					statistics.setAvg(avg);
					statistics.setMed(med);
				}

				resMessage = new Message(receivedMsg.getOperation(), Answers.GetStatisticsFromDB, statisticsList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resMessage;
	}
	
	public static Message StudentGetStatisticsForLecturer(Message receivedMsg, Object selectedGrade) {
		// examId, serialNumberOfExecutedExam
		Message resMessage = null;
		ResultSet rs = Query.getStatisticsForLecturer(receivedMsg.getObj());
		ArrayList<ExamStatistics> statisticsList = new ArrayList<>();
		ArrayList<Double> grades = new ArrayList<>();

		try {
			if (!rs.next()) {

				resMessage = new Message(receivedMsg.getOperation(), Answers.NoSpecificData);
			} else {

				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					// Extract the data from the result set

					String grade = rs.getString("finalGrade");

					// Create an ExamStatistics object and add it to the list
					ExamStatistics statistics = new ExamStatistics("0", "0", grade);

					statisticsList.add(statistics);
					grades.add(Double.parseDouble(grade));

				}

				String avg = calculateAverage(grades);
				String med = calculateMedian(grades);

				if (!statisticsList.isEmpty()) {
					ExamStatistics statistics = statisticsList.get(0);
					statistics.setAvg(avg);
					statistics.setMed(med);
				}

				resMessage = new Message(receivedMsg.getOperation(), Answers.GetStatisticsFromDB, statisticsList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resMessage;
	}


	/**
	 * Calculates the average of a list of numbers.
	 * 
	 * @param numbers an ArrayList of Doubles for which the average is to be calculated.
	 * @return a String representation of the average of the numbers, formatted to two decimal places.
	 */
	private static String calculateAverage(ArrayList<Double> numbers) {
		double sum = 0.0;
		for (Double number : numbers) {
			sum += number;
		}
		double average = sum / numbers.size();
		return String.format("%.2f", average);
	}

	/**
	 * Calculates the median of a list of numbers. 
	 * 
	 * @param numbers an ArrayList of Doubles for which the median is to be calculated.
	 * @return a String representation of the median of the numbers, formatted to two decimal places.
	 * If the size of the list is even, the average of the two middle numbers is returned.
	 */
	private static String calculateMedian(ArrayList<Double> numbers) {
		Collections.sort(numbers);

		int size = numbers.size();
		int middle = size / 2;

		double median;
		if (size % 2 == 0) {
			double value1 = numbers.get(middle - 1);
			double value2 = numbers.get(middle);
			median = (value1 + value2) / 2.0;
		} else {
			median = numbers.get(middle);
		}

		return String.format("%.2f", median);
	}

	/**
	 * Retrieves the duration of an exam for a student.
	 *
	 * @param receivedMsg The received message.
	 * @param code        The student code.
	 * @return A message containing the operation result and the duration of the
	 *         exam.
	 */
	public static Message StudentGetDuration(Message receivedMsg, Object code) {
		Message resMessage = null;
		String sCode = (String) code; // casting to send the query as a string
		ResultSet rs = Query.getDurationTime(sCode);
		int duration = 0; // Initialize duration with a default value
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMsg.getOperation(), Answers.NoSpecificData);
			} else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					// Extract the data from the result set
					duration = rs.getInt("duration");
				}
			}
			resMessage = new Message(receivedMsg.getOperation(), Answers.GetDurationFromDB, duration);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resMessage;
	}

	
}

	
