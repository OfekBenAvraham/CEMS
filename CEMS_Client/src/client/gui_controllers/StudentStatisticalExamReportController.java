package gui_controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.CourseDoneByStudent;
import entities.ExamStatistics;
import entities.Message;
import entities.Session;
import entities.Student;
import entities.User;
import enums.Operations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller for the StudentStatisticalExamReport.fxml view.
 *
 * @author Paz Fayer
 * @author Maayan Avittan
 */
public class StudentStatisticalExamReportController extends ControllerInterface implements Initializable {
	public static StudentStatisticalExamReportController studentStatisticalExamReportController;
	public static Student student;

	@FXML
	private Label lblStudentName;
	@FXML
	private Label lblCourseName;
	@FXML
	private Label lblGrade;
	@FXML
	private Label lblAverage;
	@FXML
	private Label lblMedian;
	@FXML
	private Label lblError;

	@FXML
	private Button btnBack;

	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;

	private CourseDoneByStudent selectedGrade = new CourseDoneByStudent();

	private ObservableList<ExamStatistics> statisticList = FXCollections.observableArrayList();

	/**
	 * Sets the selected grade for the student.
	 *
	 * @param selectedGrade The selected grade.
	 */
	public void setSelectedGrade(CourseDoneByStudent selectedGrade) {
		this.selectedGrade = selectedGrade;
	}

	/**
	 * This method is called after the FXML file has been loaded and can be used to
	 * initialize the scene. It sets the studentStatisticalExamReportController
	 * instance to this object and initializes various UI elements with data from
	 * the current user session and the selected grade. Additionally, it calls the
	 * `initializeChart` method to setup the chart.
	 * 
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		studentStatisticalExamReportController = this;
		student = (Student) Session.getInstance().getCurrentUser();
		this.selectedGrade = student.getChoosenCourse();
		System.out.println(selectedGrade.toString());
		if (student != null && selectedGrade != null) {
			StudentMainOptionPageController.student = student;
			lblStudentName.setText(student.getFullName());
			lblCourseName.setText(selectedGrade.getCourseName());
			lblGrade.setText(String.format("%.2f", selectedGrade.getGrade()));

			initializeChart();
		}

	}

	/**
	 * Initializes the bar chart by setting labels and adding data.
	 */
	@FXML
	private void initializeChart() {
		xAxis.setLabel("Grade Range");
		yAxis.setLabel("Number of Students");
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Students");
		selectedGrade.setStudent(student);
		Message msg = new Message(Operations.GetExamStatisticsStudent, selectedGrade);
		ClientUI.client.accept(msg);

		ObservableList<ExamStatistics> response = ClientUI.client.getStatistic();

		if (response != null && response.size() != 0) {
			statisticList.addAll(response);
			lblAverage.setText(statisticList.get(0).getAvg());
			lblMedian.setText(statisticList.get(0).getMed());

			Map<String, Integer> gradeRangeCountMap = new HashMap<>();

			for (int i = 0; i < 100; i += 10) {
				String gradeRangeKey = i + "-" + (i + 10);
				gradeRangeCountMap.put(gradeRangeKey, 0);
			}

			for (ExamStatistics statistics : statisticList) {
				double grade = Double.parseDouble(statistics.getGrade());
				int gradeRange = (int) (grade / 10) * 10;
				String gradeRangeKey = gradeRange + "-" + (gradeRange + 10);

				// Check if the grade range key exists in the map
				if (gradeRangeCountMap.containsKey(gradeRangeKey)) {
					// Increment the count for the grade range key
					gradeRangeCountMap.put(gradeRangeKey, gradeRangeCountMap.get(gradeRangeKey) + 1);
				} else {
					// Initialize the count for the grade range key to 1
					gradeRangeCountMap.put(gradeRangeKey, 1);
				}
			}

			List<XYChart.Data<String, Number>> sortedData = new ArrayList<>();

			for (Map.Entry<String, Integer> entry : gradeRangeCountMap.entrySet()) {
				String gradeRange = entry.getKey();
				int count = entry.getValue();
				sortedData.add(new XYChart.Data<>(gradeRange, count));
			}

			sortedData.sort(Comparator.comparingInt(data -> Integer.parseInt(data.getXValue().split("-")[0])));

			series.getData().addAll(sortedData);

			barChart.getData().add(series);
		} else {
			lblError.setText("There is no statistics for this exam!");
		}
	}

	@Override
	public void setUser(User user) {
		studentStatisticalExamReportController.student = (Student) user;
		this.lblStudentName.setText(student.getFullName());

	}

	/**
	 * Handles the action when the back button is clicked.
	 *
	 * @param event The action event.
	 * @throws IOException if an I/O error occurs while loading the view.
	 */
	@FXML
	void BackFromStatisticalReports(ActionEvent event) throws IOException {
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/StudentGradesViewPage.fxml"));
		Scene scene = new Scene(root2);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
		window.setScene(scene);
		window.show();
	}

}
