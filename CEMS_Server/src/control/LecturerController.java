package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import entities.Course;
import entities.Exam;
import entities.ExecutedExam;
import entities.ExamsDisplayData;
import entities.Field;
import entities.Message;
import entities.Question;
import enums.Answers;
import enums.ExamStatus;
import entities.QuestionInExam;
import enums.ExamType;
import entities.Request;

/**
 * The LecturerController class provides the methods for handling operations
 * related to lecturers..
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 *  @author Almog Elbaz
 *  @author Guy Pariente
 */
public class LecturerController {

	/**
	 * This method saves a new question into the database.
	 *
	 * @param receivedMessage Message object containing the Question details.
	 * @return String message indicating the result of the operation sending with
	 *         temp for the id and insertNewQuestion handle with it and change to a
	 *         new one.
	 */
	public static String saveNewQuestion(Message receivedMessage) {
		Question q = (Question) receivedMessage.getObj();
		String resMessage = Query.insertNewQuestion("temp", q.getField(), q.getFieldName(), q.getCourses(),
				q.getQuestionText(), q.getLecturerName(), q.getLecturerID(), q.getOption1(), q.getOption2(),
				q.getOption3(), q.getOption4(), q.getCorrectAnswer());
		return resMessage;
	}
	
	/**
	 * Updates the finalGrade and notes after a lecturer approves the exam.
	 *
	 * @param receivedMessage Message object containing the data.
	 */
	// After lecturer approves the exam  check, updates the finalGrade and notes.
	public static void updateExamFinalGradeAndNotes(Message receivedMessage) {
		ArrayList<String> data = (ArrayList<String>) receivedMessage.getObj();
		Query.updateFinalGradeAndNotesAfterApprovement(data);
	}
	
