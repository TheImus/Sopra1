package view;



import java.time.LocalTime;

import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Course;
import model.Event;

public class TabEventController {

    @FXML
    private Button BtnChangeData;
    
    @FXML
    private Label LabelEventname;

    @FXML
    private Label LabelDate;

    @FXML
    private Label LabelPlace;

    @FXML
    private Label LabelDeadline;

    @FXML
    private Label LabelStarter;

    @FXML
    private Label LabelMain;

    @FXML
    private Label LabelDessert;
    
    @FXML
    private Text TextDescription;
    
    @FXML
    private GridPane GridPaneTabEvent;

    @FXML
    void onModifyEvent(ActionEvent event) {
    	try {
			GridPane root = new GridPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewEventFromTemplate.fxml"));
			try{
				root = loader.load();
			} catch(Exception e){
				throw new RuntimeException(e);
			}
			NewEventFromTemplateController newEventFromTemplateController = (NewEventFromTemplateController) loader.getController();
			newEventFromTemplateController.setWalkingDinnerController(walkingDinnerController);
			
			Scene scene = new Scene(root);
			
			((Stage)GridPaneTabEvent.getScene().getWindow()).setScene(scene);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}
    
    public void refresh(){
    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
    	if(currentEvent.getDate()!=null){
        	LabelDate.setText(currentEvent.getDate().toString());   		
    	}
    	if(currentEvent.getName()!=null){
        	LabelEventname.setText(currentEvent.getName());   		
    	}
    	if(currentEvent.getName()!=null){
        	LabelPlace.setText(currentEvent.getCity());   		
    	}
    	if(currentEvent.getRegistrationDeadline()!=null){
        	LabelDeadline.setText(currentEvent.getRegistrationDeadline().toString());   		
    	}
    	if(currentEvent.getEventDescription()!=null){
        	TextDescription.setText(currentEvent.getEventDescription());   		
    	}
    	if(currentEvent.getCourseTimes().get(Course.STARTER)!=null){
        	LabelStarter.setText(currentEvent.getCourseTimes().get(Course.STARTER).toString());   		
    	}
    	if(currentEvent.getCourseTimes().get(Course.MAIN)!=null){
        	LabelMain.setText(currentEvent.getCourseTimes().get(Course.MAIN).toString());   		
    	}
    	if(currentEvent.getCourseTimes().get(Course.DESSERT)!=null){
        	LabelDessert.setText(currentEvent.getCourseTimes().get(Course.DESSERT).toString());   		
    	}
    	
    }

}
