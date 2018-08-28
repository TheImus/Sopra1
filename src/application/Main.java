package application;
	
import java.util.List;

import controller.WalkingDinnerController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Event;
import view.AdjustParticipantController;
import view.EventOverviewController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
private WalkingDinnerController walkingDinnerController;

	
	@Override
	public void init() throws Exception {
		walkingDinnerController = new WalkingDinnerController();
		walkingDinnerController.setWalkingDinner(walkingDinnerController.loadModel("beispielprojekt"));
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
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
