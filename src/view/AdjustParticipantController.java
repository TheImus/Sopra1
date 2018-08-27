package view;

	import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.CheckBox;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.DatePicker;
	import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
	    private ComboBox<String> CbAction;

	    @FXML
	    private CheckBox CheckBoxNoAlkohol;

	    @FXML
	    private CheckBox CheckBoxVegetarian;

	    @FXML
	    private CheckBox CheckBoxVegan;

	    @FXML
	    private Button BtnNewRestriction;

	    @FXML
	    void OnCancel(ActionEvent event) {

	    }

	    @FXML
	    void OnCreateNewRestriction(ActionEvent event) {
	    	
	    }

	    @FXML
	    void OnNoAlkoholSelected(ActionEvent event) {
	    	
	    }

	    @FXML
	    void OnParticipantAction(MouseEvent event) {
	    	CbAction.getItems().add("hi");
	    }
	    
	    @FXML
	    void OnParticipantActionSelected(ActionEvent event){
	    	
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




