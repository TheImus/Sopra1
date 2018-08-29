package view;

import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Event;

public class TabsOverviewController {

    @FXML
    private GridPane gridPaneTabOverview;

    @FXML
    private Tab TabGeneral;

    @FXML
    private Tab TabInvitation;

    @FXML
    private Tab TabParticipant;
    
    @FXML
    private Tab TabTeams;
    
    @FXML
    private Tab TabGroups;
    
    @FXML
    private MenuBar MenuBarTabOberview;

    @FXML
    private Menu MenuTabsOverview;

    @FXML
    private MenuItem MenuItemSave;

    @FXML
    private MenuItem MenuItemClose;
    
    @FXML
    private Label LabelEventName;

   
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

    public void init(){
    	try {
    		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
    		if(currentEvent!=null){
    			LabelEventName.setText(currentEvent.getName() + " - " + currentEvent.getDate().toString());
    		}
    		
			GridPane root = new GridPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TabEvent.fxml"));
			root = loader.load();
			
			TabEventController tabEventController = (TabEventController) loader.getController();
			tabEventController.setWalkingDinnerController(walkingDinnerController);	
			tabEventController.refresh();
			TabGeneral.setContent(root);
			
			GridPane root2 = new GridPane();
			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/TabInvite.fxml"));
			root2 = loader2.load();
			
			TabInviteViewController tabInviteViewController = (TabInviteViewController) loader2.getController();
			tabInviteViewController.setWalkingDinnerController(walkingDinnerController);
			tabInviteViewController.init();
			TabInvitation.setContent(root2);
			
			GridPane root3 = new GridPane();
			FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/view/TabParticipants.fxml"));
			root3 = loader3.load();
			
			TabParticipantsController tabParticipantsController = (TabParticipantsController) loader3.getController();
			tabParticipantsController.setWalkingDinnerController(walkingDinnerController);	
			tabParticipantsController.init();
			TabParticipant.setContent(root3);
			
			GridPane root4 = new GridPane();
			FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/TabAdjustTeams.fxml"));
			root4 = loader4.load();
			
			TabAdjustTeamsController tabAdjustTeamsController = (TabAdjustTeamsController) loader4.getController();
			tabAdjustTeamsController.setWalkingDinnerController(walkingDinnerController);	
			tabAdjustTeamsController.init();
			TabTeams.setContent(root4);
			
			GridPane root5 = new GridPane();
			FXMLLoader loader5 = new FXMLLoader(getClass().getResource("/view/TabGroups.fxml"));
			root5 = loader5.load();
			
			TabGroupsController tabGroupsController = (TabGroupsController) loader5.getController();
			tabGroupsController.setWalkingDinnerController(walkingDinnerController);	
			tabGroupsController.init();
			TabGroups.setContent(root5);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }


    
    @FXML
    void onCloseTabsOverview(ActionEvent event) {    	
    	try {
			BorderPane root = new BorderPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventOverview.fxml"));
			root = loader.load();
			
			EventOverviewController eventOverviewController = (EventOverviewController) loader.getController();
			eventOverviewController.setWalkingDinnerController(walkingDinnerController);
			eventOverviewController.refresh();
			Scene scene = new Scene(root);
			
			
			((Stage)gridPaneTabOverview.getScene().getWindow()).setScene(scene);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onSaveTabsOverview(ActionEvent event) {
    	WalkingDinnerController.saveModel(walkingDinnerController.getWalkingDinner(), "beispielprojekt");
    }


}
