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
import entities.ExamsDisplayData;
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
 * The LecturerStatisticalExamReportController class is responsible for managing the statistical exam report view for a lecturer.
  * @author Almog Elbaz
 *  @author Guy Pariente
 */
public class LecturerStatisticalExamReportController implements Initializable {
    /**
     * The reference to the LecturerStatisticalExamReportController instance.
     */
    public static LecturerStatisticalExamReportController lecturerStatisticalExamReportController;

    /**
     * The data of the exam for which the statistics are displayed.
     */
    public static ExamsDisplayData exam;

	@FXML
	private Label lecturerNamelbl;
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

	private ObservableList<ExamStatistics> statisticList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lecturerNamelbl.setText(Session.getInstance().getCurrentUser().getFullName());
		if(exam == null)
			return;
		lblCourseName.setText(exam.getCourseName());
		initializeChart();
	}
	/**
	 * Handles the action when the back button is clicked.
	 *
	 * @param event The action event.
	 * @throws IOException if an I/O error occurs while loading the view.
	 */
	@FXML
	void BackFromStatisticalReports(ActionEvent event) throws IOException {
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/LecturerPickExamToSeeStats.fxml"));
		Scene scene = new Scene(root2);
		// Import the stage
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
	
	/**
	 * Initializes the bar chart by setting labels and adding data.
	 */
	@FXML
	private void initializeChart() {

		xAxis.setLabel("Grade Range");
		yAxis.setLabel("Number of Students");


		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Students");
		ArrayList<String> info = new ArrayList<String>();
		info.add(exam.getExamId());
		info.add(exam.getExecuteExamSerialNumber());
		Message msg = new Message(Operations.GetExamStatistics, info);
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
				gradeRangeCountMap.put(gradeRangeKey, gradeRangeCountMap.get(gradeRangeKey) + 1);
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
			lblError.setText("There are no statistics for this exam!");
		}
	}


}
