package application;
	
import java.util.ArrayList;
import java.util.List;

import controller.TestDataFactory;
import controller.WalkingDinnerController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Event;
import view.EventOverviewController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class MainClass extends Application {
	
private WalkingDinnerController walkingDinnerController;

	
	@Override
	public void init() throws Exception { //Beispielprojekt muss später ruas
		
		
		//walkingDinnerController = new WalkingDinnerController();
		walkingDinnerController = TestDataFactory.createTestWalkingDinnerController();
		//walkingDinnerController.setWalkingDinner(walkingDinnerController.loadModel("beispielprojekt"));
		Event newEvent = TestDataFactory.createTestEvent();
		walkingDinnerController.getWalkingDinner().setCurrentEvent(null);
		walkingDinnerController.getWalkingDinner().setEvents(new ArrayList<Event>());;
		newEvent.setName("TestEvent mit Schedule von Factory");
		List<Event> evList = walkingDinnerController.getWalkingDinner().getEvents();
		if(!evList.contains(newEvent)) {
		//	evList.add(newEvent);
			evList.add(TestDataFactory.createConsistentEvent());
		}
		walkingDinnerController.saveModel(walkingDinnerController.getWalkingDinner(),"beispielprojekt");
		//walkingDinnerController.setWalkingDinner(walkingDinnerController.loadModel("beispielprojekt"));


//		walkingDinnerController = new WalkingDinnerController();
//		walkingDinnerController.setWalkingDinner(TestDataFactory.getWalkingDinner());
//		List<Event> allEvents = walkingDinnerController.getWalkingDinner().getEvents();
//		allEvents.add(TestDataFactory.createConsistentEvent());
//		walkingDinnerController.getWalkingDinner().setEvents(allEvents);
	}

	@Override	
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventOverview.fxml"));
			try{
				root = loader.load();
			} catch(Exception e){
				throw new RuntimeException(e);
			}
			EventOverviewController eventOverviewController = (EventOverviewController) loader.getController();
			eventOverviewController.setWalkingDinnerController(walkingDinnerController);
			eventOverviewController.init();
			Scene scene = new Scene(root);
			primaryStage.setTitle("WalkingDinner");
			primaryStage.getIcons().add(new Image(MainClass.class.getResourceAsStream("chef.jpg")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		
		launch(args);
	}
}
