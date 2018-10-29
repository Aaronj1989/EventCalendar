package events;

import java.time.LocalDate;


import alerts.DeleteEventMessagePrompt;

import connection.CalendarEventDao;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.stage.Stage;

public class EventListViewController {

	private Pane pane;

	private LocalDate date;

	@FXML
	private Scene scene;

	@FXML
	private AnchorPane eventAnchor;

	@FXML
	private TextArea eventMsgArea;

	@FXML
	private ListView<EventMessage> listView;

	@FXML
	private Button addEventBttn;

	private EventMessage currentMessage;

	private CalendarEventDao calendarEventDao;

	public EventListViewController() {

		this.calendarEventDao = CalendarEventDao.getInstance();

	}

	public void addEvent() {

		startSaveDateEvent(pane, date);
	}

	public void startSaveDateEvent(Pane source, LocalDate date) {

		// pop up box to type and save event to calendar
		try {
			DateEvent savedDate = new DateEvent(source, date, listView);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/events/DateEvent.fxml"));
			loader.setController(savedDate);
			Stage primaryStage = new Stage();
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Date Event");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initialize() {

		// allows you to click items in listview
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				currentMessage = (EventMessage) listView.getSelectionModel().getSelectedItem();

				String theMessage = currentMessage.getMessage();
				eventMsgArea.setText(theMessage);

			}
		});
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDateAndPane(LocalDate selectedDate, Pane selectedPane) {
		this.date = selectedDate;
		this.pane = selectedPane;
	}

	// loads the current list to the listview
	public void loadList(ListView<EventMessage> list) {
		eventMsgArea.clear();
		this.listView.setItems(list.getItems());
	}

	public void deletePrompt() {
		/*if a new listview is clicked current message is set to null so the wrong message doesn't get
		deleted */
		if (currentMessage == null) {
			return;
		}
		new DeleteEventMessagePrompt(this, currentMessage.getSubject()).confirm();
	}

	public void removeCurrentMessage() {
		eventMsgArea.clear();
		String subject = currentMessage.getSubject();
		String message = currentMessage.getMessage();
		int year = currentMessage.getYearNumber();
		int month = currentMessage.getMonthNumber();

		calendarEventDao.removeCurrentMessage(subject, message, year, month);

		listView.getItems().remove(currentMessage);

		checkIfListIsEmpty();

	}

	// if event list is empty for a particular date, that date pane reverts back to
	// its original color(White)
	public void checkIfListIsEmpty() {
		if (listView.getItems().isEmpty()) {
			pane.setStyle("-fx-background-color: white");
		}
	}
}