package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		

		try {
		
		Parent 	root = FXMLLoader.load(getClass().getResource("/calendar/Calendar.fxml"));
		
		Scene scene = new Scene(root);
	
		primaryStage.setTitle("Calendar");

		primaryStage.setScene(scene);
		
		primaryStage.show();		
		// starts the calendarController
	}  catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	public static void main(String[]args) {
		launch();
	}
}