	/**
	 * Gets the student's answers by the exam ID.
	 *
	 * @param receivedMessage Message object containing the data.
	 * @return Message object containing the student's answers.
	 */
	public static Message getStudentAnswersByExamByID(Message receivedMessage)
	{
		Message res;
	    ResultSet rs;
	    ArrayList<String> optionsRes = new ArrayList<String>();
	    rs = Query.getStudentAnswerdByExamAndStudentID((ArrayList<String>) receivedMessage.getObj());
	    if (rs == null) {
	    	res = new Message(receivedMessage.getOperation());
	        return null;
	    }
	    try {
	        while(rs.next()) {
	            optionsRes.add(rs.getString("studentanswers"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    res = new Message(receivedMessage.getOperation(), optionsRes);
	    return res;
	}
	
	public static String getLecturerNameById(String lecturerID) {
		String fullName = null;
		ResultSet rs = Query.getQuery("SELECT * FROM lecturers WHERE id = '" + lecturerID + "'");
		try {
			rs.next();
			String fName = rs.getString("firstName");
			String lName = rs.getString("lastName");
			fullName = fName + " " + lName;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fullName;
	}
	
	/**
	 * Gets the details of a single exam to validate.
	 *
	 * @param receivedMessage Message object containing the data.
	 * @return Message object containing the student's grade.
	 */
	// Lecturer Gets the final form details - single exam to validate.
	// returns RS with the student grade.
	public static Message getSingleExamToValidate(Message receivedMessage)
	{
	    Message resMessage = null;
	    ResultSet rs;
	    rs = Query.getGradeByStudentId((ArrayList<String>) receivedMessage.getObj());
	    if (rs == null) {
	        resMessage = new Message(receivedMessage.getOperation(), Answers.NoStudentsToValidate);
	        return resMessage;
	    }
	    ArrayList<String> studentGrade = new ArrayList<String>();
	    try {
	        while (rs.next()) {
	            studentGrade.add(rs.getString("grade"));
	            studentGrade.add(rs.getString("cheated"));
	            System.out.println(studentGrade);
	            resMessage = new Message(receivedMessage.getOperation(), Answers.StudentsToValidateFound, studentGrade);
	        }
	        if(studentGrade.isEmpty()){
	            resMessage = new Message(receivedMessage.getOperation(), Answers.NoStudentsToValidate);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return resMessage;

	}

	/**
	 * Gets the student IDs that need to be validated for a specific exam.
	 *
	 * @param receivedMessage Message object containing the data.
	 * @return Message object containing the student IDs to validate.
	 */
	// Lecturer gets the student id's that need to be validated for the specific exam.
	public static Message getStudentsIdToValidate(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getStudentsIdRsToValidate((ArrayList<String>) receivedMessage.getObj());
		if (rs == null) {
			resMessage = new Message(receivedMessage.getOperation(), Answers.NoStudentsToValidate);
			return resMessage;
		}
		ArrayList<String> students = new ArrayList<String>();
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMessage.getOperation(), Answers.NoStudentsToValidate);
			} 
			else {
				rs.beforeFirst(); 
				while (rs.next()) {
					String id = rs.getString("userId"); 
					students.add(id);
				}
			}

			resMessage = new Message(receivedMessage.getOperation(), Answers.StudentsToValidateFound, students);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Gets the exams done by course.
	 *
	 * @param receivedMessage Message object containing the data.
	 * @return Message object containing the done exams.
	 */
	@SuppressWarnings("unchecked")
	public static Message getExamsDoneByCourse(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getDoneExamsByCourseAndLecturerId((ArrayList<String>) receivedMessage.getObj()); // lecturerId, courseCode, courseCode.
		if (rs == null) {
			resMessage = new Message(receivedMessage.getOperation(), Answers.ExamsToValidateNotFound);
			return resMessage;
		}
		ArrayList<ExamsDisplayData> examsInProgress = new ArrayList<ExamsDisplayData>();
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMessage.getOperation(), Answers.ExamsToValidateNotFound);
			} else {
				rs.beforeFirst();
				while (rs.next()) {
					String courseCode = rs.getString("courseCode");
					String courseName = rs.getString("courseName");
	                String executionCode = rs.getString("executionCode");
	                String examId = rs.getString("id");
	                String executeExamSerialNum = rs.getString("executeExamSerialNum");
	                Timestamp executionDateTime = rs.getTimestamp("executionDateTime");     
	                ExamsDisplayData exam = new ExamsDisplayData(courseCode,courseName,examId,executionCode,executionDateTime,executeExamSerialNum);
	                examsInProgress.add(exam);
				}
			}
			resMessage = new Message(receivedMessage.getOperation(), Answers.ExamsToValidateFound, examsInProgress);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	/**
	 * Adds a request to the database.
	 *
	 * @param receivedMessage Message object containing the request.
	 * @return boolean indicating if the operation was successful.
	 */
	public static boolean AddRequestInDb(Message receivedMessage) {
		Request req = (Request) receivedMessage.getObj();
		boolean ans  = Query.insertNewRequest(req);
		return ans;
	}
	
	/**
	 * Gets the exams in progress.
	 *
	 * @param receivedMessage Message object containing the data.
	 * @return Message object containing the exams in progress.
	 */
	// Lecturer Get Exams in progress.
	public static Message getExamsInProgress(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getExamsFoundByLecturerId(receivedMessage.getObj().toString());
		if (rs == null) {
			resMessage = new Message(receivedMessage.getOperation(), Answers.ExamsInProgressNotFound);
			return resMessage;
		}
		ArrayList<ExamsDisplayData> examsInProgress = new ArrayList<ExamsDisplayData>();
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMessage.getOperation(), Answers.ExamsInProgressNotFound);
			} else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
	                String executionCode = rs.getString("executionCode");
	                String examId = rs.getString("id");
	                Timestamp executionDateTime = rs.getTimestamp("executionDateTime");
	                String courseName = rs.getString("courseName");
	                int duration = rs.getInt("duration");
	                String executedExamSerialNumber = rs.getString("serialNumber");
	                ExamsDisplayData exam = new ExamsDisplayData(executionCode, executionDateTime, courseName, duration,examId,executedExamSerialNumber);
	                examsInProgress.add(exam);
				
				}
			}
			resMessage = new Message(receivedMessage.getOperation(), Answers.ExamsInProgressFound, examsInProgress);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	/**
	 * This method locks an exam, preventing further changes.
	 * @param receivedMessage A message containing the ID of the exam to be locked.
	 * @return A message indicating whether the operation was successful.
	 */
	public static Message LockExam(Message receivedMessage) {
		Message resMessage = null;
		@SuppressWarnings("unchecked")
		boolean res = Query.LecturerUpdateExamStatusForLocked((ArrayList<String>)receivedMessage.getObj());
		if (res == true)
			resMessage = new Message(receivedMessage.getOperation(), Answers.UpdatedLockedExamSucessfully, receivedMessage.getObj());
		else
			resMessage = new Message(receivedMessage.getOperation(), Answers.UpdatedLockedExamFailed, receivedMessage.getObj());
		return resMessage;
		
	}
	
	public static ArrayList<Exam> getExamRepositoryToActive(String receivedMessage) {
		String query = "SELECT * FROM cems.exams "
			    + "JOIN cems.executedexams ON exams.id = executedexams.examID "
			    + "WHERE exams.lecturerId = '" + receivedMessage + "' AND executedexams.status = '" + ExamStatus.locked.name() + "';";
		ResultSet rs = Query.getQuery(query);
		ArrayList<Exam> repository = new ArrayList<>();
		ArrayList<String> coursesCode = new ArrayList<>();
		ArrayList<String> fieldsCode = new ArrayList<>();
		try {
			while (rs.next()) {
				coursesCode.add(rs.getString("course"));
				fieldsCode.add(rs.getString("field"));

				Boolean locked = rs.getInt("locked") == 1 ? true : false;
				Exam exam = new Exam(rs.getString("id"), rs.getInt("duration"), rs.getString("descriptionForExaminee"),
						rs.getString("descriptionForLecturer"), rs.getString("authorName"), locked,
						ExamType.valueOf(rs.getString("executionType")), rs.getString("lecturerId"), null, null);
				exam.setSerialNumber(rs.getString("serialNumber"));
				exam.setExamCode(rs.getString("executionCode"));
				repository.add(exam);
			}
			for (int i = 0; i < repository.size(); i++) {
				Exam temp = repository.get(i);
				temp.setCourse(getCourseByCode(coursesCode.get(i)));
				temp.setField(getFieldByCode(fieldsCode.get(i)));

				repository.set(i, temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return repository;
	}

	/**
	 * This method retrieves all courses that a lecturer can validate an exam for.
	 * @param receivedMessage A message containing the lecturer's ID.
	 * @return A message containing the status of the operation and a list of courses that the lecturer can validate exams for.
	 */
	public static Message getCoursesToValidateExam(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getCoursesByLecturerId(receivedMessage.getObj().toString());
		if (rs == null) {
			resMessage = new Message(receivedMessage.getOperation(), Answers.CoursesForLecturerNotFound);
			return resMessage;
		}

		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			if (!rs.next()) {
				resMessage = new Message(receivedMessage.getOperation(), Answers.CoursesForLecturerNotFound);
			} else {
				rs.beforeFirst(); // move one back because rs.next moved one forward.
				while (rs.next()) {
					Course c = new Course(rs.getString(2), rs.getString(1)); // courseCode, courseName
					courses.add((c));
				}
			}

			resMessage = new Message(receivedMessage.getOperation(), Answers.CoursesByLectuerFound, courses);
			System.out.println(resMessage.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}
	
	
	/**
	 * Gets the exams for statistics.
	 *
	 * @param receivedMessage Message object containing the data.
	 * @return Message object containing the exams.
	 */
	public static Message getExamsForStats(Message receivedMessage) {
		Message resMessage = null;
		ResultSet rs;
		rs = Query.getExamsToStatsByLecturerId((ArrayList<String>) receivedMessage.getObj());
		if (rs == null) {
			return null;
		}

		ArrayList<ExamsDisplayData> exams = new ArrayList<ExamsDisplayData>();
		try {
				while (rs.next()) {
					ExamsDisplayData ex = new ExamsDisplayData(rs.getString("course"), rs.getString("courseName"), 
							rs.getString("id"), rs.getString("executionCode"), rs.getTimestamp("executionDateTime"), rs.getString("executeExamSerialNum"));
					exams.add((ex));
				}
			resMessage = new Message(receivedMessage.getOperation(), exams);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resMessage;
	}



	/**
	 * This method updates a specific question in the database.
	 *
	 * @param receivedMessage Message object containing question details.
	 */
	public static void updateQuestion(Message receivedMessage) {
		ArrayList<String> arr = (ArrayList<String>) receivedMessage.getObj();
		Query.updateQuestion(arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4), arr.get(5), arr.get(6));
	}

	/**
	 * This method deletes a specific question from the database.
	 *
	 * @param receivedMessage Message object containing question ID.
	 */
	public static String deleteQuestion(Message receivedMessage) {
		String examsWithTheQuestion = Query.deleteQuestion((String) receivedMessage.getObj());
		return examsWithTheQuestion;
	}

	/**
	 * This method deletes a specific exam from the database.
	 *
	 * @param receivedMessage Message object containing exam ID.
	 */
	public static void deleteExam(Message receivedMessage) {
		Query.deleteQuestionsInExam((String) receivedMessage.getObj());
		Query.deleteExam((String) receivedMessage.getObj());
	}

	/**
	 * This method retrieves all questions in the repository related to a specific
	 * lecturer.
	 *
	 * @param receivedMessage String containing lecturer ID.
	 * @return A list of Question objects.
	 */
	public static ArrayList<Question> getQuestionRepository(String receivedMessage) {
		String query = "SELECT * FROM questions WHERE lecturerID = " + receivedMessage;
		ResultSet rs = Query.getQuery(query);
		ArrayList<Question> repository = new ArrayList<>();
		try {
			while (rs.next()) {
				String questionNumber = String.valueOf(rs.getInt("questionnumber"));
				Question question = new Question(rs.getString("id"), rs.getString("field"), rs.getString("fieldname"),
						rs.getString("courses"), rs.getString("questiontext"), questionNumber,
						rs.getString("lecturername"), rs.getString("lecturerid"), rs.getString("option1"),
						rs.getString("option2"), rs.getString("option3"), rs.getString("option4"),
						rs.getString("correctanswer"));
				repository.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return repository;
	}

	/**
	 * This method retrieves all exams in the repository related to a specific
	 * lecturer.
	 *
	 * @param receivedMessage String containing lecturer ID.
	 * @return A list of Exam objects.
	 */
	public static ArrayList<Exam> getExamRepository(String receivedMessage) {
		String query = "SELECT * FROM exams WHERE lecturerId = " + receivedMessage;
		ResultSet rs = Query.getQuery(query);
		ArrayList<Exam> repository = new ArrayList<>();
		ArrayList<String> coursesCode = new ArrayList<>();
		ArrayList<String> fieldsCode = new ArrayList<>();
		try {
			while (rs.next()) {
				coursesCode.add(rs.getString("course"));
				fieldsCode.add(rs.getString("field"));

				Boolean locked = rs.getInt("locked") == 1 ? true : false;
				Exam exam = new Exam(rs.getString("id"), rs.getInt("duration"), rs.getString("descriptionForExaminee"),
						rs.getString("descriptionForLecturer"), rs.getString("authorName"), locked,
						ExamType.valueOf(rs.getString("executionType")), rs.getString("lecturerId"), null, null);
				repository.add(exam);
			}
			for (int i = 0; i < repository.size(); i++) {
				Exam temp = repository.get(i);
				temp.setCourse(getCourseByCode(coursesCode.get(i)));
				temp.setField(getFieldByCode(fieldsCode.get(i)));

				repository.set(i, temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return repository;
	}

	/**
	 * This private method retrieves a course by its code.
	 *
	 * @param courseCode String representing the course code.
	 * @return Course object for the corresponding course code.
	 */
	private static Course getCourseByCode(String courseCode) {
		Course course = null;
		ResultSet rs = Query.getQuery("SELECT * FROM courses WHERE courseCode = '" + courseCode + "'");
		try {
			rs.next();
			String cField = rs.getString("fieldCode");
			String cName = rs.getString("courseName");
			String cCode = rs.getString("courseCode");
			course = new Course(cName, cCode, cField);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	/**
	 * This private method retrieves a field by its code.
	 *
	 * @param fieldCode String representing the field code.
	 * @return Field object for the corresponding field code.
	 */
	private static Field getFieldByCode(String fieldCode) {
		Field field = null;
		ResultSet rs = Query.getQuery("SELECT * FROM fields WHERE fieldCode = '" + fieldCode + "'");
		try {
			rs.next();
			String fName = rs.getString("fieldName");
			String fCode = rs.getString("fieldCode");
			field = new Field(fName, fCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return field;
	}

	/**
	 * This method retrieves all questions in a specific exam by exam ID.
	 *
	 * @param examId String representing the exam ID.
	 * @return A list of QuestionInExam objects.
	 */
	public static ArrayList<QuestionInExam> getQuestionsInExamByID(String examId) {
		String query = "SELECT * FROM questioninexam WHERE examId = '" + examId +"';";
		ResultSet rs = Query.getQuery(query);
		ArrayList<QuestionInExam> questionsInExam = new ArrayList<>();
		ArrayList<String> questionsID = new ArrayList<>();
		try {
			while (rs.next()) {
				questionsID.add(rs.getString("questionId"));
				QuestionInExam questionInExam = new QuestionInExam(examId, null, rs.getDouble("points"));
				questionsInExam.add(questionInExam);
			}
			for (int i = 0; i < questionsInExam.size(); i++) {
				QuestionInExam temp = questionsInExam.get(i);
				temp.setQuestion(getQuestionById(questionsID.get(i)));
				questionsInExam.set(i, temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionsInExam;
	}

	/**
	 * This method retrieves all exams in the repository that are ready for execution.
	 * @param ExamRepository The list of all exams in the repository.
	 * @return A list of exams that are ready to be executed.
	 */
	public static ArrayList<Exam> getExamRepositoryReadyToExecute(ArrayList<Exam> ExamRepository) {
		ArrayList<Exam> examsReady = new ArrayList<>();
		try {
			for (Exam exam : ExamRepository) {
				String query = "SELECT * FROM executedexams WHERE examID = " + exam.getExamID();
				ResultSet rs = Query.getQuery(query);
				if (rs.next()) {
					examsReady.add(exam);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return examsReady;
	}

	/**
	 * This private method retrieves a question by its ID.
	 *
	 * @param questionId String representing the question ID.
	 * @return Question object for the corresponding question ID.
	 */
	private static Question getQuestionById(String questionId) {
		Question question = null;
		ResultSet rs = Query.getQuery("SELECT * FROM questions WHERE id = '" + questionId + "'");
		try {
			rs.next();
			String questionNumber = String.valueOf(rs.getInt("questionnumber"));
			question = new Question(rs.getString("id"), rs.getString("field"), rs.getString("fieldname"),
					rs.getString("courses"), rs.getString("questiontext"), questionNumber, rs.getString("lecturername"),
					rs.getString("lecturerid"), rs.getString("option1"), rs.getString("option2"),
					rs.getString("option3"), rs.getString("option4"), rs.getString("correctanswer"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question;
	}

	/**
	 * This method saves a new exam into the database.
	 *
	 * @param receivedMessage Message object containing the Exam details.
	 * @return String message indicating the result of the operation.
	 */
	public static String saveNewExam(Message receivedMessage) {
		Exam exam = (Exam) receivedMessage.getObj();
		String finalId = Query.insertNewExam("temp", exam.getDuration(), exam.getDescriptionForExaminee(),
				exam.getDescriptionForLecturer(), exam.getLecturerName(), exam.getLocked(), exam.getExamType(),
				exam.getLecturerId(), exam.getCourse(), exam.getField());
		return finalId;
	}

	/**
	 * This method saves questions in a specific exam into the database.
	 *
	 * @param receivedMessage Message object containing a list of QuestionInExam
	 *                        objects.
	 */
	public static void saveQuestionsInExam(Message receivedMessage) {
		ArrayList<QuestionInExam> questionsInExam = (ArrayList<QuestionInExam>) receivedMessage.getObj();
		for (QuestionInExam q : questionsInExam) {
			Query.insertNewQuestionInExam(q.getQuestion().getId(), q.getPoints(), q.getExamId());
		}
	}
	
	/**
	 * This method changes the status of an exam to active.
	 * @param receivedMessage The ID of the exam to be activated.
	 */
	public static void setActiveExam(Exam receivedMessage) {
		Query.updateExamStatus(receivedMessage);
	}

	/**
	 * This method inserts a new executed exam into the database.
	 *
	 * @param executedExam ExecutedExam object containing executed exam details.
	 */
	public static void insertExecutedExam(ExecutedExam executedExam) {
		Query.insertExecutedExam(executedExam.getExamID(), executedExam.getExecutionDate(),
				executedExam.getExecutionCode());
	}
}
