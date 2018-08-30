package view;

	import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import controller.ParticipantAction;
import controller.ParticipantController;
import controller.RestrictionController;
import controller.WalkingDinnerController;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Address;
import model.Course;
import model.Event;
import model.Participant;
import model.Person;
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
	    private TextField EdEMail;

	    @FXML
	    private ComboBox<Course> CbWishCourse;

	    @FXML
	    private ComboBox<String> CbSearchBox;

	    @FXML
	    private Button BtnEdit;
	    
	    /*@FXML
	    private Button BtnDelete;*/
	    
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
	    
	    public WalkingDinnerController getWalkingDinnerController() {
			return walkingDinnerController;
		}
	    
	    
	    public boolean validate(){
	    	boolean passed = true;
	    	
	    	if(CbAction.getSelectionModel().getSelectedItem() == null){
    			CbAction.setStyle("-fx-border-color: red;");
    			passed=false;
    		}
    		else{
    			CbAction.setStyle("-fx-border-color: inherit;");
    			

    		}
	    	
	    	if(CbWishCourse.getSelectionModel().getSelectedItem() == null){
	    		CbWishCourse.setStyle("-fx-border-color: red;");
	    		passed=false;
    		}
    		else{
    			CbWishCourse.setStyle("-fx-border-color: inherit;");
    			

    		}
	    	
	    	try{
	    		EdName.setStyle("-fx-border-color: inherit;");
	    		if(EdName.getText().equals(""))
	    			throw new IllegalArgumentException();
	    	}
	    	catch(IllegalArgumentException e){
	    		EdName.setStyle("-fx-border-color: red;");
	    		passed=false;
	    	}
	    	
	    	try{
	    		EdEMail.setStyle("-fx-border-color: inherit;");
	    		if(EdEMail.getText().equals(""))
	    			throw new IllegalArgumentException();
	    	}
	    	catch(IllegalArgumentException e){
	    		EdEMail.setStyle("-fx-border-color: orange;");	    		
	    	}
	    	
	    	try{
	    		EdPlace.setStyle("-fx-border-color: inherit;");
	    		if(EdPlace.getText().equals(""))
	    			throw new IllegalArgumentException();
	    	}
	    	catch(IllegalArgumentException e){
	    		EdPlace.setStyle("-fx-border-color: red;");
	    		passed=false;
	    	}
	    	
	    	try{
	    		EdStreet.setStyle("-fx-border-color: inherit;");
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
	    		EdZipCode.setStyle("-fx-border-color: inherit;");
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
	    		DateBirthday.setStyle("-fx-border-color: inherit;");
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
		
		public ComboBox<ParticipantAction> getCbAction(){
			return CbAction;
		}

	    
	    public void init(){
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	CbAction.setCellFactory(actions ->
	    		new ListCell<ParticipantAction>() {
	    			@Override protected void updateItem(ParticipantAction item, boolean empty) {
	    				super.updateItem(item, empty);
	    				if (item == null) {
	    					setText("");
	    				} else {
	    					this.setText(item.toString());
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
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> eventRestriction = currentEvent.getRestriction();
	    	LvRestrictions.getItems().clear();
	    	for(Restriction restr : eventRestriction){
	    		
	    		String restrName = restr.getName();
	    		CheckBox restriction = new CheckBox();
	    		restriction.setText(restrName);
	    		LvRestrictions.getItems().add(restriction);
	    		if(currentParticipant!=null && currentParticipant.getRestrictions().contains(restr)){
	    			restriction.setSelected(true);
	    		}
	    	}
	    	
	    	if(currentParticipant!=null){
	    		EdName.setText(currentParticipant.getPerson().getName());
	    		EdZipCode.setText(currentParticipant.getAddress().getZipCode());
	    		EdAddressExtra.setText(currentParticipant.getAddress().getAddressAdditional());
	    		EdSpecialWished.setText(currentParticipant.getSpecialNeeds());
	    		EdPlace.setText(currentParticipant.getAddress().getCity());
	    		EdStreet.setText(currentParticipant.getAddress().getStreet());
	    		EdEMail.setText(currentParticipant.getPerson().getMailAddress());
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
	   			
	   			Tab tabOverview = tabsOverviewController.getTabParticipant();
	   			tabOverview.getTabPane().getSelectionModel().select(tabOverview);
	   			((Stage)GridPaneAdjustParticipant.getScene().getWindow()).setScene(scene);
	   			walkingDinnerController.getWalkingDinner().getCurrentEvent().setCurrentParticipant(null);
	   			
	   		} catch(Exception e) {
	   			e.printStackTrace();
	   		}
	    }
	    
	    @FXML
	    void onEditRestriction(ActionEvent event) {
	    	
	    	if(LvRestrictions.getSelectionModel().getSelectedItem() == null ||EdRestriction.getText().equals(""))
	    		return;
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> restrList = currentEvent.getRestriction();
	    	Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
	    	List<Restriction> participantRestrictions = currentParticipant.getRestrictions();
	    	
	    	
	    	
	    	CheckBox cb = LvRestrictions.getSelectionModel().getSelectedItem();
	    	String restrName = cb.getText();
	    	
	    	if(!(restrName.equals("Kein Alkohol") || restrName.equals("Veganer") || restrName.equals("Vegetarier"))){
		    	String newName = EdRestriction.getText();
		    	Restriction editedRestriction = walkingDinnerController.getRestrictionController().getRestrictionWithName(cb.getText());
		    	walkingDinnerController.getRestrictionController().renameRestriction(editedRestriction, newName);
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

	    /*
	    @FXML
	    void onDeleteRestriction(ActionEvent event) {
	    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
	    	List<Restriction> restrList = currentEvent.getRestriction();
	    	
	    	if(LvRestrictions.getSelectionModel().getSelectedItem() == null){
	    		return;
	    	}
	    	
	    	//Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
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
	    //}*/
	    
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
	    			RestrictionController restCont = walkingDinnerController.getRestrictionController();
	    			restCont.addNewRestriction(restrictionName);
//			    	Restriction restriction = new Restriction();
//			    	restriction.setName(restrictionName);
//			    	
//			    	/*Participant currentParticipant = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant();
//			    	currentParticipant.addRestriction(restriction);
//			    	restriction.addParticipant(currentParticipant);*/
//			    
		    	Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
//			    	List<Restriction> eventRestrictionList = currentEvent.getRestriction();
//			    	eventRestrictionList.add(restriction);
//			    	currentEvent.setRestriction(eventRestrictionList);
			    	
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
	    	//System.out.println("clicked");
	    }
	    
	    @FXML
	    void OnParticipantActionSelected(ActionEvent event){
	    	//System.out.println("selected");
	    	//refresh();
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
	    			Participant newPart = currentEvent.getCurrentParticipant();
	    			if(currentEvent.getCurrentParticipant()==null){
	    				newPart = new Participant();
	    			}
	    			newPart.setPerson(currentEvent.getCurrentParticipant().getPerson());
	    			currentEvent.setCurrentParticipant(newPart);
	    			partCont.setName(EdName.getText());
	    			partCont.setBirthDate(DateBirthday.getValue());
	    			Address address = new Address();
	    			address.setAddressAdditional(EdAddressExtra.getText());
	    			address.setCity(EdPlace.getText());
	    			address.setStreet(EdStreet.getText());
	    			address.setZipCode(EdZipCode.getText());
	    			partCont.setAddress(address);
	    			partCont.setMail(EdEMail.getText());
	    			partCont.setCoursePreference(CbWishCourse.getSelectionModel().getSelectedItem());
	    			partCont.setWishes(EdSpecialWished.getText());
	    			List<Restriction> restList = getRestrictions();
	    			restCont.setParticipantRestrictions(restList);
	    			
	    			walkingDinnerController.getParticipantActionController().register(newPart);
	    			
	    			
	    		}
	    		else if(CbAction.getSelectionModel().getSelectedItem()==ParticipantAction.REGISTER_NEW_PERSON){
	    			Participant newPart = new Participant();
	    			newPart.setPerson(new Person());
	    			currentEvent.setCurrentParticipant(newPart);
	    			partCont.setName(EdName.getText());
	    			partCont.setBirthDate(DateBirthday.getValue());
	    			Address address = new Address();
	    			address.setAddressAdditional(EdAddressExtra.getText());
	    			address.setCity(EdPlace.getText());
	    			address.setStreet(EdStreet.getText());
	    			address.setZipCode(EdZipCode.getText());
	    			partCont.setAddress(address);
	    			partCont.setMail(EdEMail.getText());
	    			partCont.setCoursePreference(CbWishCourse.getSelectionModel().getSelectedItem());
	    			partCont.setWishes(EdSpecialWished.getText());
	    			List<Restriction> restList = getRestrictions();
	    			restCont.setParticipantRestrictions(restList);
	    			
	    			walkingDinnerController.getParticipantActionController().register(newPart);
	    		}
	    		else if(CbAction.getSelectionModel().getSelectedItem()==ParticipantAction.UNREGISTER){
	    			walkingDinnerController.getParticipantActionController().unregister(currentEvent.getCurrentParticipant());
	    		}
	    		else if(CbAction.getSelectionModel().getSelectedItem()==ParticipantAction.UPDATE_PARTICIPANT){
	    			partCont.setName(EdName.getText());
	    			partCont.setBirthDate(DateBirthday.getValue());
	    			Address address = new Address();
	    			address.setAddressAdditional(EdAddressExtra.getText());
	    			address.setCity(EdPlace.getText());
	    			address.setStreet(EdStreet.getText());
	    			address.setZipCode(EdZipCode.getText());
	    			partCont.setAddress(address);
	    			partCont.setMail(EdEMail.getText());
	    			partCont.setCoursePreference(CbWishCourse.getSelectionModel().getSelectedItem());
	    			partCont.setWishes(EdSpecialWished.getText());
	    			List<Restriction> restList = getRestrictions();
	    			restCont.setParticipantRestrictions(restList);
	    		}
	    		
	    		try {
		   			GridPane root = new GridPane();
		   			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TabsOverview.fxml"));
		   			root = loader.load();
		   			
		   			TabsOverviewController tabsOverviewController = (TabsOverviewController) loader.getController();
		   			tabsOverviewController.setWalkingDinnerController(walkingDinnerController);
		   			tabsOverviewController.init();
		   			
		   			Scene scene = new Scene(root);
		   			
		   			Tab tabOverview = tabsOverviewController.getTabParticipant();
		   			tabOverview.getTabPane().getSelectionModel().select(tabOverview);
		   			
		   			((Stage)GridPaneAdjustParticipant.getScene().getWindow()).setScene(scene);
		   			walkingDinnerController.getWalkingDinner().getCurrentEvent().setCurrentParticipant(null);
		   			
		   		} catch(Exception e) {
		   			e.printStackTrace();
		   		}
	    	}
	    }
	    
	    public List<Restriction> getRestrictions(){ //hier liegt der fehler
	    	
	    	ArrayList<Restriction> selectedRestrictions = new ArrayList<>();
	    	RestrictionController restCont = walkingDinnerController.getRestrictionController();
	    	
	    	for(CheckBox restr : LvRestrictions.getItems()){
	    		if(restr.isSelected()){
	    			Restriction newRestriction = restCont.getRestrictionWithName(restr.getText());
	    			if(newRestriction!=null){
	    				selectedRestrictions.add(newRestriction);
	    				
	    			}	    			
	    		}
	    	}
	    	return selectedRestrictions;
	    }

	}




