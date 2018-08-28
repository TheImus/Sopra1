package view;

import java.time.LocalDate;
import java.time.LocalTime;

import controller.EventController;
import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Course;
import model.Event;
import model.WalkingDinner;

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
    void onCreateEvent(ActionEvent event) {		//Fehlerbehandlung fuer falsche Eingabe fehlt noch
    	
    	EventController eventController = walkingDinnerController.getEventController();
    	WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
    	Event currentEvent = new Event();
    	walkingDinner.getEvents().add(currentEvent);
    	walkingDinner.setCurrentEvent(currentEvent);
    	eventController.setEventName(TextEventName.getText());
    	eventController.setEventDate(PickerDate.getValue());
    	eventController.setEventPlace(TextPlace.getText());
    	eventController.setDeadline(PickerDeadline.getValue());
    	eventController.setEventDescription(TextEventDetails.getText());
    	eventController.setCourseTime(Course.STARTER, LocalTime.parse(TextStarter.getText()));
    	eventController.setCourseTime(Course.MAIN, LocalTime.parse(TextMain.getText()));
    	eventController.setCourseTime(Course.DESSERT, LocalTime.parse(TextDessert.getText()));
    	
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
    	try {
			BorderPane root = new BorderPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventOverview.fxml"));
			root = loader.load();
			
			EventOverviewController eventOverviewController = (EventOverviewController) loader.getController();
			eventOverviewController.setWalkingDinnerController(walkingDinnerController);
			
			Scene scene = new Scene(root);
			
			
			((Stage)gridPaneNewEvent.getScene().getWindow()).setScene(scene);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

    }

}
