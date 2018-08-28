package view;

import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class TabGroupsController {

    @FXML
    private Button BtnGenerateTeams;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnCancel;

    @FXML
    private Tab TabStarter;

    @FXML
    private Tab TabMain;

    @FXML
    private Tab TabDesert;

    @FXML
    private TextArea TextTeams;

    @FXML
    private TextArea TextFreeTeams;

    @FXML
    private TextArea TestHostTeams;

    @FXML
    private TextArea TextGuestTeam;

    @FXML
    private TextArea TextGuestTeam2;
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

    @FXML
    void onGenerateTeams(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) {

    }

}
