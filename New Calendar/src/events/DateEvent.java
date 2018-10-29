package events;


import java.sql.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



import connection.CalendarEventDao;

import javafx.application.Application;
import javafx.collections.*;


import javafx.event.Event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.*;
import javafx.stage.Stage;

// this class saves data to be put in a database and retrieved upon the program loading.
//the selected date is put in the date event object that object is then put into the pane holding that specicfic day
public class DateEvent extends Application {
	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private TextField subjectField;

	@FXML
	private TextArea eventTextArea;

	@FXML
	private Button saveBttn;

	@FXML
	private Button CancelBttn;

	private Pane pane;

	public LocalDate date;
	@FXML
	private Pane dateEventPane;
	@FXML
	private AnchorPane dateEventAnchor;

	private ObservableList<EventMessage> list;
	
	private ListView<EventMessage> listView;

	private CalendarEventDao calendarEventDao;

	@FXML
    private Label eventDateLabel;
	
	@FXML
	private Label eventForDateLabel;
	
	public DateEvent() {
	}

	public DateEvent(Pane pane, LocalDate date, ListView<EventMessage> listView) {

		this.pane = pane;

		this.date = date;

		this.listView = listView;

		this.calendarEventDao = CalendarEventDao.getInstance();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.stage = primaryStage;
		primaryStage = new Stage();

		Parent root = FXMLLoader.load(getClass().getResource("/events/EventListView.fxml"));
		scene = new Scene(root);

		primaryStage.setTitle("Calendar");

		primaryStage.setScene(scene);

		primaryStage.show();
	}

	@FXML
	private void saveDateEvent(Event event) {

		save(date, pane);
	}
	
	// connects to database sends the subject and body to the database along with
	// the date to later be retrieved
	// if successful paint pane light green marking it has an event saved to it
	private void save(LocalDate date, Pane pane) {

		addEventMessage(new EventMessage(subjectField.getText(), eventTextArea.getText(), date));
		pane.setStyle("-fx-background-color: lightgreen");
		stage = (Stage) saveBttn.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void closeWindow() {

	}

	public void addEventMessage(EventMessage eventMessage) {
		saveEventToDb(eventMessage);
		list = FXCollections.observableArrayList();
		list.add(eventMessage);
		listView.getItems().addAll(list);
	}

	public void saveEventToDb(EventMessage eventMessage) {

		String subject = eventMessage.getSubject();
		String message = eventMessage.getMessage();
		Date eventDate = Date.valueOf(date);
		
		calendarEventDao.saveEventMessage(subject, message, eventDate);

	}
	
	public void initialize() {
		DateTimeFormatter dateFormat= DateTimeFormatter.ofPattern("EEE dd MMM YYYY");
	
	       String formatedDate = date.format(dateFormat);
	       eventDateLabel.setText(formatedDate);
	   
	}

}
