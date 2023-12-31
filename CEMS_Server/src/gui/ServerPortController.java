package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import control.Query;
import entities.Client;
import entities.Course;
import entities.Field;
import entities.LecturerInCourse;
import entities.StudentInCourse;
import entities.UserToDB;
import enums.ClientConnectionStatus;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import ocsf.server.ConnectionToClient;
import server.EchoServer;

/**
 * Controls the server port for handling client connections and data
 * transactions. The class allows initiation of the server and handles various
 * actions like connecting the server to the database, adding a client to the
 * client list, fetching the local host address, and loading data into the
 * database from CSV file. It provides a user interface for the server to
 * configure and monitor the connections.
 * 
 * 
 * @author Ofek Ben Avraham
 * @author Rotem Porat
 * @author Maayan Avittan
 * @author Guy Pariente
 * @author Almog Elbaz
 */
public class ServerPortController {
	public static ServerPortController serverPortController;
	public static Boolean serverTryToConnectDB = true;
	// Instance variables **********************************************

	/**
	 * static instance for ServerController instance. Will be create only once for
	 * each run.
	 */
	public static EchoServer server;
	@FXML
	private Button btnConnectDB;
	@FXML
	private Button btnExit;

	@FXML
	private Button btnAddData;

	@FXML
	private TextField txtPort;
	@FXML
	private TextField txtDBPassword;
	@FXML
	private TextField txtIP;

	@FXML
	private Label lblErrorPort;

	@FXML
	private Label lblSuccess;

	@FXML
	private ToggleGroup toggleGroup;

	@FXML
	private RadioButton studentsInCourseRB;

	@FXML
	private RadioButton lecturersInCourseRB;

	@FXML
	private RadioButton coursesRB;

	@FXML
	private RadioButton fieldsRB;

	@FXML
	private RadioButton usersRB;

//	@FXML
//	private Label textConnect;

	@FXML
	private TableView<Client> table;
	@FXML
	private TableColumn<Client, String> columnIP;
	@FXML
	private TableColumn<Client, String> columnHost;
	@FXML
	private TableColumn<Client, ClientConnectionStatus> columnStatus;

	private ObservableList<Client> clientList = FXCollections.observableArrayList();

	/**
	 * The server's first window and this window's first method. load and show this
	 * window.
	 *
	 * @param primaryStage The stage for window's scene.
	 */

	@FXML
	public void initialize() {
		serverPortController = this;
		columnIP.setCellValueFactory(new PropertyValueFactory<>("ip"));
		columnHost.setCellValueFactory(new PropertyValueFactory<>("hostName"));
		columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		table.setItems(clientList);
		btnAddData.setDisable(true);
		toggleGroup = new ToggleGroup();
		studentsInCourseRB.setToggleGroup(toggleGroup);
		lecturersInCourseRB.setToggleGroup(toggleGroup);
		coursesRB.setToggleGroup(toggleGroup);
		fieldsRB.setToggleGroup(toggleGroup);
		usersRB.setToggleGroup(toggleGroup);
		try {
			txtIP.setText(getLocalHost());
			txtPort.setText("5555");
			txtDBPassword.setText("Aa123456");
			txtIP.setEditable(false);
		} catch (Exception e) {
			display(e.getMessage());
		}
	}

	@FXML
	private void handleOptionSelected(ActionEvent event) {
		RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
		if (selectedRadioButton != null) {
			btnAddData.setDisable(false); // Enable the btnAddData button
		}
	}

	/**
	 * Adds a client to the clientList.
	 *
	 * @param client the client object to add
	 */
	public void addClient(Client client) {
		Platform.runLater(() -> clientList.add(client));
	}

