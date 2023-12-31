package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.Message;
import entities.Question;

/**
 * The CourseController class provides the methods for handling operations
 * related to courses.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */
public class CourseController {
	/**
	 * This method retrieves all questions related to a specific course.
	 *
	 * @param message Message object containing lecturer ID and course details.
	 * @return A list of Question objects associated with the specified course.
	 */
	public static ArrayList<Question> getQuestionsByCourse(Message message) {
		ArrayList<String> values = (ArrayList<String>) message.getObj();
		String query = "SELECT * FROM questions WHERE lecturerID = " + "'" + values.get(0) + "' AND ("
				+ "CONCAT(',', courses, ',') LIKE '%," + values.get(1) + ",%' OR courses = '" + values.get(1)
				+ "' OR Courses LIKE '" + values.get(1) + ",%' OR Courses LIKE '%," + values.get(1) + "');";
		ResultSet rs = Query.getQuery(query);
		ArrayList<Question> questions = new ArrayList<>();
		try {
			while (rs.next()) {
				String questionNumber = String.valueOf(rs.getInt("questionnumber"));
				Question question = new Question(rs.getString("id"), rs.getString("field"), rs.getString("fieldname"),
						rs.getString("courses"), rs.getString("questiontext"), questionNumber,
						rs.getString("lecturername"), rs.getString("lecturerid"), rs.getString("option1"),
						rs.getString("option2"), rs.getString("option3"), rs.getString("option4"),
						rs.getString("correctanswer"));
				questions.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}
	
}
