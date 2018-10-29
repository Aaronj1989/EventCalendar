package alerts;

import java.util.Optional;

import events.EventListViewController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;

public class DeleteEventMessagePrompt extends Alert{
EventListViewController eventListViewController;

	public DeleteEventMessagePrompt(EventListViewController eventListViewController, String subject ) {
		super(AlertType.CONFIRMATION);
		this.eventListViewController = eventListViewController;
		this.setTitle("Confirmation Dialog");
		this.setHeaderText("Are you sure you want to delete this event " + '"' + subject + '"'+"?");
		// TODO Auto-generated constructor stub
  
	}

	
	public void confirm(){
		ButtonType yesButton = new ButtonType("yes");
		 ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		 
		 this.getButtonTypes().setAll(yesButton,cancelButton);
		 
		 Optional<ButtonType> result = this.showAndWait();
		 
		 
		 if(result.get() == yesButton) {
			 
			eventListViewController.removeCurrentMessage();
		 }
	}
}
