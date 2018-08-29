package view;
import java.io.File;
import java.util.List;

import controller.ExportController;
import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
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
    
    private Stage stage; // Stage for save file dialog
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}
	
    public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
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
    	participantList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	refresh();
    }
    
    public void refresh() {
    	participantList.getItems().remove(0, participantList.getItems().size());
    	List<Participant> list = walkingDinnerController.getWalkingDinner().getCurrentEvent().getParticipants();
    	participantList.getItems().addAll(list);
    }
    
    private ExportController getExportController(){
    	return this.walkingDinnerController.getExportController();
    }

    @FXML
    void onExportChangedParticipantData(ActionEvent event) {//TODO
    	ExportController exportController = this.getExportController();
    	
    	
    	FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text-Dateien (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Changed_Participants.txt");
        fileChooser.setTitle("Geänderte Teilnehmerdaten speichern");
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(this.stage);
        
    	
    	exportController.exportChangedParticipantData(file.getName());
    }

    @FXML
    void onExportParticipantData(ActionEvent event) {//TODO exportParticipantData
    	ExportController exportController = this.getExportController();
    	List<Participant> selectedParticipants = participantList.getSelectionModel().getSelectedItems();
    	try{
    	
	    	FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text-Dateien (*.txt)", "*.txt");
	        fileChooser.getExtensionFilters().add(extFilter);
	        fileChooser.setInitialFileName("All_Participants.txt");
	        fileChooser.setTitle("Teilnehmerdaten speichern");
	        
	        //Show save file dialog
	        File file = fileChooser.showSaveDialog(this.stage);
	        
	    	
	    	exportController.exportParticipantData(selectedParticipants, file.getName());
    	}catch(Exception e){
    		System.out.println(e.toString());
    	}
    	//exportController.exportParticipantData(participants, fileName);
    }
    
    @FXML
    void onAdjustParticipant(ActionEvent event){
    	 try {
  			Participant currPart = participantList.getSelectionModel().getSelectedItem();
  			if(currPart!=null){
  				walkingDinnerController.getWalkingDinner().getCurrentEvent().setCurrentParticipant(currPart);
  			}
    		 
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
    
    

}
