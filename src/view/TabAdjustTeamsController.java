package view;

import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class TabAdjustTeamsController {

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
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
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

}
