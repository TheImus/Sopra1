package view;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sun.prism.paint.Color;

import controller.EventController;
import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
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
    	
    	boolean passed =true;
    	
    	EventController eventController = walkingDinnerController.getEventController();
    	WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
    	if(currentEvent == null){
    		currentEvent = new Event();
    	}
    	walkingDinner.setCurrentEvent(currentEvent);
    	 
    	
    	// check of free text
    	try{
    		TextEventName.setStyle("-fx-border-color: default;");
    		eventController.setEventName(TextEventName.getText());
    		if(TextEventName.getText().equals(""))
    			throw new Exception();
    	}
    	catch(IllegalArgumentException e){
    		TextEventName.setStyle("-fx-border-color: orange;");
    		passed=false;
    	}
    	catch(Exception e){
    		TextEventName.setStyle("-fx-border-color: red;");
    		passed=false;
    	}
    	
    	try{
    		TextPlace.setStyle("-fx-border-color: default;");
    		eventController.setEventPlace(TextPlace.getText());
    		if(TextPlace.getText().equals(""))
    			throw new Exception();
    	}
    	catch(Exception e){
    		TextPlace.setStyle("-fx-border-color: red;");
    		passed=false;
    	}
    	
    	try{
    		TextEventDetails.setStyle("-fx-border-color: default;");
    		if(TextEventDetails.getText().equals(""))
    			throw new Exception();
    		eventController.setEventDescription(TextEventDetails.getText());
    	}
    	catch(Exception e){
    		TextEventDetails.setStyle("-fx-border-color: red;");
    		passed=false;
    	}
    	
    	
     	
    	
    	//date things
    	
    	try{
    		PickerDate.setStyle("-fx-border-color: default;");
    		if(PickerDate.getValue() == null)
    			throw new Exception();

    		eventController.setEventDate(PickerDate.getValue());
    	}
    	catch(Exception e){
    		PickerDate.setStyle("-fx-border-color: red;");

    		passed=false;
    	}
    	
    	try{
    		PickerDeadline.setStyle("-fx-border-color: default;");
    		if(PickerDeadline.getValue() == null)
    			throw new IllegalArgumentException("kein Value");
    		if(PickerDeadline.getValue().isAfter(PickerDate.getValue()))
    			throw new Exception("Zeit");
    		eventController.setDeadline(PickerDeadline.getValue());
    	}
    	catch(IllegalArgumentException e){
    		PickerDeadline.setStyle("-fx-border-color: red;");
    		passed=false;
    	}
    	catch(Exception e){
    		
    		PickerDeadline.setStyle("-fx-border-color: orange;");	
    		passed=false;
    	}
    	
    	
    	
    	//time things
    	try{
    		TextStarter.setStyle("-fx-border-color: default;");
    		eventController.setCourseTime(Course.STARTER, LocalTime.parse(TextStarter.getText()));
    	}
    	catch(Exception e){
    		TextStarter.setStyle("-fx-border-color: red;");
    		passed=false;
    	}
    	
    	try{
    		TextMain.setStyle("-fx-border-color: default;");
    		if(LocalTime.parse(TextStarter.getText()).isAfter(LocalTime.parse(TextMain.getText()))) 
    			throw new Exception("Zeit");
    		eventController.setCourseTime(Course.MAIN, LocalTime.parse(TextMain.getText()));
    	}
    	catch(Exception e){
    		TextMain.setStyle("-fx-border-color: red;");
    		if(e.getMessage().equals("Zeit"))
    			TextMain.setStyle("-fx-border-color: orange;");
    		passed=false;
    	}
    	
    	try{
    		TextDessert.setStyle("-fx-border-color: default;");
    		if(LocalTime.parse(TextMain.getText()).isAfter(LocalTime.parse(TextDessert.getText()))&& 
    				LocalTime.parse(TextStarter.getText()).isAfter(LocalTime.parse(TextDessert.getText()))) 
    			throw new Exception("Zeit");
    		eventController.setCourseTime(Course.DESSERT, LocalTime.parse(TextDessert.getText()));
    	}
    	catch(Exception e){    		
    		TextDessert.setStyle("-fx-border-color: red;");
    		if(e.getMessage().equals("Zeit"))
    			TextDessert.setStyle("-fx-border-color: orange;");
    		passed=false;
    	}
    	
    	
    	
    	
    	
    	if(passed){
	    	try {
	    		walkingDinner.getEvents().add(currentEvent);
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
    }

    @FXML
    void onDispose(ActionEvent event) {
    	try {
			
    		if(walkingDinnerController.getWalkingDinner().getCurrentEvent()==null){
    			BorderPane root = new BorderPane();
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventOverview.fxml"));
    			root = loader.load();
			
    			EventOverviewController eventOverviewController = (EventOverviewController) loader.getController();
    			eventOverviewController.setWalkingDinnerController(walkingDinnerController);
    			eventOverviewController.refresh();
    			Scene scene = new Scene(root);
			
			
			((Stage)gridPaneNewEvent.getScene().getWindow()).setScene(scene);
    		}
    		else{
    			GridPane root = new GridPane();
	   			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TabsOverview.fxml"));
	   			root = loader.load();
	   			
	   			TabsOverviewController tabsOverviewController = (TabsOverviewController) loader.getController();
	   			tabsOverviewController.setWalkingDinnerController(walkingDinnerController);
	   			tabsOverviewController.init();
	   			Scene scene = new Scene(root);
	   			
	   			((Stage)gridPaneNewEvent.getScene().getWindow()).setScene(scene);
    		}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

    }
    
    public void refresh(){
    	if(walkingDinnerController.getWalkingDinner().getCurrentEvent() != null){
    		BtnCreateEvent.setText("Änderungen Speichern");
    		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
    		TextEventName.setText(currentEvent.getName());
    		PickerDate.setValue(currentEvent.getDate());
    		TextPlace.setText(currentEvent.getCity());
    		PickerDeadline.setValue(currentEvent.getRegistrationDeadline());
    		TextEventDetails.setText(currentEvent.getEventDescription());
    		TextStarter.setText(currentEvent.getCourseTimes().get(Course.STARTER).toString());
    		TextMain.setText(currentEvent.getCourseTimes().get(Course.MAIN).toString());
    		TextDessert.setText(currentEvent.getCourseTimes().get(Course.DESSERT).toString());
    
    		
    	}
    }

}
