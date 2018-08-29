package application;
	
import java.util.List;

import controller.TestDataFactory;
import controller.WalkingDinnerController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Event;
import view.EventOverviewController;
import view.StageContainer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
private WalkingDinnerController walkingDinnerController;

	
	@Override
	public void init() throws Exception { //Beispielprojekt muss sp�ter ruas
		//walkingDinnerController = new WalkingDinnerController();
		walkingDinnerController = TestDataFactory.createTestWalkingDinnerController();
		//walkingDinnerController.setWalkingDinner(walkingDinnerController.loadModel("beispielprojekt"));
		Event newEvent = TestDataFactory.createTestEvent();
		newEvent.setName("TestEvent mit Schedule von Factory");
		List<Event> evList = walkingDinnerController.getWalkingDinner().getEvents();
		if(!evList.contains(newEvent)) {
			evList.add(newEvent);
		}
		walkingDinnerController.saveModel(walkingDinnerController.getWalkingDinner(),"beispielprojekt");
		//walkingDinnerController.setWalkingDinner(walkingDinnerController.loadModel("beispielprojekt"));

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
			primaryStage.setScene(scene);
			StageContainer.primaryStage = primaryStage;
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		
		launch(args);
	}
}
