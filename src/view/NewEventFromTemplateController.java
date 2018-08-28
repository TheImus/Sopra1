package view;

import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NewEventFromTemplateController {

    @FXML
    private TextField TextEventName;

    @FXML
    private DatePicker PickerDate;

    @FXML
    private TextField TextPlace;

    @FXML
    private DatePicker PickerDeadline;

    @FXML
    private TextField TextStarter;

    @FXML
    private TextField TextMain;

    @FXML
    private TextField TextDessert;

    @FXML
    private TextArea TextEventDetails;

    @FXML
    private Button BtnDispose;
    
    @FXML
    private GridPane gridPaneNewEvent;

    @FXML
    private Button BtnCreateEvent;
    
    private WalkingDinnerController walkingDinnerController;
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}


	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

    @FXML
    void onCreateEvent(ActionEvent event) {
    	try {
			GridPane root = new GridPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TabsOverview.fxml"));
			root = loader.load();
			
			TabsOverviewController tabsOverviewController = (TabsOverviewController) loader.getController();
			tabsOverviewController.setWalkingDinnerController(walkingDinnerController);
			
			Scene scene = new Scene(root);
			
			tabsOverviewController.init();
			
			((Stage)gridPaneNewEvent.getScene().getWindow()).setScene(scene);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onDispose(ActionEvent event) {

    }

}
