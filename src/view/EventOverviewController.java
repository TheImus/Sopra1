package view;

import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EventOverviewController {

    @FXML
    private GridPane MainPane;

    @FXML
    private Button BtnGenerateTeams;

    @FXML
    private ListView<?> ListTeams;

    @FXML
    private ListView<?> ListFreeParticipants;

    @FXML
    private ListView<?> ListSelectedTeams;

    @FXML
    private Button BtnDiscard;

    @FXML
    private Button BtnSave;
    
    @FXML
    private BorderPane borderPaneOverview;
    
    private WalkingDinnerController walkingDinnerController;
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}


	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

    


	@FXML
    void onDiscard(ActionEvent event) {

    }

    @FXML
    void onGenerateTeams(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) {

    }
    
    @FXML
    void onNewEvent(ActionEvent event){
    	try {
			GridPane root = new GridPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewEventFromTemplate.fxml"));
			root = loader.load();
			
			NewEventFromTemplateController newEventFromTemplateController = (NewEventFromTemplateController) loader.getController();
			newEventFromTemplateController.setWalkingDinnerController(walkingDinnerController);
			
			Scene scene = new Scene(root);
			
			((Stage)borderPaneOverview.getScene().getWindow()).setScene(scene);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onSearchEventName(ActionEvent event){
    	
    }
    

}
