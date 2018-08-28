package view;

	import java.util.ArrayList;

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
import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

	public class AdjustParticipantController {

	    @FXML
	    private DatePicker DateBirthday;

	    @FXML
	    private TextArea EdSpecialWished;

	    @FXML
	    private TextField EdName;

	    @FXML
	    private TextField EdZipCode;

	    @FXML
	    private TextField EdPlace;

	    @FXML
	    private TextField EdAddressExtra;

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
	    	
	    }

	    @FXML
	    void OnNoAlkoholSelected(ActionEvent event) {
	    	
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
	    	
	    	
	    }



		
	}




