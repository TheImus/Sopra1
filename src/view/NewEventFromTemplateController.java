package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private Button BtnCreateEvent;

    @FXML
    void onCreateEvent(ActionEvent event) {

    }

    @FXML
    void onDispose(ActionEvent event) {

    }

}
