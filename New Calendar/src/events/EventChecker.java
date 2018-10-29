package events;

import calendar.CalendarModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import connection.CalendarEventDao;

public class EventChecker {
	private ObservableList<EventMessage> list;
	CalendarModel cal;
	GridPane dayNumGrid;

	private CalendarEventDao calendarEventDao;

	public EventChecker(CalendarModel cal, GridPane dayNumGrid) {
		getCalendarEventDao();
		this.cal = cal;
		this.dayNumGrid = dayNumGrid;
	}

	public void getCalendarEventDao() {
		
		this.calendarEventDao = CalendarEventDao.getInstance();

	}

	public void checkForEvents() {

        int year = cal.getToday().getYear();
        int month = cal.getToday().getMonthValue();
		
		ObservableList<EventMessage> eventMessageList = calendarEventDao.checkForEvents(year, month);

		for (EventMessage eventMessage : eventMessageList) {
			addEventMessageToDate(eventMessage);
		}

	}
                //matches eventMessage object date to the calendar date
	public void addEventMessageToDate(EventMessage eventMessage) {

		list = FXCollections.observableArrayList();

		for (Node n : dayNumGrid.getChildren()) {
			Pane p = (Pane) n;

			if (!p.getChildren().isEmpty()) {
				// if number label in pane matches the day number of the eventMessage, add the
				// event to the list in that pane.
				Label numLabel = (Label) p.getChildren().get(0);
				int number = Integer.parseInt(numLabel.getText());
				// give the current listview a handle on the listview in the pane

				ListView<EventMessage> listView =  (ListView<EventMessage>)p.getChildren().get(1);

				// see if number in calendar pane matches day number of eventMessage date
				if (number == eventMessage.getDayOfMonth()) {

					list.add(eventMessage);

					listView.getItems().addAll(list);

					p.setStyle("-fx-background-color: lightgreen");

				}
			}

		}

	}
}
