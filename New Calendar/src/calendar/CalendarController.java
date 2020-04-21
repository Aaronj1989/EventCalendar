package calendar;

import java.time.LocalDate;

import calendar.CalendarModel;
import events.EventChecker;
import events.EventListViewController;
import events.EventMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CalendarController {
    @FXML
	private Label monthLabel;

	@FXML
	private GridPane weekDayGrid;
	@FXML
	private GridPane dayNumGrid;
	@FXML
	private Label currentDateLabel;
	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private Button deleteEventBttn;
	@FXML
	private AnchorPane decadeAnchorPane;

	private Pane previousPane;
	
	private CalendarModel cal;

   @FXML
	private EventListViewController eventListViewController;

	public CalendarController() {}

	@FXML
	public void rollBackYear(ActionEvent event) {
		cal.rollBackYear();
		setUpCalendarPanes();
	}

	// Event Listener on Button[#rollBackMonth].onAction
	@FXML
	public void rollBackMonth() {
		cal.rollBackMonth();
		setUpCalendarPanes();
	}

	// Event Listener on Button[#rollForwardMonth].onAction
	@FXML
	public void rollForwardMonth() {
		cal.rollForwardMonth();
		setUpCalendarPanes();
	}

	@FXML
	public void rollForwardYear() {
		cal.rollForwardYear();
		setUpCalendarPanes();
	}

	// gets the contents of the exact pane you're clicking on in the gridpane.
	@FXML
	private void getCell(MouseEvent event) {

		Pane currentPane = (Pane) event.getSource();

		lightUpSelectedPane(currentPane);

		Label dayNum = (Label) currentPane.getChildren().get(0);

		int selectedDayOfMonth = Integer.parseInt(dayNum.getText());
		int year = cal.getToday().getYear();
		int monthNum = cal.getToday().getMonthValue();
		LocalDate selectedDate = LocalDate.of(year, monthNum, selectedDayOfMonth);

		eventListViewController.setDateAndPane(selectedDate, currentPane);
	
		ListView<EventMessage> lv = (ListView<EventMessage>) currentPane.getChildren().get(1);

		eventListViewController.loadList(lv);

	}

	// refreshes the gridpane in accordance with the new date.
	public void setUpCalendarPanes() {
		dayNumGrid.getChildren().clear();

		Pane[] panes = new Pane[cal.getMonthLength()];
		int column = cal.getStartingPos();
		int row = 0;
		for (int i = 0; i < panes.length; i++) {
			panes[i] = new Pane();
			panes[i].setOnMouseClicked(event -> getCell(event));
			panes[i].setStyle("-fx-background-color: white");

			if (column == 6) {
				dayNumGrid.add(panes[i], column, row);
				row++;
				column = 0;
				continue;
			}
			dayNumGrid.add(panes[i], column, row);
			column++;

		}
		loadCalendarDate();

	}

	public void initialize() {
		
		cal = new CalendarModel();
	     setUpCalendarPanes();
		

	}
	
	// find where first day of month is and start building there
	// column range is from 0-6 columns representing days of the week. mon.tue.wed. etc
	public void loadCalendarDate() {
		setCurrentDateLabels();
		
		int column = cal.getStartingPos();

		int row = 0;

		int monthLength = cal.getMonthLength();

		Label[] labels = new Label[monthLength];

		ListView<EventMessage>[] lists = new ListView[labels.length];

		Pane pane = null;
            //create labels and lists for each pane to hold 
		    // the labels match the day number and the lists will hold the events saved to that date. 
		for (int i = 0; i < monthLength; i++) {
         
			labels[i] = new Label();
			labels[i].setText(Integer.toString(i+1));

			lists[i] = new ListView<EventMessage>();
			lists[i].setVisible(false);

			pane = (Pane) getNodeFromGridPane(dayNumGrid, column, row);

			if (column == 6) {
				pane.getChildren().add(labels[i]);
				pane.getChildren().add(lists[i]);

				column = 0;
				row++;
				continue;
			}
			pane.getChildren().add(labels[i]);
			pane.getChildren().add(lists[i]);
			column++;
			
		}
		//after building calendar
		checkForEvents();
	}

	// method returns and assigns Pane from gridpane  so it can be manipulated 
	// i.e grabs the pane at particular row/column and adds child objects to the pane
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {

		for (Node node : gridPane.getChildren()) {
			Integer columnIndex = GridPane.getColumnIndex(node);
			Integer rowIndex = GridPane.getRowIndex(node);

			if (columnIndex == null) {
				columnIndex = 0;
			}
	
			if (rowIndex == null) {
				rowIndex = 0;
			}
			if (columnIndex == col && rowIndex == row) {
				return node;
			}

		}
		return null;
	}

	// every time you check for events it makes call to the database
	public void checkForEvents() {
		EventChecker eventChecker = new EventChecker(cal, dayNumGrid);
		eventChecker.checkForEvents();
	}

	public void setCurrentDateLabels() {
		monthLabel.setText(cal.getMonth());
		currentDateLabel.setText(cal.getToday().toString());
	}

	public void deletePrompt() {

		eventListViewController.deletePrompt();

	}
         //lights up currently selected pane and removes light from previous pane. 
	    //clears the eventmessage list when another pane is clicked. 
	public void lightUpSelectedPane(Pane currentPane) {
		InnerShadow shadowColor = new InnerShadow();
		shadowColor.setColor(Color.BLUE);
		
		if(previousPane == currentPane) {
			return;
		}

		if (previousPane != null && previousPane != currentPane) {
		
			previousPane.setEffect(null);
		}
				
        currentPane.setEffect(shadowColor);

		previousPane = currentPane;

	}

}
