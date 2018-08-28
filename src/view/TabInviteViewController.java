package view;
/**
 * Sample Skeleton for 'TabInvite.fxml' Controller Class
 */

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.WalkingDinnerController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.Participant;

public class TabInviteViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="TreePastEvents"
    private TreeView<?> TreePastEvents; // Value injected by FXMLLoader

    @FXML // fx:id="ListInvited"
    private ListView<Participant> ListInvited; // Value injected by FXMLLoader

    @FXML // fx:id="BtnInvite"
    private Button BtnInvite; // Value injected by FXMLLoader

    @FXML // fx:id="BtnUninvite"
    private Button BtnUninvite; // Value injected by FXMLLoader

    @FXML // fx:id="BtnCopyAddress"
    private Button BtnCopyAddress; // Value injected by FXMLLoader

    @FXML // fx:id="BtnCopyEmail"
    private Button BtnCopyEmail; // Value injected by FXMLLoader

    @FXML // fx:id="BtnSavePDF"
    private Button BtnSavePDF; // Value injected by FXMLLoader

    @FXML // fx:id="BtnDispose"
    private Button BtnDispose; // Value injected by FXMLLoader

    @FXML // fx:id="BtnSave"
    private Button BtnSave; // Value injected by FXMLLoader
    
    private WalkingDinnerController walkingDinnerController;
    
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
    /**
     * Load Data from Model
     */
    public void refresh() {
    	// get uninvited Participants
    	
    	// get invited Participants
    	List<Participant> invited = getWalkingDinnerController().getWalkingDinner().getCurrentEvent().getInvited();
    	ListInvited.getItems().clear();
    	ListInvited.getItems().addAll(invited);
    }
    
    
    /**
     * WalkingDinnerController is set
     */
    public void init() {
    	if (walkingDinnerController == null) {
    		throw new NullPointerException();
    	}
    	
    	// set cell factory for invited list
    	ListInvited.setCellFactory(view ->
			new ListCell<Participant>() {
				protected void updateItem(Participant item, boolean empty) {
			        super.updateItem(item, empty);
			        if (empty || item == null) {
			            setText("");
			        } else {
			            setText(item.getPerson().getName());
			        }
			    }
			}
		);
    	
    	ListInvited.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	
    	refresh();
    }

    @FXML
    void onAddressesToClipboard(ActionEvent event) {
    	
    }

    @FXML
    void onDispose(ActionEvent event) {
    	refresh();
    }

    @FXML
    void onEMailToClipboard(ActionEvent event) {
    	
    }

    @FXML
    void onExportPDF(ActionEvent event) {
    	
    }

    @FXML
    void onInvite(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void onUninvite(ActionEvent event) {
    	ObservableList<Participant> uninviteParticipants = ListInvited.getSelectionModel().getSelectedItems();
    	getWalkingDinnerController().getInvitationController().uninvite(uninviteParticipants);
    	refresh();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert TreePastEvents != null : "fx:id=\"TreePastEvents\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert ListInvited != null : "fx:id=\"ListInvited\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnInvite != null : "fx:id=\"BtnInvite\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnUninvite != null : "fx:id=\"BtnUninvite\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnCopyAddress != null : "fx:id=\"BtnCopyAddress\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnCopyEmail != null : "fx:id=\"BtnCopyEmail\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnSavePDF != null : "fx:id=\"BtnSavePDF\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnDispose != null : "fx:id=\"BtnDispose\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnSave != null : "fx:id=\"BtnSave\" was not injected: check your FXML file 'TabInvite.fxml'.";
    }

    public class InvitedEntry {
    	private String text;
    	
    	public InvitedEntry(String text) {
    		this.text = text;
    	}
    	
    	public String getText() {
    		return this.text;
    	}
    }
}
