package view;

import java.util.List;

import controller.WalkingDinnerController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Event;
import model.Participant;

public class EventOverviewController {

	@FXML
    private BorderPane borderPaneOverview;

    @FXML
    private Button BtnNewFromTemplate;

    @FXML
    private Button BtnEdtiting;

    @FXML
    private ListView<Event> listEvent;

    @FXML
    private TextField searchEvent;
    
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
    
    public void refresh(){

    	listEvent.setCellFactory(view ->
		new ListCell<Event>() {
			protected void updateItem(Event item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		            setText(item.getName() + " - " + item.getDate().toString());
		        }
		    }
		}
    			);
    	List<Event> list = walkingDinnerController.getWalkingDinner().getEvents();
		for(Event ev:list){
			listEvent.getItems().add(ev);
		}
		
    }
    
    public void init(){
    	listEvent.setCellFactory(view ->
		new ListCell<Event>() {
			protected void updateItem(Event item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		            setText(item.getName() + " - " + item.getDate().toString());
		        }
		    }
		}
    			);
    	refresh();
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
