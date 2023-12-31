package gui_controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
import entities.Message;
import entities.Session;
import entities.Student;
import entities.Time;
import entities.User;
import enums.Operations;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Controller class for the student start manual exam GUI. Handles actions and
 * events related to starting and returning manual exams.
 * 
 * @author Paz Fayer
 * @author Maayan Avittan
 * 
 */
public class StudentStartManualExamController extends ControllerInterface implements Initializable {
	public static StudentStartManualExamController studentStartManualExamController;
	public static Student student;
	ClientController client;
	@FXML
	private Button btnGet;
	@FXML
	private Button btnReturnExam;

	@FXML
	private Button btnExit;
	@FXML
	private Label lblDownloded;
	@FXML
	private Label lblUploaded;
	@FXML
	private Label lbltime;
	@FXML
	private Label lblRemTime;
	@FXML
	private Label studentNameLbl;
	@FXML
	private Text txtTimer;

	Timeline timeline;
	
	private String code;

	private String selectedExam;

	private int duration = 0;

	private int flagToRetExam = 1;

	/**
	 * Handles the "Get Exam" button click event. Retrieves the exam file and saves
	 * it to the user's computer.
	 *
	 * @param event The action event triggered by the button click.
	 * @throws IOException If an I/O error occurs while retrieving or saving the
	 *                     exam file.
	 */
	@FXML
	void getExam(ActionEvent event) throws IOException {
		String filePath = "images/ManualExam.docx"; 

		Stage primaryStage = new Stage();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
		VBox root = new VBox();
		String savePath = openSaveFileDialog(primaryStage);
		Scene scene = new Scene(root, 300, 200);
		primaryStage.setTitle("File Save Dialog Example");
		primaryStage.setScene(scene);
		primaryStage.show();

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

		if (inputStream != null) {
			btnReturnExam.setDisable(false);
			File saveDirectory = new File(savePath);
			if (!saveDirectory.exists()) {
				saveDirectory.mkdirs();
			}
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

			String destinationPath = savePath + File.separator + fileName;

			Path destination = new File(destinationPath).toPath();
			Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("Word file saved to: " + destinationPath);
			lblDownloded.setText("Word file saved in your computer, Good Luck!");
			lbltime.setText("Note, 1 minute was added to the exam duration in order to receive the file");
			btnGet.setDisable(true);
			timeMesurment(event);

		} else {
			System.out.println("Word file not found in the project.");
			lblDownloded.setText("ERRORE: Word file not found in the project");
		}
		primaryStage.close();

	}

	private static String convertTimeToString(int minutes) {
		int hours = minutes / 60;
		int remainingMinutes = minutes % 60;
		return String.format("%02d:%02d:%02d", hours, remainingMinutes, 0);
	}

	private void timeMesurment(ActionEvent event) throws IOException {

		Time time = new Time(convertTimeToString(duration + 1));// Translate the duration to String and add 1 minute.

		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			if (time.getCurrentTime().equals("00:00:01") || flagToRetExam == 0) {

				timeline.stop();
				lblRemTime.setText("time is up!");
				btnReturnExam.setDisable(true);
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
				Pane root1;
				try {
					root1 = loader.load(getClass().getResource("/gui/StudentExamIsOver.fxml").openStream());
					Scene scene1 = new Scene(root1);
					primaryStage1.setScene(scene1);
					primaryStage1.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
			time.oneSecondPassedMinus();
			txtTimer.setText(time.getCurrentTime());

			if (time.getCurrentTime().equals("00:01:00")) {
				lblRemTime.setText("Less than 1 minute left to finish");
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		txtTimer.setText("00:00:00");
	}

	/**
	 * Opens a file save dialog window and allows the user to select a file to save.
	 *
	 * @param stage The stage to which the file save dialog window is attached.
	 * @return The absolute path of the selected file, or an empty string if the
	 *         operation was canceled.
	 */
	private String openSaveFileDialog(Stage stage) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save File");

		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			System.out.println("File saved to: " + file.getAbsolutePath());
			return file.getAbsolutePath();
		} else {
			System.out.println("Save operation canceled.");
			return "";
		}

	}

	/**
	 * Handles the "Return Exam" button click event. Uploads the completed exam file
	 * to the server.
	 *
	 * @param event The action event triggered by the button click.
	 * @throws IOException If an I/O error occurs while uploading the exam file.
	 */
	@FXML
	void retExam(ActionEvent event) throws IOException {
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload exam File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Word Documents (.docx)", ".docx");
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showOpenDialog(primaryStage);

		if (selectedFile != null) {

			String fileName = selectedFile.getName();
			String savePath = "src";
			String projectDir = System.getProperty("user.dir");
			String destinationDir = projectDir + File.separator + savePath;
			File dir = new File(destinationDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String destinationPath = destinationDir + File.separator + fileName;
			File destinationFile = new File(destinationPath);

			int count = 1;
			String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
			String extension = fileName.substring(fileName.lastIndexOf('.'));
			while (destinationFile.exists()) {
				String incrementedName = baseName + "(" + count + ")" + extension;
				destinationPath = destinationDir + File.separator + incrementedName;
				destinationFile = new File(destinationPath);
				count++;
			}

			Files.copy(selectedFile.toPath(), destinationFile.toPath());

			System.out.println("Exam file uploaded and saved to: " + destinationFile.getAbsolutePath());
			flagToRetExam = 0;
			timeline.stop();

			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); 
			Stage primaryStage2 = new Stage();
			primaryStage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
			Pane root2 = loader.load(getClass().getResource("/gui/StudentExamIsOver.fxml").openStream());
			Scene scene2 = new Scene(root2);
			primaryStage2.setScene(scene2);
			primaryStage2.show();

		} else {
			System.out.println("No exam file selected.");
			lblUploaded.setText("ERRORE: No exam file selected.");
		}

	}

	/**
	 * Handles the "Exit" button click event. Closes the current window and
	 * navigates back to the main option form.
	 *
	 * @param event The action event triggered by the button click.
	 * @throws Exception If an exception occurs during the navigation process.
	 */
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {

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
		Pane root = loader.load(getClass().getResource("/gui/StudentMainOptionForm.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * Initializes the controller and sets up the initial state of the GUI.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object.
	 * @param resources The resources used to localize the root object.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		studentStartManualExamController = this;
		student = (Student) Session.getInstance().getCurrentUser();
		selectedExam = student.getChoosenCurrentExam();
		Message msg = new Message(Operations.GetDuration, selectedExam);
		ClientUI.client.accept(msg); // sending request to DB
		duration = ClientUI.client.getDuration();

		if (student != null) {
			StudentMainOptionPageController.student = student;
			this.studentNameLbl.setText(student.getFullName());
		}
		btnReturnExam.setDisable(true);
		this.code = student.getChoosenCurrentExam();

	}

	/**
	 * Sets the current user for the controller.
	 *
	 * @param user The current user to set.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void setUser(User user) {
		studentStartManualExamController.student = (Student) user;
		this.studentNameLbl.setText(student.getFullName());

	}

}