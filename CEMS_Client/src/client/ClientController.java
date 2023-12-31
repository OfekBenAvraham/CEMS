// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;

import java.util.ArrayList;

import common.ChatIF;
import entities.CourseDoneByStudent;
import entities.ExamStatistics;
import entities.ExecutedExam;
import entities.Request;
import entities.StudentInComputerizedExam;
import javafx.collections.ObservableList;

/**
 * This class overrides some of the methods defined in the AbstractClient
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ClientController implements ChatIF {

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	public static boolean awaitResponse = false;
	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	ChatClient client;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public ClientController(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection! Terminating client.");
			System.exit(1);
		}
	}

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	public void display(Object message) {
		System.out.println("> " + message.toString());
	}

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler.
	 */
	public void accept(Object str) {
		client.handleMessageFromClientUI(str);
	}

	/**
	 * Retrieves the list of courses done by the student along with the grades.
	 *
	 * @return ObservableList of CourseDoneByStudent objects representing the grades for each course.
	 */
	public ObservableList<CourseDoneByStudent> getGrades() {
		return this.client.getGrades();
	}
	/**
	 * Retrieves the executed exam.
	 *
	 * @return The ExecutedExam object representing the executed exam.
	 */
	public ExecutedExam getExecutedExam() {
		return this.client.getExecutedExam();
	}
	/**
	 * Retrieves the information of the student in the computerized exam.
	 *
	 * @return The StudentInComputerizedExam object representing the student in the computerized exam.
	 */
	public StudentInComputerizedExam getStudentInComputerizedExam() {
		return this.client.getStudentInComputerizedExam();
	}
	/**
	 * Checks if the computerized exam is locked.
	 *
	 * @return True if the computerized exam is locked, false otherwise.
	 */
	public boolean getIsComputerizedExamLocked() {
		return this.client.isComputerizedExamLocked;
	}
	/**
	 * Retrieves the amount of time changed for the student.
	 *
	 * @return The integer representing the amount of time changed.
	 */
	public int getTimeChange() {
		return this.client.getTimeChange();
	}
	/**
	 * Retrieves the answers provided by other students in the exam.
	 *
	 * @return ArrayList of Strings representing the answers from other students.
	 */
	public ArrayList<String> getOtherStudentsAnswers() {
		return this.client.otherStudentsAnswers;
	}
	/**
	 * Checks if the student's answers have been reviewed.
	 *
	 * @return True if the answers have been reviewed, false otherwise.
	 */
	public boolean getCheckForAnswers() {
		return this.client.checkForAnswers;
	}
	/**
	 * Retrieves the duration of the exam.
	 *
	 * @return The integer representing the duration of the exam.
	 */
	public int getDuration() {
		return this.client.getDuration();
	}
	/**
	 * Retrieves the list of statistics for each exam.
	 *
	 * @return ObservableList of ExamStatistics objects representing the statistics for each exam.
	 */
	public ObservableList<ExamStatistics> getStatistic() {
		return this.client.getStatistic();
	}
}
