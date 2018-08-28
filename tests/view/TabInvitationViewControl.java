package view;

import controller.TestDataFactory;
import controller.WalkingDinnerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.WalkingDinner;

public class TabInvitationViewControl extends Application {

	private WalkingDinnerController wdController;

	@Override
	public void init() throws Exception {
		wdController = new WalkingDinnerController();
		WalkingDinner walkingDinner = TestDataFactory.createSampleWalkingDinner();
		wdController.setWalkingDinner(walkingDinner);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			
			GridPane root = new GridPane();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TabInvite.fxml"));
			try {
			    root = fxmlLoader.load();
			} catch (Exception e) {
			    throw new RuntimeException(e);
			}
			TabInviteViewController tabViewController = (TabInviteViewController) fxmlLoader.getController();
			tabViewController.setWalkingDinnerController(wdController);		
			
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			tabViewController.init();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
