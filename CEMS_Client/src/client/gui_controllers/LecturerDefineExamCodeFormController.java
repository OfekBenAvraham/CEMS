package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Exam;
import entities.ExecutedExam;
import entities.Message;
import entities.Session;
import enums.Operations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Controller for managing the Lecturer Define Exam Code form. This form allows
 * a lecturer to define an exam code, including setting a date and time for the
 * exam. The lecturer inputs a 6-digit code, chooses a date from a DatePicker,
 * and sets the time with two Spinners for hour and minute.
 * 
 *  @author Ofek Ben Avraham
 *	@author Rotem Porat 
 */
public class LecturerDefineExamCodeFormController implements Initializable {
	public static Exam exam;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnSubmit;

	@FXML
	private TextField code;

	@FXML
	private Text textError;

	@FXML
	private Label examId;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Spinner<Integer> hourSpinner;

	@FXML
	private Spinner<Integer> minuteSpinner;

	@FXML
	private Label lecturerNameLbl;

	/**
	 * Initializes the form components with default values.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNameLbl.setText(Session.getInstance().getCurrentUser().getFullName());
		examId.setText(exam.getExamID());

		SpinnerValueFactory<Integer> hourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
		hourSpinner.setValueFactory(hourFactory);
		SpinnerValueFactory<Integer> minuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
		minuteSpinner.setValueFactory(minuteFactory);
		StringConverter<Integer> leadingZeroConverter = new LeadingZeroConverter();
		hourSpinner.getValueFactory().setConverter(leadingZeroConverter);
		minuteSpinner.getValueFactory().setConverter(leadingZeroConverter);
	}

	/**
	 * This is a helper class used to format the display of the hour and minute
	 * Spinners. It ensures that values are displayed with leading zeros when needed
	 * (e.g., '02' instead of '2').
	 */
	private static class LeadingZeroConverter extends StringConverter<Integer> {
		/**
		 * Converts the Integer value to a String. If the integer is less than 10, a
		 * leading zero will be added to the String representation.
		 * 
		 * @param value The integer value to be converted to a string.
		 * @return A string representation of the integer value with a leading zero if
		 *         the value is less than 10.
		 */
		@Override
		public String toString(Integer value) {
			return String.format("%02d", value);
		}

		/**
		 * Converts the String to an Integer. This method is not really needed in the
		 * current context but is required by the StringConverter interface.
		 * 
		 * @param string The string to be converted to an integer.
		 * @return The integer representation of the string.
		 */
		@Override
		public Integer fromString(String string) {
			return Integer.parseInt(string);
		}
	}

	/**
	 * Handles the event when the Add Exam Code button is clicked.
	 * 
	 * Validates the form fields and creates a new ExecutedExam to be added to the
	 * system.
	 *
	 * @param event The event triggered by clicking the Add Exam Code button.
	 * @throws IOException If an error occurs during I/O operations.
	 */
	@FXML
	void addExamCode(ActionEvent event) throws IOException {
		String codeText = code.getText();
		if (codeText.length() != 4) {
			textError.setText("Code must be 4 chars");
			return;
		}

		if (!codeText.matches("[a-zA-Z0-9]*")) {
			textError.setText("Code must be 4 characters long and consist of only digits or letters.");
			return;
		}

		LocalDate selectedDate = datePicker.getValue();
		if (selectedDate == null) {
			textError.setText("Please select a date");
			return;
		}

		int selectedHour = hourSpinner.getValue();
		int selectedMinute = minuteSpinner.getValue();
		if (selectedHour == 0 && selectedMinute == 0) {
			textError.setText("Please select a time");
			return;
		}
		LocalDateTime dateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedHour, selectedMinute));
		ExecutedExam executedExam = new ExecutedExam(exam.getExamID(), codeText, dateTime);
		Message message = new Message(Operations.DefineCode, executedExam);
		ClientUI.client.accept(message);
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage1 = new Stage();
		primaryStage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				String userId = Session.getInstance().getCurrentUser().getUserID();
				Message msg = new Message(Operations.UserLogout, userId);
				ClientUI.client.accept(msg);
				Session.getInstance().logout();
				Message sendMessage = new Message(Operations.Disconect);
				ClientUI.client.accept(sendMessage);
			}
		});
		AnchorPane root = loader
				.load(getClass().getResource("/gui/LecturerPersonalExamRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Handles the event when the Exit button is clicked.
	 * 
	 * Hides the current form and displays the LecturerPersonalExamRepositoryForm.
	 *
	 * @param event The event triggered by clicking the Exit button.
	 * @throws IOException If an error occurs during I/O operations.
	 */
	@FXML
	void getExitBtn(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage1 = new Stage();
		primaryStage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				String userId = Session.getInstance().getCurrentUser().getUserID();
				Message msg = new Message(Operations.UserLogout, userId);
				ClientUI.client.accept(msg);
				Session.getInstance().logout();
				Message sendMessage = new Message(Operations.Disconect);
				ClientUI.client.accept(sendMessage);
			}
		});
		AnchorPane root = loader
				.load(getClass().getResource("/gui/LecturerPersonalExamRepositoryForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

}
