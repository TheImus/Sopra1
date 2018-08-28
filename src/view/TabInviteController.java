package view;
import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;

public class TabInviteController{

	@FXML
    private TreeView<?> TreePastEvents;

    @FXML
    private ListView<?> ListInvited;

    @FXML
    private Button BtnInvite;

    @FXML
    private Button BtnUninvite;

    @FXML
    private Button BtnCopyAddress;

    @FXML
    private Button BtnCopyEmail;

    @FXML
    private Button BtnSavePDF;

    @FXML
    private Button BtnDispose;

    @FXML
    private Button BtnSave;

    @FXML
    void onCopyToClip(ActionEvent event) {

    }

    @FXML
    void onCopyToClipboard(ActionEvent event) {

    }

    @FXML
    void onDispose(ActionEvent event) {

    }

    @FXML
    void onExportPDF(ActionEvent event) {

    }

    @FXML
    void onInvite(ActionEvent event) {

    }

    @FXML
    void onUninvite(ActionEvent event) {

    }
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

}