	/**
	 * Initiates the starting process of the server.
	 *
	 * @param primaryStage The main stage
	 * @throws Exception when there's any error during the process
	 */
	public void start(Stage primaryStage) throws Exception {
		try {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setTitle("Server Port");
				primaryStage.setScene(scene);

				primaryStage.show();

			} catch (Exception e) {
				System.err.println("Exception in start method: " + e.getMessage());
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println("Exception in start method: " + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Gets the local host address.
	 *
	 * @return local host IP address
	 * @throws Exception when unable to fetch local host
	 */
	public String getLocalHost() throws Exception {
		String localHost = InetAddress.getLocalHost().getHostAddress().toString();
		return localHost;
	}

	/**
	 * @return the port number user puts in
	 */
	private String getport() {
		return txtPort.getText();
	}

	/**
	 * Fetches data from a CSV file and loads it into the database.
	 *
	 * @param event The action event from the button click.
	 * @throws IOException when there's any error reading the file
	 */
	@FXML
	void getDataBtn(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		Stage stage = (Stage) btnAddData.getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(stage);
		UserToDB user;
		if (selectedFile != null) {
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
			String id = selectedRadioButton.getId();
			switch (id) {
			case "lecturersInCourseRB":
				LecturerInCourse lecturerInCourse;
				ArrayList<LecturerInCourse> lecturersInCourse = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
					String line;
					// Skip the header line
					if ((line = br.readLine()) != null) {
					}
					while ((line = br.readLine()) != null) {
						String[] values = line.split(",");
						String lecturerId = values[0];
						String courseCode = values[1];
						lecturerInCourse = new LecturerInCourse(lecturerId, courseCode);
						lecturersInCourse.add(lecturerInCourse);
					}
					for (LecturerInCourse l : lecturersInCourse) {
						Query.insertLecturerInCourse(l.getLecturerId(), l.getCourseCode());
					}
					lblErrorPort.setText("");
					lblSuccess.setText("Success with adding lecturers data");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "usersRB":
				ArrayList<UserToDB> users = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
					String line;
					// Skip the header line
					if ((line = br.readLine()) != null) {
						// Ignore
					}
					while ((line = br.readLine()) != null) {
						// Split on comma
						String[] values = line.split(",");
						String firstName = values[0];
						String lastName = values[1];
						String userID = values[2];
						String email = values[3];
						String userName = values[4];
						String password = values[5];
						String isLecturer = values[6];
						String isStudent = values[7];
						String phoneNumber = values[8];
						String isLoggedIn = values[9];
						String isHeadOfDepartment = values[10];
						user = new UserToDB(firstName, lastName, userID, email, userName, password, phoneNumber,
								isLecturer, isStudent, isLoggedIn, isHeadOfDepartment);
						users.add(user);
					}
					for (UserToDB member : users) {
						Query.insertUsers(member.getFirstName(), member.getLastName(), member.getUserID(),
								member.getEmail(), member.getUserName(), member.getPassword(), member.getPhoneNumber(),
								member.getIslecturer(), member.getIsstudent(), member.getIsLogginIn(),
								member.getIsheadofdepartment());
						if (Boolean.parseBoolean(member.getIsheadofdepartment())) {
							Query.insertHeadOfDepartment(member.getUserID(), member.getFirstName(),
									member.getLastName());
						}
						if (Boolean.parseBoolean(member.getIslecturer())) {
							Query.insertLecturer(member.getUserID(), member.getFirstName(), member.getLastName());
						}
						if (Boolean.parseBoolean(member.getIsstudent())) {
							Query.insertStudent(member.getUserID(), member.getFirstName(), member.getLastName());
						}
					}
					lblErrorPort.setText("");
					lblSuccess.setText("Success with adding users data");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "fieldsRB":
				Field field;
				ArrayList<Field> fields = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
					String line;
					// Skip the header line
					if ((line = br.readLine()) != null) {
					}
					while ((line = br.readLine()) != null) {
						String[] values = line.split(",");
						String fieldName = values[0];
						String fieldCode = values[1];
						field = new Field(fieldName, fieldCode);
						fields.add(field);
					}
					for (Field f : fields) {
						Query.insertFields(f.getFieldName(), f.getFieldCode());
					}
					lblErrorPort.setText("");
					lblSuccess.setText("Success with adding fields data");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "studentsInCourseRB":
				StudentInCourse studentIncourse;
				ArrayList<StudentInCourse> studentsIncourse = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
					String line;
					// Skip the header line
					if ((line = br.readLine()) != null) {
					}
					while ((line = br.readLine()) != null) {
						String[] values = line.split(",");
						String courseCode = values[0];
						String studentID = values[1];
						studentIncourse = new StudentInCourse(courseCode, studentID);
						studentsIncourse.add(studentIncourse);
					}
					for (StudentInCourse s : studentsIncourse) {
						Query.insertStudentInCourse(s.getCourseCode(), s.getStudentID());
					}
					lblErrorPort.setText("");
					lblSuccess.setText("Success with adding students data");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "coursesRB":
				Course course;
				ArrayList<Course> courses = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
					String line;
					// Skip the header line
					if ((line = br.readLine()) != null) {
					}
					while ((line = br.readLine()) != null) {
						String[] values = line.split(",");
						String courseName = values[0];
						String courseCode = values[1];
						String fieldCode = values[2];
						course = new Course(courseName, courseCode, fieldCode);
						courses.add(course);
					}
					for (Course c : courses) {
						Query.insertCourses(c.getCourseName(), c.getCourseCode(), c.getFieldCode());
					}
					lblErrorPort.setText("");
					lblSuccess.setText("Success with adding courses data");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				// Handle unknown selection
				break;
			}
		}

	}

	/**
	 * Handles the click action of the "Connect" button. This method initiates the
	 * connection between the server and the database. It checks for the port number
	 * and the database password as inputs, and handles the connection state based
	 * on the validity of these inputs. In case of any exceptions, it captures and
	 * displays the error message.
	 *
	 * @param event The action event from the button click.
	 * @throws Exception when there's any error during the process
	 */
	@FXML
	public void connectActionButton(ActionEvent event) throws Exception {
		String port = getport();
		FXMLLoader loader = new FXMLLoader();
		// check if user put port number
		if (port.trim().isEmpty()) {
			lblSuccess.setText("");
			lblErrorPort.setText("you must enter port number");
			return;
		}
		// trim isn't empty
		String DBpassword = getDB();
		System.out.println(DBpassword);
		// check if user put port number

		if (DBpassword.trim().isEmpty()) {
			lblSuccess.setText("");
			lblErrorPort.setText("you must enter DB password");
			return;
		}
		try {
			// initialize the instance
			server = new EchoServer(Integer.parseInt(port), DBpassword);
			server.runServer();
			while (serverTryToConnectDB) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (!server.connectionSuccessfull || !server.connectionToDBSuccessfull) {
				server.close();
				lblSuccess.setText("");
				lblErrorPort.setText("connection faild");
				serverTryToConnectDB = true;
				btnConnectDB.setDisable(false); // Re-enable the connect button
				txtDBPassword.clear(); // Clear the DB password field
			} else {
				btnConnectDB.setDisable(true);
				lblErrorPort.setText("");
				lblSuccess.setText("Connected Succesfully");
				display("server started");
			}
		} catch (Exception e) {
			lblSuccess.setText("");
			lblErrorPort.setText("Error: " + e.getMessage());
			e.printStackTrace();
			server.close();
			serverTryToConnectDB = true;
			btnConnectDB.setDisable(false); // Re-enable the connect button
			txtDBPassword.clear(); // Clear the DB password field
		}
	}

	/**
	 * Refreshes the list of connected clients.
	 */
	public void refresh() {
		ArrayList<Client> clients = new ArrayList<>();
		String clientAddress; // the whole client's data
		String[] ca; // tmp for string clientAddress for split method
		String ip; // client's ip address
		String hostName; // client's host name

		try {
			// there is at least 1 client that connected to server
			if (server.getNumberOfClients() != 0) {
				Thread[] clientConnected = new Thread[server.getNumberOfClients()];
				clientConnected = server.getClientConnections();
				for (Thread client : clientConnected) {
					// get client's ip address and host's name
					clientAddress = (((ConnectionToClient) client).getInetAddress().getLocalHost()).toString();
					ca = clientAddress.split("/");
					hostName = ca[0];
					ip = ca[1];
					// add the details to the table
					clients.add(new Client(ip, hostName, ClientConnectionStatus.CONNECTED));
				}
			}
			setColumns(clients);
		} catch (Exception e) {
			display(e.getMessage());
		}

	}

	/**
	 * Sets the columns for the table of clients.
	 *
	 * @param clients The list of clients to display.
	 * @throws IOException when there's any error during the process
	 */
	public void setColumns(ArrayList<Client> clients) throws IOException {
		if (clients == null) {
			throw new IllegalArgumentException("Client list cannot be null");
		}
		boolean disconect = true;
		if (!clientList.isEmpty()) {
			for (Client c1 : clientList) {
				for (Client c2 : clients) {
					if (c1.equals(c2)) {
						disconect = false;
						break;
					}
				}
				if (disconect) {
					c1.setStatus(ClientConnectionStatus.DISCONNECTED);
					clients.add(c1);
					display(clients.get(0).getStatus().name());
				}
				disconect = true;
			}
		}
		clientList.clear();
		clientList = FXCollections.observableArrayList(clients);
		columnIP.setCellValueFactory(new PropertyValueFactory<>("ip"));
		columnHost.setCellValueFactory(new PropertyValueFactory<>("hostName"));
		columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		table.setItems(clientList);
	}

	/**
	 * @return the DB password number user puts in
	 */
	private String getDB() {
		return txtDBPassword.getText();
	}

	/**
	 * This is FXML event handler. Handles the action of click on 'exit' button.
	 *
	 * @param event The action event.
	 */
	@FXML
	public void getExitButton(ActionEvent event) {
		display("exit server");
		System.exit(0);
	}

	/**
	 * This method displays a message into the console.
	 *
	 * @param message The string to be displayed.
	 */
	public static void display(String message) {
		System.out.println("> " + message);
	}
}
//End of ServerPortController class
