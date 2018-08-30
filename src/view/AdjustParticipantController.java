package view;

	import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import controller.ParticipantAction;
import controller.ParticipantController;
import controller.RestrictionController;
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
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Address;
import model.Course;
import model.Event;
import model.Participant;
import model.Restriction;

	public class AdjustParticipantController {
		
		@FXML
		private ScrollPane SpRestrictionList;

	    @FXML
	    private DatePicker DateBirthday;
	    
	    @FXML
	    private ListView<CheckBox> LvRestrictions;

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
	    private ComboBox<Course> CbWishCourse;

	    @FXML
	    private ComboBox<String> CbSearchBox;

	    @FXML
	    private Button BtnEdit;
	    
	    @FXML
	    private Button BtnDelete;
	    
	    @FXML
	    private Button BtnCancel;
	    
	    @FXML
	    private Button BtnCallAction;
	    
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
	    
	    private RestrictionController restrictionController = walkingDinnerController.getRestrictionController();
	    
	    
	    public WalkingDinnerController getWalkingDinnerController() {
			return walkingDinnerController;
		}
	    
	    
	    public boolean validate(){
	    	boolean passed = true;
	    	/*Participant current;
	    	
	    	ParticipantController participantController = walkingDinnerController.getParticipantController();
	    	if(walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant() == null)
	    	{
	    		current = new Participant();
	    	}
	    	else{
		    	current = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	}*/
	    	
	    	try{
	    		EdName.setStyle("-fx-border-color: grey;");
	    		if(EdName.getText().equals(""))
	    			throw new IllegalArgumentException();
	    	}
	    	catch(IllegalArgumentException e){
	    		EdName.setStyle("-fx-border-color: red;");
	    		passed=false;
	    	}
	    	
	    	try{
	    		EdStreet.setStyle("-fx-border-color: grey;");
	    		if(EdStreet.getText().equals(""))
	    			throw new IllegalArgumentException();
	    		Address address = new Address();
	    		address.setAddressAdditional(EdStreet.getText());
	    		
	    		
	    		//participantController.setAddress(address);
	    	}
	    	catch(IllegalArgumentException e){
	    		EdStreet.setStyle("-fx-border-color: red;");
	    		passed=false;
	    	}
	    	
	    	try{
	    		EdZipCode.setStyle("-fx-border-color: grey;");
	    		if(!isValidZipCode())
	    			throw new IllegalArgumentException();
	    		Address address = new Address();
	    		address.setAddressAdditional(EdZipCode.getText());		
	    		//participantController.setAddress(address);
	    	}
	    	catch(IllegalArgumentException e){
	    		EdZipCode.setStyle("-fx-border-color: red;");
	    		passed=false;
	    	}
	    	
	    	
	    	try{
	    		DateBirthday.setStyle("-fx-border-color: grey;");
	    		if(DateBirthday.getValue() == null)
	    			throw new IllegalArgumentException("kein Value");
	    		if(DateBirthday.getValue().isAfter(LocalDate.now()))
	    			throw new Exception("Zeit");
	    		//participantController.setBirthDate(DateBirthday.getValue());
	    	}
	    	catch(IllegalArgumentException e){
	    		DateBirthday.setStyle("-fx-border-color: red;");
	    		passed=false;
	    	}
	    	catch(Exception e){
	    		
	    		DateBirthday.setStyle("-fx-border-color: orange;");	
	    		passed=false;
	    	}
	    	
	    	
	    	
	    	//if(passed)
	    		//walkingDinnerController.getWalkingDinner().getCurrentEvent().setCurrentParticipant(current);
	    	
	    	return passed;
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
	    					setText(item.getText()); 
	    				} else {
	    					setText("");
	    				}
	    				
	    			}
	    	
	    		});
	    	
	    	CbWishCourse.setCellFactory(actions ->
    		new ListCell<Course>() {
    			@Override protected void updateItem(Course item, boolean empty) {
    				super.updateItem(item, empty);
    				if (item != null) { 
    					setText(item.toString()); 
    				} else {
    					setText("");
    				}
    				
    			}
    	
    		});
	    	
	    	List<ParticipantAction> actionList = walkingDinnerController.getParticipantActionController().getPossibleActions();
	    	System.out.println(actionList.size());
	    	for(ParticipantAction action : actionList){
	    		CbAction.getItems().add(action);
	    	}
	    		 
	    	
	    	LvRestrictions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	    	
	    	/*
    		CheckBox alcohol = new CheckBox();
    		alcohol.setText("Kein Alkohol");
    		
    		CheckBox vegetarian = new CheckBox();
    		vegetarian.setText("Vegetarier");
    	
    		CheckBox vegan = new CheckBox();
    		vegan.setText("Vegan");
    		
    		
    		LvRestrictions.getItems().add(alcohol);
    		LvRestrictions.getItems().add(vegetarian);
    		LvRestrictions.getItems().add(vegan);*/
	    	
	    	List<Restriction> restrList = walkingDinnerController.getWalkingDinner().getCurrentEvent().getRestriction();
	    	for(Restriction restr : restrList){
	    		String restrName = restr.getName();
	    		CheckBox restriction = new CheckBox();
	    		restriction.setText(restrName);
	    		LvRestrictions.getItems().add(restriction);
	    	}
	    	
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
	    		CbWishCourse.setValue(courseWish);  		
	    		
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
	   			walkingDinnerController.getWalkingDinner().getCurrentEvent().setCurrentParticipant(null);
	   			
	   		} catch(Exception e) {
	   			e.printStackTrace();
	   		}
	    }
	    
	    @FXML
	    void onEditRestriction(ActionEvent event) {
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> restrList = currentEvent.getRestriction();
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	List<Restriction> participantRestrictions = currentParticipant.getRestrictions();
	    	
	    	CheckBox cb = LvRestrictions.getSelectionModel().getSelectedItem();
	    	String restrName = cb.getText();
	    	
	    	if(!(restrName.equals("Kein Alkohol") || restrName.equals("Veganer") || restrName.equals("Vegetarier"))){
		    	String newName = EdRestriction.getText();
		    	cb.setText(newName);
	    	}
	    	
	    	EdRestriction.clear();
	    	
	    	/*
	    	for(Restriction restr : restrList) {
	    		if(restr.getName().equals(cb.getText())) {
	    			restr.setName(newName);
	    		}
	    	}
	    	
	    	for(Restriction restr : participantRestrictions) {
	    		if(restr.getName().equals(cb.getText())) {
	    			restr.setName(newName);
	    		}
	    	}*/
	    	
	    }
	    
	    @FXML
	    void onDeleteRestriction(ActionEvent event) {
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> restrList = currentEvent.getRestriction();

	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	CheckBox cb = LvRestrictions.getSelectionModel().getSelectedItem();
	    	String restrName = cb.getText();
	    	
	    	if(!(restrName.equals("Kein Alkohol") || restrName.equals("Veganer") || restrName.equals("Vegetarier"))){
	    		LvRestrictions.getItems().remove(cb);
	    	}

    		/*
    		for(Restriction restr : restrList) {
    			if (restr.getName().equals(restrName)) {
    				
    	    		restr.setName(restrName);
    	    		currentParticipant.removeRestriction(restr);
    	    		LvRestrictions.getItems().remove(cb);
    	    		
    				//deleteRestrictionFromEvent(restr);
    	    		
    	    		restrList.remove(restr);
    			}
    		}*/
    		
    		/*
    		if(!restr.isPermanent()) {
    			
	    		restr.setName(restrName);
	    		currentParticipant.removeRestriction(restr);
	    		LvRestrictions.getItems().remove(cb);
	    		
				deleteRestrictionFromEvent(restr);
    		}*/

	 
	    }
	    
	    /*
	    void deleteRestrictionFromEvent(Restriction restriction) {
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> restrList = currentEvent.getRestriction();

	    	if(restrList.contains(restriction)) {
	    		restrList.remove(restriction);
	    	}
	    }*/

	    @FXML
	    void OnCreateNewRestriction(ActionEvent event) {
	    	
	    	String restrictionName = EdRestriction.getText();
	    	if(!restrictionName.isEmpty()) {
	    		
	    		if(!checkIfRestrictionAlreadyExists(restrictionName)) {
			    	Restriction restriction = new Restriction();
			    	restriction.setName(restrictionName);
			    	
			    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
			    	currentParticipant.addRestriction(restriction);
			    	restriction.addParticipant(currentParticipant);
			    
			    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
			    	List<Restriction> eventRestrictionList = currentEvent.getRestriction();
			    	eventRestrictionList.add(restriction);
			    	currentEvent.setRestriction(eventRestrictionList);
			    	
			    	CheckBox newRestriction = new CheckBox();
			    	newRestriction.setText(restrictionName);
			    	LvRestrictions.getItems().add(newRestriction);
		    	}
	    	}
	    	EdRestriction.clear();
	    }
	    
	    boolean checkIfRestrictionAlreadyExists(String name) {
	    	List<Restriction> restrictionList = walkingDinnerController.getWalkingDinner().getCurrentEvent().getRestriction();
	    	
	    	for(Restriction restr : restrictionList) {
	    		if (restr.getName().equals(name)) {
	    			return true;
	    		}
	    	}
	    	
	    	return false;
	    }

	    @FXML
	    void OnNoAlkoholSelected(ActionEvent event) {
	    	//Fill the original List with all Participants of the model
	    	Restriction alcohol = new Restriction();
	    	alcohol.setName("Kein Alkohol");
	    	alcohol.setPermanent(true);
	    	
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	currentParticipant.addRestriction(alcohol);
	    	alcohol.addParticipant(currentParticipant);
	    	
	    	
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> eventRestrictionList = currentEvent.getRestriction();
	    	eventRestrictionList.add(alcohol);
	    	currentEvent.setRestriction(eventRestrictionList);
	    	
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
	    	Restriction vegan = new Restriction();
	    	vegan.setName("Vegan");
	    	vegan.setPermanent(true);
	    	
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	currentParticipant.addRestriction(vegan);
	    	vegan.addParticipant(currentParticipant);
	    	
	    	
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> eventRestrictionList = currentEvent.getRestriction();
	    	eventRestrictionList.add(vegan);
	    	currentEvent.setRestriction(eventRestrictionList);
	    }

	    @FXML
	    void OnVegetarianSelected(ActionEvent event) {
	    	Restriction vegetarian = new Restriction();
	    	vegetarian.setName("Vegetarier");
	    	vegetarian.setPermanent(true);
	    	
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	currentParticipant.addRestriction(vegetarian);
	    	vegetarian.addParticipant(currentParticipant);
	    	
	    	
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> eventRestrictionList = currentEvent.getRestriction();
	    	eventRestrictionList.add(vegetarian);
	    	currentEvent.setRestriction(eventRestrictionList);
	    }
	    
	    @FXML
	    void OnWishCourseClicked(MouseEvent event) {
	    	CbWishCourse.getItems().addAll(Course.STARTER,Course.MAIN,Course.DESSERT);
	    }
	    	    
	    boolean isValidZipCode(){
	    	String zipCode = EdZipCode.getText();
	    	Pattern validZipCode = Pattern.compile("\\b\\d{5}\\b");
	    	
	    	return validZipCode.matcher(zipCode).matches();
	    }
	    
	    @FXML
	    void onBtnCallAction(ActionEvent event){
	    	if(validate()){
	    		ParticipantController partCont = walkingDinnerController.getParticipantController();
	    		RestrictionController restCont = walkingDinnerController.getRestrictionController();
	    		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    		if(CbAction.getSelectionModel().getSelectedItem()==ParticipantAction.REGISTER){
	    			Participant newPart = new Participant();
	    			currentEvent.setCurrentParticipant(newPart);
	    			partCont.setName(EdName.getText());
	    			partCont.setBirthDate(DateBirthday.getValue());
	    			Address address = new Address();
	    			address.setAddressAdditional(EdAddressExtra.getText());
	    			address.setCity(EdPlace.getText());
	    			address.setStreet(EdStreet.getText());
	    			address.setZipCode(EdZipCode.getText());
	    			partCont.setAddress(address);
	    			partCont.setCoursePreference(CbWishCourse.getSelectionModel().getSelectedItem());
	    			partCont.setWishes(EdSpecialWished.getText());
	    			//List<Restriction> restList = getRestrictions();
	    			//restCont.setParticipantRestrictions(restList);
	    			
	    			currentEvent.getParticipants().add(newPart);
	    			
	    			
	    		}
	    		else if(CbAction.getSelectionModel().getSelectedItem()==ParticipantAction.REGISTER){
	    			
	    		}
	    		else if(CbAction.getSelectionModel().getSelectedItem()==ParticipantAction.REGISTER){
	    			
	    		}
	    		else if(CbAction.getSelectionModel().getSelectedItem()==ParticipantAction.REGISTER){
	    			
	    		}
	    	}
	    }

	}




