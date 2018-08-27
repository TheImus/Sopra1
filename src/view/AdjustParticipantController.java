package view;

	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.CheckBox;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.DatePicker;
	import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;

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
	    private ComboBox<?> CbWishCourse;

	    @FXML
	    private ComboBox<?> CbSearchBox;

	    @FXML
	    private Button BtnCancel;

	    @FXML
	    private ComboBox<?> CbAction;

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
	    void OnParticipantAction(ActionEvent event) {

	    }

	    @FXML
	    void OnVeganSelected(ActionEvent event) {

	    }

	    @FXML
	    void OnVegetarianSelected(ActionEvent event) {

	    }

	}




