package view;

import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Course;
import model.Group;
import model.Team;

public class TabGroupsController {
	
	private WalkingDinnerController walkingDinnerController;

    @FXML
    private ComboBox<Course> cbCourse;

    @FXML
    private Button BtnSetAsCooking;

    @FXML
    private ListView<Team> ListSelectedGroup;

    @FXML
    private ListView<Group> ListGroups;

    @FXML
    private TitledPane ListFreeTeams;

    @FXML
    private Button BtnAddTeamToGroup;

    @FXML
    private Button BtnRemoveTeamFromGroup;

    @FXML
    void onBtnAddTeamToGroup(ActionEvent event) {

    }

    @FXML
    void onBtnRemoveTeamFromGroup(ActionEvent event) {

    }

    @FXML
    void onBtnSetCooking(ActionEvent event) {

    }

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
    /**
     * Initializes TabGroups
     */
    public void init() {
		Image image = new Image(getClass().getResourceAsStream("/view/chef.svg"));
		ImageView imageView = new ImageView();
		imageView.setImage(image);
    	
    	//BtnSetAsCooking.setGraphic(value);
		BtnSetAsCooking.setGraphic(imageView);
    }

}
