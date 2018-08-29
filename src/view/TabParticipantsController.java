package view;
import java.util.List;

import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Participant;

public class TabParticipantsController {

    @FXML
    private ListView<Participant> participantList;

    @FXML
    private Button BtnExportParticipantData;

    @FXML
    private Button BtnExportChanges;
    
    @FXML
    private Button BtnCreateParticipant;
    
    @FXML
    private Button BtnAdjustParticipant;
    
    @FXML
    private GridPane GridPaneParticipants;
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}
    
    public void init() {
    	participantList.setCellFactory(view ->
		new ListCell<Participant>() {
			protected void updateItem(Participant item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		            setText(item.getPerson().getName());
		        }
		    }
		});
    	refresh();
    }
    
    public void refresh() {
    	participantList.getItems().remove(0, participantList.getItems().size());
    	List<Participant> list = walkingDinnerController.getWalkingDinner().getCurrentEvent().getParticipants();
    	participantList.getItems().addAll(list);
    }

    @FXML
    void onExportChangedParticipantData(ActionEvent event) {

    }

    @FXML
    void onExportParticipantData(ActionEvent event) {

    }
    
    @FXML
    void onAdjustParticipant(ActionEvent event){
    	 try {
    		Participant currPart = participantList.getSelectionModel().getSelectedItem();
     		walkingDinnerController.getWalkingDinner().getCurrentEvent().setCurrentParticipant(currPart);
   			
  			GridPane root = new GridPane();
  			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdjustParticipant.fxml"));
  			root = loader.load();
  			
  			AdjustParticipantController adjustParticipantController = (AdjustParticipantController) loader.getController();
  			adjustParticipantController.setWalkingDinnerController(walkingDinnerController);
  			adjustParticipantController.init();
  			Scene scene = new Scene(root);
  			
  			((Stage)GridPaneParticipants.getScene().getWindow()).setScene(scene);
  			
    		
  		} catch(Exception e) {
  			e.printStackTrace();
  		}
    }
    
    @FXML
    void onCreateParticipant(ActionEvent event){
    	 try {
   			GridPane root = new GridPane();
   			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdjustParticipant.fxml"));
   			root = loader.load();
   			
   			AdjustParticipantController adjustParticipantController = (AdjustParticipantController) loader.getController();
   			adjustParticipantController.setWalkingDinnerController(walkingDinnerController);
   			adjustParticipantController.init();
   			Scene scene = new Scene(root);
   			
   			((Stage)GridPaneParticipants.getScene().getWindow()).setScene(scene);
   			
   		} catch(Exception e) {
   			e.printStackTrace();
   		}
    }
    
    

}
