package view;
import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TabParticipantsController {

    @FXML
    private ListView<?> participantList;

    @FXML
    private Button BtnExportParticipantData;

    @FXML
    private Button BtnExportChanges;
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

    @FXML
    void onExportChangedParticipantData(ActionEvent event) {

    }

    @FXML
    void onExportParticipantData(ActionEvent event) {

    }
    
    

}
