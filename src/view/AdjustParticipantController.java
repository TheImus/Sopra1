package view;

	import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import controller.ParticipantAction;
import controller.WalkingDinnerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
	import javafx.scene.control.CheckBox;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Course;
import model.Participant;
import model.Restriction;

	public class AdjustParticipantController {
		
		@FXML
		private ScrollPane SpRestrictionList;

	    @FXML
	    private DatePicker DateBirthday;

	    @FXML
	    private TextArea EdSpecialWished;
	    
	    @FXML
	    private TextField EdStreet;
	    
	    @FXML
	    private TextField EdName;

	    @FXML
	    private TextField EdZipCode;

	    @FXML
	    private TextField EdPlace;

	    @FXML
	    private TextField EdAddressExtra;
	    
	    @FXML
	    private TextField EdRestriction;

	    @FXML
	    private ComboBox<String> CbWishCourse;

	    @FXML
	    private ComboBox<String> CbSearchBox;

	    @FXML
	    private Button BtnCancel;
	    
	    @FXML
	    private ComboBox<ParticipantAction> CbAction;

	    @FXML
	    private CheckBox CheckBoxNoAlkohol;

	    @FXML
	    private CheckBox CheckBoxVegetarian;

	    @FXML
	    private CheckBox CheckBoxVegan;

	    @FXML
	    private Button BtnNewRestriction;
	    
	    @FXML
	    private GridPane GridPaneAdjustParticipant;
	    
	    private WalkingDinnerController walkingDinnerController;
	    
	    public WalkingDinnerController getWalkingDinnerController() {
			return walkingDinnerController;
		}


		public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
			this.walkingDinnerController = walkingDinnerController;
		}

	    
	    public void init(){
	    	//Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	CbAction.setCellFactory(actions ->
	    		new ListCell<ParticipantAction>() {
	    			@Override protected void updateItem(ParticipantAction item, boolean empty) {
	    				super.updateItem(item, empty);
	    				if (item != null) { 
	    					setText(item.getText(item)); 
	    				} else {
	    					setText("");
	    				}
	    				
	    			}
	    		});
	    	refresh();
	    	
	    }
	    
	    public void refresh(){
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	if(currentParticipant!=null){
	    		EdName.setText(currentParticipant.getPerson().getName());
	    		EdZipCode.setText(currentParticipant.getAddress().getZipCode());
	    		EdAddressExtra.setText(currentParticipant.getAddress().getAddressAdditional());
	    		EdSpecialWished.setText(currentParticipant.getSpecialNeeds());
	    		EdPlace.setText(currentParticipant.getAddress().getCity());
	    		EdStreet.setText(currentParticipant.getAddress().getStreet());
	    		DateBirthday.setValue(currentParticipant.getPerson().getBirthDate());
	    		
	    		Course courseWish = currentParticipant.getCourseWish();
	    		CbWishCourse.setValue(courseWish.getText(courseWish));
	    		
	    		
	    		
	    	}	
	    }
	    
	    

	    @FXML
	    void OnCancel(ActionEvent event) {
	    	 try {
	   			GridPane root = new GridPane();
	   			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TabsOverview.fxml"));
	   			root = loader.load();
	   			
	   			TabsOverviewController tabsOverviewController = (TabsOverviewController) loader.getController();
	   			tabsOverviewController.setWalkingDinnerController(walkingDinnerController);
	   			tabsOverviewController.init();
	   			Scene scene = new Scene(root);
	   			
	   			((Stage)GridPaneAdjustParticipant.getScene().getWindow()).setScene(scene);
	   			
	   		} catch(Exception e) {
	   			e.printStackTrace();
	   		}
	    }

	    @FXML
	    void OnCreateNewRestriction(ActionEvent event) {
	    	String restrictionName = EdRestriction.getText();
	    	Restriction restriction = new Restriction();
	    	restriction.setName(restrictionName);
	    	
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	currentParticipant.addRestriction(restriction);
	    	restriction.addParticipant(currentParticipant);
	    	
	    	CheckBox newRestriction = new CheckBox();
	    	newRestriction.setId(restrictionName);
	    	//SpRestrictionList.addChild(newRestriction);
	    }

	    @FXML
	    void OnNoAlkoholSelected(ActionEvent event) {
	    	//Fill the original List with all Participants of the model
	    }

	    @FXML
	    void OnParticipantAction(MouseEvent event) {
	    	//CbAction.getItems().add("hi");
	    }
	    
	    @FXML
	    void OnParticipantActionSelected(ActionEvent event){
	    	EdPlace.setText("hal");
	    }

	    @FXML
	    void OnVeganSelected(ActionEvent event) {
	    	
	    }

	    @FXML
	    void OnVegetarianSelected(ActionEvent event) {

	    }
	    
	    @FXML
	    void OnWishCourseClicked(MouseEvent event) {
	    	CbWishCourse.getItems().addAll("Vorspeise","Hauptspeise","Nachspeise");
	    }
	    
	    @FXML
	    void OnSearchBoxClicked(MouseEvent event) {
	    	List<Participant> participantList = walkingDinnerController.getWalkingDinner().getCurrentEvent().getParticipants();
	    	String search = CbSearchBox.getEditor().getText();
	    	
	    	for(Participant part : participantList){
	    		if(part.getPerson().getName().equals(search)){
	    			//return part;
	    		}
	    	}
	    	
	    	
	    }
	    
	    
	    boolean isValidZipCode(){
	    	String zipCode = EdZipCode.getText();
	    	Pattern validZipCode = Pattern.compile("\\b\\d{5}\\b");
	    	
	    	return validZipCode.matcher(zipCode).matches();
	    }

	    boolean checkIfParticipantIsLoaded(){
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	return currentParticipant == null;
	    }
	    
	    /*
	    public void start(Stage stage){
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	if(!(checkIfParticipantIsLoaded())){
	    		EdName.setText(currentParticipant.getPerson().getName());
	    	}
	    }*/

	}




